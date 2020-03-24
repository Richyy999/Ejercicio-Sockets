package ejercicio5Cliente;

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
			op = mostrarMenu();
			switch (op) {
			case "1":
				enviar("sum");
				break;
			case "2":
				enviar("res");
				break;
			case "3":
				enviar("mul");
				break;
			case "4":
				enviar("div");
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

	private static void enviar(String param) {
		System.out.println("Introduce el primer número: ");
		String num1 = sc.next();
		System.out.println("Introduce el segundo número: ");
		String num2 = sc.next();
		int puerto = 2019;
		String ip = "127.0.0.1";
		Socket soque = null;
		PrintStream ps = null;
		try {
			soque = new Socket(ip, puerto);
			ps = new PrintStream(soque.getOutputStream());
			ps.println(param + SEPARADOR + num1 + SEPARADOR + num2);
			respuesta(soque);
			ps.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void respuesta(Socket soque) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(soque.getInputStream()));
			String[] res = br.readLine().split(SEPARADOR);
			switch (res[0]) {
			case "sum":
				System.out.println("La suma es: " + res[1]);
				break;
			case "res":
				System.out.println("La resta es: " + res[1]);
				break;
			case "mul":
				System.out.println("La multiplicación es: " + res[1]);
				break;
			case "div":
				System.out.println("La división es: " + res[1]);
				break;
			case "err":
				System.err.println("Ha habido un error en el servidor");
				break;
			}
			br.close();
			soque.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String mostrarMenu() {
		System.out.println("\n1.- Sumar\n2.- Restar\n3.- Muliplicar\n4.- Dividir\n0.- Salir");
		return sc.next();
	}
}
