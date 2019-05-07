import java.io.*;
import java.util.*;

public class BankAccounts {

    public static void main(String... args) throws IOException {

        //MUHAMMAD-AZHAR CISC 3115-MW9
        //constant definitions

        Bank bank = new Bank();
        DateInfo dateInfo = new DateInfo(Calendar.getInstance());

        final int MAX_NUM = 50;

        //variable declarations
        char choice;									//menu item selected
        boolean not_done = true;						//loop control flag

        // open input test cases file
        //File testFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/testCases.txt");
        //File testFile = new File("testCases.txt");

        //create Scanner objec
        //Scanner kybd = new Scanner(testFile);
        Scanner kybd = new Scanner(System.in);

        // open the output file
        //PrintWriter outFile = new PrintWriter("/Users/Xiao/IdeaProjects/BankAccount_1/output.txt");
        //PrintWriter outFile = new PrintWriter("output.txt");
        PrintWriter outFile = new PrintWriter(System.out);

        outFile.println("MUHAMMAD-AZHAR \nCISC 5115-MW9\n");
        /* first part */
        /* fill and print initial database */
        int totalAccounts = bank.readAccts(bank);
        bank.printAccts(bank,totalAccounts,outFile);

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
                    bank.printAccts(bank, totalAccounts, outFile);
                    break;
                case 'b':
                case 'B':
                    bank.balance(bank, totalAccounts ,outFile,kybd);
                    break;
                case 'd':
                case 'D':
                    bank.deposit(bank, dateInfo, totalAccounts,outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    bank.withdrawal(bank, dateInfo, totalAccounts,outFile,kybd);
                    break;
                case 'c':
                case 'C':
                    bank.clearCheck(bank,dateInfo, totalAccounts,outFile,kybd);
                    break;
                case 'i':
                case 'I':
                    bank.accountInfo(bank, totalAccounts, outFile, kybd);
                    break;
                case 'n':
                case 'N':
                    bank.newAcct(bank, totalAccounts, outFile, kybd);
                    if (bank.success == true){
                        totalAccounts+=1;
                    }
                    break;
                case 'x':
                case 'X':
                    bank.deleteAcct(bank, totalAccounts, outFile, kybd);
                    if(bank.success == true){
                        totalAccounts--;
                    }
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
     *  Prints the menu of transaction choicesimport java.io.*;
import java.util.*;

public class BankAccounts {

    public static void main(String... args) throws IOException {

        //MUHAMMAD-AZHAR CISC 3115-MW9
        //constant definitions

        Bank bank = new Bank();
        DateInfo dateInfo = new DateInfo(Calendar.getInstance());

        final int MAX_NUM = 50;

        //variable declarations
        char choice;									//menu item selected
        boolean not_done = true;						//loop control flag

        // open input test cases file
        //File testFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/testCases.txt");
        //File testFile = new File("testCases.txt");

        //create Scanner objec
        //Scanner kybd = new Scanner(testFile);
        Scanner kybd = new Scanner(System.in);

        // open the output file
        //PrintWriter outFile = new PrintWriter("/Users/Xiao/IdeaProjects/BankAccount_1/output.txt");
        //PrintWriter outFile = new PrintWriter("output.txt");
        PrintWriter outFile = new PrintWriter(System.out);

        outFile.println("MUHAMMAD-AZHAR \nCISC 5115-MW9\n");
        /* first part */
    /* fill and print initial database */
    int totalAccounts = bank.readAccts(bank);
        bank.printAccts(bank,totalAccounts,outFile);

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
                bank.printAccts(bank, totalAccounts, outFile);
                break;
            case 'b':
            case 'B':
                bank.balance(bank, totalAccounts ,outFile,kybd);
                break;
            case 'd':
            case 'D':
                bank.deposit(bank, dateInfo, totalAccounts,outFile,kybd);
                break;
            case 'w':
            case 'W':
                bank.withdrawal(bank, dateInfo, totalAccounts,outFile,kybd);
                break;
            case 'c':
            case 'C':
                bank.clearCheck(bank,dateInfo, totalAccounts,outFile,kybd);
                break;
            case 'i':
            case 'I':
                bank.accountInfo(bank, totalAccounts, outFile, kybd);
                break;
            case 'n':
            case 'N':
                bank.newAcct(bank, totalAccounts, outFile, kybd);
                if (bank.success == true){
                    totalAccounts+=1;
                }
                break;
            case 'x':
            case 'X':
                bank.deleteAcct(bank, totalAccounts, outFile, kybd);
                if(bank.success == true){
                    totalAccounts--;
                }
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
        System.out.println("Select one of the following transactions:");
        System.out.println("\t****************************");
        System.out.println("\t    List of Choices         ");
        System.out.println("\t****************************");
        System.out.println("\t     W -- Withdrawal");
        System.out.println("\t     D -- Deposit");
        System.out.println("\t     C -- Clear Check");
        System.out.println("\t     N -- New Account");
        System.out.println("\t     B -- Balance Inquiry");
        System.out.println("\t     I -- Account Info");
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
        tempstr = keyboard.nextLine();		//flush previous ENTER
        tempstr = keyboard.nextLine();		//wait for ENTER
    }
}
     */
    public static void menu() {
        System.out.println();
        System.out.println("Select one of the following transactions:");
        System.out.println("\t****************************");
        System.out.println("\t    List of Choices         ");
        System.out.println("\t****************************");
        System.out.println("\t     W -- Withdrawal");
        System.out.println("\t     D -- Deposit");
        System.out.println("\t     C -- Clear Check");
        System.out.println("\t     N -- New Account");
        System.out.println("\t     B -- Balance Inquiry");
        System.out.println("\t     I -- Account Info");
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
        tempstr = keyboard.nextLine();		//flush previous ENTER
        tempstr = keyboard.nextLine();		//wait for ENTER
    }
}