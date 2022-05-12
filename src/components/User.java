package components;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    // defining the USer related data
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;


    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
    // store the pin's MD5 hash, rather than the original value, for security reasons

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, caught NOSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // Empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log messages -- (By string formatting)
        System.out.printf("New user %s, %s with ID %s created.\n", this.lastName, this.firstName, this.uuid);


    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getUUID() {
        return uuid;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, caught NOSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
    return false;
    }

    public Object getFirstName() {
        return this.firstName;
    }

    public void printAccountSummary() {
        System.out.printf("\n\n%s's accounts summary", this.firstName);
        for(int i =0; i<this.accounts.size(); ++i){
            System.out.printf("%d), %s\n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();


    }


}
