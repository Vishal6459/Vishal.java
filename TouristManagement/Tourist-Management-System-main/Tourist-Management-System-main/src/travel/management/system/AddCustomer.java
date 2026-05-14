package travel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {
    JLabel lblusername,labelusername,lblid,lblnumber,lblname,labelname,lblgender,lblcountry,lbladdress,lblemail,lblphonenumber;
    JComboBox comboid;
    JTextField tfnumber,tfcountry,tfaddress,tfemail,tfphonenumber;
    JRadioButton rmale,rfemale,rothers;
    JButton add,back;
// account

    AddCustomer(String username){
        setBounds(450,200,850,550);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);


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
        comboid.setBounds(220,90,150,25);
        comboid.setBackground(Color.WHITE);
        add(comboid);

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

        rmale= new JRadioButton("Male");
        rmale.setBounds(220,210,70,25);
        rmale.setBackground(Color.WHITE);
        add(rmale);

        rfemale= new JRadioButton("Female");
        rfemale.setBounds(290,210,70,25);
        rfemale.setBackground(Color.WHITE);
        add(rfemale);

        rothers= new JRadioButton("Others");
        rothers.setBounds(360,210,70,25);
        rothers.setBackground(Color.WHITE);
        add(rothers);

        ButtonGroup bg=new ButtonGroup();
        bg.add(rmale);
        bg.add(rfemale);
        bg.add(rothers);

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

        add= new JButton("Add");
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

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/newcustomer.jpg"));
        Image i2=i1.getImage().getScaledInstance(400,500,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel image= new JLabel(i3);
        image.setBounds(450,40,450,420);
        add(image);

        try{
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from account where username='"+username+"'");
            while(rs.next()){
                labelusername.setText(rs.getString("username"));
                labelname.setText(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==add){
            String username=labelusername.getText();
            String id =(String) comboid.getSelectedItem();
            String number= tfnumber.getText();
            String name= labelname.getText();
            String gender=null;
            if(rmale.isSelected()){
                gender="Male";
            }else if(rfemale.isSelected()) {
                gender="Female";
            }else{
                gender="others";
            }
            String country= tfcountry.getText();
            String address=tfaddress.getText();
            String phone = tfphonenumber.getText();
            String email= tfemail.getText();

            try{
                Conn c= new Conn();
                String query="insert into customer3 values('"+username+"','"+id+"','"+number+"','"+name+"','"+gender+"','"+country+"','"+address+"','"+phone+"','"+email+"')";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null,"Customer Details Added Sucessfully");
                setVisible(false);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            setVisible(false);
        }
    }

    public static void main(String args[]){
        new AddCustomer("7vik");
    }
}
