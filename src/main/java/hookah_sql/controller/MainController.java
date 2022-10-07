package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component("mainController")
public class MainController implements Controller {

    @FXML
    private Button mixButton;

    @FXML
    private Button tabaccoButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        mixButton.setOnAction(event -> fxMain.openNewScene(mixButton, "/view/mix.fxml"));
        tabaccoButton.setOnAction(event -> fxMain.openNewScene(tabaccoButton, "/view/tabacco.fxml"));
    }
}
