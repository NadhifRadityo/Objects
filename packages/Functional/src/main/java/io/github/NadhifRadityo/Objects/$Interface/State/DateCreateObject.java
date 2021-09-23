package io.github.NadhifRadityo.Objects.$Interface.State;

import java.util.Date;

public interface DateCreateObject {
	long getTimestampObjectCreated();
	default Date getDateObjectCreated() { return new Date(getTimestampObjectCreated()); }
}
