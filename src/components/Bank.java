package components;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private  String bankName;
    private ArrayList<User> users;
    private  ArrayList<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }


    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // continue looping until we get a unique ID
        do{
            // generate the number
            uuid = "";
            for(int c= 0; c<len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();


            }
            //Check to make sure it is unique
            nonUnique = false;
            for(User u: this.users){
                if(uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while (nonUnique);
        return uuid;
    }

    public  String getNewAccountUUID(){
        String AccountUuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // continue looping until we get a unique ID
        do{
            // generate the number
            AccountUuid = "";
            for(int c= 0; c<len; c++){
                AccountUuid += ((Integer)rng.nextInt(10)).toString();


            }
            //Check to make sure it is unique
            nonUnique = false;
            for(Account a: this.accounts){
                if(AccountUuid.compareTo(a.getAccountUuid()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while (nonUnique);
        return AccountUuid;

    }
    /*
    * Add an Account
    * @param account: The account to be added
    * */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public User addUser(String firstName, String lastName, String pin){
        // Acreate a new user object and add to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        return newUser;
    }

    public User userLogin(String userId, String pin){

        // search through the liust of users
        for(User u: users){
            if(u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)){
                return  u;
            }
        }

        // if we havent foudn the user or have incorrect pin
        return  null;

    }

    public Object getName() {
        return bankName;
    }
}
