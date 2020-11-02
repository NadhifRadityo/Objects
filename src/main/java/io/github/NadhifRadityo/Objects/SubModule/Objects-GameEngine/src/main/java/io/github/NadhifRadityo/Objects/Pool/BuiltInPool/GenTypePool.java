package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.*;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;

@SuppressWarnings("jol")
public abstract class GenTypePool<G extends GenType> extends Pool<G> {

	protected GenTypePool(PoolFactory<G> factory) { super(factory); }
	protected GenTypePool(PoolFactory<G> factory, PoolConfig<G> config) { super(factory, config); }

//	private static ThreadLocal<GenTypeIdentityWrapper<GenType>> identityWrappers = new ThreadLocal<>();
//	@SuppressWarnings({"unchecked", "rawtypes"})
//	@Override protected IdentityWrapper<G> identityWrapper(G object) {
//		GenTypeIdentityWrapper<G> result = (GenTypeIdentityWrapper<G>) identityWrappers.get();
//		if(result == null) {
//			result = new GenTypeIdentityWrapper(null);
//			identityWrappers.set((GenTypeIdentityWrapper<GenType>) result);
//		}
//		result.setInstance(object);
//		result.setHashCalculated();
//		return result;
//	}
//	@Override protected IdentityWrapper<G> newIdentityWrapper(G object) {
//		return new GenTypeIdentityWrapper<>(object);
//	}
//
//	protected static class GenTypeIdentityWrapper<G extends GenType> extends IdentityWrapper<G> {
//		public GenTypeIdentityWrapper(G instance) { super(instance); }
//
//		protected void setInstance(G instance) { this.instance = instance; }
//		protected void setHashCalculated() { this.hashCalculated = false; }
//
//		@Override public int hashCode() {
//			if(hashCalculated) return lastHash; hashCalculated = true;
//			return lastHash = calculateHash(instance);
//		}
//
//		@SuppressWarnings("ArrayHashCode")
//		protected static int calculateHash(GenType genType) {
//			if(genType instanceof IVecN) return ((IVecN) genType).d.hashCode();
//			if(genType instanceof LVecN) return ((LVecN) genType).d.hashCode();
//			if(genType instanceof SVecN) return ((SVecN) genType).d.hashCode();
//			if(genType instanceof FVecN) return ((FVecN) genType).d.hashCode();
//			if(genType instanceof VecN) return ((VecN) genType).d.hashCode();
//			if(genType instanceof IMatMxN) return ((IMatMxN) genType).d.hashCode();
//			if(genType instanceof LMatMxN) return ((LMatMxN) genType).d.hashCode();
//			if(genType instanceof SMatMxN) return ((SMatMxN) genType).d.hashCode();
//			if(genType instanceof FMatMxN) return ((FMatMxN) genType).d.hashCode();
//			if(genType instanceof MatMxN) return ((MatMxN) genType).d.hashCode();
//			throw new IllegalArgumentException("GenType not supported");
//		}
//	}
}
