package com.fixsokoban.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fixsokoban.config.Config;
import com.fixsokoban.po.BackSpaceEntity;
import com.fixsokoban.po.Insect;
import com.fixsokoban.po.MapData;
import com.fixsokoban.utils.LoadMapDataUtils;
import com.fixsokoban.utils.Musician;

/**
 * 控制核心类
 * 
 * @author Shinelon
 *
 */
public class CoreEngineroller {

	private MapData mapData = new MapData();
	private MapData saveOldMapData = new MapData();

	private Insect insect = new Insect();
	private Insect oldInsect = new Insect();

	// 记录旧地图中Target的个数
	private int targetCount;

	// 回退栈
	private Stack<BackSpaceEntity> backStack = new Stack<BackSpaceEntity>();

	// 瓢虫坐标
	private int insect_X;
	private int insect_Y;

	// 地图二维数组数据
	private int[][] mapArray;

	// 原始地图二维数组数据
	private int[][] oldMapArray;

	/**
	 * 加载地图数据
	 * 
	 * @param level
	 * @return
	 */
	public boolean loadMap(int level) {

		// 清空栈中的所有元素
		if (!backStack.isEmpty())
			backStack.removeAllElements();

		// 加载地图,默认加载第一关卡
		LoadMapDataUtils.load(level, insect, mapData);

		// 再次载入一份当前地图作为原始数据存储
		LoadMapDataUtils.load(level, oldInsect, saveOldMapData);

		mapArray = mapData.getMapDataArray();
		oldMapArray = saveOldMapData.getMapDataArray();

		// 计算原来地图中的Target数目
		targetCount = calOldBoxTarget();

		insect_X = insect.getOneDimension();
		insect_Y = insect.getTwoDimension();

		if (mapData != null)
			return true;

		return false;
	}

	/**
	 * 计算当前有多少个OK
	 * 
	 * @return
	 */
	public int calCurrent_OK() {

		int count_OK = 0;

		for (int x = 0; x < MapData.DATA_ROW; x++) {

			for (int y = 0; y < MapData.DATA_COL; y++) {

				if (mapArray[x][y] == MapData.OK)
					count_OK++;

			}
		}

		return count_OK;

	}

	/**
	 * 旧地图中有多少个Target
	 * 
	 * @return
	 */
	public int calOldBoxTarget() {

		int countTarget = 0;

		for (int x = 0; x < MapData.DATA_ROW; x++) {

			for (int y = 0; y < MapData.DATA_COL; y++) {

				if (mapArray[x][y] == MapData.BOX)
					countTarget++;

			}
		}

		return countTarget;

	}

	/**
	 * 构建地图
	 * 
	 * @param graphics
	 * @param playBoard
	 * @return
	 */
	public boolean createMap(Graphics graphics, JPanel playBoard) {

		// 是否成功绘图标志
		boolean flag = false;

		// 获取地图数据
		int[][] mapArray = mapData.getMapDataArray();

		// 循环创建label并为其设置相应的图片
		for (int i = 0; i < MapData.DATA_ROW; i++) {

			for (int j = 0; j < MapData.DATA_COL; j++) {

				Image img = null;

				int x = i * Config.PIC_W;
				int y = j * Config.PIC_H;

				switch (mapArray[i][j]) {
				case 0: {
					img = Config.imgRes[0];
					break;
				}
				case 1: {
					img = Config.imgRes[1];
					break;
				}
				case 2: {
					img = Config.imgRes[2];
					break;
				}
				case 3: {
					img = Config.imgRes[3];
					break;
				}
				case 4: {
					img = Config.imgRes[4];
					break;
				}
				case 5: {
					img = Config.imgRes[5];
					break;
				}
				case 6: {
					img = Config.imgRes[6];
					break;
				}
				case 7: {
					img = Config.imgRes[7];
					break;
				}
				case 8: {
					img = Config.imgRes[8];
					break;
				}
				case 9: {
					img = Config.imgRes[9];
					break;
				}
				}

				// 绘图初始地图
				flag = graphics.drawImage(img, x, y, playBoard);
			}
		}

		return flag;
	}

