package com.fixsokoban.po;

import java.awt.Toolkit;

/**
 * 地图数据实体
 * 
 * @author Shinelon
 *
 */
public class MapData {

	// 20行
	public static int DATA_ROW = 20;

	// 20列
	public static int DATA_COL = 20;

	// 内圈围墙
	public static final int OUTER_RING = 0;

	// 墙
	public static final int WALL = 1;

	// 地板
	public static final int WAY = 2;

	// 箱子
	public static final int BOX = 3;

	// 目标
	public static final int TARGET = 4;

	// 人物向上
	public static final int UP = 5;

	// 人物向下
	public static final int DOWN = 6;

	// 人物向左
	public static final int LEFT = 7;

	// 人物向右
	public static final int RIGHT = 8;

	// 成功
	public static final int OK = 9;

	/**
	 * 会退栈标记
	 */

	// 向上移动没有移动箱子
	public static final int UP_NOT_MOVE_BOX = 100;

	// 向上移动移动箱子
	public static final int UP_MOVE_BOX = 200;

	// 地图二维数组数据
	private int[][] mapDataArray;

	public int[][] getMapDataArray() {
		return mapDataArray;
	}

	public void setMapDataArray(int[][] mapDataArray) {
		this.mapDataArray = mapDataArray;
	}

	/**
	 * 地图数据
	 */
//	 private int[][] mapData = {
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 1, 4, 2, 3, 5, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 1, 1, 1, 3, 2, 3, 4, 1, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//	 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

}
