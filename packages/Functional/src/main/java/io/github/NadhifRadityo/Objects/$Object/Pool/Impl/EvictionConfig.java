package io.github.NadhifRadityo.Objects.$Object.Pool.Impl;

public class EvictionConfig {

	protected long idleEvictTime;
	protected long idleSoftEvictTime;
	protected int minIdle;

	public EvictionConfig(long idleEvictTime, long idleSoftEvictTime, int minIdle) {
		this.idleEvictTime = idleEvictTime > 0 ? idleEvictTime : Long.MAX_VALUE;
		this.idleSoftEvictTime = idleSoftEvictTime > 0 ? idleSoftEvictTime : Long.MAX_VALUE;
		this.minIdle = minIdle;
	}

	public long getIdleEvictTime() { return idleEvictTime; }
	public long getIdleSoftEvictTime() { return idleSoftEvictTime; }
	public int getMinIdle() { return minIdle; }

	public void setIdleEvictTime(long idleEvictTime) { this.idleEvictTime = idleEvictTime; }
	public void setIdleSoftEvictTime(long idleSoftEvictTime) { this.idleSoftEvictTime = idleSoftEvictTime; }
	public void setMinIdle(int minIdle) { this.minIdle = minIdle; }
}