	/**
	 * 向上移动
	 * 
	 * @return
	 */
	public boolean moveUp() {

		BackSpaceEntity backSpaceEntity = null;

		// 判断虫子现在是否在预定义位置上
		if (oldMapArray[insect_X][insect_Y] == MapData.TARGET
				|| oldMapArray[insect_X][insect_Y] == MapData.OK) {

			// 如果虫子前面是箱子或预定义位置
			if (mapArray[insect_X][insect_Y - 1] == MapData.WAY
					|| mapArray[insect_X][insect_Y - 1] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 如果虫子前面是箱子
			} else if (mapArray[insect_X][insect_Y - 1] == MapData.BOX) {

				// 如果箱子前面是地板
				if (mapArray[insect_X][insect_Y - 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成地板
					mapArray[insect_X][insect_Y - 2] = MapData.BOX;

					// 如果箱子前面是预定义位置
				} else if (mapArray[insect_X][insect_Y - 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成OK
					mapArray[insect_X][insect_Y - 2] = MapData.OK;

				} else {
					return false;
				}

				// 如果虫子前面是OK
			} else if (mapArray[insect_X][insect_Y - 1] == MapData.OK) {

				// 如果OK前面是地板
				if (mapArray[insect_X][insect_Y - 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成箱子
					mapArray[insect_X][insect_Y - 2] = MapData.BOX;

				} else if (mapArray[insect_X][insect_Y - 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成OK
					mapArray[insect_X][insect_Y - 2] = MapData.OK;

				} else {
					return false;
				}

				// 如果虫子前面是墙
			} else if (mapArray[insect_X][insect_Y - 1] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X][insect_Y - 1] = MapData.UP;

			// 恢复箱子预定义位置
			mapArray[insect_X][insect_Y] = MapData.TARGET;

			insect_Y--;

			backStack.push(backSpaceEntity);

			return true;

			// 虫子在地板上
		} else {

			// 虫子前面是地板或者预定义位置
			if (mapArray[insect_X][insect_Y - 1] == MapData.WAY
					|| mapArray[insect_X][insect_Y - 1] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 虫子前面是箱子
			} else if (mapArray[insect_X][insect_Y - 1] == MapData.BOX) {

				// 箱子前面是地板
				if (mapArray[insect_X][insect_Y - 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y - 2] = MapData.BOX;

					// 箱子前面是预定义位置
				} else if (mapArray[insect_X][insect_Y - 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y - 2] = MapData.OK;

				} else {
					return false;
				}

				// 虫子前面是OK
			} else if (mapArray[insect_X][insect_Y - 1] == MapData.OK) {

				// OK前面是地板
				if (mapArray[insect_X][insect_Y - 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y - 2] = MapData.BOX;

					// OK前面是预定义位置
				} else if (mapArray[insect_X][insect_Y - 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y - 2] = MapData.OK;

				} else {
					return false;
				}

				// 如果虫子前面碰到的是墙壁
			} else if (mapArray[insect_X][insect_Y - 1] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X][insect_Y - 1] = MapData.UP;
			mapArray[insect_X][insect_Y] = MapData.WAY;

			insect_Y--;

			backStack.push(backSpaceEntity);

			return true;
		}

	}

	/**
	 * 向下移动
	 * 
	 * @return
	 */
	public boolean moveDown() {

		BackSpaceEntity backSpaceEntity = null;

		// 判断虫子现在是否在预定义位置上
		if (oldMapArray[insect_X][insect_Y] == MapData.TARGET
				|| oldMapArray[insect_X][insect_Y] == MapData.OK) {

			// 如果虫子前面是箱子或预定义位置
			if (mapArray[insect_X][insect_Y + 1] == MapData.WAY
					|| mapArray[insect_X][insect_Y + 1] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 如果虫子前面是箱子
			} else if (mapArray[insect_X][insect_Y + 1] == MapData.BOX) {

				// 如果箱子前面是地板
				if (mapArray[insect_X][insect_Y + 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成地板
					mapArray[insect_X][insect_Y + 2] = MapData.BOX;

					// 如果箱子前面是预定义位置
				} else if (mapArray[insect_X][insect_Y + 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成OK
					mapArray[insect_X][insect_Y + 2] = MapData.OK;

				} else {
					return false;
				}
				// 如果虫子前面是OK
			} else if (mapArray[insect_X][insect_Y + 1] == MapData.OK) {

				// 如果OK前面是地板
				if (mapArray[insect_X][insect_Y + 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成箱子
					mapArray[insect_X][insect_Y + 2] = MapData.BOX;

					// 如果ＯＫ前面是预定义位置
				} else if (mapArray[insect_X][insect_Y + 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成OK
					mapArray[insect_X][insect_Y + 2] = MapData.OK;
				} else {
					return false;
				}
				// 如果虫子前面是墙
			} else if (mapArray[insect_X][insect_Y + 1] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X][insect_Y + 1] = MapData.DOWN;

			// 恢复箱子预定义位置
			mapArray[insect_X][insect_Y] = MapData.TARGET;

			insect_Y++;

			backStack.push(backSpaceEntity);

			return true;

			// 虫子在地板上
		} else {

			// 虫子前面是地板或者预定义位置
			if (mapArray[insect_X][insect_Y + 1] == MapData.WAY
					|| mapArray[insect_X][insect_Y + 1] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 虫子前面是箱子
			} else if (mapArray[insect_X][insect_Y + 1] == MapData.BOX) {

				// 箱子前面是地板
				if (mapArray[insect_X][insect_Y + 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y + 2] = MapData.BOX;

					// 箱子前面是预定义位置
				} else if (mapArray[insect_X][insect_Y + 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y + 2] = MapData.OK;
				} else {
					return false;
				}

				// 虫子前面是OK
			} else if (mapArray[insect_X][insect_Y + 1] == MapData.OK) {

				// OK前面是地板
				if (mapArray[insect_X][insect_Y + 2] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y + 2] = MapData.BOX;

					// OK前面是预定义位置
				} else if (mapArray[insect_X][insect_Y + 2] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X][insect_Y + 2] = MapData.OK;
				} else {
					return false;
				}
				// 如果碰到墙
			} else if (mapArray[insect_X][insect_Y + 1] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X][insect_Y + 1] = MapData.DOWN;
			mapArray[insect_X][insect_Y] = MapData.WAY;
			insect_Y++;
			backStack.push(backSpaceEntity);
			return true;
		}

	}

	/**
	 * 向左移动
	 * 
	 * @return
	 */
	public boolean moveLeft() {

		BackSpaceEntity backSpaceEntity = null;

		// 判断虫子现在是否在预定义位置上
		if (oldMapArray[insect_X][insect_Y] == MapData.TARGET
				|| oldMapArray[insect_X][insect_Y] == MapData.OK) {

			// 如果虫子前面是箱子或预定义位置
			if (mapArray[insect_X - 1][insect_Y] == MapData.WAY
					|| mapArray[insect_X - 1][insect_Y] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 如果虫子前面是箱子
			} else if (mapArray[insect_X - 1][insect_Y] == MapData.BOX) {

				// 如果箱子前面是地板
				if (mapArray[insect_X - 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成地板
					mapArray[insect_X - 2][insect_Y] = MapData.BOX;

					// 如果箱子前面是预定义位置
				} else if (mapArray[insect_X - 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成OK
					mapArray[insect_X - 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}
				// 如果虫子前面是OK
			} else if (mapArray[insect_X - 1][insect_Y] == MapData.OK) {

				// 如果OK前面是地板
				if (mapArray[insect_X - 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成箱子
					mapArray[insect_X - 2][insect_Y] = MapData.BOX;

					// 如果ＯＫ前面是预定义位置
				} else if (mapArray[insect_X - 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成OK
					mapArray[insect_X - 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}
				// 如果虫子前面是墙
			} else if (mapArray[insect_X - 1][insect_Y] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X - 1][insect_Y] = MapData.LEFT;

			// 恢复箱子预定义位置
			mapArray[insect_X][insect_Y] = MapData.TARGET;

			insect_X--;

			backStack.push(backSpaceEntity);
			return true;

			// 虫子在地板上
		} else {

			// 虫子前面是地板或者预定义位置
			if (mapArray[insect_X - 1][insect_Y] == MapData.WAY
					|| mapArray[insect_X - 1][insect_Y] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 虫子前面是箱子
			} else if (mapArray[insect_X - 1][insect_Y] == MapData.BOX) {

				// 箱子前面是地板
				if (mapArray[insect_X - 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X - 2][insect_Y] = MapData.BOX;
				} else if (mapArray[insect_X - 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X - 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}

				// 虫子前面是OK
			} else if (mapArray[insect_X - 1][insect_Y] == MapData.OK) {

				// OK前面是地板
				if (mapArray[insect_X - 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X - 2][insect_Y] = MapData.BOX;

					// OK前面是预定义位置
				} else if (mapArray[insect_X - 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X - 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}
				// 如果碰到墙
			} else if (mapArray[insect_X - 1][insect_Y] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X - 1][insect_Y] = MapData.LEFT;
			mapArray[insect_X][insect_Y] = MapData.WAY;
			insect_X--;

			backStack.push(backSpaceEntity);

			return true;
		}
	}

	/**
	 * 向右移动
	 * 
	 * @return
	 */
	public boolean moveRight() {

		BackSpaceEntity backSpaceEntity = null;

		// 判断虫子现在是否在预定义位置上
		if (oldMapArray[insect_X][insect_Y] == MapData.TARGET
				|| oldMapArray[insect_X][insect_Y] == MapData.OK) {

			// 如果虫子前面是箱子或预定义位置
			if (mapArray[insect_X + 1][insect_Y] == MapData.WAY
					|| mapArray[insect_X + 1][insect_Y] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 如果虫子前面是箱子
			} else if (mapArray[insect_X + 1][insect_Y] == MapData.BOX) {

				// 如果箱子前面是地板
				if (mapArray[insect_X + 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成地板
					mapArray[insect_X + 2][insect_Y] = MapData.BOX;

					// 如果箱子前面是预定义位置
				} else if (mapArray[insect_X + 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将箱子前面变成OK
					mapArray[insect_X + 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}
				// 如果虫子前面是OK
			} else if (mapArray[insect_X + 1][insect_Y] == MapData.OK) {

				// 如果OK前面是地板
				if (mapArray[insect_X + 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成箱子
					mapArray[insect_X + 2][insect_Y] = MapData.BOX;

					// 如果ＯＫ前面是预定义位置
				} else if (mapArray[insect_X + 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					// 将OK前面变成OK
					mapArray[insect_X + 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}
				// 如果虫子前面是墙
			} else if (mapArray[insect_X + 1][insect_Y] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X + 1][insect_Y] = MapData.RIGHT;

			// 恢复箱子预定义位置
			mapArray[insect_X][insect_Y] = MapData.TARGET;

			insect_X++;

			backStack.push(backSpaceEntity);
			return true;

			// 虫子在地板上
		} else {

			// 虫子前面是地板或者预定义位置
			if (mapArray[insect_X + 1][insect_Y] == MapData.WAY
					|| mapArray[insect_X + 1][insect_Y] == MapData.TARGET) {

				backSpaceEntity = encapsulationBackSpace(mapData
						.getMapDataArray());

				// 虫子前面是箱子
			} else if (mapArray[insect_X + 1][insect_Y] == MapData.BOX) {

				// 箱子前面是地板
				if (mapArray[insect_X + 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X + 2][insect_Y] = MapData.BOX;

					// 箱子前面是预定义位置
				} else if (mapArray[insect_X + 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X + 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}

				// 虫子前面是OK
			} else if (mapArray[insect_X + 1][insect_Y] == MapData.OK) {

				// OK前面是地板
				if (mapArray[insect_X + 2][insect_Y] == MapData.WAY) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X + 2][insect_Y] = MapData.BOX;

					// OK前面是预定义位置
				} else if (mapArray[insect_X + 2][insect_Y] == MapData.TARGET) {

					backSpaceEntity = encapsulationBackSpace(mapData
							.getMapDataArray());

					mapArray[insect_X + 2][insect_Y] = MapData.OK;
				} else {
					return false;
				}
				// 如果碰到墙
			} else if (mapArray[insect_X + 1][insect_Y] == MapData.WALL) {
				return false;
			}

			mapArray[insect_X + 1][insect_Y] = MapData.RIGHT;
			mapArray[insect_X][insect_Y] = MapData.WAY;
			insect_X++;

			backStack.push(backSpaceEntity);
			return true;
		}
	}

	/**
	 * 播放音乐
	 * 
	 * @param musicFilePath
	 * @return
	 */
	public Musician playMusic(String musicFilePath) {

		Musician musicThread = new Musician(musicFilePath);

		musicThread.start();

		return musicThread;
	}

	/**
	 * 停止在播放的音乐
	 * 
	 * @param musicThread
	 * @return
	 */
	public boolean stopMusic(Musician musicThread) {
		try {
			musicThread.stopMusic();
			musicThread.setFlag(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 判断是否胜利
	 * 
	 * @return
	 */
	public boolean isSuccess() {

		// 判断箱子是否全部到位
		if (calCurrent_OK() == targetCount) {
			return true;
		}

		return false;

	}

	/**
	 * 回退
	 * 
	 * @return
	 */
	public boolean backSpace() {

		if (backStack.isEmpty()) {
			JOptionPane.showMessageDialog(null, "你还没有移动!");
			return false;
		}

		int[][] tempData = backStack.pop().getMapData();

		if (tempData != null) {
			mapData.setMapDataArray(tempData);

			mapArray = mapData.getMapDataArray();

			// 更新人物的位置
			for (int x = 0; x < MapData.DATA_ROW; x++) {
				for (int y = 0; y < MapData.DATA_COL; y++) {

					if (mapArray[x][y] == MapData.UP
							|| mapArray[x][y] == MapData.DOWN
							|| mapArray[x][y] == MapData.LEFT
							|| mapArray[x][y] == MapData.RIGHT) {

						insect.setOneDimension(x);
						insect.setTwoDimension(y);

						insect_X = x;
						insect_Y = y;

					}

				}
			}
			return true;
		}

		return false;
	}

	/**
	 * 封装回退实体
	 * 
	 * @return
	 */
	public BackSpaceEntity encapsulationBackSpace(int[][] mapData) {

		int[][] tempMapData = new int[MapData.DATA_ROW][MapData.DATA_COL];

		for (int x = 0; x < MapData.DATA_ROW; x++) {
			for (int y = 0; y < MapData.DATA_COL; y++) {
				tempMapData[x][y] = mapData[x][y];
			}
		}

		BackSpaceEntity backSpaceEntity = new BackSpaceEntity();
		backSpaceEntity.setMapData(tempMapData);

		return backSpaceEntity;
	}
}
