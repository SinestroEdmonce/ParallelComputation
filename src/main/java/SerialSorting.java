import java.lang.*;
import java.util.*;

enum SerialSortingKind { S_NONE, S_QUICK, S_ENUM, S_MERGE}

/**
 * A class that implement serial sorting methods
 */
public class SerialSorting {
    private SerialSortingKind sortingKind = SerialSortingKind.S_NONE;
    private ArrayList<Integer> dataSet;
    private ArrayList<Integer> result = null;

    /**
     * Initialize the class instance
     * @param kind
     * @param resource
     */
    public SerialSorting(SerialSortingKind kind, ArrayList<Integer> resource){
        this.sortingKind = kind;
        this.dataSet = new ArrayList<Integer>(resource);
    }

    /**
     * Reset the sorting kind
     * @param kind
     */
    public void setSortingKind(SerialSortingKind kind){
        this.sortingKind = kind;
    }

    /**
     * Reset the data set
     * @param resource
     */
    public void setDataSet(ArrayList<Integer> resource){
        this.dataSet = new ArrayList<Integer>(resource);
    }

    /**
     * Obtain the sorting kind
     * @return
     */
    public SerialSortingKind getSortingKind(){
        return this.sortingKind;
    }

    /**
     * Obtain the data set
     * @return
     */
    public ArrayList<Integer> getDataSet(){
        return this.dataSet;
    }

    /**
     * Sort the array as required and output the result
     */
    public void sortAsRequired(String output){

        switch (this.sortingKind){
            case S_ENUM: {
                QuickSort quickSort = new QuickSort(this.dataSet);
                this.result = new ArrayList<Integer>(quickSort.sorting());
                break;
            }
            case S_MERGE: {
                MergeSort mergeSort = new MergeSort(this.dataSet);
                this.result = new ArrayList<Integer>(mergeSort.sorting());
                break;
            }
            case S_QUICK: {
                EnumerationSort enumerationSort = new EnumerationSort(this.dataSet);
                this.result = new ArrayList<Integer>(enumerationSort.sorting());
                break;
            }
            case S_NONE: default: {
                System.out.println("No selection has been made! Default mode will be QuickSort");

                QuickSort quickSort = new QuickSort(this.dataSet);
                this.result = new ArrayList<Integer>(quickSort.sorting());
                break;
            }
        }

        printResult(output);
    }

    /**
     * QuickSort class, designed for achieving quick sorting
     */
    class QuickSort {
        private ArrayList<Integer> data;

        public QuickSort(ArrayList<Integer> resource){
            this.data = new ArrayList<Integer>(resource);
        }

        public ArrayList<Integer> sorting(){
            int lowPos = 0;
            int highPos = this.data.size()-1;
            ArrayList<Integer> res = new ArrayList<Integer>(this.data);

            // Calculate the program running time
            long startTime = System.currentTimeMillis();

            this.quickSort(res, lowPos, highPos);

            long interval = System.currentTimeMillis()-startTime;
            System.out.println("Total time of serial quick sort: " + interval);

            return res;
        }

        private void quickSort(ArrayList<Integer> source, int low, int high){
            int pivot = -1;

            if (low<high){
                // Find the correct location of pivotKey
                pivot = partition(source, low, high);

                // Continue to sorting the rest
                quickSort(source, low, pivot-1);
                quickSort(source, pivot+1, high);
            }
        }

        /**
         * Obtain the correct location of pivotKey and sort it in correct order
         * @param source
         * @param low
         * @param high
         * @return
         */
        private int partition(ArrayList<Integer> source, int low, int high){
            try{
                int pivotKey = source.get(low);

                while(low<high){
                    // Put the element that is smaller than the pivotKey in front of pK
                    while(low<high && source.get(high)>=pivotKey){
                        high -= 1;
                    }
                    swap(source, low, high);
                    // Put the element that is smaller than the pivotKey in front of pK
                    while(low<high && source.get(low)<=pivotKey){
                        low += 1;
                    }
                    swap(source, low, high);
                }
                return low;
            }catch(Exception e){
                e.printStackTrace();
            }

            return low;
        }
    }

    /**
     * MergeSort class, designed for achieving merge-sorting
     */
    class MergeSort {
        private ArrayList<Integer> data;

        public MergeSort(ArrayList<Integer> resource){
            this.data = new ArrayList<Integer>(resource);
        }

