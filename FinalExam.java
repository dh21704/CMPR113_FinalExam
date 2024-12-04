/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Danny
 */
public class FinalExam extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Skate Shop");
        
        GridPane gridPane = new GridPane();
        
        ComboBox<String> decks = new ComboBox<>();
        decks.getItems().addAll("The Master Thrasher $60", "The Dictator $45", "The Street King $50");
        
        ComboBox<String> trucks = new ComboBox();
        trucks.getItems().addAll("7.75-inch axle $35", "8-inch axle $40", "8.5-inch axle $45");
        
        ComboBox<String> wheels = new ComboBox();
        wheels.getItems().addAll("51 mm $20", "55 mm $22", "58 mm $24");
        
        Label wheelsLabel = new Label("Wheels: ");
        Label truckLabel = new Label("Trucks:");
        Label decksLabel = new Label("Decks:");
        
        Button submitButton = new Button("Submit");
        
        gridPane.add(wheelsLabel, 0, 0);
        gridPane.add(wheels, 1, 0);
        
        gridPane.add(truckLabel, 0, 1);
        gridPane.add(trucks, 1, 1);
        
        gridPane.add(decksLabel, 0, 2);
        gridPane.add(decks, 1, 2);
        
        gridPane.add(submitButton, 2, 4);
        
        submitButton.setOnAction(e ->
        {
            String wheel = wheels.getValue();
            String deck = decks.getValue();
            String truck = trucks.getValue();
            
            double price = 0.0;
            
            if("The Master Thrasher $60".equals(deck))
            {
                price += 60.0;
            }
            else if ("The Dictator $45".equals(deck))
            {
                price += 45;
            }
            else
            {
                price += 50;
            }
            
            if("51 mm $20".equals(wheel))
            {
                price += 20;
            }
            else if ("55 mm $22".equals(wheel))
            {
                price += 22;
            }
            else
            {
                price += 24;
            }
            
            if("7.75-inch axle $35".equals(truck))
            {
                price += 35;
            }
            else if ("8-inch axle $40".equals(truck))
            {
                price += 40;
            }
            else
            {
                price += 45;
            }
            
            double salesTax = 0.06;
            
            double taxAdded = salesTax * price;
            
            double total = taxAdded + price;
            
            insertData(total, deck, wheel, truck);
        }
        );
        
        Scene scene = new Scene(gridPane, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void insertData(double price, String deck, String wheel, String truck)
    {
        String url = "INSERT INTO skateshop (price, deck, wheel, truck) VALUES (?, ?, ?, ?)";

        try(Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(url))            
        {
           pstmt.setFloat(1, (float) price);
           pstmt.setString(2, deck);
           pstmt.setString(3, wheel);
           pstmt.setString(4, truck);
           
           pstmt.executeUpdate();
           JOptionPane.showMessageDialog(null, "INFO HAS BEEN INSERTED\nDeck: " + deck
           + "\nTrucks: " + truck
                   + "\nWheels: " + wheel +
                   "\nPrice: " + price);
                    
        } catch(SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
                
                
    }
    
    private Connection connect()
{
    Connection conn = null;
    String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/FinalExam/skateshop.db";
    try {
        conn = DriverManager.getConnection(url);
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return conn;
}


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
