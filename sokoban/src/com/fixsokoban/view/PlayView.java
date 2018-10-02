package com.fixsokoban.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.fixsokoban.config.Config;
import com.fixsokoban.controller.CoreEngineroller;
import com.fixsokoban.dao.BaseDao;
import com.fixsokoban.po.Record;
import com.fixsokoban.utils.Musician;

/**
 * 游戏界面
 * 
 * @author Shinelon
 *
 */
public class PlayView extends JFrame implements KeyListener, ActionListener {

	// 游戏面板引用
	private PlayBoard playBorder = new PlayBoard();

	// 游戏引擎引用
	private CoreEngineroller coreEngineroller = new CoreEngineroller();

	// 菜单栏
	private JMenuBar jMenuBar = new JMenuBar();

	// 当前关卡 默认为简单关卡
	public static int level = Config.EASY_LEVEL;

	// 记录移动的脚步数(默认为0)
	public static int moves = 0;

	// 当前玩家名称
	public static String palyerName;

	// 显示当前游戏信息
	// private ShowInfoView showInfoView = new ShowInfoView();

	// 暂停按钮
	private boolean pasueFlag = false;

	// 当前游戏的音乐现场引用
	private Musician musicThread = null;

	// 菜单栏选项
	private JMenu menu1 = new JMenu("游戏");
	private JMenuItem item1 = new JMenuItem("开始");
	private JMenuItem item2 = new JMenuItem("暂停");
	private JMenuItem item3 = new JMenuItem("上一步");
	private JMenuItem item4 = new JMenuItem("重新开始");

	private JMenu menu2 = new JMenu("音乐");
	private JMenuItem item5 = new JMenuItem("沙漠");
	private JMenuItem item6 = new JMenuItem("鸟语花香");
	private JMenuItem item7 = new JMenuItem("我的游戏");
	private JMenuItem item8 = new JMenuItem("亡灵序曲");

	private JMenu menu3 = new JMenu("关卡");
	private JMenuItem item9 = new JMenuItem("简单");
	private JMenuItem item10 = new JMenuItem("中等");
	private JMenuItem item11 = new JMenuItem("困难");

	private JMenu menu4 = new JMenu("帮助");
	private JMenuItem item14 = new JMenuItem("关于");
	private JMenuItem item15 = new JMenuItem("风云榜");

	/**
	 * 初始化菜单栏
	 */
	public void initMenuBar() {

		menu1.add(item1);
		item1.setEnabled(false);
		menu1.addSeparator();
		menu1.add(item2);
		menu1.addSeparator();
		menu1.add(item3);
		menu1.addSeparator();
		menu1.add(item4);
		jMenuBar.add(menu1);

		menu2.add(item5);
		item5.setEnabled(false);
		menu2.addSeparator();
		menu2.add(item6);
		menu2.addSeparator();
		menu2.add(item7);
		menu2.addSeparator();
		menu2.add(item8);
		jMenuBar.add(menu2);

		menu3.add(item9);
		item9.setEnabled(false);
		menu3.addSeparator();
		menu3.add(item10);
		menu3.addSeparator();
		menu3.add(item11);
		jMenuBar.add(menu3);

		menu4.add(item14);
		menu4.addSeparator();
		menu4.add(item15);
		jMenuBar.add(menu4);

	}

	/**
	 * 给菜单选项添加监听
	 * 
	 * @return
	 */
	public void addListenerForMenuItems() {
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
		item5.addActionListener(this);
		item6.addActionListener(this);
		item7.addActionListener(this);
		item8.addActionListener(this);
		item9.addActionListener(this);
		item10.addActionListener(this);
		item11.addActionListener(this);
		item14.addActionListener(this);
		item15.addActionListener(this);
	}

