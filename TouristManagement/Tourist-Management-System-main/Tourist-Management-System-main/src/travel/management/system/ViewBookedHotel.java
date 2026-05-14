package travel.management.system;

import javax.swing.*;
import java .awt.*;
import java.sql.*;
import java.awt.event.*;

public class ViewBookedHotel extends JFrame implements ActionListener{
    JLabel lblusername,lblid,lblnumber,lblphonenumber,text,lblpackage,lblpersons,lblprice,lbldays,lblac,lblfood;
    JLabel labelusername,labelid,labelnumber,labelphonenumber,labelpackage,labelpersons,labelprice,labeldays,labelac,labelfood ;
    JButton back;
    ViewBookedHotel(String username){
        setBounds(400,200,1000,600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        text= new JLabel("VIEW BOOKED HOTEL DETAILS");
        text.setBounds(60,0,400,30);
        text.setFont(new Font("Tahoma",Font.BOLD,20));
        add(text);

        lblusername= new JLabel("Username");
        lblusername.setBounds(30,50,150,25);
        lblusername.setForeground(Color.DARK_GRAY);
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(220,50,150,25);
        add(labelusername);

        lblpackage= new JLabel("Hotel Name");
        lblpackage .setBounds(30,90,150,25);
        lblpackage.setForeground(Color.DARK_GRAY);
        add(lblpackage);

        labelpackage = new JLabel();
        labelpackage.setBounds(220,90,150,25);
        add(labelpackage);

        lblpersons= new JLabel("Total Persons");
        lblpersons.setBounds(30,130,150,25);
        lblpersons.setForeground(Color.DARK_GRAY);
        add(lblpersons);

        labelpersons= new JLabel();
        labelpersons.setBounds(220,130,150,25);
        add(labelpersons);

        lbldays= new JLabel("Total Days");
        lbldays.setBounds(30,170,150,25);
        lbldays.setForeground(Color.DARK_GRAY);
        add(lbldays);

        labeldays= new JLabel();
        labeldays.setBounds(220,170,150,25);
        add(labeldays);

        lblac= new JLabel("AC / NON AC");
        lblac.setBounds(30,210,150,25);
        lblac.setForeground(Color.DARK_GRAY);
        add(lblac);

        labelac= new JLabel();
        labelac.setBounds(220,210,150,25);
        add(labelac);

        lblfood= new JLabel("Food Included");
        lblfood.setBounds(30,250,150,25);
        lblfood.setForeground(Color.DARK_GRAY);
        add(lblfood);

        labelfood= new JLabel();
        labelfood.setBounds(220,250,150,25);
        add(labelfood);

        lblid=new JLabel("Id");
        lblid.setBounds(30,290,150,25);
        lblid.setForeground(Color.DARK_GRAY);
        add(lblid);

        labelid=new JLabel();
        labelid.setBounds(220,290,150,25);
        add(labelid);

        lblnumber=new JLabel("Number");
        lblnumber.setBounds(30,330,150,25);
        lblnumber.setForeground(Color.DARK_GRAY);
        add(lblnumber);

        labelnumber=new JLabel();
        labelnumber.setBounds(220,330,150,25);
        add(labelnumber);

        lblphonenumber= new JLabel("Phone");
        lblphonenumber.setBounds(30,370,150,25);
        lblphonenumber.setForeground(Color.DARK_GRAY);
        add(lblphonenumber);

        labelphonenumber= new JLabel();
        labelphonenumber.setBounds(220,370,150,25);
        add(labelphonenumber);

        lblprice= new JLabel("Price");
        lblprice.setBounds(30,410,150,25);
        lblprice.setForeground(Color.DARK_GRAY);
        add(lblprice);

        labelprice= new JLabel();
        labelprice.setBounds(220,410,150,25);
        add(labelprice);


        back = new JButton("Back");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        back.setBounds(130,460,100,25);
        back.addActionListener(this);
        add(back);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/bookedDetails.jpg"));
        Image i2=i1.getImage().getScaledInstance(500,400,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel image= new JLabel(i3);
        image.setBounds(450,20,500,400);
        add(image);

        try{
            Conn c=new Conn();
            String query="select * from bookhotel where username ='"+username+"'";
            ResultSet rs=c.s.executeQuery(query);
            while(rs.next()){
                labelusername.setText(rs.getString("username"));
                labelpackage.setText(rs.getString("name"));
                labelpersons.setText(rs.getString("persons"));
                labelid.setText(rs.getString("id"));
                labelnumber.setText(rs.getString("number"));
                labelphonenumber.setText(rs.getString("phonenumber"));
                labelprice.setText(rs.getString("price"));
                labelfood.setText(rs.getString("food"));
                labelac.setText(rs.getString("ac"));
                labeldays.setText(rs.getString("days"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){

        setVisible(false);
    }

    public static void main(String[] args){

        new ViewBookedHotel("7vik");
    }
}
