package me.chisato.multisort;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SortController {

    @FXML
    private VBox container1, container2, container3, container4, container5, container6;

    private List<VBox> containers;
    private List<List<Line>> linesList;
    private ExecutorService executorService;
    private List<int[]> arrays;

    private Random random = new Random();

    @FXML
    private void initialize() {
        executorService = Executors.newFixedThreadPool(6);

        containers = new ArrayList<>();
        containers.add(container1);
        containers.add(container2);
        containers.add(container3);
        containers.add(container4);
        containers.add(container5);
        containers.add(container6);

        linesList = new ArrayList<>();
        arrays = new ArrayList<>();

        for (VBox container : containers) {
            int[] arr = generateRandomArray(20, 1, 20);
            arrays.add(arr);
            List<Line> lines = createLines(arr);
            linesList.add(lines);
            lines.forEach(container.getChildren()::add);
        }
    }

    private List<Line> createLines(int[] arr) {
        List<Line> lines = new ArrayList<>();
        for (int value : arr) {
            double length = value * 10.0;
            Line line = new Line(0, 0, length, 0);
            line.getStyleClass().add("line");
            lines.add(line);
        }
        return lines;
    }

    private int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = min + random.nextInt(max - min + 1);
        }
        return array;
    }

    @FXML
    private void startSorting() {
        if (executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(6);
        }

        executorService.submit(() -> bubbleSortWithDelay(arrays.get(0), "BubbleSort", 0));
        executorService.submit(() -> selectionSortWithDelay(arrays.get(1), "SelectionSort", 1));
        executorService.submit(() -> insertionSortWithDelay(arrays.get(2), "InsertionSort", 2));
        executorService.submit(() -> shellSortWithDelay(arrays.get(3), "ShellSort", 3));
        executorService.submit(() -> heapSortWithDelay(arrays.get(4), "HeapSort", 4));

        executorService.submit(() -> {
            try {
                executorService.shutdown();
                while (!executorService.isTerminated()) {
                    Thread.sleep(100);
                }
                Platform.runLater(() -> {
                    // 重新启用按钮等操作可以在这里执行
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateUIOnSwap(int[] arr, int i, int j, String algorithmName, int containerIndex) {
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

    private void bubbleSortWithDelay(int[] arr, String algorithmName, int containerIndex) {
        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    SortAlgoritms.swap(arr, j, j + 1, algorithmName);
                    swapped = true;
                    updateUIOnSwap(arr, j, j + 1, algorithmName, containerIndex);
                    try {
                        Thread.sleep(100); // 延迟以允许UI更新
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!swapped) break;
        }
    }

    private void selectionSortWithDelay(int[] arr, String algorithmName, int containerIndex) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                SortAlgoritms.swap(arr, i, minIndex, algorithmName);
                updateUIOnSwap(arr, i, minIndex, algorithmName, containerIndex);
                try {
                    Thread.sleep(100); // 延迟以允许UI更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void insertionSortWithDelay(int[] arr, String algorithmName, int containerIndex) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                SortAlgoritms.swap(arr, j, j + 1, algorithmName);
                updateUIOnSwap(arr, j, j + 1, algorithmName, containerIndex);
                try {
                    Thread.sleep(100); // 延迟以允许UI更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private void shellSortWithDelay(int[] arr, String algorithmName, int containerIndex) {
        int gap = arr.length / 2;
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j >= gap && arr[j - gap] > arr[j]) {
                    SortAlgoritms.swap(arr, j - gap, j, algorithmName);
                    updateUIOnSwap(arr, j - gap, j, algorithmName, containerIndex);
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
    }

    private void heapSortWithDelay(int[] arr, String algorithmName, int containerIndex) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapifyWithDelay(arr, arr.length, i, algorithmName, containerIndex);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            SortAlgoritms.swap(arr, 0, i, algorithmName);
            updateUIOnSwap(arr, 0, i, algorithmName, containerIndex);
            try {
                Thread.sleep(100); // 延迟以允许UI更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heapifyWithDelay(arr, i, 0, algorithmName, containerIndex);
        }
    }

    private void heapifyWithDelay(int[] arr, int n, int i, String algorithmName, int containerIndex) {
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
            SortAlgoritms.swap(arr, i, largest, algorithmName);
            updateUIOnSwap(arr, i, largest, algorithmName, containerIndex);
            try {
                Thread.sleep(100); // 延迟以允许UI更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heapifyWithDelay(arr, n, largest, algorithmName, containerIndex);
        }
    }
}