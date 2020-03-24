package ejercicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Ejercicio1ServerHilo implements Runnable {

	private Socket soque;
	private Calendar cal;

	public Ejercicio1ServerHilo(Socket soque) {
		this.soque = soque;
		cal = Calendar.getInstance();
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(soque.getInputStream()));) {
			String peticion = br.readLine();
			switch (peticion) {
			case "XML":
				crearXML(pedirHora());
				break;
			case "PRO":
				enviarHora(pedirHora());
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void enviarHora(String pedirHora) {
		try (PrintWriter pw = new PrintWriter(soque.getOutputStream())) {
			pw.println(pedirHora);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String pedirHora() {
		String hora = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(cal.get(Calendar.MINUTE));
		String second = String.valueOf(cal.get(Calendar.SECOND));
		return hora + ":" + minute + ":" + second;
	}

	private void crearXML(String hora) {
		String[] horaArr = hora.split(":");

		Element root = new Element("HoraActual");
		Document docum = new Document();
		docum.setRootElement(root);

		Element time = new Element("time");
		time.addContent(new Element("hora").setText(horaArr[0]));
		time.addContent(new Element("minuto").setText(horaArr[1]));
		time.addContent(new Element("segundo").setText(horaArr[2]));
		docum.getRootElement().addContent(time);

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		String xml = out.outputString(docum);
		try (PrintWriter pw = new PrintWriter(soque.getOutputStream());) {
			pw.println(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
