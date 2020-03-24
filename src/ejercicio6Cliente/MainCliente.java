package ejercicio6Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainCliente {

	private static final String SEPARADOR = ";";

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		String op;
		do {
			op = mostarMenu();
			switch (op) {
			case "1":
				pedirPrimo();
				break;
			case "0":
				System.out.println("Adios");
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}
		} while (!op.equals("0"));
	}

	private static void pedirPrimo() {
		System.out.println("Introduce un número: ");
		String num = sc.next();
		String address = "127.0.0.1";
		int port = 2019;
		Socket soque = null;
		PrintStream ps = null;
		try {
			soque = new Socket(address, port);
			ps = new PrintStream(soque.getOutputStream());
			ps.println(num);
			responder(soque);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ps.close();
		}
	}

	private static void responder(Socket soque) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(soque.getInputStream()));
			String[] res = br.readLine().split(SEPARADOR);
			System.out.println("El número " + res[0] + res[1]);
			System.out.println("Se ha tardado " + res[2] + " milisegundos en procesar la petición");
			br.close();
			soque.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String mostarMenu() {
		System.out.println("1.- Comprobar número primo\n0.- Salir");
		return sc.next();
	}
}