	public PlayView() {

		// 初始化工具条
		initMenuBar();

		// 给菜单子按钮添加监听器
		addListenerForMenuItems();

		// 加载地图,默认加载第1关卡
		coreEngineroller.loadMap(level);

		// 播放音乐 默认为沙漠
		musicThread = coreEngineroller.playMusic(Config.musicFilePaths[0]);

		// 为面板设置游戏引擎
		playBorder.setCoreEngineroller(coreEngineroller);

		// 添加工具条
		this.setJMenuBar(jMenuBar);

		// 给游戏画板添加键盘监听器
		this.addKeyListener(this);

		// 设置为绝对布局
		this.setLayout(null);

		// 添加playBorder面板
		this.add(playBorder);

		// 添加ShowInfoView
		// this.add(showInfoView);

		// 设置窗口可视化
		this.setVisible(true);

		// 设置不可改变窗口大小
		this.setResizable(false);

		// 设置标题
		this.setTitle("推箱子");

		// 设置游戏窗口的大小
		this.setSize(Config.PLAY_WINDOW_W, Config.PLAY_WINDOW_H);

		// 设置游戏窗口出现的位置
		this.setLocation(Config.PLAY_WINDOW_X, Config.PLAY_WINDOW_Y);

		// 正常关闭窗口
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new PlayView();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * 键盘监听事件被触发
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if (!pasueFlag) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				if (coreEngineroller.moveUp())
					moves++;
				break;
			case KeyEvent.VK_S:
				if (coreEngineroller.moveDown())
					moves++;
				break;
			case KeyEvent.VK_A:
				if (coreEngineroller.moveLeft())
					moves++;
				break;
			case KeyEvent.VK_D:
				if (coreEngineroller.moveRight())
					moves++;
				break;
			}

			// 重绘
			playBorder.repaint();

