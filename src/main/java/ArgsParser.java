import org.apache.commons.cli.*;

import org.apache.commons.cli.ParseException;

public class ArgsParser {
    private CommandLine commandLine;

    public void parseArgs(String []args) throws ParseException{
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        options.addOption("sq", "serial quick sorting", false, "The serial quick sorting");
        options.addOption("se", "serial enumeration sorting", false, "The serial enumeration sorting");
        options.addOption("sm", "serial merge sorting", false, "The serial merge sorting");
        options.addOption("pq", "parallel quick sorting", false, "The parallel quick sorting");
        options.addOption("pe", "parallel enumeration sorting", false, "The parallel enumeration sorting");
        options.addOption("pm", "parallel merge sorting", false, "The parallel merge sorting");

        options.addOption("s", "source data path", true, "The data source");
        options.addOption("r", "result storage path", true, "The result file");
        options.addOption("h", "help", false, "User Manual");

        this.commandLine = parser.parse(options, args);

        if (this.commandLine.hasOption("h")) {
            System.out.println("Simple User Manual:");
            System.out.println("                  : -sq         open the serial quick sorting");
            System.out.println("                  : -se         open the serial enumeration sorting");
            System.out.println("                  : -sm         open the serial merge sorting");
            System.out.println("                  : -pq         open the parallel quick sorting");
            System.out.println("                  : -pe         open the parallel enumeration sorting");
            System.out.println("                  : -pm         open the parallel merge sorting");
            System.out.println("                  : -s          input the data source path");
            System.out.println("                  : -r          input the result storage path");
            System.out.println("                  : -h          see the simple user manual");
        }
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

    public SortingKind getSortingKind(){
        if (this.commandLine.hasOption("sq")){
            return SortingKind.S_QUICK;
        }
        else if (this.commandLine.hasOption("se")){
            return SortingKind.S_ENUM;
        }
        else if (this.commandLine.hasOption("sm")){
            return SortingKind.S_MERGE;
        }
        else if (this.commandLine.hasOption("pq")){
            return SortingKind.P_QUICK;
        }
        else if (this.commandLine.hasOption("pe")){
            return SortingKind.P_ENUM;
        }
        else if (this.commandLine.hasOption("pm")) {
            return SortingKind.P_MERGE;
        }
        else
            return SortingKind.NONE;
    }
}

