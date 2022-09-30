package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component("mainController")
public class MainController implements Controller {

    @FXML
    private Button MixButton;

    @FXML
    private Button TabaccoButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        MixButton.setOnAction(event -> fxMain.openNewScene(MixButton, "/view/mix.fxml"));
        TabaccoButton.setOnAction(event -> fxMain.openNewScene(TabaccoButton, "/view/tabacco.fxml"));
    }
}
