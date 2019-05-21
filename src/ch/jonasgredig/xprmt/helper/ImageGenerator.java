package ch.jonasgredig.xprmt.helper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageGenerator {

    public BufferedImage generateRandomPicture(int xSize, int ySize, boolean redSelected, boolean greenSelected, boolean blueSelected, boolean chronoSelected) {
        int red_value;
        int green_value;
        int blue_value;

        int red_max = 0;
        int green_max = 0;
        int blue_max = 0;

        if (redSelected) {
            red_max = 255;
        }
        if (greenSelected) {
            green_max = 255;
        }
        if (blueSelected) {
            blue_max = 255;
        }

        final BufferedImage res = new BufferedImage( xSize, ySize, BufferedImage.TYPE_INT_RGB );

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                if (chronoSelected) {
                    int value = getRandomNumberInRange(0, 255);
                    red_value = value;
                    green_value = value;
                    blue_value = value;
                } else {
                    red_value = getRandomNumberInRange(0, red_max);
                    green_value = getRandomNumberInRange(0, green_max);
                    blue_value = getRandomNumberInRange(0, blue_max);
                }

                res.setRGB(x, y, new Color(red_value, green_value, blue_value).getRGB());
            }
        }
        return res;
    }

    public boolean savePNG(BufferedImage image, String path, String format) throws IOException {
            RenderedImage rendImage = image;
            ImageIO.write(rendImage, format, new File(path));
            return true;

    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            return min;
        }

        Random r = new Random();
        return r.nextInt(max);
    }

}
