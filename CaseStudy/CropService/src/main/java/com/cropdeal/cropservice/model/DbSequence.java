package com.cropdeal.cropservice.model;

public class DbSequence {

	private String id;
	private int seq;

	public DbSequence() {

	}

	public DbSequence(String id, int seq) {
		super();
		this.id = id;
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
