package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.dao.MixDAO;
import hookah_sql.mix.Mix;
import javafx.collections.ObservableList;
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
            refresh(searchField.getText());
        }
    }

    private void refresh(String name) {
        mixListTable.refresh();

        ObservableList<Mix> listToAdd = MixDAO.getMix(name);
        mixListTable.setItems(listToAdd);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (searchField.getText() == null || searchField.getText().length() == 0) {
            errorMessage += "Поисковый запрос не может быть пустым!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Некорректные значения");
            alert.setHeaderText("Пожалуйста, введите корректное значание в поисковое поле");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
