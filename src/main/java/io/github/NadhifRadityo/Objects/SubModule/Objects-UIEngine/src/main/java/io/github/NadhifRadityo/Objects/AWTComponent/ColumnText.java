package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnText extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1599946490929612637L;

	protected int row;
	protected final Map<String, ColumnTextGroup> groups = new LinkedHashMap<>();
	
	public ColumnText(int row) { setRow(row); }
	public ColumnText() { this(1); }
	
	public JPanel addColumn(JLabel key, Component... components) {
		Component insert = components[0];
		JLabel twodots;
		if(components.length != 1 || !(components[0] instanceof JLabel)) {
			insert = new JPanel();
			((JPanel) insert).setOpaque(false);
			twodots = new JLabel();
			twodots.setFont(components[0] != null ? components[0].getFont() : null);
			twodots.setForeground(components[0] != null ? components[0].getForeground() : null);
			twodots.setOpaque(false);
			((JPanel) insert).add(twodots);
			for(Component component : components)
				if(component != null) ((JPanel) insert).add(component);
		} else twodots = (JLabel) insert;
		if(!twodots.getText().startsWith(":")) twodots.setText(": " + twodots.getText());
		key.setText(key.getText().replaceAll("([:]+([\\s]+|))$", ""));
		
		ColumnTextGroup group = new ColumnTextGroup(key, insert, this);
		firePropertyChange("ColumnAdded", groups.get(key.getText()), group);
		groups.put(key.getText(), group); rearrange();
		if(insert instanceof JPanel) return (JPanel) insert;
		return null;
	}
	public void removeColumn(String key) {
		ColumnTextGroup group = groups.remove(key);
		firePropertyChange("ColumnRemoved", group, null);
		rearrange();
	}
	public void removeColumn(JLabel key) { removeColumn(key.getText()); }

	private void rearrange(Collection<String> keys) {
		for(String key : keys) {
			ColumnTextGroup group = groups.get(key);
			JLabel keyLabel = group.getKey();
			Component valueLabel = group.getValue();

			remove(keyLabel);
			remove(valueLabel);

			add(keyLabel);
			add(valueLabel);
		}
	}
	public void rearrange() {
		rearrange(groups.keySet());
	}

	public void shuffle() {
		ArrayList<String> shuffleKey = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { shuffleKey.addAll(groups.keySet()); Collections.shuffle(shuffleKey);
			rearrange(shuffleKey); } finally { Pool.returnObject(ArrayList.class, shuffleKey); }
	}
	public void sort() {
		ArrayList<String> shuffleKey = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { shuffleKey.addAll(groups.keySet()); Collections.sort(shuffleKey);
			rearrange(shuffleKey); } finally { Pool.returnObject(ArrayList.class, shuffleKey); }
	}

	public ColumnTextGroup getColumn(String key) { return groups.get(key); }
	public ColumnTextGroup[] getAllColumns() { return groups.values().toArray(new ColumnTextGroup[0]); }
	public Map<String, ColumnTextGroup> getGroups() { return Collections.unmodifiableMap(groups); }

	public int getRow() { return row; }
	public void setRow(int row) {
		setLayout(new MigLayout("wrap " + row * 2));
		this.row = row; rearrange();
	}

	@Override public void removeAll() {
		String[] keys = groups.keySet().toArray(new String[0]);
		for(String key : keys) removeColumn(key);
		super.removeAll(); rearrange();
	}
	
//	public void addText(JLabel key, Component value) {
//		Component insert = value;
//		JLabel val;
//		if(!(value instanceof JLabel)) {
//			insert = new JPanel();
//			((JPanel) insert).setOpaque(false);
//			val = new JLabel();
//			val.setFont(value.getFont());
//			val.setForeground(value.getForeground());
//			val.setOpaque(false);
//			((JPanel) insert).add(val);
//			((JPanel) insert).add(value);
//		} else val = (JLabel) value;
//		if(!val.getText().startsWith(":")) val.setText(": " + val.getText());
//		key.setText(key.getText().replaceAll("([:]+([\\s]+|))$", ""));
//		
//		ColumnTextGroup group = new ColumnTextGroup(key, insert);
//		groups.put(key.getText(), group);
//		rearrange();
//	}
	
	public static class ColumnTextGroup {
		protected final JLabel key;
		protected final Component value;
		protected final ColumnText columnText;
		
		private ColumnTextGroup(JLabel key, Component value, ColumnText columnText) {
			this.key = key;
			this.value = value;
			this.columnText = columnText;
		}

		public JLabel getKey() { return key; }
		public Component getValue() { return value; }
		public ColumnText getColumnText() { return columnText; }
	}
}
