/**
 * @projectName Parallel_Serial_Sorting
 * @fileName Main
 * @auther Qiaoyi Yin
 * @time 2018-12-29 03:01
 * @function Main function
 */

import org.apache.commons.cli.ParseException;

import java.util.ArrayList;

public class Main {
    public static void main(String []args){
        // Parse the arguments from the command line
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parseArgs(args);
        }catch (ParseException e){
            e.printStackTrace();
        }

        String inputFile = argsParser.getSrcPath();
        String outputFile = argsParser.getResPath();
        SortingKind sortingKind = argsParser.getSortingKind();
        int threadMaxNum = argsParser.getThreadMaxNum();
        int step = argsParser.getStep();

        // Obtain the content in the source file
        FileOperator fileOperator = new FileOperator();
        ArrayList<Integer> sourceData = fileOperator.obtainSourceArray(inputFile);

        switch(sortingKind){
            case S_QUICK:case S_MERGE:case S_ENUM:{
                SerialSorting sorting = new SerialSorting(sortingKind, sourceData);
                sorting.sortAsRequired(outputFile);
                break;
            }
            case P_ENUM:case P_MERGE:case P_QUICK:{
                for (int threadNum = threadMaxNum; threadNum>0; threadNum-=step) {
                    // Process the serial sorting methods
                    System.out.print(threadNum+": ");
                    ParallelSorting sorting = new ParallelSorting(sortingKind, sourceData, threadNum);
                    sorting.sortAsRequired(outputFile);
                }
                break;
            }
            case NONE: System.out.println("Please select a kind of sorting mode.");
        }


        System.out.println("All parallel/serial sorting methods have been finished.");
    }
}
