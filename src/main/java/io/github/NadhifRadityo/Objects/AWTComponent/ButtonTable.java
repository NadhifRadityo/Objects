package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.AWTComponent.Table.EmptyTableModel;
import io.github.NadhifRadityo.Objects.Utilizations.RandomString;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

public class ButtonTable {
	protected TableCellRenderer renderer = (table, value, isSelected, hasFocus, row, col) -> {
		ButtonTablePanel buttonPanel = getTablePanel(row, col);
		if(buttonPanel == null) return null;
		buttonPanel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		buttonPanel.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
		return buttonPanel;
	};
	protected TableCellEditor editor = new ButtonTableCellEditor();
	protected class ButtonTableCellEditor extends AbstractCellEditor implements TableCellEditor {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7730541905051799746L;
		@Override public Object getCellEditorValue() { return currentButton; }
		@Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
			ButtonTablePanel buttonPanel = getTablePanel(row, col);
			if(buttonPanel == null) return null;
			buttonPanel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
			buttonPanel.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
			return buttonPanel;
		}
	}
	protected TableModel model = new EmptyTableModel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6652535574218994263L;
		@Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			JButton button = searchButton(rowIndex, columnIndex, aValue.toString());
			if(button == null) return; button.doClick();
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	};
	protected TableModelListener listener = new TableModelListener() {
		@Override public void tableChanged(TableModelEvent e) {
			if(e.getType() == TableModelEvent.UPDATE) return;
			int shift = e.getLastRow() - e.getFirstRow() + 1;
			shift *= e.getType() == TableModelEvent.DELETE ? -1 : 1;
			
			if(e.getType() == TableModelEvent.DELETE) { for(int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
				for(ButtonTablePanel buttonPanel : getTablePanels(i, -1)) removeCell(buttonPanel);
			} }
			for(int i = e.getLastRow() + 1; i <= maxRows; i++) { for(ButtonTablePanel buttonPanel : getTablePanels(i, -1)) {
				if(buttonPanel == null) continue;
				changeCellLocation(buttonPanel, buttonPanel.getRow() + shift, buttonPanel.getColumn());
			} }
		}
	};
	
	protected List<ButtonTablePanel> buttonPanels = new ArrayList<>();
	protected String currentButton;
	private final ActionListener stopEditing = e -> editor.stopCellEditing();

	protected int maxRows = 0;
	protected int maxCols = 0;
	
	public TableCellRenderer getRenderer() { return renderer; }
	public TableCellEditor getEditor() { return editor; }
	public TableModel getModel() { return model; }
	public TableModelListener getListener() { return listener; }
	
	public int getMaxRows() { return maxRows; }
	public int getMaxCols() { return maxCols; }
	
	public ButtonTablePanel addCell(int row, int col) { return getTablePanelOrCreate(row, col); }
	public ButtonTablePanel removeCell(int row, int col) {
		ButtonTablePanel removed = getTablePanel(row, col);
		removeCell(removed);
		return removed;
	}
	public void removeCell(ButtonTablePanel buttonPanel) {
		buttonPanels.remove(buttonPanel);
		readButtonPanels();
	}
	public void changeCellLocation(ButtonTablePanel buttonPanel, int row, int col) {
		buttonPanel.setRow(row);
		buttonPanel.setColumn(col);
	}
	
	public void addButton(JButton button, int row, int col) {
		getTablePanelOrCreate(row, col).addButton(button);
	}
	public void removeButton(JButton button, int row, int col) {
		ButtonTablePanel buttonPanel = getTablePanel(row, col);
		if(buttonPanel == null) throw new IllegalArgumentException();
		buttonPanel.removeButton(button);
	}
	public void removeButton(JButton button) {
		for(ButtonTablePanel buttonPanel : buttonPanels) {
			if(Arrays.asList(buttonPanel.getButtons()).contains(button)) continue;
			buttonPanel.removeButton(button);
			return;
		} throw new IllegalArgumentException();
	}
	
	public ButtonTablePanel getTablePanel(int row, int col) {
		for(ButtonTablePanel buttonPanel : buttonPanels) {
			if(buttonPanel.getRow() != row || buttonPanel.getColumn() != col) continue;
			return buttonPanel;
		} return null;
	}
	protected ButtonTablePanel[] getTablePanels(int row, int col) {
		List<ButtonTablePanel> result = new ArrayList<>();
		for(int i = (row < 0 ? 0 : row); i <= (row < 0 ? maxRows : row); i++) {
			for(int j = (col < 0 ? 0 : col); j <= (col < 0 ? maxCols : col); j++) {
				ButtonTablePanel buttonPanel = getTablePanel(i, j);
				if(buttonPanel != null) result.add(buttonPanel);
			}
		} return result.toArray(new ButtonTablePanel[0]);
	}
	protected ButtonTablePanel getTablePanelOrCreate(int row, int col) {
		ButtonTablePanel buttonPanel = getTablePanel(row, col);
		if(buttonPanel != null) return buttonPanel;
		buttonPanel = new ButtonTablePanel(row, col);
		buttonPanels.add(buttonPanel);
		readButtonPanels();
		return buttonPanel;
	}
	protected JButton searchButton(int row, int col, String command) {
		ButtonTablePanel buttonPanel = getTablePanel(row, col);
		if(buttonPanel == null) return null;
		return buttonPanel.getButton(command);
	}
	
	private void readButtonPanels() {
		maxRows = 0; maxCols = 0;
		for(ButtonTablePanel buttonPanel : buttonPanels) {
			maxRows = Math.max(buttonPanel.getRow(), maxRows);
			maxCols = Math.max(buttonPanel.getColumn(), maxCols);
		}
	}
	
	/*
	 * Classes
	 */
	public class ButtonTablePanel extends Panel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4991021280055436458L;
		protected int row;
		protected int col;
		protected final Map<String, JButton> buttons = new HashMap<>();
		
		protected ButtonTablePanel(int row, int col) {
			this.row = row; this.col = col;
			setLayout(new FlowLayout(FlowLayout.LEFT));
		}
		
		public void addButton(JButton button) {
			String randomCommand = new RandomString().nextString();
			button.setActionCommand(randomCommand);
			button.addActionListener(e -> currentButton = e.getActionCommand());
			buttons.put(randomCommand, button);
			add(button);
			removeActionListener(stopEditing);
			addActionListener(stopEditing);
		}
		public void removeButton(JButton button) {
			buttons.remove(getCommand(button));
			remove(button);
			removeActionListener(stopEditing);
			addActionListener(stopEditing);
		}
		
		public void setButtonActionCommand(JButton button, String command) {
			if(!buttons.values().contains(button)) throw new IllegalArgumentException("JButton is not added!");
			if(command == null) throw new NullPointerException();
			if(buttons.keySet().contains(command) && !buttons.get(command).equals(button))
				throw new IllegalArgumentException("Command already exists.");
			buttons.remove(button.getActionCommand());
			buttons.put(command, button);
			button.setActionCommand(command);
		}
		
		protected void addActionListener(ActionListener l) { for(JButton button : buttons.values()) button.addActionListener(l); }
		protected void removeActionListener(ActionListener l) { for(JButton button : buttons.values()) button.removeActionListener(l); }
		
		protected void setRow(int row) {
			int oldRow = this.row; this.row = row;
			firePropertyChange("reallocateRow", oldRow, row);
		}
		protected void setColumn(int col) {
			int oldCol = this.col; this.col = col;
			firePropertyChange("reallocateCol", oldCol, col);
		}
		public int getRow() { return row; }
		public int getColumn() { return col; }
		
		public JButton getButton(String command) { return buttons.get(command); }
		public JButton[] getButtons() { return buttons.values().toArray(new JButton[0]); }
		public String getCommand(JButton button) {
			for(Entry<String, JButton> pair : buttons.entrySet()) {
				if(pair.getValue().equals(button)) return pair.getKey();
			} return null;
		}

		@Override
		public boolean equals(final Object other) {
			if (this == other)
				return true;
			if (other == null)
				return false;
			if (!getClass().equals(other.getClass()))
				return false;
			ButtonTablePanel castOther = ButtonTablePanel.class.cast(other);
			return Objects.equals(row, castOther.row) && Objects.equals(col, castOther.col)
					&& Objects.equals(buttons, castOther.buttons);
		}
		@Override
		public int hashCode() {
			return Objects.hash(row, col, buttons);
		}
		@Override
		public String toString() {
			return new ToStringBuilder(this).append("row", row).append("col", col).append("buttons", buttons)
					.toString();
		}
	}
}
