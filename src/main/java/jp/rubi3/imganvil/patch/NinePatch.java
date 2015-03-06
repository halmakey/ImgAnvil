package jp.rubi3.imganvil.patch;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.IntStream;

/**
 * Created by kikuchi on 2015/03/03.
 */
public class NinePatch extends AbstractPatch<NinePatch> {
    Graphics2D graphics;
    
    public NinePatch(File file) {
        super();
        try {
            inFile = file;
            BufferedImage base = ImageIO.read(inFile);
            bufferedImage = new BufferedImage(base.getWidth() + 2, base.getHeight() + 2, BufferedImage.TYPE_INT_ARGB);
            graphics = bufferedImage.createGraphics();
            graphics.drawImage(base, 1, 1, null);
        } catch (Exception e) {
            exception = e;
        }
    }

    public NinePatch content(int left, int top, int right, int bottom) {
        if (!isSuccessful()) {
            return this;
        }

        BufferedImage canvas = bufferedImage;
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

    public NinePatch innerStretch(int left, int top, int right, int bottom) {
        if (!isSuccessful()) {
            return this;
        }

        BufferedImage canvas = bufferedImage;
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

    public NinePatch outerStretch(int left, int top, int right, int bottom) {
        if (!isSuccessful()) {
            return this;
        }

        BufferedImage canvas = bufferedImage;
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

    @Override
    public File suggestOutFile() {
        String name = inFile.getName();
        String extension = FilenameUtils.getExtension(name);
        name = FilenameUtils.removeExtension(name) + ".9." + extension;

        return new File(inFile.getParent(), name);
    }
}
