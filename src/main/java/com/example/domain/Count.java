package com.example.domain;

/**
 * itemsテーブルの商品数の取得に関するドメイン.
 * 
 * @author shibatamasayuki
 *
 */
public class Count {
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Count [count=" + count + "]";
	}
}
