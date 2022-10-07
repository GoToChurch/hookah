package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component("tabaccoController")
public class TabaccoController implements Controller {

    @FXML
    private Button tabaccoListButton;

    @FXML
    private Button tabaccoButton;

    @FXML
    private Button backButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        tabaccoListButton.setOnAction(event -> fxMain.openNewScene(tabaccoListButton, "/view/tabacco_list.fxml"));
        tabaccoButton.setOnAction(event -> fxMain.openNewScene(tabaccoButton, "/view/tabacco_detail.fxml"));
        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/main.fxml"));
    }
}
