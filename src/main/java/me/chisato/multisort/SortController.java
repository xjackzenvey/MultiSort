package me.chisato.multisort;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static me.chisato.multisort.SortAlgorithms.*;
import static me.chisato.multisort.utils.*;

public class SortController {

    @FXML
    private VBox container1, container2, container3, container4, container5, container6;

    // 这是第一个页面传过来用于表式数据分布的
    @FXML
    private String dataDistribution;

    // 这是第一个页面表示数据量的
    private int dataSize;


    private List<VBox> containers;
    private List<List<Line>> linesList;
    private ExecutorService executorService;
    private List<int[]> arrays;


    private int[] staticArray;
    private List<Line> staticLines;
    private int staticArraySize;

    // 盒子编号到算法名称的映射
    private String[] container2Algo = {"BubbleSort", "SelectionSort", "InsertionSort", "ShellSort", "HeapSort", "QuickSort"};


    // 重载构造函数接受第一个页面的数据
    public SortController(String dataDistribution, int dataSize) {
        this.dataDistribution = dataDistribution;
        this.dataSize = dataSize;
    }

    @FXML
    private void initialize() {

        executorService = Executors.newFixedThreadPool(8);

        containers = new ArrayList<>();
        containers.add(container1);
        containers.add(container2);
        containers.add(container3);
        containers.add(container4);
        containers.add(container5);
        containers.add(container6);

        linesList = new ArrayList<>();
        arrays = new ArrayList<>();

        this.staticArraySize = this.dataSize;

        // staticArray = generateRandomArray(20, 1, 20);
        // 根据欢迎窗口传入的数据分布生成数组
        staticArray = handleArray(dataDistribution, dataSize, 1, dataSize);

        staticLines = createLines(staticArray);



        int vboxNumber = 0;
        for (VBox container : containers) {
            // int[] arr = generateRandomArray(20, 1, 20);
            int[] arr = staticArray.clone();
            arrays.add(arr);
            List<Line> lines = createLines(arr);
            linesList.add(lines);
            lines.forEach(container.getChildren()::add);

            // 在Vbox中Line的下方添加文字
            String AlgoName = container2Algo[vboxNumber];
            Text AlgoNameLable = new Text(AlgoName);
            AlgoNameLable.getStyleClass().add("AlgoNameLable");
            container.getChildren().add(AlgoNameLable);
            vboxNumber++;
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

    @FXML
    private void startSorting() {
        if (executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(8);
        }

        executorService.submit(() -> bubbleSortWithDelay(arrays.get(0), container2Algo[0], 0, linesList));
        executorService.submit(() -> selectionSortWithDelay(arrays.get(1), container2Algo[1], 1, linesList));
        executorService.submit(() -> insertionSortWithDelay(arrays.get(2), container2Algo[2], 2, linesList));
        executorService.submit(() -> shellSortWithDelay(arrays.get(3), container2Algo[3], 3, linesList));
        executorService.submit(() -> heapSortWithDelay(arrays.get(4), container2Algo[4], 4, linesList));
        executorService.submit(() -> quickSortWithDelay(arrays.get(5),0, staticArraySize - 1, container2Algo[5], 5, linesList));

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




}