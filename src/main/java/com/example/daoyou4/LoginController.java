package com.example.daoyou4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements ControllerStage{
    StageController myController;
    @FXML
    private Label loginError;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    public void login(ActionEvent actionEvent) {
        myController.changeShowStage("login", "index");
    }
    @FXML
    public void mLogin(ActionEvent actionEvent) {
        if (username.getText().isBlank()||password.getText().isBlank()) {
            loginError.setText("用户名或密码不能为空");
            loginError.visibleProperty().setValue(true);
        }else if (username.getText().equals("admin")&&password.getText().equals("123456")) {
            loginError.visibleProperty().setValue(false);
            myController.changeShowStage("login", "mindex");
        }else {
            loginError.setText("用户名或密码错误");
            loginError.visibleProperty().setValue(true);
        }
    }

    @Override
    public void setStageController(StageController stagecontroller) {
        this.myController = stagecontroller;
    }
}