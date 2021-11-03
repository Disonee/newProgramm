package ru.sapteh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.service.UserService;
import ru.sapteh.model.User;


public class MainController {
    private final UserService userService;

    public MainController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        this.userService = new UserService(factory);
    }

    @FXML
    private TextField firstNameText;
    
    @FXML
    private TextField lastNameText;
    
    @FXML
    private TextField ageText;


    public void addedButton (ActionEvent actionEvent){
        userService.save(new User(
                firstNameText.getText(),
                lastNameText.getText(),
                Integer.parseInt(ageText.getText())
        ));
    }
    public void exitButton(ActionEvent actionEvent){
        final Button source = (Button) actionEvent.getSource();
        source.getScene().getWindow().hide();
    }
    @FXML
    public void initialize(){
        
    }
    
}
