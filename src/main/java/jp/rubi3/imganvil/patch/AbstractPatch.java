package jp.rubi3.imganvil.patch;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by kikuchi on 2015/03/03.
 */
public abstract class AbstractPatch<T extends AbstractPatch> {
    protected BufferedImage bufferedImage;
    protected Exception exception;
    protected File inFile;

    public Exception getException() {
        return exception;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public boolean isSuccessful() {
        return exception == null;
    }
    
    public abstract File suggestOutFile();
}
