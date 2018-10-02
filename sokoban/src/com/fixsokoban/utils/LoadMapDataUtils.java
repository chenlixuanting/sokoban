package com.fixsokoban.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fixsokoban.po.Insect;
import com.fixsokoban.po.MapData;

/**
 * 加载地图数据的工具类
 * 
 * @author Shinelon
 *
 */
public class LoadMapDataUtils {

	@SuppressWarnings("resource")
	public static void load(int level, Insect insect, MapData mapData) {

		int[][] map = new int[20][20];

		FileReader fileReader;
		BufferedReader bufferedReader = null;

		String bb = "";
		int c = 0;

		String str;

		try {
			File file = new File("maps/" + level + ".map");
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
		} catch (IOException e) {
			System.out.println(e);
		}

		try {
			while ((str = bufferedReader.readLine()) != null) {
				bb = bb + str;
			}
		} catch (IOException g) {
			System.out.println(g);
		}

		byte[] strBytes = bb.getBytes();
		int len = bb.length();
		int[] e = new int[len];

		for (int i = 0; i < bb.length(); i++)
			e[i] = strBytes[i] - 48;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				map[i][j] = e[c];

				if (e[c] == 5) {
					insect.setOneDimension(i);
					insect.setTwoDimension(j);
				}

				c++;
			}
		}

		mapData.setMapDataArray(map);

		return;
	}
}
