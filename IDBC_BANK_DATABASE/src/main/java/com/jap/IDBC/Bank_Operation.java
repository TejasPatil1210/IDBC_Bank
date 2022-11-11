package com.jap.IDBC;
import java.sql.*;
import java.util.Scanner;
public class Bank_Operation {
     Save_Account save=new Save_Account();
     Pay_Account pay=new Pay_Account();
    Scanner scan = new Scanner(System.in);
    Validation validation=new Validation();

    public void create_Save_Account() {
        Bank_Operation bankO=new Bank_Operation();
       String Email_id= bankO.customer_details();
       Validation valid=new Validation();
        System.out.println("You will need to deposit Rs.500 to activate the Save account");
        System.out.println("How will you deposit the amount(via cash/cheque)?");
        String paymentmode=scan.next();
        Double opening_balance=500.00;
        //if(paymentmode.equalsIgnoreCase("cash")) {save.setAcc_balance(500.00);}//set(500.00)//,PaymentMode.CASH);
        //if (paymentmode.equalsIgnoreCase("cheque")) {save.setAcc_balance(500.00);}//,PaymentMode.CHEQUE);
        System.out.println();
        String acc_NO=save.displayaccount_no();
        System.out.println("Your Account Number is:");
        System.out.print(acc_NO);
        System.out.println();
        System.out.println("Set User ID to Your Account:");
        String User_Id=scan.next();
        int Ucheck=valid.checkUserId(User_Id);
            while (Ucheck==0)
            {
                System.out.println("This User ID is already exists. Please try with another one! ");
                System.out.println("Set User ID to Your Account:");
                User_Id=scan.next();
                Ucheck=valid.checkUserId(User_Id);
            }
        System.out.println("Set Password to Your Account:");
        String password=scan.next();
        String Acc_type="Save";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            //insert values to the account table
            String query = "insert into Account values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,acc_NO);
            pst.setString(2,Email_id);
            pst.setString(3,User_Id);
            pst.setString(4,password);
            pst.setString(5,Acc_type);
            pst.setDouble(6,opening_balance);
            int rows = pst.executeUpdate();
            //System.out.println("Inserted Successfully");
            //insert values to the log table
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
            String paymode="Opening Balance";
            //if(paymentmode.equalsIgnoreCase(PaymentMode.CASH)) paymode="Opening Balance by Cash Deposit";
            //if(paymentmode.equalsIgnoreCase(PaymentMode.CHEQUE)) paymode="Opening Balance by Cheque Deposit";
            String query1 = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst1 = con.prepareStatement(query1);
            pst1.setString(1,User_Id);
            pst1.setString(2,acc_NO);
            pst1.setString(3,date);
            pst1.setString(4,time);
            pst1.setString(5,paymode);
            pst1.setString(6,null);
            pst1.setDouble(7,opening_balance);
            pst1.setDouble(8,opening_balance);
            rows = pst1.executeUpdate();
            //System.out.println("Inserted Successfully");
        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
        bankO.displaySave_accServices(acc_NO);
    }
    public static void displaySave_accServices(String acc_no) {
       Bank_Operation bank=new Bank_Operation();
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        while (true){
            System.out.println("Save Account Services:");
            System.out.println("Select the Services from the Main Menu");
            System.out.println();
            System.out.println("----------------------------------------------");
            System.out.printf("%5s %12s  \n", "Sr.No", "Services");
            System.out.println("----------------------------------------------");
            System.out.printf("%2s %20s  \n", "1", "Check Balance");
            System.out.printf("%2s %15s  \n", "2", "Deposit ");
            System.out.printf("%2s %17s  \n", "3", "Withdrawal");
            System.out.printf("%2s %20s  \n", "4", "Fund Transfer");
            System.out.printf("%2s %14s  \n", "5", "History");
            System.out.printf("%2s %29s  \n", "6", "Create Another Account");
            System.out.printf("%2s %22s  \n", "7", "Interest Amount");
            System.out.printf("%2s %11s  \n", "8", "Exit");
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Enter your Choice of Service");
            int choiceForServices = scanner.nextInt();
            scanner.nextLine();
            switch (choiceForServices){
                case 1:bank.displaysaveaccCheckbalance(acc_no);break;
                case 2:bank.displaysaveaccdeposit(acc_no);break;
                case 3:bank.displaysaveaccwithdrawal(acc_no);break;
                case 4:bank.displaysaveaccfundtransfer(acc_no);break;
                case 5:bank.acc_History(acc_no);break;
                case 6:bank.displayCreateAccount(acc_no);break;
                case 7:bank.displayInterestamount(acc_no);break;
                case 8:BankImpl.displaymainmenu();
                default: System.out.println("Invalid Choice!!!");
            }
            System.out.println();
        }
    }

