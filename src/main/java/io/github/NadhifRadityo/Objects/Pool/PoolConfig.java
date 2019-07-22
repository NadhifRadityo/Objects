package io.github.NadhifRadityo.Objects.Pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class PoolConfig extends GenericObjectPoolConfig {
	
	@Override public PoolConfig clone() { return (PoolConfig) super.clone(); }
}
