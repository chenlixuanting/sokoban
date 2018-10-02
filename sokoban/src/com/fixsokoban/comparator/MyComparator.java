package com.fixsokoban.comparator;

import java.util.Comparator;

import com.fixsokoban.po.Record;

public class MyComparator implements Comparator<Record> {

	@Override
	public int compare(Record o1, Record o2) {
		return o1.getMoves() - o2.getMoves();
	}

}
