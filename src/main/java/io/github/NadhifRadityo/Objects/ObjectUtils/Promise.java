package io.github.NadhifRadityo.Objects.ObjectUtils;

import io.github.NadhifRadityo.Objects.Object.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.RunnablePost;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Promise {
	protected final Map<RunnablePost, CallbackResult> callbacks;
	protected final Handler handler;
	protected final Result result;

	protected final Object runLock = new Object();
	protected boolean isRunning = false;

	protected Promise(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, Handler handler, String title, String subject, Result result) {
		this.callbacks = new LinkedHashMap<>();
		this.handler = handler;
		this.result = result;
		CallbackResult callbackResult = new CallbackResult(CallbackResult.CallbackType.MAIN);
		callbacks.put(handler.post(() -> doJob(callback, callbackResult, this), title, subject), callbackResult);
	}
	public Promise(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, Handler handler, String title, String subject) { this(callback, handler, title, subject, new Result()); }
	public Promise(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, Handler handler) { this(callback, handler, null, null); }
	public Promise(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, String title, String subject) { this(callback, Handler.getMain(), title, subject); }
	public Promise(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback) { this(callback, Handler.getMain(), null, null); }

	public Handler getHandler() { return handler; }
	public Result getResult() { return result; }
	public Promise await() { return await(-1); }
	public Promise await(long timeout) {
		Iterator<Map.Entry<RunnablePost, CallbackResult>> iterator = callbacks.entrySet().iterator();
		long start = System.currentTimeMillis();
		Consumer<RunnablePost> wait = (post) -> { synchronized(post) { while(!post.isDone()) {
			if(timeout >= 0) {
				long time = timeout - (System.currentTimeMillis() - start); if(time < 0) break;
				try { post.wait(time); } catch(InterruptedException ignored) { }
			} else try { post.wait(); } catch(InterruptedException ignored) { }
		} } };

		boolean mayCancelled = false;
		while(iterator.hasNext()) {
			if(timeout >= 0 && (System.currentTimeMillis() - start) >= timeout) return this;
			Map.Entry<RunnablePost, CallbackResult> entry = iterator.next();
			RunnablePost post = entry.getKey();
			CallbackResult result = entry.getValue();
			if(result.getType() == CallbackResult.CallbackType.MAIN) {
				if(!post.isDone()) wait.accept(post); mayCancelled = result.isRejected(); continue; }
			if(result.getType() == CallbackResult.CallbackType.THEN && !mayCancelled) {
				if(!post.isDone()) wait.accept(post); mayCancelled = result.isRejected(); continue; }
			if(result.getType() == CallbackResult.CallbackType.THEN && mayCancelled)
				continue; // Search for fail & lastly block
			if(result.getType() == CallbackResult.CallbackType.FAIL && mayCancelled) {
				if(!post.isDone()) wait.accept(post); mayCancelled = result.isRejected(); continue; }
			if(result.getType() == CallbackResult.CallbackType.FAIL && !mayCancelled)
				continue; // Search for then & lastly block
			if(result.getType() == CallbackResult.CallbackType.LASTLY) {
				if(!post.isDone()) wait.accept(post); mayCancelled = result.isRejected(); continue; }
			break;
		} return this;
	}

	public Promise then(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, String title, String subject) { CallbackResult callbackResult = new CallbackResult(CallbackResult.CallbackType.THEN); callbacks.put(handler.post(() -> { if(!result.isRejected()) doJob(callback, callbackResult, this); }, title, subject), callbackResult); return this; }
	public Promise then(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback) { return then(callback, null, null); }
	public Promise fail(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, String title, String subject) { CallbackResult callbackResult = new CallbackResult(CallbackResult.CallbackType.FAIL); callbacks.put(handler.post(() -> { if(result.isRejected()) doJob(callback, callbackResult, this); }, title, subject), callbackResult); return this; }
	public Promise fail(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback) { return fail(callback, null, null); }
	public Promise lastly(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback, String title, String subject) { CallbackResult callbackResult = new CallbackResult(CallbackResult.CallbackType.LASTLY); callbacks.put(handler.post(() -> doJob(callback, callbackResult, this), title, subject), callbackResult); return this; }
	public Promise lastly(ThrowsReferencedCallback.ThrowsObjectReferencedCallback callback) { return lastly(callback, null, null); }
	public Promise thenV(ThrowsReferencedCallback.ThrowsVoidReferencedCallback callback, String title, String subject) { return then((args) -> { callback.get(args); return getResult(args); }, title, subject); }
	public Promise thenV(ThrowsReferencedCallback.ThrowsVoidReferencedCallback callback) { return thenV(callback, null, null); }
	public Promise failV(ThrowsReferencedCallback.ThrowsVoidReferencedCallback callback, String title, String subject) { return fail((args) -> { callback.get(args); return getResult(args); }, title, subject); }
	public Promise failV(ThrowsReferencedCallback.ThrowsVoidReferencedCallback callback) { return failV(callback, null, null); }
	public Promise lastlyV(ThrowsReferencedCallback.ThrowsVoidReferencedCallback callback, String title, String subject) { return lastly((args) -> { callback.get(args); return getResult(args); }, title, subject); }
	public Promise lastlyV(ThrowsReferencedCallback.ThrowsVoidReferencedCallback callback) { return lastlyV(callback, null, null); }

	public static class Result {
		protected static final Throwable NULL = new Error();
		protected Object object = NULL;
		protected Throwable exception = NULL;

		protected void Resolve(Object object) { this.object = object; }
		protected void Reject(Throwable exception) { this.exception = exception != null ? exception : NULL; }

		public Object getObject() { return object != NULL ? object : null; }
		public Throwable getException() { return exception != NULL ? exception : null; }
		public boolean isResolved() { return object != NULL; }
		public boolean isRejected() { return exception != NULL; }
	}
	protected static class CallbackResult {
		protected final CallbackType type;
		protected boolean resolved;
		protected boolean rejected;

		protected CallbackResult(CallbackType type) {
			this.type = type;
		}

		public CallbackType getType() { return type; }
		public boolean isResolved() { return resolved; }
		public boolean isRejected() { return rejected; }

		public void setResolved() { this.resolved = true; }
		public void setRejected() { this.rejected = true; }

		protected enum CallbackType {
			MAIN, THEN, FAIL, LASTLY
		}
	}

	public static Result getResult(Object... args) { return (Result) args[0]; }
	public static Promise getPromise(Object... args) { return (Promise) args[1]; }
	public static Result resolve(Result result, Object object) { result.Resolve(object); return result; }
	public static Result reject(Result result, Throwable e) { result.Reject(e); return result; }
	public static Result resolve(Object object, Object... args) { return resolve(getResult(args), object); }
	public static Result reject(Throwable e, Object... args) { return reject(getResult(args), e); }
	protected static void doJob(ThrowsReferencedCallback<Object> callback, CallbackResult callbackResult, Promise promise) { synchronized(promise.runLock) {
		while(promise.isRunning) { ExceptionUtils.doSilentThrowsRunnable(false, promise.runLock::wait); }
		promise.isRunning = true;
		Result result = promise.getResult();
		ExceptionUtils.doSilentThrowsRunnable((e) -> reject(result, e), () -> {
			Object returned = callback.get(result, promise);
			if(returned != result) resolve(result, returned);
			if(callbackResult.getType() == CallbackResult.CallbackType.FAIL)
				reject(result, null);
		});
		if(result.isResolved()) callbackResult.setResolved();
		if(result.isRejected()) callbackResult.setRejected();
		promise.isRunning = false;
		promise.runLock.notifyAll();
	} }
}
