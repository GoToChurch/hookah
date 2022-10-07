package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.MixDAO;
import hookah_sql.mix.Mix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("mixListController")
public class MixListController implements Controller {

    @FXML
    protected TableView<Mix> mixListTable;

    @FXML
    private TableColumn<Mix, String> tabaccosColumn;

    @FXML
    private TableColumn<Mix, String> flavorsColumn;

    @FXML
    private Label mixCount;

    @FXML
    private Menu tabaccoFilterMenu;

    @FXML
    private Menu hardnessFilterMenu;

    @FXML
    private Menu tasteFilterMenu;

    @FXML
    private Label tabaccosValue;

    @FXML
    private Label flavorsValue;

    @FXML
    private Label tastesValue;

    @FXML
    private Label hardnessValue;

    @FXML
    private Button backButton;

    protected FxMain fxMain;

    public MixListController() {

    }

    @FXML
    private void initialize() {
        refresh();
        prepareFilterMenu();
        prepareTable();
    }

    protected void prepareTable() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        tabaccosColumn.setCellValueFactory(cellData -> cellData.getValue().namesProperty());
        flavorsColumn.setCellValueFactory(cellData -> cellData.getValue().flavorsProperty());

        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/mix.fxml"));

        showMixDetails(null);
        mixListTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showMixDetails(newValue)
        );
    }

    private void prepareFilterMenu() {
        for (MenuItem menuItem : tabaccoFilterMenu.getItems()) {
            menuItem.setOnAction(event -> refreshFromFilter((CheckMenuItem) menuItem));
        }

        for (MenuItem menuItem : tasteFilterMenu.getItems()) {
            menuItem.setOnAction(event -> refreshFromFilter((CheckMenuItem) menuItem));
        }

        for (MenuItem menuItem : hardnessFilterMenu.getItems()) {
            menuItem.setOnAction(event -> refreshFromFilter((CheckMenuItem) menuItem));
        }
    }

    protected void showMixDetails(Mix mix) {
        if (mix != null) {
            tabaccosValue.setText(mix.getNamesProperty());
            flavorsValue.setText(mix.getFlavorsProperty());
            tastesValue.setText(mix.getTasteProperty());
            hardnessValue.setText(String.valueOf(mix.getHardnessProperty()));
        } else {
            tabaccosValue.setText("");
            flavorsValue.setText("");
            tastesValue.setText("");
            hardnessValue.setText("");
        }
    }

    @FXML
    private void refresh() {
        mixListTable.refresh();

        ObservableList<Mix> listToAdd = MixDAO.getMixList();
        mixListTable.setItems(listToAdd);

        mixCount.setText(String.valueOf(Objects.requireNonNull(listToAdd).size()));

        cleanUpCheckBoxes(listToAdd);
    }

    private void refreshFromButtons() {
        mixListTable.refresh();

        ObservableList<Mix> listToAdd = MixDAO.getMixList(getAllChosenTabaccos(), getAllNotChosenTastes(), getAllChosenHardnesses());
        mixListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        mixCount.setText(String.valueOf(listToAdd.size()));
    }

    private void refreshFromFilter(CheckMenuItem menuItem) {
        mixListTable.refresh();

        ObservableList<Mix> listToAdd = MixDAO.getMixList(menuItem, mixListTable.getItems());

        mixListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        mixCount.setText(String.valueOf(listToAdd.size()));
    }

    private void cleanUpCheckBoxes(ObservableList<Mix> list) {
        Set<String> allTabaccosInTable = new HashSet<>();
        Set<String> allTastesInTable = new HashSet<>();
        Set<Integer> allHardnessesInTable = new HashSet<>();

        for (Mix mix : list) {
            for (String tabacco : mix.getAllTabaccosInMix()) {
                allTabaccosInTable.add(tabacco.toLowerCase());
            }
            allTastesInTable.addAll(Arrays.asList(mix.getTaste().split(", ")));
            allHardnessesInTable.add(mix.getHardness());
        }

        for (MenuItem menuItem : tabaccoFilterMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
            checkMenuItem.setSelected(allTabaccosInTable.contains(checkMenuItem.getText().toLowerCase()));
        }

        for (MenuItem menuItem : tasteFilterMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
            checkMenuItem.setSelected(allTastesInTable.contains(checkMenuItem.getText()));
        }

        for (MenuItem menuItem : hardnessFilterMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
            checkMenuItem.setSelected(allHardnessesInTable.contains(Integer.valueOf(checkMenuItem.getText())));
        }
    }

    private List<String> getAllChosenTabaccos() {
        List<String> tabaccoNames = new ArrayList<>();

        for (MenuItem menuItem : tabaccoFilterMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
            if (checkMenuItem.isSelected()) {
                tabaccoNames.add(checkMenuItem.getText());
            }
        }

        return tabaccoNames;
    }

    private List<String> getAllNotChosenTastes() {
        List<String> tastes = new ArrayList<>();

        for (MenuItem menuItem : tasteFilterMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
            if (!checkMenuItem.isSelected()) {
                tastes.add(checkMenuItem.getText());
            }
        }

        return tastes;
    }

    private ArrayList<Integer> getAllChosenHardnesses() {
        ArrayList<Integer> hardnesses = new ArrayList<>();

        for (MenuItem menuItem : hardnessFilterMenu.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
            if (checkMenuItem.isSelected()) {
                hardnesses.add(Integer.valueOf(checkMenuItem.getText()));
            }
        }

        return hardnesses;
    }

    @FXML
    private void clear() {
        ObservableList<Mix> listToAdd = FXCollections.observableArrayList(new ArrayList<>());

        mixListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        mixCount.setText(String.valueOf(listToAdd.size()));
    }

    @FXML
    private void handleDeleteMix() {
        Mix selectedMix = mixListTable.getSelectionModel().getSelectedItem();

        if (selectedMix != null) {
            boolean okClicked = fxMain.openDeleteConfirmDialog();
            if (okClicked) {
                MixDAO.delete(selectedMix);
                refreshFromButtons();
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Нет выделеннего объекта");
            alert.setHeaderText("Не выбран ни один микс");
            alert.setContentText("Пожалуйста, выберете микс из таблицы.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditMix() {
        Mix selectedMix = mixListTable.getSelectionModel().getSelectedItem();

        if (selectedMix != null) {
            boolean okClicked = fxMain.openMixEditDialog(selectedMix);
            if (okClicked) {
                MixDAO.update(selectedMix);
                refreshFromButtons();
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Нет выделеннего объекта");
            alert.setHeaderText("Не выбран ни один микс");
            alert.setContentText("Пожалуйста, выберете микс из таблицы.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewMix() {
        Mix tempMix = Context.getInstance().getContext().getBean("mix", Mix.class);;
        boolean okClicked = fxMain.openMixEditDialog(tempMix);
        if (okClicked) {
            MixDAO.add(tempMix);
            refreshFromButtons();
        }
    }
}

