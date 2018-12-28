import org.apache.commons.cli.*;

import org.apache.commons.cli.ParseException;

public class ArgsParser {
    private CommandLine commandLine;

    public void parseArgs(String []args) throws ParseException{
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("s", "source data path", true, "The data source");
        options.addOption("r", "result storage path", true, "The result file");
        options.addOption("h", "help", false, "User Manual");

        this.commandLine = parser.parse(options, args);

        if (this.commandLine.hasOption("h"))
            System.out.println("Please Input the source data path and the result storage path.");
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
}