        public ArrayList<Integer> sorting(){
            ArrayList<Integer> result = new ArrayList<Integer>(this.data);

            // Calculate the program running time
            long startTime = System.currentTimeMillis();

            this.sort(this.data, 0, this.data.size()-1, result);

            long interval = System.currentTimeMillis()-startTime;
            System.out.println("Total time of serial merge sort: " + interval);

            return result;
        }

        private void sort(ArrayList<Integer> arrayList, int left,int right, ArrayList<Integer> temp){
            if (left<right) {
                int mid = (left + right) / 2;
                // Merge sort in left
                this.sort(arrayList, left, mid, temp);
                // Merge sort in right
                this.sort(arrayList, mid + 1, right, temp);
                // Merge the left and right ordered sub-array
                this.merge(arrayList, left, mid, right, temp);
            }
        }

        /**
         * Merge the left and right ordered sub-array
         * @param arrayList
         * @param left
         * @param mid
         * @param right
         * @param temp
         */
        private void merge(ArrayList<Integer> arrayList, int left, int mid, int right, ArrayList<Integer> temp){
            // Pointer in left
            int ptLeft = left;
            // Pointer in right
            int ptRight = mid+1;
            // Pointer in temp array
            int ptTemp = 0;

            // Use elements in original array to fill the temp array in order
            while(ptLeft<=mid && ptRight<=right){
                if (arrayList.get(ptLeft)<=arrayList.get(ptRight)){
                    temp.set(ptTemp, arrayList.get(ptLeft));
                    ptTemp += 1;
                    ptLeft += 1;
                }
                else{
                    temp.set(ptTemp, arrayList.get(ptRight));
                    ptTemp += 1;
                    ptRight += 1;
                }
            }

            // Use the rest elements in left sub-array to fill the temp array
            while(ptLeft<=mid){
                temp.set(ptTemp, arrayList.get(ptLeft));
                ptTemp += 1;
                ptLeft += 1;
            }

            // Use the rest elements in right sub-array to fill the temp array
            while(ptRight<=right){
                temp.set(ptTemp, arrayList.get(ptRight));
                ptTemp += 1;
                ptRight += 1;
            }
        }

    }

    /**
     * EnumerationSort class, designed for achieving enumeration sorting
     */
    class EnumerationSort {
        private ArrayList<Integer> data;

        public EnumerationSort(ArrayList<Integer> resource){
            this.data = new ArrayList<Integer>(resource);
        }

        public ArrayList<Integer> sorting(){
            ArrayList<Integer> result = new ArrayList<Integer>(this.data);
            // Calculate the program running time
            long startTime = System.currentTimeMillis();

            for (int idx=0; idx<this.data.size(); ++idx){
                int finalPos = 0;
                for (int itr=0; itr<this.data.size(); ++itr){
                    if (this.data.get(idx)>this.data.get(itr)){
                        finalPos += 1;
                    }
                }
                int tmp = this.data.get(idx);
                result.set(finalPos, tmp);
            }

            long interval = System.currentTimeMillis()-startTime;
            System.out.println("Total time of serial enumeration sort: " + interval);

            return result;
        }

    }

    /**
     * Output results into the given file
     * @param outputName
     */
    public void printResult(String outputName){
        FileOperator fileOperator = new FileOperator();
        fileOperator.output2File(outputName, this.result);
    }

    /**
     *
     * @param array
     * @param low
     * @param high
     */
    public void swap(ArrayList<Integer> array, int low, int high){
        int tmp = array.get(low);
        array.set(low, array.get(high));
        array.set(high, tmp);
    }

    public static void main(String []args){
        // Parse the arguments from the command line
//        ArgsParser argsParser = new ArgsParser();
//        try {
//            argsParser.parseArgs(args);
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//
//        String inputFile = argsParser.getSrcPath();
//        String outputFile = argsParser.getResPath();

        String inputFile = "src/random.txt";
        String outputFile = "./result.txt";

        // Obtain the content in the source file
        FileOperator fileOperator = new FileOperator();
        ArrayList<Integer> sourceData = fileOperator.obtainSourceArray(inputFile);

        // Process the serial sorting methods
        SerialSorting quickSort = new SerialSorting(SerialSortingKind.S_QUICK, sourceData);
        //SerialSorting mergeSort = new SerialSorting(SerialSortingKind.S_MERGE, sourceData);
        //SerialSorting enumerationSort = new SerialSorting(SerialSortingKind.S_ENUM, sourceData);

        quickSort.sortAsRequired(outputFile);
        //mergeSort.sortAsRequired(outputFile);
        //enumerationSort.sortAsRequired(outputFile);

        System.out.println("All serial sorting methods have been finished.");
    }
}
