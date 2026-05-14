package travel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateCustomer extends JFrame implements ActionListener {
    JLabel lblusername,labelusername,lblid,lblnumber,lblname,labelname,lblgender,lblcountry,lbladdress,lblemail,lblphonenumber;
    JComboBox comboid;
    JTextField tfnumber,tfcountry,tfaddress,tfemail,tfphonenumber,tfid,tfgender;
    JRadioButton rmale,rfemale,rothers;
    JButton add,back;


    UpdateCustomer(String username){
        setBounds(500,200,850,550);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel text = new JLabel("UPDATE CUSTOMER DETAILS");
        text.setBounds(50,0,300,25);
        text.setFont(new Font("Tahoma",Font.PLAIN,20));
        add(text);

        lblusername= new JLabel("Username");
        lblusername.setBounds(30,50,150,25);
        lblusername.setForeground(Color.DARK_GRAY);
        add(lblusername);


        labelusername = new JLabel();
        labelusername.setBounds(220,50,150,25);
        add(labelusername);


        lblid =new JLabel("Id");
        lblid.setBounds(30,90,150,25);
        lblid.setForeground(Color.DARK_GRAY);
        add(lblid);

        comboid = new JComboBox(new String[]{"Passport" , "Ration Card", "Aadhar Card", "Pan Card"});
        comboid.setBounds(220,90,300,25);
        comboid.setBackground(Color.WHITE);
        add(comboid);

        tfid= new JTextField();
        tfid.setBounds(220,90,300,25);
        add(tfid);

        lblnumber =new JLabel("Number");
        lblnumber.setBounds(30,130,150,25);
        lblnumber.setForeground(Color.DARK_GRAY);
        add(lblnumber);

        tfnumber= new JTextField();
        tfnumber.setBounds(220,130,300,25);
        add(tfnumber);

        lblname= new JLabel("Name");
        lblname.setBounds(30,170,150,25);
        lblname.setForeground(Color.DARK_GRAY);
        add(lblname);

        labelname = new JLabel();
        labelname.setBounds(220,170,150,25);
        add(labelname);

        lblgender = new JLabel("Gender");
        lblgender.setBounds(30,210,150,25);
        lblgender.setForeground(Color.DARK_GRAY);
        add(lblgender);

        tfgender= new JTextField();
        tfgender.setBounds(220,210,300,25);
        add(tfgender);

        lblcountry= new JLabel("Country");
        lblcountry.setBounds(30,250,150,25);
        lblcountry.setForeground(Color.DARK_GRAY);
        add(lblcountry);

        tfcountry= new JTextField();
        tfcountry.setBounds(220,250,300,25);
        add(tfcountry);

        lbladdress= new JLabel("Address");
        lbladdress.setBounds(30,290,150,25);
        lbladdress.setForeground(Color.DARK_GRAY);
        add(lbladdress);

        tfaddress= new JTextField();
        tfaddress.setBounds(220,290,300,25);
        add(tfaddress);

        lblemail= new JLabel("Email");
        lblemail.setBounds(30,330,150,25);
        lblemail.setForeground(Color.DARK_GRAY);
        add(lblemail);

        tfemail= new JTextField();
        tfemail.setBounds(220,330,300,25);
        add(tfemail);

        lblphonenumber= new JLabel("Phone Number");
        lblphonenumber.setBounds(30,370,150,25);
        lblphonenumber.setForeground(Color.DARK_GRAY);
        add(lblphonenumber);

        tfphonenumber= new JTextField();
        tfphonenumber.setBounds(220,370,300,25);
        add(tfphonenumber);

        add= new JButton("Update");
        add.setBackground(Color.GRAY);
        add.setForeground(Color.BLACK);
        add.setBounds(90,430,100,25);
        add.addActionListener(this);
        add(add);

        back= new JButton("Back");
        back.setBackground(Color.GRAY);
        back.setForeground(Color.BLACK);
        back.setBounds(290,430,100,25);
        back.addActionListener(this);
        add(back);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/update.png"));
        Image i2=i1.getImage().getScaledInstance(400,300,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel image= new JLabel(i3);
        image.setBounds(450,100,450,300);
        add(image);

        try{
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from customer3 where username= '"+username+"'");
            while(rs.next()){
                labelusername.setText(rs.getString("username"));
                labelname.setText(rs.getString("name"));
                tfid.setText(rs.getString("id"));
                tfnumber.setText(rs.getString("number"));
                tfgender.setText(rs.getString("gender"));
                tfcountry.setText(rs.getString("country"));
                tfaddress.setText(rs.getString("address"));
                tfphonenumber.setText(rs.getString("phonenumber"));
                tfemail.setText(rs.getString("email"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==add){
            String username=labelname.getText();
            String id =tfid.getText();
            String number= tfnumber.getText();
            String name= labelname.getText();
            String gender= tfgender.getText();
            String country= tfcountry.getText();
            String address=tfaddress.getText();
            String phone = tfphonenumber.getText();
            String email= tfemail.getText();

            try{
                Conn c= new Conn();
                String query="update customer3 set username='"+username+"',id= '"+id+"', number= '"+number+"' ,name= '"+name+"' ,gender= '"+gender+"' ,country= '"+country+"' ,address= '"+address+"' ,phonenumber= '"+phone+"' ,email= '"+email+"'";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null,"Customer Details Updated Sucessfully");
                setVisible(false);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            setVisible(false);
        }
    }

    public static void main(String args[]){
        new UpdateCustomer("7vik");
    }
}
