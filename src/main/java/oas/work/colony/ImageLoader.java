package oas.work.colony.client.gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;

public class ImageLoader {
    public static List<BufferedImage> loadImages(String folderPath) {
        List<BufferedImage> images = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] subFiles = folder.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    if (subFile.isDirectory()) {
                        File logoFile = new File(subFile, "logo.jpg");
                        if (logoFile.exists() && logoFile.isFile()) {
                            try {
                                BufferedImage image = ImageIO.read(logoFile);
                                if (image != null) {
                                    images.add(resizeImage(image, 80, 80));
                                }
                            } catch (IOException e) {
                                System.err.println("Erreur lors de la lecture de l'image : " + logoFile.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
        return images;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();
        return resizedImage;
    }
}
