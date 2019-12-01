package Server;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class ServerImagen {

	public static void main(String[] args) {
		try {
			System.out.println("Load");
			ServerSocket serverSocket = new ServerSocket(2019);
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();

			System.out.println("Reading: " + System.currentTimeMillis());

			byte[] sizeAr = new byte[4];
			inputStream.read(sizeAr);
			int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

			byte[] imageAr = new byte[size];
			inputStream.read(imageAr);

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

			System.out.println(
					"Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
			ImageIO.write(image, "png", new File(pedirRuta()));

			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String pedirRuta() {
		FileDialog dialog = new FileDialog((Frame) null, "Enviar Imagen", FileDialog.SAVE);
		dialog.setVisible(true);
		String ruta = dialog.getDirectory() + dialog.getFile();
		dialog.dispose();
		return ruta;
	}
}
