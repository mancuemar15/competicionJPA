package com.aytos.es.competicionjpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Records")
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Column(name = "Genre")
	private String genre;

	@Column(name = "Category")
	private String category;

	@Column(name = "BenchPress")
	private int benchPress;

	@Column(name = "Deadlift")
	private int deadlift;

	@Column(name = "Squat")
	private int squat;

	@Column(name = "Total")
	private int total;

	public Record() {
		super();
	}

	public Record(String genre, String category, int benchPress, int deadlift, int squat, int total) {
		super();
		this.genre = genre;
		this.category = category;
		this.benchPress = benchPress;
		this.deadlift = deadlift;
		this.squat = squat;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBenchPress() {
		return benchPress;
	}

	public void setBenchPress(int benchPress) {
		this.benchPress = benchPress;
	}

	public int getDeadlift() {
		return deadlift;
	}

	public void setDeadlift(int deadlift) {
		this.deadlift = deadlift;
	}

	public int getSquat() {
		return squat;
	}

	public void setSquat(int squat) {
		this.squat = squat;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