    public  void displaysaveaccCheckbalance(String account_no)
    {
        save.checkbalance(account_no);
    }

    public  void displaysaveaccdeposit(String account_no)
    {
        Scanner scan=new Scanner(System.in);
        System.out.println("How will you deposit the amount(via cash/cheque)?");
        String paymentmode=scan.next();
        System.out.println("Enter the Deposit Amount ");
        double deposit_amt= scan.nextDouble();
        int amountchk=validation.amountvalid(deposit_amt);
        while (amountchk==0)
        {
            System.out.println("Amount is Invalid!");
            System.out.println("Enter the Deposit Amount ");
            deposit_amt= scan.nextDouble();
            amountchk=validation.amountvalid(deposit_amt);
        }
        if(paymentmode.equalsIgnoreCase("cash")) save.deposit(account_no,deposit_amt,PaymentMode.CASH);
        if (paymentmode.equalsIgnoreCase("cheque")) save.deposit(account_no,deposit_amt,PaymentMode.CHEQUE);
    }

    public  void displaysaveaccwithdrawal(String account_no)
    {
        Scanner scan=new Scanner(System.in);
        //System.out.println("Please Enter your Account Number:");
        //=scan.next();
        System.out.println("Enter the Withdrawal Amount ");
        double witdrawal_amt= scan.nextDouble();
        int amountchk=validation.amountvalid(witdrawal_amt);
        while (amountchk==0)
        {
            System.out.println("Amount is Invalid!");
            System.out.println("Enter the Withdrawal Amount ");
            witdrawal_amt= scan.nextDouble();
            amountchk=validation.amountvalid(witdrawal_amt);
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            //Statement st;
            String selectQue1="Select Account_No from Account where Account_No="+account_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            if(account_no.equalsIgnoreCase(s)){
                String selectQue2="Select Acc_Balance from Account where Account_No="+account_no;
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery(selectQue2);
                double balance=0.0;
                while (rs2.next())
                {
                    balance=rs2.getDouble(1);
                }
                if((balance>500)&&(witdrawal_amt<balance)) {
                    save.withdrawal(account_no,witdrawal_amt);
                }
                else System.out.println("Insufficient Balance!!! \nWe can't accept your request");
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }

    public  void displaysaveaccfundtransfer(String account_no)
    {
        Scanner scan=new Scanner(System.in);
        //System.out.println("Please Enter Account Number:");
        //=scan.next();
        System.out.println("Enter Account Number that you want to transfer fund:");
        String acc_num=scan.next();
        int acchk=validation.accvalid(account_no,acc_num);
        while(acchk==0)
        {
            System.out.println("Account Number is Invalid!!!");
            System.out.println("Enter Account Number that you want to transfer fund:");
            acc_num=scan.next();
            acchk=validation.accvalid(account_no,acc_num);
        }
        System.out.println("Enter the Amount ");
        double transfer_amt= scan.nextDouble();
        int amountchk=validation.amountvalid(transfer_amt);
        while (amountchk==0)
        {
            System.out.println("Amount is Invalid!");
            System.out.println("Enter the Amount ");
            transfer_amt= scan.nextDouble();
            amountchk=validation.amountvalid(transfer_amt);
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            //Statement st;
            String selectQue1="Select Account_No from Account where Account_No="+account_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            if(account_no.equalsIgnoreCase(s)){
                String selectQue2="Select Acc_Balance from Account where Account_No="+account_no;
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery(selectQue2);
                double balance=0.0;
                while (rs2.next())
                {
                    balance=rs2.getDouble(1);
                }
                if((balance>500)&&(transfer_amt<balance)) {
                    save.fundtransfer(account_no,acc_num,transfer_amt);
                }
                else System.out.println("Insufficient Balance!!! \nWe can't accept your request");
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }

    public void displayInterestamount(String acc_num)
    {
        save.calculateInterest(acc_num);
    }


    public void create_Pay_Account() {
        Bank_Operation bankO=new Bank_Operation();
        String emailid=bankO.customer_details();
        Validation valid=new Validation();
        System.out.println("You will need to deposit Rs.1000 to activate the Pay account");
        System.out.println("How will you deposit the amount(via cash/cheque)?");
        String paymentmode=scan.next();
        Double opening_balance=1000.00;
        //if(paymentmode.equalsIgnoreCase("cash")) pay.setAcc_balance(1000.00);//PaymentMode.CASH
        //if (paymentmode.equalsIgnoreCase("cheque")) pay.setAcc_balance(1000.00);
        System.out.println();
        String acc_No=pay.displayaccount_no();
        System.out.println("Your Account Number is:");
        System.out.print(acc_No);
        System.out.println();
        System.out.println("Set User Id to Your Account:");
        String userid=scan.next();
        int Ucheck=valid.checkUserId(userid);
        while (Ucheck==0)
        {
            System.out.println("This User ID is already exists. Please try with another one! ");
            System.out.println("Set User ID to Your Account:");
            userid = scan.next();
            Ucheck = valid.checkUserId(userid);
        }
        System.out.println("Set Password to Your Account:");
        String password=scan.next();
        //bankO.insertaccNOintocustomer(emailid,userid);
        String Acc_type="Pay";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            //insert values to the account table
            String query = "insert into Account values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, acc_No);
            pst.setString(2, emailid);
            pst.setString(3, userid);
            pst.setString(4, password);
            pst.setString(5, Acc_type);
            pst.setDouble(6, opening_balance);

            int rows = pst.executeUpdate();
           // System.out.println("Inserted Successfully");
            //insert values to the log table
            String selectQue = "Select curdate()";
            Statement st1 = con.createStatement();
            ResultSet rs3 = st1.executeQuery(selectQue);
            String date = "";
            while (rs3.next()) {
                date = rs3.getString(1);
            }
            String selectQue3 = "Select curtime()";
            st1 = con.createStatement();
            ResultSet rs = st1.executeQuery(selectQue3);
            String time = "";
            while (rs.next()) {
                time = rs.getString(1);
            }
            int rows1;
            String paymode = "Opening Balance";
           // if (paymentmode.equals(PaymentMode.CASH)) paymode = "Opening Balance by Cash Deposit";
            //if (paymentmode.equals(PaymentMode.CHEQUE)) paymode = "Opening Balance by Cheque Deposit";
            String query1 = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst1 = con.prepareStatement(query1);
            pst1.setString(1, userid);
            pst1.setString(2, acc_No);
            pst1.setString(3, date);
            pst1.setString(4, time);
            pst1.setString(5, paymode);
            pst1.setString(6, null);
            pst1.setDouble(7, opening_balance);
            pst1.setDouble(8, opening_balance);
            rows = pst1.executeUpdate();
            //System.out.println("Inserted Successfully");
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
        displayPay_accServices(acc_No);
    }
    public static void displayPay_accServices(String acc_No) {
        Bank_Operation bank=new Bank_Operation();
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        while (true){
            System.out.println("Pay Account Services:");
            System.out.println("Select the Services from the Main Menu");
            System.out.println();
            System.out.println("----------------------------------------------");
            System.out.printf("%5s %12s  \n", "Sr.No", "Services");
            System.out.println("----------------------------------------------");
            System.out.printf("%2s %20s  \n", "1", "Check Balance");
            System.out.printf("%2s %15s  \n", "2", "Deposit ");
            System.out.printf("%2s %17s  \n", "3", "Withdrawal");
            System.out.printf("%2s %20s  \n", "4", "Fund Transfer");
            System.out.printf("%2s %14s  \n", "5", "History");
            System.out.printf("%2s %29s  \n", "6", "Create Another Account");
            System.out.printf("%2s %11s  \n", "7", "Exit");
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Enter your Choice of Service");
            int choiceForServices = scanner.nextInt();
            scanner.nextLine();

            switch (choiceForServices){
                case 1:bank.displayPayaccCheckbalance(acc_No);break;
                case 2:bank.displayPayaccdeposit(acc_No);break;
                case 3:bank.displayPayaccwithdrawal(acc_No);break;
                case 4:bank.displayPayaccfundtransfer(acc_No);break;
                case 5:bank.acc_History(acc_No);break;
                case 6:bank.displayCreateAccount(acc_No);break;
                case 7:BankImpl.displaymainmenu();
                default: System.out.println("Invalid Choice!!!");
            }
            System.out.println("\n");
        }
    }

    public  void displayPayaccCheckbalance(String account_no)
    {
        pay.checkbalance(account_no);
    }

    public  void displayPayaccdeposit(String account_no)
    {
        Scanner scan=new Scanner(System.in);
        System.out.println("How will you deposit the amount(via cash/cheque)?");
        String paymentmode=scan.next();
        System.out.println("Enter the Deposit Amount ");
        double deposit_amt= scan.nextDouble();
        int amountchk=validation.amountvalid(deposit_amt);
        while (amountchk==0)
        {
            System.out.println("Amount is Invalid!");
            System.out.println("Enter the Deposit Amount ");
            deposit_amt= scan.nextDouble();
            amountchk=validation.amountvalid(deposit_amt);
        }
        if(paymentmode.equalsIgnoreCase("cash")) pay.deposit(account_no,deposit_amt,PaymentMode.CASH);
        if (paymentmode.equalsIgnoreCase("cheque")) pay.deposit(account_no,deposit_amt,PaymentMode.CHEQUE);
    }

    public  void displayPayaccwithdrawal(String account_no)
    {
        Scanner scan=new Scanner(System.in);
        //System.out.println("Please Enter your Account Number:");
        //=scan.next();
        System.out.println("Enter the Withdrawal Amount ");
        double witdrawal_amt= scan.nextDouble();
        int amountchk=validation.amountvalid(witdrawal_amt);
        while (amountchk==0)
        {
            System.out.println("Amount is Invalid!");
            System.out.println("Enter the Withdrawal Amount ");
            witdrawal_amt= scan.nextDouble();
            amountchk=validation.amountvalid(witdrawal_amt);
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue1="Select Account_No from Account where Account_No="+account_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            if(account_no.equalsIgnoreCase(s)){
                String selectQue2="Select Acc_Balance from Account where Account_No="+account_no;
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery(selectQue2);
                double balance=0.0;
                while (rs2.next())
                {
                    balance=rs2.getDouble(1);
                }
                if((balance>1000)&&(witdrawal_amt<balance)) {
                    pay.withdrawal(account_no,witdrawal_amt);
                }
                else System.out.println("Insufficient Balance!!! \nWe can't accept your request");
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }

    public  void displayPayaccfundtransfer(String account_no)
    {
        Scanner scan=new Scanner(System.in);
        //System.out.println("Please Enter Your Account Number:");
        //=scan.next();
        System.out.println("Enter Account Number that you want to transfer fund:");
        String acc_num=scan.next();
        int acchk=validation.accvalid(account_no,acc_num);
        while(acchk==0)
        {
            System.out.println("Account Number is Invalid!!!");
            System.out.println("Enter Account Number that you want to transfer fund:");
            acc_num=scan.next();
            acchk=validation.accvalid(account_no,acc_num);
        }
        System.out.println("Enter the Amount ");
        double transfer_amt= scan.nextDouble();
        int amountchk=validation.amountvalid(transfer_amt);
        while (amountchk==0)
        {
            System.out.println("Amount is Invalid!");
            System.out.println("Enter the Amount ");
            transfer_amt= scan.nextDouble();
            amountchk=validation.amountvalid(transfer_amt);
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            //Statement st;
            String selectQue1="Select Account_No from Account where Account_No="+account_no;
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery(selectQue1);
            String s="";
            while (rs1.next()) {s=rs1.getString(1);}
            //System.out.println("Data retrieve Successfully");
            if(account_no.equalsIgnoreCase(s)){
                String selectQue2="Select Acc_balance from Account where Account_No="+account_no;
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery(selectQue2);
                double balance=0.0;
                while (rs2.next()) {balance=rs2.getDouble(1);}
               // System.out.println("Data retrieve Successfully");
                if((balance>1000)&&(transfer_amt<balance)) {
                 //   System.out.println("Enter into if block");
                    pay.fundtransfer(account_no,acc_num,transfer_amt);
                }
                else System.out.println("Insufficient Balance!!! \nWe can't accept your request");
            }
            else {System.out.println("Account Number is not Matched!!! Please Enter again!!!");}
        }catch (Exception e) {
            System.out.println("Error----" + e);
        }


    }

    public void displayCreateAccount(String account_no) {
        Scanner scan = new Scanner(System.in);
        Validation valid = new Validation();
        while (true) {
            System.out.println("Which type of Account do you want to create?");
            System.out.println("\n 1.Save Account \n 2.Pay Account  \n 3.Exit \n Enter your choice");
            int choice1 = scan.nextInt();
            switch (choice1) {
                case 1:
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
                        String selectq = "Select acc_type,emailid from account where account_no=" + account_no;
                        Statement st = con.createStatement();
                        ResultSet rs1 = st.executeQuery(selectq);
                        String acctype = "";
                        String email = "";
                        while (rs1.next()) {
                            acctype = rs1.getString(1);
                            email = rs1.getString(2);
                        }
                        System.out.println(acctype);
                        String selectq1 = "Select acc_type from account where emailid='" + email + "' and acc_type='save'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(selectq1);
                        String acctype1 = "";
                        // String email1 = "";
                        while (rs2.next()) {
                            acctype1 = rs2.getString(1);
                            //  email = rs1.getString(2);
                        }
                        System.out.println(acctype1);
                        if (!acctype1.equalsIgnoreCase("save") /*|| acctype1.equalsIgnoreCase(null)*/) {
                            System.out.println("You will need to deposit Rs.500 to activate the Save account");
                            System.out.println("How will you deposit the amount(via cash/cheque)?");
                            String paymentmode = scan.next();
                            Double opening_balance = 500.00;
                            System.out.println();
                            String acc_NO = save.displayaccount_no();
                            System.out.println("Your Account Number is:");
                            System.out.print(acc_NO);
                            System.out.println();
                            System.out.println("Set User ID to Your Account:");
                            String User_Id = scan.next();
                            int Ucheck = valid.checkUserId(User_Id);
                            while (Ucheck == 0)
                            {
                                System.out.println("This User ID is already exists. Please try with another one! ");
                                System.out.println("Set User ID to Your Account:");
                                User_Id = scan.next();
                                Ucheck =valid.checkUserId(User_Id);
                            }
                            System.out.println("Set Password to Your Account:");
                            String password = scan.next();
                            String Acc_type = "Save";

                            //insert values to the account table
                            String query = "insert into Account values(?,?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(query);
                            pst.setString(1, acc_NO);
                            pst.setString(2, email);
                            pst.setString(3, User_Id);
                            pst.setString(4, password);
                            pst.setString(5, Acc_type);
                            pst.setDouble(6, opening_balance);
                            int rows = pst.executeUpdate();
                            //insert values to the log table
                            String selectQue = "Select curdate()";
                            Statement st1 = con.createStatement();
                            ResultSet rs3 = st1.executeQuery(selectQue);
                            String date = "";
                            while (rs3.next()) {
                                date = rs3.getString(1);
                            }
                            String selectQue3 = "Select curtime()";
                            st1 = con.createStatement();
                            ResultSet rs = st1.executeQuery(selectQue3);
                            String time = "";
                            while (rs.next()) {
                                time = rs.getString(1);
                            }
                            int rows1;
                            String paymode = "Opening Balance";
                            String query1 = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
                            PreparedStatement pst1 = con.prepareStatement(query1);
                            pst1.setString(1, User_Id);
                            pst1.setString(2, acc_NO);
                            pst1.setString(3, date);
                            pst1.setString(4, time);
                            pst1.setString(5, paymode);
                            pst1.setString(6, null);
                            pst1.setDouble(7, opening_balance);
                            pst1.setDouble(8, opening_balance);
                            rows = pst1.executeUpdate();
                            displaySave_accServices(acc_NO);
                        } else System.out.println("You already have an Save account");
                    } catch (Exception e) {
                        System.out.println("Error----" + e);
                    }
                    break;
                case 2:
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
                        String selectq = "Select acc_type,emailid from account where account_no=" + account_no;
                        Statement st = con.createStatement();
                        ResultSet rs1 = st.executeQuery(selectq);
                        String acctype = "";
                        String email = "";
                        while (rs1.next()) {
                            acctype = rs1.getString(1);
                            email = rs1.getString(2);
                        }
                        System.out.println(acctype);
                        String selectq1 = "Select acc_type from account where emailid='" + email + "' and acc_type='pay'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(selectq1);
                        String acctype1 = "";
                        // String email1 = "";
                        while (rs2.next()) {
                            acctype1 = rs2.getString(1);
                            //  email = rs1.getString(2);
                        }
                        System.out.println(acctype1);
                        if (!acctype1.equalsIgnoreCase("pay") /*|| acctype1.equalsIgnoreCase(null)*/) {
                            System.out.println("You will need to deposit Rs.1000 to activate the Save account");
                            System.out.println("How will you deposit the amount(via cash/cheque)?");
                            String paymentmode = scan.next();
                            Double opening_balance = 1000.00;
                            System.out.println();
                            String acc_NO = pay.displayaccount_no();
                            System.out.println("Your Account Number is:");
                            System.out.print(acc_NO);
                            System.out.println();
                            System.out.println("Set User ID to Your Account:");
                            String User_Id = scan.next();
                            int Ucheck = valid.checkUserId(User_Id);
                            while (Ucheck == 0) {
                                System.out.println("This User ID is already exists. Please try with another one! ");
                                System.out.println("Set User ID to Your Account:");
                                User_Id = scan.next();
                                Ucheck = valid.checkUserId(User_Id);
                            }
                            System.out.println("Set Password to Your Account:");
                            String password = scan.next();
                            String Acc_type = "Pay";
                            //insert values to the account table
                            String query = "insert into Account values(?,?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(query);
                            pst.setString(1, acc_NO);
                            pst.setString(2, email);
                            pst.setString(3, User_Id);
                            pst.setString(4, password);
                            pst.setString(5, Acc_type);
                            pst.setDouble(6, opening_balance);
                            int rows = pst.executeUpdate();
                            //insert values to the log table
                            String selectQue = "Select curdate()";
                            Statement st1 = con.createStatement();
                            ResultSet rs3 = st1.executeQuery(selectQue);
                            String date = "";
                            while (rs3.next()) {
                                date = rs3.getString(1);
                            }
                            String selectQue3 = "Select curtime()";
                            st1 = con.createStatement();
                            ResultSet rs = st1.executeQuery(selectQue3);
                            String time = "";
                            while (rs.next()) {
                                time = rs.getString(1);
                            }
                            int rows1;
                            String paymode = "Opening Balance";
                            String query1 = "insert into Logs(User_Id,Account_No,Date,Time,Particulars,Withdrawal,Deposit,Balance) values(?,?,?,?,?,?,?,?)";
                            PreparedStatement pst1 = con.prepareStatement(query1);
                            pst1.setString(1, User_Id);
                            pst1.setString(2, acc_NO);
                            pst1.setString(3, date);
                            pst1.setString(4, time);
                            pst1.setString(5, paymode);
                            pst1.setString(6, null);
                            pst1.setDouble(7, opening_balance);
                            pst1.setDouble(8, opening_balance);
                            int rows2 = pst1.executeUpdate();
                            displayPay_accServices(acc_NO);
                        } else System.out.println("You already have an Pay account");
                    } catch (Exception e) {
                        System.out.println("Error----" + e);
                    }
                    break;
                case 3:
                    BankImpl.displaymainmenu();
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }
    }



    public String customer_details(){
        Validation valid=new Validation();
        Scanner scan = new Scanner(System.in);
        String s1="";
        System.out.println("Enter Personal Details:");
        System.out.println("Enter email Address:");
        String email = scan.nextLine();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue="select Emailid from customer where Emailid='"+email+"'";
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(selectQue);
            String emailchk="";
            while (rs.next())
            {
                emailchk=rs.getString(1);
            }
            if(emailchk.equalsIgnoreCase(null) || !(emailchk.equalsIgnoreCase(email))) {


        System.out.println("Enter Your First name:");
        String name = scan.next();
        System.out.println("Enter Your Last name:");
        String lastname = scan.next();
        System.out.println("Enter Your Age:");
        int age = scan.nextInt();
        scan.nextLine();
        while ((age<18)||(age>120)){
            System.out.println("Invalid Age! The valid age for creating a bank account is 18");
            System.out.println("Enter Your Age:");
            age = scan.nextInt();
            scan.nextLine();
        }
        System.out.println("Enter Your Date of Birth(YYYY-MM-DD):");
        String dob = scan.nextLine();
        int dobcheck=valid.isDOBValid(dob);
        while (dobcheck==0) {
            System.out.println("Date of Birth is Invalid!!! pLease check once and Write in Given Format");
            System.out.println("Enter Your Date of Birth(DD-MM-YYYY):");
            dob = scan.nextLine();
            dobcheck=valid.isDOBValid(dob);
        }

        System.out.println("Enter Your Gender:");
        String gender = scan.nextLine();
        System.out.println("Enter Your Phone Number in given Format(9874563210/+919874563210):");
        String phoneNo = scan.nextLine();
        int phonecheck=valid.isPhoneNoValid(phoneNo);
        int mobchk=validation.duplicatemobchk(phoneNo);
        while ((phonecheck==0)||(mobchk==0))
        {
            if(phonecheck==0)
            {
                System.out.println("Phone Number is Invalid!!! pLease check Once and Write in Given Format");
                System.out.println("Enter Your Phone Number in given Format(9874563210/+919874563210):");
                phoneNo = scan.nextLine();
                phonecheck=valid.isPhoneNoValid(phoneNo);
                mobchk=validation.duplicatemobchk(phoneNo);
            }
            if(mobchk==0)
            {
                System.out.println("This Mobile Number is already registered!!!");
                System.out.println("Enter Your Phone Number in given Format(9874563210/+919874563210):");
                phoneNo = scan.nextLine();
                mobchk=validation.duplicatemobchk(phoneNo);
                phonecheck=valid.isPhoneNoValid(phoneNo);
            }
        }

       /* while (mobchk==0)
        {
            System.out.println("This Mobile Number is already registered!!!");
            System.out.println("Enter Your Phone Number in given Format(9874563210/+919874563210):");
            phoneNo = scan.nextLine();
            mobchk=validation.duplicatemobchk(phoneNo);

        }*/

        System.out.println("Enter Aadhar Card Number:");
        String aadhar = scan.next();
        int aadharcheck=valid.isAadharValid(aadhar);
        int dupaadharchk=validation.duplicateaadharchk(aadhar);
                while ((aadharcheck==0)||(dupaadharchk==0))
                {
                    if(aadharcheck==0)
                    {
                        System.out.println("Aadhar Number is Invalid!!! pLease check Once and Write in Given Format");
                        System.out.println("Enter Aadhar Card Number:");
                        aadhar = scan.next();
                        aadharcheck=valid.isAadharValid(aadhar);
                        dupaadharchk=validation.duplicateaadharchk(aadhar);
                    }
                    if(dupaadharchk==0)
                    {
                        System.out.println("This Aadhar Number is already registered!!!");
                        System.out.println("Enter Aadhar Card Number:");
                        aadhar = scan.next();
                        dupaadharchk=validation.duplicateaadharchk(aadhar);
                        aadharcheck=valid.isAadharValid(aadhar);
                    }
                }

        System.out.println("Enter PAN card Number:");
        String pan = scan.next();
                int pancheck=valid.isPANValid(pan);
                int duppanchk=validation.duplicatePANchk(pan);
                while ((pancheck==0)||(duppanchk==0))
                {
                    if(pancheck==0)
                    {
                        System.out.println("PAN Number is Invalid!!! pLease check Once and Write in Given Format");
                        System.out.println("Enter PAN Card Number:");
                        pan = scan.next();
                        pancheck=valid.isPANValid(pan);
                        duppanchk=validation.duplicatePANchk(pan);
                    }
                    if(duppanchk==0)
                    {
                        System.out.println("This PAN Number is already registered!!!");
                        System.out.println("Enter PAN Card Number:");
                        pan = scan.next();
                        duppanchk=validation.duplicatePANchk(pan);
                        pancheck=valid.isPANValid(pan);
                    }
                }

                System.out.println("Enter Address Details:");
        System.out.println("Enter Your Door Number:");
        int doorno = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter a Street Name:");
        String streetname = scan.nextLine();
        System.out.println("Enter a City Name:");
        String cityname = scan.nextLine();
        System.out.println("Enter a State Name:");
        String statename = scan.nextLine();
        System.out.println("Enter a Country Name:");
        String countryname = scan.nextLine();
        System.out.println("Enter Pincode:");
        String pin = scan.next();
        scan.nextLine();


                String query = "";
                PreparedStatement pst;
                int rows;
               // System.out.println("inserted Successfully");
                query = "insert into customer(firstName,LastName,age,DOB,Gender,Emailid,Mobile_No,Aadhar_No,PAN_No,Door_No,Street_Name,City_Name,State_Name,Country_Name,PinCode) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, lastname);
                pst.setInt(3, age);
                pst.setString(4, dob);
                pst.setString(5, gender);
                pst.setString(6, email);
                pst.setString(7, phoneNo);
                pst.setString(8, aadhar);
                pst.setString(9, pan);
                pst.setInt(10, doorno);
                pst.setString(11, streetname);
                pst.setString(12, cityname);
                pst.setString(13, statename);
                pst.setString(14, countryname);
                pst.setString(15, pin);
                rows = pst.executeUpdate();
                //System.out.println("Inserted Successfully");
                String selectQue1 = "Select Emailid from customer where Emailid='" + email+"'";
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(selectQue1);

                while (rs1.next()) {
                    s1 = rs1.getString(1);
                }
            }else {System.out.println("You already have an account");BankImpl.displaymainmenu();}
        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
        System.out.println("!!! Thank you for providing your details !!!");
        return s1;
    }

  /*  public void insertaccNOintocustomer(String emailid,String User_ID)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String query5="set foreign_key_checks=0;";
            Statement st5 = con.createStatement();
            int rows5 = st5.executeUpdate(query5);
            System.out.println();
            String query = "update customer set User_ID='"+User_ID+"'"+" where Emailid='"+emailid+"'";
            Statement st = con.createStatement();
            int rows = st.executeUpdate(query);
           // System.out.println("Updated Successfully");
        } catch (Exception e) {
            System.out.println("Error----" + e);
        }
    }*/
    public void acc_History(String account_no)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IDBCBANK", "root", "TejasPatil143");
            String selectQue="Select Account_No from logs where Account_No="+account_no;
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(selectQue);
            String s="";
            while (rs.next())
            {
                s=rs.getString(1);
            }
            if(account_no.equalsIgnoreCase(s)) {
                String selectQue1 = "Select * from logs where Account_No=" + account_no;
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(selectQue1);
                System.out.printf("%5s"+"\t"+ "%8s"+"\t"+" %11s  %9s %13s %24s %20s %10s %15s\n", "Log_Id", "User_Id", "Account_No","Date","Time","Particulars","Withdrawal","Deposit","Balance");
                while (rs1.next()) {
                    System.out.println(rs1.getInt(1) + "\t"+"\t" +rs1.getString(2) + "\t"+ rs1.getLong(3) + "\t" + rs1.getDate(4) + "\t"+"\t" + rs1.getTime(5)
                            + "\t"+ "\t" + rs1.getString(6) + "\t"+ "\t"+ "\t"+ "\t" + rs1.getDouble(7) + "\t"+ "\t"+ "\t" + rs1.getDouble(8)+ "\t"+ "\t"+ "\t" + rs1.getDouble(9));
                }
            }
            else System.out.println("Account Number is not Matched!!! Please Enter again!!!");
            }catch (Exception e) {
                System.out.println("Error----" + e);
        }
    }
}
