package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.dao.MixDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component("mixDetailController")
public class MixDetailController extends MixListController implements Controller {

    @FXML
    private TextField searchField;

    @FXML
    private void initialize() {
        prepareTable();
    }

    @FXML
    private void getMixFromSearchField() {
        if (isInputValid()) {
            refresh(MixDAO.getMix(searchField.getText()));
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (searchField.getText() == null || searchField.getText().length() == 0) {
            errorMessage += "Search field must be not null!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
