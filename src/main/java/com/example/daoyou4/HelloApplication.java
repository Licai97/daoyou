package com.example.daoyou4;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static String indexViewID = "index";
    public static String indexViewRes = "index.fxml";
    public static String mindexViewID = "mindex";
    public static String mindexViewRes = "mindex.fxml";
    public static String detailsViewID = "details";
    public static String detailsViewRes = "details.fxml";

    public static String loginViewID = "login";
    public static String loginViewRes = "login.fxml";

    private StageController stageController;
    @Override
    public void start(Stage stage) throws IOException {
        stageController=new StageController();
        stageController.LoaderStage(loginViewID,loginViewRes);
        stageController.LoaderStage(indexViewID,indexViewRes);
        stageController.LoaderStage(mindexViewID,mindexViewRes);
        stageController.LoaderStage(detailsViewID,detailsViewRes);
        stageController.showStage(loginViewID);
    }

    public static void main(String[] args) {
        launch();
    }

}