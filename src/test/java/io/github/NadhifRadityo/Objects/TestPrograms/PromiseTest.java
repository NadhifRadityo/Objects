package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Object.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.ObjectUtils.Promise;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;
import io.github.NadhifRadityo.Objects.Thread.RunnablePost;
import org.json.JSONObject;

import java.nio.ByteBuffer;

import static io.github.NadhifRadityo.Objects.ObjectUtils.Promise.getResult;

@TestProgram(group = "Utilizations")
public class PromiseTest extends Tester {
	public PromiseTest() {
		super("PromiseTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		HandlerThread handlerThread = new HandlerThread(getName());
		handlerThread.start();
		handlerThread.getLooper().setProgressHandler((current, total, desc, job, looper) -> {
			String title = job instanceof RunnablePost.IdentifiedRunnablePost ? ((RunnablePost.IdentifiedRunnablePost) job).getTitle() : null;
			String subject = job instanceof RunnablePost.IdentifiedRunnablePost ? ((RunnablePost.IdentifiedRunnablePost) job).getSubject() : null;
			logger.log("Title: " + title + " Subject: " + subject + " Desc: " + desc + " (" + (current / total) * 100 + ")");
		});

		ReferencedCallback<Promise.Result> doPromise = (_args) -> {
			boolean isMomHappy = (boolean) _args[0];
			Promise promise = new Promise((args) -> {
				if(isMomHappy) { return new JSONObject("{ brand: 'Samsung', color: 'black' }"); }
				throw new Exception("mom is not happy");
			}, handlerThread.getThreadHandler(), "Ask", "Mom").thenV((ThrowsReferencedCallback.PThrowsVoidReferencedCallback) (args) -> {
				logger.log(getResult(args).getObject());
			}, "Yes", "I have new phone!").failV((ThrowsReferencedCallback.PThrowsVoidReferencedCallback) (args) -> {
				logger.log(getResult(args).getException().getMessage()); throw (Exception) getResult(args).getException();
			}, "Nope", "IDK why").lastlyV((ThrowsReferencedCallback.PThrowsVoidReferencedCallback) (args) -> {
				logger.log("Meh done!");
			}, "Fyuhh", "What ever.");
			promise.await();
			logger.log("Result: " + promise.getResult().getObject());
			logger.log("Error: " + promise.getResult().getException());
			return promise.getResult();
		};
		try {
			return doPromise.get(true).isResolved() && doPromise.get(false).isRejected();
		} finally {
			handlerThread.quit();
			handlerThread.join();
		}
	}
}
