package com.fixsokoban.po;

/**
 * 闯关记录
 * 
 * @author Shinelon
 *
 */
public class Record {

	private String name; // 用户名
	private String level; // 难度
	private int moves; // 移动步数

	public Record(String name, String level, int moves) {
		super();
		this.name = name;
		this.level = level;
		this.moves = moves;
	}

	public Record() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

}
