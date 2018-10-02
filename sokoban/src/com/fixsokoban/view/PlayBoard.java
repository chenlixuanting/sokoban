package com.fixsokoban.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fixsokoban.config.Config;
import com.fixsokoban.controller.CoreEngineroller;
import com.fixsokoban.po.MapData;

/**
 * 内部类实现游戏区域
 * 
 * @author Shinelon
 *
 */
public class PlayBoard extends JPanel {

	// 依赖的游戏引擎
	private CoreEngineroller coreEngineroller;

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		// 创建地图
		coreEngineroller.createMap(g, this);

		g.setFont(new Font("仿宋", Font.BOLD, 20));
		g.setColor(Color.BLUE);
		g.drawString("玩家:" + PlayView.palyerName, 550, 30);
		g.setColor(Color.RED);
		g.drawString("难度:" + judgeLevel(PlayView.level), 550, 60);
		g.drawString("移动步数:" + PlayView.moves, 550, 90);

	}

	public CoreEngineroller getCoreEngineroller() {
		return coreEngineroller;
	}

	public void setCoreEngineroller(CoreEngineroller coreEngineroller) {
		this.coreEngineroller = coreEngineroller;
	}

	public PlayBoard() {

		super();

		// 设置游戏画板大小
		this.setSize(Config.PLAY_BOARD_W, Config.PLAY_BOARD_H);

		// 设置邮箱画板出现的位置
		this.setBounds(0, 0, Config.PLAY_BOARD_W, Config.PLAY_BOARD_H);

		// 设置游戏面板背景颜色
		this.setBackground(Color.WHITE);
	}

	public PlayBoard(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PlayBoard(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PlayBoard(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public static String judgeLevel(int level) {
		if (PlayView.level == Config.EASY_LEVEL)
			return "简单";
		else if (PlayView.level == Config.MEDIUM_LEVEL) {
			return "中等难度";
		} else {
			return "困难";
		}
	}
}
