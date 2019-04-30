package io.github.NadhifRadityo.Objects.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.github.NadhifRadityo.Objects.Object.DeadableObject;

public class PriorityList<E> implements DeadableObject {
	protected final Map<E, Integer> map;
	protected final boolean deadAllowed;
	protected final boolean fromHighest;
	protected volatile boolean dead = false;
	
	public PriorityList(boolean deadAllowed, boolean fromHighest) {
		this.map = Collections.synchronizedMap(new HashMap<E, Integer>());
		this.deadAllowed = deadAllowed;
		this.fromHighest = fromHighest;
	}
	public PriorityList(boolean fromHighest) { this(true, fromHighest); }
	public PriorityList() { this(true, true); }
	
	@Override public boolean isDead() { return dead; }
	@Override public void setDead() { quit(); }
	public Map<E, Integer> getMap() { return Collections.unmodifiableMap(map); }
	
	public synchronized Integer add(E e) { return add(e, 0); }
	public synchronized Integer add(E e, int priority) {
		assertDead();
		final Integer returnVal = map.put(e, priority);
		notifyAll();
		return returnVal;
	}
	public synchronized void addAll(Map<? extends E, ? extends Integer> m) {
		assertDead();
		map.putAll(m);
		notifyAll();
	}
	public synchronized Integer addIfAbsent(E obj) {
		return addIfAbsent(obj, 0);
	}
	public synchronized Integer addIfAbsent(E obj, int priority) {
		assertDead();
		final Integer returnVal = map.putIfAbsent(obj, priority);
		notifyAll();
		return returnVal;
	}
	
	public synchronized Integer remove(E o) {
		assertDead();
		final Integer returnVal = map.remove(o);
		notifyAll();
		return returnVal;
	}
	public synchronized boolean remove(E obj, int priority) {
		assertDead();
		final boolean returnVal = map.remove(obj, priority);
		notifyAll();
		return returnVal;
	}
	
	public synchronized List<E> get() {
		assertDead();
		Set<Entry<E, Integer>> entry = map.entrySet();
		int minPriority = Integer.MAX_VALUE;
		int maxPriority = Integer.MIN_VALUE;
		List<E> returnVal = new ArrayList<>();
		
		for(Entry<E, Integer> pair : entry) {
			minPriority = Math.min(minPriority, pair.getValue());
			maxPriority = Math.max(maxPriority, pair.getValue());
		}
		
		for(int i = fromHighest ? maxPriority : minPriority; 
				fromHighest ? (i >= minPriority) : (i <= maxPriority); i += fromHighest ? -1 : +1) {
			List<E> values = new ArrayList<>();
			for(Entry<E, Integer> pair : entry) {
				if(pair.getValue().intValue() != i) continue;
				values.add(pair.getKey());
			} returnVal.addAll(values);
		} return returnVal;
	}
	
	public synchronized int size() {
		assertDead();
		return map.size();
	}
	public synchronized boolean contains(E o) {
		assertDead();
		return map.containsKey(o);
	}
	public synchronized boolean containsPriorityAt(int priority) {
		assertDead();
		return map.containsValue(priority);
	}
	
	public synchronized void quit() {
		if(!deadAllowed) throw new IllegalStateException("Not allowed to dead.");
		if(dead) return; this.dead = true;
		map.clear();
	}
	
//	public static void main(String[] args) {
//		PriorityList<String> list = new PriorityList<>();
//		list.add("test 0");
//		list.add("test 1", 1);
//		list.add("test -1", -1);
//		list.add("test 2", 2);
//		System.out.println(ArrayUtils.deepToString(list.get()));
//		list.remove("test 0");
//		list.remove("test -1");
//		System.out.println(ArrayUtils.deepToString(list.get()));
//		list.setDead();
//		System.out.println(list.isDead());
//		System.out.println(ArrayUtils.deepToString(list.get()));
//	}
}
