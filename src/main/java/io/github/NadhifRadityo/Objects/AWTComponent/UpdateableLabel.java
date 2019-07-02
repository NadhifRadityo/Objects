package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class UpdateableLabel extends Label {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1231842608460004208L;
	
	protected ReferencedCallback<String> callback;
	
	public UpdateableLabel(ReferencedCallback<String> callback) {
		super(); if(callback != null) setText(callback.get(this));
		this.callback = callback;
	}
	
	public ReferencedCallback<String> getCallback() { return callback; }
	public void setCallback(ReferencedCallback<String> callback) { this.callback = callback; }
	
	@Override public void updateUI() { updateText(); super.updateUI(); }
	public void updateText() { if(callback != null) setText(callback.get(this)); }

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("callback", callback).toString();
	}
	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof UpdateableLabel))
			return false;
		UpdateableLabel castOther = (UpdateableLabel) other;
		return new EqualsBuilder().appendSuper(super.equals(other)).append(callback, castOther.callback).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(callback).toHashCode();
	}
}
