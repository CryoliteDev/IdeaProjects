import java.io.*;
import java.util.*;

public class BankAccounts {

    public static void main(String... args) throws IOException {

        //MUHAMMAD-AZHAR CISC 3115-MW9
        //constant definitions
        final int MAX_NUM = 50;

        //variable declarations
        char choice;									//menu item selected
        boolean not_done = true;						//loop control flag
        int MAX_ACCTS = 50;
        Account[] accounts = new Account[MAX_ACCTS];    //array of account numbers

        // open input test cases file
        //File testFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/testCases.txt");
        File testFile = new File("testCases.txt");

        //create Scanner objec
        Scanner kybd = new Scanner(testFile);
        //Scanner kybd = new Scanner(System.in);

        // open the output file
        //PrintWriter outFile = new PrintWriter("/Users/Xiao/IdeaProjects/BankAccount_1/output.txt");
        PrintWriter outFile = new PrintWriter("output.txt");
        //PrintWriter outFile = new PrintWriter(System.out);

        outFile.println("MUHAMMAD-AZHAR \nCISC 5115-MW9\n");
        /* first part */
        /* fill and print initial database */
        int totalAccounts = readAccts(accounts,MAX_NUM);
        printAccts(accounts,totalAccounts,outFile);

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
                    printAccts(accounts, totalAccounts, outFile);
                    break;
                case 'b':
                case 'B':
                    balance(accounts, totalAccounts ,outFile,kybd);
                    break;
                case 'd':
                case 'D':
                    deposit(accounts, totalAccounts,outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    withdrawal(accounts, totalAccounts,outFile,kybd);
                    break;
                case 'i':
                case 'I':
                    accountInfo(accounts, totalAccounts, outFile, kybd);
                    break;
                case 'n':
                case 'N':
                    totalAccounts = newAcct(accounts, totalAccounts,outFile,kybd);
                    break;
                case 'x':
                case 'X':
                    totalAccounts = deleteAcct(accounts, totalAccounts,outFile,kybd);
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

    /* Method readAccts()
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  maxAccts - maximum number of active accounts allowed
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(Account[] accounts, int maxAccts) throws IOException {

        //open database input file and creates File object
        //File dbFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/input.txt");
        File dbFile = new File("input.txt");

        //create Scanner object
        Scanner sc = new Scanner(dbFile);

        int totalAccountsReadIn = 0;                          //account number counter

        while (sc.hasNext() && totalAccountsReadIn < maxAccts) {
            String fName = sc.next();
            String lName = sc.next();
            String acctType = sc.next();
            String SSN = sc.next();
            int acctNum = Integer.parseInt(sc.next());
            Double acctBal = Double.parseDouble(sc.next());
            accounts[totalAccountsReadIn] = new Account(fName, lName, SSN, acctNum, acctType,acctBal);
            totalAccountsReadIn++;
        }

        //close the input file
        sc.close();

        //return the account number totalAccountsReadIn
        return totalAccountsReadIn;
    }

    /* Method printAccts:
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  numAccts - number of active accounts
     *  outFile - reference to the output file
     * Process:
     *  Prints the database of accounts and balances
     * Output:
     *  Prints the database of accounts and balances
     */
    public static void printAccts(Account[] accounts, int numAccts, PrintWriter outFile) {

        outFile.println("\t\t\t\t\t\t*******************************");
        outFile.println("\t\t\t\t\t\t\tDatabase of Bank Accounts");
        outFile.println("\t\t\t\t\t\t*******************************");
        outFile.println();
        outFile.printf("%-14s   %-14s    %-18s  %-12s   %-6s    %-16s ",
                "First Name", "Last Name", "Account Type", "SSN", "Account", "Balance");
        outFile.println();
        outFile.print("------------------------------------------------------------------------------------------");
        for (int index = 0; index < numAccts; index++) {
            outFile.println();
            outFile.printf("%-14s  |  %-14s  |  %-12s  |  %-10s  | %-8s  | $%7.2f ",
                    accounts[index].getName().getfName(),
                    accounts[index].getName().getlName(),
                    accounts[index].getAcctType(),
                    accounts[index].getDepositor().getSSN(),
                    accounts[index].getAcctNum(),
                    accounts[index].getAcctBal());
        }
        outFile.println();
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
     *  Account[] accounts - reference to array of account numbers
     *  requestedAccount - requested account requested_number
     * Process:
     *  Performs a linear search on the acctNunArray for the requested account
     * Output:
     *  If found, the index of the requested account is returned
     *  Otherwise, returns -1
     */
    public static int findAcct(Account[] accounts, int numAccts, int requestedAccount) {

        //runs through the array to find the requested account number
        for (int index = 0; index < numAccts; index++)
            if (accounts[index].getAcctNum() == requestedAccount)
                return index;   //returns the index where the account is located
        return -1;
    }

    /* Method balance:
     * Input:
     *  Account[] accounts - reference to array of account numbers
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
    public static void balance(Account[] accounts, int numAccts, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;

        System.out.println();
        System.out.print("Enter the account number: ");			//prompt for account number
        requestedAccount = kybd.nextInt();						//read-in the account number

        //calls findAcct() to search if requestedAccount exists
        index = findAcct(accounts, numAccts, requestedAccount);

        if (index == -1) {    //invalid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {    //valid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.printf("Current Balance: $%.2f", accounts[index].getAcctBal());
            outFile.println();
        }
        outFile.println();
        outFile.flush();        //flush the output buffer
    }

    /* Method deposit:
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  totalAccountsReadIn - number of active accounts
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
    public static void deposit(Account[] accounts, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        int requestedAccount;
        int index;
        double amountToDeposit;
        double currentBal;

        outFile.println();
        System.out.print("Enter the account number: ");			//prompt for account number
        requestedAccount = kybd.nextInt();						//read-in the account number

        //call findAcct to search if requestedAccount exists
        index = findAcct(accounts,totalAccountsReadIn , requestedAccount);

        if (index == -1) {  //invalid account
            outFile.println("Transaction Requested: Deposit");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {    //valid account
            System.out.print("Enter amount to deposit: ");		//prompt for amount to deposit
            amountToDeposit = kybd.nextDouble();				//read-in the amount to deposit

            if (amountToDeposit <= 0.00) {  //invalid amount to deposit
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                outFile.printf("Error: $%.2f is an invalid amount", amountToDeposit);
                outFile.println();
            } else {
                currentBal = accounts[index].getAcctBal();
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", accounts[index].getAcctBal());
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                accounts[index].setAcctBal(currentBal + amountToDeposit);         //make the deposit
                outFile.printf("New Balance: $%.2f", accounts[index].getAcctBal());
                outFile.println();
            }
        }
        outFile.println();
        outFile.flush();        //flush the output buffer
    }

    /* Function Withdrawal
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  Prompts for the requested account
     *  calls findAccts() to see if the account exists
     *  If the account exists, prompts for the amount to withdraw
     *  if the amount is valid, it withdraws the amount and prints the new balance
     *  otherwise, an error message is printed.
     * Output:
     *  for a valid withdrawal , the withdrawal transaction is printed
     *  otherwise, an error message is printed.
     */
    public static void withdrawal(Account[] accounts, int numAccts, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        double amountToWithdraw;
        double currentBal;

        outFile.println();
        System.out.println("Enter the account number: ");       //prompts for account number
        requestedAccount = kybd.nextInt();                      //reads in the account number

        //call findAcct to search if requestedAccount exists.
        index = findAcct(accounts, numAccts, requestedAccount);

        if (index == -1) {     //invalid account
            outFile.println("Transaction Requested: Withdrawal");
            outFile.println("Error: Account number: " + requestedAccount + " does not exist.");
        } else {    //valid account
            System.out.print("Enter amount to withdraw: ");     //prompts for amount to withdraw
            amountToWithdraw = kybd.nextDouble();               //reads in the amount to withdraw

            if (amountToWithdraw <= 0.00) {     //invalid amount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                outFile.println();
            } else if (amountToWithdraw > accounts[index].getAcctBal()) {    //invalid amount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                outFile.print(" is higher than your balance of " + accounts[index].getAcctBal());
                outFile.println();
            } else {    //valid amount to withdraw
                currentBal = accounts[index].getAcctBal();
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", accounts[index].getAcctBal());
                outFile.println();
                outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                accounts[index].setAcctBal(currentBal - amountToWithdraw);      //makes the withdrawal
                outFile.printf("New Balance: $%.2f", accounts[index].getAcctBal());
                outFile.println();
            }
        }
        outFile.println();
        outFile.flush();        //flush the output buffer
    }

    /* Method accountInfo
     * Input:
     *  Account[] accounts - array of accounts numbers
     *  totalAccountsReadIn - number of active accounts
     *  outFile - Reference to the output file
     *  kypd - reference to the "testCases" input file.
     * Process:
     *  Prompts  for the SSN
     *  Calls findAcct() to see if the SSN exists
     *  If the account exists, the account info is printed
     * Output:
     *  if the SSS exists, the account info is printed
     *  Otherwise, an error message is printed.
     */
    public static void accountInfo(Account[] accounts, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        outFile.println();

        System.out.print("Enter your SSN: ");
        String requestedSSN = kybd.next();
        outFile.println();

        for (int index = 0; index < totalAccountsReadIn; index++) {
            if (accounts[index].getDepositor().getSSN().equals(requestedSSN)) {
                outFile.println("Transaction Requested: Account Information");
                outFile.println("Account Owner: " + accounts[index].getName().getfName() + " " +
                        accounts[index].getName().getlName());
                outFile.println("Account Type: " + accounts[index].getAcctType());
                outFile.println("Account Number: " + accounts[index].getAcctNum());
                outFile.println("Social Security Number: " + accounts[index].getDepositor().getSSN());
                outFile.printf("Account Balance: $%.2f", accounts[index].getAcctBal());
                outFile.println();

                outFile.flush();    //flush the output buffer

                return;
            }
        }

        outFile.println();
        outFile.println("Transaction Requested: Account Information");
        outFile.println("Error: Account with the SSN of: " + requestedSSN + " does not exist");

        outFile.flush();    //flush the output buffer
    }


        /* Function new account:
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  prompts for the requested account
     *  calls findAcct() to see if the account exists
     *  if the account exists, prompts for another account number
     *  if the account does not exist, it makes the account and returns the new account number
     * Output:
     *  for a valid new account, a success message is printed with the new account number.
     */
    public static int newAcct(Account[] accounts, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        int index;
        String fNameRequested = "";
        String lNameRequested = "";
        String SSNRequested = "";
        int requestedAccount;
        String acctTypeRequested = "";
        boolean acctCreated = false;

        if (totalAccountsReadIn < accounts.length) {
            System.out.println("Enter new Account Number: ");
            requestedAccount = kybd.nextInt();

            //calls findAcct() to search if requestedAccounts exits
            index = findAcct(accounts, totalAccountsReadIn, requestedAccount);

            if (index == -1 && (requestedAccount <= 999999) && (requestedAccount >= 100000)) {
                acctCreated = true;
            } else if (requestedAccount < 999999 || requestedAccount < 100000) {
                outFile.println("Transaction Requested: New Account");
                outFile.println("Error: Account Number: " + requestedAccount + " is an Invalid Account Number");
                acctCreated = false;
            } else {
                outFile.println("Transaction Requested: New Account");
                outFile.println("Error: Account Number: " + requestedAccount + " Already exists.");
                acctCreated = false;
            }

            if (acctCreated) {
                System.out.println("Enter First Name: ");
                fNameRequested = kybd.next();
            }

            if (acctCreated) {
                System.out.println("Enter Last Name: ");
                lNameRequested = kybd.next();
            }

            if (acctCreated) {
                System.out.println("Enter Social Security Number: ");
                SSNRequested = kybd.next();

                for (int i = 0; i < totalAccountsReadIn; i++) {
                    //Checks if the SSN exists or not
                    if (accounts[i].getDepositor().getSSN().equals(SSNRequested)) {
                        outFile.println("Transaction Requested: New Account");
                        outFile.println("Error: Socail Security Number " + SSNRequested + " already exists");
                        acctCreated = false;
                    }
                }
            }

            if (acctCreated) {
                System.out.println();
                System.out.println("Enter Account Type to Open: ");     //Prompts for account Type
                acctTypeRequested = kybd.next();

                if (acctTypeRequested.equals("Checking") ||
                        acctTypeRequested.equals("Savings") ||
                        acctTypeRequested.equals("CD") ||
                        acctTypeRequested.equals("checking") ||
                        acctTypeRequested.equals("savings") ||
                        acctTypeRequested.equals("cd")) {
                    acctCreated = true;
                }
            }

            if (acctCreated) {
                outFile.println();
                outFile.println("Transaction Requested: New Account");

                //Sets the new account to the next available spot with a balanceof $0.
                accounts[totalAccountsReadIn] = new Account(fNameRequested, lNameRequested, SSNRequested,
                        requestedAccount, acctTypeRequested, 0.0);

                //Displays new Account Information.
                outFile.println("Accounts Request: New Account Information");
                outFile.println("Owner: " + accounts[totalAccountsReadIn].getName().getfName() + " " +
                        accounts[totalAccountsReadIn].getName().getlName());
                outFile.println("Social Security Number: " + accounts[totalAccountsReadIn].getDepositor().getSSN());
                outFile.println("Account Type: " + accounts[totalAccountsReadIn].getAcctType());
                outFile.println("Account Number: " + requestedAccount + " Created");
                totalAccountsReadIn += 1;

            }
        }

            outFile.println();
            outFile.flush();
            return totalAccountsReadIn;
    }


    /* Function delete account:
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  prompts for the requested account
     *  calls findAcct() to see if the account exists
     *  if the account exists, checks if the account has balance
     *  if there is balance, it returns the same array without deletion
     *  if the account does not have a balance, it deletes the account and returns the edited acctNumArray
     * Output:
     *  for a valid deletion, success message is printed with deleted account number.
     */
    public static int deleteAcct(Account[] accounts,  int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        int deleteAccount;
        int index;

        System.out.println("Enter the account number to delete: ");       //prompts for the account number to delete
        deleteAccount = kybd.nextInt();                                 //reads in the account number.

        index = findAcct(accounts, totalAccountsReadIn, deleteAccount);

        if (index != -1) {  //Valid account number
            if (accounts[index].getAcctBal() != 0) {
                outFile.println("Transaction Requested: Delete Account");
                outFile.println("Account Number: " + deleteAccount);
                outFile.println("This account has a remaining balance of " + accounts[index].getAcctBal() +
                        " please empty the balance and retry");
                outFile.println();
                return totalAccountsReadIn;        //returns the array w/out deleting an account.
            } else {
                while (index < totalAccountsReadIn) {
                    accounts[index] = accounts[index+1];
                    index++;
                }
            }
            outFile.println("Transaction Requested: Delete Account");
            outFile.println("Account Number: " + deleteAccount + " has been successfully deleted");
            totalAccountsReadIn--;
            outFile.flush();        //flush the output buffer
            outFile.println();
            return totalAccountsReadIn;        //returns the numAccts w/ the account deleted
        }

        //Invalid Account
        outFile.println("Error, invalid account number: " + deleteAccount);
        outFile.println();
        outFile.flush();        //flush the output buffer
        return totalAccountsReadIn;        //returns the numAccts w/out the account deleted.
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