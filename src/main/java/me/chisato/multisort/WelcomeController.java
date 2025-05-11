package me.chisato.multisort;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private ComboBox<String> distributionComboBox;

    @FXML
    private ComboBox<Integer> dataSizeComboBox;

    @FXML
    private void handleProceedButton() throws IOException {
        String distribution = distributionComboBox.getValue();
        int dataSize = dataSizeComboBox.getValue();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sorting.fxml"));

        // 传递数据！
        loader.setControllerFactory(c -> new SortController(distribution, dataSize));

        Parent root = loader.load();

        // 获取下一个页面的控制器
        SortController nextPageController = loader.getController();

        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.setMinHeight(800);
        stage.setMinWidth(1400);
        stage.setMaxWidth(1400);
        stage.setMaxHeight(800);
        stage.setScene(new Scene(root));
        stage.setTitle("数据处理页面");
        stage.show();
    }
}