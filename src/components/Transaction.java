package components;

import java.util.Date;

public class Transaction {
    // The amount of this transaction
    private double amount;
    // The date and time of the transaction
    private Date timeStamp;
    // The memo for this transaction
    private  String memo;
    // The account in whihc the transaction was performed
    private  Account inAccount;


    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timeStamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) {
        this(amount,inAccount);
        this.memo = memo;
    }


    public double getAmount() {
        return this.amount;

    }

    public String getSummaryLine() {
        if(this.amount >= 0){
            return String.format("%s : $%0.2f : %s",
                    this.timeStamp.toString(),
                    this.amount, this.memo);

        }else{
            return String.format("%s : $(%0.2f) : %s",
                    this.timeStamp.toString(),
                    this.amount,
                    this.memo);
        }
    }
}
