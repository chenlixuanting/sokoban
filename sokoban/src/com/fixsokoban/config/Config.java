package com.fixsokoban.config;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ResourceBundle;

/**
 * 配置文件
 * 
 * @author Shinelon
 *
 */
public class Config {

	// 开始窗口出现的x,y坐标
	public static int START_WINDOW_X;
	public static int START_WINDOW_Y;

	// 开始窗口的宽,高
	public static int START_WINDOW_H;
	public static int START_WINDOW_W;

	// 游戏窗口出现的x,y坐标
	public static int PLAY_WINDOW_X;
	public static int PLAY_WINDOW_Y;

	// 游戏窗口的宽,高
	public static int PLAY_WINDOW_H;
	public static int PLAY_WINDOW_W;

	// 游戏界面的宽,高
	public static int PLAY_BOARD_H;
	public static int PLAY_BOARD_W;

	// 图片的统一长,宽
	public static int PIC_H;
	public static int PIC_W;

	// 图片资源
	public static Image[] imgRes = new Image[10];

	// 关卡难度
	public static int EASY_LEVEL;
	public static int MEDIUM_LEVEL;
	public static int HARD_LEVEL;

	// 音乐资源路径
	public static String[] musicFilePaths = new String[5];

	/**
	 * 初始化图片资源
	 * 
	 */

	/**
	 * outerRing = 0 wall = 1 way =2 box =3 target = 4 up = 5
	 */

	static {
		imgRes[0] = Toolkit.getDefaultToolkit().getImage("res/outerRing.png");
		imgRes[1] = Toolkit.getDefaultToolkit().getImage("res/wall.png");
		imgRes[2] = Toolkit.getDefaultToolkit().getImage("res/way.png");
		imgRes[3] = Toolkit.getDefaultToolkit().getImage("res/box.png");
		imgRes[4] = Toolkit.getDefaultToolkit().getImage("res/target.png");
		imgRes[5] = Toolkit.getDefaultToolkit().getImage("res/up.png");
		imgRes[6] = Toolkit.getDefaultToolkit().getImage("res/down.png");
		imgRes[7] = Toolkit.getDefaultToolkit().getImage("res/left.png");
		imgRes[8] = Toolkit.getDefaultToolkit().getImage("res/right.png");
		imgRes[9] = Toolkit.getDefaultToolkit().getImage("res/ok.png");
	}

	/**
	 * 音乐资源引用
	 */
	static {

		musicFilePaths[0] = new String("music/Desert.wav");
		musicFilePaths[1] = new String("music/Forest.wav");
		musicFilePaths[2] = new String("music/MyGame.wav");
		musicFilePaths[3] = new String("music/TheDawn.wav");

	}

	/**
	 * 初始化配置资源
	 */
	static {

		// 开始窗口出现的x,y坐标
		START_WINDOW_X = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("StartWindow_X"));
		START_WINDOW_Y = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("StartWindow_Y"));

		// 开始窗口的宽,高
		START_WINDOW_W = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("StartWindow_W"));
		START_WINDOW_H = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("StartWindow_H"));

		// 游戏窗口出现的x,y坐标
		PLAY_WINDOW_X = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("PlayWindow_X"));
		PLAY_WINDOW_Y = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("PlayWindow_Y"));

		// 游戏窗口的宽,高
		PLAY_WINDOW_H = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("PlayWindow_H"));
		PLAY_WINDOW_W = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("PlayWindow_W"));

		// 游戏界面的宽,高
		PLAY_BOARD_H = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("PlayBorder_H"));
		PLAY_BOARD_W = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("PlayBorder_W"));

		// 图片的统一长,宽
		PIC_H = Integer.valueOf(ResourceBundle.getBundle("config").getString(
				"Pic_H"));
		PIC_W = Integer.valueOf(ResourceBundle.getBundle("config").getString(
				"Pic_W"));

		// 关卡难度
		EASY_LEVEL = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("EasyLevel"));
		MEDIUM_LEVEL = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("MediumLevel"));
		HARD_LEVEL = Integer.valueOf(ResourceBundle.getBundle("config")
				.getString("HardLevel"));

	}

}
