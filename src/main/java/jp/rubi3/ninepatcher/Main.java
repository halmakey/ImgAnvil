package jp.rubi3.ninepatcher;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import jp.rubi3.ninepatcher.anvil.NinePatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by kikuchi on 2015/01/20.
 */
public class Main {
    static {
        System.setProperty("java.awt.headless", "true");
    }

    public static void main(String[] args) {
        JCommander jc = new JCommander();
        jc.setProgramName("ImgAnvil");

        TrimParams trimParams = new TrimParams();
        NinePatchParams ninePatchParams = new NinePatchParams();

        try {
            jc.addCommand(trimParams);
            jc.addCommand(ninePatchParams);
            jc.parse(args);

            switch (jc.getParsedCommand()) {
                case "trim":
                    trim(trimParams);
                    break;
                case "nine":
                    nine(ninePatchParams);
                    break;
                default:
                    jc.usage();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            jc.usage();
            return;
        }
    }
    
    private static void trim(TrimParams params) {
        params.fileParamList().forEach(new Consumer<FileParam>() {
            @Override
            public void accept(FileParam fileParam) {
                ImgAnvil anvil = new ImgAnvil(fileParam.inFile);
                anvil.saveTo(fileParam.outFile);

                if (anvil.isSuccessful() && !anvil.equalsInOutFile() && !params.nodelete) {
                    fileParam.inFile.delete();
                }

                anvil.printState();
            }
        });
        
    }
    
    private static void nine(NinePatchParams params) {
        params.fileParamList().forEach(new Consumer<FileParam>() {
            @Override
            public void accept(FileParam fileParam) {
                ImgAnvil anvil = new ImgAnvil(fileParam.inFile);
                NinePatch patch = anvil.ninePatch();
                
                if (params.contentArea != null) {
                    patch.content(
                            params.contentArea.get(0).intValue(),
                            params.contentArea.get(1).intValue(),
                            params.contentArea.get(2).intValue(),
                            params.contentArea.get(3).intValue()
                    );
                }
                
                if (params.innerStretch != null) {
                    patch.innerStretch(
                            params.innerStretch.get(0).intValue(),
                            params.innerStretch.get(1).intValue(),
                            params.innerStretch.get(2).intValue(),
                            params.innerStretch.get(3).intValue()
                    );
                }
                
                if (params.outerStretch != null) {
                    patch.outerStretch(
                            params.outerStretch.get(0).intValue(),
                            params.outerStretch.get(1).intValue(),
                            params.outerStretch.get(2).intValue(),
                            params.outerStretch.get(3).intValue()
                    );
                }
                
                anvil.saveTo(fileParam.outFile);

                if (anvil.isSuccessful() && !anvil.equalsInOutFile() && !params.nodelete) {
                    fileParam.inFile.delete();
                }

                anvil.printState();
            }
        });
    }

    public static class FileParam {
        File inFile;
        File outFile;
    }

    @Parameters(commandNames = "trim")
    public static class TrimParams {
        @Parameter(names = "-in", description = "In Files", variableArity = true, required = true)
        List<File> inFiles;

        @Parameter(names = "-out", description = "Out Files", variableArity = true)
        List<File> outFiles;

        @Parameter(names = "-nodelete", description = "No Delete In Files")
        boolean nodelete;

        public List<FileParam> fileParamList() {
            int inSize = inFiles.size();
            int outSize = outFiles == null ? 0 : outFiles.size();
            if (inSize != outSize && outSize > 0) {
                throw new RuntimeException("The number of input and output files did not match .");
            }

            List<FileParam> list = new ArrayList<>(inFiles.size());
            boolean overwrite = outSize == 0;

            for (int i = 0; i < inFiles.size(); i++) {
                FileParam file = new FileParam();
                file.inFile = inFiles.get(i);
                file.outFile = overwrite ? null : outFiles.get(i);
                list.add(file);
            }

            return list;
        }

        @Override
        public String toString() {
            return "TrimParam{" +
                    "inFiles=" + inFiles +
                    ", outFiles=" + outFiles +
                    ", nodelete=" + nodelete +
                    '}';
        }
    }

    @Parameters(commandNames = "nine", commandDescription = "Generate 9-Patched png")
    public static class NinePatchParams extends TrimParams {
        @Parameter(names = "-content", arity = 4, description = "Content Padding : <left top right bottom>")
        List<Integer> contentArea;

        @Parameter(names = "-inner", arity = 4, description = "Inner Stretchable Area : <left top right bottom>")
        List<Integer> innerStretch;

        @Parameter(names = "-outer", arity = 4, description = "Outer Stretchable Area : <left top right bottom>")
        List<Integer> outerStretch;

        @Override
        public String toString() {
            return "NinePatchParams{" +
                    "contentArea=" + contentArea +
                    ", innerStretch=" + innerStretch +
                    ", outerStretch=" + outerStretch +
                    '}';
        }
    }
}
