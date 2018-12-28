import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class FileOperator {
    private StringBuilder inputContent;
    private StringBuilder outputContent;

    /**
     * Read one line every time from the given file
     */
    private void readFileByLines(String inputName){
        File file = new File(inputName);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            this.inputContent = new StringBuilder();

            while((tempString = reader.readLine()) != null){
                this.inputContent.append(tempString);
                this.inputContent.append(" ");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Obtain the data from the source file and transform it into array
     * @return
     */
    public ArrayList<Integer> obtainSourceArray(String inputName){
        try{
            this.readFileByLines(inputName);
            StringTokenizer tokenizer = new StringTokenizer(this.inputContent.toString());

            ArrayList<Integer> source = new ArrayList<Integer>();
            while(tokenizer.hasMoreTokens()){
                source.add(Integer.valueOf(tokenizer.nextToken()));
            }
            return source;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Output the result to files
     * @param outputName
     * @param source
     */
    public void output2File(String outputName, ArrayList<Integer> source){
        this.outputContent = new StringBuilder();
        Iterator<Integer> itr = source.iterator();

        while(itr.hasNext()){
            this.outputContent.append((itr.next().intValue()));
            this.outputContent.append(" ");
        }

        try {
            FileWriter fileWriter = new FileWriter(outputName, true);
            fileWriter.write(this.outputContent.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
