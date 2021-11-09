package ru.sapteh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.model.Admin;
import ru.sapteh.service.AdminService;
import ru.sapteh.service.UserService;
import ru.sapteh.model.User;

import java.io.IOException;
import java.util.Objects;


public class MainController {
    private final AdminService adminService;

    @FXML
    public TextField passwordText;
    @FXML
    public TextField loginText;
    @FXML
    public Label alertText;

    public MainController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        this.adminService = new AdminService(factory);
    }



    @FXML
    private void inputButton(ActionEvent actionEvent){
        Admin admin = new Admin(loginText.getText(), passwordText.getText());
        final boolean isExists = adminService.findAll().stream().anyMatch(admin::equals);

        if(isExists) {
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            openWindow();
        } else {
            alertText.setTextFill(Color.RED);
            alertText.setText("Логин или пароль не соответствует");
        }
    }

    public void exitButton(ActionEvent actionEvent){
        final Button source = (Button) actionEvent.getSource();
        source.getScene().getWindow().hide();
    }

    public void openWindow() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull((getClass().getResource("/views/list_users.fxml"))));
            Stage stage = new Stage();
            stage.setTitle("Table users");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
