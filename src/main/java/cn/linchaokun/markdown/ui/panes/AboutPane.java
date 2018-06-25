package cn.linchaokun.markdown.ui.panes;

import cn.linchaokun.markdown.controller.IndexController;
import cn.linchaokun.markdown.utils.Utilities;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

import java.io.IOException;

public class AboutPane extends StackPane {

    @FXML
    private WebView aboutWebView;
    @FXML
    private JFXButton backButton;

    public AboutPane(IndexController ui) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlView/panes/AboutPane.fxml"));
            fxmlLoader.setController(this);
            Parent root = (Region) fxmlLoader.load();
            getStylesheets().add("/css/JMarkPad.css");

            addListeners(ui);
            writeTextAreaText();

            getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addListeners(IndexController ui) {
        backButton.setOnAction(e -> {
            ui.drawersStack.setMouseTransparent(true);
            ui.drawersStack.toggle(ui.aboutDrawer);
        });
    }

    private void writeTextAreaText() {
        aboutWebView.getEngine().loadContent(Utilities.reparse("# 简介 \n" +
                "这是一个使用JavaFX创建的MarkDown编辑器项目.\n" +
                "FxMarkDown是一款简约的MarkDown文本编辑器，可以实时预览。\n" +
                "\n.\n\n" +
                "# 源代码\n" +
                "在下面的github存储库中查找完整的源代码,欢迎Star\n" +
                "https://github.com/hualuoyixiao/JavaFx-MarkDown\n\n" +
                "**感谢您使用FxMarkDown**"), "text/html");

    }


}