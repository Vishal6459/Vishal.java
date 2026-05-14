package travel.management.system;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookPackage extends JFrame implements ActionListener {
    JLabel lblusername,lblid,lblnumber,lblpersons,lbltotal,lblpackage,lblphonenumber;
    JLabel labelusername,labelid,labelnumber,labelprice,labelphonenumber;
    Choice cpackage;
    JTextField tfpersons;
    JButton checkprice,bookpackage,back;
    String username;
    BookPackage(String username){
        this.username=username;
        setBounds(350,200,1100,500);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel text= new JLabel("BOOK PACKAGE");
        text.setBounds(100,10,200,30);
        text.setFont(new Font("Tahoma",Font.BOLD,20));
        add(text);

        lblusername= new JLabel("Username");
        lblusername.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblusername.setBounds(40,70,100,20);
        lblusername.setForeground(Color.DARK_GRAY);
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(250,70,200,20);
        labelusername.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(labelusername);

        lblpackage= new JLabel("Select Package");
        lblpackage.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblpackage.setBounds(40,110,150,25);
        lblpackage.setForeground(Color.DARK_GRAY);
        add(lblpackage);

        cpackage= new Choice();
        cpackage.add("Gold Package");
        cpackage.add("Silver Package");
        cpackage.add("Bronze Package");
        cpackage.setBounds(250,110,200,25);
        add(cpackage);

        lblpersons= new JLabel("Total Persons");
        lblpersons.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblpersons.setBounds(40,150,150,25);
        lblpersons.setForeground(Color.DARK_GRAY);
        add(lblpersons);

        tfpersons =new JTextField("1");
        tfpersons.setBounds(250,150,200,25);
        add(tfpersons);

        lblid=new JLabel("Id");
        lblid.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblid.setBounds(40,190,150,20);
        lblid.setForeground(Color.DARK_GRAY);
        add(lblid);

        labelid=new JLabel();
        labelid.setBounds(250,190,200,25);
        add(labelid);

        lblnumber=new JLabel("Number");
        lblnumber.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblnumber.setBounds(40,230,150,25);
        lblnumber.setForeground(Color.DARK_GRAY);
        add(lblnumber);

        labelnumber=new JLabel();
        labelnumber.setBounds(250,230,150,25);
        add(labelnumber);

        lblphonenumber=new JLabel("Phone");
        lblphonenumber.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblphonenumber.setBounds(40,270,150,20);
        lblphonenumber.setForeground(Color.DARK_GRAY);
        add(lblphonenumber);

        labelphonenumber=new JLabel();
        labelphonenumber.setBounds(250,270,200,25);
        add(labelphonenumber);

        lbltotal=new JLabel("Total Price");
        lbltotal.setFont(new Font("Tahoma",Font.PLAIN,16));
        lbltotal.setBounds(40,310,150,25);
        lbltotal.setForeground(Color.DARK_GRAY);
        add(lbltotal);

        labelprice=new JLabel();
        labelprice.setBounds(250,310,150,25);
        add(labelprice);

        try{
            Conn c=new Conn();
            String query="select * from customer3 where username ='"+username+"'";
            ResultSet rs=c.s.executeQuery(query);
            while(rs.next()){
                labelusername.setText(rs.getString("username"));
                labelid.setText(rs.getString("id"));
                labelnumber.setText(rs.getString("number"));
                labelphonenumber.setText(rs.getString("phonenumber"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        checkprice= new JButton("Check Price");
        checkprice.setBackground(Color.BLACK);
        checkprice.setForeground(Color.WHITE);
        checkprice.setBounds(60,380,120,25);
        checkprice.addActionListener(this);
        add(checkprice);

        bookpackage= new JButton("Book Package");
        bookpackage.setBackground(Color.BLACK);
        bookpackage.setForeground(Color.WHITE);
        bookpackage.setBounds(200,380,120,25);
        bookpackage.addActionListener(this);
        add(bookpackage);

        back= new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(340,380,120,25);
        back.addActionListener(this);
        add(back);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/bookpackage.jpg"));
        Image i2= i1.getImage().getScaledInstance(500,300,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel l12=new JLabel(i3);
        l12.setBounds(550,50,500,300);
        add(l12);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==checkprice){
            String pack=cpackage.getSelectedItem();
            int cost=0;
            if(pack.equals("Gold Package")){
                cost+=15000;

            }else if(pack.equals("Silver Package")){
                cost+=25000;
            }else{
                cost+=35000;
            }
            int persons=Integer.parseInt(tfpersons.getText());
            cost *=persons;
            labelprice.setText("Rs"+cost);

        }else if(ae.getSource()==bookpackage){
            try{
                Conn c= new Conn();
                c.s.executeUpdate("insert into bookpackage values('"+labelusername.getText()+"','"+cpackage.getSelectedItem()+"','"+tfpersons.getText()+"','"+labelid.getText()+"','"+labelnumber.getText()+"','"+labelphonenumber.getText()+"','"+labelprice.getText()+"')");
                JOptionPane.showMessageDialog(null,"Package Booked Successfully");
                setVisible(false);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            setVisible(false);
        }
    }
    public static void main(String[] args){
        new BookPackage("7vik");
    }
}
