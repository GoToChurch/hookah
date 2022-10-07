package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.tabacco.Tabacco;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component("tabaccoDetailController")
public class TabaccoDetailController extends TabaccoListController implements Controller {

    @FXML
    private TextField searchField;

    @FXML
    void getTabaccoFromSearchField(ActionEvent event) {
        if (isInputValid()) {
            refresh(searchField.getText());
        }
    }

    @FXML
    private void initialize() {
        prepareTable();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (searchField.getText() == null || searchField.getText().length() == 0) {
            errorMessage += "Поисковый запрос не может быть пустым!!\n";
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

    private void refresh(String name) {
        tabaccoListTable.refresh();

        ObservableList<Tabacco> listToAdd = TabaccoDAO.getTabacco(name);
        tabaccoListTable.setItems(listToAdd);
    }
}


