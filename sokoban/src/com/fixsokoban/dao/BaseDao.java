package com.fixsokoban.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fixsokoban.comparator.MyComparator;
import com.fixsokoban.po.Record;
import com.fixsokoban.utils.JDBCUtils;

/**
 * 数据库操作类
 * 
 * @author Shinelon
 *
 */
public class BaseDao {

	private static Connection con = JDBCUtils.getConnection();

	// 插入一条记录
	public static boolean insertOneRecord(Record rec) {

		String sql = "insert into record values(?,?,?);";
		PreparedStatement ps = null;
		boolean flag = false;

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, rec.getName());
			ps.setString(2, rec.getLevel());
			ps.setInt(3, rec.getMoves());

			flag = ps.execute();

			// 提交事务
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// 按等级获取记录
	public static List<Record> findByLevel(String level) {

		String sql = "select * from record where level=?";
		List<Record> records = new ArrayList<Record>();
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, level);
			ResultSet rs = ps.executeQuery();

			// 提交事务
			con.commit();

			while (rs.next()) {
				Record re = new Record();
				re.setName(rs.getString("name"));
				re.setLevel(rs.getString("level"));
				re.setMoves(rs.getInt("moves"));
				records.add(re);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Collections.sort(records, new MyComparator());

		return records;
	}

	/**
	 * 按名称获取记录
	 * 
	 * @param name
	 * @return
	 */
	public static List<Record> findByName(String name) {

		String sql = "select * from record where name=?";
		List<Record> records = new ArrayList<Record>();
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			// 提交事务
			con.commit();

			while (rs.next()) {
				Record re = new Record();
				re.setName(rs.getString("name"));
				re.setLevel(rs.getString("level"));
				re.setMoves(rs.getInt("moves"));
				records.add(re);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Collections.sort(records, new MyComparator());

		return records;

	}

	/**
	 * 按名称和等级获取记录
	 * 
	 * @param name
	 * @param level
	 * @return
	 */
	public static List<Record> findByNameAndLevel(String name, String level) {

		String sql = "select * from record where name=? and level=?";
		List<Record> records = new ArrayList<Record>();
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, level);
			ResultSet rs = ps.executeQuery();

			// 提交事务
			con.commit();

			while (rs.next()) {
				Record re = new Record();
				re.setName(rs.getString("name"));
				re.setLevel(rs.getString("level"));
				re.setMoves(rs.getInt("moves"));
				records.add(re);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Collections.sort(records, new MyComparator());

		return records;
	}

	// 获取所有记录
	public static List<Record> findAll() {

		List<Record> records = new ArrayList<Record>();
		List<Record> easyRecords = findByLevel("简单");
		List<Record> MedRecords = findByLevel("中等难度");
		List<Record> HardRecords = findByLevel("困难");

		records.addAll(easyRecords);
		records.addAll(MedRecords);
		records.addAll(HardRecords);

		return records;
	}
}
