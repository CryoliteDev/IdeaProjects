import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

public class Bank {

    //Holds the Bank Account info.
    private Account[] accounts = new Account[50];
    private int maturityDate;

    private static DateInfo todaysDate = new DateInfo(2019, 3, 6);

    public boolean success = false;                  // To check if to create/delete account

    /* Method readAccts()
     * Input:
     *  Account[] accounts - reference to array of account numbers
     *  maxAccts - maximum number of active accounts allowed
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(Bank bank) throws IOException {

        //open database input file and creates File object
        //File dbFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/input.txt");
        File dbFile = new File("input.txt");

        //create Scanner object
        Scanner sc = new Scanner(dbFile);

        int totalAccountsReadIn = 0;                          //account number counter

        while (sc.hasNext() && totalAccountsReadIn <= 50) {
            String fName = sc.next();
            String lName = sc.next();
            String acctType = sc.next();
            String SSN = sc.next();
            int acctNum = Integer.parseInt(sc.next());
            Double acctBal = Double.parseDouble(sc.next());
            int matDate = Integer.parseInt(sc.next());
            bank.getAccounts()[totalAccountsReadIn] = new Account(fName, lName, SSN, acctNum,
                    acctType, acctBal, matDate);
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
    public static void printAccts(Bank bank, int totalAccountsReadin, PrintWriter outFile) {

        outFile.println("\t\t\t\t\t\t*******************************");
        outFile.println("\t\t\t\t\t\t\tDatabase of Bank Accounts");
        outFile.println("\t\t\t\t\t\t*******************************");
        outFile.println();
        outFile.printf("%-14s   %-14s    %-18s  %-12s   %-6s    %-16s ",
                "First Name", "Last Name", "Account Type", "SSN", "Account", "Balance");
        outFile.println();
        outFile.print("------------------------------------------------------------------------------------------");
        for (int index = 0; index < totalAccountsReadin; index++) {
            outFile.println();
            outFile.printf("%-14s  |  %-14s  |  %-12s  |  %-10s  | %-8s  | $%7.2f ",
                    bank.getAccounts()[index].getName().getfName(),
                    bank.getAccounts()[index].getName().getlName(),
                    bank.getAccounts()[index].getAcctType(),
                    bank.getAccounts()[index].getDepositor().getSSN(),
                    bank.getAccounts()[index].getAcctNum(),
                    bank.getAccounts()[index].getAcctBal());
        }
        outFile.println();
        outFile.println();
        //flush the output file
        outFile.flush();
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
    public int findAcct(Bank bank, int totalAccountsReadIn, int requestedAccount) {
        for (int index = 0; index < totalAccountsReadIn; index++) {
            if (bank.getAccounts()[index].getAcctNum() == requestedAccount)
                return index;
        }
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
    public static void balance(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;

        System.out.println();
        System.out.print("Enter the account number: ");          //prompt for account number
        requestedAccount = kybd.nextInt();                //read-in the account number

        //calls findAcct() to search if requestedAccount exists
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index == -1) {    //invalid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {    //valid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.printf("Current Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
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
     *  For a valid deposit, the deposit transaction is printed
     *  Otherwise, an error message is printed
     */
    public static void deposit(Bank bank, DateInfo dateInfo, int totalAccountsReadIn,
                               PrintWriter outFile, Scanner kybd) {

        int index = -1;
        int requestedAccount;
        double amountToDeposit;
        double currentBal;

        outFile.println();
        System.out.print("Enter the account number: ");          //prompt for account number
        requestedAccount = kybd.nextInt();                //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index != -1) {
            if (bank.getAccounts()[index].getAcctType().equals("CD")) {
                bank.depCdAcct(bank,dateInfo,index,requestedAccount, outFile, kybd);
            }
            System.out.print("Enter amount to deposit: ");    //prompt for amount to deposit
            amountToDeposit = kybd.nextDouble();            //read-in the amount to deposit

            if (amountToDeposit <= 0.00) {  //invalid amount to deposit
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                outFile.printf("Error: $%.2f is an invalid amount", amountToDeposit);
                outFile.println();
            } else {
                currentBal = bank.getAccounts()[index].getAcctBal();
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                bank.getAccounts()[index].setAcctBal(currentBal + amountToDeposit);         //make the deposit
                outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
            }
        } else {
            outFile.println("Transaction Requested: Deposit");
            outFile.println("Error: Account number: " + requestedAccount + " does not exist.");
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
    public static void withdrawal(Bank bank, DateInfo dateInfo, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        double amountToWithdraw;
        double currentBal;

        outFile.println();
        System.out.println("Enter the account number: ");       //prompts for account number
        requestedAccount = kybd.nextInt();                      //reads in the account number

        //call findAcct to
        // search if requestedAccount exists.
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index != -1) {
            if (bank.getAccounts()[index].getAcctType().equals("CD")) {
                bank.withCdAcct(bank, dateInfo, index, requestedAccount, outFile, kybd);
            }
            System.out.println("Enter amount to Withdraw: ");   //Prompts for the account number
            amountToWithdraw = kybd.nextDouble();               //Reads in the amount to withdraw

            if (amountToWithdraw <= 0.0) { //invalid ammount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                outFile.println();
            } else if (amountToWithdraw > bank.getAccounts()[index].getAcctBal()) { //invalid amount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                outFile.print(" is higher than you balance of " + bank.getAccounts()[index].getAcctBal());
                outFile.println();
            } else {    //valid amount to withdraw
                currentBal = bank.getAccounts()[index].getAcctBal();
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
                outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                bank.getAccounts()[index].setAcctBal(currentBal - amountToWithdraw);
                outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
            }
        } else {
            //Error message
            outFile.println("Transaction Requested: Withdrawal");
            outFile.println("Error: Account Number: " + requestedAccount + " does not exist");
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
    public static void accountInfo(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        outFile.println();

        System.out.print("Enter your SSN: ");
        String requestedSSN = kybd.next();
        outFile.println();

        for (int index = 0; index < totalAccountsReadIn; index++) {
            if (bank.getAccounts()[index].getDepositor().getSSN().equals(requestedSSN)) {
                outFile.println("Transaction Requested: Account Information");
                outFile.println("Account Owner: " + bank.getAccounts()[index].getName().getfName() + " " +
                        bank.getAccounts()[index].getName().getlName());
                outFile.println("Account Type: " + bank.getAccounts()[index].getAcctType());
                outFile.println("Account Number: " + bank.getAccounts()[index].getAcctNum());
                outFile.println("Social Security Number: " + bank.getAccounts()[index].getDepositor().getSSN());
                outFile.printf("Account Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
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

    /* Method clearCheck
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
    public static boolean clearCheck(Bank bank, DateInfo dateInfo, int totalAccountsReadIn, PrintWriter outFile,
                                     Scanner kybd) {

        //variable declarations
        int yearCK, monthCK, dayOfMonthCK;
        int requestedAccount;
        int index = -1;
        double amountToWithdraw;
        double currentBal;
        boolean clearCK = false;

        outFile.println();
        System.out.println("Enter the account number: ");       //prompts for account number
        requestedAccount = kybd.nextInt();                      //reads in the account number

        //call findAcct to search if requestedAccount exists.
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index != -1) {   //valid account
            if (bank.getAccounts()[index].getAcctType().equals("CHECKING")) {
                //They user enters the date on the check
                System.out.println("Enter the Date on the Check: \n In the Format of MM DD YYYY");
                monthCK = kybd.nextInt();
                dayOfMonthCK = kybd.nextInt();
                yearCK = kybd.nextInt();
                monthCK = monthCK - 1;      //Subtracts one because the Calender.MONTH starts at 0.

                //The entered date is set to the checkDate Calendar
                dateInfo.checkDate.set(yearCK, monthCK, dayOfMonthCK);
                dateInfo.checkDate.set(Calendar.MONTH, monthCK);
                dateInfo.checkDate.set(Calendar.DAY_OF_MONTH, dayOfMonthCK);
                dateInfo.checkDate.set(Calendar.YEAR, yearCK);

                //sets the c1 calendar date 6 month before today's date.
                dateInfo.c1.add(Calendar.MONTH, -6);

                System.out.println("The date on the check  is: " + dateInfo.checkDate.getTime());

                if (dateInfo.checkDate.after(dateInfo.c1) &&
                        dateInfo.checkDate.before(dateInfo.calendar)) {  //valid account

                    System.out.print("Enter amount to withdrawal amount on the check: "); //prompts4amt2 withdraw
                    amountToWithdraw = kybd.nextDouble();               //reads in the amount to withdraw

                    if (amountToWithdraw <= 0.00) {     //invalid amount to withdraw
                        currentBal = bank.getAccounts()[index].getAcctBal();
                        outFile.println("Transaction Requested: Clear Check");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                        outFile.println("\nA bouncing fee of $2.50 will be charged from your account.");
                        bank.getAccounts()[index].setAcctBal(currentBal - 2.50);
                        outFile.println();
                        clearCK = false;
                    } else if (amountToWithdraw > bank.getAccounts()[index].getAcctBal()) { //invalid amt to withdraw
                        currentBal = bank.getAccounts()[index].getAcctBal();
                        outFile.println("Transaction Requested: Clear Check");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                        outFile.print(" is higher than your balance of " + bank.getAccounts()[index].getAcctBal());
                        outFile.println("\nA bouncing fee of $2.50 will be charged from your account.");
                        bank.getAccounts()[index].setAcctBal(currentBal - 2.50);
                        outFile.println();
                        clearCK = false;
                    } else {    //valid amount to withdraw
                        currentBal = bank.getAccounts()[index].getAcctBal();
                        outFile.println("Transaction Requested: Clear Check");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                        outFile.println();
                        outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                        bank.getAccounts()[index].setAcctBal(currentBal - amountToWithdraw);     //makes the withdrawal
                        outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                        outFile.println();
                        clearCK = true;
                    }
                } else if (dateInfo.checkDate.before(dateInfo.c1)) {
                    outFile.println("The Check is older than six months and can not be deposited.");
                }
            } else {
                outFile.println("Transaction Requested: Clear Check");
                outFile.println("Error: Account Number: " + requestedAccount + " is not a Checking Account");
            }

        } else {
            outFile.println("Transaction Requested: Clear Check");
            outFile.println("The entered Account Number: " + requestedAccount + " does not exist");
        }

        outFile.println();
        outFile.flush();        //flush the output buffer
        return clearCK;
    }



    /*Method depCdAcct
     *Function check CD Acct:
     * Input:
     *  Bank bank - reference to array of account numbers
     *  Date date - reference to Calendar class.
     *  index - the active account
     *  requestedAccount - the active account
     *  outFile - reference to output file
     *  kybd - reference to the "test cases" input file
     * Process:
     *  prompts for the maturity date
     *  if the account exists, prompts for another account number
     *  if the account does not exist, it makes the account and returns the new account number
     * Output:
     *  for a valid new account, a success message is printed with the new account number.
     */

    public static int depCdAcct(Bank bank, DateInfo dateInfo, int index, int requestedAccount,
                                PrintWriter outFile, Scanner kybd) {

        double amountToDeposit;
        double currentBal;

        outFile.println();
        if (bank.getAccounts()[index].getMatDate() == 0) { //no maturity date
            System.out.println("Choose a Maturity Date. \nIt can be either 6, 12, 18, or 24 months.");
            bank.maturityDate = kybd.nextInt();
            if (bank.maturityDate == 6 || bank.maturityDate == 12 ||
                    bank.maturityDate == 18 || bank.maturityDate == 24) {
                dateInfo.maturityCal.add(Calendar.MONTH, bank.maturityDate);
                outFile.println("Your maturity date has been set to : " + dateInfo.maturityCal.getTime());
                bank.getAccounts()[index].setMatDate(1);
                return bank.getAccounts()[index].getMatDate();
            } else {
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Error: Invalid Maturity Date");
                return bank.getAccounts()[index].getMatDate();
            }
        }
        if (bank.getAccounts()[index].getMatDate() == 1) { //has maturity date
            if (dateInfo.maturityCal.getTime().after(dateInfo.calendar.getTime())) { //Maturity in Affect
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Error: Your Maturity Date is still in Affect");
            } else if (dateInfo.maturityCal.getTime().before(dateInfo.calendar.getTime())) { //After Maturity date
                System.out.print("Enter amount to deposit: ");    //prompt for amount to deposit
                amountToDeposit = kybd.nextDouble();            //read-in the amount to deposit
                if (amountToDeposit <= 0.00) {  //invalid amount to deposit
                    outFile.println("Transaction Requested: Deposit");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.println("Amount to Deposit: $" + amountToDeposit);
                    outFile.printf("Error: $%.2f is an invalid amount", amountToDeposit);
                    outFile.println();
                } else {    //Valid Transaction
                    currentBal = bank.getAccounts()[index].getAcctBal();
                    outFile.println("Transaction Requested: Deposit");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                    outFile.println();
                    outFile.println("Amount to Deposit: $" + amountToDeposit);
                    bank.getAccounts()[index].setAcctBal(currentBal + amountToDeposit);     //make the deposit
                    outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                    outFile.println();
                }
            }
        }

        outFile.println();
        outFile.flush();    //flush the output buffer
        return bank.getAccounts()[index].getMatDate();
    }

    public static int withCdAcct(Bank bank, DateInfo dateInfo, int index, int requestedAccount,
                                 PrintWriter outFile, Scanner kybd) {

        double amountToWithdraw;
        double currentBal;

        outFile.println();
        if (bank.getAccounts()[index].getMatDate() == 0) { //no maturity date
            System.out.println("Choose a Maturity Date. \nIt can be either 6, 12, 18, or 24 months.");
            bank.maturityDate = kybd.nextInt();
            if (bank.maturityDate == 6 || bank.maturityDate == 12 ||
                    bank.maturityDate == 18 || bank.maturityDate == 24) {   //Valid Maturity Date
                dateInfo.maturityCal.add(Calendar.MONTH, bank.maturityDate);
                outFile.println("Your maturity date has been set to: " + dateInfo.maturityCal.getTime());
                outFile.println();
                bank.getAccounts()[index].setMatDate(1);
                return bank.getAccounts()[index].getMatDate();
            } else {    //Invalid Maturity Date
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Error: Invalid Maturity Date");
                outFile.println();
                return bank.getAccounts()[index].getMatDate();
            }
        }
        if (bank.getAccounts()[index].getMatDate() == 1) {  //has a maturity date

            if (dateInfo.maturityCal.getTime().after(dateInfo.calendar.getTime())) {    //maturity in Affect
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Error: Your Maturity Date is still in Affect");
                outFile.println();
            } else if (dateInfo.maturityCal.getTime().before(dateInfo.calendar.getTime())) { //after maturity date
                System.out.print("Enter amount to withdraw: ");     //prompts for amount to withdraw
                amountToWithdraw = kybd.nextDouble();               //reads in the amount to withdraw

                if (amountToWithdraw <= 0.00) {     //invalid amount to withdraw
                    outFile.println("Transaction Requested: Withdrawal");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                    outFile.println();
                } else if (amountToWithdraw > bank.getAccounts()[index].getAcctBal()) {    //invalid amount to withdraw
                    outFile.println("Transaction Requested: Withdrawal");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                    outFile.print(" is higher than your balance of " + bank.getAccounts()[index].getAcctBal());
                    outFile.println();
                } else {    //valid amount to withdraw
                    currentBal = bank.getAccounts()[index].getAcctBal();
                    outFile.println("Transaction Requested: Withdrawal");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                    outFile.println();
                    outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                    bank.getAccounts()[index].setAcctBal(currentBal - amountToWithdraw);      //makes the withdrawal
                    outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                    outFile.println();
                }
            }
            outFile.println();
        }

        outFile.println();
        outFile.flush();    //flush the output buffer
        return bank.getAccounts()[index].getMatDate();
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
    public boolean newAcct(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        int index;
        String fNameRequested = "";
        String lNameRequested = "";
        String SSNRequested = "";
        int requestedAccount;
        String acctTypeRequested = "";
        int matDate = 0;
        boolean acctCreated = false;

        if (totalAccountsReadIn < bank.getAccounts().length) {
            System.out.println("Enter new Account Number: ");
            requestedAccount = kybd.nextInt();

            //calls findAcct() to search if requestedAccount exists
            index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

            if (index == -1 && (requestedAccount <= 999999) && (requestedAccount >= 100000)) {
                acctCreated = true;
            } else {
                outFile.println("Transaction Requested: New Account");
                outFile.println("Error: Account Number: " + requestedAccount + " is invalid");
                acctCreated = false;
            }

            if (acctCreated) {
                //Prompts for the first then the last name
                System.out.println("Enter First Name: ");
                fNameRequested = kybd.next();

                System.out.println("Enter Last Name: ");
                lNameRequested = kybd.next();

                //Prompts for the acctType
                System.out.println("Enter Account Type to Open: ");
                acctTypeRequested = kybd.next();

                if (!(acctTypeRequested.equals("Checking") ||
                        acctTypeRequested.equals("Savings") ||
                        acctTypeRequested.equals("checking") ||
                        acctTypeRequested.equals("savings") ||
                        acctTypeRequested.equals("cd") ||
                        acctTypeRequested.equals("CD"))) {

                    System.out.println("Error: The Account Type entered in invalid");
                    acctCreated = false;
                }

                if (acctTypeRequested.equals("cd") || acctTypeRequested.equals("CD"))
                    matDate = 0;



                //Prompts for the SSN
                System.out.println("Enter Social Security Number: ");
                SSNRequested = kybd.next();
                for (int i = 0; i < totalAccountsReadIn; i++) {
                    //check if the ssn exists already
                    if (bank.getAccounts()[i].getDepositor().getSSN().equals(SSNRequested)) {
                        outFile.println("Transaction Requested: New Account");
                        outFile.println("Error: Social Securtiy Number " + SSNRequested + " already exists");
                        acctCreated = false;
                    }
                }
            }

            if (acctCreated) {
                outFile.println();
                outFile.println("Transaction requested: New Account");

                //Sets the new account to the next avalible spot w/ bal of $0
                bank.setAccts(bank, totalAccountsReadIn, fNameRequested, lNameRequested, SSNRequested,
                        requestedAccount, acctTypeRequested, 0.0, matDate);

                //Displays new acct info
                outFile.println("Accounts Request: New Account Information");
                outFile.println("Owner " + bank.getAccounts()[totalAccountsReadIn].getName().getfName() + " " +
                        bank.getAccounts()[totalAccountsReadIn].getName().getlName());
                outFile.println("Social Security Number: " + bank.getAccounts()[totalAccountsReadIn].getDepositor().getSSN());
                outFile.println("Account Type: " + bank.getAccounts()[totalAccountsReadIn].getAcctType());
                outFile.println("Account Number: " + requestedAccount + " Successfully Created");

                outFile.println();
                outFile.flush();
                return success = true;
            }

            if (!acctCreated) {
                outFile.println("Transaction Requested: New Account");
                outFile.println("Error: Account not created");
                return success = false;
            }
        }

        outFile.println();
        outFile.flush();

        return success = false;
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
    public boolean deleteAcct(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        int deleteAccount;
        int index;

        System.out.println("Enter the account number to delete: ");       //prompts for the account number to delete
        deleteAccount = kybd.nextInt();                                 //reads in the account number.

        index = bank.findAcct(bank, totalAccountsReadIn, deleteAccount);

        if (index != -1) {  //Valid account number
            if (bank.getAccounts()[index].getAcctBal() != 0) {
                outFile.println("Transaction Requested: Delete Account");
                outFile.println("Account Number: " + deleteAccount);
                outFile.println("This account has a remaining balance of " + bank.getAccounts()[index].getAcctBal() +
                        " please empty the balance and retry");
                outFile.println();
                return success;        //returns the array w/out deleting an account.
            } else {
                while (index < totalAccountsReadIn) {
                    bank.getAccounts()[index] = bank.getAccounts()[index + 1];
                    index++;
                }
            }
            outFile.println("Transaction Requested: Delete Account");
            outFile.println("Account Number: " + deleteAccount + " has been successfully deleted");
            outFile.flush();        //flush the output buffer
            outFile.println();
            return success = true;        //returns the numAccts w/ the account deleted
        }

        //Invalid Account
        outFile.println("Error, invalid account number: " + deleteAccount);
        outFile.println();
        outFile.flush();        //flush the output buffer
        return success;        //returns the numAccts w/out the account deleted.
    }

    //Getter for Account Array
    public Account[] getAccounts() {
        return accounts;
    }

    /*
     * Method setAcct()
     * input:
     * Bank bank - Constructor which hold the accounts.
     * totalAccountsReadIn - number of active accounts
     * fName - first Name
     * lName  - last Name
     * SSN - Social Security Number
     * acctNum - The account Number
     * acctType - the type of account(Checking, Saving, or CD)
     * acctBal - the account balance.
     * matDate
     * Process:
     * adds a new account to the array of accounts
     */
    public void setAccts(Bank bank, int totalAccountsReadIn, String fName, String lName,
                         String SSN, int acctNum, String acctType, Double acctBal, int matDate) {

        bank.accounts[totalAccountsReadIn] = new Account(fName, lName, SSN, acctNum, acctType, acctBal, matDate);
    }
}