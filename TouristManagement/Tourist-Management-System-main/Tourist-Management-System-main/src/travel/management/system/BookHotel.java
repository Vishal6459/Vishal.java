package travel.management.system;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookHotel extends JFrame implements ActionListener {
    JLabel lblusername,lblid,lblnumber,lblpersons,lbltotal,lblpackage,lblphonenumber,lbldays,lblacroom,lblfood;
    JLabel labelusername,labelid,labelnumber,labelprice,labelphonenumber;
    Choice chotel,cac,cfood;
    JTextField tfpersons,tfdays;
    JButton checkprice,bookpackage,back;
    String username;
    BookHotel(String username){
        this.username=username;
        setBounds(350,200,1100,600);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel text= new JLabel("BOOK HOTELS");
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

        lblpackage= new JLabel("Select Hotel");
        lblpackage.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblpackage.setBounds(40,110,150,25);
        lblpackage.setForeground(Color.DARK_GRAY);
        add(lblpackage);

        chotel= new Choice();
        chotel.setBounds(250,110,200,25);
        add(chotel);

        try{
            Conn c= new Conn();
            ResultSet rs=c.s.executeQuery("select * from hotel1");
            while(rs.next()){
                chotel.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lblpersons= new JLabel("Total Persons");
        lblpersons.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblpersons.setBounds(40,150,150,25);
        lblpersons.setForeground(Color.DARK_GRAY);
        add(lblpersons);

        tfpersons =new JTextField("1");
        tfpersons.setBounds(250,150,200,25);
        add(tfpersons);

        lbldays= new JLabel("No. of Days");
        lbldays.setFont(new Font("Tahoma",Font.PLAIN,16));
        lbldays.setBounds(40,190,150,25);
        lbldays.setForeground(Color.DARK_GRAY);
        add(lbldays);

        tfdays =new JTextField("1");
        tfdays.setBounds(250,190,200,25);
        add(tfdays);

        lblacroom= new JLabel("AC / NON AC");
        lblacroom.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblacroom.setBounds(40,230,150,25);
        lblacroom.setForeground(Color.DARK_GRAY);
        add(lblacroom);

        cac= new Choice();
        cac.add("AC");
        cac.add("NON AC");
        cac.setBounds(250,230,200,25);
        add(cac);

        lblfood= new JLabel("Food Included");
        lblfood.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblfood.setBounds(40,270,150,25);
        lblfood.setForeground(Color.DARK_GRAY);
        add(lblfood);

        cfood= new Choice();
        cfood.add("Yes");
        cfood.add("No");
        cfood.setBounds(250,270,200,25);
        add(cfood);

        lblid=new JLabel("Id");
        lblid.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblid.setBounds(40,310,150,20);
        lblid.setForeground(Color.DARK_GRAY);
        add(lblid);

        labelid=new JLabel();
        labelid.setBounds(250,310,200,25);
        add(labelid);

        lblnumber=new JLabel("Number");
        lblnumber.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblnumber.setBounds(40,350,150,25);
        lblnumber.setForeground(Color.DARK_GRAY);
        add(lblnumber);

        labelnumber=new JLabel();
        labelnumber.setBounds(250,350,150,25);
        add(labelnumber);

        lblphonenumber=new JLabel("Phone");
        lblphonenumber.setFont(new Font("Tahoma",Font.PLAIN,16));
        lblphonenumber.setBounds(40,390,150,20);
        lblphonenumber.setForeground(Color.DARK_GRAY);
        add(lblphonenumber);

        labelphonenumber=new JLabel();
        labelphonenumber.setBounds(250,390,200,25);
        add(labelphonenumber);

        lbltotal=new JLabel("Total Price");
        lbltotal.setFont(new Font("Tahoma",Font.PLAIN,16));
        lbltotal.setBounds(40,430,150,25);
        lbltotal.setForeground(Color.DARK_GRAY);
        add(lbltotal);

        labelprice=new JLabel();
        labelprice.setBounds(250,430,150,25);
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
        checkprice.setBounds(60,490,120,25);
        checkprice.addActionListener(this);
        add(checkprice);

        bookpackage= new JButton("Book Hotel");
        bookpackage.setBackground(Color.BLACK);
        bookpackage.setForeground(Color.WHITE);
        bookpackage.setBounds(200,490,120,25);
        bookpackage.addActionListener(this);
        add(bookpackage);

        back= new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(340,490,120,25);
        back.addActionListener(this);
        add(back);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/book.jpg"));
        Image i2= i1.getImage().getScaledInstance(600,400,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel l12=new JLabel(i3);
        l12.setBounds(500,50,600,300);
        add(l12);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==checkprice){
            try{
                Conn c= new Conn();
                ResultSet rs= c.s.executeQuery("select * from hotel1 where name='"+chotel.getSelectedItem()+"'");
                while(rs.next()){
                    int cost= Integer.parseInt(rs.getString("costperperson"));
                    int ac= Integer.parseInt(rs.getString("acroom"));
                    int food= Integer.parseInt(rs.getString("foodincluded"));


                    int persons= Integer.parseInt(tfpersons.getText());
                    int days= Integer.parseInt(tfdays.getText());

                    String acselected= cac.getSelectedItem();
                    String foodselected= cfood.getSelectedItem();

                    if(persons * days >0){
                        int total=0;
                        total +=acselected.equals("AC") ? ac:0;
                        total +=foodselected.equals("Yes") ? food:0;
                        total +=cost;
                        total= total* days*persons;

                        labelprice.setText("Rs "+total);

                    }else{
                        JOptionPane.showMessageDialog(null,"Please Enter a Valid Number");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(ae.getSource()==bookpackage){
            try{
                Conn c= new Conn();
                c.s.executeUpdate("insert into bookhotel values('"+labelusername.getText()+"','"+chotel.getSelectedItem()+"','"+tfpersons.getText()+"','"+tfdays.getText()+"','"+ cac.getSelectedItem()+"','"+cfood.getSelectedItem()+"','"+labelid.getText()+"','"+labelnumber.getText()+"','"+labelphonenumber.getText()+"','"+labelprice.getText()+"')");

                JOptionPane.showMessageDialog(null,"Hotel Booked Successfully");
                setVisible(false);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            setVisible(false);
        }
    }
    public static void main(String[] args){
        new BookHotel("7vik");
    }
}
