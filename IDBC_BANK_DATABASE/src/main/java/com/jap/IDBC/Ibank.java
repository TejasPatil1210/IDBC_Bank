package com.jap.IDBC;

public interface Ibank {
    void checkbalance(String acc_no);
    void deposit(String acc_no,double deposit_amount,PaymentMode paymentMode);
    void withdrawal(String acc_no,double withdrawal_amount);
    void fundtransfer(String acc_no,String account_no,double transfer_Amount);
    //void fundtr
}
