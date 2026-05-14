package travel.management.system;

import javax.swing.*;
import java .awt.*;
import java.sql.*;
import java.awt.event.*;

public class DeleteDetails extends JFrame implements ActionListener{
    JLabel lblusername,lblid,lblnumber,lblname,lblgender,lblcountry,lbladdress,lblphonenumber,lblemail;
    JLabel labelusername,labelid,labelnumber,labelname,labelgender,labelcountry,labeladdress,labelphonenumber,labelemail;
    JButton back;
    String username;
    DeleteDetails(String username){
        this.username=username;
        setBounds(450,180,870,625);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        lblusername= new JLabel("Username");
        lblusername.setBounds(30,50,150,25);
        lblusername.setForeground(Color.DARK_GRAY);
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(220,50,150,25);
        add(labelusername);

        lblid= new JLabel("Id");
        lblid.setBounds(30,110,150,25);
        lblid.setForeground(Color.DARK_GRAY);
        add(lblid);

        labelid = new JLabel();
        labelid.setBounds(220,110,150,25);
        add(labelid);

        lblnumber= new JLabel("Number");
        lblnumber.setBounds(30,170,150,25);
        lblnumber.setForeground(Color.DARK_GRAY);
        add(lblnumber);

        labelnumber= new JLabel();
        labelnumber.setBounds(220,170,150,25);
        add(labelnumber);

        lblname=new JLabel("Name");
        lblname.setBounds(30,230,150,25);
        lblname.setForeground(Color.DARK_GRAY);
        add(lblname);

        labelname=new JLabel();
        labelname.setBounds(220,230,150,25);
        add(labelname);

        lblgender=new JLabel("Gender");
        lblgender.setBounds(30,290,150,25);
        lblgender.setForeground(Color.DARK_GRAY);
        add(lblgender);

        labelgender=new JLabel();
        labelgender.setBounds(220,290,150,25);
        add(labelgender);

        lblcountry= new JLabel("Country");
        lblcountry.setBounds(500,50,150,25);
        lblcountry.setForeground(Color.DARK_GRAY);
        add(lblcountry);

        labelcountry= new JLabel();
        labelcountry.setBounds(690,50,150,25);
        add(labelcountry);

        lbladdress= new JLabel("Address");
        lbladdress.setBounds(500,110,150,25);
        lbladdress.setForeground(Color.DARK_GRAY);
        add(lbladdress);

        labeladdress= new JLabel();
        labeladdress.setBounds(690,110,150,25);
        add(labeladdress);

        lblphonenumber= new JLabel("Phone");
        lblphonenumber.setBounds(500,170,150,25);
        lblphonenumber.setForeground(Color.DARK_GRAY);
        add(lblphonenumber);

        labelphonenumber= new JLabel();
        labelphonenumber.setBounds(690,170,150,25);
        add(labelphonenumber);

        lblemail= new JLabel("Email");
        lblemail.setBounds(500,230,150,25);
        lblemail.setForeground(Color.DARK_GRAY);
        add(lblemail);

        labelemail= new JLabel();
        labelemail.setBounds(690,230,150,25);
        add(labelemail);


        back = new JButton("Delete");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        back.setBounds(350,350,100,25);
        back.addActionListener(this);
        add(back);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/viewall.jpg"));
        Image i2=i1.getImage().getScaledInstance(600,200,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel image= new JLabel(i3);
        image.setBounds(20,400,600,200);
        add(image);

        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/viewall.jpg"));
        Image i5=i4.getImage().getScaledInstance(600,200,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel image1= new JLabel(i6);
        image1.setBounds(600,400,600,200);
        add(image1);

        try{
            Conn c=new Conn();
            String query="select * from customer3 where username ='"+username+"'";
            ResultSet rs=c.s.executeQuery(query);
            while(rs.next()){
                labelusername.setText(rs.getString("username"));
                labelid.setText(rs.getString("id"));
                labelnumber.setText(rs.getString("number"));
                labelname.setText(rs.getString("name"));
                labelgender.setText(rs.getString("gender"));
                labelcountry.setText(rs.getString("country"));
                labeladdress.setText(rs.getString("address"));
                labelphonenumber.setText(rs.getString("phonenumber"));
                labelemail.setText(rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        try{
            Conn c= new Conn();
            c.s.executeUpdate("delete from account where username = '"+username+"'");
            c.s.executeUpdate("delete from customer3 where username = '"+username+"'");
            c.s.executeUpdate("delete from bookpackage where username = '"+username+"'");
            c.s.executeUpdate("delete from bookhotel where username = '"+username+"'");

            JOptionPane.showMessageDialog(null,"Data Deleted Sucessfully");

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new DeleteDetails("7vik");
    }
}
