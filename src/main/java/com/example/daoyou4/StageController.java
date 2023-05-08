package com.example.daoyou4;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageController{

    private HashMap<String, Stage> stages = new HashMap<>();    //通过hashmap存储窗口

    public void addStage(Stage stage,String name) {
        stages.put(name, stage);
    }

    public Stage getStage(String name) {
        return stages.get(name);
    }



    public void LoaderStage(String name,String resources) throws IOException{   //加载fxml文件，抛出异常
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resources));
        Pane tempPane= (Pane) loader.load();
        ControllerStage controllerStage = (ControllerStage) loader.getController();
        controllerStage.setStageController(this);
        Scene tempscene = new Scene(tempPane);
        Stage tempStage = new Stage();
        tempStage.setScene(tempscene);
        tempStage.setTitle(name);
        addStage(tempStage, name);
    }

    public boolean showStage(String name) {
        this.getStage(name).show();
        return true;
    }

    public boolean changeShowStage(String close,String showing) {
        this.getStage(close).close();
        showStage(showing);
        return true;
    }

    public boolean removeStage(String name) {
        if(this.removeStage(name) == true) {
            return true;
        }
        else {
            return false;
        }
    }


}