package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component("tabaccoController")
public class TabaccoController implements Controller {

    @FXML
    private Button TabaccoListButton;

    @FXML
    private Button TabaccoButton;

    @FXML
    private Button BackButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        TabaccoListButton.setOnAction(event -> fxMain.openNewScene(TabaccoListButton, "/view/tabacco_list.fxml"));
        TabaccoButton.setOnAction(event -> fxMain.openNewScene(TabaccoButton, "/view/tabacco_detail.fxml"));
        BackButton.setOnAction(event -> fxMain.openNewScene(BackButton, "/view/main.fxml"));
    }
}
