package com.Javacrud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JavaCrud {
    private JTextField tfName;
    private JTextField tfPrice;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField tfSearch;
    private JPanel Main;
    private JTextField tfQuantity;
    private JButton searchButton;


    Connection con;
    PreparedStatement pst;


    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JavaCrud() {


        connection();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty;

                name = tfName.getText();
                price = tfPrice.getText();
                qty = tfQuantity.getText();

                try {
                    pst = con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Addedddddd!!!!");

                    tfName.setText("");
                    tfPrice.setText("");
                    tfQuantity.setText("");
                    tfName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                    System.out.println(e1);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String pid = tfSearch.getText();
                    pst = con.prepareStatement("select pname,price,qty from products where id = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        tfName.setText(name);
                        tfPrice.setText(price);
                        tfQuantity.setText(qty);
                    }
                    else
                    {
                        tfName.setText("");
                        tfPrice.setText("");
                        tfQuantity.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");

                    }
                }

                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid,name,price,qty;

                name = tfName.getText();
                price = tfPrice.getText();
                qty = tfQuantity.getText();
                pid = tfSearch.getText();

                try {

                    pst = con.prepareStatement("update products set pname = ?,price = ?,qty = ? where id = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");

                    tfName.setText("");
                    tfPrice.setText("");
                    tfQuantity.setText("");
                    tfName.requestFocus();
                    tfSearch.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid;

                pid = tfSearch.getText();


                try {
                    pst = con.prepareStatement("delete from products  where id = ?");
                    pst.setString(1, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");

                    tfName.setText("");
                    tfPrice.setText("");
                    tfQuantity.setText("");
                    tfName.requestFocus();
                    tfSearch.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });

    }






    public void connection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:gbproducts.db");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }


}
