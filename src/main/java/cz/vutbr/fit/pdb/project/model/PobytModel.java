package cz.vutbr.fit.pdb.project.model;

import javax.swing.table.AbstractTableModel;

public class PobytModel extends AbstractTableModel {
	
	String[] columnNames = {"SPZ","Vjezd","Výjezd"};
    private Object[][] data = null;

    public PobytModel(Object[][] data) {
        this.data = data;
    }
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public String (int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }
}
