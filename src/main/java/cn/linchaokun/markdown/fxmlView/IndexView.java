package cn.linchaokun.markdown.fxmlView;

import cn.linchaokun.markdown.controller.IndexController;
import cn.linchaokun.markdown.utils.JavaFxViewUtil;
import com.jfoenix.controls.JFXDecorator;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Parent;

/** 
 * @ClassName: IndexView
 */
@FXMLView(value = "/fxmlView/Index.fxml")
public class IndexView extends AbstractFxmlView {
    public IndexView() throws Exception {
        GUIState.getStage().setTitle("MarkDown编辑器  By:埖落一笑");//修改标题国际化
    }

    @Override
    public Parent getView() {
        JFXDecorator decorator = JavaFxViewUtil.getJFXDecorator(GUIState.getStage(),GUIState.getStage().getTitle(),"/images/icon.jpg",super.getView());
        decorator.setOnCloseButtonAction(()->{System.exit(0);});

        IndexController.refreshThemeDecorator(decorator);
        return decorator;
    }


}
