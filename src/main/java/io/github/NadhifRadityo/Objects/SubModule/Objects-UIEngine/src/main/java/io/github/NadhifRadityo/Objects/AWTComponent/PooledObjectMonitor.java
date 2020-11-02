package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class PooledObjectMonitor extends JPanel {
	protected static Handler updateHandler;
	protected static long updateDelay = 1000;
	private static final List<PooledObjectMonitor> listenForUpdate = new ArrayList<>();
	private static final PooledObjectMonitor mainPooledObjectMonitor;

	public static Handler getUpdateHandler() { return updateHandler; }
	public static long getUpdateDelay() { return updateDelay; }
	public static PooledObjectMonitor getMainPooledObjectMonitor() { return mainPooledObjectMonitor; }

	public static void setUpdateHandler(Handler handler) {
		updateHandler = handler; if(handler == null) return;
		updateHandler.post(new Runnable() { @Override public void run() {
			listenForUpdate.forEach(PooledObjectMonitor::update);
			if(updateHandler == null || !updateHandler.equals(handler)) return;
			updateHandler.postDelayed(this, updateDelay);
		} });
	}
	public static void setUpdateDelay(long delay) { updateDelay = delay; }

	static {
		// Making sure Pool is inited
		if(Pool.getPools().size() == 0) Pool.getPool(ArrayList.class);
		mainPooledObjectMonitor = new PooledObjectMonitor(Pool.getPools().keySet().toArray(new Class[0]));
	}

	protected final Table table;
	protected final List<Class> types;

	public PooledObjectMonitor(Class... types) {
		setLayout(new MigLayout());
		this.table = new Table(new DefaultTableModel(new String[] {
				"Class Name",
				"Created", "Borrowed", "Returned", "Destroyed",
				"Num. Idle", "Num. Active", "Num. Waiters"
		}, 0));
		this.types = new ArrayList<>(Arrays.asList(types));
		rearrange();
		
		add(new JScrollPane(table));
		if(this.types.size() > 0) listenForUpdate.add(this);
	}

	public void addType(Class type) { types.add(type); rearrange(); if(!listenForUpdate.contains(this)) listenForUpdate.add(this); }
	public void removeType(Class type) { types.remove(type); rearrange(); if(types.size() == 0) listenForUpdate.remove(this); }
	public List<Class> getTypes() { return Collections.unmodifiableList(types); }
	public Table getTable() { return table; }

	protected synchronized void rearrange() {
		types.sort(Comparator.comparing(Class::getCanonicalName, StringUtils.sortStringWithNumber));
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		for(int i = 0; i < tableModel.getRowCount(); i++) tableModel.removeRow(i);
		for(Class type : types) { ArrayList<String> rowData = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			rowData.add(type.getCanonicalName()); rowData.add("0");
			rowData.add("0"); rowData.add("0"); rowData.add("0");
			rowData.add("0"); rowData.add("0"); rowData.add("0");
			tableModel.addRow(rowData.toArray());
		} finally { Pool.returnObject(ArrayList.class, rowData); } }
	}
	protected synchronized void update() {
		for(int i = 0; i < types.size(); i++) {
			Class<?> type = types.get(i);
			Pool pool = Pool.getPool(type);
			if(pool == null) continue;
			table.setValueAt(pool.getCreatedCount(), i, 1);
			table.setValueAt(pool.getBorrowedCount(), i, 2);
			table.setValueAt(pool.getReturnedCount(), i, 3);
			table.setValueAt(pool.getDestroyedCount(), i, 4);
			table.setValueAt(pool.getTotalIdle(), i, 5);
			table.setValueAt(pool.getTotalActive(), i, 6);
			table.setValueAt(pool.getTotalWaiters(), i, 7);
		}
	}
}
