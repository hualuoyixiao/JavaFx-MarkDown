package cn.linchaokun.markdown.utils;

import cn.linchaokun.markdown.ui.MyTab;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.paint.Color;

public class HelpUtil {

    public static void creHelpDoc(String name , JFXTabPane tabPane, Color colorTheme,String text){
        MyTab examplesTab = new MyTab(name, tabPane, colorTheme);

        JFXTextArea textArea = new JFXTextArea();


        examplesTab.setTextArea(textArea);


        tabPane.getTabs().add(examplesTab);
        tabPane.getSelectionModel().select(examplesTab);
        textArea.setText(text);
    }
}
