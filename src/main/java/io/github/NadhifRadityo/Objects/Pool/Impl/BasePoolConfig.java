package io.github.NadhifRadityo.Objects.Pool.Impl;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.io.PrintWriter;

@SuppressWarnings({"jol", "unchecked"})
public class BasePoolConfig<T, POOLED extends BasePooledObject<T, POOLED>> {
	public static final boolean DEFAULT_LIFO = true;
	public static final boolean DEFAULT_FAIRNESS = false;
	public static final long DEFAULT_MAX_WAIT_MILLIS = -1L;
	public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1000L * 60L * 30L;
	public static final long DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = -1;
	public static final long DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS = 5L * 1000L;
	public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;
	public static final boolean DEFAULT_TEST_ON_CREATE = false;
	public static final boolean DEFAULT_TEST_ON_BORROW = false;
	public static final boolean DEFAULT_TEST_ON_RETURN = false;
	public static final boolean DEFAULT_TEST_WHILE_IDLE = false;
	public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS;
	public static final boolean DEFAULT_BLOCK_WHEN_EXHAUSTED = true;
	public static final boolean DEFAULT_JMX_ENABLE = true;
	public static final String DEFAULT_JMX_NAME_PREFIX = "pool";
	public static final String DEFAULT_JMX_NAME_BASE = null;
	public static final String DEFAULT_EVICTION_POLICY_CLASS_NAME = DefaultEvictionPolicy.class.getName();
	public static final int DEFAULT_MAX_TOTAL = 8;
	public static final int DEFAULT_MAX_IDLE = 8;
	public static final int DEFAULT_MIN_IDLE = 0;

	private boolean lifo = DEFAULT_LIFO;
	private boolean fairness = DEFAULT_FAIRNESS;
	private long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;
	private long minEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
	private long evictorShutdownTimeoutMillis = DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS;
	private long softMinEvictableIdleTimeMillis = DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
	private int numTestsPerEvictionRun = DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
	private EvictionPolicy<T, POOLED> evictionPolicy = null; // Only 2.6.0 applications set this
	//	private String evictionPolicyClassName = DEFAULT_EVICTION_POLICY_CLASS_NAME;
	private boolean testOnCreate = DEFAULT_TEST_ON_CREATE;
	private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;
	private boolean testOnReturn = DEFAULT_TEST_ON_RETURN;
	private boolean testWhileIdle = DEFAULT_TEST_WHILE_IDLE;
	private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
	private boolean blockWhenExhausted = DEFAULT_BLOCK_WHEN_EXHAUSTED;
	private boolean jmxEnabled = DEFAULT_JMX_ENABLE;
	private String jmxNamePrefix = DEFAULT_JMX_NAME_PREFIX;
	private String jmxNameBase = DEFAULT_JMX_NAME_BASE;
	private int maxTotal = DEFAULT_MAX_TOTAL;
	private int maxIdle = DEFAULT_MAX_IDLE;
	private int minIdle = DEFAULT_MIN_IDLE;

	public BasePoolConfig() {

	}

	public boolean getLifo() { return lifo; }
	public boolean getFairness() { return fairness; }
	public long getMaxWaitMillis() { return maxWaitMillis; }
	public long getMinEvictableIdleTimeMillis() { return minEvictableIdleTimeMillis; }
	public long getSoftMinEvictableIdleTimeMillis() { return softMinEvictableIdleTimeMillis; }
	public int getNumTestsPerEvictionRun() { return numTestsPerEvictionRun; }
	public long getEvictorShutdownTimeoutMillis() { return evictorShutdownTimeoutMillis; }
	public boolean getTestOnCreate() { return testOnCreate; }
	public boolean getTestOnBorrow() { return testOnBorrow; }
	public boolean getTestOnReturn() { return testOnReturn; }
	public boolean getTestWhileIdle() { return testWhileIdle; }
	public long getTimeBetweenEvictionRunsMillis() { return timeBetweenEvictionRunsMillis; }
	public EvictionPolicy<T, POOLED> getEvictionPolicy() { return evictionPolicy; }
	public String getEvictionPolicyClassName() { return evictionPolicy != null ? evictionPolicy.getClass().getName() : null; }
	public boolean getBlockWhenExhausted() { return blockWhenExhausted; }
	public boolean getJmxEnabled() { return jmxEnabled; }
	public String getJmxNameBase() { return jmxNameBase; }
	public String getJmxNamePrefix() { return jmxNamePrefix; }
	public int getMaxTotal() { return maxTotal; }
	public int getMaxIdle() { return maxIdle; }
	public int getMinIdle() { return minIdle; }

