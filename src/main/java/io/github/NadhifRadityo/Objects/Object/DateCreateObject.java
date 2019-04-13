package io.github.NadhifRadityo.Objects.Object;

import java.util.Date;

public interface DateCreateObject {
	public long getTimestampObjectCreated();
	default public Date getDateObjectCreated() {
		return new Date(getTimestampObjectCreated());
	}
}
