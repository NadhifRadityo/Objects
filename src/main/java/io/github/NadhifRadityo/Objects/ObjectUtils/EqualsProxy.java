package io.github.NadhifRadityo.Objects.ObjectUtils;

import io.github.NadhifRadityo.Objects.Utilizations.SecurityUtils;

import java.util.Objects;
import java.util.UUID;

public class EqualsProxy<T> extends Proxy<T> {
	private final UUID id;

	public EqualsProxy(T object) { super(object); this.id = SecurityUtils.getRandomUUID(); }
	public EqualsProxy() { this(null); }

	@Override public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof EqualsProxy)) return false;
		EqualsProxy that = (EqualsProxy) obj;
		return Objects.equals(this.id, that.id);
	} @Override public int hashCode() { return this.id != null ? this.id.hashCode() : 0; }
}
