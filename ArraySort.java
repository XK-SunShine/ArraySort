package ArraySort;

import java.util.*;

public class ArraySort {
    private static int[] swap(int[] arr, int x, int y) {
        int temp;
        temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
        return arr;
    }

    public static void bubbleSort(int[] arr) {//冒泡排序
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1])
                    swap(arr, j, j + 1);
            }
    }

    public static void simpleSelectSort(int[] arr) {//简单选择排序
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[min])
                    min = j;
            }
            if (min != i)
                swap(arr, i, min);
        }
    }

    public static void insertSort(int[] arr) {//插入排序
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            while (i >= 1 && arr[i - 1] > temp) {
                arr[i] = arr[i - 1];
                i--;
            }
            arr[i] = temp;
        }
    }

    public static void quickSort(int[] arr) {//便于理解版快速排序
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int L, int R) {
        if (L < R) {
            int sure = partition(arr, L, R);
            quickSort(arr, L, sure - 1);
            quickSort(arr, sure + 1, R);
        }
    }

    private static int partition(int[] arr, int L, int R) {
        int sentinel = arr[L];
        int i = L, j = R + 1;
        while (true) {
            while (arr[--j] > sentinel) ;
            if (i >= j)
                return i;
            swap(arr, i, j);
            while (arr[++i] < sentinel) ;
            if (i >= j)
                return j;
            swap(arr, i, j);
        }
    }

    public static void shellSort(int[] arr) {//希尔排序
        for (int delta = arr.length/2; delta>=1; delta/=2){//对每个增量进行一次排序
            for (int i=delta; i<arr.length; i++){
                for (int j=i; j>=delta && arr[j]<arr[j-delta]; j-=delta){ //注意每个地方增量和差值都是delta
                    swap(arr,j-delta,j);
                }
            }//loop i
        }//loop delta
    }

    private static void adjustHeap(int []arr,int i,int length){
        int temp = arr[i];//先取出当前元素i
        for(int k=i*2+1;k<length;k=k*2+1){//从i结点的左子结点开始，也就是2i+1处开始
            if(k+1<length && arr[k]<arr[k+1]){//如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if(arr[k] >temp){//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            }else{
                break;
            }
        }
        arr[i] = temp;//将temp值放到最终的位置
    }
    public static void heapSort(int[] arr) {//堆排序
        //1.构建大顶堆
        for(int i=arr.length/2-1;i>=0;i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr,i,arr.length);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for(int j=arr.length-1;j>0;j--){
            swap(arr,0,j);//将堆顶元素与末尾元素进行交换
            adjustHeap(arr,0,j);//重新对堆进行调整
        }
    }


    public static void mergeSort(int[] arr) {//归并排序
        int[] temp =new int[arr.length];
        internalMergeSort(arr, temp, 0, arr.length-1);
    }
    private static void internalMergeSort(int[] a, int[] b, int left, int right){
        if (left<right){
            int middle = (left+right)/2;
            internalMergeSort(a, b, left, middle);          //左子数组
            internalMergeSort(a, b, middle+1, right);       //右子数组
            mergeSortedArray(a, b, left, middle, right);    //合并两个子数组
        }
    }
    // 合并两个有序子序列 arr[left, ..., middle] 和 arr[middle+1, ..., right]
    private static void mergeSortedArray(int arr[], int temp[], int left, int middle, int right){
        int i=left;
        int j=middle+1;
        int k=0;
        while ( i<=middle && j<=right){
            if (arr[i] <=arr[j]){
                temp[k++] = arr[i++];
            }
            else{
                temp[k++] = arr[j++];
            }
        }
        while (i <=middle){
            temp[k++] = arr[i++];
        }
        while ( j<=right){
            temp[k++] = arr[j++];
        }
        for (i=0; i<k; ++i){
            arr[left+i] = temp[i];
        }
    }

    public static void countSort(int[] arr, int[] sorted) {//计数排序
        int max = Arrays.stream(arr).max().getAsInt();
        int[] temp = new int[max + 1];
        for (int i = 0; i <= max; i++)
            temp[i] = 0;
        for (int j = 0; j < arr.length; j++)
            temp[arr[j]]++;
        for (int i = 1; i <= max; i++)
            temp[i] = temp[i] + temp[i - 1];
        for (int j = arr.length - 1; j >= 0; j--) {
            sorted[temp[arr[j]] - 1] = arr[j];
            temp[arr[j]]--;
        }
    }

    public static void bucketSort(int[] arr) {//桶排序
        int bucketCount = 10;
        Integer[][] bucket = new Integer[bucketCount][arr.length];  //Integer初始为null,以与数字0区别。
        for (int i = 0; i < arr.length; i++) {
            int quotient = arr[i] / 10;
            for (int j = 0; j < arr.length; j++) {
                if (bucket[quotient][j] == null) {
                    bucket[quotient][j] = arr[i];
                    break;
                }
            }
        }
        for (int i = 0; i < bucket.length; i++) {
            for (int j = 1; j < bucket[i].length; ++j) {
                if (bucket[i][j] == null) {
                    break;
                }
                int value = bucket[i][j];
                int position = j;
                while (position > 0 && bucket[i][position - 1] > value) {
                    bucket[i][position] = bucket[i][position - 1];
                    position--;
                }
                bucket[i][position] = value;
            }

        }
        for (int i = 0, index = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i].length; j++) {
                if (bucket[i][j] != null) {
                    arr[index] = bucket[i][j];
                    index++;
                } else {
                    break;
                }
            }
        }
    }

    private static int getFigure(int i,int k)
    {
        int[] a = {1,10,100};
        return (i/a[k-1])%10;
    }
    public static void radixSort(int[] arr) {//基数排序
        int max=0;//max为位数
        int temp=Arrays.stream(arr).max().getAsInt();
        while(temp>0){
            temp/=10;
            max++;
        }
        int[] count = new int[arr.length];
        int[] bucket = new int[arr.length];
        for (int k = 1; k <= max; k++) {//k表示第几位，1代表个位，2代表十位，3代表百位
            for (int i = 0; i < arr.length; i++) {
                count[i] = 0;
            }
            for (int i = 0; i < arr.length; i++) {
                count[getFigure(arr[i], k)]++;
            }
            for (int i = 1; i < arr.length; i++) {
                count[i] = count[i] + count[i - 1];
            }
            for (int i = arr.length - 1; i >= 0; i--) {
                int j = getFigure(arr[i], k);
                bucket[count[j] - 1] = arr[i];
                count[j]--;
            }
            for (int i = 0, j = 0; i < arr.length; i++, j++) {
                arr[i] = bucket[j];
            }
        }
    }
}

