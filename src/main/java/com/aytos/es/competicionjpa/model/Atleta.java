package com.aytos.es.competicionjpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Atleta")
public class Atleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Column(name = "FullName")
	private String fullName;

	@Column(name = "Age")
	private int age;

	@Column(name = "Genre")
	private String genre;

	@Column(name = "BenchPress")
	private int benchPress;

	@Column(name = "Deadlift")
	private int deadlift;

	@Column(name = "Squat")
	private int squat;

	@Column(name = "Total")
	private int total;

	public void calcularTotal() {
		this.total = this.benchPress + this.deadlift + this.squat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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
