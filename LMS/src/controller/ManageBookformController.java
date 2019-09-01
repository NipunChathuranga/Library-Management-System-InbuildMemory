package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BookTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageBookformController {
    public AnchorPane anchrPaneManageBooks;
    public JFXTextField txtFieldBookID;
    public JFXTextField txtFieldTitle;
    public JFXTextField txtFieldAuthor;
    public JFXTextField txtFieldPrice;
    public TableView<BookTM> tblViewBooks;
    public TableColumn<BookTM, String> clmnBookID;
    public TableColumn<BookTM, String> clmnTitle;
    public TableColumn<BookTM, String> clmnAuthor;
    public TableColumn<BookTM, Double> clmnPrice;
    public TableColumn<BookTM, String> clmnStatus;
    public JFXButton btnAdd;
    public JFXButton btnNewBook;
    public TextField txtFieldSearch;
    public JFXTextField txtFieldStatus;
    public JFXButton btnDeleteBook;

    public void initialize() {

        txtFieldBookID.setDisable(true);
        txtFieldAuthor.setDisable(true);
        txtFieldTitle.setDisable(true);
        txtFieldPrice.setDisable(true);
        txtFieldStatus.setDisable(true);
        txtFieldSearch.setDisable(true);
        btnDeleteBook.setDisable(true);

        btnAdd.setDisable(true);


        ObservableList<BookTM> books = FXCollections.observableList(DB.booklist);
        tblViewBooks.setItems(books);
        tblViewBooks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookid"));
        tblViewBooks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblViewBooks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblViewBooks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblViewBooks.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("status"));

        tblViewBooks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookTM>() {
            @Override
            public void changed(ObservableValue<? extends BookTM> observable, BookTM oldValue, BookTM newValue) {
                BookTM selectedbookdetail = tblViewBooks.getSelectionModel().getSelectedItem();
                if (selectedbookdetail == null) {
                    btnAdd.setText("Add");
                    btnAdd.setDisable(true);
                    return;
                } else {

                    btnAdd.setText("Update");
                    txtFieldAuthor.setDisable(false);
                    txtFieldPrice.setDisable(false);
                    txtFieldSearch.setDisable(false);
                    txtFieldStatus.setDisable(false);
                    txtFieldTitle.setDisable(false);
                    btnAdd.setDisable(false);
                    btnDeleteBook.setDisable(false);
                    txtFieldTitle.setText(selectedbookdetail.getTitle());
                    txtFieldAuthor.setText(selectedbookdetail.getAuthor());
                    txtFieldStatus.setText(selectedbookdetail.getStatus());
                    txtFieldPrice.setText(String.valueOf(selectedbookdetail.getPrice()));


                }


            }
        });


        txtFieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<BookTM> selectbook = FXCollections.observableArrayList();


                for (BookTM regbook : books) {
                    if (regbook.getBookid().toLowerCase().contains(newValue.toLowerCase()) ||
                            regbook.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                            regbook.getAuthor().toLowerCase().contains(newValue.toLowerCase())) {
                        selectbook.add(regbook);

                    }


                }

                tblViewBooks.setItems(selectbook);
            }
        });


    }


    public void btnAdd_OnAction(ActionEvent actionEvent) {

        try {
            if (btnAdd.getText().equals("Add")) {
                ObservableList<BookTM> books = tblViewBooks.getItems();
                books.add(new BookTM(txtFieldBookID.getText(), txtFieldTitle.getText(),
                        txtFieldAuthor.getText(), Double.parseDouble(txtFieldPrice.getText()),
                        txtFieldStatus.getText()));
                btnNewBook_OnAction(actionEvent);
            } else {

                BookTM selectedbook = tblViewBooks.getSelectionModel().getSelectedItem();
                selectedbook.setAuthor(txtFieldAuthor.getText());
                selectedbook.setTitle(txtFieldTitle.getText());
                selectedbook.setPrice(Double.parseDouble(txtFieldPrice.getText()));
                selectedbook.setStatus(txtFieldStatus.getText());
                tblViewBooks.refresh();
                btnNewBook_OnAction(actionEvent);


            }
        } catch (NumberFormatException e) {
            System.out.println("You must set values for the text fields.");
        }

    }

    public void btnNewBook_OnAction(ActionEvent actionEvent) {
        txtFieldSearch.clear();
        txtFieldStatus.clear();
        txtFieldPrice.clear();
        txtFieldAuthor.clear();
        txtFieldTitle.clear();
       // txtFieldStatus.setText("Available");
        txtFieldAuthor.setDisable(false);
        txtFieldPrice.setDisable(false);
        txtFieldSearch.setDisable(false);
        txtFieldStatus.setDisable(false);
        txtFieldTitle.setDisable(false);
        btnAdd.setDisable(false);

        int maxId = 0;
        for (BookTM books : DB.booklist) {
            int id = Integer.parseInt(books.getBookid().replace("B", ""));
            if (id > maxId) {
                maxId = id;
            }
        }

        maxId = maxId + 1;
        String id = "";
        if (maxId < 10) {
            id = "B00" + maxId;
        } else if (maxId < 100) {
            id = "B0" + maxId;
        } else {
            id = "B" + maxId;
        }
        txtFieldBookID.setText(id);


    }

    public void btnDeleteBook_OnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this book?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            BookTM selectedItem = tblViewBooks.getSelectionModel().getSelectedItem();
            tblViewBooks.getItems().remove(selectedItem);
        }
    }

    public void btnBacktoHome_OnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Mainform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrPaneManageBooks.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
}
