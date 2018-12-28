import java.lang.*;
import java.math.*;
import java.net.Inet4Address;
import java.util.*;

enum SerialSortingKind { S_NONE, S_QUICK, S_ENUM, S_MERGE}

/**
 *
 */
public class SerialSorting {
    private SerialSortingKind sortingKind = SerialSortingKind.S_NONE;
    private ArrayList<Integer> dataSet;
    private ArrayList<Integer> result;

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
    public void sortAsRequired(){

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

        printResult();
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
            this.quickSort(res, lowPos, highPos);
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
            int pivot = -1;
            int
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
            int pivot = -1;
            int
        }

    }

    /**
     *
     */
    public void printResult(){
        
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
}
