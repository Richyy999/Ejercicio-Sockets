package ejercicio5Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloServer implements Runnable {

	private static final String SEPARADOR = ";";

	private Socket soque;

	public HiloServer(Socket soque) {
		this.soque = soque;
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(soque.getInputStream()));) {
			String[] peticion = br.readLine().split(";");
			switch (peticion[0]) {
			case "sum":
				sumar(peticion[1], peticion[2]);
				break;
			case "res":
				restar(peticion[1], peticion[2]);
				break;
			case "mul":
				multiplicar(peticion[1], peticion[2]);
				break;
			case "div":
				dividir(peticion[1], peticion[2]);
				break;
			default:
				System.out.println("ERR");
				responder("err" + SEPARADOR);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void dividir(String num1, String num2) {
		try {
			int numero1 = Integer.parseInt(num1);
			int numero2 = Integer.parseInt(num2);
			String division = String.valueOf(numero1 / numero2);
			responder("div" + SEPARADOR + division);
		} catch (Exception e) {
			responder("err" + SEPARADOR);
		}
	}

	private void multiplicar(String num1, String num2) {
		try {
			int numero1 = Integer.parseInt(num1);
			int numero2 = Integer.parseInt(num2);
			String multiplicacion = String.valueOf(numero1 * numero2);
			responder("mul" + SEPARADOR + multiplicacion);
		} catch (Exception e) {
			responder("err" + SEPARADOR);
		}
	}

	private void restar(String num1, String num2) {
		try {
			int numero1 = Integer.parseInt(num1);
			int numero2 = Integer.parseInt(num2);
			String resta = String.valueOf(numero1 - numero2);
			responder("res" + SEPARADOR + resta);
		} catch (Exception e) {
			responder("err" + SEPARADOR);
		}
	}

	private void sumar(String num1, String num2) {
		try {
			int numero1 = Integer.parseInt(num1);
			int numero2 = Integer.parseInt(num2);
			String suma = String.valueOf(numero1 + numero2);
			responder("sum" + SEPARADOR + suma);
		} catch (Exception e) {
			responder("err" + SEPARADOR);
		}
	}

	private void responder(String res) {
		try (PrintWriter pw = new PrintWriter(soque.getOutputStream());) {
			pw.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
