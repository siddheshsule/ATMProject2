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
