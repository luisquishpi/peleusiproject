package ec.peleusi.utils.fx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageUtils {
	public static byte[] getByteFoto(Image image) {
		byte[] byteImage = null;
		try {
			BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", s);
			byteImage = s.toByteArray();
			s.close();
		} catch (IOException e) {
			byteImage = null;
			e.printStackTrace();
		}
		return byteImage;
	}
}
