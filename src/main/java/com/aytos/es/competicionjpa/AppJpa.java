package com.aytos.es.competicionjpa;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.aytos.es.competicionjpa.model.HistoricoRecord;
import com.aytos.es.competicionjpa.model.Record;

import com.aytos.es.competicionjpa.dao.AppJpaDAO;
import com.aytos.es.competicionjpa.model.Atleta;
import com.aytos.es.competicionjpa.model.Ejercicio;
import com.aytos.es.competicionjpa.utils.JPAUtil;
import com.aytos.es.competicionjpa.utils.ScannerUtil;

public class AppJpa {

	private static final AppJpaDAO appJpaDAO = new AppJpaDAO();

	public static void main(String[] args) {
		ejecutarMenuPrincipal();

		appJpaDAO.cerrar();
		JPAUtil.shutdown();
		ScannerUtil.cerrar();
	}

	private static void mostrarOpcionesMenuPrincipal() {
		System.out.println("\n*************************** Menú principal ***************************");
		System.out.println("1. Introducir atleta");
		System.out.println("2. Revisar clasificación");
		System.out.println("3. Eliminar atleta");
		System.out.println("4. Modificar atleta");
		System.out.println("5. Mostrar récords");
		System.out.println("0. Salir");
	}

	private static void mostrarOpcionesMenuClasificaciones() {
		System.out.println("\nMenú de clasificaciones:");
		System.out.println("1. Masculino Máster");
		System.out.println("2. Masculino Normal");
		System.out.println("3. Femenino Máster");
		System.out.println("4. Femenino Normal");
		System.out.println("0. Volver al menú principal");
	}

	private static void ejecutarMenuPrincipal() {
		int opcion = 0;

		do {
			mostrarOpcionesMenuPrincipal();
			opcion = ScannerUtil.getNumeroEnteroPositivo("Seleccione una opción: ");

			switch (opcion) {
			case 0:
				System.out.println("\n************ Gracias por utilizar el menú ************");
				break;
			case 1:
				insertarAtleta();
				break;
			case 2:
				ejecutarMenuClasificaciones();
				break;
			case 3:
				eliminarAtleta();
				break;
			case 4:
				modificarAtleta();
				break;
			case 5:
				mostrarTablaRecords();
				break;
			default:
				System.out.println("Opción inválida. Por favor seleccione una opción válida");
				break;
			}
		} while (opcion != 0);

	}

	private static void ejecutarMenuClasificaciones() {
		int opcion;

		do {
			mostrarOpcionesMenuClasificaciones();
			opcion = ScannerUtil.getNumeroEnteroPositivo("Seleccione una clasificación: ");

			switch (opcion) {
			case 0:
				ejecutarMenuPrincipal();
				break;
			case 1:
				mostrarClasificacion("Masculino", "MASTER");
				break;
			case 2:
				mostrarClasificacion("Masculino", "NORMAL");
				break;
			case 3:
				mostrarClasificacion("Femenino", "MASTER");
				break;
			case 4:
				mostrarClasificacion("Femenino", "NORMAL");
				break;
			default:
				System.out.println("Opción inválida. Por favor seleccione una opción válida");
				break;
			}
		} while (opcion != 5);
	}

	private static void insertarAtleta() {
		Atleta atletaAInsertar = new Atleta();

		System.out.println("\n=========================== NUEVO ATLETA ===========================");
		atletaAInsertar.setFullName(ScannerUtil.getString("Introduce el nombre completo: "));
		atletaAInsertar.setAge(ScannerUtil.getNumeroEnteroPositivo("Introduce la edad: "));
		atletaAInsertar.setGenre(ScannerUtil.getString("Introduce el género: "));
		atletaAInsertar.setBenchPress(ScannerUtil.getNumeroEnteroPositivo("Introduce la marca de BenchPress: "));
		atletaAInsertar.setDeadlift(ScannerUtil.getNumeroEnteroPositivo("Introduce la marca de Deadlift: "));
		atletaAInsertar.setSquat(ScannerUtil.getNumeroEnteroPositivo("Introduce la marca de Squat: "));
		atletaAInsertar.calcularTotal();

		if (appJpaDAO.insertarAtleta(atletaAInsertar)) {
			System.out.println("\n✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓  ATLETA INSERTADO CON ÉXITO ✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓");
			comprobarSiNuevoRecord(atletaAInsertar);
		} else {
			System.out.println("\nxxxxxxxxxxxxxxxxx NO SE HA PODIDO INSERTAR EL ATLETA xxxxxxxxxxxxxxxxx");
		}
	}

