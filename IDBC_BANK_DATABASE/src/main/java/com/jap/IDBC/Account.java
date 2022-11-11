package com.jap.IDBC;

import java.sql.*;
import java.util.Random;
public class Account implements Ibank{
  //  private String acc_no;
   // private StringBuilder acc;
    //private double acc_balance;
   // private PaymentMode paymentMode;
    public Account(){}

    //public void setAcc_balance( double acc_balance){ this.acc_balance=acc_balance;}
    //public double getAcc_balance(){return acc_balance;}

    public StringBuilder generateACC_no(){
        Random random=new Random();
        int rdnum;
        String acc_no[]=new String[12];

        for(int i=0;i<12;i++)
        {
            rdnum=1+random.nextInt(9);
            acc_no[i]=Integer.toString(rdnum);
        }
        StringBuilder sbacc = new StringBuilder("");
        for(int i=0;i<12;i++) {
            sbacc.append(acc_no[i]);
        }
        return sbacc;
    }
    public String displayaccount_no() {
        String acc = String.valueOf(generateACC_no());
        return acc;
    }
    public void checkbalance(String acc_no) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            //Statement st;
            String selectQue1="Select Account_no from Account where Account_No="+acc_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
           // String acctype="";
            while (rs1.next())
            {
                s=rs1.getString(1);
            }
            if(acc_no.equalsIgnoreCase(s)){
                String selectQue2="Select Acc_Balance from Account where Account_No="+acc_no;
            Statement st2=con.createStatement();
            ResultSet rs2=st2.executeQuery(selectQue2);
                String s1="";
                while (rs2.next())
                {
                    s1=rs2.getString(1);
                }
                System.out.println("Your Current balance is: Rs."+s1);
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }

