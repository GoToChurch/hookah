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
    private TextField TabaccoCountTextField;

    @FXML
    private TextField HardnessTextField;

    @FXML
    private TextField TabaccosTextField;

    @FXML
    private TextField TastesTextField;

    @FXML
    private Label randomMixTabaccosValue;

    @FXML
    private Label randomMixFlavorsValue;

    @FXML
    private Label randomMixTastesValue;

    @FXML
    private Label randomMixHardnessValue;

    @FXML
    private Button BackButton;

    private Mix mix;

    private FxMain fxMain;

    @FXML
    private void initialize() {
        fxMain = Context.getInstance().getContext().getBean("fxMain", FxMain.class);
        mix = Context.getInstance().getContext().getBean("mix", Mix.class);
        BackButton.setOnAction(event -> fxMain.openNewScene(BackButton, "/view/mix.fxml"));
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

        if (TabaccoCountTextField.getText() == null || TabaccoCountTextField.getText().length() == 0) {
            errorMessage += "Tabacco count must be not null!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(FxMain.getPrimaryStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
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
        int tabaccoCount = Integer.parseInt(TabaccoCountTextField.getText());
        int hardness = Integer.parseInt(HardnessTextField.getText());
        List<String> tabaccoString = Stream.of(TabaccosTextField.getText().split(", ")).filter(taste -> taste != "").collect(Collectors.toList());
        List<String> tastes = Stream.of(TastesTextField.getText().split(", ")).filter(taste -> taste != "").collect(Collectors.toList());
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

