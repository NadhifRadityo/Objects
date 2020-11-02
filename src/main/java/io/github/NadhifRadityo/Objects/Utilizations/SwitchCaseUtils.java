package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Object.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;

public class SwitchCaseUtils {
	public static <T> T gotoSwitchCase(Class<T> clazz, ReferencedCallback<Object> switchCase, Object scase, Object... args) {
		while(true) {
			Object returned = switchCase.get(scase, args);
			if(clazz.equals(Void.class) && returned == null) return null;
			if(!clazz.isAssignableFrom(returned.getClass()) && !(returned instanceof Proxy))
				returned = caseGoto(returned);
			if(returned instanceof Proxy && Pool.getPool(Proxy.class).isUsing(returned)) {
				Object[] toPass = (Object[]) ((Proxy) returned).get();
				scase = toPass[0]; args = (Object[]) toPass[1];
				Pool.returnObject(Proxy.class, (Proxy) returned);
				continue;
			} return clazz.cast(returned);
		}
	}
	public static <T> T gotoSwitchCaseThrowable(Class<T> clazz, ThrowsReferencedCallback<Object> switchCase, Object scase, Object... args) throws Exception {
		while(true) {
			Object returned = switchCase.get(scase, args);
			if(clazz.equals(Void.class) && returned == null) return null;
			if(!clazz.isAssignableFrom(returned.getClass()) && !(returned instanceof Proxy))
				returned = caseGoto(returned);
			if(returned instanceof Proxy && Pool.getPool(Proxy.class).isUsing(returned)) {
				Object[] toPass = (Object[]) ((Proxy) returned).get();
				scase = toPass[0]; args = (Object[]) toPass[1];
				Pool.returnObject(Proxy.class, (Proxy) returned);
				continue;
			} return clazz.cast(returned);
		}
	}

	public static Proxy caseGoto(Object object, Object... args) {
		Proxy proxy = Pool.tryBorrow(Pool.getPool(Proxy.class));
		proxy.set(new Object[] { object, args }); return proxy;
	}
}
