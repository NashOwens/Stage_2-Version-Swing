package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public abstract class DeleteProduct extends JFrame implements ActionListener {

    private static JTextField ID;
    private static JButton Submit;

    public static JFrame productDelSelc(JFrame menuWindow, Connection conn){


        menuWindow.setLayout(new GridBagLayout());
        menuWindow.setSize(400, 100);


        JLabel enterID = new JLabel("Enter item ID:");
        menuWindow.add(enterID);

        ID = new JTextField("", 10);
        menuWindow.add(ID);

        Submit = new JButton("Submit");
        menuWindow.add(Submit);

        Submit.addActionListener(e -> {
            if(e.getSource() == Submit) {
                try {
                    int productDel = parseInt(ID.getText());
                    deleteProduct(productDel);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter an Integer ID");
                } finally {
                    UserMenu.userMenuReturn(removeAll(menuWindow, enterID, Submit, ID));
                }
            }
        });

        return menuWindow;
    }
    public static JFrame removeAll(JFrame menuWindow, JLabel enterID, JButton submit, JTextField ID) {
        menuWindow.remove(enterID);
        menuWindow.remove(submit);
        menuWindow.remove(ID);
        return menuWindow;
    }

    public static void deleteProduct(int productDel){
        String sql = "DELETE FROM products WHERE ID = ?";
        Connection conn = Main.connect();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, productDel);
            // execute the delete statement
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "A record has been deleted");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
    }
}