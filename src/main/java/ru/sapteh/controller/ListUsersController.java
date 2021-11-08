package ru.sapteh.controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.model.User;
import ru.sapteh.service.UserService;


public class ListUsersController {

    private final UserService userService;

    public ListUsersController(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        userService = new UserService(factory);
    }

    private final ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User,Integer> idColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, Integer> ageColumn;
    @FXML
    public Label labelId;
    @FXML
    public TextField textFieldFirstName;
    @FXML
    public TextField textFieldLastName;
    @FXML
    public TextField textFieldAge;

    @FXML
    private void initialize() {
        initData();

        idColumn.setCellValueFactory(u-> new SimpleObjectProperty<>(u.getValue().getId()));
        firstNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getLastName()));
        ageColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getAge()));
        userTableView.setItems(users);

        listenerTabUserDetails(null);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
            listenerTabUserDetails(newValue);
        });
    }

    private void initData(){
    users.addAll(userService.findAll());

}

@FXML
public void buttonUpdate(ActionEvent actionEvent){
    int selectIndex = userTableView.getSelectionModel().getSelectedIndex();
    final User user = userTableView.getSelectionModel().getSelectedItem();
    user.setFirstName(textFieldFirstName.getText());
    user.setLastName(textFieldLastName.getText());
    user.setAge(Integer.parseInt(textFieldAge.getText()));
    userTableView.getItems().set(selectIndex, user);
    userService.update(user);
    cleanTextField();


}

@FXML
public void buttonDelete(ActionEvent actionEvent){
    final User user = userTableView.getSelectionModel().getSelectedItem();
    userService.delete(user);
    userTableView.getItems().remove(user);
    cleanTextField();
    System.out.println("Delete user:" + user);
}

@FXML
public void buttonSave(ActionEvent actionEvent){
    User user = new User(textFieldFirstName.getText(), textFieldLastName.getText(), Integer.parseInt(textFieldAge.getText()));
    userService.save(user);
    userTableView.getItems().add(user);
    cleanTextField();
}

private void listenerTabUserDetails (User user){
    if (user != null) {
        labelId.setText(String.valueOf(user.getId()));
        textFieldFirstName.setText(user.getFirstName());
        textFieldLastName.setText(user.getLastName());
        textFieldAge.setText(String.valueOf(user.getAge()));
    }else {
        labelId.setText("");
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldAge.setText("");

}
}
private void cleanTextField() {

}
}