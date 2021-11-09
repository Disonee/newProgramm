package ru.sapteh.controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.Text;
import ru.sapteh.model.User;
import ru.sapteh.service.UserService;


public class ListUsersController {

    private final UserService userService;
    private final ObservableList<User> users = FXCollections.observableArrayList();

    public ListUsersController(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        userService = new UserService(factory);
    }
    @FXML
    public TextField searchText;
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
    private Label countLbl;

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
        initUsersFromDatabase();

        searchByFirstName();

        userTableView.setItems(users);
        userTableView.setEditable(true);

        idColumn.setCellValueFactory(u-> new SimpleObjectProperty<>(u.getValue().getId()));

        firstNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getFirstName()));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(event ->{
            User user = event.getTableView().getItems().get(event.getTablePosition().getRow());
            user.setFirstName(event.getNewValue());
            userService.update(user);
        });

        lastNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getLastName()));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(event ->{
            User user = event.getTableView().getItems().get(event.getTablePosition().getRow());
            user.setLastName(event.getNewValue());
            userService.update(user);
        });

        ageColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getAge()));
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer age){
                return String.valueOf(age);
            }
            @Override
            public Integer fromString(String age) {
                return Integer.parseInt(age);
            }
        }));
        ageColumn.setOnEditCommit(event ->{
            User user = event.getTableView().getItems().get(event.getTablePosition().getRow());
            user.setAge(event.getNewValue());
            userService.update(user);
        });

        listenerTabUserDetails(null);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            listenerTabUserDetails(newValue);
        });

        countLbl.setText(String.valueOf(userTableView.getItems().size()));

        listenerTabUserDetails(null);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
            listenerTabUserDetails(newValue);
        });
    }

    private void initUsersFromDatabase(){
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
    countLbl.setText(String.valueOf(userTableView.getItems().size()));


}

@FXML
public void buttonDelete(ActionEvent actionEvent){
    final User user = userTableView.getSelectionModel().getSelectedItem();
    userService.delete(user);
    userTableView.getItems().remove(user);
    cleanTextField();
    System.out.println("Delete user:" + user);
    countLbl.setText(String.valueOf(userTableView.getItems().size()));
}

@FXML
public void buttonSave(ActionEvent actionEvent){
    User user = new User(textFieldFirstName.getText(), textFieldLastName.getText(), Integer.parseInt(textFieldAge.getText()));
    userService.save(user);
    userTableView.getItems().add(user);
    cleanTextField();
    countLbl.setText(String.valueOf(userTableView.getItems().size()));
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
    private void searchByFirstName() {
        searchText.textProperty().addListener((obs, old, newValue) -> {
            FilteredList<User> userFilteredList = new FilteredList<>(users,
                    s -> s.getFirstName().toLowerCase().contains(newValue.toLowerCase().trim()));
            userTableView.setItems(userFilteredList);
            countLbl.setText(String.valueOf(userFilteredList.size()));
        });
    }
private void cleanTextField() {
    textFieldFirstName.clear();
    textFieldLastName.clear();
    textFieldAge.clear();
    labelId.setText("");
}
}