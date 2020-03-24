package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static void main(String[] args) {
		try {
			while (true) {
				ServerSocket sSoque = new ServerSocket(2019);
				System.out.println("Servidor esperando peticiones por el puerto 2019");
				// Esperando a que llegue una petición
				Socket soque = sSoque.accept();
				InputStreamReader isr = new InputStreamReader(soque.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				
				System.out.println(soque.getInetAddress().getHostAddress());

				String numero = br.readLine();
				String[] num = numero.split("-");

				int num1 = Integer.parseInt(num[0]);
				int num2 = Integer.parseInt(num[1]);
				int res = num1 + num2;

				PrintStream ps = new PrintStream(soque.getOutputStream());
				ps.println(res);
				sSoque.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
