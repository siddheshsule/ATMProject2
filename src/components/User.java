package components;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    // defining the User related data
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    /**
    * Constructor for User
     * @param : String firstName, String lastName, String pin, Bank theBank
     * Pin's MD5 is stored rather than the original value.
     * MD5 (message-digest algorithm) is a cryptographic protocol used for
     * authenticating messages as well as content verification and digital signatures.
     * MD5 is based on a hash function that verifies that a file you sent matches the
     * file received by the person you sent it to.
     *
     * Referece: https://www.avast.com/c-md5-hashing-algorithm#:~:text=What%20is%20MD5%3F,person%20you%20sent%20it%20to.
    * */
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
    /** Adds an account
     *
     * * */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }
    /** Getter for the UUID
     * */
    public String getUUID() {
        return uuid;
    }
    /**Validates the PIN
     * @return : Boolean
     * */
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
    /**A getter for the first name of user.
     * */
    public Object getFirstName() {
        return this.firstName;
    }
    /**Prints the account summary
     * */
    public void printAccountSummary() {
        System.out.printf("\n\n%s's accounts summary", this.firstName);
        for(int i =0; i<this.accounts.size(); ++i){
            System.out.printf("%d), %s\n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();


    }
    /**
     * Get the balance of the particular account
     * @param actIdx    the idx of the account to use
     * @return          the balance of the account
     * *
     * */

    public double getAcctBalance(int actIdx){
        return this.accounts.get(actIdx).getBalance();
    }


    public int numAccounts() {
        return this.accounts.size();
    }

    public void printTransactionHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransactionHistory();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);

    }

    public Object getAcctUuid(int acctIdx) {
        return this.accounts.get(acctIdx).getAccountUuid();
    }

    public void resetPin(int inputPin) {
        this.uuid = String.valueOf(inputPin);
    }
}
