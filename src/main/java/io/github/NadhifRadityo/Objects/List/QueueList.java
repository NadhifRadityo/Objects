package io.github.NadhifRadityo.Objects.List;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.github.NadhifRadityo.Objects.Object.DeadableObject;

public class QueueList<E> implements DeadableObject {
	protected final Map<E, Long> map;
	protected final boolean deadAllowed;
	protected volatile boolean dead = false;
	private volatile boolean isWaiting = false;
	
	public QueueList(boolean deadAllowed) {
		this.map = Collections.synchronizedMap(new HashMap<E, Long>());
		this.deadAllowed = deadAllowed;
	} public QueueList() { this(true); }
	
	@Override public boolean isDead() { return dead; }
	@Override public void setDead() { quit(); }
	
	public synchronized Long add(E e) { return add(e, System.currentTimeMillis()); }
	public synchronized Long add(E e, long returnAt) {
		assertDead();
		final Long returnVal = map.put(e, returnAt);
		notifyAll();
		return returnVal;
	}
	public synchronized void addAll(Map<? extends E, ? extends Long> m) {
		assertDead();
		map.putAll(m);
		notifyAll();
	}
	public synchronized Long addIfAbsent(E obj) {
		return addIfAbsent(obj, System.currentTimeMillis());
	}
	public synchronized Long addIfAbsent(E obj, long returnAt) {
		assertDead();
		final Long returnVal = map.putIfAbsent(obj, returnAt);
		notifyAll();
		return returnVal;
	}
	
	public synchronized Long remove(E o) {
		assertDead();
		final Long returnVal = map.remove(o);
		notifyAll();
		return returnVal;
	}
	public synchronized boolean remove(E obj, long returnAt) {
		assertDead();
		final boolean returnVal = map.remove(obj, returnAt);
		notifyAll();
		return returnVal;
	}
	
	public synchronized E get() {
		assertDead();
		while(size() == 0) {
			try { isWaiting = true; wait();
			} catch (InterruptedException e) { e.printStackTrace(); }
		} isWaiting = false;
		
		Long minReturnAt = Long.MAX_VALUE;
		E returnVal = null;
		for(Entry<E, Long> entry : map.entrySet()) { if(entry.getValue().longValue() <= minReturnAt.longValue()) {
			minReturnAt = entry.getValue();
			returnVal = entry.getKey();
		} }
		map.remove(returnVal);
		return returnVal;
	}
	
	public synchronized int size() {
		assertDead();
		return map.size();
	}	
	public synchronized boolean isWaiting() {
		assertDead();
		return isWaiting;
	}
	public synchronized boolean contains(E o) {
		assertDead();
		return map.containsKey(o);
	}
	public synchronized boolean containsReturnAt(long returnAt) {
		assertDead();
		return map.containsValue(returnAt);
	}
	
	public synchronized void quit() {
		if(!deadAllowed) throw new IllegalStateException("Not allowed to dead.");
		if(dead) return; this.dead = true;
		map.put(null, 0L);
		notifyAll();
		map.clear();
	}
	
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
