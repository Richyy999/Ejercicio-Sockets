package Ejercicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Ejercicio1Cliente {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String opcion = "";
		do {
			System.out.println("1.- Formato XML\n2.- Formato Propietario\n0.- Salir");
			opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				enviar("XML");
				break;
			case "2":
				enviar("PRO");
				break;
			case "0":
				System.out.println("Adiós");
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}
		} while (!opcion.equals("0"));
		sc.close();
	}

	private static void enviar(String peticion) {
		String ip = "127.0.0.1";
		int puerto = 2019;
		try (Socket soque = new Socket(ip, puerto);
				PrintStream ps = new PrintStream(soque.getOutputStream());
				InputStreamReader isr = new InputStreamReader(soque.getInputStream());
				BufferedReader br = new BufferedReader(isr);) {
			ps.println(peticion);
			String res = br.readLine();
			if (res.startsWith("<")) {
				System.out.println(parsear(isr));
			} else
				System.out.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String parsear(InputStreamReader isr) {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document docum = builder.build(isr);
			Element root = docum.getRootElement();
			List<Element> datos = root.getChildren();
			String hora = "";
			for (Element element : datos) {
				hora = element.getChildText("hora") + ":" + element.getChildText("minuto") + ":"
						+ element.getChildText("segundo");
			}
			return hora;
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
