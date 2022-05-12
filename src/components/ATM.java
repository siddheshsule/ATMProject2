package components;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Bank of Radolfzell");
        User aUser = theBank.addUser("John", "Doe", "1234");
        Account newAccount = new Account("Saving", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        User currentUser;

        while (true){
            //stay in the login prompt until successful login
            currentUser = ATM.mainMenuPrompt(theBank, sc);

            //Stay in main menu until user quits
            ATM.printUserMenu(currentUser, sc);
        }

    }

    private static void printUserMenu(User currentUser, Scanner sc) {
        //Print a summary of the user account
        currentUser.printAccountSummary();
        // init
        int choice;
        //User menu
        do{
            System.out.printf("Welcome %s, what would you like to do? (Please enter your choice)",
                    currentUser.getFirstName());
            System.out.println("\n1. Show transaction history");
            System.out.println(" 2. Withdraw");
            System.out.println(" 3. Deposit");
            System.out.println(" 4. Transfer");
            System.out.println(" 5. Change PIN");
            System.out.println(" 6. Quit");
            System.out.println();
            System.out.println(" Enter the choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 6 ){
                System.out.println("Please enter a valid choice (1-6)");
            }
        }while (choice <1 || choice > 5);

        switch (choice){

        case 1:
            ATM.showTransactionHistory(currentUser, sc);
            break;
        case 2:
            ATM.withdrawFunds(currentUser, sc);
            break;
        case 3:
            ATM.depositFunds(currentUser, sc);
            break;
        case 4:
            ATM.transferFunds(currentUser, sc);
            break;
        case 5:
            ATM.changePin(currentUser, sc);
            break;
        }

        // re-display the menu unless the user wants to quit....(The recursive way)
        if(choice !=5){
            ATM.printUserMenu(currentUser, sc);
        }
    }

    private static void changePin(User currentUser, Scanner sc) {
        System.out.println("Please enter your current pin: ");
        int inputPin = sc.nextInt();
        if(currentUser.getUUID().equals(inputPin)){
            System.out.println("Please enter a new 4 digit pin: ");
            inputPin = sc.nextInt();
            currentUser.resetPin(inputPin);
        }
    }

    private static void transferFunds(User currentUser, Scanner sc) {
        //init
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", currentUser.numAccounts());
            fromAcct = sc.nextInt();
            if(fromAcct < 0 || fromAcct >= currentUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while (fromAcct < 0 || fromAcct>= currentUser.numAccounts());
        int actIdx = 0;
        acctBal = currentUser.getAcctBalance(fromAcct);

        // get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", currentUser.numAccounts());
            toAcct = sc.nextInt();
            if(toAcct < 0 || toAcct >= currentUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while (toAcct < 0 || toAcct>= currentUser.numAccounts());

        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max €%.02f): € ", acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }else if(amount > acctBal){
                System.out.printf("Amount must be greater than\n" +
                        "balance of €%.02f.\n", acctBal);
            }

        }while (amount <0 || amount > acctBal);

        currentUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s",
                currentUser.getAcctUuid(fromAcct)));

        
    }

    public static void showTransactionHistory(User currentUser, Scanner sc) {
        int theAcct;
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    " whose transactions you want to see.", currentUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct < 0 || theAcct >= currentUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(theAcct <0 || theAcct >= currentUser.numAccounts());

        //print the transaction history

        currentUser.printTransactionHistory(theAcct);


    }

    private static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        do{

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.println("Enter the userID: ");
            userID = sc.nextLine();
            System.out.println("Enter the pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if(authUser == null){
                System.out.println("Incorrect credentials " + "Please try again.");
            }

        }while (authUser == null);
        return authUser;
    }

    public static void withdrawFunds(User currentUser, Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        String memo;


        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", currentUser.numAccounts());
            fromAcct = sc.nextInt();
            if(fromAcct < 0 || fromAcct >= currentUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while (fromAcct < 0 || fromAcct>= currentUser.numAccounts());
        int actIdx = 0;
        acctBal = currentUser.getAcctBalance(fromAcct);

        do{
            System.out.printf("Enter the amount to transfer (max €%.02f): € ", acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }else if(amount > acctBal){
                System.out.printf("Amount must be greater than\n" +
                        "balance of €%.02f.\n", acctBal);
            }

        }while (amount <0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdraw
        currentUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    public static void depositFunds(User currentUser, Scanner sc){
        int toAcct;
        double amount;
        double acctBal;
        String memo;


        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit in: ", currentUser.numAccounts());
            toAcct = sc.nextInt();
            if(toAcct < 0 || toAcct >= currentUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while (toAcct < 0 || toAcct>= currentUser.numAccounts());
        acctBal = currentUser.getAcctBalance(toAcct);

        do{
            System.out.printf("Enter the amount to transfer (max €%.02f): € ", acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }

        }while (amount <0);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdraw
        currentUser.addAcctTransaction(toAcct, amount, memo);
    }
}
