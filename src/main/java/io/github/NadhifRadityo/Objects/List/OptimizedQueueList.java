package io.github.NadhifRadityo.Objects.List;

import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Utilizations.EclipseCollectionsUtils;
import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.api.tuple.primitive.ObjectLongPair;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectLongHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.SynchronizedObjectLongMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class OptimizedQueueList<E> implements DeadableObject, Iterable<E> {
	private static final long[] M_AFIELD_Synchronized$_$S = EclipseCollectionsUtils.M_AFIELD_Synchronized$_$S(SynchronizedObjectLongMap.class);
	private static final long[] M_AFIELD_$HashMap_$HM = EclipseCollectionsUtils.M_AFIELD_$HashMap_$HM(ObjectLongHashMap.class);
	private static final Object NULL_KEY = EclipseCollectionsUtils.getSentinelObjectMap_NULLKEY(ObjectLongHashMap.class);
	private static final Object REMOVED_KEY = EclipseCollectionsUtils.getSentinelObjectMap_REMOVEDKEY(ObjectLongHashMap.class);

	protected final MutableObjectLongMap<E> map;
	protected final ObjectLongPairImpl<Object> pair;
	protected final boolean isDeadAllowed;
	private volatile boolean isDead = false;
	private volatile boolean isWaiting = false;

	public OptimizedQueueList(boolean isDeadAllowed) {
		this.map = new ObjectLongHashMap<E>().asSynchronized();
		this.pair = new ObjectLongPairImpl<>(null, 0);
		this.isDeadAllowed = isDeadAllowed;
	}
	public OptimizedQueueList() { this(true); }

	@Override public boolean isDead() { return isDead; }
	@Override public void setDead() { quit(); }
	public Map.Entry<E, Long>[] getMap() {
		MutableObjectLongMap<E> sourceMap = (MutableObjectLongMap<E>) EclipseCollectionsUtils.getSynchronizedSourceMap(this.map, M_AFIELD_Synchronized$_$S);
		Map.Entry<E, Long>[] result = new Map.Entry[sourceMap.size()]; int j = 0;
		E[] keys = (E[]) EclipseCollectionsUtils.getKeys(sourceMap, M_AFIELD_$HashMap_$HM);
		long[] values = (long[]) EclipseCollectionsUtils.getValues(sourceMap, M_AFIELD_$HashMap_$HM);
		for(int i = 0; i < keys.length; i++) { E key = keys[i]; long value = values[i];
			if(key == null || key == NULL_KEY || key == REMOVED_KEY) continue;
			result[j++] = MapUtils.dumpMapEntry(key, value);
		} return result;
	}

	public synchronized Long add(E e) { return add(e, System.currentTimeMillis()); }
	public synchronized Long add(E e, long returnAt) {
		assertNotDead();
		map.put(e, returnAt);
		final Long returnVal = null; //not supported
		notifyAll();
		return returnVal;
	}
	public synchronized void addAll(Map<? extends E, ? extends Long> m) {
		assertNotDead();
		for(Map.Entry<? extends E, ? extends Long> entry : m.entrySet())
			map.put(entry.getKey(), entry.getValue());
		notifyAll();
	}
	public synchronized Long addIfAbsent(E obj) {
		return addIfAbsent(obj, System.currentTimeMillis());
	}
	public synchronized Long addIfAbsent(E obj, long returnAt) {
		assertNotDead();
		map.getIfAbsentPut(obj, returnAt);
		final Long returnVal = null; //not supported
		notifyAll();
		return returnVal;
	}

	public synchronized Long remove(E o) {
		assertNotDead();
		map.remove(o);
		final Long returnVal = null; //not supported
		notifyAll();
		return returnVal;
	}
	public synchronized boolean remove(E obj, long returnAt) {
		assertNotDead();
		final boolean returnVal = map.containsKey(obj); //partialy supported
		map.remove(obj);
		notifyAll();
		return returnVal;
	}

	private synchronized ObjectLongPairImpl<E> getSortedEntry(boolean fromOldest) {
		pair.setOne(REMOVED_KEY); pair.setTwo(Long.MAX_VALUE);
		MutableObjectLongMap<E> sourceMap = (MutableObjectLongMap<E>) EclipseCollectionsUtils.getSynchronizedSourceMap(this.map, M_AFIELD_Synchronized$_$S);
		E[] keys = (E[]) EclipseCollectionsUtils.getKeys(sourceMap, M_AFIELD_$HashMap_$HM);
		long[] values = (long[]) EclipseCollectionsUtils.getValues(sourceMap, M_AFIELD_$HashMap_$HM);
		for(int i = 0; i < keys.length; i++) { E key = keys[i]; long value = values[i];
			if(key == null || key == REMOVED_KEY) continue;
			if(fromOldest && value < pair.getTwo()) { pair.setOne(key); pair.setTwo(value); }
			else if(!fromOldest && value > pair.getTwo()) { pair.setOne(key); pair.setTwo(value); }
		} if(pair.getOne() == NULL_KEY) pair.setOne(null);
		return pair.getOne() != REMOVED_KEY ? (ObjectLongPairImpl<E>) pair : null;
	}
	private synchronized ObjectLongPairImpl<E> get0(boolean fromOldest) {
		ObjectLongPairImpl<E> value = null;
		while(value == null) {
			while(map.size() == 0) { isWaiting = true;
				try { wait(); } catch (InterruptedException ignored) { }
			} isWaiting = false;
			value = getSortedEntry(fromOldest);
		} return value;
	}
	public synchronized E get(boolean fromOldest) {
		if(map.size() == 0) assertNotDead();
		ObjectLongPairImpl<E> pair = get0(fromOldest);
		long scheduled; long now;
		while((scheduled = pair.getTwo()) > (now = System.currentTimeMillis())) {
			try { wait(scheduled - now); } catch(InterruptedException ignored) { } pair = get0(fromOldest);
		} map.remove(pair.getOne()); return pair.getOne();
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

	protected static class ObjectLongPairImpl<E> implements ObjectLongPair<E> {
		protected E key;
		protected long value;

		public ObjectLongPairImpl(E key, long value) {
			this.key = key;
			this.value = value;
		}

		@Override public E getOne() { return key; }
		@Override public long getTwo() { return value; }
		public void setOne(E key) { this.key = key; }
		public void setTwo(long value) { this.value = value; }

		@Override public int compareTo(ObjectLongPair<E> o) {
			int i = ((Comparable<E>) this.key).compareTo(o.getOne());
			if(i != 0) return i; return Long.compare(this.value, o.getTwo());
		}
	}

//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		final OptimizedQueueList<String> queue = new OptimizedQueueList<>(true);
//		Thread t1 = new Thread(RunnableUtils.convertToRunnable(true, () -> {
//			while(true) {
////			for(int i = 0; i <= 3; i++) {
//				if(queue.isDead()) return;
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
////		t2.start();
//		t1.start();
//		Scanner scanner = new Scanner(System.in);
//		while(true) {
//			String val = scanner.nextLine();
//			if(val.equalsIgnoreCase("quit")) break;
//			if(val.equalsIgnoreCase("null")) val = null;
//			System.out.println("Input: " + val); queue.add(val);
//		}
//		scanner.close();
//		queue.quit();
//		System.out.println("Waiting -> " + queue.isWaiting());
//		t1.stop();
//	}
}