	private static void mostrarClasificacion(String genre, String categoria) {
		List<Atleta> atletas;

		if ("MASTER".equals(categoria)) {
			atletas = appJpaDAO.obtenerAtletasMasterPorGenero(genre);
		} else {
			atletas = appJpaDAO.obtenerAtletasNormalPorGenero(genre);
		}

		if (!atletas.isEmpty()) {
			System.out.println("\n======================== CLASIFICACIÓN " + genre.toUpperCase() + " " + categoria
					+ " ========================");
			System.out.println("ID\tNombre\t\t\tEdad\tGénero\t\tBenchPress\tDeadlift\tSquat\tTotal");
			for (Atleta atleta : atletas) {
				System.out.println(atleta.getId() + "\t" + atleta.getFullName() + "\t" + atleta.getAge() + "\t"
						+ atleta.getGenre() + "\t" + atleta.getBenchPress() + "\t\t" + atleta.getDeadlift() + "\t\t"
						+ atleta.getSquat() + "\t" + atleta.getTotal());
			}
		} else {
			System.out.println("xxxxxxxxxxxxxxxxxxxxx CLASIFICACIÓN VACÍA xxxxxxxxxxxxxxxxxxxxx");
		}
	}

	private static void eliminarAtleta() {
		System.out.println("\n=========================== ELIMINAR ATLETA ===========================");
		int idAtletaAEliminar = ScannerUtil
				.getNumeroEnteroPositivo("Introduce el id del atleta que quieres eliminar: ");

		if (appJpaDAO.eliminarAtletaPorId(idAtletaAEliminar)) {
			System.out.println(
					"\n✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓ ATLETA CON ID " + idAtletaAEliminar + " ELIMINADO CON ÉXITO ✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓");
		} else {
			System.out.println("\nxxxxxxxxxxxxxxxxx NO SE HA PODIDO ELIMINAR EL ATLETA xxxxxxxxxxxxxxxxx");
			System.out.println("NO EXISTE ATLETA CON ID " + idAtletaAEliminar);
		}
	}

	private static void modificarAtleta() {
		System.out.println("\n=========================== ACTUALIZAR ATLETA ===========================");
		int idAtletaAActualizar = ScannerUtil
				.getNumeroEnteroPositivo("Introduce el id del atleta que quieres modificar: ");
		Atleta atletaAActualizar = appJpaDAO.obtenerAtletaPorId(idAtletaAActualizar);

		if (Objects.nonNull(atletaAActualizar)) {
			atletaAActualizar.setBenchPress(ScannerUtil.getNumeroEnteroPositivo("Introduce la marca de BenchPress: "));
			atletaAActualizar.setDeadlift(ScannerUtil.getNumeroEnteroPositivo("Introduce la marca de Deadlift: "));
			atletaAActualizar.setSquat(ScannerUtil.getNumeroEnteroPositivo("Introduce la marca de Squat: "));
			atletaAActualizar.calcularTotal();

			if (appJpaDAO.actualizarAtleta(atletaAActualizar)) {
				System.out.println("\n✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓ ATLETA CON ID " + idAtletaAActualizar
						+ " ACTUALIZADO CON ÉXITO ✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓");
				comprobarSiNuevoRecord(atletaAActualizar);
			} else {
				System.out.println("\nxxxxxxxxxxxxxxxxx NO SE HA PODIDO ACTUALIZAR EL ATLETA xxxxxxxxxxxxxxxxx");
			}
		} else {
			System.out.println("\nNO EXISTE ATLETA CON ID " + idAtletaAActualizar);
		}
	}

	private static void crearRecordsEnBaseDatos() {
		Record recordMasterMasculino = crearRecord("Masculino", "MASTER");
		Record recordNormalMasculino = crearRecord("Masculino", "NORMAL");
		Record recordMasterFemenino = crearRecord("Femenino", "MASTER");
		Record recordNormalFemenino = crearRecord("Femenino", "NORMAL");

		List<Record> records = Arrays.asList(recordMasterMasculino, recordNormalMasculino, recordMasterFemenino,
				recordNormalFemenino);

		appJpaDAO.insertarRecords(records);
	}

