package travel.management.system;
import javax.print.attribute.standard.Destination;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Dashboard extends JFrame implements ActionListener {

    JButton addpersonalDetails,updatePersonalDetails,viewPersonalDetails,bookhotels,destinations;
    JButton viewbookedHotels,deletePersonalDetails,checkPackage,bookPackage,viewPackage,viewhotels;
    JButton payments,calculator,notepad,about;
    String username;
    Dashboard(String username){
        this.username=username;
//        setBounds(0,0,1600,1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        JPanel p1= new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.DARK_GRAY);
        p1.setBounds(0,0,1600,65);
        add(p1);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/dashboard.png"));
        Image i2=i1.getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel icon=new JLabel(i3);
        icon.setBounds(5,0,70,70);
        p1.add(icon);

        JLabel heading= new JLabel("Dashboard");
        heading.setBounds(80,10,300,40);
        heading.setFont(new Font("Tahoma",Font.BOLD,30));
        heading.setForeground(Color.WHITE);
        p1.add(heading);


        JPanel p2= new JPanel();
        p2.setLayout(null);
        p2.setBackground(Color.WHITE);
        p2.setBounds(0,65,300,900);
        add(p2);

        addpersonalDetails= new JButton("Add Personal Details");
        addpersonalDetails.setBounds(0,0,300,50);
        addpersonalDetails.setBackground(Color.DARK_GRAY);
        addpersonalDetails.setForeground(Color.WHITE);
        addpersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
        addpersonalDetails.setMargin(new Insets(0,0,0,60));
        addpersonalDetails.addActionListener(this);
        p2.add(addpersonalDetails);

        updatePersonalDetails= new JButton("Update Personal Details");
        updatePersonalDetails.setBounds(0,50,300,50);
        updatePersonalDetails.setForeground(Color.WHITE);
        updatePersonalDetails.setBackground(Color.DARK_GRAY);
        updatePersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
        updatePersonalDetails.setMargin(new Insets(0,0,0,30));
        updatePersonalDetails.addActionListener(this);
        p2.add(updatePersonalDetails);

        viewPersonalDetails= new JButton("View Details");
        viewPersonalDetails.setBounds(0,100,300,50);
        viewPersonalDetails.setForeground(Color.WHITE);
        viewPersonalDetails.setBackground(Color.DARK_GRAY);
        viewPersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
        viewPersonalDetails.setMargin(new Insets(0,0,0,130));
        viewPersonalDetails.addActionListener(this);
        p2.add(viewPersonalDetails);


        deletePersonalDetails= new JButton("Delete Personal Details");
        deletePersonalDetails.setBounds(0,150,300,50);
        deletePersonalDetails.setForeground(Color.WHITE);
        deletePersonalDetails.setBackground(Color.DARK_GRAY);
        deletePersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
        deletePersonalDetails.setMargin(new Insets(0,0,0,40));
        deletePersonalDetails.addActionListener(this);
        p2.add(deletePersonalDetails);


        checkPackage= new JButton("Check Package");
        checkPackage.setBounds(0,200,300,50);
        checkPackage.setForeground(Color.WHITE);
        checkPackage.setBackground(Color.DARK_GRAY);
        checkPackage.setFont(new Font("Tahoma",Font.PLAIN,20));
        checkPackage.setMargin(new Insets(0,0,0,110));
        checkPackage.addActionListener(this);
        p2.add(checkPackage);

        bookPackage= new JButton("Book Package");
        bookPackage.setBounds(0,250,300,50);
        bookPackage.setForeground(Color.WHITE);
        bookPackage.setBackground(Color.DARK_GRAY);
        bookPackage.setFont(new Font("Tahoma",Font.PLAIN,20));
        bookPackage.setMargin(new Insets(0,0,0,120));
        bookPackage.addActionListener(this);
        p2.add(bookPackage);

        viewPackage= new JButton("View Package");
        viewPackage.setBounds(0,300,300,50);
        viewPackage.setForeground(Color.WHITE);
        viewPackage.setBackground(Color.DARK_GRAY);
        viewPackage.setFont(new Font("Tahoma",Font.PLAIN,20));
        viewPackage.setMargin(new Insets(0,0,0,120));
        viewPackage.addActionListener(this);
        p2.add(viewPackage);


        viewhotels= new JButton("View Hotels");
        viewhotels.setBounds(0,350,300,50);
        viewhotels.setForeground(Color.WHITE);
        viewhotels.setBackground(Color.DARK_GRAY);
        viewhotels.setFont(new Font("Tahoma",Font.PLAIN,20));
        viewhotels.setMargin(new Insets(0,0,0,140));
        viewhotels.addActionListener(this);
        p2.add(viewhotels);

        bookhotels= new JButton("Book Hotels");
        bookhotels.setBounds(0,400,300,50);
        bookhotels.setForeground(Color.WHITE);
        bookhotels.setBackground(Color.DARK_GRAY);
        bookhotels.setFont(new Font("Tahoma",Font.PLAIN,20));
        bookhotels.setMargin(new Insets(0,0,0,140));
        bookhotels.addActionListener(this);
        p2.add(bookhotels);


        viewbookedHotels= new JButton("View Booked Hotels");
        viewbookedHotels.setBounds(0,450,300,50);
        viewbookedHotels.setForeground(Color.WHITE);
        viewbookedHotels.setBackground(Color.DARK_GRAY);
        viewbookedHotels.setFont(new Font("Tahoma",Font.PLAIN,20));
        viewbookedHotels.setMargin(new Insets(0,0,0,70));
        viewbookedHotels.addActionListener(this);

        p2.add(viewbookedHotels);


        destinations= new JButton("Destinations");
        destinations.setBounds(0,500,300,50);
        destinations.setForeground(Color.WHITE);
        destinations.setBackground(Color.DARK_GRAY);
        destinations.setFont(new Font("Tahoma",Font.PLAIN,20));
        destinations.setMargin(new Insets(0,0,0,140));
        destinations.addActionListener(this);
        p2.add(destinations);

        payments= new JButton("Payments");
        payments.setBounds(0,550,300,50);
        payments.setForeground(Color.WHITE);
        payments.setBackground(Color.DARK_GRAY);
        payments.setFont(new Font("Tahoma",Font.PLAIN,20));
        payments.setMargin(new Insets(0,0,0,165));
        payments.addActionListener(this);
        p2.add(payments);


        calculator= new JButton("Calculator");
        calculator.setBounds(0,600,300,50);
        calculator.setForeground(Color.WHITE);
        calculator.setBackground(Color.DARK_GRAY);
        calculator.setFont(new Font("Tahoma",Font.PLAIN,20));
        calculator.setMargin(new Insets(0,0,0,165));
        calculator.addActionListener(this);
        p2.add(calculator);

        notepad= new JButton("Notepad");
        notepad.setBounds(0,650,300,50);
        notepad.setForeground(Color.WHITE);
        notepad.setBackground(Color.DARK_GRAY);
        notepad.setFont(new Font("Tahoma",Font.PLAIN,20));
        notepad.setMargin(new Insets(0,0,0,170));
        notepad.addActionListener(this);
        p2.add(notepad);

        about= new JButton("About");
        about.setBounds(0,700,300,50);
        about.setForeground(Color.WHITE);
        about.setBackground(Color.DARK_GRAY);
        about.setFont(new Font("Tahoma",Font.PLAIN,20));
        about.setMargin(new Insets(0,0,0,180));
        about.addActionListener(this);
        p2.add(about);

        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i5=i4.getImage().getScaledInstance(1650,1000,Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel image=new JLabel(i6);
        image.setBounds(5,0,1650,1000);
        add(image);

        JLabel text= new JLabel("Travel and Tourism Management System");
        text.setBounds(350,90,1200,70);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Tahoma",Font.BOLD,55));
        image.add(text);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==addpersonalDetails){
            new AddCustomer(username);
        }else if(ae.getSource()==viewPersonalDetails){
            new ViewCustomer(username);
        }else if(ae.getSource()==updatePersonalDetails){
            new UpdateCustomer(username);
        }else if(ae.getSource()==checkPackage){
            new CheckPackage();
        }else if (ae.getSource()==bookPackage){
            new BookPackage(username);
        }else if(ae.getSource()==viewPackage){
            new ViewPackage(username);
        }else if(ae.getSource()==viewhotels){
            new CheckHotels();
        }else if(ae.getSource()==destinations){
            new Destinations();
        }else if(ae.getSource()==bookhotels){
            new BookHotel(username);
        }else if(ae.getSource()==viewbookedHotels){
            new ViewBookedHotel(username);
        }else if(ae.getSource()==payments){
            new Payment();
        }else if(ae.getSource()==calculator){
            try{
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(ae.getSource()==notepad){
            try{
                Runtime.getRuntime().exec("notepad.exe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(ae.getSource()==about){
            new About();
        }else if(ae.getSource()==deletePersonalDetails){
            new DeleteDetails(username);
        }
    }

    public static void main(String args[]){
        new Dashboard("");
    }

}
