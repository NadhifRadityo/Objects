package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.Object.IdObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.DimensionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Direction.Compass;
import io.github.NadhifRadityo.Objects.Utilizations.Direction.Direction2D;
import io.github.NadhifRadityo.Objects.Utilizations.IdObjectUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class TabbedPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4115193786971165461L;
	
	protected JPanel tabButtonsPanel;
	protected JPanel tabWindowsPanel;
	protected final List<TabChangedListener> tabChangedListeners = new ArrayList<>();
	protected final Map<String, TabbedPanelGroup> tabs = new HashMap<>();

	protected Compass direction;
	protected TabbedPanelGroup currentTab;
	
	public TabbedPanel(Compass compassDirection) {
		this.direction = compassDirection.toPrimaryDirection();
		if(direction == null) throw new IllegalArgumentException("Invalid direction!");
		
		setLayout(new MigLayout("wrap, gap 0"));
		setOpaque(false);
		initTabsButton();
		initTabsWindow();
	}
	public TabbedPanel(Direction2D direction) { this(direction.toCompass()); }
	public TabbedPanel() { this(Direction2D.UP); }
	
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
		tabs.put(key, group); button.addActionListener(event -> selectTab(key)); rearrange();
	}
	public void removeTab(String key) { tabs.remove(key); rearrange(); }
	
	public void rearrange(List<String> keys) {
		if(keys.isEmpty()) return;
		for(String key : keys)
			tabButtonsPanel.add(tabs.get(key).getButton(), "grow");
		selectTab(keys.get(0));
	}
	public void rearrange() {
		ArrayList<TabbedPanelGroup> tabsGroup = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		ArrayList<String> sorted = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { tabsGroup.addAll(tabs.values()); tabsGroup.sort(Comparator.comparing(IdObject::getId));
			for(TabbedPanelGroup group : tabsGroup) sorted.add(group.getKey()); rearrange(sorted);
		} finally { Pool.returnObject(ArrayList.class, tabsGroup); Pool.returnObject(ArrayList.class, sorted); }
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
	public TabbedPanelGroup getCurrentTab() { return currentTab; }
	
	public List<TabChangedListener> getTabChangedListeners() { return Collections.unmodifiableList(tabChangedListeners); }
	public void addTabChangedListener(TabChangedListener listener) { tabChangedListeners.add(listener); }
	public void removeTabChangedListener(TabChangedListener listener) { tabChangedListeners.remove(listener); }
	
	public JPanel getTabButtonsPanel() { return tabButtonsPanel; }
	public TabbedPanelGroup getTab(String key) { return tabs.get(key); }
	
	public void setTabsButtonLocation(Compass compassDirection) { this.direction = compassDirection.toPrimaryDirection(); remove(tabButtonsPanel); initTabsButton(); }
	public void setTabsButtonLocation(Direction2D direction) { setTabsButtonLocation(direction.toCompass()); }
	@Override public void setSize(Dimension d) { super.setSize(d); rearrange(); }
	@Override public void setPreferredSize(Dimension preferredSize) { super.setPreferredSize(preferredSize); rearrange(); }
	@Override public void setSize(int width, int height) { super.setSize(width, height); rearrange(); }
	@Override public void setMaximumSize(Dimension maximumSize) { super.setMaximumSize(maximumSize); rearrange(); }
	@Override public void setMinimumSize(Dimension minimumSize) { super.setMinimumSize(minimumSize); rearrange(); }
	
	public static class TabbedPanelGroup implements IdObject {
		private final long id = IdObjectUtils.getNewId(TabbedPanelGroup.class);
		protected final TabbedPanel parent;
		protected final String key;
		protected final JButton button;
		protected final JPanel panel;
		
		private TabbedPanelGroup(TabbedPanel parent, String key, JButton button, JPanel panel) {
			this.parent = parent;
			this.key = key;
			this.button = button;
			this.panel = panel;
		}

		@Override public long getId() { return id; }
		public TabbedPanel getHolder() { return parent; }
		public String getKey() { return key; }
		public JButton getButton() { return button; }
		public JPanel getPanel() { return panel; }
	}
	public interface TabChangedListener{
		void onChange(TabbedPanelGroup tab);
	}
}
