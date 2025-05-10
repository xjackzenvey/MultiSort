package me.chisato.multisort;

/*
 * @author Chisato
 * @date 2025 / 05 / 09
 * @description 排序算法类，不仅需要排序，排序时需要突出Swap，Swap时要通知Javafx前端
 */

public class SortAlgoritms {

    // todo: 定义一个回调接口，用于通知 JavaFX 前端更新 UI
    public interface SwapCallback {
        void onSwap(int arr[], int i, int j, String algorithmName);
    }

    private static SwapCallback swapCallback;

    public static void setSwapCallback(SwapCallback callback) {
        swapCallback = callback;
    }

    public static void swap(int arr[], int i, int j, String AlgorithmName) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        // todo: 添加代码来通知 JavaFX 前端进行 UI 更新
        if (swapCallback != null) {
            swapCallback.onSwap(arr, i, j, AlgorithmName);
        }

    }

    public static void bubbleSort(int[] arr, int length) {
        String AlgorithmName = "BubbleSort";
        boolean swapped;
        for (int i = 0; i < length - 1; i++) {
            swapped = false;
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1, AlgorithmName); // 调用 swap 函数交换元素
                    swapped = true;
                }
            }
            // 如果没有发生交换，说明数组已经有序，提前退出
            if (!swapped) {
                break;
            }
        }
    }

    public static void selectionSort(int[] arr, int length) {
        String AlgorithmName = "SelectionSort";
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(arr, i, minIndex, AlgorithmName); // 调用 swap 函数交换元素
            }
        }
    }

    public static void insertionSort(int[] arr, int length) {
        String AlgorithmName = "InsertionSort";
        for (int i = 1; i < length; i++) {
            int key = arr[i];
            int j = i - 1;

            // 移动比key大的元素到右边一位
            while (j >= 0 && arr[j] > key) {
                swap(arr, j, j + 1, AlgorithmName); // 使用 swap 函数移动元素
                j--;
            }

            // 插入key到正确位置
            arr[j + 1] = key;
        }
    }

    public static void shellSort(int[] arr, int length) {
        String AlgorithmName = "ShellSort";
        int gap = length / 2; // 初始步长，设置为数组长度的一半

        while (gap > 0) {
            // 对每个步长 gap 的子数组进行插入排序
            for (int i = gap; i < length; i++) {
                int j = i;
                // 在子数组中进行比较，交换元素
                while (j >= gap && arr[j - gap] > arr[j]) {
                    swap(arr, j - gap, j, AlgorithmName); // 使用 swap 函数交换元素
                    j -= gap;
                }
            }
            gap /= 2; // 缩小步长
        }
    }

    public static void heapSort(int[] arr, int length) {

        String AlgorithmName = "HeapSort";

        // 构建最大堆
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(arr, length, i);
        }

        // 逐个将最大元素移到数组末尾
        for (int i = length - 1; i > 0; i--) {
            swap(arr, 0, i, AlgorithmName); // 将当前最大元素（堆顶）移到数组末尾
            heapify(arr, i, 0); // 重新调整剩余元素为最大堆
        }
    }

    // 调整堆结构，使其成为最大堆
    private static void heapify(int[] arr, int n, int i) {
        String AlgorithmName = "Heapify";
        int largest = i; // 初始化最大值为当前节点
        int left = 2 * i + 1; // 左子节点
        int right = 2 * i + 2; // 右子节点

        // 如果左子节点存在且大于当前最大值，则更新最大值
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点存在且大于当前最大值，则更新最大值
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是当前节点，则交换并递归调整子树
        if (largest != i) {
            swap(arr, i, largest, AlgorithmName);
            heapify(arr, n, largest);
        }
    }
}