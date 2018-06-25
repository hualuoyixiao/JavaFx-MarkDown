package cn.linchaokun.markdown;

import cn.linchaokun.markdown.fxmlView.IndexView;
import cn.linchaokun.markdown.utils.JavaFxViewUtil;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.GUIState;
import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MarkdownApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen(){
            @Override
            public String getImagePath() {
                return "/images/javafx.png";
            }
        };
        launch(MarkdownApplication.class,IndexView.class,splashScreen,args);
    }

    @Override
    public void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
        super.beforeInitialView(stage, ctx);

        Scene scene = JavaFxViewUtil.getJFXDecoratorScene(stage,"",null,new AnchorPane());
        stage.setScene(scene);
        GUIState.setScene(scene);
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("hahah");
    }
}
