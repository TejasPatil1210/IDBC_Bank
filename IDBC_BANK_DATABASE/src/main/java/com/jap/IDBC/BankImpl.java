package com.jap.IDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BankImpl {
    public static void main(String[] args) {
       displaymainmenu();
    }
    public static void displaymainmenu()
    {
        Validation val=new Validation();
        Bank_Operation bankObj=new Bank_Operation();
        System.out.println("Hello Dear,Sir/Maam");
        System.out.println("Welcome to IDBC Bank");
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n 1.Existing User \n 2.Create New Account  \n 3.Exit \n Enter your choice");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter User ID");
                    String u_ID=scan.next();
                    System.out.println("Enter Password");
                    String pass=scan.next();
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
                        Statement st;
                        String selectQue1="Select Account_no,Acc_Type,User_Id,Password from Account where User_Id='"+u_ID+"'";
                        st=con.createStatement();
                        ResultSet rs1=st.executeQuery(selectQue1);
                        String accno="";
                        String acctype="";
                        String pass1="";
                        String userId="";
                        while (rs1.next())
                        {
                            accno=rs1.getString(1);
                            acctype=rs1.getString(2);
                            userId=rs1.getString(3);
                            pass1=rs1.getString(4);
                        }
                        /*int useridchk=val.checkUserId(u_ID,userId);
                        while (useridchk==0)
                        {
                            System.out.println(u_ID+""+userId);
                            System.out.println("User Id is invalid!!!Please Enter again");
                            System.out.println("Enter User ID");
                            u_ID=scan.next();
                            System.out.println(u_ID+""+userId);
                            useridchk=val.checkUserId(u_ID,userId);
                        }*/
                        int passwdchk=val.checkPassword(pass,pass1);
                        while (passwdchk==0)
                        {
                            System.out.println("Incorrect Password!!!Please Enter again");
                            System.out.println("Enter Password");
                            pass=scan.next();
                            passwdchk=val.checkPassword(pass,pass1);
                        }
                        //if(u_ID.equalsIgnoreCase(userId)&&(pass.equalsIgnoreCase(pass1)))
                        System.out.println("Welcome Back to IDBC Bank");
                            if(acctype.equalsIgnoreCase("Save")) bankObj.displaySave_accServices(accno);
                            if(acctype.equalsIgnoreCase("Pay")) bankObj.displayPay_accServices(accno);
                        //else System.out.println("Account Number is not Matched!!! Please Enter again!!!");
                    }catch (Exception e) {
                        System.out.println("Error----" + e);
                    }
                    break;
                case 2:
                    while (true) {
                    System.out.println("Which type of Account do you want to create?");
                    System.out.println("\n 1.Save Account \n 2.Pay Account  \n 3.Exit \n Enter your choice");
                    int choice1 = scan.nextInt();
                        switch (choice1) {
                            case 1:bankObj.create_Save_Account();break;
                            case 2: bankObj.create_Pay_Account();break;
                            case 3: displaymainmenu();break;
                            default: System.out.println("Invalid Choice!");break;
                        }
                    }
                case 3:System.exit(0);
                default: System.out.println("Invalid Choice!");break;
            }
        }
    }
}

