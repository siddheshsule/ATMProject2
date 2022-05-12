package components;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Radolfzell");

        User aUser = theBank.addUser("Siddhesh", "Sule", "224368");
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
        //Printa a summary of the user account
        currentUser.printAccountSummary();

        // init
        int choice;

        //User menu

        do{
            System.out.printf("Welcome %s, what would you like to do? (Please enter your choice)",
                    currentUser.getFirstName());
            System.out.println(" 1. Show transaction history");
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
            ATM.despositFunds(currentUser, sc);
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

    public static void showTransactionHistory(User currentUser, Scanner sc) {
        int theAcct;
        do{
            System.out.printf("Enter the number (1-%d) of th")
        }

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
                System.out.println("Incorrect credentials" + "Please try again.");
            }

        }while (authUser == null);
        return authUser;
    }


}
