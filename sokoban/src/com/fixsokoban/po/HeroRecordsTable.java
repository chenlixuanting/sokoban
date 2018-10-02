package com.fixsokoban.po;

import javax.swing.table.AbstractTableModel;

/**
 * 风云榜表格数据实体
 * 
 * @author Shinelon
 *
 */
public class HeroRecordsTable extends AbstractTableModel {

	// 表格数据实体
	private Object[][] p = null;

	/**
	 * { { "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) }, {
	 * "陈宣锦", "简单", new Integer(123) }, { "陈宣锦", "简单", new Integer(123) } };
	 */

	// 列字段
	private String[] n = { "姓名", "难度", "移动步数" };

	/**
	 * ;
	 * 
	 * @return
	 */

	public Object[][] getP() {
		return p;
	}

	public void setP(Object[][] p) {
		this.p = p;
	}

	public String[] getN() {
		return n;
	}

	public void setN(String[] n) {
		this.n = n;
	}

	@Override
	public int getRowCount() {
		return p.length;
	}

	@Override
	public int getColumnCount() {
		return n.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return p[row][col];
	}

	@Override
	public String getColumnName(int column) {
		return n[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		p[rowIndex][columnIndex] = value;
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
