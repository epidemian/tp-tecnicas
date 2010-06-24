package view.game;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static final Map<String, BufferedImage> IMAGES = new HashMap<String, BufferedImage>();
	private static final String IMG_DIR = ""; // "res/img/";

	private static BufferedImage loadImage(String path) {
		System.out.println("LOAD IMAGE " + path);
		BufferedImage image = null;
		try {
			URL url = ImageLoader.class.getClassLoader().getResource(path);
			image = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public static BufferedImage getImage(String imgName) {

		String path = IMG_DIR + imgName;
		if (!IMAGES.containsKey(path))
			IMAGES.put(path, loadImage(path));
		return IMAGES.get(path);
	}
}
