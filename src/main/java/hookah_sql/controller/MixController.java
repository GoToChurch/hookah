package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component("mixController")
public class MixController implements Controller {

    @FXML
    private Button randomMixButton;

    @FXML
    private Button mixListButton;

    @FXML
    private Button mixButton;

    @FXML
    private Button backButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        randomMixButton.setOnAction(event -> fxMain.openNewScene(randomMixButton, "/view/random_mix.fxml"));
        mixListButton.setOnAction(event -> fxMain.openNewScene(mixListButton, "/view/mix_list.fxml"));
        mixButton.setOnAction(event -> fxMain.openNewScene(mixButton, "/view/mix_detail.fxml"));
        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/main.fxml"));
    }
}

