package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import hookah_sql.utils.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component("tabaccoListController")
public class TabaccoListController implements Controller {

    @FXML
    private TableView<Tabacco> tabaccoListTable;

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
    private Button allButton;

    @FXML
    private Button mustHaveButton;

    @FXML
    private Button blackBurnButton;

    @FXML
    private Button satyrButton;

    @FXML
    private Button darksideButton;

    @FXML
    private Button tangiersButton;

    @FXML
    private Button duftButton;

    @FXML
    private Button elementButton;

    @FXML
    private TextField tabaccoAddNameField;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.ALL));
        prepareTable();
        prepareFilterButtons();
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

    private void prepareFilterButtons() {
        allButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.ALL)));
        blackBurnButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.BLACKBURN)));
        darksideButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.DARKSIDE)));
        duftButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.DUFT)));
        elementButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.ELEMENT)));
        mustHaveButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.MUSTHAVE)));
        satyrButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.SATYR)));
        tangiersButton.setOnAction(event -> refresh(TabaccoDAO.getTabaccoList(TabaccoEnum.TANGIERS)));
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

    protected void refresh(ObservableList<Tabacco> tabaccoObservableList) {
        tabaccoListTable.refresh();
        tabaccoListTable.setItems(tabaccoObservableList);
    }

    @FXML
    private void handleDeleteTabacco() {
        Tabacco selectedTabacco = tabaccoListTable.getSelectionModel().getSelectedItem();

        if (selectedTabacco != null) {
            TabaccoDAO.delete(selectedTabacco);
            refresh(TabaccoDAO.getTabaccoList(Utils.getTobaccoEnumByName(selectedTabacco.getTabaccoName())));
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No tabacco selected");
            alert.setContentText("Please select tabacco in the table.");

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
                refresh(TabaccoDAO.getTabaccoList(Utils.getTobaccoEnumByName(selectedTabacco.getTabaccoName())));
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No tabacco selected");
            alert.setContentText("Please select tabacco in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewTabacco() {
        Tabacco tempTabacco = Utils.castTabaccoInSpecificClass(tabaccoAddNameField.getText());
        boolean okClicked = fxMain.openTabaccoEditDialog(tempTabacco);
        if (okClicked) {
            TabaccoDAO.add(tempTabacco);
            refresh(TabaccoDAO.getTabaccoList(Utils.getTobaccoEnumByName(tempTabacco.getTabaccoName())));
        }
    }
}