			// 进行胜利判断
			if (coreEngineroller.isSuccess()) {

				// 进行游戏记录的存储
				BaseDao.insertOneRecord(new Record(PlayView.palyerName,
						PlayBoard.judgeLevel(PlayView.level), PlayView.moves));

				if (JOptionPane.showConfirmDialog(this,
						"你已经顺利通过本关卡!是否继续进行下一关?", "提示",
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {

					// 循环选关
					if (++level > Config.HARD_LEVEL)
						level = Config.EASY_LEVEL;

					// 更改难度项
					if (level == Config.EASY_LEVEL) {
						// 恢复中等关卡切换按钮
						fixItem10();
						// 恢复困难关卡切换按钮
						fixItem11();
						item9.setEnabled(false);

					} else if (level == Config.MEDIUM_LEVEL) {
						// 恢复简单关卡切换按钮
						fixItem9();
						// 恢复困难关卡切换按钮
						fixItem11();
						item10.setEnabled(false);
					} else {
						// 恢复简单关卡切换按钮
						fixItem9();
						// 恢复中等关卡切换按钮
						fixItem10();
						item11.setEnabled(false);
					}

					fixItem5();
					fixItem6();
					fixItem7();
					fixItem8();

					// 更改音乐项
					if (level == Config.EASY_LEVEL) {
						item5.setEnabled(false);
					} else if (level == Config.MEDIUM_LEVEL) {
						item6.setEnabled(false);
					} else {
						item7.setEnabled(false);
					}

					// 开启下关
					reStart(level);
				}

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == item9) {

			fixItem6();
			fixItem7();
			fixItem8();

			// 恢复中等关卡切换按钮
			fixItem10();

			// 恢复困难关卡切换按钮
			fixItem11();

			// 修改当前关卡
			level = Config.EASY_LEVEL;

			// 重新选择简单难度
			reStart(Config.EASY_LEVEL);

			item9.setEnabled(false);

			item5.setEnabled(false);

		} else if (e.getSource() == item10) {

			fixItem5();
			fixItem7();
			fixItem8();

			// 恢复简单关卡切换按钮
			fixItem9();

			// 恢复困难关卡切换按钮
			fixItem11();

			// 修改当前关卡
			level = Config.MEDIUM_LEVEL;

			// 重新选择中等难度
			reStart(Config.MEDIUM_LEVEL);

			item10.setEnabled(false);

			item6.setEnabled(false);

		} else if (e.getSource() == item11) {

			fixItem5();
			fixItem6();
			fixItem8();

			// 恢复简单关卡切换按钮
			fixItem9();
			// 恢复中等关卡切换按钮
			fixItem10();

			// 修改当前关卡
			level = Config.HARD_LEVEL;

			// 重新选择困难难度
			reStart(Config.HARD_LEVEL);

			item11.setEnabled(false);

			item7.setEnabled(false);
			// 重新开始
		} else if (e.getSource() == item4) {

			reStart(level);

			// 切换沙漠音乐
		} else if (e.getSource() == item5) {

			fixItem5();
			fixItem6();
			fixItem7();
			fixItem8();

			// 停止当前音乐
			coreEngineroller.stopMusic(musicThread);
			// 播放新的音乐
			musicThread = coreEngineroller.playMusic(Config.musicFilePaths[0]);

			item5.setEnabled(false);
			// 切换森林音乐
		} else if (e.getSource() == item6) {

			fixItem5();
			fixItem7();
			fixItem8();

			// 停止当前音乐
			coreEngineroller.stopMusic(musicThread);
			// 播放新的音乐
			musicThread = coreEngineroller.playMusic(Config.musicFilePaths[1]);

			item6.setEnabled(false);
			// 切换我的游戏音乐
		} else if (e.getSource() == item7) {

			fixItem5();
			fixItem6();
			fixItem8();

			// 停止当前音乐
			coreEngineroller.stopMusic(musicThread);
			// 播放新的音乐
			musicThread = coreEngineroller.playMusic(Config.musicFilePaths[2]);

			item7.setEnabled(false);
			// 切换亡灵序曲音乐
		} else if (e.getSource() == item8) {

			fixItem5();
			fixItem6();
			fixItem7();

			// 停止当前音乐
			coreEngineroller.stopMusic(musicThread);
			// 播放新的音乐
			musicThread = coreEngineroller.playMusic(Config.musicFilePaths[3]);

			item8.setEnabled(false);

			// 点击关于按钮
		} else if (e.getSource() == item14) {
			JOptionPane.showMessageDialog(this, "游戏说明:\n" + "\t w:上\n"
					+ "\t s:下\n" + "\t a:左\n" + "\t d:右\n");

			// 按下开始按钮
		} else if (e.getSource() == item1) {
			pasueFlag = false;

			item2.setEnabled(true);

			// 开启上一步按钮
			item3.setEnabled(true);
			// 开启重新开始按钮
			item4.setEnabled(true);

			// 开启除当前关卡外所有的关卡切换按钮
			if (level == Config.EASY_LEVEL) {
				fixItem10();
				fixItem11();
			} else if (level == Config.MEDIUM_LEVEL) {
				fixItem9();
				fixItem11();
			} else if (level == Config.HARD_LEVEL) {
				fixItem9();
				fixItem10();
			}

			item1.setEnabled(false);

			// 按下暂停按钮
		} else if (e.getSource() == item2) {
			pasueFlag = true;

			item2.setEnabled(false);

			// 关闭上一步按钮
			item3.setEnabled(false);
			// 关闭重新开始按钮
			item4.setEnabled(false);

			// 关闭关卡切换的所有按钮
			item9.setEnabled(false);
			item10.setEnabled(false);
			item11.setEnabled(false);

			item1.setEnabled(true);

			JOptionPane.showMessageDialog(this, "游戏暂停!");

			// 上一步按钮被按下
		} else if (e.getSource() == item3) {

			if (coreEngineroller.backSpace())
				moves--;

			playBorder.repaint();
		} else if (e.getSource() == item15) {
			new HeroRecordsView();
		}

	}

	/**
	 * 重新开始
	 * 
	 * @return
	 */
	public boolean reStart(int level) {

		// 停止当前音乐
		coreEngineroller.stopMusic(musicThread);

		// 播放新的音乐
		musicThread = coreEngineroller.playMusic(Config.musicFilePaths[level]);

		// 加载地图,默认加载第1关卡
		coreEngineroller.loadMap(level);

		// 重置移动步数
		moves = 0;

		// 重绘
		playBorder.repaint();

		return true;
	}

	/**
	 * 恢复简单关卡切换按钮
	 * 
	 * @return
	 */
	public boolean fixItem9() {

		if (!item9.isEnabled()) {
			item9.setEnabled(true);
		}

		return true;

	}

	/**
	 * 恢复中等关卡切换按钮
	 * 
	 * @return
	 */
	public boolean fixItem10() {

		if (!item10.isEnabled()) {
			item10.setEnabled(true);
		}
		return true;
	}

	/**
	 * 恢复困难关卡切换按钮
	 * 
	 * @return
	 */
	public boolean fixItem11() {

		if (!item11.isEnabled()) {
			item11.setEnabled(true);
		}
		return true;
	}

	/**
	 * 恢复沙漠音乐
	 * 
	 * @return
	 */
	public boolean fixItem5() {
		if (!item5.isEnabled()) {
			item5.setEnabled(true);
		}
		return true;
	}

	/**
	 * 恢复森林音乐
	 */
	public boolean fixItem6() {
		if (!item6.isEnabled()) {
			item6.setEnabled(true);
		}
		return true;
	}

	/**
	 * 恢复我的游戏音乐
	 */
	public boolean fixItem7() {
		if (!item7.isEnabled()) {
			item7.setEnabled(true);
		}
		return true;
	}

	/**
	 * 恢复亡灵序曲音乐
	 */
	public boolean fixItem8() {
		if (!item8.isEnabled()) {
			item8.setEnabled(true);
		}
		return true;
	}
}
