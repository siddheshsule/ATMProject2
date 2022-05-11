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
}
