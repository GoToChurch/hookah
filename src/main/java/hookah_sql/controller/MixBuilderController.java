package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.MixDAO;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.mix.Mix;
import hookah_sql.tabacco.Tabacco;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MixBuilderController  extends TabaccoListController implements Controller {
    @FXML
    private TextField searchField;

    @FXML
    private Label mixTabaccosValue;

    @FXML
    private Label mixFlavorsValue;

    @FXML
    private Label mixTastesValue;

    @FXML
    private Label mixHardnessValue;

    private Mix mix;

    @FXML
    private void initialize() {
        mix = Context.getInstance().getContext().getBean("mix", Mix.class);
        refresh();
        prepareTable();
        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/mix_list.fxml"));
        prepareFilterMenu();
        refreshMix();
    }

    private void showMixDetails(Mix mix) {
        if (!mix.getNames().equals("")) {
            mix.prepareProperties();
            mixTabaccosValue.setText(mix.getNamesProperty());
            mixFlavorsValue.setText(mix.getFlavorsProperty());
            mixTastesValue.setText(mix.getTasteProperty());
            mixHardnessValue.setText(String.valueOf(mix.getHardnessProperty() / mix.getTabaccoList().size()));
        } else {
            mixTabaccosValue.setText("");
            mixFlavorsValue.setText("");
            mixTastesValue.setText("");
            mixHardnessValue.setText("");
        }
    }

    private void refreshMix() {
        showMixDetails(mix);
    }

    @FXML
    private void getTabaccoFromSearchField(ActionEvent event) {
        if (isInputValid()) {
            refresh(searchField.getText());
        }
    }

    @FXML
    private void addNewTabaccoInMix() {
        Tabacco selectedTabacco = tabaccoListTable.getSelectionModel().getSelectedItem();

        if (selectedTabacco != null) {
            mix.addToMix(selectedTabacco);
            showMixDetails(mix);

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Нет выделеннего объекта");
            alert.setHeaderText("Не выбран ни один табак");
            alert.setContentText("Пожалуйста, выберете табак из таблицы.");

            alert.showAndWait();
        }
    }

    @FXML
    private void clearMix() {
        mix.clearMix();
        refreshMix();
    }

    @FXML
    private void saveMix() {
        if (!mix.getNames().equals("")) {
            mix.finilizeMix();
            mix.countHardness();
            MixDAO.add(mix);
        }
    }

    private void refresh(String name) {
        tabaccoListTable.refresh();

        ObservableList<Tabacco> listToAdd = TabaccoDAO.getTabaccoBySearchQuery(name);
        tabaccoListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        tabaccoCount.setText(String.valueOf(listToAdd.size()));
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

