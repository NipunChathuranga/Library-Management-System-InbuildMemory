package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DB;
import db.IssueDetail;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BookTM;
import util.IBookCartTM;
import util.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

public class IssueBookformController {
    public JFXTextField txtFieldIssueID;
    public JFXTextField txtFieldDate;
    public JFXComboBox<String> cmbMemberID;
    public JFXTextField txtFieldName;
    public JFXComboBox<String> cmbBookID;
    public JFXTextField txtFieldTitle;
    public JFXTextField txtFieldAuthor;
    public JFXButton btnNewIssue;
    public JFXButton btnIssueBook;
    public TableView<IBookCartTM> tblViewIssued;
    public JFXButton btnAdd;
    public AnchorPane anchrpaneIssueBook;
    public TableColumn clmnBookID;
    public TableColumn clmnTitle;
    public TableColumn clmnAuthor;
    public TableColumn clmnBtnDel;
    private ArrayList<BookTM> tempbooks = new ArrayList<>();

    public void initialize() {
        tblViewIssued.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookid"));
        tblViewIssued.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblViewIssued.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblViewIssued.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("delete"));

//        ObservableList<String> books = cmbBookID.getItems();
//        for (BookTM book : DB.booklist) {
//            if(book.getStatus().equals("Available")){
//                books.add(book.getBookid());
//            }
//        }

        ObservableList<String> members = cmbMemberID.getItems();
        for (MemberTM member : DB.memberlist) {
            members.add(member.getId());
        }

        tempbooks = new ArrayList<>();
        for (BookTM book : DB.booklist) {
            tempbooks.add(book.clone());
        }

        cmbMemberID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedmemberID = cmbMemberID.getSelectionModel().getSelectedItem();

                for (IssueDetail issueDetail:DB.issuedBookList){
                    if(issueDetail.getMemberId().equals(selectedmemberID)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning Alert");

                        // Header Text: null
                        alert.setHeaderText(null);
                        alert.setContentText("A member can borrow only one book at a time! ");

                        alert.showAndWait();
                    }
                }

                enableIssueBookButton();
//                for(BookTM bookTM:DB.booklist){
//                    if(bookTM.getStatus()=="NA"){
//                        if(bookTM.)
//                    }
//                }

                for (MemberTM member : DB.memberlist) {
                    if (member.getId().equals(selectedmemberID)) {
                        txtFieldName.setText(member.getName());
                        txtFieldName.setEditable(false);
                        break;
                    }
                }
            }
        });


        cmbBookID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedBookCode = cmbBookID.getSelectionModel().getSelectedItem();

                if (selectedBookCode == null) {

                    txtFieldAuthor.clear();
                    txtFieldTitle.clear();

                    btnAdd.setDisable(true);
                    return;
                }


                btnAdd.setDisable(false);
                for (BookTM bookTM : tempbooks) {
                    if (bookTM.getBookid().equals(selectedBookCode)) {
                        txtFieldTitle.setText((bookTM.getTitle()));
                        txtFieldAuthor.setText(bookTM.getAuthor());
                        txtFieldAuthor.setEditable(false);
                        txtFieldTitle.setEditable(false);

                    }
                }
            }
        });


    }


    public void btnBackToHome_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Mainform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrpaneIssueBook.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnNewIssue_OnAction(ActionEvent actionEvent) {
        ObservableList<String> books = cmbBookID.getItems();
        books.clear();
        for (BookTM book : DB.booklist) {
            if (book.getStatus().equals("Available")) {
                books.add(book.getBookid());
            }
        }



        reset();
        tempbooks = new ArrayList<>();
        for (BookTM item : DB.booklist) {
            tempbooks.add(item.clone());
        }

    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {

        String selectedItemCode = cmbBookID.getSelectionModel().getSelectedItem();

        ObservableList<IBookCartTM> details = tblViewIssued.getItems();
        Button btnDelete = new Button("Delete");


        IBookCartTM detailTM = new IBookCartTM(cmbBookID.getSelectionModel().getSelectedItem(),
                txtFieldTitle.getText(), txtFieldAuthor.getText(), btnDelete);
        for (BookTM bookTM : tempbooks) {
            if (bookTM.getBookid().equals(cmbBookID.getSelectionModel().getSelectedItem())) {
                bookTM.setStatus("NA");
            }
        }

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (BookTM tempbook : tempbooks) {
                    if (tempbook.getBookid().equals(detailTM.getBookid())) {

                        tempbook.setStatus("Available");
                        break;
                    }
                }
                tblViewIssued.getItems().remove(detailTM);


                enableIssueBookButton();
                if (tblViewIssued.getItems().size() == 1) {
                    btnAdd.setDisable(true);
                }
                cmbBookID.requestFocus();
                cmbBookID.getSelectionModel().clearSelection();
                tblViewIssued.getSelectionModel().clearSelection();
                btnAdd.setDisable(false);
            }

        });

        details.add(detailTM);
        enableIssueBookButton();
        cmbBookID.requestFocus();
        //cmbBookID.getSelectionModel().clearSelection();
        tblViewIssued.getSelectionModel().clearSelection();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Borrowing Limits");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(" You have reached your borrowing limit ! ");

        alert.showAndWait();

    }

    private void reset() {

        txtFieldDate.setText(String.valueOf(LocalDate.now()));
        btnIssueBook.setDisable(true);
        btnAdd.setDisable(true);
        txtFieldTitle.setEditable(false);
        txtFieldName.setEditable(false);
        txtFieldAuthor.setEditable(false);
        cmbBookID.getSelectionModel().clearSelection();
        cmbMemberID.getSelectionModel().clearSelection();
        tblViewIssued.getItems().clear();
        txtFieldName.clear();

        int maxOrderId = 0;
        for (IssueDetail issueDetail : DB.issuedBookList) {
            int issueId = Integer.parseInt(issueDetail.getIssueId().replace("I", ""));
            if (issueId > maxOrderId) {
                maxOrderId = issueId;
            }
        }
        maxOrderId++;
        if (maxOrderId < 10) {
            txtFieldIssueID.setText("I00" + maxOrderId);
        } else if (maxOrderId < 100) {
            txtFieldIssueID.setText("I0" + maxOrderId);
        } else {
            txtFieldIssueID.setText("I" + maxOrderId);
        }


    }


    private void enableIssueBookButton() {

        String selectedMember = cmbMemberID.getSelectionModel().getSelectedItem();
        int size = tblViewIssued.getItems().size();
        if (selectedMember == null || size == 0) {
            btnIssueBook.setDisable(true);
        } else {
            btnIssueBook.setDisable(false);
        }


    }


    public void btnIssueBook_OnAction(ActionEvent actionEvent) {
        IssueDetail issueDetail = new IssueDetail(txtFieldIssueID.getText(),
                LocalDate.now(),cmbBookID.getSelectionModel().getSelectedItem(),
                cmbMemberID.getSelectionModel().getSelectedItem(), txtFieldName.getText());
        DB.issuedBookList.add(issueDetail);
        DB.booklist = tempbooks;
        System.out.println(issueDetail);
        reset();


    }
}
