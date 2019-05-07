import java.io.*;
import java.util.*;

public class Bank {

    private ArrayList<Account> accounts;    // holds all the account

    public Bank() {
        accounts = new ArrayList<Account>();
    }

    public boolean success = false;                  // To check if to create/delete account
    private static double totalInChecking;
    private static double totalInSavings;
    private static double totaInCD;
    private static double totalBalance;
    private static Date todaysDate = new Date(2018, 12, 12);

    //Prints the static variables onto the outFile
    public void printTotalBalance(PrintWriter outFile) {
        outFile.println();
        outFile.printf("Total Amount in All Checking Accounts : $%7.2f", totalInChecking);
        outFile.println();
        outFile.printf("Total Amount in All Savings Accounts : $%7.2f", totalInSavings);
        outFile.println();
        outFile.printf("Total Amount in All CD Accounts : $%7.2f", totaInCD);
        outFile.println();
        outFile.printf("Total Amount in All Accounts : $%7.2f", totalBalance);
        outFile.println();
        outFile.println();

    }

    //adds the amount to deposit to the correct static variables
    public void addTotalBalance(String Type, double amountToDeposit) {
        switch (Type) {
            case "Checking":
                totalInChecking += amountToDeposit;
                break;
            case "Savings":
                totalInSavings += amountToDeposit;
                System.out.println(amountToDeposit);
                break;
            case "CD":
                totaInCD += amountToDeposit;
                break;
            default:

        }
        totalBalance += amountToDeposit;

    }

    //subtracts the amount to deposit to the correct static variables
    public void subtractTotalBalance(String Type, double amountToWithdraw) {
        switch (Type) {

            case ("Checking"):
                totalInChecking -= amountToWithdraw;
                break;
            case ("Savings"):
                totalInSavings -= amountToWithdraw;
                break;
            case ("CD"):
                totaInCD -= amountToWithdraw;
                break;
            default:

        }
        totalBalance -= amountToWithdraw;
    }

    public String toString() {
        return String.format("Total Amount in All Checking Accounts : $%7.2f \n"
                        + "Total Amount in All Savings Accounts : $%7.2f \n"
                        + "Total Amount in All CD Accounts : $%7.2f \n"
                        + "Total Amount in All Accounts : $%7.2f", totalInChecking,
                totalInSavings, totaInCD, totalBalance);
    }
    /* Method findAcct:
     * Input:
     * Bank bank - Constructor which hold the accounts.
     * totalAccountsReadIn - number of active accounts
     * requestedAccount - requested account requested_number
     * Process:
     *  Performs a linear search on the acctNunArray for the requested account
     * Output:
     *  If found, the index of the requested account is returned
     *  Otherwise, returns -1
     */
    public int findAcct(Bank bank, int requestedAccount) {
        for (int index = 0; index < bank.getAccounts().size(); index++)
            if (bank.getAccounts().get(index).getAcctNum() == requestedAccount)
                return index;
        return -1;
    }

    /* Method readAccts()
     * Input:
     *  Bank bank - constructor that holds the accounts
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(Bank bank, int totalAccountsReadIn, PrintWriter outFile) throws IOException {
        // open database input file
        // create File Object

        File dbFile = new File("input.txt");

        // create a Scanner Object
        Scanner sc = new Scanner(dbFile);

        int count = 0;

        while (sc.hasNext()) {
            String fName = sc.next();
            String lName = sc.next();
            String SSN = sc.next();
            int acctNum = Integer.parseInt(sc.next());
            String acctType = sc.next();
            Double acctBal = Double.parseDouble(sc.next());
            String acctStat = sc.next();
            int year = sc.nextInt();
            int month = sc.nextInt();
            int date = sc.nextInt();

            if (acctStat.equals("Open")) {
                totalAccountsReadIn++;
            }

            bank.setAccts(bank.getAccounts(), fName, lName, SSN, acctNum, acctType,
                    acctBal, acctStat, year, month, date);
            bank.addTotalBalance(acctType,acctBal);

            count++;
        }

        // close the input file
        sc.close();

        //return the account number count
        return totalAccountsReadIn;

    }

    /* Method printAccts:
     * Input:
     *  Bank bank - Constructor which hold the accounts.
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to the output file
     * Process:
     *  Prints the database of accounts and balances
     * Output:
     *  Prints the database of accounts and balances
     */
    public static void printAccts(Bank bank, PrintWriter outFile) {
        outFile.println("\t\t\t\t\t\t**********************************");
        outFile.println("\t\t\t\t\t\t\tDatabase of Account Accounts");
        outFile.println("\t\t\t\t\t\t**********************************");
        outFile.println();
        outFile.printf("%-14s   %-14s   %-12s   %-11s   %-8s   %-9s  %-8s",
                "First Name", "Last Name", "Account Type", "SSN", "Account", "Balance", "Status");
        outFile.println();
        outFile.print("---------------------------------------------------------------------------------------------");
        for (int index = 0; index < bank.getAccounts().size(); index++) {
            outFile.println();
            outFile.printf("%-14s | %-14s | %-12s | %-10s | %-8s | $%7.2f | %-12s",
                    bank.getAccounts().get(index).getDepositor().getName().getfName(),
                    bank.getAccounts().get(index).getDepositor().getName().getlName(),
                    bank.getAccounts().get(index).getAcctType(),
                    bank.getAccounts().get(index).getDepositor().getSSN(),
                    bank.getAccounts().get(index).getAcctNum(),
                    bank.getAccounts().get(index).getAcctBal(),
                    bank.getAccounts().get(index).getAcctStat());
        }
        outFile.println();
        outFile.println();

        //flush the output file
        outFile.flush();
    }

