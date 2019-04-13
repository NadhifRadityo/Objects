package io.github.NadhifRadityo.Objects.AWTComponent;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import io.github.NadhifRadityo.Objects.Utilizations.DateCreateObject;
import net.miginfocom.swing.MigLayout;

public class ColumnText extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1599946490929612637L;
	
	private int row;
	private final Map<String, ColumnTextGroup> groups = new HashMap<String, ColumnTextGroup>();
	
	public ColumnText(int row) {
		setRow(row);
	}
	public ColumnText() {
		this(1);
	}
	
	public ColumnTextGroup getColumn(String key) {
		return groups.get(key);
	}
	public ColumnTextGroup[] getAllColumns() {
		return groups.values().toArray(new ColumnTextGroup[groups.size()]);
	}
	public Map<String, ColumnTextGroup> getGroups() {
		return Collections.unmodifiableMap(groups);
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		setLayout(new MigLayout("wrap " + row * 2));
		this.row = row;
		rearrange();
	}
	
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
		groups.put(key.getText(), group);
		rearrange();
		if(insert instanceof JPanel) return (JPanel) insert;
		return null;
	}
	public void removeColumn(String key) {
		ColumnTextGroup group = groups.remove(key);
		firePropertyChange("ColumnRemoved", group, null);
		rearrange();
	}
	public void removeColumn(JLabel key) {
		removeColumn(key.getText());
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
	
	private void rearrange(List<String> keys) {
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
		List<ColumnTextGroup> columnsGroup = new ArrayList<ColumnTextGroup>(groups.values());
		Collections.sort(columnsGroup, new Comparator<ColumnTextGroup>() {
			@Override public int compare(ColumnTextGroup o1, ColumnTextGroup o2) {
				return o1.getDateObjectCreated().compareTo(o2.getDateObjectCreated());
			}
		});
		
		ArrayList<String> sorted = new ArrayList<String>();
		for(ColumnTextGroup group : columnsGroup) sorted.add(group.getKey().getText());  
		rearrange(sorted);
	}
	
	public void shuffle() {
		List<String> shuffleKey = new ArrayList<String>(groups.keySet());
		Collections.shuffle(shuffleKey);
		rearrange(shuffleKey);
	}
	public void sort() {
		List<String> shuffleKey = new ArrayList<String>(groups.keySet());
		Collections.sort(shuffleKey);
		rearrange(shuffleKey);
	}
	
	public static class ColumnTextGroup implements DateCreateObject {
		private final long timestampCreated = System.currentTimeMillis();
		private final JLabel key;
		private final Component value;
		private final ColumnText columnText;
		
		private ColumnTextGroup(JLabel key, Component value, ColumnText columnText) {
			this.key = key;
			this.value = value;
			this.columnText = columnText;
		}
		
		public JLabel getKey() {
			return key;
		}
		public Component getValue() {
			return value;
		}
		public ColumnText getColumnText() {
			return columnText;
		}
		
		@Override
		public long getTimestampObjectCreated() {
			return timestampCreated;
		}
	}
}
