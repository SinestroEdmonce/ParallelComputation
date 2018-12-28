import org.apache.commons.cli.*;

import org.apache.commons.cli.ParseException;

public class ArgsParser {
    private CommandLine commandLine;

    public void parseArgs(String []args) throws ParseException{
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("q", "quick sorting", false, "The serial quick sorting");
        options.addOption("e", "enumeration sorting", false, "The serial enumeration sorting");
        options.addOption("m", "merge sorting", false, "The serial merge sorting");
        options.addOption("s", "source data path", true, "The data source");
        options.addOption("r", "result storage path", true, "The result file");
        options.addOption("h", "help", false, "User Manual");

        this.commandLine = parser.parse(options, args);

        if (this.commandLine.hasOption("h"))
            System.out.println("Please Input the source data path, the result storage path and also the kind of serial sorting");
    }

    public String getSrcPath(){
        if (this.commandLine.hasOption("s"))
            return this.commandLine.getOptionValue("s");
        return null;
    }

    public String getResPath(){
        if (this.commandLine.hasOption("r"))
            return this.commandLine.getOptionValue("r");
        return null;
    }

    public SerialSortingKind getSortingKind(){
        if (this.commandLine.hasOption("q")){
            return SerialSortingKind.S_QUICK;
        }
        else if (this.commandLine.hasOption("e")){
            return SerialSortingKind.S_ENUM;
        }
        else if (this.commandLine.hasOption("m")){
            return SerialSortingKind.S_MERGE;
        }
        else
            return SerialSortingKind.S_NONE;
    }
}

