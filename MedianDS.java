import java.io.IOException;

public class MedianDS {
    private MaxTreeHeap S;
    private MinTreeHeap L;


    public MedianDS (int[] A){

        int med_index = (A.length + 1)/2;
        int cur_med = select(A, med_index - 1);
        int[] max_heap = new int[med_index];
        int max_index = 0, min_index =0;
        int[] min_heap = new int [A.length - med_index];
        for (int i=0; i < A.length; i++){
            if (A[i] < cur_med)
                max_heap[max_index++] = A[i];
            else
                min_heap[min_index++] = A[i];
        }
        S = MaxTreeHeap.BuildHeapT(max_heap);
        L = MinTreeHeap.BuildHeapT(min_heap);
    }



    //returns index of k (k-element) in sorted array
    public static int partition (int[] A, int from, int to, int k){
        int n = to - from; // the length
        int[] B = new int[n];
        int start = 0, end = n - 1;
        for (int i = 0; i < n ; i++){
            int curr_elem = A[from + i];
            if (curr_elem > k)
                B[end--] = curr_elem;
            else if (curr_elem < k)
                B[start++] = curr_elem;
        }
        int index_of_k = start;
        B[index_of_k] = k;
        for (int i=0; i < n; i++)
            A[i + from] = B[i];
        return index_of_k + from;
    }


    //returns the element in k-th place in ordered array
    public static int select (int[] A, int med_index){
        if(A.length == 1)
            return A[0];
        int len = 0, index = 0, tail = 0;
        if (A.length % 5 == 0)
            len = A.length/5;
        else {
            len = A.length / 5 + 1;
            tail = A.length % 5;
        }
        int[] arr_of_med = new int[len];
        for (int start = 0; start < A.length; start = start + 5) {
            int size = 5;
            if (start + 5 > A.length )
                size = tail;
            int[] help_arr = new int[size];
            int i = 0;
            while (start + i < A.length && i < size){
                help_arr[i] = A[start + i];
                i++;
            }
            arr_of_med[index++] = naive_med(help_arr);
        }
        int median = select(arr_of_med,(arr_of_med.length + 1)/2 - 1);
        int q = partition(A, 0, A.length, median);
        if (q == med_index - 1)
            return q;
        else if (med_index < q){
            int[] arrCopy = new int[q];
            for (int j = 0; j < q; j++)
                arrCopy[j] = A[j];
             return select(arrCopy,median);
        }
        else {
            int[] arrCopy = new int[A.length - q - 1];
            for (int j = q + 1; j < A.length; j++)
                arrCopy[j - q - 1] = A[j];
            return select(arrCopy, median - q - 1);
        }
    }

    public static int naive_med(int[] arr){
        mergeSort(arr, 0, arr.length - 1);
        return arr[(arr.length+1)/2 - 1];
    }

    public static void mergeSort(int arr[], int left, int right)      
    {
        int middle;
        if (left < right) {
            middle = (left + right) / 2;

            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }
    public static void merge(int arr[], int left, int middle, int right)
    {
        int low = middle - left + 1;
        int high = right - middle;

        int L[] = new int[low];
        int R[] = new int[high];
        int i = 0, j = 0;
        for (i = 0; i < low; i++)
            L[i] = arr[left + i];
        for (j = 0; j < high; j++)
            R[j] = arr[middle + 1 + j];
        int k = left;
        i = 0;
        j = 0;
        while (i < low && j < high){
            if (L[i] <= R[j])
                arr[k] = L[i++];
            else
                arr[k] = R[j++];
            k++;
        }
        while (i < low)
            arr[k++] = L[i++];
        while (j < high)
            arr[k++] = R[j++];
    }

    public void insert(int x) throws IOException {
        if (x < findMedian()) {
            S.HeapInsert(x);

        } else {
            L.HeapInsert(x);
        }
        if (L.num_of_Nodes()== S.num_of_Nodes() + 1) {
            int r = L.HeapExtractMin();
            S.HeapInsert(r);
        }
        if (L.num_of_Nodes() == S.num_of_Nodes() - 2) {
            int r = S.HeapExtractMax();
            L.HeapInsert(r);
        }
    }

    public void delMedian() throws IOException{
        S.HeapExtractMax();
        if (L.num_of_Nodes() == S.num_of_Nodes()+1){
            int r=L.HeapExtractMin();
            S.HeapInsert(r);
        }
    }

    public int findMedian (){
        return S.ShowMax();
    }
}
