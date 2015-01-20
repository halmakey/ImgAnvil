package jp.rubi3.ninepatcher;

import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;
import static java.lang.System.out;

import java.io.File;
import java.util.stream.IntStream;

/**
 * Created by kikuchi on 2015/01/20.
 */
public class Main {
    private static final String OPTION_IN_FILE = "i";
    private static final String OPTION_STRETCHABLE_AREA_PADDING = "s";
    private static final String OPTION_CONTENT_PADDING = "c";
    private static final String OPTION_DELETE_INFILE = "d";

    public static void main(String[] args) {

        Options options = new Options();

        {
            options.addOption(OptionBuilder.withArgName("path")
                    .hasArg()
                    .withDescription("入力ファイル名")
                    .isRequired()
                    .create(OPTION_IN_FILE));

            options.addOption(OptionBuilder.withArgName("left top right bottom")
                    .hasArgs(4)
                    .withDescription("Padding for Stretchable area.")
                    .create(OPTION_STRETCHABLE_AREA_PADDING));

            options.addOption(OptionBuilder.withArgName("left top right bottom")
                    .hasArgs(4)
                    .withDescription("Padding for Content area.")
                    .create(OPTION_CONTENT_PADDING));

            options.addOption(OptionBuilder.withArgName("delete")
                    .withDescription("Delete infile.")
                    .create(OPTION_DELETE_INFILE));
        }
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter help = new HelpFormatter();
            help.printHelp(Main.class.getSimpleName(), options, true);
            return;
        }

        String inFile = cmd.getOptionValue(OPTION_IN_FILE);
        String extension = FilenameUtils.getExtension(inFile);
        String outFile = FilenameUtils.removeExtension(inFile);
        outFile += ".9." + extension;

        Patcher patcher = new Patcher(new File(inFile));
        
        if (cmd.hasOption(OPTION_STRETCHABLE_AREA_PADDING)) {
            String values[] = cmd.getOptionValues(OPTION_STRETCHABLE_AREA_PADDING);
            int edges[] = new int[args.length];
            IntStream.range(0, values.length).forEach(i -> {
                try {
                    edges[i] = Integer.parseInt(values[i]);
                } catch (NumberFormatException e) {
                    edges[i] = 0;
                }
            });
            patcher.padding(edges[0], edges[1], edges[2], edges[3]);
        } else {
            patcher.padding(0, 0, 0, 0);
        }
        
        if (cmd.hasOption(OPTION_CONTENT_PADDING)) {
            String values[] = cmd.getOptionValues(OPTION_CONTENT_PADDING);
            int edges[] = new int[args.length];
            IntStream.range(0, values.length).forEach(i -> {
                try {
                    edges[i] = Integer.parseInt(values[i]);
                } catch (NumberFormatException e) {
                    edges[i] = 0;
                }
            });
            patcher.content(edges[0], edges[1], edges[2], edges[3]);
        } else {
            patcher.content(0, 0, 0, 0);
        }

        if (patcher.saveTo(new File(outFile)).isSuccessful()) {
            out.println(outFile + " success");
            
            if (cmd.hasOption(OPTION_DELETE_INFILE)) {
                if (!new File(inFile).delete()) {
                    out.println(inFile + " deletion failed.");
                }
            }
        } else {
            out.println(outFile + " failed " + patcher.getException().getLocalizedMessage());
        }
        
    }
}
