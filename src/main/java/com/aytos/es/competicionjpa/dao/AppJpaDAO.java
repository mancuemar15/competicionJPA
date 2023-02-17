package com.aytos.es.competicionjpa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.aytos.es.competicionjpa.model.Atleta;
import com.aytos.es.competicionjpa.model.Ejercicio;
import com.aytos.es.competicionjpa.model.HistoricoRecord;
import com.aytos.es.competicionjpa.model.Record;
import com.aytos.es.competicionjpa.utils.JPAUtil;

public class AppJpaDAO {

	private EntityManager entityManager;

	public AppJpaDAO() {
		super();
		this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	}

	// Operaciones sobre Atleta

	public Atleta obtenerAtletaPorId(int id) {
		Atleta atleta = null;
		try {
			atleta = this.entityManager.find(Atleta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return atleta;
	}

	public List<Atleta> obtenerAtletasMasterPorGenero(String genre) {
		List<Atleta> atletas = new ArrayList<>();
		try {
			atletas = this.entityManager
					.createQuery("SELECT a FROM Atleta a WHERE Genre = :genre AND Age > 32 ORDER BY Total DESC",
							Atleta.class)
					.setParameter("genre", genre)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return atletas;
	}

	public List<Atleta> obtenerAtletasNormalPorGenero(String genre) {
		List<Atleta> atletas = new ArrayList<>();
		try {
			atletas = this.entityManager
					.createQuery("SELECT a FROM Atleta a WHERE Genre = :genre AND Age <= 32 ORDER BY Total DESC",
							Atleta.class)
					.setParameter("genre", genre)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return atletas;
	}

	public int obtenerMaximaMarcaSegunGeneroCategoriaYEjercicio(String genre, String category, String exercise) {
		int maximaMarca = 0;
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
		Root<Atleta> root = query.from(Atleta.class);

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get("genre"), genre));

		if ("MASTER".equals(category)) {
			predicates.add(cb.gt(root.get("age"), 32));
		} else {
			predicates.add(cb.le(root.get("age"), 32));
		}

		query.select(cb.max(root.get(exercise))).where(predicates.toArray(new Predicate[predicates.size()]));

		try {
			maximaMarca = this.entityManager.createQuery(query).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maximaMarca;
	}

	public boolean insertarAtleta(Atleta atleta) {
		boolean exito = false;
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.persist(atleta);
			this.entityManager.getTransaction().commit();
			exito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	public boolean actualizarAtleta(Atleta atleta) {
		boolean exito = false;

		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.merge(atleta);
			this.entityManager.getTransaction().commit();
			exito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exito;
	}

	public boolean eliminarAtletaPorId(int id) {
		boolean exito = false;
		this.entityManager.getTransaction().begin();
		Atleta atletaAEliminar = this.obtenerAtletaPorId(id);

		if (Objects.nonNull(atletaAEliminar)) {
			this.entityManager.remove(atletaAEliminar);
			exito = true;
		}

		this.entityManager.getTransaction().commit();
		return exito;
	}

	// Operaciones sobre ejercicio

	public Ejercicio obtenerEjercicioPorNombre(String exercise) {
		Ejercicio ejercicio = null;
		try {
			ejercicio = (Ejercicio) this.entityManager
					.createQuery("SELECT e FROM Ejercicio e WHERE Name = :name", Ejercicio.class)
					.setParameter("name", exercise)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ejercicio;
	}

	// Operaciones sobre Record

	public List<Record> obtenerTodosLosRecords() {
		List<Record> records = new ArrayList<>();
		try {
			records = this.entityManager.createQuery("from Record", Record.class)
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}

	public Record obtenerRecordMasterPorGenero(String genre) {
		Record record = null;
		try {
			record = (Record) this.entityManager
					.createQuery("SELECT r FROM Record r WHERE Genre = :genre AND Category = 'MASTER'")
					.setParameter("genre", genre)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	public Record obtenerRecordNormalPorGenero(String genre) {
		Record record = null;
		try {
			record = (Record) this.entityManager
					.createQuery("SELECT r FROM Record r WHERE Genre = :genre AND Category = 'NORMAL'")
					.setParameter("genre", genre)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	public void insertarRecords(List<Record> records) {
		try {
			this.entityManager.getTransaction().begin();
			records.stream().forEach(record -> this.entityManager.persist(record));
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarRecord(Record record) {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.merge(record);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Operaciones sobre HistoricoRecord

	public void insertarRecordEnHistorico(HistoricoRecord historicoRecord) {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.persist(historicoRecord);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cerrar() {
		this.entityManager.close();
	}

}
