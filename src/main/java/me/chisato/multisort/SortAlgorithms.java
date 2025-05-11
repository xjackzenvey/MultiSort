package me.chisato.multisort;
import javafx.application.Platform;
import javafx.scene.shape.Line;

import java.util.logging.Logger;
import java.util.List;

/*
 * @author Chisato
 * @date 2025 / 05 / 09
 * @description 排序算法类，不仅需要排序，排序时需要突出Swap，Swap时要通知Javafx前端
 */

public class SortAlgorithms {

    private static Logger logger = Logger.getLogger(SortAlgorithms.class.getName());

    public static void swap(int arr[], int i, int j, String AlgorithmName) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;


    }

    public static void updateUIOnSwap(int[] arr, int i, int j, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        Platform.runLater(() -> {
            List<Line> lines = linesList.get(containerIndex);
            if (i < lines.size() && j < lines.size()) {
                Line lineI = lines.get(i);
                Line lineJ = lines.get(j);
                double lengthI = arr[i] * 10.0;
                double lengthJ = arr[j] * 10.0;
                lineI.setEndX(lengthI);
                lineJ.setEndX(lengthJ);
            }
        });
    }

    public static void bubbleSortWithDelay(int[] arr, String algorithmName, int containerIndex, List<List<Line>> linesList) {

        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    SortAlgorithms.swap(arr, j, j + 1, algorithmName);
                    swapped = true;
                    updateUIOnSwap(arr, j, j + 1, algorithmName, containerIndex, linesList);
                    try {
                        Thread.sleep(100); // 延迟以允许UI更新
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!swapped) break;
        }

        onCompleteSorting(linesList.get(containerIndex));
    }

    public static void selectionSortWithDelay(int[] arr, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(arr, i, minIndex, algorithmName);
                updateUIOnSwap(arr, i, minIndex, algorithmName, containerIndex, linesList);
                try {
                    Thread.sleep(100); // 延迟以允许UI更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        onCompleteSorting(linesList.get(containerIndex));
    }

    public static void insertionSortWithDelay(int[] arr, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                swap(arr, j, j + 1, algorithmName);
                updateUIOnSwap(arr, j, j + 1, algorithmName, containerIndex, linesList);
                try {
                    Thread.sleep(100); // 延迟以允许UI更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                j--;
            }
            arr[j + 1] = key;
        }

        onCompleteSorting(linesList.get(containerIndex));
    }

    public static void shellSortWithDelay(int[] arr, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        int gap = arr.length / 2;
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j >= gap && arr[j - gap] > arr[j]) {
                    swap(arr, j - gap, j, algorithmName);
                    updateUIOnSwap(arr, j - gap, j, algorithmName, containerIndex, linesList);
                    try {
                        Thread.sleep(100); // 延迟以允许UI更新
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    j -= gap;
                }
            }
            gap /= 2;
        }

        onCompleteSorting(linesList.get(containerIndex));
    }

    public static void heapSortWithDelay(int[] arr, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapifyWithDelay(arr, arr.length, i, algorithmName, containerIndex, linesList);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i, algorithmName);
            updateUIOnSwap(arr, 0, i, algorithmName, containerIndex, linesList);
            try {
                Thread.sleep(100); // 延迟以允许UI更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heapifyWithDelay(arr, i, 0, algorithmName, containerIndex, linesList);
        }

        onCompleteSorting(linesList.get(containerIndex));
    }

    public static void heapifyWithDelay(int[] arr, int n, int i, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest, algorithmName);
            updateUIOnSwap(arr, i, largest, algorithmName, containerIndex, linesList);
            try {
                Thread.sleep(100); // 延迟以允许UI更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heapifyWithDelay(arr, n, largest, algorithmName, containerIndex, linesList);
        }
    }

    public static void quickSortWithDelay(int[] arr, int low, int high, String algorithmName, int containerIndex, List<List<Line>> linesList) {

        if (low < high) {
            int pi = partition(arr, low, high, algorithmName, containerIndex, linesList);
            // updateUIOnSwap(arr, low, pi, algorithmName, containerIndex, linesList);
            try {
                Thread.sleep(100); // 延迟以允许UI更新
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            quickSortWithDelay(arr, low, pi - 1, algorithmName, containerIndex, linesList);
            quickSortWithDelay(arr, pi + 1, high, algorithmName, containerIndex, linesList);
        }

        onCompleteSorting(linesList.get(containerIndex));
    }

    public static int partition(int[] arr, int low, int high, String algorithmName, int containerIndex, List<List<Line>> linesList) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j, algorithmName);
                updateUIOnSwap(arr, i, j, algorithmName, containerIndex, linesList);
                try {
                    Thread.sleep(100); // 延迟以允许UI更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        swap(arr, i + 1, high, algorithmName);
        updateUIOnSwap(arr, i + 1, high, algorithmName, containerIndex, linesList);
        return i + 1;


    }


    // 排序完成后更改线条的颜色！
    private static void onCompleteSorting(List<Line> lines) {
        Platform.runLater(() -> {
                for (Line line : lines) {
                    line.setStyle("-fx-stroke: #b0c4d8;"); // 设置线条颜色为绿色
                }
            }
        );
    }
}