package cliente;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

public class ClienteImagen {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 2019);
			OutputStream outputStream = socket.getOutputStream();

			BufferedImage image = ImageIO.read(new File(pedirRuta()));

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", byteArrayOutputStream);

			byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			outputStream.write(size);
			outputStream.write(byteArrayOutputStream.toByteArray());
			outputStream.flush();
			System.out.println("Flushed: " + System.currentTimeMillis());

			Thread.sleep(120000);
			System.out.println("Closing: " + System.currentTimeMillis());
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String pedirRuta() {
		FileDialog dialog = new FileDialog((Frame) null, "Enviar Imagen", FileDialog.LOAD);
		dialog.setVisible(true);
		String ruta = dialog.getDirectory() + dialog.getFile();
		dialog.dispose();
		return ruta;
	}
}
