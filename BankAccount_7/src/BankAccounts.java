import java.io.*;
import java.util.*;

public class BankAccounts {

    public static void main(String... args) throws IOException {

        //MUHAMMAD-AZHAR CISC 3115-MW9
        //constant definitions
        Bank bank = new Bank();
        DateInfo dateInfo = new DateInfo(Calendar.getInstance());
        boolean success = false;

        //variable declarations
        char choice;
        boolean not_done = true;
        boolean transactionHistory = false;

        // open input test cases file
        //File testFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/testCases.txt");
        File testFile = new File("testCases.txt");

        //create Scanner object
        Scanner kybd = new Scanner(testFile);
        //Scanner kybd = new Scanner(System.in);

        // open the output file
        //PrintWriter outFile = new PrintWriter("/Users/Xiao/IdeaProjects/BankAccount_1/output.txt");
        PrintWriter outFile = new PrintWriter("output.txt");
        //PrintWriter outFile = new PrintWriter(System.out);

        outFile.println("MUHAMMAD-AZHAR \nCISC 5115-MW9\n");
        /* first part */
        /* fill and print initial database */
        bank.readAccts();
        bank.printAccts(bank,outFile);

        /* second part */
        /* prompts for a transaction and then */
        /* call functions to process the requested transaction */
        do {
            menu();
            choice = kybd.next().charAt(0);
            switch(choice) {
                case 'q':
                case 'Q':
                    not_done = false;
                    bank.printAccts(bank, outFile);
                    bank.printAllTransactions(bank, outFile);
                    bank.printTotalBalance(outFile);
                    break;
                case 'b':
                case 'B':
                    bank.balance(outFile,kybd);
                    break;
                case 'd':
                case 'D':
                    bank.deposit(dateInfo,outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    bank.withdrawal(dateInfo, outFile,kybd);
                    break;
                case 'c':
                case 'C':
                    bank.clearCheck(dateInfo,outFile,kybd);
                    break;
                case 'i':
                case 'I':
                    bank.accountInfo(transactionHistory, outFile, kybd);
                    break;
                case 'h':
                case 'H':
                    transactionHistory = true;
                    bank.accountInfo(transactionHistory, outFile,kybd);
                    break;
                case 'n':
                case 'N':
                    bank.newAcct(outFile, kybd);
                    break;
                case 'S':
                case 's':
                    bank.closeAccount(outFile,kybd);
                    break;
                case 'R':
                case 'r':
                    bank.reopenAccount(outFile,kybd);
                    break;
                case 'x':
                case 'X':
                    bank.deleteAcct(outFile, kybd);
                    break;
                default:
                    outFile.println("Error: \"" + choice + "\" is an invalid selection -  try again");
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
        System.out.println("****************************");
        System.out.println("List of Choices         ");
        System.out.println("****************************");
        System.out.println("Select one of the following transactions:\n");
        System.out.println("W -- Withdrawal");
        System.out.println("D -- Deposit");
        System.out.println("C -- Clear Check");
        System.out.println("N -- New Account");
        System.out.println("B -- Balance Inquiry");
        System.out.println("I -- Account Info");
        System.out.println("H -- Account Info with Transaction History");
        System.out.println("S -- Close Account");
        System.out.println("R -- Reopen a closed Account");
        System.out.println("X -- Delete Account");
        System.out.println("Q -- Quit");
        System.out.println();
        System.out.print("Enter your selection: ");
    }

    /* Method pause() */
    public static void pause(Scanner keyboard) {
        String tempstr;
        System.out.println();
        System.out.print("press ENTER to continue");
        tempstr = keyboard.nextLine();		//flush previous ENTER
        tempstr = keyboard.nextLine();		//wait for ENTER
    }
}