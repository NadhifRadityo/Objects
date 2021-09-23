package io.github.NadhifRadityo.Objects.$Object.Thread;

public abstract class RunnablePost {
	protected boolean done;
	protected boolean cancelled;
	protected boolean fixedRate;
	protected long period;

	public RunnablePost(boolean fixedRate, long period) {
		this.fixedRate = fixedRate;
		this.period = period;
	}
	public RunnablePost() {
		this(false, 0);
	}

	public boolean isDone() { return done; }
	public boolean isCancelled() { return cancelled; }
	public boolean getFixedRate() { return fixedRate; }
	public long getPeriod() { return period; }

	public void setCancelled() { this.cancelled = true; }
	public void setFixedRate(boolean fixedRate) { this.fixedRate = fixedRate; }
	public void setPeriod(long period) { this.period = period; }

	public abstract void work() throws Exception;
	public abstract void stop() throws Exception;

	@Override
	public String toString() {
		return "RunnablePost{" +
				"done=" + done +
				", cancelled=" + cancelled +
				", fixedRate=" + fixedRate +
				", period=" + period +
				"} (" + Integer.toHexString(System.identityHashCode(this)) + ")";
	}

	public static abstract class IdentifiedRunnablePost extends RunnablePost {
		protected String title;
		protected String subject;

		public IdentifiedRunnablePost(boolean fixedRate, long period, String title, String subject) {
			super(fixedRate, period);
			this.title = title;
			this.subject = subject;
		}
		public IdentifiedRunnablePost(String title, String subject) {
			this(false, 0, title, subject);
		}

		public String getTitle() { return title == null ? getClass().getCanonicalName() : title; }
		public String getSubject() { return subject; }

		public void setTitle(String title) { this.title = title; }
		public void setSubject(String subject) { this.subject = subject; }

		@Override
		public String toString() {
			return "IdentifiedRunnablePost(" +
					"done=" + done +
					", cancelled=" + cancelled +
					", fixedRate=" + fixedRate +
					", period=" + period +
					", title='" + title + '\'' +
					", subject='" + subject + '\'' +
					"} (" + Integer.toHexString(System.identityHashCode(this)) + ")";
		}
	}
}
