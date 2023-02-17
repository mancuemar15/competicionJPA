package com.aytos.es.competicionjpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HistoricoRecord")
public class HistoricoRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "IdAtleta", nullable = false)
	private Atleta atleta;

	@ManyToOne
	@JoinColumn(name = "IdRecord", nullable = false)
	private Record record;

	@ManyToOne
	@JoinColumn(name = "IdEjercicio", nullable = false)
	private Ejercicio ejercicio;

	@Column(name = "Weight")
	private int weight;

	public HistoricoRecord(Atleta atleta, Record record, Ejercicio ejercicio, int weight) {
		super();
		this.atleta = atleta;
		this.record = record;
		this.ejercicio = ejercicio;
		this.weight = weight;
	}

	public HistoricoRecord() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Atleta getAtleta() {
		return atleta;
	}

	public void setAtleta(Atleta atleta) {
		this.atleta = atleta;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Ejercicio getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
