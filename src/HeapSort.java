import java.util.*;
import java.lang.*;

//*******************************************************************
// Class Name: HeapSort
//
// Description: The class to do heap sort for the url arraylist
//*******************************************************************
public class HeapSort {

    private int heapSize;

    //*******************************************************************
// Method Name: HeapSort
//
// Description: constructor, only set arraylist size to heapSize
//*******************************************************************
    //constructor
    HeapSort(ArrayList<URL> _A) {
        heapSize = _A.size();
    }

    //*******************************************************************
// Method Name: MaxHeapify
//
// Description: To compare a node's two child and to maintain the property of max heap
//*******************************************************************
    void MaxHeapify(ArrayList<URL> A, int i) {
//        System.out.println("Before MaxHeapify");
//        printList(A);
//        System.out.println();

        int largest = i;
        int l = getLeft(i);
        int r = getRight(i);

        if (l < heapSize && A.get(l).getTotalScore() > A.get(i).getTotalScore()) {
            largest = l;
        }

        if (r < heapSize && A.get(r).getTotalScore() > A.get(largest).getTotalScore()) {
            largest = r;
        }
        if (largest != i) {
            Collections.swap(A, i, largest);
            MaxHeapify(A, largest);//call to compare to the num now have the largest's index
        }
//        System.out.println("After MaxHeapify");
//        printList(A);
    }

    //*******************************************************************
// Method Name: BuildMaxHeap
//
// Description: To build a max heap tree
//*******************************************************************
    void BuildMaxHeap(ArrayList<URL> A) {
//        System.out.println("BuildMaxHeap");
//        printList(A);
//        System.out.println();
        int heapSize = A.size();
        for (int i = (A.size() / 2 - 1); i >= 0; i--) {
            MaxHeapify(A, i);
        }
//        System.out.println("After BuildMaxHeap");
//        printList(A);
    }

    //*******************************************************************
// Method Name: HeapSort1\
//
// Description: Sort the elements in the array in ascending order
//*******************************************************************
    void HeapSort(ArrayList<URL> A) {
//        System.out.println("Before HeapSort");
//        printList(A);
//        System.out.println();

        BuildMaxHeap(A);
        //MaxHeap is build, should make a copy of it, before it swap?
        for (int i = A.size() - 1; i > 0; i--) {
            Collections.swap(A, i, 0);
            heapSize -= 1;
            MaxHeapify(A, 0);
        }
//        System.out.println("After HeapSort");
//        printList(A);
    }

    //*******************************************************************
// Method Name: HeapExtractMax
//
// Description: Extract the largest(root) from a heap tree
//*******************************************************************
    URL HeapExtractMax(ArrayList<URL> A) {
//        System.out.println("Before HeapExtractMax");
//        printList(A);
//        System.out.println();
        if (heapSize < 1) {
            throw new RuntimeException("Heap underflow");
        }
        URL max = HeapMaximum(A);
        A.set(0, A.get(heapSize - 1));
        heapSize -= 1;
        MaxHeapify(A, 0);
//        System.out.println("After HeapExtractMax");
//        printList(A);
        return max;
    }

    //*******************************************************************
// Method Name: HeapIncreaseKey
//
// Description: After insert a new element to the heap, sort it again to maintain the property of max heap
//*******************************************************************
    void HeapIncreaseKey(ArrayList<URL> A, int i, URL key) {
//        System.out.println("HeapIncreaseKey");
//        printList(A);
//        System.out.println();
        i -= 1;
        if (key.getTotalScore() < A.get(i).getTotalScore()) {
            throw new RuntimeException("New key is smaller than current key");
        }
        A.set(i, key);
        while (i > 0 && A.get(getParent(i)).getTotalScore() < A.get(i).getTotalScore()) {
            Collections.swap(A, i, getParent(i));
            i = getParent(i);
        }
//        System.out.println("After HeapIncreaseKey");
//        printList(A);
    }

    //*******************************************************************
// Method Name: MaxHeapInsert
//
// Description: Insert a new element to the max heap
//*******************************************************************
    void MaxHeapInsert(ArrayList<URL> A, URL key) {
//        System.out.println("Before MaxHeapInsert");
//        printList(A);
//        System.out.println();
        heapSize += 1;
        A.add(key);//set the leaf first
        A.get(heapSize - 1).setTotalScore((int) Double.NEGATIVE_INFINITY);
        HeapIncreaseKey(A, heapSize, key);//then give the leaf real value
//        System.out.println("After MaxHeapInsert");
//        printList(A);
    }

    //*******************************************************************
// Method Name: setHeapSize
//
// Description: set the heap size back to the size of the arraylist
//*******************************************************************
    void setHeapSize(ArrayList<URL> A) {
        heapSize = A.size();
    }

    //*******************************************************************
// Method Name: HeapMaximum
//
// Description: Accessor to get the largest element(root) of the mas heap
//*******************************************************************
    URL HeapMaximum(ArrayList<URL> A) {
        //System.out.println("HeapMaximum: "+ A.get(0).getTotalScore()+" "+A.get(0).getUrl());
        return A.get(0);

    }

    //*******************************************************************
// Method Name: getParent
//
// Description: Accessor to get the index of the parent node
//*******************************************************************
    int getParent(int i) {
        return (i - 1 / 2);
    }

    //*******************************************************************
// Method Name: getLeft
//
// Description: Accessor to get the index of the left child node
//*******************************************************************
    int getLeft(int i) {
        return (2 * i + 1);
    }

    //*******************************************************************
// Method Name: getRight
//
// Description: Accessor to get the index of the right child node
//*******************************************************************
    int getRight(int i) {
        return (2 * i + 2);
    }

    //*******************************************************************
// Method Name: printList
//
// Description: to print all the elements in  the list
// put it in the heap sort file for the convenience of give every method's output
//*******************************************************************
    void printList(ArrayList<URL> A) {
        for (URL temp : A) {
            System.out.println("Score: " + temp.getTotalScore() + " URL: " + temp.getUrl());
        }
    }

}