package jp.rubi3.ninepatcher;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by kikuchi on 2015/01/20.
 */
public class Patcher {
    private BufferedImage base;
    @Getter
    private BufferedImage canvas;
    private Graphics2D graphics;
    @Getter
    private Exception exception;

    public Patcher(File file) {
        try {
            base = ImageIO.read(file);
            canvas = new BufferedImage(base.getWidth() + 2, base.getHeight() + 2, BufferedImage.TYPE_INT_ARGB);
            graphics = canvas.createGraphics();

            graphics.drawImage(base, 1, 1, null);
            
            exception = null;
            
        } catch (IOException e) {
            exception = e;
            e.printStackTrace();
        }
    }
    
    public Patcher content(int left, int top, int right, int bottom) {
        if (exception != null) {
            return this;
        }

        int minX = 1;
        int minY = 1;
        int maxX = canvas.getWidth() - 2;
        int maxY = canvas.getHeight() - 2;
        int infoY = canvas.getHeight() - 1;
        int infoX = canvas.getWidth() - 1;
        
        graphics.setColor(Color.black);
        graphics.drawLine(minX + left, infoY, maxX - right, infoY);
        graphics.drawLine(infoX, minY + top, infoX, maxY - bottom);
        return this;
    }

    public Patcher padding(int left, int top, int right, int bottom) {
        if (exception != null) {
            return this;
        }
        
        int minX = 1;
        int minY = 1;
        int maxX = canvas.getWidth() - 2;
        int maxY = canvas.getHeight() - 2;
        int infoY = 0;
        int infoX = 0;

        graphics.setColor(Color.black);
        graphics.drawLine(minX + left, infoY, maxX - right, infoY);
        graphics.drawLine(infoX, minY + top, infoX, maxY - bottom);
        return this;
    }
    
    public Patcher saveTo(File file) {
        if (exception != null) {
            return this;
        }

        try {
            ImageIO.write(canvas, "png", file);
        } catch (IOException e) {
            exception = e;
            e.printStackTrace();
        }
        return this;
    }
    
    public boolean isSuccessful() {
        return exception == null;
    }
}
