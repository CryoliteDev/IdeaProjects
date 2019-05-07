import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HW {


    public static void main(String... args) {

        int MAX_ACCTS = 50;


        BankAccount[MAX_ACCTS] bankAccounts;

        int totalAccounts = readAccts(bankAccounts, MAX_ACCTS);

        //MUHAMMAD-AZHAR CISC 3315-MW9
        //constant definitions
        final int MAX_NUM = 50;

        //variable declarations
        int[] acctNumArray = new int[MAX_NUM];            //array of account numbers
        double[] balanceArray = new double[MAX_NUM];    //array of balances
        int numAccts;                                    //number of accounts
        char choice;                                    //menu item selected
        boolean not_done = true;                        //loop control flag
        String[] bankAccount = new String[MAX_NUM];

        // open input test cases file
        //File testFile = new File("/bc/cisc3115/pgms/chapter_00/prj00_01BankAccounts/mytestcases.txt");
        File testFile = new File("testCases.txt");

        //create Scanner object
        Scanner kybd = new Scanner(testFile);
        //Scanner kybd = new Scanner(System.in);

        // open the output file
        //PrintWriter outFile = new PrintWriter("/bc/cisc3115/pgms/chapter_00/prj00_01BankAccounts/myoutput.txt");
        PrintWriter outFile = new PrintWriter("output.txt");
        //PrintWriter outFile = new PrintWriter(System.out);

        outFile.println("MUHAMMAD-AZHAR \nCISC 3115-MW9");
        /* first part */
        /* fill and print initial database */
        numAccts = readAccts(bankAccount,acctNumArray, balanceArray, MAX_NUM);
        printAccts(acctNumArray, balanceArray, numAccts, outFile);



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
                    printAccts(acctNumArray, balanceArray, numAccts, outFile);
                    break;
                case 'b':
                case 'B':
                    balance(acctNumArray, balanceArray, numAccts, outFile, kybd);
                    break;
                case 'd':
                case 'D':
                    deposit(acctNumArray, balanceArray, numAccts, outFile, kybd);
                    break;
                case 'w':
                case 'W':
                    withdrawal(acctNumArray, balanceArray, numAccts, outFile, kybd);
                    break;
                case 'n':
                case 'N':
                    numAccts = newAcct(acctNumArray, balanceArray, numAccts, outFile, kybd);
                    break;
                case 'x':
                case 'X':
                    numAccts = deleteAcct(acctNumArray, balanceArray, numAccts, outFile, kybd);
                    break;
                default:
                    outFile.println("\nError: " + choice + " is an invalid selection -  try again");
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
     *  acctNumArray - reference to array of account numbers
     *  balanceArray - reference to array of account balances
     *  maxAccts - maximum number of active accounts allowed
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(BankAccount bankAccount[], int maxAccts) throws IOException {
        // open database input file
        // create File Object

        File dbFile = new File("input.txt");

        // create a Scanner Object
        Scanner sc = new Scanner(dbFile);

        int totalAccountsReadIn = 0;

        while(sc.hasNext() && totalAccountsReadIn <= 50){

            String fName =  sc.next();
            String lName = sc.next();
            String SSN = sc.next();
            int acctNum = Integer.parseInt(sc.next());
            String acctType = sc.next();
            Double acctBal = Double.parseDouble(sc.next());



            BankAccount temp = new BankAccount(fName,lName, SSN, acctNum, acctType, acctBal);

            bankAccount[0] = temp;

            for(int i = 0; i < totalAccountsReadIn; i++){

            }

            totalAccountsReadIn++;
        }

        // close the input file
        sc.close();

        //return the account number count
        return totalAccountsReadIn;
    }

    /* Method printAccts:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
     *  outFile - reference to the output file
     * Process:
     *  Prints the database of accounts and balances
     * Output:
     *  Prints the database of accounts and balances
     */
    public static void printAccts(int[] acctNumArray, double[] balanceArray, int numAccts,
                                  PrintWriter outFile) {
        outFile.println();
        outFile.println("\t\tDatabase of Bank Accounts");
        outFile.println();
        outFile.println("Account   Balance");
        for (int index = 0; index < numAccts; index++) {
            outFile.printf("%7d  $%7.2f", acctNumArray[index], balanceArray[index]);
            outFile.println();
        }
        outFile.println();

        //flush the output file
        outFile.flush();
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
        System.out.println("\t     B -- Balance Inquiry");
        System.out.println("\t     X -- Delete Account");
        System.out.println("\t     Q -- Quit");
        System.out.println();
        System.out.print("\tEnter your selection: ");
    }

    /* Method findAcct:
     * Input:
     *  acctNumArray - array of account numbers
     *  numAccts - number of active accounts
     *  requestedAccount - requested account requested_number
     * Process:
     *  Performs a linear search on the acctNunArray for the requested account
     * Output:
     *  If found, the index of the requested account is returned
     *  Otherwise, returns -1
     */
    public static int findAcct(int[] acctNumArray, int numAccts, int requestedAccount) {
        for (int index = 0; index < numAccts; index++)
            if (acctNumArray[index] == requestedAccount)
                return index;
        return -1;
    }

    /* Method balance:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
     *  outFile - reference to output file
     *  kybd - reference to the "test cases" input file
     * Process:
     *  Prompts for the requested account
     *  Calls findAcct() to see if the account exists
     *  If the account exists, the balance is printed
     *  Otherwise, an error message is printed
     * Output:
     *  If the account exists, the balance is printed
     *  Otherwise, an error message is printed
     */
    public static void balance(int[] acctNumArray, double[] balanceArray, int numAccts,
                               PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;

        outFile.println();
        System.out.print("Enter the account number: ");            //prompt for account number
        requestedAccount = kybd.nextInt();                        //read-in the account number

        System.out.print("request is" + requestedAccount);

        //call findAcct to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        if (index == -1)                                        //invalid account
        {
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else                                                    //valid zccount
        {
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.printf("Current Balance: $%.2f", balanceArray[index]);
            outFile.println();
        }
        outFile.println();

        outFile.flush();                //flush the output buffer
    }

    /* Method deposit:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
     *  outFile - reference to the output file
     *  kybd - reference to the "test cases" input file
     * Process:
     *  Prompts for the requested account
     *  Calls findacct() to see if the account exists
     *  If the account exists, prompts for the amount to deposit
     *  If the amount is valid, it makes the deposit and prints the new balance
     *  Otherwise, an error message is printed
     * Output:
     *  For a valid deposit, the deposit transaction is printed
     *  Otherwise, an error message is printed
     */
    public static void deposit(int[] acctNumArray, double[] balanceArray, int numAccts,
                               PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        double amountToDeposit;

        outFile.println();
        System.out.print("Enter the account number: ");            //prompt for account number
        requestedAccount = kybd.nextInt();                        //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        if (index == -1)                                        //invalid account
        {
            outFile.println("Transaction Requested: Deposit");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else                                                    //valid account
        {
            System.out.print("Enter amount to deposit: ");        //prompt for amount to deposit
            amountToDeposit = kybd.nextDouble();                        //read-in the amount to deposit

            if (amountToDeposit <= 0.00) {
                //invalid amount to deposit
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error: $%.2f is an invalid amount", amountToDeposit);
                outFile.println();
            } else {
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                balanceArray[index] += amountToDeposit;
                outFile.printf("New Balance: $%.2f", balanceArray[index]);
                outFile.println();
            }
        }
        System.out.println();

        outFile.flush();                //flush the output buffer
    }

    /* Function withdrawal:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  Prompts for the requested account
     *  Calls findAcct() to see if the account exists
     *  If the account exists, prompts for the amount to withdraw
     *  If the amount is valid, it withdraws the amount and prints the new balance
     *  Otherwise, an error message is printed
     * Output:
     *  For a valid withdrawal, the deposit transaction is printed
     *  Otherwise, an error message is printed
     */
    public static void withdrawal(int[] acctNumArray, double[] balanceArray, int numAccts,
                                  PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        double amountToWithdraw;

        outFile.println();
        System.out.println("Enter the account number: ");            //prompt for account number
        requestedAccount = kybd.nextInt();                    //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        if (index == -1)                                        //invalid account
        {
            outFile.println("Transaction Requested: Withdrawal");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else                                                    //valid account
        {
            System.out.print("Enter amount to withdraw: ");        //prompt for amount to deposit
            amountToWithdraw = kybd.nextDouble();                //read-in the amount to deposit

            if (amountToWithdraw <= 0.00) {
                //invalid amount to deposit
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                outFile.println();
            } else if (amountToWithdraw > balanceArray[index]) {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal amount: $%.2f", amountToWithdraw);
                outFile.print(" is higher than account balance");
                outFile.println();
            } else {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToWithdraw);
                balanceArray[index] -= amountToWithdraw;            //make the deposit
                outFile.printf("New Balance: $%.2f", balanceArray[index]);
                outFile.println();
            }
        }
        outFile.println();

        outFile.flush();
    }

    /* Function new account:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  Prompts for the requested account
     *  Calls findAcct() to see if the account exists
     *  If the account exists, prompts for another account number
     *  If the account does not exist, it makes the account and returns
     * Output:
     *  For a valid new account, a success message is printed with the new number
     */
    public static int newAcct(int[] acctNumArray, double[] balanceArray, int numAccts,
                              PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;

        outFile.println();
        System.out.println("Enter new account number: ");
        requestedAccount = kybd.nextInt();


        //call findAcct to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        if (index == -1 && (requestedAccount <= 999999) && (requestedAccount >= 100000)) //Valid Account
        {
            acctNumArray[numAccts] = requestedAccount;
            balanceArray[numAccts] = 0;

            outFile.println("Transaction Requested: Add New Account ");
            outFile.println("Account Number: " + requestedAccount + " Successfully Created.");
            numAccts++;

            return numAccts;
        }

        //Invalid Account
        outFile.println("Transaction Requested: Add New Account ");
        outFile.println("Error, Account Number: " + requestedAccount + " is unavailable.");
        outFile.println();
        outFile.flush();                //flush the output buffer
        return numAccts;
    }

    /* Function delete account:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  num_accts - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  Prompts for the requested account
     *  Calls findAcct() to see if the account exists
     *  If the account exists, checks if account has balance
     *  If there is a balance, it returns without deletion
     *  If the account does not have a balance, it deletes the account and returns
     * Output:
     *  For a valid deletion, success message is printed with deleted account no.
     */
    public static int deleteAcct(int[] acctNumArray, double[] balanceArray, int numAccts,
                                 PrintWriter outFile, Scanner kybd) {
        int deleteAccount;
        int index;

        outFile.println();
        System.out.println("Enter the account number to delete:");
        deleteAccount = kybd.nextInt();

        index = findAcct(acctNumArray, numAccts, deleteAccount);

        if (index != -1)    //Valid Account
        {
            if (balanceArray[index] != 0) {
                outFile.println("Transaction Requested: Delete Account ");
                outFile.println("This account has a remaining balance, please empty the balance and retry");
                return numAccts;
            } else {
                while (index < numAccts - 1) {
                    acctNumArray[index] = acctNumArray[index + 1];
                    balanceArray[index] = balanceArray[index + 1];
                    index++;
                }
            }
            outFile.println("Transaction Requested: Delete Account ");
            outFile.println("Account Number: " + deleteAccount + " successfully deleted");
            numAccts--;
            outFile.flush();                //flush the output buffer
            return numAccts;
        }

        //Invalid Account
        outFile.println("Error, invalid account number: " + deleteAccount);
        outFile.println();
        outFile.flush();                //flush the output buffer
        return numAccts;
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