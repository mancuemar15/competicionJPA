package com.aytos.es.competicionjpa.utils;

import java.util.Scanner;

public class ScannerUtil {

	private static final Scanner SC = new Scanner(System.in);

	public static int getNumeroEnteroPositivo(String mensaje) {
		int input = -1;
		do {
			System.out.print(mensaje);
			try {
				input = Integer.parseInt(SC.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Introduce un número.");
			}
		} while (input < 0);
		return input;
	}

	public static String getString(String mensaje) {
		System.out.print(mensaje);
		return SC.nextLine().trim();
	}

	public static void cerrar() {
		SC.close();
	}

}
