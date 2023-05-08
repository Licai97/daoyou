package com.example.daoyou4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class DetailController implements ControllerStage{
    StageController myController;

    @FXML
    public void exit(ActionEvent actionEvent) {
        myController.changeShowStage("details", "index");
    }

    @Override
    public void setStageController(StageController stagecontroller) {
        this.myController = stagecontroller;
    }
}