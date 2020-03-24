package ejercicio6Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static void main(String[] args) {
		try (ServerSocket sSoque = new ServerSocket(2019)) {
			while (true) {
				System.out.println("Esperando peticiones...");
				Socket soque = sSoque.accept();
				new Thread(new HiloServer(soque)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