    /* Method accountInfo:
     * Input:
     *  Bank bank - Constructor which hold the accounts.
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to output file
     *  kybd - reference to the "test cases" input file
     * Process:
     *  Prompts for the SSN
     *  Calls findAcct() to see if the SSN exists
     *  If the account exists, the account info is printed
     *  Otherwise, an error message is printed
     * Output:
     *  If the SSN exists, the account info  is printed
     *  Otherwise, an error message is printed
     */
    public static void accountInfo(Bank bank, int totalAccountsReadIn, boolean hist,
                                   PrintWriter outFile, Scanner kybd) {

        boolean same = true;
        System.out.print("Enter a SSN: ");
        String requestedSSN = kybd.next();
        String failureReason;
        String transType;
        outFile.println();

        for (int index = 0; index < bank.getAccounts().size(); index++) {
            if (bank.findAcctSSN(bank.getAccounts(), index, outFile).equals(requestedSSN) && hist == false) {
                if (bank.getAccounts().get(index).getAcctStat().equals("Open")) {
                    transType = "Account Information";
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            true, null);
                    outFile.println("Transaction Requested: Account Information");
                    outFile.println("Account Owner: " +
                            bank.getAccounts().get(index).getDepositor().getName().getfName() + " "
                            + bank.getAccounts().get(index).getDepositor().getName().getlName());
                    outFile.println("Account Type: " + bank.getAccounts().get(index).getAcctType());
                    outFile.println("Account Number: " + bank.getAccounts().get(index).getAcctNum());
                    outFile.println("Social Security Number: " +
                            bank.getAccounts().get(index).getDepositor().getSSN());
                    outFile.printf("Account Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                    same = false;
                } else {
                    transType = "Account Information";
                    failureReason = "Error: Account " + bank.getAccounts().get(index).getAcctNum() +
                            " is currently closed";
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            false, failureReason);
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println(failureReason);
                    same = false;
                }

            } else if (bank.findAcctSSN(bank.getAccounts(), index, outFile).equals(requestedSSN) && hist == true) {
                if (bank.getAccounts().get(index).getAcctStat().equals("Open")) {
                    transType = "Account Information with Transaction History";
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            true, null);
                    outFile.println("Transaction Requested: Account Information");
                    outFile.println("Account Owner: " +
                            bank.getAccounts().get(index).getDepositor().getName().getfName() + " "
                            + bank.getAccounts().get(index).getDepositor().getName().getlName());
                    outFile.println("Account Type: " + bank.getAccounts().get(index).getAcctType());
                    outFile.println("Account Number: " + bank.getAccounts().get(index).getAcctNum());
                    outFile.println("Social Security Number: " +
                            bank.getAccounts().get(index).getDepositor().getSSN());
                    outFile.printf("Account Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                    same = false;
                    bank.getAccounts().get(index).getTrans().get(index).printTransactions(bank, index, outFile);
                } else {
                    transType = "Account Info with Transaction History";
                    failureReason = "Error: Account " + bank.getAccounts().get(index).getAcctNum() +
                            " is currently closed";
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            false, failureReason);
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println(failureReason);
                    outFile.println();
                    same = false;
                }

            }
        }
        if (same) {
            outFile.println("Transaction Requested: Account Information");
            outFile.println("Error: Account with SSN of " + requestedSSN + " does not exist");
            outFile.println();

        }                 //flush the output buffer

        outFile.println();
        outFile.flush();                  //flush the output buffer
    }

    /* Method balance:
     * Input:
     *  Bank bank - Constructor which hold the accounts.
     *  totalAccountsReadIn - number of active accounts
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
    public static void balance(Bank bank, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        String transType = "Balance";
        String failureReason;

        System.out.print("Enter the account number: ");           //prompt for account number
        requestedAccount = kybd.nextInt();                        //read-in the account number
        System.out.print("request is" + requestedAccount);

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, requestedAccount);

        if (index == -1) {    //invalid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {  //valid account
            if (bank.getAccounts().get(index).getAcctStat().equals("Open")){
                bank.getAccounts().get(index).addTrans(transType, -1,true, null);
                outFile.println("Transaction Requested: Balance Inquiry");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Current Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                outFile.println();
                if (bank.getAccounts().get(index).getAcctType().equals("CD")) {
                    outFile.println("The Date is: " + bank.getAccounts().get(index).getDate());
                }
            } else {
                failureReason = "Error: Account Number: " + requestedAccount + " is Closed";
                bank.getAccounts().get(index).addTrans(transType, -1, false,failureReason);
                outFile.println("Transaction Requested: Balance Inquiry");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println(failureReason);
            }
        }
        outFile.println();

        outFile.flush();    //flush the output buffer
    }

    /* Method deposit:
     * Input:
     *  Bank bank - Constructor which hold the accounts.
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
    public static void deposit(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        String transType = "Deposit";
        String failureReason;
        double amountToDeposit;

        outFile.println();
        System.out.print("Enter the account number: ");           //prompt for account number
        requestedAccount = kybd.nextInt();                        //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, requestedAccount);

        if (index == -1)      //invalid account
        {
            outFile.println("Transaction Requested: Deposit");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {      //valid account
            if (bank.getAccounts().get(index).getAcctStat().equals("Open")) {
                System.out.print("Enter amount to deposit: ");        //prompt for amount to deposit
                amountToDeposit = kybd.nextDouble();                  //read-in the amount to deposit
                if (amountToDeposit <= 0.00) {
                    //invalid amount to deposit
                    failureReason = String.format("Error: $%.2f is an invalid ammount", amountToDeposit);
                    bank.getAccounts().get(index).addTrans(transType, amountToDeposit,
                            false, failureReason);
                    outFile.println("Transaction Requested: Deposit");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf(failureReason);
                    outFile.println();
                } else { // Valid account
                    bank.getAccounts().get(index).addTrans(transType, amountToDeposit,
                            true, null);
                    outFile.println("Transaction Requested: Deposit");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Old Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                    outFile.println("Amount to Deposit: $" + amountToDeposit);
                    bank.getAccounts().get(index).makeDeposit(amountToDeposit);
                    outFile.printf("New Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                    bank.addTotalBalance(bank.getAccounts().get(index).getAcctType(),amountToDeposit);
                }
            } else {
                failureReason = "Error: Account Number: " + requestedAccount + " is closed";
                bank.getAccounts().get(index).addTrans(transType,-1, false, failureReason);
                outFile.println("Transaction Requested: " + transType);
                outFile.println(failureReason);
            }
        }
        System.out.println();
        outFile.flush();     //flush the output buffer
    }

    /* Function withdrawal:
     * Input:
     *  Bank bank - Constructor which hold the accounts.
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to output file
     *  kypd - reference to the "test cases" input file
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
    public static void withdrawal(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        double amountToWithdraw;
        String transType = "Withdrawal";
        String failureReason;

        outFile.println();
        System.out.println("Enter the account number: ");            //prompt for account number
        requestedAccount = kybd.nextInt();                           //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, requestedAccount);

        if (index == -1) {      //invalid account
            outFile.println("Transaction Requested: " + transType);
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {    //valid account
            if (bank.getAccounts().get(index).getAcctStat().equals("Open")) {
                System.out.print("Enter amount to withdraw: ");      //prompt for amount to withdraw
                amountToWithdraw = kybd.nextDouble();                //read-in the amount to withdraw
                if (amountToWithdraw > bank.getAccounts().get(index).getAcctBal()) {
                    outFile.println("Transaction Requested: Withdrawal");
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Error, Withdrawal amount: $%.2f", amountToWithdraw);
                    outFile.print(" is higher than account balance of: $" + bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                } else if (amountToWithdraw < 0.00) {
                    //invalid amount to withdraw
                    failureReason = String.format("Error: $%.2f is and invalid amount", amountToWithdraw);
                    bank.getAccounts().get(index).addTrans(transType,amountToWithdraw,
                            false, failureReason);
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf(failureReason);
                    outFile.println();
                } else {
                    bank.getAccounts().get(index).addTrans(transType, amountToWithdraw,
                            true, null);
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println("Account Number: " + requestedAccount);
                    outFile.printf("Old Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                    outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                    bank.getAccounts().get(index).makeWithdrawal(amountToWithdraw);            //make the withdrawal
                    outFile.printf("New Balance: $%.2f", bank.getAccounts().get(index).getAcctBal());
                    outFile.println();
                    outFile.println("Withdrawal Successful");
                    bank.subtractTotalBalance(bank.getAccounts().get(index).getAcctType(), amountToWithdraw);
                }
            } else {
                failureReason = "Error: Account " + requestedAccount + " is closed";
                bank.getAccounts().get(index).addTrans(transType, -1, false, failureReason);
                outFile.println("Transaction Requested: " + transType);
                outFile.println(failureReason);
            }
        }
        outFile.println();
        outFile.flush();
    }

    /* Function new account:
     * Input:
     *  Bank bank - Constructor which hold the accounts.
     *  totalAccountsReadIn - number of active accounts
     *  outFile - reference to output file
     *  kypd - reference to the "test cases" input file
     * Process:
     *  Prompts for the requested account
     *  Calls findAcct() to see if the account exists
     *  If the account exists, prompts for another account number
     *  If the account does not exist, it makes the account and returns
     * Output:
     *  For a valid new account, a success message is printed with the new number
     */
    public boolean newAcct(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        int index;
        int year = 0,
            month = 0,
            day= 0;
        String fNameReqested = "";
        String lNameRequested = "";
        String SSNRequested = "";
        int requestedAccount;
        String acctTypeRequested = "";
        boolean acctCreated;
        String transType = " New Account";
        String failureReason;

        System.out.println("Enter new account number: ");
        requestedAccount = kybd.nextInt();

        //call findAcct to search if requestedAccount exists
        index = findAcct(bank, requestedAccount);
        if (index == -1 && (requestedAccount <= 999999) && (requestedAccount >= 100000)) { //Valid Account
            acctCreated = true;
        } else if (requestedAccount > 999999 || requestedAccount < 100000) {
            failureReason = "Error: Account Number: " + requestedAccount + "is an invalid Account Number";
            bank.getAccounts().get(index).addTrans(transType, -1, false, failureReason);
            outFile.println("Transaction Requested: " + transType);
            outFile.println(failureReason);
            acctCreated = false;
        } else {
            failureReason = "Error: Account Number: " + requestedAccount + " Already Exists";
            bank.getAccounts().get(index).addTrans(transType, -1, false, failureReason);
            outFile.println("Transaction Requested: " + transType);
            outFile.println(failureReason);
            return success = false;
        }
        if (acctCreated == true) {
            System.out.print("Enter First Name: ");                 //prompt for first name
            fNameReqested = kybd.next();                            //read-in the first name
        }
        if (acctCreated == true) {
            System.out.print("Enter Last Name: ");                  //prompt for last name
            lNameRequested = kybd.next();                           //reads in the last name
        }
        if (acctCreated == true) {
            System.out.print("Enter Social Security Number: ");     //prompt for the SSN
            SSNRequested = kybd.next();                             //reads in the SSN

            for (int j = 0; j < bank.getAccounts().size(); j++) {
                //Checks if the SSN exists or not
                if (bank.getAccounts().get(j).getDepositor().getSSN().equals(SSNRequested)) {
                    failureReason = "Error: Social Security Number " + SSNRequested +
                            " already exists in another account";
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            false, failureReason);
                    outFile.println("Account Request: " + transType);
                    outFile.println(failureReason);
                    acctCreated = false;
                }
            }
        }

        if (acctCreated) {
            System.out.println();
            System.out.print("Enter Account Type to open: ");       //prompt for account type
            acctTypeRequested = kybd.next();                        //read-in the account type

            //Checks for the input to be correct
            if (acctTypeRequested.equals("Checking") ||
                    acctTypeRequested.equals("Savings") ||
                    acctTypeRequested.equals("CD") ||
                    acctTypeRequested.equals("checking") ||
                    acctTypeRequested.equals("savings") ||
                    acctTypeRequested.equals("cd")) {
                if (acctTypeRequested.equals("CD")) {
                    System.out.println();
                    System.out.println("Choose a date: ");
                    System.out.println("Enter Month: ");
                    month = kybd.nextInt();
                    if (month <= 12 && month >= 1) {
                        System.out.println();
                        System.out.println("Enter the Day: ");
                        day = kybd.nextInt();
                        if (day <= 31 && day >= 1) {
                            System.out.println();
                            System.out.println("Enter the Year: ");
                            year = kybd.nextInt();
                            if (year <=9999 && year >= 1000) {
                                acctCreated = true;
                            } else {
                                failureReason = "The year " + year + " has to be between 1000-9999";
                                bank.getAccounts().get(index).addTrans(transType, -1,
                                        false, failureReason);
                                outFile.println("Account Request: New Account");
                                outFile.println(failureReason);
                                acctCreated = false;
                            }
                        }else {
                            failureReason = "The day " + day + " has to be between 1-31";
                            bank.getAccounts().get(index).addTrans(transType, -1,
                                    false, failureReason);
                            outFile.println("Account Request: New Account");
                            outFile.println(failureReason);
                            acctCreated = false;
                        }
                    }
                        else {
                            failureReason = "The month " + month + " has to be between 1-12";
                            bank.getAccounts().get(index).addTrans(transType, -1,
                                    false, failureReason);
                            outFile.println("Account Request: New Account");
                            outFile.println(failureReason);
                            acctCreated = false;
                        }
                }
            }
        }

        //Creates the new account
        if (acctCreated) {
            outFile.println();
            outFile.println("Transaction Requested: " + transType);
            bank.setAccts(bank.getAccounts(),fNameReqested, lNameRequested, SSNRequested,
                    requestedAccount,acctTypeRequested,
                    0.0, "Open", year, month, day);
            bank.getAccounts().get(bank.getAccounts().size()-1).addTrans(transType,-1,
                    true,null);
            //Displays new Account information
            outFile.println("Account Request: New Account Information");
            outFile.println("Owner: " +
                    bank.getAccounts().get(bank.getAccounts().size()-1).getDepositor().getName().getfName() +
                    " " + bank.getAccounts().get(bank.getAccounts().size()-1).getDepositor().getName().getlName());
            outFile.println("Social Security Number: " +
                    bank.getAccounts().get(bank.getAccounts().size()-1).getDepositor().getSSN());
            outFile.println("Account Type: " + bank.getAccounts().get(bank.getAccounts().size()-1).getAcctType());
            if (bank.getAccounts().get(bank.getAccounts().size()-1).getAcctType().equals("CD")) {
                outFile.println("Account Date: " + bank.getAccounts().get(bank.getAccounts().size()-1).getDate());
            }
            outFile.println("Account " + requestedAccount + " Created");
            success = true;
            outFile.println();
            outFile.flush();
            return success;
        }

        outFile.println();
        outFile.flush();     //flush the output buffer
        return success;
    }

    /*
     * Method deleteAcct()
     * Input:
     * Bank bank - Constructor which hold the accounts.
     * totalAccountsReadIn - number of active accounts
     * outFile - reference to output file
     * kypd - reference to the "test cases" input file
     * Process:
     * Deletes an existing account from the database.
     */

    public boolean deleteAcct(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        int deleteAccount;
        int index;
        String transType = "Delete Account";
        String failureReason;

        outFile.println();
        System.out.println("Enter the account number to delete:");
        deleteAccount = kybd.nextInt();

        index = bank.findAcct(bank, deleteAccount);

        if (index != -1)    //Valid Account
        {
            if (bank.getAccounts().get(index).getAcctStat().equals("Closed")) {
                if (bank.getAccounts().get(index).getAcctBal() == 0) {
                    outFile.println("Transaction Requested: " + transType);
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            true, null);
                    bank.getAccounts().remove(index);
                    outFile.println("Account Number: " + deleteAccount + " has been DELETED");
                    return success = true;
                } else {
                    //balance > 0
                    failureReason = "Error: Account Number: " + deleteAccount + " still has a balance above  $0.00";
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            false, failureReason);
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println(failureReason);
                }
            } else {
                failureReason = "Error: Account Number " + deleteAccount + " is currently open";
                bank.getAccounts().get(index).addTrans(transType, -1, false, failureReason);
                outFile.println("Transaction Requested: " + transType);
                outFile.println(failureReason);
            }
        }
        outFile.println();
        outFile.flush();               //flush the output buffer
        return success = false;
    }

    /* Method findAcctSSN:
     * Input:
     * Bank bank - Constructor which hold the accounts.
     * totalAccountsReadIn - number of active accounts
     * requestedSSN - the requested SSN to check
     * Process:
     *  Performs a linear search on the acctNunArray for the requested SSN
     * Output:
     *  If found, the index of the requested SSN is returned
     *  Otherwise, returns -1
     */
    public String findAcctSSN(ArrayList<Account> accounts, int index, PrintWriter outFile) {
        String SSN = accounts.get(index).getDepositor().getSSN();
        return SSN;
    }

    /*
     * Method close()
     * Input:
     * Bank bank - Constructor which hold the accounts.
     * totalAccountsReadIn - number of active accounts
     * outFile - reference to output file
     * kypd - reference to the "test cases" input file
     * Process:
     * Deletes an existing account from the database.
     */
    public int close(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        String transType = "Close Account";
        System.out.println("Enter your Account Number");
        int requestedAccount = kybd.nextInt();

        for (int index = 0; index < bank.getAccounts().size(); index++) {
            if (bank.getAccounts().get(index).getAcctNum() == requestedAccount) {
                if (bank.getAccounts().get(index).getAcctStat().equals("Open")) {
                    bank.getAccounts().get(index).addTrans(transType, -1,
                            true, null);
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println("Closing Account: " + bank.getAccounts().get(index).getAcctNum());
                    outFile.println();
                    bank.getAccounts().get(index).closeAcct();
                    totalAccountsReadIn -= 1;

                }
            }
        }
        return totalAccountsReadIn;
    }

    /*
     * Method close()
     * Input:
     * Bank bank - Constructor which hold the accounts.
     * totalAccountsReadIn - number of active accounts
     * outFile - reference to output file
     * kypd - reference to the "test cases" input file
     * Process:
     * Deletes an existing account from the database.
     */
    public int Open(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        String transType = "Reopen Account";
        System.out.println("\nEnter your Account Number");
        int requestedAccount = kybd.nextInt();
        outFile.println();
        for (int index = 0; index < bank.getAccounts().size(); index++) {
            if (bank.getAccounts().get(index).getAcctNum() == requestedAccount) {
                if (bank.getAccounts().get(index).getAcctStat().equals("Closed")) {
                    outFile.println("Transaction Requested: " + transType);
                    outFile.println("REOPENING Account: " + bank.getAccounts().get(index).getAcctNum());
                    outFile.println();
                    bank.getAccounts().get(index).reopenAcct();
                    totalAccountsReadIn += 1;
                }
            }
        }
        return totalAccountsReadIn;
    }

    //Getter for Account Array
    public ArrayList<Account> getAccounts() {
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
     * Process:
     * adds a new account to the array of accounts
     */
    public void setAccts(ArrayList<Account> accounts, String fName, String lName,
                         String SSN, int acctNum, String acctType, Double acctBal,
                         String acctStat, int year, int month, int day) {
        if (acctType.equals("Checkings")) {
            accounts.add(new CheckingAccount(fName, lName, SSN, acctNum, acctType, acctBal, acctStat));
        }
        if (acctType.equals("Savings")) {
            accounts.add(new SavingsAccount(fName, lName, SSN, acctNum, acctType, acctBal, acctStat));
        }
        if (acctType.equals("CD")) {
            accounts.add(new CDAccount(fName, lName, SSN, acctNum, acctType, acctBal, acctStat,
                    year, month, day));
        }
    }
    public static Date getTodaysDate(){
        return todaysDate;
    }

    public static void setTodaysDate(Date todaysDate) {
        Bank.todaysDate = todaysDate;
    }
}