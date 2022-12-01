package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import hookah_sql.tabacco.TabaccosKnowledgeBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("tabaccosKnowledgeBaseController")
public class TabaccosKnowledgeBaseController {
    @FXML
    protected TableView<TabaccosKnowledgeBase> tabaccoListTable;

    @FXML
    private TableColumn<TabaccosKnowledgeBase, String> tabaccoColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Label knowledgeText;

    @FXML
    private Button backButton;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        refresh();
        prepareTable();
    }

    protected void prepareTable() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        tabaccoColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/tabacco.fxml"));

        showTabaccoDetails(null);
        tabaccoListTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showTabaccoDetails(newValue)
        );
    }

    private void showTabaccoDetails(TabaccosKnowledgeBase tabaccosKnowledgeBase) {
        if (tabaccosKnowledgeBase != null) {
            knowledgeText.setText(tabaccosKnowledgeBase.getDescription());
        } else {
            knowledgeText.setText("");
        }
    }

    @FXML
    protected void refresh() {
        tabaccoListTable.refresh();

        ObservableList<TabaccosKnowledgeBase> listToAdd = TabaccoDAO.getTabaccoKnowledgeList();
        tabaccoListTable.setItems(listToAdd);
    }

    private void refresh(String name) {
        tabaccoListTable.refresh();

        ObservableList<TabaccosKnowledgeBase> listToAdd = TabaccoDAO.getTabaccoKnowledgeBySearchQuery(name);
        tabaccoListTable.setItems(listToAdd);
    }

    @FXML
    void getTabaccoFromSearchField(ActionEvent event) {
        if (isInputValid()) {
            refresh(searchField.getText());
        }
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
}