/*附录：
public static void quickSort(int[] arr, int L, int R) {//随机快速排序，快速排序改良版
        if(L < R) {
            // 把数组中随机的一个元素与最后一个元素交换，这样以最后一个元素作为基准值实际上就是以数组中随机的一个元素作为基准值
            swap(arr, new Random().nextInt(R - L + 1) + L, R);
            int sure= partition(arr, L, R);
            quickSort(arr, L, sure - 1);
            quickSort(arr, sure + 1, R);
        }
    }

    public static int partition(int[] arr, int L, int R) {
        int sentinel=arr[R];
        int i=L-1;
        for(int j=L;j<=R-1;j++){
            if (arr[j]<=sentinel){
                ++i;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,R);
        return i+1;
    }
 */
/*
 public static class ArrayHeap {//堆排序类
        private int[] array;
        public ArrayHeap(int[] arr) {
            this.array = arr;
        }
        private int getParentIndex(int child) {
            return (child - 1) / 2;
        }
        private int getLeftChildIndex(int parent) {
            return 2 * parent + 1;
        }
        //初始化一个大顶堆
        private void initHeap() {
            int last = array.length - 1;
            for (int i = getParentIndex(last); i >= 0; --i) { // 从最后一个非叶子结点开始筛选
                int k = i;
                int j = getLeftChildIndex(k);
                while (j <= last) {
                    if (j < last) {
                        if (array[j] <= array[j + 1]) { // 右子节点更大
                            j++;
                        }
                    }
                    if (array[k] > array[j]) {           //父节点大于子节点中较大者，已经找到最终位置
                        break; // 停止筛选
                    } else {
                        swap(k, j);
                        k = j; // 继续筛选
                    }
                    j = getLeftChildIndex(k);
                }// loop while
            }// loop i
        }
        //调整堆。
        private void adjustHeap(int lastIndex) {
            int k = 0;
            while (k <= getParentIndex(lastIndex)) {
                int j = getLeftChildIndex(k);
                if (j < lastIndex) {
                    if (array[j] < array[j + 1]) {
                        j++;
                    }
                }
                if (array[k] < array[j]) {
                    swap(k, j);
                    k = j; // 继续筛选
                } else {
                    break; // 停止筛选
                }
            }
        }
        //堆排序。
        public void sort() {
            initHeap();
            int last = array.length - 1;
            while (last > 0) {
                swap(0, last);
                last--;
                if (last > 0) { // 这里如果不判断，将造成最终前两个元素逆序。
                    adjustHeap(last);
                }
            }
        }
        private void swap(int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
 */