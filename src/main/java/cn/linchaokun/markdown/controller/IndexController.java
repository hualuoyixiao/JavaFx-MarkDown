package cn.linchaokun.markdown.controller;

import cn.linchaokun.markdown.constant.HelpDocConstant;
import cn.linchaokun.markdown.io.XML;
import cn.linchaokun.markdown.ui.MyTab;
import cn.linchaokun.markdown.ui.panes.AboutPane;
import cn.linchaokun.markdown.ui.panes.OptionsPane;
import cn.linchaokun.markdown.utils.HelpUtil;
import cn.linchaokun.markdown.utils.SystemUtil;
import cn.linchaokun.markdown.utils.Utilities;
import cn.linchaokun.markdown.utils.VariablesToSave;
import com.jfoenix.controls.*;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class IndexController implements Initializable {

    private Stage stage;
    public Color colorTheme;
    public JFXDrawer optionsDrawer, aboutDrawer;
    private static String receivedPath = "";

    private XML xml;

    private static JFXDecorator jfDecorator;


    private static String colorStr;
    @FXML
    public JFXDrawersStack drawersStack;
    @FXML
    public JFXTabPane tabPane;
    @FXML
    public MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = GUIState.getStage();

        new Utilities();
        loadXMLValues();
        loadDrawers();

        if (tabPane.getTabs().size() < 1) {
            MyTab tab = new MyTab("New 1", tabPane, colorTheme);
            tabPane.getTabs().add(tab);
        }
        refreshTheme();
    }

    private void loadXMLValues() {
        try {
            xml = new XML("/"+SystemUtil.getUserHome() +"/jmarkpad.xml");

            stage.setWidth(Double.valueOf(xml.loadVariable("width")));
            stage.setHeight(Double.valueOf(xml.loadVariable("height")));
            colorTheme = new Color(Double.valueOf(xml.loadVariable("red")),
                    Double.valueOf(xml.loadVariable("green")),
                    Double.valueOf(xml.loadVariable("blue")), 1);

            for (String path : xml.loadVariables("file")) {
                MyTab tab = new MyTab(path.split("\\\\")[path.split("\\\\").length - 1], tabPane, colorTheme);
                File file = new File(path);
                try {
                    openFileIntoTab(file, tab);

                    tab.setFilePath(file.getAbsolutePath());

                    tabPane.getTabs().add(tab);
                    tabPane.getSelectionModel().select(tab);
                } catch (FileNotFoundException ignored) {

                }
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();

        } catch (NullPointerException e) {
            colorTheme = new Color(0.3921568691730499,
                    0.7098039388656616,
                    0.9647058844566345, 1);
        }
    }

    private void loadDrawers() {

        drawersStack.setMouseTransparent(true);

        FlowPane content = new FlowPane();

        StackPane optionsDrawerPane = new StackPane();
        optionsDrawer = new JFXDrawer();
        OptionsPane optionsPane = new OptionsPane(this);
        optionsDrawerPane.getChildren().add(optionsPane);
        optionsDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        optionsDrawer.setSidePane(optionsDrawerPane);
        optionsDrawer.setDefaultDrawerSize(150);
        optionsDrawer.setOverLayVisible(false);
        optionsDrawer.setResizableOnDrag(true);


        aboutDrawer = new JFXDrawer();

        AboutPane aboutPane = new AboutPane(this);
        StackPane aboutDrawerPane = new StackPane();
        aboutDrawerPane.getChildren().add(aboutPane);
        aboutDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        aboutDrawer.setSidePane(aboutDrawerPane);
        aboutDrawer.setDefaultDrawerSize(stage.getWidth());
        aboutDrawer.setOverLayVisible(false);
        aboutDrawer.setResizableOnDrag(true);

        drawersStack.setContent(content);

    }

    private void openFileIntoTab(File file, MyTab tab) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String text;

        JFXTextArea textArea = new JFXTextArea("");
        while ((text = bufferedReader.readLine()) != null) {
            textArea.appendText(text + "\n");
        }
        tab.setTextArea(textArea);
        bufferedReader.close();

    }

    public void refreshTheme() {
        String colorThemeString = Utilities.toRGB(colorTheme), colorThemeStringBrighter = Utilities.toRGB(colorTheme.brighter().brighter());
        colorStr = colorThemeString;

//        de.setStyle("-fx-decorator-color: " + colorThemeString + ";");
        menuBar.setStyle("-fx-background-color: " + colorThemeString + ";");
        tabPane.setStyle("tab-header-background: " + colorThemeStringBrighter + ";");

        if(jfDecorator!=null){
            jfDecorator.setStyle("-fx-decorator-color: " + colorStr + ";");
        }


        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            ((MyTab) tabPane.getTabs().get(i)).updateButtonColor(colorTheme);
        }
    }

    public static void refreshThemeDecorator(JFXDecorator decorator){
        decorator.setStyle("-fx-decorator-color: " + colorStr + ";");
        jfDecorator = decorator;

    }

    private boolean isFileIsAlreadyOpen(String filePath) {
        boolean result = false;
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            MyTab currentlyOpenTab = (MyTab) tabPane.getTabs().get(i);
            if (currentlyOpenTab.getFilePath().equals(filePath)) {
                tabPane.getSelectionModel().select(i);
                result = true;
            }
        }
        return result;
    }


    /**
     * 新建文件
     *
     * @param actionEvent
     */
    public void newClicked(ActionEvent actionEvent) {
        String newFileName = "";
        int counter = 1;
        boolean usedName;
        while (newFileName.equals("")) {
            usedName = false;
            for (int i = 0; i < tabPane.getTabs().size(); i++) {
                if (tabPane.getTabs().get(i).getText().contains("New " + counter)) {
                    usedName = true;
                    i = tabPane.getTabs().size();
                }
            }
            if (!usedName) {
                newFileName = "New " + counter;
            }
            counter++;
        }


        MyTab tab = new MyTab(newFileName, tabPane, colorTheme);

        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    /**
     * 打开文件
     *
     * @param actionEvent
     */
    public void openClicked(ActionEvent actionEvent) {
        File file = Utilities.fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (isFileIsAlreadyOpen(file.getAbsolutePath())) {
                return;
            }

            MyTab tab = new MyTab(file.getName(), tabPane, colorTheme);
            try {
                openFileIntoTab(file, tab);
                tab.setFilePath(file.getAbsolutePath());

                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 保存文件
     *
     * @param actionEvent
     */
    public void saveClicked(ActionEvent actionEvent) {
        ((MyTab) tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex())).checkSaveInCurrentPath();
    }

    /**
     * 另存为
     *
     * @param actionEvent
     */
    public void saveAsClicked(ActionEvent actionEvent) {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            ((MyTab) tabPane.getTabs().get(i)).saveAs();
        }
    }

    /**
     * 保存全部
     *
     * @param actionEvent
     */
    public void saveAllClicked(ActionEvent actionEvent) {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            ((MyTab) tabPane.getTabs().get(i)).checkSaveInCurrentPath();
        }
    }

    /**
     * 关闭退出
     *
     * @param actionEvent
     */
    public void closeClicked(ActionEvent actionEvent) {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            if (!((MyTab) tabPane.getTabs().get(selectedIndex)).isSaved) {
                ((MyTab) tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex())).checkIfUserWantsToSaveFile();
            }
            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
        } else {
            System.exit(0);
        }

    }


    /**
     * 选项
     *
     * @param actionEvent
     */
    public void optionsClicked(ActionEvent actionEvent) {
        drawersStack.toggle(optionsDrawer);
        drawersStack.setMouseTransparent(false);
    }

    /**
     * 帮助
     *
     * @param actionEvent
     */
    public void markDownHelpClicked(ActionEvent actionEvent) {

        HelpUtil.creHelpDoc("Examples",tabPane, colorTheme,HelpDocConstant.SIMPLE);


    }

    /**
     * 关于
     *
     * @param actionEvent
     */
    public void aboutClicked(ActionEvent actionEvent) {
        drawersStack.toggle(aboutDrawer);
        drawersStack.setMouseTransparent(false);
    }

    /**
     * 保存主题
     *
     * @param actionEvent
     */
    public void optionsSaveClicked(ActionEvent actionEvent) {
        String[] filePaths = new String[0];

        VariablesToSave variablesToSave = new VariablesToSave();

        variablesToSave.width = stage.getWidth();
        variablesToSave.height = stage.getHeight();
        variablesToSave.red = colorTheme.getRed();
        variablesToSave.green = colorTheme.getGreen();
        variablesToSave.blue = colorTheme.getBlue();
        variablesToSave.paths = filePaths;
        xml.writeVariables(variablesToSave);

    }


}
