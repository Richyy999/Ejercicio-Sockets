package ServerHilos;

import java.net.ServerSocket;
import java.net.Socket;

public class MainHilosSoket {

	public static void main(String[] args) {
		try (ServerSocket sSoque = new ServerSocket(2019);) {
			while (true) {
				System.out.println("Servidor esperando peticiones por el puerto 2019");
				// Esperando a que llegue una petición
				Socket soque = sSoque.accept();
				new Thread(new HiloSoket(soque)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}