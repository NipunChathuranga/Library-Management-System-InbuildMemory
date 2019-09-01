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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MemberTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;


public class ManageMemberformController {
    public JFXTextField txtFieldMemberID;
    public JFXTextField txtFieldName;
    public JFXTextField txtFieldAddress;
    public TableView<MemberTM> tblViewMembers;
    public TableColumn<MemberTM, String> clmnMemberID;
    public TableColumn<MemberTM, String> clmnName;
    public TableColumn<MemberTM, String> clmnAddress;
    public AnchorPane anchrpane_ManageMems;
    public JFXButton btnNewMember;
    public JFXButton btnDelete;
    public JFXButton btnSave;

    public void initialize() {

        txtFieldMemberID.setDisable(true);
        txtFieldAddress.setDisable(true);
        txtFieldName.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        ObservableList<MemberTM> members = FXCollections.observableList(DB.memberlist);
        tblViewMembers.setItems(members);

        tblViewMembers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberTM>() {
            @Override
            public void changed(ObservableValue<? extends MemberTM> observable, MemberTM oldValue, MemberTM newValue) {
                MemberTM selecteditem = tblViewMembers.getSelectionModel().getSelectedItem();
                if (selecteditem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;

                }

                btnSave.setText("Update");
                txtFieldMemberID.setText(selecteditem.getId());
                txtFieldAddress.setText(selecteditem.getAddress());
                txtFieldName.setText(selecteditem.getName());
                txtFieldMemberID.setDisable(false);
                txtFieldName.setDisable(false);
                txtFieldAddress.setDisable(false);


            }
        });

        tblViewMembers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblViewMembers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblViewMembers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
    }


    public void btnNewMember_OnAction(ActionEvent actionEvent) {
        txtFieldName.clear();
        txtFieldAddress.clear();
        txtFieldMemberID.clear();
        tblViewMembers.getSelectionModel().clearSelection();
        txtFieldName.setDisable(false);
        txtFieldAddress.setDisable(false);
        btnDelete.setDisable(false);
        btnSave.setDisable(false);

        int maxID = 0;
        for (MemberTM member : DB.memberlist) {

            int id = Integer.parseInt(member.getId().replace("M", ""));
            if (id > maxID) {
                maxID = id;
            }
        }

        maxID = maxID + 1;
        String id = "";
        if (maxID < 10) {
            id = "M00" + maxID;
        } else if (maxID < 100) {
            id = "M0" + maxID;
        } else {
            id = "M" + maxID;
        }
        txtFieldMemberID.setText(id);


    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this member?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            MemberTM selectedItem = tblViewMembers.getSelectionModel().getSelectedItem();
            tblViewMembers.getItems().remove(selectedItem);
        }


    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        if (btnSave.getText().equals("Save")) {
            ObservableList<MemberTM> memberTMS = tblViewMembers.getItems();
            memberTMS.add(new MemberTM(txtFieldMemberID.getText(),
                    txtFieldName.getText(), txtFieldAddress.getText()));
            btnNewMember_OnAction(actionEvent);

        } else {

            MemberTM selectedmember = tblViewMembers.getSelectionModel().getSelectedItem();
            selectedmember.setName(txtFieldName.getText());
            selectedmember.setAddress(txtFieldAddress.getText());
            tblViewMembers.refresh();
            btnNewMember_OnAction(actionEvent);


        }


    }

    public void btnBacktoHome_OnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Mainform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrpane_ManageMems.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
}
