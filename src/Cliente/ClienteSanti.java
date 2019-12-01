package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSanti {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean seguir = true;
		while (seguir) {
			System.out.println(
					"1.- Sumar\n2.- Restar\n3.- Multiplicar\n4.- Dividir\n5.- Calcular número primo\n6.- Salir");
			String eleccion = sc.nextLine();
			switch (eleccion) {
			case "1":
				sumar(sc);
				break;
			case "2":
				restar(sc);
				break;
			case "3":
				multiplicar(sc);
				break;
			case "4":
				dividir(sc);
				break;
			case "5":
				numPrimo(sc);
				break;
			case "6":
				seguir = false;
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}
		}
		System.out.println("Adios");
	}

	private static void enviar(String peticion) {
		String ip = "10.34.84.120";
		int puerto = 2019;
		try (Socket soque = new Socket(ip, puerto);
				PrintStream ps = new PrintStream(soque.getOutputStream());
				InputStreamReader isr = new InputStreamReader(soque.getInputStream());
				BufferedReader br = new BufferedReader(isr)) {
			ps.println(peticion);
			String res = br.readLine();
			if (res.startsWith("Es") || res.startsWith("No"))
				System.out.println(res);
			else
				System.out.println("El resultado de la operación es " + res);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void dividir(Scanner sc) {
		System.out.println("Inroduce el primer número: ");
		String num1 = sc.nextLine();
		System.out.println("Introduce el segundo número: ");
		String num2 = sc.nextLine();
		String peticion = "div" + " " + num1 + " " + num2;
		enviar(peticion);
	}

	private static void multiplicar(Scanner sc) {
		System.out.println("Inroduce el primer número: ");
		String num1 = sc.nextLine();
		System.out.println("Introduce el segundo número: ");
		String num2 = sc.nextLine();
		String peticion = "mul" + " " + num1 + " " + num2;
		enviar(peticion);
	}

	private static void restar(Scanner sc) {
		System.out.println("Inroduce el primer número: ");
		String num1 = sc.nextLine();
		System.out.println("Introduce el segundo número: ");
		String num2 = sc.nextLine();
		String peticion = "res" + " " + num1 + " " + num2;
		enviar(peticion);
	}

	private static void sumar(Scanner sc) {
		System.out.println("Inroduce el primer número: ");
		String num1 = sc.nextLine();
		System.out.println("Introduce el segundo número: ");
		String num2 = sc.nextLine();
		String peticion = "sum" + " " + num1 + " " + num2;
		enviar(peticion);
	}

	private static void numPrimo(Scanner sc) {
		System.out.println("Introduce un número: ");
		String num = sc.nextLine();
		String peticion = "pri " + num + " 0";
		enviar(peticion);
	}
}
