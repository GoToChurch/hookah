package hookah_sql.controller;

import hookah_sql.FxMain;
import hookah_sql.config.Context;
import hookah_sql.dao.MixDAO;
import hookah_sql.mix.Mix;
import hookah_sql.mix.Mixer;
import hookah_sql.tabacco.*;
import hookah_sql.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("randomMixController")
public class RandomMixController implements Controller {

    @FXML
    private TextField tabaccoCountTextField;

    @FXML
    private TextField hardnessTextField;

    @FXML
    private TextField tabaccosTextField;

    @FXML
    private TextField tastesTextField;

    @FXML
    private Label randomMixTabaccosValue;

    @FXML
    private Label randomMixFlavorsValue;

    @FXML
    private Label randomMixTastesValue;

    @FXML
    private Label randomMixHardnessValue;

    @FXML
    private Button backButton;

    private Mix mix;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        mix = Context.getInstance().getContext().getBean("mix", Mix.class);
        backButton.setOnAction(event -> fxMain.openNewScene(backButton, "/view/mix.fxml"));
        randomMixTabaccosValue.setText("");
        randomMixFlavorsValue.setText("");
        randomMixTastesValue.setText("");
        randomMixHardnessValue.setText("");
    }

    @FXML
    private void saveMix() {
        if (mix != null) {
            MixDAO.add(mix);
        }
    }

    @FXML
    void getRandomMix(ActionEvent event) {
        if (isInputValid()) {
            createMix();
            randomMixTabaccosValue.setText(mix.getNames());
            randomMixFlavorsValue.setText(mix.getFlavors());
            randomMixTastesValue.setText(mix.getTaste());
            randomMixHardnessValue.setText(String.valueOf(mix.getHardness()));
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (tabaccoCountTextField.getText() == null || tabaccoCountTextField.getText().length() == 0) {
            errorMessage += "Количество табаков должно быть больше 0!\n";
        }
        if (hardnessTextField.getText() == null || hardnessTextField.getText().equals("")) {
            errorMessage += "Крепость должна быть числовой!\n";
        }
        else {
            try {
                Integer.parseInt(hardnessTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Крепость должна быть числовой!\n";
            }
            if (hardnessTextField.getText().equals("1") || hardnessTextField.getText().equals("2")) {
                errorMessage += "Крепость не может быть меньше 3 и больше 0!\n";
            }
            if (Integer.parseInt(hardnessTextField.getText()) < 0 || Integer.parseInt(hardnessTextField.getText()) > 10) {
                errorMessage += "Крепость не может быть меньше 0 и больше 10!\n";
            }
            if (Integer.parseInt(hardnessTextField.getText()) > 0 && !tabaccosTextField.getText().equals("")) {
                errorMessage += "Нельзя ввести крепость больше 0 и желаемые табаки одновременно!\n";
            }
        }
        if (!tastesTextField.getText().equals("")) {
            for (String taste : Utils.capitalize(tastesTextField.getText()).split(", ")) {
                if (!Utils.getAllTastes().contains(taste)) {
                    errorMessage += String.format("Вкуса '%s' нет в базе данных!\n", taste);
                    break;
                }
            }
        }

        if (!tabaccosTextField.getText().equals("")) {
            for (String tabacco : Utils.capitalize(tabaccosTextField.getText()).split(", ")) {
                if (!Utils.getAllTabaccos().contains(tabacco)) {
                    errorMessage += String.format("Табака '%s' нет в базе данных!\n", tabacco);
                    break;
                }
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Поля с некоректными значениями");
            alert.setHeaderText("Пожалуйста, введите корректные значения");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private void createMix() {
        Mixer mixer = Context.getInstance().getContext().getBean("mixer", Mixer.class);
        if (mix.getTabaccoList().size() != 0) {
            mix.clearMix();
        }
        int tabaccoCount = Integer.parseInt(tabaccoCountTextField.getText());
        int hardness = Integer.parseInt(hardnessTextField.getText());
        List<String> tabaccoString = Stream.of(Utils.capitalize(tabaccosTextField.getText()).split(", ")).filter(taste -> taste != "").collect(Collectors.toList());
        List<String> tastes = Stream.of(Utils.capitalize(tastesTextField.getText()).split(", ")).filter(taste -> taste != "").collect(Collectors.toList());
        Tabacco[] tabaccos = new Tabacco[tabaccoString.size()];
        if (tabaccoString.size() != 0) {
            for (int i = 0; i < tabaccoString.size(); i++) {
                tabaccos[i] = Utils.convertStringInTobacco(tabaccoString.get(i));
            }
        }

        if (hardness == 0 && tastes.size() == 0 && tabaccoString.size() == 0) {
            mix = mixer.mix(tabaccoCount);
        }
        else if (tastes.size() == 0 && tabaccoString.size() == 0 && hardness > 0) {
            mix = mixer.mix(tabaccoCount, hardness);
        }
        else if (tabaccoString.size() == 0 && hardness == 0 && tastes.size() > 0) {
            mix = mixer.mix(tabaccoCount, tastes);
        }
        else if (tabaccoString.size() == 0 && hardness > 0 && tastes.size() > 0) {
            mix = mixer.mix(tabaccoCount, hardness, tastes);
        }
        else if (tabaccos.length > 0 && tastes.size() < 1 && hardness == 0) {
            mix = mixer.mix(tabaccoCount, tabaccos);
        }
        else if (tastes.size() > 0 && tabaccos.length > 0 && hardness == 0) {
            mix = mixer.mix(tabaccoCount, tastes, tabaccos);
        }
        mix.finilizeMix();
    }

}

