import java.io.*;
import java.util.*;

public class BankAccount {
    //MUHAMMAD-AZHAR
    public static void main(String... args) throws IOException {
        Bank bank = new Bank();

        //constant definitions
        int totalAccountsReadIn = 0;
        //variable declarations
        char choice;                                    // menu item selected
        boolean not_done = true;

        //readAccts(bank);
        // open input test cases file
        File testFile = new File("testCases.txt");

        //create Scanner object
        Scanner kybd = new Scanner(testFile);
        //Scanner kybd = new Scanner(System.in);

        // open the output file
        PrintWriter outFile = new PrintWriter("output.txt");
        //PrintWriter outFile = new PrintWriter(System.out);
        int totalAccounts = bank.readAccts(bank, totalAccountsReadIn, outFile);
        outFile.println("MUHAMMAD-AZHAR");
        /* first part */
        /* fill and print initial database */
        bank.printAccts(bank, outFile);

        /* second part */
        /* prompts for a transaction and then */
        /* call functions to process the requested transaction */
        do {
            menu();
            choice = kybd.next().charAt(0);
            switch (choice) {
                case 'q':
                case 'Q':
                    not_done = false;
                    //print all trans
                    bank.getAccounts().get(0).getTrans().get(0).getTransaction().printTransactions(bank,
                            -1, outFile);
                    // print total bal
                    bank.printTotalBalance(outFile);
                    break;
                case 'b':
                case 'B':
                    bank.balance(bank, outFile, kybd);
                    break;
                case 'i':
                case 'I':
                    bank.accountInfo(bank, totalAccounts, false , outFile, kybd);
                    break;
                case 'd':
                case 'D':
                    bank.deposit(bank, totalAccounts, outFile, kybd);
                    break;
                case 'c':
                case 'C':
                    totalAccounts= bank.close(bank, totalAccountsReadIn,outFile,kybd);
                    break;
                case 'h':
                case 'H':
                    bank.accountInfo(bank, totalAccountsReadIn,true,outFile,kybd);
                    break;
                case 'r':
                case 'R':
                    totalAccounts = bank.Open(bank, totalAccountsReadIn, outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    bank.withdrawal(bank, totalAccountsReadIn, outFile, kybd);
                    break;
                case 'n':
                case 'N':
                    //the if statement for the boolean goes here.
                    bank.newAcct(bank, totalAccountsReadIn, outFile, kybd);
//                    if (bank.success == true){
//                        totalAccounts+=1;
//                    }
                    break;
                case 'x':
                case 'X':
                    bank.deleteAcct(bank, totalAccountsReadIn, outFile, kybd);
//                    if(bank.success == true){
//                        bank.getAccounts().remove(index);
//                    }
                    break;
                default:
                    outFile.println("\nError: " + choice + " is an invalid selection -  try again");
                    outFile.println();
                    outFile.flush();
                    break;
            }
            // give user a chance to look at output before printing menu
            //pause(kybd);
        } while (not_done);

        //close the output file
        outFile.close();

        //close the test cases input file
        kybd.close();

        System.out.println();
        System.out.println("The program is terminating");
    }


    /* Method menu()
     * Input:
     *  none
     * Process:
     *  Prints the menu of transaction choices
     * Output:
     *  Prints the menu of transaction choices
     */
    public static void menu() {
        System.out.println();
        System.out.println("Select one of the following transactions:");
        System.out.println("\t****************************");
        System.out.println("\t    List of Choices         ");
        System.out.println("\t****************************");
        System.out.println("\t     W -- Withdrawal");
        System.out.println("\t     D -- Deposit");
        System.out.println("\t     N -- New Account");
        System.out.println("\t     O -- UnFreeze An Account");
        System.out.println("\t     B -- Balance Inquiry");
        System.out.println("\t     I -- Account Info");
        System.out.println("\t     H -- Account Info and Transaction History");
        System.out.println("\t     C -- Freeze An Account");
        System.out.println("\t     X -- Delete Account");
        System.out.println("\t     Q -- Quit");
        System.out.println();
        System.out.print("\tEnter your selection: ");
    }

    /* Method pause() */
    public static void pause(Scanner keyboard) {
        String tempstr;
        System.out.println();
        System.out.print("press ENTER to continue");
        tempstr = keyboard.nextLine();        //flush previous ENTER
        tempstr = keyboard.nextLine();        //wait for ENTER
    }
}