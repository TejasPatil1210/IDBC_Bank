package com.jap.IDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
public class Save_Account extends Account {
    public Save_Account() {
        super();
    }
    public void calculateInterest(String acc_no )
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1="Select Account_No from Account where Account_No="+acc_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            if(acc_no.equalsIgnoreCase(s)){
                String selectQue2="Select Acc_Balance from Account where Account_No="+acc_no;
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery(selectQue2);
                double balance=0.0;
                while (rs2.next())
                {
                    balance=rs2.getDouble(1);
                }
                double calc_interest_amt=(balance*2.5)/100;
                System.out.println("You will get Rs."+calc_interest_amt+" as Interest on Rs."+ balance+" after 1 year");
                System.out.println();
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }
}

