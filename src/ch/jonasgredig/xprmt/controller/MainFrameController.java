package ch.jonasgredig.xprmt.controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MainFrameController {

    public BufferedImage generateRandomPicture(int xSize, int ySize) {
        final BufferedImage res = new BufferedImage( xSize, ySize, BufferedImage.TYPE_INT_RGB );
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                res.setRGB(x, y, Color.WHITE.getRGB() );
            }
        }
        return res;
    }

    public boolean savePNG(BufferedImage image, String path){
        try {
            RenderedImage rendImage = image;
            ImageIO.write(rendImage, "bmp", new File(path));
            ImageIO.write(rendImage, "PNG", new File(path));
            return true;
        } catch ( IOException e) {
            return false;
        }
    }
}
