package io.github.NadhifRadityo.Objects.AWTComponent;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Table extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3976478478143987893L;
	protected final List<CustomCellRenderer> cellsRenderer = new ArrayList<>();
	protected CustomCellRenderer defaultCellRenderer;
	protected TableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1841264004046517379L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component comp = null;
			if(value != null) {
				CustomCellRenderer renderer = searchRenderer(row, column, value.getClass());
				if(renderer != null) comp = renderer.getTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			} if(comp == null) comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			return comp;
		}
	};
	
	protected final List<TableModel> tableModels = new ArrayList<>();
	protected TableModel defaultTableModel;
	protected CustomTableModel tableModel = new CustomTableModel();
	protected class CustomTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 836907730698990693L;
		@Override public boolean isCellEditable(int rowIndex, int columnIndex) {
			for(CellEditable cellEditable : cellsEditable) {
				if(cellEditable.getRow() != rowIndex || cellEditable.getColumn() != columnIndex) continue;
				return cellEditable.isEditable();
			} return defaultTableModel.isCellEditable(rowIndex, columnIndex);
		}
		
		@Override public int getRowCount() { return defaultTableModel.getRowCount(); }
		@Override public int getColumnCount() { return defaultTableModel.getColumnCount(); }
		@Override public Object getValueAt(int rowIndex, int columnIndex) { return defaultTableModel.getValueAt(rowIndex, columnIndex); }
		@Override public String getColumnName(int columnIndex) { return defaultTableModel.getColumnName(columnIndex); }
		@Override public Class<?> getColumnClass(int columnIndex) { return defaultTableModel.getColumnClass(columnIndex); }
		
		@Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			defaultTableModel.setValueAt(aValue, rowIndex, columnIndex);
			for(TableModel model : tableModels) try { model.setValueAt(aValue, rowIndex, columnIndex); } catch(Exception e) {
				if(!(e instanceof UnsupportedOperationException)) throw e;
			}
		}
		@Override public void addTableModelListener(TableModelListener l) {
			defaultTableModel.addTableModelListener(l); super.addTableModelListener(l);
			for(TableModel model : tableModels) try { model.addTableModelListener(l); } catch(Exception e) {
				if(!(e instanceof UnsupportedOperationException)) throw e;
			}
		}
		@Override public void removeTableModelListener(TableModelListener l) {
			defaultTableModel.removeTableModelListener(l); super.addTableModelListener(l);
			for(TableModel model : tableModels) try { model.removeTableModelListener(l); } catch(Exception e) {
				if(!(e instanceof UnsupportedOperationException)) throw e;
			}
		}
	}
	
	protected final List<CellEditable> cellsEditable = new ArrayList<>();
	protected final List<CellMouseListener> cellClickListenerHandler = new ArrayList<>();

	public Table() { super(); onConstruct(); }
	public Table(TableModel tableModel) { super(tableModel); onConstruct(); }
	public Table(int numRows, int numColumns) { super(numRows, numColumns); onConstruct(); }
	public Table(Object[][] rowData, Object[] columnNames) { super(rowData, columnNames); onConstruct(); }
	public Table(TableModel tableModel, TableColumnModel tableColumnModel) { super(tableModel, tableColumnModel); onConstruct(); }
	@SuppressWarnings("rawtypes") public Table(Vector<? extends Vector> rowData, Vector<?> columnNames) { super(rowData, columnNames); onConstruct(); }
	public Table(TableModel tableModel, TableColumnModel tableColumnModel, ListSelectionModel listSelectionModel) { super(tableModel, tableColumnModel, listSelectionModel); onConstruct(); }
	
	private void onConstruct() {
		Font defaultFont = new Font("Segoe UI", Font.PLAIN, 12);
		Color defaultFontColor = new Color(51, 51, 51);
		Color defaultColor = new Color(62, 226, 141);
		
		setFont(defaultFont);
        setForeground(defaultFontColor);
		setSelectionBackground(defaultColor);
		setGridColor(Color.white);
		setRowHeight(20);
		setShowHorizontalLines(false);
		setShowVerticalLines(false);
		super.setDefaultRenderer(Object.class, cellRenderer);
		super.setModel(tableModel);

		addMouseListener(cellClickListener);
	}
	
	@Override public void setDefaultRenderer(Class<?> columnClass, TableCellRenderer renderer) {
		defaultCellRenderer = new CustomCellRenderer(Integer.MAX_VALUE, Integer.MAX_VALUE, columnClass, renderer);
	}
	public void setCellRenderer(int row, int col, Class<?> columnClass, TableCellRenderer renderer) {
		CustomCellRenderer cellRenderer = new CustomCellRenderer(row, col, columnClass, renderer);
		cellsRenderer.add(cellRenderer);
		tableModel.fireTableCellUpdated(row, col);
	}
	public void setCellRenderer(int row, int col, TableCellRenderer renderer) {
		setCellRenderer(row, col, Object.class, renderer);
	}
	
	public void resetDefaultRenderer() { defaultCellRenderer = null; }
	private CustomCellRenderer searchRenderer(int row, int col, Class<?> columnClass) {
		for(CustomCellRenderer renderer : cellsRenderer) {
			if(renderer.getColumnClass().isAssignableFrom(columnClass) && 
				renderer.getRow() == row && renderer.getColumn() == col)
				return renderer;
		}
		return defaultCellRenderer;
	}

	@Override public TableModel getModel() { return defaultTableModel; }
	@Override public void setModel(TableModel dataModel) {
		if(dataModel == null) throw new IllegalArgumentException("Cannot set a null TableModel");
		if(dataModel == defaultTableModel) return;
		if(tableModel == null) tableModel = new CustomTableModel();
		
		if(defaultTableModel != null)
			for(TableModelListener l : tableModel.getTableModelListeners()) defaultTableModel.removeTableModelListener(l);
		this.defaultTableModel = dataModel;
		for(TableModelListener l : tableModel.getTableModelListeners()) defaultTableModel.addTableModelListener(l);
	}

	public void addExtendedTableModel(TableModel model) {
		for(TableModelListener l : tableModel.getTableModelListeners())
			model.addTableModelListener(l);
		tableModels.add(model);
	}
	public void removeExtendedTableModel(TableModel model) {
		tableModels.remove(model);
		for(TableModelListener l : tableModel.getTableModelListeners())
			model.removeTableModelListener(l);
	}
	
	public void setCellEditable(int row, int col, boolean editable) {
		for(CellEditable cellEditable : cellsEditable) {
			if(cellEditable.getRow() != row || cellEditable.getColumn() != col) continue;
			cellEditable.setEditable(editable);
			tableModel.fireTableCellUpdated(row, col);
			return;
		}
		cellsEditable.add(new CellEditable(row, col, editable));
		tableModel.fireTableCellUpdated(row, col);
	}
	@Override public boolean isCellEditable(int row, int col) { return tableModel.isCellEditable(row, col); }
	
	/*
	 * Classes 
	 */
	protected static class CustomCellRenderer {
		protected final int row;
		protected final int col;
		protected final Class<?> columnClass;
		protected final TableCellRenderer renderer;
		
		protected CustomCellRenderer(int row, int col, Class<?> columnClass, TableCellRenderer renderer){
			this.row = row;
			this.col = col;
			this.columnClass = columnClass;
			this.renderer = renderer;
		}
		
		public int getRow() { return row; }
		public int getColumn() { return col; }
		public Class<?> getColumnClass() { return columnClass; }
		public TableCellRenderer getTableCellRenderer() { return renderer; }
	}

	public static abstract class EmptyTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5066026657509509460L;
		public EmptyTableModel() { super(); }
		
		@Override public int getRowCount() { throw new UnsupportedOperationException(); }
		@Override public int getColumnCount() { throw new UnsupportedOperationException(); }
		@Override public Object getValueAt(int rowIndex, int columnIndex) { throw new UnsupportedOperationException(); }
		@Override public String getColumnName(int columnIndex) { throw new UnsupportedOperationException(); }
		@Override public Class<?> getColumnClass(int columnIndex) { throw new UnsupportedOperationException(); }
		@Override public boolean isCellEditable(int rowIndex, int columnIndex) { throw new UnsupportedOperationException(); }

		@Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) { throw new UnsupportedOperationException(); }
	}
	
	protected final MouseAdapter cellClickListener = new MouseAdapter() {
		@Override public void mousePressed(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mousePressed(event));
		}
		@Override public void mouseReleased(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseReleased(event));
		}
		@Override public void mouseClicked(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseClicked(event));
		}
		@Override public void mouseExited(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseExited(event));
		}
		@Override public void mouseEntered(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseEntered(event));
		}
		@Override public void mouseDragged(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseDragged(event));
		}
		@Override public void mouseMoved(MouseEvent e) {
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseMoved(event));
		}
		@Override public void mouseWheelMoved(MouseWheelEvent e) { 
			CellMouseEvent event = proccessMouseEvent(e);
			if(event != null) cellClickListenerHandler.forEach(listener -> listener.mouseWheelMoved(event));
		}
	};
	public void addCellMouseListener(CellMouseListener listener) {
		cellClickListenerHandler.add(listener);
	}
	public void removeCellMouseListener(CellMouseListener listener) {
		cellClickListenerHandler.remove(listener);
	}
	
	private CellMouseEvent proccessMouseEvent(MouseEvent e) {
		int row = rowAtPoint(e.getPoint());
		int col = columnAtPoint(e.getPoint());
		if(row < 0 || col < 0) return null;
		return new CellMouseEvent(row, col, e);
	}
	
	public static abstract class CellMouseListener {
		public void mousePressed(CellMouseEvent e) { }
		public void mouseReleased(CellMouseEvent e) { }
		public void mouseClicked(CellMouseEvent e) { }
		public void mouseExited(CellMouseEvent e) { }
		public void mouseEntered(CellMouseEvent e) { }
		public void mouseDragged(CellMouseEvent e) { }
		public void mouseMoved(CellMouseEvent e) { }
		public void mouseWheelMoved(CellMouseEvent e) { }
	}
	public static class CellMouseEvent {
		protected final int row;
		protected final int col;
		protected final MouseEvent event;
		
		protected CellMouseEvent(int row, int col, MouseEvent event) {
			this.row = row;
			this.col = col;
			this.event = event;
		}
		
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return col;
		}
		public MouseEvent getMouseEvent() {
			return event;
		}
	}
	
	public static class CellEditable {
		protected final int row;
		protected final int col;
		protected boolean editable;
		
		protected CellEditable(int row, int col, boolean editable) {
			this.row = row;
			this.col = col;
			this.editable = editable;
		}
		
		public int getRow() { return row; }
		public int getColumn() { return col; }
		public boolean isEditable() { return editable; }
		
		public void setEditable(boolean editable) { this.editable = editable; }
	}
}
