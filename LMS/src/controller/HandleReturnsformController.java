package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DB;
import db.IssueDetail;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BookTM;
import util.IBookCartTM;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class HandleReturnsformController {
    public JFXComboBox<String> cmbIssuedID;
    public JFXTextField txtFieldMemberName;
    public JFXTextField txtFieldIssuedDate;
    public JFXTextField txtFieldCurrentDate;
    public JFXTextField txtFieldFee;
    public JFXButton btnCalculateFee;
    public TableView<IssueDetail> tblViewHandleBooks;
    public TableColumn clmnIssueID;
    public TableColumn clmnMemID;
    public TableColumn clmnName;
    public TableColumn clmnIssuedDate;
    public AnchorPane anchrPaneHandleReturns;
    public TextField txtFieldSearch;
    public JFXButton btnDone;

    public void initialize() {

        tblViewHandleBooks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblViewHandleBooks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblViewHandleBooks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("membername"));
        tblViewHandleBooks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("issueDate"));

        ObservableList<IssueDetail> issueDetails = FXCollections.observableList(DB.issuedBookList);
        ObservableList<IBookCartTM> iBookCartTMS = FXCollections.observableList(DB.tblCart);
        tblViewHandleBooks.setItems(issueDetails);

        ObservableList<String> issuedids = cmbIssuedID.getItems();
        for (IssueDetail issueDetail : DB.issuedBookList) {
            issuedids.add(issueDetail.getIssueId());
        }

        cmbIssuedID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedIssueID = cmbIssuedID.getSelectionModel().getSelectedItem();
                for (IssueDetail issueDetail : DB.issuedBookList) {
                    if (issueDetail.getIssueId().equals(selectedIssueID)) {
                        txtFieldMemberName.setText(issueDetail.getMembername());
                        txtFieldIssuedDate.setText(String.valueOf(issueDetail.getIssueDate()));

                    }
                }
            }
        });


        txtFieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<IssueDetail> selectbook = FXCollections.observableArrayList();
                for (IssueDetail book : issueDetails) {

                    if (book.getIssueId().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getMemberId().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getMembername().toLowerCase().contains(newValue.toLowerCase())


                            ) {
                        selectbook.add(book);
                    }
                }
                tblViewHandleBooks.setItems(selectbook);
            }

        });


    }


    public void btnBackToHome_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Mainform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrPaneHandleReturns.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }


    public void btnCalculateFee_OnAction(ActionEvent actionEvent) {

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String issueDate = txtFieldIssuedDate.getText();
        String currentDate = txtFieldCurrentDate.getText();
        int validDate = 14;
        double fee = 0;
        try {
            Date dateBefore = myFormat.parse(issueDate);
            Date dateAfter = myFormat.parse(currentDate);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000 * 60 * 60 * 24));
            System.out.println("Dates between " + daysBetween);
            if (daysBetween > validDate) {
                int extentndceDays = (int) daysBetween - validDate;
                fee = extentndceDays * 15;

            }
            txtFieldFee.setText(fee + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnDone_OnAction(ActionEvent actionEvent) {
        for (IssueDetail issueDetail : DB.issuedBookList) {
            if (issueDetail.getIssueId().equals(cmbIssuedID.getSelectionModel().getSelectedItem())) {
                for (BookTM bookTM : DB.booklist) {
                    if (issueDetail.getBookid().equals(bookTM.getBookid())) {
                        bookTM.setStatus("Available");
                    }
                }
            }
        }

        tblViewHandleBooks.getItems().clear();
        cmbIssuedID.getSelectionModel().clearSelection();
        txtFieldMemberName.clear();
        txtFieldCurrentDate.clear();
        txtFieldIssuedDate.clear();
        txtFieldFee.clear();
    }
}
