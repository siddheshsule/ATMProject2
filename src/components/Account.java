package components;

import java.util.ArrayList;

public class Account {
    private String accountName;
    private double accountBalance;
    private String accountUuid;
    private  User accountHolder;
    private ArrayList<Transaction> transactions;
    /**
     * */

    public Account(String accountName, User accountHolder, Bank theBank) {
        this.accountName = accountName;
        this.accountHolder = accountHolder;
        this.accountUuid = theBank.getNewUserUUID();
        this.transactions = new ArrayList<Transaction>();
        accountHolder.addAccount(this);
        theBank.addAccount(this);

    }

    public String getAccountUuid() {
        return accountUuid;

    }


    public Object getSummaryLine() {
        // get the accounts balance
        double balance = this.getBalance();

        //format the summary line whethe rthe balance is negative
        if(balance >=0){
            return String.format("%s : %.02f : %s", this.accountUuid, balance, this.accountName);
        }
        return null;
    }

    public double getBalance() {
        double balance = 0;
        for(Transaction t: this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }
    /*
    * Printing the transaction history of the account
    * */
    public void printTransactionHistory(){
        System.out.printf("\nShowing transaction history for account %s\n", this.accountUuid);
        for(int i = this.transactions.size() - 1; i>=0; --i){
            System.out.printf(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }


}