	private static Record crearRecord(String genre, String category) {
		int mayorMarcaBenchPress = appJpaDAO.obtenerMaximaMarcaSegunGeneroCategoriaYEjercicio(genre, category,
				"benchPress");
		int mayorMarcaDeadlift = appJpaDAO.obtenerMaximaMarcaSegunGeneroCategoriaYEjercicio(genre, category,
				"deadlift");
		int mayorMarcaSquat = appJpaDAO.obtenerMaximaMarcaSegunGeneroCategoriaYEjercicio(genre, category, "squat");
		int mayorMarcaTotal = appJpaDAO.obtenerMaximaMarcaSegunGeneroCategoriaYEjercicio(genre, category, "total");

		return new Record(genre, category, mayorMarcaBenchPress, mayorMarcaDeadlift, mayorMarcaSquat, mayorMarcaTotal);
	}

	private static void comprobarSiNuevoRecord(Atleta atleta) {
		Record record;

		if (atleta.getAge() > 32) {
			record = appJpaDAO.obtenerRecordMasterPorGenero(atleta.getGenre());
		} else {
			record = appJpaDAO.obtenerRecordNormalPorGenero(atleta.getGenre());
		}

		if (Objects.nonNull(record)) {
			if (atleta.getBenchPress() > record.getBenchPress()) {
				System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD EN BENCHPRESS !!!!!!!!!!!!");
				record.setBenchPress(atleta.getBenchPress());

				Ejercicio ejercicio = appJpaDAO.obtenerEjercicioPorNombre("BENCHPRESS");
				appJpaDAO.insertarRecordEnHistorico(
						new HistoricoRecord(atleta, record, ejercicio, atleta.getBenchPress()));
			}

			if (atleta.getDeadlift() > record.getDeadlift()) {
				System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD EN DEADLIFT !!!!!!!!!!!!");
				record.setDeadlift(atleta.getDeadlift());

				Ejercicio ejercicio = appJpaDAO.obtenerEjercicioPorNombre("DEADLIFT");
				appJpaDAO.insertarRecordEnHistorico(
						new HistoricoRecord(atleta, record, ejercicio, atleta.getDeadlift()));
			}

			if (atleta.getSquat() > record.getSquat()) {
				System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD EN SQUAT !!!!!!!!!!!!");
				record.setSquat(atleta.getSquat());

				Ejercicio ejercicio = appJpaDAO.obtenerEjercicioPorNombre("SQUAT");
				appJpaDAO.insertarRecordEnHistorico(new HistoricoRecord(atleta, record, ejercicio, atleta.getSquat()));
			}

			if (atleta.getTotal() > record.getTotal()) {
				System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD TOTAL !!!!!!!!!!!!");
				record.setTotal(atleta.getTotal());

				Ejercicio ejercicio = appJpaDAO.obtenerEjercicioPorNombre("TOTAL");
				appJpaDAO.insertarRecordEnHistorico(new HistoricoRecord(atleta, record, ejercicio, atleta.getTotal()));
			}

			appJpaDAO.actualizarRecord(record);
		} else {
			crearRecordsEnBaseDatos();
			System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD EN BENCHPRESS !!!!!!!!!!!!");
			System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD EN DEADLIFT !!!!!!!!!!!!");
			System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD EN SQUAT !!!!!!!!!!!!");
			System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡ NUEVO RÉCORD TOTAL !!!!!!!!!!!!");
		}
	}

	private static void mostrarTablaRecords() {
		List<Record> records = appJpaDAO.obtenerTodosLosRecords();

		if (records.isEmpty()) {
			crearRecordsEnBaseDatos();
			records = appJpaDAO.obtenerTodosLosRecords();
		}

		System.out.println("\n======================== RÉCORDS ========================");
		System.out.println("Género\t\tCategoría\tBenchPress\tDeadlift\tSquat\tTotal");
		System.out.println("------\t\t---------\t----------\t--------\t-----\t-----");

		for (Record record : records) {
			System.out.println(record.getGenre() + "\t" + record.getCategory() + "\t\t" + record.getBenchPress()
					+ "\t\t" + record.getDeadlift() + "\t\t" + record.getSquat() + "\t" + record.getTotal());
		}
	}

}
