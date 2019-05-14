package ch.jonasgredig.xprmt.controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainFrameController {

    public BufferedImage generateRandomPicture(int xSize, int ySize) {
        int red = 0;
        int green = 0;
        int blue = 0;
        final BufferedImage res = new BufferedImage( xSize, ySize, BufferedImage.TYPE_INT_RGB );
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                red = getRandomNumberInRange(0, 255);
                green = getRandomNumberInRange(0, 255);
                blue = getRandomNumberInRange(0, 255);
                res.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
        return res;
    }

    public boolean savePNG(BufferedImage image, String path){
        try {
            RenderedImage rendImage = image;
            ImageIO.write(rendImage, "bmp", new File(path + "FILENAME.bmp"));
            ImageIO.write(rendImage, "PNG", new File(path + "FILENAME.png"));
            return true;
        } catch ( IOException e) {
            return false;
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
