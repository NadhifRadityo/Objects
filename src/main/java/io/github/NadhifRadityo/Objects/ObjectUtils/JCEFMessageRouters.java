package io.github.NadhifRadityo.Objects.ObjectUtils;

import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;
import org.json.JSONObject;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JCEFUtils;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SecurityUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;

public class JCEFMessageRouters implements DeadableObject {
	protected ExceptionHandler exceptionHandler;
	protected volatile boolean dead = false;

	protected final CefClient client;
	protected final CefBrowser browser;
	protected final CefMessageRouter router;

	public JCEFMessageRouters(CefClient client, CefBrowser browser) {
		this.client = client;
		this.browser = browser;
		this.router = CefMessageRouter.create();
		client.addMessageRouter(router);
		attachListener();
		JCEFUtils.runJs(generateCefQuery(MessageType.VALIDATE, "window.Utilization && window.Utilization.CEF ? 'true' + '|' + Utilization.CEF.newInstance() : 'false'"), browser);
	}

	public ExceptionHandler getExceptionHandler() { return exceptionHandler; }
	public CefClient getClient() { assertDead(); return client; }
	public CefBrowser getBrowser() { assertDead(); return browser; }
	public CefMessageRouter getRouter() { assertDead(); return router; }

	public void setExceptionHandler(ExceptionHandler exceptionHandler) { this.exceptionHandler = exceptionHandler; }

	@Override public boolean isDead() { return dead; }
	@Override public void setDead() { this.dead = true; }

	protected void attachListener() {
		router.addHandler(new CefMessageRouterHandlerAdapter() {
			final int n = (int) NumberUtils.map(Math.random(), 0, 1, 7, 28);
			String instanceKey = "";
			@Override public boolean onQuery(CefBrowser browser, CefFrame frame, long query_id, String request,
					boolean persistent, CefQueryCallback callback) { ExceptionUtils.doSilentException(exceptionHandler, (ThrowsRunnable) () -> {
				MessageType type = MessageType.fromType(Integer.parseInt(request.split("|")[0]));
				String data = request.replaceFirst(type.getType() + "|", "");
				if(type == MessageType.ERROR) exceptionHandler.onExceptionOccurred(new Exception(new JSONObject(data).getString("stack")));
				else if(type == MessageType.VALIDATE) {
					if(Boolean.parseBoolean(data.split("|")[0])) {
						instanceKey = data.split("|")[1]; 
						JSONObject messageTypes = new JSONObject();
						for(MessageType message : MessageType.values()) messageTypes.put(message.name(), message.getType());
						JCEFUtils.runJs(generateCefQuery(MessageType.HANDSHAKE, "window.Utilization.CEF.registerMessageTypes('" + StringUtils.escapeString(messageTypes.toString()) + "', "
								+ (instanceKey + System.identityHashCode(JCEFMessageRouters.this)) + ", " + n + ");"), browser);
					} else { setDead(); throw new Exception("CEFUtilization is not available in browser!"); }
				} else if(type == MessageType.HANDSHAKE) {
					JSONObject messageTypes = new JSONObject();
					for(MessageType message : MessageType.values()) messageTypes.put(message.name(), message.getType());
					String garbHash = StringUtils.mergeCross(messageTypes.toString(), instanceKey + System.identityHashCode(JCEFMessageRouters.this));
					String[] shuffle = new String[garbHash.length() / n + (garbHash.length() % n > 1 ? 1 : 0)];
					for(int i = 0; i < shuffle.length; i++) {
						shuffle[i] = garbHash.substring(i * n, Math.min(i * n + n, garbHash.length()));
						if(i % 3 == 0) shuffle[i] = SecurityUtils.getMD5(shuffle[i]).toString();
					} String finalHash = SecurityUtils.getSHA256(StringUtils.mergeCross(shuffle));
					System.out.println(data + " => " + finalHash);
					if(!data.equals(finalHash)) { setDead(); throw new Exception("Failed validating!"); }
					
				}
			}); return true; }
		}, true);
	}

	protected static String generateCefQuery(MessageType messageType, String request) {
		return String.format("try {\n"
				+ "window.cefQuery({\n"
				+ "		request: %d + '|' + (%s),\n"
				+ "		onSuccess: function(response) {},\n"
				+ "		onFailure: function(code, msg) { alert('Error! Code: ' + code + ' Msg: ' + msg); }\n"
				+ "});\n"
				+ ""
				+ "} catch(e) {\n"
				+ ""
				+ "window.cefQuery({\n"
				+ "		request: %d + '|' + (function() { var ex = {}; Object.assign(ex, e); ex.stack = e.stack; return ex; })(),\n"
				+ "		onSuccess: function(response) {},\n"
				+ "		onFailure: function(code, msg) { alert('Error! Code: ' + code + ' Msg: ' + msg); }\n"
				+ "});\n"
				+ "throw e;\n"
				+ ""
				+ "}\n", messageType.getType(), request, MessageType.ERROR.getType());
	}

	protected enum MessageType {
		ERROR(-1),
		VALIDATE(0),
		HANDSHAKE(1),
		MESSAGE(2);

		private final int type;
		MessageType(int type) {
			this.type = type;
		}

		public int getType() { return type; }

		public static MessageType fromType(int type) {
			for(MessageType message : MessageType.values()) {
				if(message.getType() == type) return message;
			} return null;
		}
	}
}
