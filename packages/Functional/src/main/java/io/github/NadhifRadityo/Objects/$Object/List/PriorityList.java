package io.github.NadhifRadityo.Objects.$Object.List;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;
import io.github.NadhifRadityo.Objects.$Interface.State.DeadableObject;
import io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Sorting.Sorting;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class PriorityList<E> implements DeadableObject, Iterable<E> {
	protected static final Comparator<Map.Entry<Object, Integer>> FROM_HIGHEST = Comparator.reverse(java.util.Comparator.comparingInt(Map.Entry::getValue));
	protected static final Comparator<Map.Entry<Object, Integer>> FROM_LOWEST = Comparator.natural(java.util.Comparator.comparingInt(Map.Entry::getValue));

	protected final Map<E, Integer> map;
	protected final boolean isDeadAllowed;
	private volatile boolean isDead = false;
	private volatile boolean isChanged = true;
	private volatile boolean lastFromHighest = false;

	public PriorityList(boolean isDeadAllowed) {
		this.map = Collections.synchronizedMap(new LinkedHashMap<>());
		this.isDeadAllowed = isDeadAllowed;
	} public PriorityList() { this(true); }

	@Override public boolean isDead() { return isDead; }
	@Override public void setDead() { quit(); }
	public Map.Entry<E, Integer>[] getMap() { return map.entrySet().toArray(new Map.Entry[0]); }

	public synchronized Integer add(E e) { return add(e, 0); }
	public synchronized Integer add(E e, int priority) {
		assertNotDead();
		final Integer returnVal = map.put(e, priority);
		isChanged = true;
		notifyAll();
		return returnVal;
	}
	public synchronized void addAll(Map<? extends E, ? extends Integer> m) {
		assertNotDead();
		map.putAll(m);
		isChanged = true;
		notifyAll();
	}
	public synchronized Integer addIfAbsent(E obj) {
		return addIfAbsent(obj, 0);
	}
	public synchronized Integer addIfAbsent(E obj, int priority) {
		assertNotDead();
		final Integer returnVal = map.putIfAbsent(obj, priority);
		isChanged = true;
		notifyAll();
		return returnVal;
	}

	public synchronized Integer remove(E o) {
		assertNotDead();
		final Integer returnVal = map.remove(o);
		notifyAll();
		return returnVal;
	}
	public synchronized boolean remove(E obj, int priority) {
		assertNotDead();
		final boolean returnVal = map.remove(obj, priority);
		notifyAll();
		return returnVal;
	}

	public synchronized Set<E> get(boolean fromHighest) {
		assertNotDead();
		if((isChanged || lastFromHighest != fromHighest) && map.size() > 1) {
			// Suspected as nearly sorted array
			lastFromHighest = fromHighest;
			if(fromHighest) Sorting.SHELL_SORT.sort(map, FROM_HIGHEST);
			else Sorting.INSERTION_SORT.sort(map, FROM_LOWEST);
			isChanged = false;
		} return map.keySet();
	} public synchronized Set<E> get() { return get(true); }

	public synchronized int size() {
		assertNotDead();
		return map.size();
	}
	public synchronized boolean contains(E o) {
		assertNotDead();
		return map.containsKey(o);
	}
	public synchronized boolean containsPriorityAt(int priority) {
		assertNotDead();
		return map.containsValue(priority);
	}
	public synchronized void clear() { map.clear(); }
	public synchronized boolean isEmpty() { return map.isEmpty(); }
	
	public synchronized void quit() {
		if(!isDeadAllowed) throw new IllegalStateException("Not allowed to dead.");
		if(isDead) return; this.isDead = true;
		map.clear();
	}

	@Override public Iterator<E> iterator() { return get().iterator(); }
	@Override public void forEach(Consumer<? super E> action) { get().forEach(action); }
	@Override public Spliterator<E> spliterator() { return get().spliterator(); }

//		public static void main(String[] args) {
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
