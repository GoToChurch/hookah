package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component("mixController")
public class MixController implements Controller {

    @FXML
    private Button RandomMixButton;

    @FXML
    private Button MixListButton;

    @FXML
    private Button MixButton;

    @FXML
    private Button BackButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        RandomMixButton.setOnAction(event -> fxMain.openNewScene(RandomMixButton, "/view/random_mix.fxml"));
        MixListButton.setOnAction(event -> fxMain.openNewScene(MixListButton, "/view/mix_list.fxml"));
        MixButton.setOnAction(event -> fxMain.openNewScene(MixButton, "/view/mix_detail.fxml"));
        BackButton.setOnAction(event -> fxMain.openNewScene(BackButton, "/view/main.fxml"));
    }
}

