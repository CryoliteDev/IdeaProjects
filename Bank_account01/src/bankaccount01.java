import java.io.*;
import java.util.*;

public class BankAccount {
    public static void main(String... args) throws IOException {
//MUHAMMAD-AZHAR CISC 3315-MW9
//constant definitions
        final int MAX_NUM = 50;
//variable declarations
        char choice; // menu item selected
        boolean not_done = true; // loop control flag
        int MAX_ACCTS = 50;
        Account[] accounts = new Account[MAX_ACCTS]; // holds all the account
        int totalAccounts = readAccts(accounts, MAX_ACCTS);
// open input test cases file
        File testFile = new File("testCases.txt");
//create Scanner object
        Scanner kybd = new Scanner(testFile);
//Scanner kybd = new Scanner(System.in);
// open the output file
        PrintWriter outFile = new PrintWriter("output.txt");
//PrintWriter outFile = new PrintWriter(System.out);
        outFile.println("MUHAMMAD-AZHAR \nCISC 3115-MW9");
        /* first part */
        /* fill and print initial database */
        printAccts(accounts, totalAccounts, outFile);
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
                    printAccts(accounts, totalAccounts, outFile);
                    break;
                case 'b':
                case 'B':
                    balance(accounts, totalAccounts, outFile, kybd);
                    break;
                case 'i':
                case 'I':
                    accountInfo(accounts, totalAccounts, outFile, kybd);
                case 'd':
                case 'D':
                    deposit(accounts, totalAccounts, outFile, kybd);
                    break;
                case 'w':
                case 'W':
                    withdrawal(accounts, totalAccounts, outFile, kybd);
                    break;
                case 'n':
                case 'N':
                    totalAccounts = newAcct(accounts, totalAccounts, outFile, kybd);
                    break;
                case 'x':
                case 'X':
                    totalAccounts = deleteAcct(accounts, totalAccounts, outFile, kybd);
                    break;
                default:
                    outFile.println("\nError: " + choice + " is an invalid selection - try again");
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

    /* Method readAccts()
     * Input:
     * Account[] accounts - reference to array of account
     * maxAccts - maximum number of active accounts allowed
     * Process:
     * Reads the initial database of accounts and balances
     * Output:
     * Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(Account[] accounts, int maxAccts) throws IOException {
// open database input file
// create File Object
        File dbFile = new File("input.txt");
// create a Scanner Object
        Scanner sc = new Scanner(dbFile);
        int totalAccountsReadIn = 0;
        while (sc.hasNext() && totalAccountsReadIn <= 50) {
            String fName = sc.next();
            String lName = sc.next();
            String SSN = sc.next();
            int acctNum = Integer.parseInt(sc.next());
            String acctType = sc.next();
            Double acctBal = Double.parseDouble(sc.next());
            accounts[totalAccountsReadIn] = new Account(fName, lName, SSN, acctNum, acctType, acctBal);
            totalAccountsReadIn++;
        }
// close the input file
        sc.close();
//return the account number count
        return totalAccountsReadIn;
    }

    /* Method printAccts:
     * Input:
     * Account account - array of account numbers
     * numAccts - number of active accounts
     * outFile - reference to the output file
     * Process:
     * Prints the database of accounts and balances
     * Output:
     * Prints the database of accounts and balances
     */
    public static void printAccts(Account[] accounts, int numAccts, PrintWriter outFile) {
        outFile.println();
        outFile.println("\t\t\t\t\t\t\tDatabase of Account Accounts");
        outFile.println();
        outFile.printf("%-12s %-12s %-12s %-10s %-8s %-14s ",
                "First Name", "Last Name", "Account Type", "SSN", "Account", "Balance");
        for (int index = 0; index < numAccts; index++) {
            outFile.println();
            outFile.printf("%-12s | %-12s | %-12s | %-10s | %-8s | $%7.2f ", accounts[index].getfName(),
                    accounts[index].getlName(), accounts[index].getAcctType(), accounts[index].getSSN(),
                    accounts[index].getAcctNum(), accounts[index].getAcctBal());
        }
        outFile.println();
//flush the output file
        outFile.flush();
    }

    /* Method menu()
     * Input:
     * none
     * Process:
     * Prints the menu of transaction choices
     * Output:
     * Prints the menu of transaction choices
     */
    public static void menu() {
        System.out.println();
        System.out.println("Select one of the following transactions:");
        System.out.println("\t****************************");
        System.out.println("\t List of Choices ");
        System.out.println("\t****************************");
        System.out.println("\t W -- Withdrawal");
    }
}