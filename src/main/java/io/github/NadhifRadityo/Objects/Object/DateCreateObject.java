package io.github.NadhifRadityo.Objects.Object;

import java.util.Date;

public interface DateCreateObject {
	long getTimestampObjectCreated();
	default Date getDateObjectCreated() { return new Date(getTimestampObjectCreated()); }
}
