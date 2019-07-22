package io.github.NadhifRadityo.Objects.Pool;

import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;
import org.apache.commons.pool2.BasePooledObjectFactory;

public abstract class PoolFactory<T> extends BasePooledObjectFactory<Proxy<T>> { }
