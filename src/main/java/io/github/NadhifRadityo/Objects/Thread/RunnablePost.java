package io.github.NadhifRadityo.Objects.Thread;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class RunnablePost {
	private static long nextId = 0;
	protected synchronized long getNextId() { return nextId++; }
	
	protected final long id = getNextId();
	protected String title;
	protected String subject;
	
	public RunnablePost(String title, String subject) {
		this.title = title;
		this.subject = subject;
	}
	
	public long getId() { return id; }
	public String getTitle() { return title == null ? getClass().getCanonicalName() : title; }
	public String getSubject() { return subject; }
	
	public void setTitle(String title) { this.title = title; }
	public void setSubject(String subject) { this.subject = subject; }
	
	public abstract void work() throws Exception;
	public abstract void stop() throws Exception;

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!getClass().equals(other.getClass()))
			return false;
		RunnablePost castOther = RunnablePost.class.cast(other);
		return Objects.equals(id, castOther.id) && Objects.equals(title, castOther.title)
				&& Objects.equals(subject, castOther.subject);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, title, subject);
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("title", title).append("subject", subject).toString();
	}
}
