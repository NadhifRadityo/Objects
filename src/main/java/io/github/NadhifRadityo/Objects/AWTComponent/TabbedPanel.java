package io.github.NadhifRadityo.Objects.AWTComponent;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import io.github.NadhifRadityo.Objects.Object.DateCreateObject;
import io.github.NadhifRadityo.Objects.Utilizations.DimensionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Direction.Compass;
import io.github.NadhifRadityo.Objects.Utilizations.Direction.Direction2D;
import net.miginfocom.swing.MigLayout;

public class TabbedPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4115193786971165461L;
	
	private JPanel tabButtonsPanel;
	private JPanel tabWindowsPanel;
	private final List<TabChangedListener> tabChangedListeners = new ArrayList<TabChangedListener>();
	private final Map<String, TabbedPanelGroup> tabs = new HashMap<String, TabbedPanelGroup>();
	
	private Compass direction;
	private TabbedPanelGroup currentTab;
	
	public TabbedPanel(Compass compassDirection) {
		this.direction = compassDirection.toPrimaryDirection();
		if(direction == null) throw new IllegalArgumentException("Invalid direction!");
		
		setLayout(new MigLayout("wrap, gap 0"));
		setOpaque(false);
		initTabsButton();
		initTabsWindow();
	}
	
	public TabbedPanel(Direction2D direction) {
		this(direction.toCompass());
	}
	public TabbedPanel() {
		this(Direction2D.UP);
	}
	
	private void initTabsButton() {
		tabButtonsPanel = new JPanel();
		tabButtonsPanel.setLayout(new MigLayout("gap 0, fill" + (direction.equals(Compass.EAST) || direction.equals(Compass.WEST) ? ", wrap" : "")));
		tabButtonsPanel.setOpaque(false);
		add(tabButtonsPanel, direction.toString());
		rearrange();
	}
	private void initTabsWindow() {
		tabWindowsPanel = new JPanel();
		tabWindowsPanel.setLayout(new MigLayout("gap 0"));
		tabWindowsPanel.setOpaque(false);
		add(tabWindowsPanel);
		rearrange();
	}

	public void addTabs(String key, JButton button, JPanel panel) {
		TabbedPanelGroup group = new TabbedPanelGroup(this, key, button, panel);
		tabs.put(key, group);
		
		button.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent event) { selectTab(key); }
		});
		rearrange();
	}
	public void removeTab(String key) {
		tabs.remove(key);
		rearrange();
	}
	
	public void rearrange(List<String> keys) {
		if(keys.isEmpty()) return;
		for(String key : keys)
			tabButtonsPanel.add(tabs.get(key).getButton(), "grow");
		selectTab(keys.get(0));
	}
	public void rearrange() {
		List<TabbedPanelGroup> tabsGroup = new ArrayList<TabbedPanelGroup>(tabs.values());
		Collections.sort(tabsGroup, new Comparator<TabbedPanelGroup>() {
			@Override public int compare(TabbedPanelGroup o1, TabbedPanelGroup o2) {
				return o1.getDateObjectCreated().compareTo(o2.getDateObjectCreated());
			}
		});
		
		ArrayList<String> sorted = new ArrayList<String>();
		for(TabbedPanelGroup group : tabsGroup) sorted.add(group.getKey());  
		rearrange(sorted);
	}
	
	public void selectTab(String key) {
		TabbedPanelGroup group = tabs.get(key);
		if(group == null) return;
		
		currentTab = group;
		tabs.forEach((keys, val) -> {
			val.getPanel().setVisible(false);
			tabWindowsPanel.remove(val.getPanel());
		});
		
		Dimension panelDim = DimensionUtils.getMaxDimension();
		JPanel panel = group.getPanel();
		panel.setVisible(true);
		panel.setSize(panelDim);
		panel.setPreferredSize(panelDim);
		tabWindowsPanel.add(panel);
		tabChangedListeners.forEach(listener -> listener.onChange(group));
	}
	public TabbedPanelGroup getCurrentTab() {
		return currentTab;
	}
	
	public List<TabChangedListener> getTabChangedListeners() {
		return Collections.unmodifiableList(tabChangedListeners);
	}
	public void addTabChangedListener(TabChangedListener listener) {
		tabChangedListeners.add(listener);
	}
	public void removeTabChangedListener(TabChangedListener listener) {
		tabChangedListeners.remove(listener);
	}
	
	public JPanel getTabButtonsPanel() {
		return tabButtonsPanel;
	}
	public TabbedPanelGroup getTab(String key) {
		return tabs.get(key);
	}
	
	public void setTabsButtonLocation(Compass compassDirection) {
		this.direction = compassDirection.toPrimaryDirection();
		remove(tabButtonsPanel);
		initTabsButton();
	}
	public void setTabsButtonLocation(Direction2D direction) {
		setTabsButtonLocation(direction.toCompass());
	}
	public void setSize(Dimension d) {
		super.setSize(d);
		rearrange();
	}
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		rearrange();
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		rearrange();
	}
	public void setMaximumSize(Dimension maximumSize) {
		super.setMaximumSize(maximumSize);
		rearrange();
	}
	public void setMinimumSize(Dimension minimumSize) {
		super.setMinimumSize(minimumSize);
		rearrange();
	}
	
	public static class TabbedPanelGroup implements DateCreateObject {
		private final long timestampCreated = System.nanoTime();
		private TabbedPanel parent;
		private String key;
		private JButton button;
		private JPanel panel;
		
		private TabbedPanelGroup(TabbedPanel parent, String key, JButton button, JPanel panel) {
			this.parent = parent;
			this.key = key;
			this.button = button;
			this.panel = panel;
		}
		
		public TabbedPanel getHolder() {
			return parent;
		}
		public String getKey() {
			return key;
		}
		public JButton getButton() {
			return button;
		}
		public JPanel getPanel() {
			return panel;
		}

		@Override
		public long getTimestampObjectCreated() {
			return timestampCreated;
		}
	}
	public interface TabChangedListener{
		void onChange(TabbedPanelGroup tab);
	}
}
