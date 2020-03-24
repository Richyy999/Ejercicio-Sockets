package ejercicio6Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class HiloServer implements Runnable {

	private static final String SEPARADOR = ";";

	private Socket soque;

	private Date dia;

	public HiloServer(Socket soque) {
		this.soque = soque;
		this.dia = new Date();
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(soque.getInputStream()))) {
			String num = br.readLine();
			calcularPrimo(num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void calcularPrimo(String num) {
		System.out.println("Procesando petición...");
		long numero = Long.parseLong(num);
		long tiempoInicio = dia.getTime();
		boolean esPrimo = true;
		int contador = 0;
		for (long i = numero; i > 0; i--) {
			if (numero % i == 0)
				contador++;
		}
		esPrimo = contador == 2;
		long tiempoProceso = tiempoInicio - dia.getTime();
		System.out.println("Petición procesada");
		if (esPrimo)
			responder(num + SEPARADOR + " es primo" + SEPARADOR + String.valueOf(tiempoProceso));
		else
			responder(num + SEPARADOR + " no es primo" + SEPARADOR + String.valueOf(tiempoProceso));

	}

	private void responder(String response) {
		try (PrintWriter pw = new PrintWriter(soque.getOutputStream())) {
			pw.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
