package view.game;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageLoader {

    private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    private static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			URL url = ImageLoader.class.getClassLoader()
					.getResource(path);
			image = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
    }

    public static BufferedImage getImage(String imgName){

        if (!images.containsKey(imgName))
			images.put(imgName, loadImage(imgName));
		return images.get(imgName);
    }
}
