package cn.linchaokun.markdown.ui.panes;

import cn.linchaokun.markdown.controller.IndexController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class OptionsPane extends StackPane {
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private JFXButton backButton;


    public OptionsPane(IndexController ui) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlView/panes/OptionsPane.fxml"));
            fxmlLoader.setController(this);
            Parent root = (Region) fxmlLoader.load();
            getStylesheets().add("/css/JMarkPad.css");

            addListeners(ui);

            colorPicker.setValue(ui.colorTheme);
            getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addListeners(IndexController ui) {
        colorPicker.setOnAction(t -> {
            ui.colorTheme = colorPicker.getValue();
            ui.refreshTheme();
        });

        backButton.setOnAction(e -> {
            ui.drawersStack.setMouseTransparent(true);
            ui.drawersStack.toggle(ui.optionsDrawer);
        });
    }
}