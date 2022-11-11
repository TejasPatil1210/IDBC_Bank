package com.jap.IDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public int isPhoneNoValid(String phone)
    {
        Pattern p=Pattern.compile("^(\\+)(0|91)?[6-9][0-9]{9}$");
        Pattern p1=Pattern.compile("^[6-9][0-9]{9}$");
        Matcher m=p.matcher(phone);
        Matcher m1=p1.matcher(phone);
        if((m.find())||(m1.find())) return 1;
        else return 0;
    }

    public int isDOBValid(String dob)
    {
        Pattern p=Pattern.compile("^[0-9]{4}(\\-)([1-9]|10|11|12)(\\-)([1-9]|[1-2][0-9]|[3][0-1])$");
        Matcher m=p.matcher(dob);
        if(m.find()) return 1;
        else return 0;
    }
    public int isAadharValid(String Aadhar)
    {
        Pattern p=Pattern.compile("^[0-9]{12}$");
        Matcher m=p.matcher(Aadhar);
        if(m.find()) return 1;
        else return 0;
    }
    public int isPANValid(String pan)
    {
        Pattern p=Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]{1}$");
        Matcher m=p.matcher(pan);
        if(m.find()) return 1;
        else return 0;
    }

   public int checkUserId(String userIdIP )
    {
        String userid="";
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1 = "Select User_Id from Account where User_Id='" + userIdIP + "'";
            Statement st1 = con1.createStatement();
            ResultSet rs3 = st1.executeQuery(selectQue1);
            //String  = "";
            while (rs3.next()) {
                userid = rs3.getString(1);
            }

        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
        if (!userIdIP.equalsIgnoreCase(userid)) return 1;
        else return 0;
    }

    public int checkPassword(String PassIP,String pass )
    {
        if(PassIP.equalsIgnoreCase(pass)) return 1;
        else return 0;
    }

    public int amountvalid(double amount)
    {
        if(amount>0) return 1;
        else return 0;
    }

    public int accvalid(String accNo,String acc)
    {
        if(accNo.equalsIgnoreCase(acc)) return 0;
        else return 1;
    }

    public int duplicatemobchk(String mobNO)
    {
        String mobileNo="";
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1 = "Select mobile_no from customer where mobile_No='" + mobNO + "'";
            Statement st1 = con1.createStatement();
            ResultSet rs3 = st1.executeQuery(selectQue1);
            //String  = "";
            while (rs3.next()) {
                mobileNo = rs3.getString(1);
            }

        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
        if (mobNO.equalsIgnoreCase(mobileNo)) return 0;
        else return 1;
    }

    public int duplicateaadharchk(String aadharNO)
    {
        String adhNo="";
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1 = "Select Aadhar_No from customer where Aadhar_No='" + aadharNO + "'";
            Statement st1 = con1.createStatement();
            ResultSet rs3 = st1.executeQuery(selectQue1);
            //String  = "";
            while (rs3.next()) {
                adhNo = rs3.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
        if (aadharNO.equalsIgnoreCase(adhNo)) return 0;
        else return 1;
    }

    public int duplicatePANchk(String panNO)
    {
        String pan_number="";
        try {
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1 = "Select PAN_No from customer where PAN_No='" + panNO + "'";
            Statement st1 = con1.createStatement();
            ResultSet rs3 = st1.executeQuery(selectQue1);
            //String  = "";
            while (rs3.next()) {
                pan_number = rs3.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
        if (panNO.equalsIgnoreCase(pan_number)) return 0;
        else return 1;
    }
}


