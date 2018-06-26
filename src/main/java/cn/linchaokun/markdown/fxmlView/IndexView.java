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

    @Override
    public Parent getView() {
        GUIState.getStage().setTitle("FxMarkDown编辑器  By:埖落一笑");//修改标题国际化
        JFXDecorator decorator = JavaFxViewUtil.getJFXDecorator(GUIState.getStage(),GUIState.getStage().getTitle(),"/images/icon.png",super.getView());
        decorator.setOnCloseButtonAction(()->{System.exit(0);});

        IndexController.refreshThemeDecorator(decorator);
        return decorator;
    }


}