    public void deposit(String acc_no, double deposit_amount, PaymentMode paymentMode)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1="Select Account_no from Account where Account_No="+acc_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            if(acc_no.equalsIgnoreCase(s)){
                String Que1="update Account set Acc_Balance=Acc_Balance+"+deposit_amount+" where Account_No="+acc_no;
                Statement st2=con.createStatement();
                int rows=st2.executeUpdate(Que1);
                System.out.println("Deposited Successfully");
                String selectQue2="Select Acc_Balance from Account where Account_No="+acc_no;
                Statement st3=con.createStatement();
                ResultSet rs2=st3.executeQuery(selectQue2);
                String s1="";
                while (rs2.next()) {s1=rs2.getString(1);}
                System.out.println("Your Current balance is: Rs."+s1);

                String selectQue10="Select User_Id from Account where Account_No="+acc_no;
                Statement st4=con.createStatement();
                ResultSet rs5=st4.executeQuery(selectQue10);
                String user_Id="";
                while (rs5.next()) {user_Id=rs5.getString(1);}
                String selectQue="Select curdate()";
                Statement st1=con.createStatement();
                ResultSet rs3=st1.executeQuery(selectQue);
                String date="";
                while (rs3.next()) {date=rs3.getString(1);}
                String selectQue3="Select curtime()";
                st1=con.createStatement();
                ResultSet rs=st1.executeQuery(selectQue3);
                String time="";
                while (rs.next()) {time=rs.getString(1);}
               // int rows1;
                String paymode="";
                if(paymentMode.equals(PaymentMode.CASH)) paymode="Cash Deposit";
                if(paymentMode.equals(PaymentMode.CHEQUE)) paymode="Cheque Deposit";
                String query = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1,user_Id);
                pst.setString(2,acc_no);
                pst.setString(3,date);
                pst.setString(4,time);
                pst.setString(5,paymode);
                pst.setString(6,null);
                pst.setDouble(7,deposit_amount);
                pst.setString(8,s1);
                rows = pst.executeUpdate();
                //System.out.println("Inserted Successfully");

            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
   }

    public void withdrawal(String acc_no,double withdrawal_amount)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1="Select Account_no from Account where Account_No="+acc_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next())
            {
                s=rs1.getString(1);
            }
            if(acc_no.equalsIgnoreCase(s)){
                String Que1="update Account set Acc_Balance=Acc_Balance-"+withdrawal_amount+" where Account_No="+acc_no;
                Statement st2=con.createStatement();
                int rows=st2.executeUpdate(Que1);
                System.out.println("Withdrawal Successfully");
                String selectQue2="Select Acc_Balance from Account where Account_No="+acc_no;
                Statement st3=con.createStatement();
                ResultSet rs2=st3.executeQuery(selectQue2);
                String s1="";
                while (rs2.next()) {s1=rs2.getString(1);}
                System.out.println("Your Current balance is: Rs."+s1);

                String selectQue10="Select User_Id from Account where Account_No="+acc_no;
                Statement st4=con.createStatement();
                ResultSet rs5=st4.executeQuery(selectQue10);
                String user_Id="";
                while (rs5.next()) {user_Id=rs5.getString(1);}

                String selectQue="Select curdate()";
                Statement st1=con.createStatement();
                ResultSet rs3=st1.executeQuery(selectQue);
                String date="";
                while (rs3.next()) {date=rs3.getString(1);}
                String selectQue3="Select curtime()";
                st1=con.createStatement();
                ResultSet rs=st1.executeQuery(selectQue3);
                String time="";
                while (rs.next()) {time=rs.getString(1);}
                int rows1;
                String paymode="withdrawal";

                String query = "insert into Logs(User_ID,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1,user_Id);
                pst.setString(2,acc_no);
                pst.setString(3,date);
                pst.setString(4,time);
                pst.setString(5,paymode);
                pst.setDouble(6,withdrawal_amount);
                pst.setString(7,null);
                pst.setString(8,s1);
                rows = pst.executeUpdate();
              //  System.out.println("Inserted Successfully");
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }

    }
    public void fundtransfer(String acc_no,String accountNo,double transfer_Amount)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
           //sender Account info
            String selectQue1="Select Account_no from Account where Account_No="+acc_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            //System.out.println("data retrieve Successfully in fundtransfer function");
           // Receiver Account Info
            String selectQue2="Select Account_no,User_Id from Account where Account_no="+accountNo;
            Statement st2=con.createStatement();
            ResultSet rs2=st2.executeQuery(selectQue2);
            String s2="";
            String u_Id="";
            while (rs2.next()) {s2=rs2.getString(1);
            u_Id=rs2.getString(2);}
           // System.out.println("data retrieve Successfully in fundtransfer function");
            //transfer Process
            if((acc_no.equalsIgnoreCase(s))&&(accountNo.equalsIgnoreCase(s2))){
                String Que1="update Account set Acc_Balance=Acc_Balance-"+transfer_Amount+" where Account_No="+acc_no;
                Statement st3=con.createStatement();
                int rows=st3.executeUpdate(Que1);
                String Que2="update Account set Acc_Balance=Acc_Balance+"+transfer_Amount+" where Account_No="+s2;
                Statement st4=con.createStatement();
                int rows1=st4.executeUpdate(Que2);
                System.out.println("Fund Transferred Successfully");
                //checking balance of sender account
                String selectQue3="Select Acc_Balance from Account where Account_No="+acc_no;
                Statement st5=con.createStatement();
                ResultSet rs4=st5.executeQuery(selectQue3);
                String s1="";
                while (rs4.next()) {s1=rs4.getString(1);}
                System.out.println("Your Current balance is: Rs."+s1);
                //update sender logs
                String selectQue10="Select User_Id from Account where Account_No="+acc_no;
                Statement sta=con.createStatement();
                ResultSet rs5=sta.executeQuery(selectQue10);
                String user_Id="";
                while (rs5.next()) {user_Id=rs5.getString(1);}
                String selectQue="Select curdate()";
                Statement st1=con.createStatement();
                ResultSet rs3=st1.executeQuery(selectQue);
                String date="";
                while (rs3.next()) {date=rs3.getString(1);}
                String selectQue5="Select curtime()";
                st1=con.createStatement();
                ResultSet rs=st1.executeQuery(selectQue5);
                String time="";
                while (rs.next()) {time=rs.getString(1);}
                //int rows1;
                String paymode="Fund Transfer to A/C No."+s2;
                String query = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1,user_Id);
                pst.setString(2,acc_no);
                pst.setString(3,date);
                pst.setString(4,time);
                pst.setString(5,paymode);
                pst.setDouble(6,transfer_Amount);
                pst.setString(7,null);
                pst.setString(8,s1);
                int rows2 = pst.executeUpdate();
                //System.out.println("Inserted Successfully");

                //Update Receiver Logs
                String selectQue11="Select User_Id from Account where Account_No="+s2;
                Statement sta1=con.createStatement();
                ResultSet rst=sta1.executeQuery(selectQue11);
                String user_Id1="";
                while (rst.next()) {user_Id1=rst.getString(1);}
                String selectQue6="Select curdate()";
                Statement st6=con.createStatement();
                ResultSet rs6=st6.executeQuery(selectQue6);
                String date1="";
                while (rs6.next()) {date1=rs6.getString(1);}
                String selectQue7="Select curtime()";
                st6=con.createStatement();
                ResultSet rs7=st6.executeQuery(selectQue7);
                String time1="";
                while (rs7.next()) {time1=rs7.getString(1);}

                String selectQue4="Select Acc_Balance from Account where Account_No="+s2;
                Statement st9=con.createStatement();
                ResultSet rs9=st9.executeQuery(selectQue4);
                String s3="";
                while (rs9.next()) {s3=rs9.getString(1);}
                String paymode1="Received Fund from A/C NO."+acc_no;
                String query1 = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst1 = con.prepareStatement(query1);
                pst1.setString(1,user_Id1);
                pst1.setString(2,s2);
                pst1.setString(3,date1);
                pst1.setString(4,time1);
                pst1.setString(5,paymode1);
                pst1.setString(6,null);
                pst1.setDouble(7,transfer_Amount);
                pst1.setString(8,s3);
                int rows3 = pst1.executeUpdate();
               // System.out.println("Inserted Successfully");
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }
}
