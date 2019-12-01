package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class MainCliente {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca los números: ");
		String num1 = sc.nextLine();
		String num2 = sc.nextLine();

		String cadena = num1 + "-" + num2;
		String ip = "10.34.80.74";
		int puerto = 2019;
		try (Socket soque = new Socket(ip, puerto);
				PrintStream ps = new PrintStream(soque.getOutputStream());
				InputStreamReader isr = new InputStreamReader(soque.getInputStream());
				BufferedReader br = new BufferedReader(isr)) {
			ps.println(cadena);
			String res = br.readLine();
			System.out.println("El resultado de la operación es " + res);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
