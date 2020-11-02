package io.github.NadhifRadityo.Objects.Pool.Impl;

public abstract class BasePoolFactory<T, POOLED extends BasePooledObject<T, POOLED>> {
	public abstract T create() throws Exception;
	public abstract POOLED wrap(T obj);
	public POOLED makeObject() throws Exception { return wrap(create()); }
	public void activateObject(POOLED p) throws Exception { }
	public boolean validateObject(POOLED p) { return true; }
	public void passivateObject(POOLED p) throws Exception { }
	public void destroyObject(POOLED p) throws Exception { }
}