	public void setLifo(boolean lifo) { this.lifo = lifo; }
	public void setFairness(boolean fairness) { this.fairness = fairness; }
	public void setMaxWaitMillis(long maxWaitMillis) { this.maxWaitMillis = maxWaitMillis; }
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) { this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis; }
	public void setSoftMinEvictableIdleTimeMillis( long softMinEvictableIdleTimeMillis) { this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis; }
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) { this.numTestsPerEvictionRun = numTestsPerEvictionRun; }
	public void setEvictorShutdownTimeoutMillis(long evictorShutdownTimeoutMillis) { this.evictorShutdownTimeoutMillis = evictorShutdownTimeoutMillis; }
	public void setTestOnCreate(boolean testOnCreate) { this.testOnCreate = testOnCreate; }
	public void setTestOnBorrow(boolean testOnBorrow) { this.testOnBorrow = testOnBorrow; }
	public void setTestOnReturn(boolean testOnReturn) { this.testOnReturn = testOnReturn; }
	public void setTestWhileIdle(boolean testWhileIdle) { this.testWhileIdle = testWhileIdle; }
	public void setTimeBetweenEvictionRunsMillis( long timeBetweenEvictionRunsMillis) { this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis; }
	public void setEvictionPolicy(EvictionPolicy<T, POOLED> evictionPolicy) { this.evictionPolicy = evictionPolicy; }
	public void setEvictionPolicyClassName(String evictionPolicyClassName, ClassLoader classLoader) {
		setEvictionPolicy((EvictionPolicy<T, POOLED>) ExceptionUtils.doSilentThrowsReferencedCallback((ReferencedCallback<Object>)
				(args) -> ExceptionUtils.doSilentThrowsReferencedCallback(ExceptionUtils.silentException, (_args) -> Class.forName(evictionPolicyClassName, true, org.apache.commons.pool2.impl.EvictionPolicy.class.getClassLoader()).getConstructor().newInstance()),
				(args) -> Class.forName(evictionPolicyClassName, true, classLoader).getConstructor().newInstance()));
	}
	public void setEvictionPolicyClassName(String evictionPolicyClassName) { setEvictionPolicyClassName(evictionPolicyClassName, Thread.currentThread().getContextClassLoader()); }
	public void setBlockWhenExhausted(boolean blockWhenExhausted) { this.blockWhenExhausted = blockWhenExhausted; }
	public void setJmxEnabled(boolean jmxEnabled) { this.jmxEnabled = jmxEnabled; }
	public void setJmxNameBase(String jmxNameBase) { this.jmxNameBase = jmxNameBase; }
	public void setJmxNamePrefix(String jmxNamePrefix) { this.jmxNamePrefix = jmxNamePrefix; }
	public void setMaxTotal(int maxTotal) { this.maxTotal = maxTotal; }
	public void setMaxIdle(int maxIdle) { this.maxIdle = maxIdle; }
	public void setMinIdle(int minIdle) { this.minIdle = minIdle; }

	private boolean abandonedConfig = true;
	private boolean removeAbandonedOnBorrow = false;
	private boolean removeAbandonedOnMaintenance = true;
	private int removeAbandonedTimeout = 300;
	private boolean logAbandoned = false;
	private boolean requireFullStackTrace = true;
	private PrintWriter logWriter = new PrintWriter(System.out);
	private boolean useUsageTracking = false;

	public boolean isAbandonedConfig() { return abandonedConfig; }
	public boolean getRemoveAbandonedOnBorrow() { return this.removeAbandonedOnBorrow; }
	public boolean getRemoveAbandonedOnMaintenance() { return this.removeAbandonedOnMaintenance; }
	public boolean getLogAbandoned() { return this.logAbandoned; }
	public boolean getRequireFullStackTrace() { return requireFullStackTrace; }
	public PrintWriter getLogWriter() { return logWriter; }
	public boolean getUseUsageTracking() { return useUsageTracking; }
	public int getRemoveAbandonedTimeout() { return this.removeAbandonedTimeout; }
	public void setAbandonedConfig(boolean abandonedConfig) { this.abandonedConfig = abandonedConfig; }
	public void setRemoveAbandonedOnBorrow(boolean removeAbandonedOnBorrow) { this.removeAbandonedOnBorrow = removeAbandonedOnBorrow; }
	public void setRemoveAbandonedOnMaintenance(boolean removeAbandonedOnMaintenance) { this.removeAbandonedOnMaintenance = removeAbandonedOnMaintenance; }
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) { this.removeAbandonedTimeout = removeAbandonedTimeout; }
	public void setLogAbandoned(boolean logAbandoned) { this.logAbandoned = logAbandoned; }
	public void setRequireFullStackTrace(boolean requireFullStackTrace) { this.requireFullStackTrace = requireFullStackTrace; }
	public void setLogWriter(PrintWriter logWriter) { this.logWriter = logWriter; }
	public void setUseUsageTracking(boolean useUsageTracking) { this.useUsageTracking = useUsageTracking; }

	private ExceptionHandler exceptionHandler = ExceptionUtils.silentException;

	public ExceptionHandler getExceptionHandler() { return exceptionHandler; }
	public void setExceptionHandler(ExceptionHandler exceptionHandler) { this.exceptionHandler = exceptionHandler; }
}
