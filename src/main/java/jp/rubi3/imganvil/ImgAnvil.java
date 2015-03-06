package jp.rubi3.imganvil;

import jp.rubi3.imganvil.patch.AbstractPatch;
import jp.rubi3.imganvil.patch.NinePatch;
import jp.rubi3.imganvil.patch.NopPatch;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by kikuchi on 2015/01/20.
 */
public class ImgAnvil {
    private File inFile;
    private File outFile;
    private Exception exception;

    private AbstractPatch patch;
    public ImgAnvil(File inFile) {
        this.inFile = inFile;
    }

    public NinePatch ninePatch() {
        if (this.patch instanceof NinePatch) {
            return (NinePatch) this.patch;
        }
        NinePatch patch = new NinePatch(inFile);
        this.patch = patch;
        return patch;
    }
    
    public boolean isSuccessful() {
        return exception == null && patch != null && patch.isSuccessful();
    }

    public Exception getException() {
        if (exception != null) {
            return exception;
        }
        if (patch != null && patch.getException() != null) {
            return patch.getException();
        }
        return null;
    }

    public File getInFile() {
        return inFile;
    }

    public File getOutFile() {
        return outFile;
    }
    
    public boolean equalsInOutFile() {
        return Objects.equals(inFile, outFile);
    }

    public ImgAnvil saveTo(File file) {
        if (patch == null) {
            patch = new NopPatch(inFile);
        }
        if (!isSuccessful()) {
            return this;
        }

        if (file == null) {
            file = patch.suggestOutFile();
        }

        try {
            file.getParentFile().mkdirs();
            ImageIO.write(patch.getBufferedImage(), "png", file);
            outFile = file;
        } catch (IOException e) {
            exception = e;
            e.printStackTrace();
        }
        return this;
    }

    public ImgAnvil printState() {
        String result = isSuccessful() ? "success" : String.format("failure %s", getException().getLocalizedMessage());

        String path = inFile.toString();
        if (!equalsInOutFile() && outFile != null) {
            path = path.concat(String.format(" to %s", outFile));
        }
        System.out.println(String.format("%s %s", result, path));
        return this;
    }
}
