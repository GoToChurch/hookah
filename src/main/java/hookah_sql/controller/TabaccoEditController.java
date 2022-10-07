package hookah_sql.controller;

import hookah_sql.tabacco.Tabacco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TabaccoEditController implements Controller {

    @FXML
    private TextField tabaccoField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField flavorField;

    @FXML
    private TextField tastesField;

    @FXML
    private TextField hardnessField;

    @FXML
    private TextField descriptionField;

    private Tabacco tabacco;

    private boolean okClicked = false;

    private Stage dialogStage;

    @FXML
    void handleCancel(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void handleOk(ActionEvent event) {
        if (isInputValid()) {
            tabacco.setName(nameField.getText());
            tabacco.setFlavor(flavorField.getText());
            tabacco.setDescription(descriptionField.getText());
            tabacco.setTaste(tastesField.getText());
            tabacco.setHardness(Integer.parseInt(hardnessField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setTabacco(Tabacco tabacco) {
        this.tabacco = tabacco;

        tabaccoField.setText(tabacco.getTabaccoName());
        nameField.setText(tabacco.getName());
        flavorField.setText(tabacco.getFlavor());
        descriptionField.setText(tabacco.getDescription());
        tastesField.setText(tabacco.getTaste());
        hardnessField.setText(String.valueOf(tabacco.getHardness()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Поле 'Название' не должно быть пустым!\n";
        }
        if (flavorField.getText() == null || flavorField.getText().length() == 0) {
            errorMessage += "Поле 'Вкус' не должно быть пустым!\n";
        }
        if (tastesField.getText() == null || tastesField.getText().length() == 0) {
            errorMessage += "Поле 'Вкусовые качества' не должно быть пустым!\n";
        }

        if (hardnessField.getText() == null || hardnessField.getText().length() == 0) {
            errorMessage += "Поле 'Крепость' не должно быть пустым!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(hardnessField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Крепость должна быть числовым значением!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные значения");
            alert.setHeaderText("Пожалуйста, введите корректные значения в текстовые поля");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

