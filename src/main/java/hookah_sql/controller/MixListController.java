package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.MixDAO;
import hookah_sql.mix.Mix;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component("mixListController")
public class MixListController implements Controller {

    @FXML
    private TableView<Mix> MixListTable;

    @FXML
    private TableColumn<Mix, String> TabaccosColumn;

    @FXML
    private TableColumn<Mix, String> FlavorsColumn;

    @FXML
    private Label TabaccosValue;

    @FXML
    private Label FlavorsValue;

    @FXML
    private Label TastesValue;

    @FXML
    private Label HardnessValue;

    @FXML
    private Button BackButton;

    protected FxMain fxMain;

    public MixListController() {

    }

    @FXML
    private void initialize() {
        refresh(MixDAO.getMixList());
        prepareTable();
    }

    protected void prepareTable() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        TabaccosColumn.setCellValueFactory(cellData -> cellData.getValue().namesProperty());
        FlavorsColumn.setCellValueFactory(cellData -> cellData.getValue().flavorsProperty());

        BackButton.setOnAction(event -> fxMain.openNewScene(BackButton, "/view/mix.fxml"));

        showMixDetails(null);
        MixListTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showMixDetails(newValue)
        );
    }

    protected void showMixDetails(Mix mix) {
        if (mix != null) {
            TabaccosValue.setText(mix.getNamesProperty());
            FlavorsValue.setText(mix.getFlavorsProperty());
            TastesValue.setText(mix.getTasteProperty());
            HardnessValue.setText(String.valueOf(mix.getHardnessProperty()));
        } else {
            TabaccosValue.setText("");
            FlavorsValue.setText("");
            TastesValue.setText("");
            HardnessValue.setText("");
        }
    }

    @FXML
    private void handleDeleteMix() {
        Mix selectedMix = MixListTable.getSelectionModel().getSelectedItem();

        if (selectedMix != null) {
            MixDAO.delete(selectedMix);
            refresh(MixDAO.getMixList());
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No mix selected");
            alert.setContentText("Please select mix in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditMix() {
        Mix selectedMix = MixListTable.getSelectionModel().getSelectedItem();

        if (selectedMix != null) {
            boolean okClicked = fxMain.openMixEditDialog(selectedMix);
            if (okClicked) {
                MixDAO.update(selectedMix);
                refresh(MixDAO.getMixList());
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No mix selected");
            alert.setContentText("Please select mix in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewMix() {
        Mix tempMix = Context.getInstance().getContext().getBean("mix", Mix.class);;
        boolean okClicked = fxMain.openMixEditDialog(tempMix);
        if (okClicked) {
            MixDAO.add(tempMix);
            refresh(MixDAO.getMixList());
        }
    }

    protected void refresh(ObservableList<Mix> mixObservableList) {
        MixListTable.refresh();
        MixListTable.setItems(mixObservableList);
    }
}

