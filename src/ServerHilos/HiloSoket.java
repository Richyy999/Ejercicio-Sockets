package ServerHilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HiloSoket implements Runnable {

	private Socket soque;
	
	public static int numPeticion = 0;

	public HiloSoket(Socket soque) {
		this.soque = soque;
		numPeticion++;
	}

	@Override
	public void run() {
		InputStreamReader isr;
		try {
			System.out.println("Procdesando petición: " + numPeticion);
			isr = new InputStreamReader(soque.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			
			System.out.println(soque.getInetAddress().getHostAddress());

			String numero = br.readLine();
			String[] num = numero.split("-");

			int num1 = Integer.parseInt(num[0]);
			int num2 = Integer.parseInt(num[1]);
			int res = num1 + num2;

			PrintStream ps = new PrintStream(soque.getOutputStream());
			ps.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				soque.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
