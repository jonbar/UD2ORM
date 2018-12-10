/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
* Jonny
 */
package view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import controller.GestionListaEnMemoria;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import model.Person;
import org.xml.sax.SAXException;

/**
 *
 * @author idoia
 */
public class MainWindow extends Application {

    private final TableView<Person> table = new TableView<>();
    final HBox hb = new HBox();
    ObservableList<Person> data;

    int opzioa;

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, SQLException {
        Scene scene = new Scene(new Group());

        data = GestionListaEnMemoria.observableORM();

        stage.setTitle("Datuen Taula");
        stage.setWidth(530);
        stage.setHeight(550);
        final Label label = new Label("Pertsonak");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Person, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        idCol.setCellFactory(TextFieldTableCell.<Person, Integer>forTableColumn(new IntegerStringConverter()));
        idCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Person, Integer> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setId(t.getNewValue());
                });
        TableColumn<Person, String> firstNameCol
                = new TableColumn<>("Izena");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        firstNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Person, String> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setIzena(t.getNewValue());
                    Person person = table.getSelectionModel().getSelectedItem();
                    GestionListaEnMemoria.updateORM(1, t.getNewValue(), person.getId());
                });

        TableColumn<Person, String> lastNameCol
                = new TableColumn<>("Abizena");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        lastNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Person, String> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setAbizena(t.getNewValue());
                    Person person = table.getSelectionModel().getSelectedItem();
                    GestionListaEnMemoria.updateORM(2, t.getNewValue(), person.getId());
                });

        TableColumn<Person, String> emailCol = new TableColumn<>("eMaila");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        emailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Person, String> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setKorreoa(t.getNewValue());
                    Person person = table.getSelectionModel().getSelectedItem();
                    GestionListaEnMemoria.updateORM(3, t.getNewValue(), person.getId());
                });

        table.setItems(data);
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, emailCol);
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("izen");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("abizen");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("emaila");

        final Button addButton = new Button("Gehitu");
        addButton.setStyle("-fx-padding: 5 10 10 10; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #00b300 0%, #008000 100%), GREEN, GREEN, radial-gradient(center 50% 50%, radius 100%, #99ff99, #33ff33); -fx-font-weight: bold; -fx-font-size: 1em;");
        addButton.setOnAction((ActionEvent e) -> {
            if (addFirstName.getText() != null || addLastName.getText() != null || addEmail.getText() != null) {
                String addIzena = addFirstName.getText();
                String addAbizena = addLastName.getText();
                String addKorreoa = addEmail.getText();

                GestionListaEnMemoria.insertORM(addIzena, addAbizena, addKorreoa);

                addFirstName.clear();
                addLastName.clear();
                addEmail.clear();
            } else {
                JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
            }
        });

        final Button removeButton = new Button("Ezabatu");
        removeButton.setStyle("-fx-padding: 5 10 10 10; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: #990000, #990000, radial-gradient(center 50% 50%, radius 100%, #ff8080, #ff8080); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold; -fx-font-size: 1em;");
        removeButton.setOnAction((ActionEvent e) -> {
            Person person = table.getSelectionModel().getSelectedItem();
            data.remove(person);

            GestionListaEnMemoria.deleteORM(person);

        });

        hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
