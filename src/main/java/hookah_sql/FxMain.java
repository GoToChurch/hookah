package hookah_sql;

import hookah_sql.controller.MixEditController;
import hookah_sql.controller.TabaccoEditController;
import hookah_sql.mix.Mix;
import hookah_sql.tabacco.Tabacco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component("fxMain")
public class FxMain extends Application {
    private static final Logger logger = LoggerFactory.getLogger(FxMain.class);

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting the app");

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Hookah");

        initRootLayout();

    }

    public void initRootLayout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error ocussed while initializating root layout");
        }
    }

    public void openNewScene(Button button, String window) {
        button.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(window));

        try {
            fxmlLoader.load();
            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error ocussed while opening new scene");
        }
    }

    public boolean openMixEditDialog(Mix mix) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/mix_edit_dialog.fxml"));

        try {
            fxmlLoader.load();
            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            MixEditController controller = fxmlLoader.getController();
            controller.setDialogStage(stage);
            controller.setMix(mix);

            stage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error ocussed while opening mix edit dialig");
            return false;
        }
    }

    public boolean openTabaccoEditDialog(Tabacco tabacco) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/tabacco_edit_dialog.fxml"));

        try {
            fxmlLoader.load();
            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            TabaccoEditController controller = fxmlLoader.getController();
            controller.setDialogStage(stage);
            controller.setTabacco(tabacco);

            stage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error ocussed while opening tabacco edit dialig");
            return false;
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
