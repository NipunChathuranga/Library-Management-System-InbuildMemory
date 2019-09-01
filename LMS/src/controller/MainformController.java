package controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class MainformController {

    public AnchorPane anchrpane_MainMenu;

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }

    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
        }
    }


    public void btnMember_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnMember_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void btnIssueBooks_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnIssueBooks_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void btnHandleR_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnHandleR_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void btnManageBooks_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnManageBooks_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void ManageMembers_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ManageMembersform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrpane_MainMenu.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void ManageBooks_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ManageBooksform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrpane_MainMenu.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }

    public void IssueBook_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/IssueBookform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrpane_MainMenu.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void HandleReturns_OnClick(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/HandleReturnsform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchrpane_MainMenu.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
}
