package jp.rubi3.ninepatcher;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        int maxX = canvas.getWidth() - 1;
        int maxY = canvas.getHeight() - 1;
        int infoY = canvas.getHeight() - 1;
        int infoX = canvas.getWidth() - 1;

        IntStream.range(minX + left, maxX - right).forEach(value -> canvas.setRGB(value, infoY, 0xFF000000));
        IntStream.range(minY + top, maxY - bottom).forEach(value -> canvas.setRGB(infoX, value, 0xFF000000));

        return this;
    }

    public Patcher innerStretch(int left, int top, int right, int bottom) {
        if (exception != null) {
            return this;
        }
        
        int minX = 1;
        int minY = 1;
        int maxX = canvas.getWidth() - 1;
        int maxY = canvas.getHeight() - 1;
        int infoY = 0;
        int infoX = 0;
        
        IntStream.range(minX + left, maxX - right).forEach(value -> canvas.setRGB(value, infoY, 0xFF000000));
        IntStream.range(minY + top, maxY - bottom).forEach(value -> canvas.setRGB(infoX, value, 0xFF000000));

        return this;
    }

    public Patcher outerStretch(int left, int top, int right, int bottom) {
        if (exception != null) {
            return this;
        }

        int minX = 1;
        int minY = 1;
        int maxX = canvas.getWidth() - 1;
        int maxY = canvas.getHeight() - 1;
        int infoY = 0;
        int infoX = 0;

        IntStream.range(minX, minX + left).forEach(value -> canvas.setRGB(value, infoY, 0xFF000000));
        IntStream.range(maxX - right, maxX).forEach(value -> canvas.setRGB(value, infoY, 0xFF000000));

        IntStream.range(minY, minY + top).forEach(value -> canvas.setRGB(infoX, value, 0xFF000000));
        IntStream.range(maxY - bottom, maxY).forEach(value -> canvas.setRGB(infoX, value, 0xFF000000));

        return this;
    }

    public Patcher saveTo(File file) {
        if (exception != null) {
            return this;
        }

        try {
            file.getParentFile().mkdirs();
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
