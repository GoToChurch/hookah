package hookah_sql.controller;

import hookah_sql.mix.Mix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component("mixEditController")
public class MixEditController implements Controller {

    @FXML
    private TextField tabaccosField;

    @FXML
    private TextField flavorsField;

    @FXML
    private TextField tastesField;

    @FXML
    private TextField hardnessField;

    private Mix mix;

    private boolean okClicked = false;

    private Stage dialogStage;

    @FXML
    void handleCancel(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void handleOk(ActionEvent event) {
        if (isInputValid()) {
            mix.setNames(tabaccosField.getText());
            mix.setFlavors(flavorsField.getText());
            mix.setTaste(tastesField.getText());
            mix.setHardness(Integer.parseInt(hardnessField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMix(Mix mix) {
        this.mix = mix;

        tabaccosField.setText(mix.getNames());
        flavorsField.setText(mix.getFlavors());
        tastesField.setText(mix.getTaste());
        hardnessField.setText(String.valueOf(mix.getHardness()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (tabaccosField.getText() == null || tabaccosField.getText().length() == 0) {
            errorMessage += "No valid tabaccos!\n";
        }
        if (flavorsField.getText() == null || flavorsField.getText().length() == 0) {
            errorMessage += "No valid flavors!\n";
        }
        if (tastesField.getText() == null || tastesField.getText().length() == 0) {
            errorMessage += "No valid tastes!\n";
        }

        if (hardnessField.getText() == null || hardnessField.getText().length() == 0) {
            errorMessage += "No valid hardness!\n";
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(hardnessField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid hardness (must be an integer)!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

