/**
 * @projectName Parallel_Serial_Sorting
 * @fileName ParallelSorting
 * @auther Qiaoyi Yin
 * @time 2018-12-28 23:04
 * @function A method integrated with three kinds of parallel sorting
 */
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.lang.Runnable;
import java.lang.Thread;

public class ParallelSorting {
    private SortingKind sortingKind = SortingKind.NONE;
    private ArrayList<Integer> dataSet;
    private ArrayList<Integer> result = null;
    private int threadMaxNum;
    /**
     * Initialize the class instance
     * @param kind
     * @param resource
     */
    public ParallelSorting(SortingKind kind, ArrayList<Integer> resource, int threadMaxNum){
        this.sortingKind = kind;
        this.dataSet = new ArrayList<Integer>(resource);
        this.threadMaxNum = threadMaxNum;
    }

    /**
     * Reset the sorting kind
     * @param kind
     */
    public void setSortingKind(SortingKind kind){
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
    public SortingKind getSortingKind(){
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
            case P_QUICK: {
                QuickSort quickSort = new QuickSort(this.dataSet, this.threadMaxNum);
                this.result = new ArrayList<Integer>(quickSort.sorting());
                break;
            }
            case P_MERGE: {
                MergeSort mergeSort = new MergeSort(this.dataSet, this.threadMaxNum);
                this.result = new ArrayList<Integer>(mergeSort.sorting());
                break;
            }
            case P_ENUM: {
                EnumerationSort enumerationSort = new EnumerationSort(this.dataSet, this.threadMaxNum);
                this.result = new ArrayList<Integer>(enumerationSort.sorting());
                break;
            }
            case NONE: default: {
                System.out.println("No selection has been made! Default mode will be QuickSort");

                QuickSort quickSort = new ParallelSorting.QuickSort(this.dataSet, this.threadMaxNum);
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
        private ArrayList<Integer> result;
        private int threadMaxNum;

        public QuickSort(ArrayList<Integer> resource, int threadMaxNum){
            this.data = new ArrayList<Integer>(resource);
            this.threadMaxNum = threadMaxNum;
        }

        public ArrayList<Integer> sorting(){
            int lowPos = 0;
            int highPos = this.data.size()-1;
            this.result = new ArrayList<Integer>(this.data);

            // Calculate the program running time
            long startTime = System.currentTimeMillis();

            // Parallel computing part
            QSort qSort = new QSort(this.result, 0, this.result.size()-1, this.threadMaxNum);
            Thread mainThread = new Thread(qSort);
            mainThread.start();

            try {
                mainThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            long interval = System.currentTimeMillis()-startTime;
            System.out.println("Total time of parallel quick sort: " + interval);

            this.result = qSort.getSrcData();
            return result;
        }

        class QSort implements Runnable {
            private ArrayList<Integer> srcData;
            private int low;
            private int high;
            private int threadNum;

            public QSort(ArrayList<Integer> source, int low, int high, int threadMaxNum){
                this.srcData = source;
                this.high = high;
                this.low = low;
                this.threadNum = threadMaxNum;
            }

            public  ArrayList<Integer> getSrcData(){
                return this.srcData;
            }

            private void parallelQuickSort() {
                int pivot = -1;

                if (low < high) {
                    // Find the correct location of pivotKey
                    pivot = partition(this.srcData, this.low, this.high);

                    // Continue to sorting the rest
                    QSort qSortSub1 = new QSort(this.srcData, this.low, pivot-1, this.threadNum-1);
                    QSort qSortSub2 = new QSort(this.srcData, pivot+1, this.high, this.threadNum-1);
                    Thread subThread1 = new Thread(qSortSub1);
                    Thread subThread2 = new Thread(qSortSub2);
                    subThread1.start();
                    subThread2.start();

                    try {
                        subThread1.join();
                        subThread2.join();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
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
             *
             * @param source
             * @param low
             * @param high
             * @return
             */
            private int partition(ArrayList<Integer> source, int low, int high) {
                try {
                    int pivotKey = source.get(low);

                    while (low < high) {
                        // Put the element that is smaller than the pivotKey in front of pK
                        while (low < high && source.get(high) >= pivotKey) {
                            high -= 1;
                        }
                        swap(source, low, high);
                        // Put the element that is smaller than the pivotKey in front of pK
                        while (low < high && source.get(low) <= pivotKey) {
                            low += 1;
                        }
                        swap(source, low, high);
                    }
                    return low;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return low;
            }

            @Override
            public void run() {
                if (this.threadNum > 0){
                    parallelQuickSort();
                }
                else{
                    quickSort(this.srcData, this.low, this.high);
                }

            }

        }
    }

    /**
     * MergeSort class, designed for achieving merge-sorting
     */
    class MergeSort {
        private ArrayList<Integer> data;
        private int threadMaxNum;

        public MergeSort(ArrayList<Integer> resource, int threadMaxNum){
            this.data = new ArrayList<Integer>(resource);
            this.threadMaxNum = threadMaxNum;
        }

        public ArrayList<Integer> sorting(){

            // Calculate the program running time
            long startTime = System.currentTimeMillis();

            // Parallel computing part
            MSort mSort = new MSort(this.data, 0, this.data.size()-1, this.threadMaxNum);
            Thread mainThread = new Thread(mSort);
            mainThread.start();

            try {
                mainThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            long interval = System.currentTimeMillis()-startTime;
            System.out.println("Total time of parallel merge sort: " + interval);

            return this.data;
        }

        class MSort implements Runnable {
            private ArrayList<Integer> srcData;
            private int left;
            private int right;
            private int threadNum;

            public MSort(ArrayList<Integer> source, int left, int right, int threadMaxNum){
                this.srcData = source;
                this.right = right;
                this.left = left;
                this.threadNum = threadMaxNum;
            }

            @Override
            public void run() {
                if (this.threadNum > 0){
                    parallelMergeSort();
                }
                else{
                    mergeSort(this.srcData, this.left, this.right);
                }

            }

            private void parallelMergeSort() {
                if (left < right) {
                    int mid = (left + right) / 2;

                    // Merge sort in left
                    MSort qSortSub1 = new MSort(this.srcData, this.left, mid, this.threadNum-1);
                    // Merge sort in right
                    MSort qSortSub2 = new MSort(this.srcData, mid+1, this.right, this.threadNum-1);

                    // Create sub-threads to continue the task
                    Thread subThread1 = new Thread(qSortSub1);
                    Thread subThread2 = new Thread(qSortSub2);
                    subThread1.start();
                    subThread2.start();

                    try {
                        subThread1.join();
                        subThread2.join();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Merge the left and right ordered sub-array
                    this.merge(this.srcData, left, mid, right);
                }
            }

            private void mergeSort(ArrayList<Integer> arrayList, int left,int right){
                if (left<right) {
                    int mid = (left + right) / 2;
                    // Merge sort in left
                    this.mergeSort(arrayList, left, mid);
                    // Merge sort in right
                    this.mergeSort(arrayList, mid + 1, right);
                    // Merge the left and right ordered sub-array
                    this.merge(arrayList, left, mid, right);
                }
            }

            /**
             * Merge the left and right ordered sub-array
             *
             * @param arrayList
             * @param left
             * @param mid
             * @param right
             */
            private void merge(ArrayList<Integer> arrayList, int left, int mid, int right) {
                // Temp array to store the ordered results
                int[] temp = new int[right - left + 1];
                // Pointer in left
                int ptLeft = left;
                // Pointer in right
                int ptRight = mid + 1;
                // Pointer in temp array
                int ptTemp = 0;

                // Use elements in original array to fill the temp array in order
                while (ptLeft <= mid && ptRight <= right) {
                    if (arrayList.get(ptLeft) <= arrayList.get(ptRight)) {
                        temp[ptTemp] = arrayList.get(ptLeft);
                        ptTemp += 1;
                        ptLeft += 1;
                    } else {
                        temp[ptTemp] = arrayList.get(ptRight);
                        ptTemp += 1;
                        ptRight += 1;
                    }
                }

                // Use the rest elements in left sub-array to fill the temp array
                while (ptLeft <= mid) {
                    temp[ptTemp] = arrayList.get(ptLeft);
                    ptTemp += 1;
                    ptLeft += 1;
                }

                // Use the rest elements in right sub-array to fill the temp array
                while (ptRight <= right) {
                    temp[ptTemp] = arrayList.get(ptRight);
                    ptTemp += 1;
                    ptRight += 1;
                }

                // Store the ordered elements in temp array
                for (int idx = 0; idx < temp.length; ++idx) {
                    arrayList.set(left + idx, temp[idx]);
                }
            }
        }

    }

    /**
     * EnumerationSort class, designed for achieving enumeration sorting
     */
    class EnumerationSort {
        private ArrayList<Integer> data;
        private int threadMaxNum;
        private ArrayList<Integer> result;

        public EnumerationSort(ArrayList<Integer> resource, int threadMaxNum){
            this.data = new ArrayList<Integer>(resource);
            this.threadMaxNum = threadMaxNum;
        }

        public ArrayList<Integer> sorting(){
            this.result = new ArrayList<Integer>(this.data);

            // Calculate the program running time
            long interval = 0;

            for (int idx=0; idx<=this.data.size()/threadMaxNum+1; ++idx){
                ArrayList<Thread> threadList = new ArrayList<Thread>();
                for (int itr=0; itr<threadMaxNum; ++itr){
                    int curPos = idx*threadMaxNum+itr;
                    if (curPos < this.data.size()){
                        // Calculate the program running time
                        long startTime = System.currentTimeMillis();

                        ESort eSort = new ESort(this.data.get(curPos), this.result, this.data);
                        Thread subThread = new Thread(eSort);
                        subThread.start();
                        threadList.add(subThread);

                        interval += System.currentTimeMillis()-startTime;
                    }
                }

                // Calculate the program running time
                long startTime = System.currentTimeMillis();

                // Parent thread will be await until the child threads have been finished
                try {

                    for (Thread thread: threadList) {
                        thread.join();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                interval += System.currentTimeMillis()-startTime;
            }

            System.out.println("Total time of parallel enumeration sort: " + interval);
            return this.result;
        }

        class ESort implements Runnable {
            private ArrayList<Integer> result;
            private ArrayList<Integer> srcData;
            private int currentElement;

            public ESort(int curElem, ArrayList<Integer> result, ArrayList<Integer> srcData){
                this.result = result;
                this.srcData = srcData;
                this.currentElement = curElem;
            }

            @Override
            public void run(){
                int finalPos = 0;
                for (int idx=0; idx<this.srcData.size(); ++idx){
                    if (this.currentElement > this.srcData.get(idx)){
                        finalPos += 1;
                    }
                }
                this.result.set(finalPos, this.currentElement);
            }

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
     * swap the two elements' location
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

        for (int threadNum = threadMaxNum; threadNum>0; threadNum-=step) {
            // Process the serial sorting methods
            System.out.print(threadNum+": ");
            ParallelSorting sorting = new ParallelSorting(sortingKind, sourceData, threadNum);
            sorting.sortAsRequired(outputFile);
        }

        System.out.println("All parallel sorting methods have been finished.");
    }
}
