package Utils;

import LeetCode.ListNode;

import java.util.*;

/**
 * 각종 사용하기 유용한 함수들을 리스트로 담음
 * 1. 소수 판별
 * 2. 최대 공약수/최소 공배수
 * 3. 실수 값 Equal
 */
public class Util {
    public static void main(String[] args){
        int[] arr = {8, 2, 1, 8, 11, 2, 33, 45};

        /*CountingSort.sort(arr);
        System.out.println(Arrays.toString(,arr));*/
        /*BinarySearchTree bst = new BinarySearchTree();
        {
            bst.add(12);
            bst.add(5); bst.add(3); bst.add(1); bst.add(7); bst.add(9);
            bst.add(15); bst.add(13); bst.add(14); bst.add(18); bst.add(16); bst.add(17); bst.add(21);bst.add(19);bst.add(23);
        }
        bst.print(BinarySearchTree.OrderType.IN_ORDER);
        bst.delete(15);
        bst.print(BinarySearchTree.OrderType.IN_ORDER);
        bst.delete(21);
        bst.print(BinarySearchTree.OrderType.IN_ORDER);
        *//*System.out.println("Pre-order");
        bst.print(BinarySearchTree.OrderType.PRE_ORDER);
        System.out.println("Post-order");
        bst.print(BinarySearchTree.OrderType.POST_ORDER);*/
    }

    public static void swap(int[] arr, int from, int to){
        int tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }

    /**
     * 소수 여부를 판별합니다.
     * @param number
     * @return true if number is prime
     */
    public static boolean IsPrime(long number){
        if(number <= 2L) return false;
        for(long i = 2L; i <= (long)Math.sqrt(number) ; ++i){
            if(number % i == 0)
                return false;
        }
        return true;
    }

