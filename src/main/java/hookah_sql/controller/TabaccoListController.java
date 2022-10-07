package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import hookah_sql.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("tabaccoListController")
public class TabaccoListController implements Controller {

    @FXML
    protected TableView<Tabacco> tabaccoListTable;

    @FXML
    private TableColumn<Tabacco, String> nameColumn;

    @FXML
    private TableColumn<Tabacco, String> flavorColumn;

    @FXML
    private TableColumn<Tabacco, String> tabaccoColumn;

    @FXML
    private Button backButton;

    @FXML
    private Label tabaccoValue;

    @FXML
    private Label nameValue;

    @FXML
    private Label flavorValue;

    @FXML
    private Label tastesValue;

    @FXML
    private Label hardnessValue;

    @FXML
    private Label descriptionValue;

    @FXML
    private Menu tabaccoFilterMenu;

    @FXML
    private Menu hardnessFilterMenu;

    @FXML
    private Menu tasteFilterMenu;

    @FXML
    private Label tabaccoCount;

    @FXML
    private Button resetButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField tabaccoAddNameField;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        refresh();
        prepareTable();
        prepareFilterMenu();
    }

    protected void prepareTable() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        tabaccoColumn.setCellValueFactory(cellData -> cellData.getValue().tabaccoProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        flavorColumn.setCellValueFactory(cellData -> cellData.getValue().flavorProperty());

        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/tabacco.fxml"));

        showTabaccoDetails(null);
        tabaccoListTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showTabaccoDetails(newValue)
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

    protected void showTabaccoDetails(Tabacco tabacco) {
        if (tabacco != null) {
            tabaccoValue.setText(tabacco.getTabaccoProperty());
            nameValue.setText(tabacco.getNameProperty());
            flavorValue.setText(tabacco.getFlavorProperty());
            descriptionValue.setText(tabacco.getDescriptionProperty());
            tastesValue.setText(tabacco.getTasteProperty());
            hardnessValue.setText(String.valueOf(tabacco.getHardnessProperty()));
        } else {
            tabaccoValue.setText("");
            nameValue.setText("");
            flavorValue.setText("");
            descriptionValue.setText("");
            tastesValue.setText("");
            hardnessValue.setText("");
        }
    }

    @FXML
    private void refresh() {
        tabaccoListTable.refresh();

        ObservableList<Tabacco> listToAdd = TabaccoDAO.getTabaccoList(TabaccoEnum.ALL);
        tabaccoListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        tabaccoCount.setText(String.valueOf(listToAdd.size()));
    }

    private void refreshFromButtons() {
        tabaccoListTable.refresh();

        ObservableList<Tabacco> listToAdd = TabaccoDAO.getTabaccoList(
                getAllChosenTabaccos(), getAllNotChosenTastes(), getAllChosenHardnesses()
        );
        tabaccoListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        tabaccoCount.setText(String.valueOf(listToAdd.size()));
    }

    private void refreshFromFilter(CheckMenuItem menuItem) {
        tabaccoListTable.refresh();

        ObservableList<Tabacco> listToAdd = TabaccoDAO.getTabaccoList(menuItem, tabaccoListTable.getItems()
        );

        tabaccoListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        tabaccoCount.setText(String.valueOf(listToAdd.size()));
    }

    private void cleanUpCheckBoxes(ObservableList<Tabacco> list) {
        Set<String> allTabaccosInTable = new HashSet<>();
        Set<String> allTastesInTable = new HashSet<>();
        Set<Integer> allHardnessesInTable = new HashSet<>();

        for (Tabacco tabacco : list) {
            allTabaccosInTable.add(tabacco.getTabaccoName().toLowerCase());
            allTastesInTable.addAll(Arrays.asList(tabacco.getTaste().split(", ")));
            allHardnessesInTable.add(tabacco.getHardness());
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

    @FXML
    private void clear() {
        ObservableList<Tabacco> listToAdd = FXCollections.observableArrayList(new ArrayList<>());

        tabaccoListTable.setItems(listToAdd);

        cleanUpCheckBoxes(listToAdd);

        tabaccoCount.setText(String.valueOf(listToAdd.size()));
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
    private void handleDeleteTabacco() {
        Tabacco selectedTabacco = tabaccoListTable.getSelectionModel().getSelectedItem();

        if (selectedTabacco != null) {
            boolean okClicked = fxMain.openDeleteConfirmDialog();
            if (okClicked) {
                TabaccoDAO.delete(selectedTabacco);
                refreshFromButtons();
            }
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
    private void handleEditTabacco() {
        Tabacco selectedTabacco = tabaccoListTable.getSelectionModel().getSelectedItem();

        if (selectedTabacco != null) {
            boolean okClicked = fxMain.openTabaccoEditDialog(selectedTabacco);
            if (okClicked) {
                TabaccoDAO.update(selectedTabacco);
                refreshFromButtons();
            }

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
    private void handleNewTabacco() {
        if (!tabaccoAddNameField.getText().equals("")) {
            Tabacco tempTabacco = Utils.convertStringInTobacco(tabaccoAddNameField.getText());
            boolean okClicked = fxMain.openTabaccoEditDialog(tempTabacco);
            if (okClicked) {
                TabaccoDAO.add(tempTabacco);
                refreshFromButtons();
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Нет названия");
            alert.setHeaderText("Не введено название марки табака");
            alert.setContentText("Пожалуйста, введите название марки табака в текстовое поле.");

            alert.showAndWait();
        }
    }
}
