package io.github.NadhifRadityo.Objects.List;

import io.github.NadhifRadityo.Objects.Object.Comparator;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting.Sorting;
import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Spliterator;
import java.util.function.Consumer;

public class QueueList<E> implements DeadableObject, Iterable<E> {
	protected static final Comparator<Map.Entry<Object, Long>> FROM_LATEST = Comparator.reverse(java.util.Comparator.comparingLong(Map.Entry::getValue));
	protected static final Comparator<Map.Entry<Object, Long>> FROM_OLDEST = Comparator.natural(java.util.Comparator.comparingLong(Map.Entry::getValue));

	protected final Map<E, Long> map;
	protected final boolean isDeadAllowed;
	private volatile boolean isDead = false;
	private volatile boolean isWaiting = false;
	private volatile boolean isChanged = true;
	private volatile boolean lastFromOldest = false;

	public QueueList(boolean isDeadAllowed) {
		this.map = Collections.synchronizedMap(new LinkedHashMap<>());
		this.isDeadAllowed = isDeadAllowed;
	}
	public QueueList() { this(true); }

	@Override public boolean isDead() { return isDead; }
	@Override public void setDead() { quit(); }
	public Map.Entry<E, Long>[] getMap() { return map.entrySet().toArray(new Map.Entry[0]); }

	public synchronized Long add(E e) { return add(e, System.currentTimeMillis()); }
	public synchronized Long add(E e, long returnAt) {
		assertNotDead();
		final Long returnVal = map.put(e, returnAt);
		isChanged = true;
		notifyAll();
		return returnVal;
	}
	public synchronized void addAll(Map<? extends E, ? extends Long> m) {
		assertNotDead();
		map.putAll(m);
		isChanged = true;
		notifyAll();
	}
	public synchronized Long addIfAbsent(E obj) {
		return addIfAbsent(obj, System.currentTimeMillis());
	}
	public synchronized Long addIfAbsent(E obj, long returnAt) {
		assertNotDead();
		final Long returnVal = map.putIfAbsent(obj, returnAt);
		isChanged = true;
		notifyAll();
		return returnVal;
	}

	public synchronized Long remove(E o) {
		assertNotDead();
		final Long returnVal = map.remove(o);
		notifyAll();
		return returnVal;
	}
	public synchronized boolean remove(E obj, long returnAt) {
		assertNotDead();
		final boolean returnVal = map.remove(obj, returnAt);
		notifyAll();
		return returnVal;
	}

	private synchronized Entry<E, Long> getSortedEntry(boolean fromOldest) {
		if((isChanged || lastFromOldest != fromOldest) && map.size() > 1) {
			// Suspected as nearly sorted array
			lastFromOldest = fromOldest;
			if(fromOldest) Sorting.INSERTION_SORT.sort(map, FROM_OLDEST);
			else Sorting.SHELL_SORT.sort(map, FROM_LATEST);
			isChanged = false;
		} return MapUtils.getHead((LinkedHashMap<E, Long>) MapUtils.getSourceMap(map));
	}
	private synchronized Entry<E, Long> get0(boolean fromOldest) {
		Entry<E, Long> value = null;
		while(value == null) {
			while(map.size() == 0) { isWaiting = true;
				try { wait(); } catch (InterruptedException ignored) { }
			} isWaiting = false;
			value = getSortedEntry(fromOldest);
		} return value;
	}
	public synchronized E get(boolean fromOldest) {
		if(map.size() == 0) assertNotDead();
		Entry<E, Long> value = get0(fromOldest);
		long scheduled; long now;
		while((scheduled = NumberUtils.primitive(value.getValue(), 0L)) > (now = System.currentTimeMillis())) {
			try { wait(scheduled - now); } catch(InterruptedException ignored) { } value = get0(fromOldest);
		} map.remove(value.getKey()); return value.getKey();
	} public synchronized E get() { return get(true); }

	public synchronized int size() {
		assertNotDead();
		return map.size();
	}
	public synchronized boolean isWaiting() {
		assertNotDead();
		return isWaiting;
	}
	public synchronized boolean contains(E o) {
		assertNotDead();
		return map.containsKey(o);
	}
	public synchronized boolean containsReturnAt(long returnAt) {
		assertNotDead();
		return map.containsValue(returnAt);
	}
	public void clear() { map.clear(); }
	public boolean isEmpty() { return map.isEmpty(); }

	public synchronized void quit() {
		if(!isDeadAllowed) throw new IllegalStateException("Not allowed to dead.");
		if(isDead) return;
		map.put(null, Long.MIN_VALUE);
		notifyAll();
		this.isDead = true;
	}

	@Override public Iterator<E> iterator() { return map.keySet().iterator(); }
	@Override public void forEach(Consumer<? super E> action) { map.keySet().forEach(action); }
	@Override public Spliterator<E> spliterator() { return map.keySet().spliterator(); }

	//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		final ListQueue<String> queue = new ListQueue<>(true);
//		Thread t1 = new Thread(RunnableUtils.convertToRunnable(true, () -> {
//			while(true) {
////			for(int i = 0; i <= 3; i++) {
//				System.out.println("Length -> " + queue.size() + " Get -> " + queue.get());
//			}
//		}));
//		Thread t2 = new Thread(() -> {
//			while(true) {
//				System.out.println("Size -> " + queue.size());
//				System.out.println("Waiting -> " + queue.isWaiting());
//				try { Thread.sleep(200L);
//				} catch (InterruptedException e) { }
//			}
//		});
//		t2.start();
//		t1.start();
//		Scanner scanner = new Scanner(System.in);
//		while(true) {
//			String val = scanner.nextLine();
//			if(val.equalsIgnoreCase("quit")) break;
//			if(val.equalsIgnoreCase("null")) val = null;
//			queue.add(val);
//		}
//		scanner.close();
//		queue.quit();
//		System.out.println("Waiting -> " + queue.isWaiting());
//		t1.stop();
//	}
}
