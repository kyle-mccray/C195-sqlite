package loginScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
    public static String DB_URL = "jdbc:sqlite:C:\\Users\\Kyle\\Documents\\Projects\\C195\\src\\my_db";
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        primaryStage.setScene(new Scene(root, 1280 , 720));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);

    }
}
