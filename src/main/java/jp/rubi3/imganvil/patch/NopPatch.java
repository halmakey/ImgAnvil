package jp.rubi3.imganvil.patch;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by kikuchi on 2015/03/03.
 */
public class NopPatch extends AbstractPatch<NopPatch> {
    public NopPatch(File file) {
        try {
            inFile = file;
            bufferedImage = ImageIO.read(inFile);
        } catch (IOException e) {
            e.printStackTrace();
            exception = e;
        }
    }

    @Override
    public File suggestOutFile() {
        return inFile;
    }
}
