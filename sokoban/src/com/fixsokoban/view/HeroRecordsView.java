package com.fixsokoban.view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.fixsokoban.config.Config;
import com.fixsokoban.dao.BaseDao;
import com.fixsokoban.po.HeroRecordsTable;
import com.fixsokoban.po.Record;

/**
 * 风云榜面板
 * 
 * @author Shinelon
 *
 */
public class HeroRecordsView extends JFrame implements ActionListener {

	private JTable t = null;
	private JScrollPane s = null;
	private HeroRecordsTable recordsTable = null;

	private JLabel level = new JLabel("难度:");
	private JLabel searchName = new JLabel("姓名:");
	private JButton searchBtn = new JButton("搜索");
	private JTextField searchFiled = new JTextField();
	private JComboBox<String> jBox = new JComboBox<String>();
	public static boolean flag = true;

	private String strLevel;

	public void init() {

		/**
		 * 从数据库加载表格数据
		 */
		recordsTable = new HeroRecordsTable();
		List<Record> records = BaseDao.findAll();
		recordsTable.setP(encapsualteRecord(records));

		t = new JTable(recordsTable);
		s = new JScrollPane(t);

		/**
		 * 添加下拉框选项
		 */
		jBox.addItem("全部");
		jBox.addItem("简单");
		jBox.addItem("中等难度");
		jBox.addItem("困难");

		/**
		 * 设置table中的内容居中
		 */
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		t.setDefaultRenderer(Object.class, r);
		// t.setPreferredScrollableViewportSize(new Dimension(400, 30));

		/**
		 * 组件定位
		 */
		s = new JScrollPane(t);
		level.setBounds(5, 10, 50, 30);
		jBox.setBounds(50, 10, 110, 30);
		searchName.setBounds(170, 10, 50, 30);
		searchFiled.setBounds(205, 10, 120, 30);
		searchBtn.setBounds(335, 10, 155, 30);
		s.setBounds(0, 50, 500, 220);

		/**
		 * 添加监听器
		 */
		searchBtn.addActionListener(this);

	}

	public HeroRecordsView() {

		init();

		this.setLayout(null);

		this.add(s);
		this.add(level);
		this.add(jBox);
		this.add(searchBtn);
		this.add(searchFiled);
		this.add(searchName);

		this.setSize(510, 300);
		this.setTitle("风云榜");
		this.setResizable(false);
		this.setVisible(true);
		this.setLocation(Config.START_WINDOW_X, Config.START_WINDOW_Y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 激活窗口事件
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public HeroRecordsTable getRecordsTable() {
		return recordsTable;
	}

	public void setRecordsTable(HeroRecordsTable recordsTable) {
		this.recordsTable = recordsTable;
	}

	public JTable getT() {
		return t;
	}

	public void setT(JTable t) {
		this.t = t;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == searchBtn) {

			List<Record> records = null;
			String levelCondition = jBox.getSelectedItem().toString();
			String searchName = searchFiled.getText();

			if (searchName.length() == 0 && levelCondition.equals("全部")) {
				records = BaseDao.findAll();
			} else if (searchName.length() == 0 && !levelCondition.equals("全部")) {
				records = BaseDao.findByLevel(levelCondition);
			} else if (searchName.length() != 0 && levelCondition.equals("全部")) {
				records = BaseDao.findByName(searchName);
			} else {
				records = BaseDao
						.findByNameAndLevel(searchName, levelCondition);
			}

			recordsTable.setP(encapsualteRecord(records));

			s.repaint();

		}

	}

	public static Object[][] encapsualteRecord(List<Record> list) {

		Object[][] objs = new Object[1000][3];

		for (int x = 0; x < objs.length && x < list.size(); x++) {
			Record rec = list.get(x);
			objs[x][0] = rec.getName();
			objs[x][1] = rec.getLevel();
			objs[x][2] = rec.getMoves();
		}

		return objs;

	}

	// 重写这个方法
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			return; // 直接返回，阻止默认动作，阻止窗口关闭
		}
		super.processWindowEvent(e); // 该语句会执行窗口事件的默认动作(如：隐藏)
	}

}