    /**
     * 두 수의 최대 공약수를 반환합니다
     * @param a
     * @param b
     * @return gcd of a & b
     */
    public static long gcd(int a, int b){
        while(b != 0){
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return Math.abs(a);
    }

    /**
     * 두 수의 최소 공배수를 반환합니다.
     * @param a
     * @param b
     * @return lcm of a & b
     */
    public static long lcm(int a, int b){
        return (a * (long)b) / gcd(a, b);
    }

    /**
     * 두 실수가 기준 값보다 차가 적은지 확인
     * 근사값 이슈로 인한 함수
     * @param a
     * @param b
     * @return
     */
    public static boolean absoluteEqual(double a, double b){
        final double LIMIT = 1e-10;
        return Math.abs(a - b) < LIMIT;
    }

    public static class BubbleSort {
        public static void sort(int[] arr){
            for(int i = 0; i < arr.length - 1; ++i){
                for(int j = 1; j < arr.length - i; ++j){
                    if(arr[j - 1] > arr[j])
                        swap(arr, j - 1, j);
                }
            }
        }
    }

    public static class SelectionSort{
        public static void sort(int[] arr){
            for(int i = 0; i < arr.length; ++i){
                int minIndex = i;
                for(int j = i + 1; j < arr.length; ++j){
                    if(arr[minIndex] > arr[j])
                        minIndex = j;
                }
                if(minIndex != i)
                    swap(arr, minIndex, i);
            }
        }
    }

    public static int getMax(int[] arr){
        int ret = Integer.MIN_VALUE;
        for(var a : arr){
            if(a > ret)
                ret = a;
        }
        return ret;
    }

    public static class InsertionSort{
        public static void sort(int[] arr){
            for(int i = 1; i < arr.length; ++i){
                int nTmp = arr[i];
                int prev = i - 1;
                while(prev >= 0 && arr[prev] > nTmp){
                    arr[prev + 1] = arr[prev];
                    --prev;
                }
                arr[prev + 1] = nTmp;
            }
        }
    }

    public static class CountingSort{
        public static void sort(int[] arr){
            int[] counting = new int[getMax(arr) + 1];
            int[] ret = new int[arr.length];

            for(int i = 0; i < arr.length; ++i)
                ++counting[arr[i]];

            for(int i = 1; i < counting.length; ++i)
                counting[i] += counting[i - 1];

            for(int i = arr.length - 1; i >= 0 ; --i){
                ret[--counting[arr[i]]] = arr[i];
            }

            /*for(int i = 0; i < arr.length; ++i){
                ret[--counting[arr[i]]] = arr[i];
            }*/
            for(int i = 0; i < arr.length; ++i)
                arr[i] = ret[i];
        }
    }

    public static class QuickSort{
        public static void sort(int[] arr){
            quickSort(arr, 0, arr.length - 1);
        }

        private static void quickSort(int[] arr, int start, int end){
            if(start >= end)
                return;

            int pivot = start;
            int left = start + 1;
            int right = end;
            while(left <= right){
                while(left <= end && arr[pivot] >= arr[left]) ++left;
                while(start < right && arr[pivot] < arr[right]) --right;

                if(left < right)
                    swap(arr, left, right);
                else
                    swap(arr, pivot, right);
            }

            quickSort(arr, start, right - 1);
            quickSort(arr, right + 1, end);
        }
    }

    public static class MergeSort{
        public static void sort(int[] arr){
            mergeSort(arr, 0, arr.length - 1);
        }

        private static void mergeSort(int[] arr, int start, int end){
            if(start >= end)
                return;

            int mid = (start + end) >>> 1;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }

        private static void merge(int[] arr, int start, int mid, int end){
            int[] lArr = Arrays.copyOfRange(arr, start, mid + 1);
            int[] rArr = Arrays.copyOfRange(arr, mid + 1, end + 1);
            int ll = lArr.length;
            int rl = rArr.length;
            int i = 0, j = 0;
            int k = start;
            while(i < ll && j < rl){
                if(lArr[i] <= rArr[j]) arr[k++] = lArr[i++];
                else arr[k++] = rArr[j++];
            }

            while(i < ll) arr[k++] = lArr[i++];
            while(j < rl) arr[k++] = rArr[j++];
        }

    }

    public static class HeapSort{
        public static void sort(int[] arr){
            int n = arr.length;

            for(int i = n / 2 - 1; i >= 0; --i)
                heapify(arr, n, i);

            for(int i = n - 1; i > 0; --i){
                swap(arr, i, 0);
                heapify(arr, i, 0);
            }
        }

        private static void heapify(int[] arr, int n, int i){
            int parent = i;
            int lChild = i * 2 + 1;
            int rChild = i * 2 + 2;

            if(lChild < n && arr[parent] < arr[lChild]) parent = lChild;
            if(rChild < n && arr[parent] < arr[rChild]) parent = rChild;

            if(parent != i){
                swap(arr, parent, i);
                heapify(arr, n, parent);
            }
        }
    }

    public static class RadixSort{
        public static void sort(int[] arr){
            int maxValue = getMax(arr);

            for(int exp = 1; maxValue / exp > 0; exp *= 10)
                countingSort(arr, exp);
        }

        private static void countingSort(int[] arr, int exp){
            int[] buffer = new int[arr.length];
            int[] counting = new int[10];

            for(int i = 0 ; i < arr.length; ++i)
                counting[(arr[i] / exp) % 10]++;

            for(int i = 1 ; i < counting.length; ++i)
                counting[i] += counting[i - 1];

            for(int i = arr.length - 1; i >= 0; --i){
                buffer[--counting[(arr[i] / exp) % 10]] = arr[i];
            }
            for(int i = 0; i < arr.length; ++i)
                arr[i] = buffer[i];
            System.out.println(Arrays.toString(arr));
        }
    }

    public static class StackWithQueue{

        private int flag = 0;
        private Queue queue0 = new LinkedList();
        private Queue queue1 = new LinkedList();

        public void push(int value){
            if(flag == 0){
                queue1.add(value);
                while(!queue0.isEmpty()){
                    queue1.add(queue0.poll());
                }
                flag = 1;
            } else{
                queue0.add(value);
                while(!queue1.isEmpty()){
                    queue0.add(queue1.poll());
                }
                flag = 0;
            }
        }

        public int pop(){
            if(0 == flag)
                return (int)queue0.poll();
            return (int)queue1.poll();
        }

        public boolean isEmpty(){
            return queue0.isEmpty() && queue1.isEmpty();
        }
    }

    public static class QueueWithStack{

        private Stack stack0 = new Stack();
        private Stack stack1 = new Stack();

        public void push(int value){
            stack0.push(value);
        }

        public int pop(){
            if(stack1.isEmpty()){
                while(!stack0.isEmpty()){
                    stack1.push(stack0.pop());
                }
            }
            return (int)stack1.pop();
        }

        public boolean isEmpty(){
            return stack0.isEmpty() && stack1.isEmpty();
        }
    }
}
