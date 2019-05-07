import java.io.*;
import java.util.*;

public class FinalExam {
    public static void main(String[] args) throws FileNotFoundException {

        char choice;                             // menu item selected
        boolean not_done = true;

        ArrayList<SavingsAccount> accounts = new ArrayList<SavingsAccount>();

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
        readAccts(accounts);
        printAccts(accounts, outFile);

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
                    printAccts(accounts, outFile);
                    break;
                case 'b':
                case 'B':
                    getBalance(accounts, outFile, kybd);
                    break;
                case 'i':
                case 'I':
                    addInterest(accounts,outFile, kybd);
                    break;
                case 'd':
                case 'D':
                    makeDeposit(accounts,outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    makeWithdrawal(accounts,outFile,kybd);
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
        System.out.println("\t     B -- Account Inquiry");
        System.out.println("\t     D -- Deposit");
        System.out.println("\t     W -- Withdrawal");
        System.out.println("\t     I -- Add Interest");
        System.out.println("\t     Q -- Quit");
        System.out.println();
        System.out.print("\tEnter your selection: ");
    }

    public static void readAccts(ArrayList<SavingsAccount> accounts) throws FileNotFoundException {
        // open database input file
        // create File Object

        File dbFile = new File("input.txt");

        // create a Scanner Object
        Scanner sc = new Scanner(dbFile);

        while (sc.hasNext()) {
            int acctNum = sc.nextInt();
            Double acctBal = sc.nextDouble();

            accounts.add(new SavingsAccount(acctNum, acctBal));
        }

        // close the input file
        sc.close();
    }

    public static void printAccts(ArrayList<SavingsAccount> accounts, PrintWriter outFile) {
        outFile.println();
        outFile.println("**************************");
        outFile.println("\tDatabase of Accounts");
        outFile.println("**************************");
        outFile.println();
        outFile.printf("%-8s   %-8s  ", "Account", "Balance");
        outFile.println();
        outFile.print("--------------------");
        for (int index = 0; index < accounts.size(); index++) {
            outFile.println();
            outFile.printf("%-8s | $%7.2f ", accounts.get(index).getAcctNum(), accounts.get(index).getBalance());
        }
        outFile.println();
        outFile.println("--------------------");

        //flush the output file
        outFile.flush();

    }

    public static int findAcct(ArrayList<SavingsAccount> accounts, int requestedAccount) {

        for (int index = 0; index < accounts.size(); index++) {
            if (accounts.get(index).getAcctNum() == requestedAccount) {
                return index;
            }
        }
        return -1;
    }

    public static void getBalance(ArrayList<SavingsAccount> accounts, PrintWriter outFile, Scanner kybd) {
        System.out.println("Enter your Account Number: ");
        int requestedAccount = kybd.nextInt();

        int index = findAcct(accounts, requestedAccount);

        outFile.println("Transaction Requested: Account Inquiry");

        try {
            if (index == -1) {
                throw new AccountNotFound(requestedAccount);
            } else {
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Current Balance: $%.2f", accounts.get(index).getBalance());
                outFile.println();

            }
        } catch (AccountNotFound e) {
            outFile.println(e.getMessage());
        }
        outFile.println();
        outFile.flush();
    }

    public static void makeDeposit(ArrayList<SavingsAccount> accounts, PrintWriter outFile, Scanner kybd) {
        System.out.println("Enter your Account Number: ");
        int requestedAccount = kybd.nextInt();

        int index = findAcct(accounts, requestedAccount);

        try {
            if (index == -1) {
                throw new AccountNotFound(requestedAccount);
            } else {
                double amountToDeposit = kybd.nextDouble();

                accounts.get(index).makeDeposit(amountToDeposit, outFile);

            }
        } catch (AccountNotFound e) {
            outFile.println(e.getMessage());
        }
        outFile.println();
        outFile.flush();
    }

    public static void makeWithdrawal (ArrayList<SavingsAccount> accounts, PrintWriter outFile, Scanner kybd) {
        System.out.println("Enter your Account Number: ");
        int requestedAccount = kybd.nextInt();

        int index = findAcct(accounts, requestedAccount);

        try {
            if (index == -1) {
                throw new AccountNotFound(requestedAccount);
            } else {
                double amountToWithdraw = kybd.nextDouble();

                accounts.get(index).makeWithdrawal(amountToWithdraw, outFile);
            }
        } catch (AccountNotFound e) {
            outFile.println(e.getMessage());
        }
        outFile.println();
        outFile.flush();
    }

    public static void addInterest(ArrayList<SavingsAccount> accounts, PrintWriter outFile, Scanner kybd) {
        System.out.println("Enter your Account Number: ");
        int requestedAccount = kybd.nextInt();
        double rate = 8.76;
        int index = findAcct(accounts, requestedAccount);

        outFile.println("Transaction: Add Interest");
        try {
            if (index == -1) {
                throw new AccountNotFound(requestedAccount);
            } else {
                accounts.get(index).addInterest(rate,outFile);
            }
        } catch (AccountNotFound e) {
            outFile.println(e.getMessage());
        }
        outFile.println();
        outFile.flush();
    }
}
