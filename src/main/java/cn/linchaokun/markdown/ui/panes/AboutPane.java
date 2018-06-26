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
        aboutWebView.getEngine().loadContent(Utilities.reparse("# FxMarkDown编辑器\n" +
                "\n" +
                "![FxMarkDown编辑器](https://raw.githubusercontent.com/hualuoyixiao/JavaFx-MarkDown/master/src/main/resources/images/javafx.png)\n" +
                "\n" +
                "什么是FxMarkdown编辑器？FxMarkdown是一种使用Markdown轻量级的「标记语言」，通常为程序员群体所用，目前它已是全球最大的技术分享网站 GitHub 和技术问答网站 StackOverFlow 的御用书写格式。\n" +
                "\n" +
                "内置Markdown编辑器和使用指南，非技术类笔记用户，千万不要被「标记」、「语言」吓到，Markdown的语法十分简单，常用的标记符号不超过十个，用于日常写作记录绰绰有余，不到半小时就能完全掌握,具体语法可参考帮助示例或网上查找。\n" +
                "\n" +
                "---\n" +
                "## 感谢使用FxMarkDown编辑器\n"), "text/html");

    }


}