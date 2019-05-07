import java.io.*;
import java.util.*;
public class Bank {

    private Account[] accounts = new Account[50];    // holds all the account

    public boolean success = false;                  // To check if to create/delete acount

    /* Method readAccts()
     * Input:
     *  Bank bank - constructor that holds the accounts
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(Bank bank) throws IOException {
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
            bank.getAccounts()[totalAccountsReadIn] = new Account(fName, lName, SSN, acctNum, acctType, acctBal);

            totalAccountsReadIn++;

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
    public static void printAccts(Bank bank, int totalAccountsReadIn, PrintWriter outFile) {
        outFile.println("\t\t\t\t\t\t**********************************");
        outFile.println("\t\t\t\t\t\t\tDatabase of Account Accounts");
        outFile.println("\t\t\t\t\t\t**********************************");
        outFile.println();
        outFile.printf("%-14s   %-14s   %-12s   %-11s   %-8s   %-16s ",
                "First Name","Last Name", "Account Type", "SSN", "Account", "Balance");
        outFile.println();
        outFile.print("----------------------------------------------------------------------------------");
        for (int index = 0; index < totalAccountsReadIn; index++) {
            outFile.println();
            outFile.printf("%-14s | %-14s | %-12s | %-10s | %-8s | $%7.2f ",
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
    public static void accountInfo(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {

        System.out.print("Enter a SSN: ");
        String requestedSSN = kybd.next();
        outFile.println();

        int index = bank.findAcctSSN(bank, totalAccountsReadIn, requestedSSN);

        if (index != -1) {
            outFile.println("Transaction Requested: Account Information");
            outFile.println("Account Owner: " + bank.getAccounts()[index].getName().getfName() + " " +
                    bank.getAccounts()[index].getName().getlName());
            outFile.println("Account Type: " + bank.getAccounts()[index].getAcctType());
            outFile.println("Account Number: " + bank.getAccounts()[index].getAcctNum());
            outFile.println("Social Security Number: " + bank.getAccounts()[index].getDepositor().getSSN());
            outFile.printf("Account Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
            outFile.println();
            outFile.flush();                  //flush the output buffer

            return;
        }

        outFile.println();
        outFile.println("Transaction Requested: Account Information");
        outFile.println("Error: Account with SSN of " + requestedSSN + " does not exist");
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
    public static void balance(Bank bank,int totalAccountsReadIn, PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;

        System.out.print("Enter the account number: ");           //prompt for account number
        requestedAccount = kybd.nextInt();                        //read-in the account number
        System.out.print("request is" + requestedAccount);

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index == -1) {    //invalid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        }
         else{              //valid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.printf("Current Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
            outFile.println();
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
        double amountToDeposit;

        outFile.println();
        System.out.print("Enter the account number: ");           //prompt for account number
        requestedAccount = kybd.nextInt();                        //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index == -1)      //invalid account
        {
            outFile.println("Transaction Requested: Deposit");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else      //valid account
        {
            System.out.print("Enter amount to deposit: ");        //prompt for amount to deposit
            amountToDeposit = kybd.nextDouble();                  //read-in the amount to deposit

            if (amountToDeposit <= 0.00) {
                //invalid amount to deposit
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error: $%.2f is an invalid amount", amountToDeposit);
                outFile.println();
            } else {
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                bank.getAccounts()[index].makeDeposit(amountToDeposit);
                outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
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

        outFile.println();
        System.out.println("Enter the account number: ");            //prompt for account number
        requestedAccount = kybd.nextInt();                           //read-in the account number

        //call findAcct to search if requestedAccount exists
        index = bank.findAcct(bank, totalAccountsReadIn, requestedAccount);

        if (index == -1){      //invalid account
            outFile.println("Transaction Requested: Withdrawal");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        }
          else{    //valid account
            System.out.print("Enter amount to withdraw: ");      //prompt for amount to withdraw
            amountToWithdraw = kybd.nextDouble();                //read-in the amount to withdraw

            if (amountToWithdraw <= 0.00) {
                //invalid amount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                outFile.println();
            } else if (amountToWithdraw > bank.getAccounts()[index].getAcctBal()) {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal amount: $%.2f", amountToWithdraw);
                outFile.print(" is higher than account balance of: $" + bank.getAccounts()[index].getAcctBal());
                outFile.println();
            } else {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
                outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                bank.getAccounts()[index].makeWithdrawal(amountToWithdraw);                 //make the withdrawal
                outFile.printf("New Balance: $%.2f", bank.getAccounts()[index].getAcctBal());
                outFile.println();
                outFile.println("Withdrawal Successful");
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
        String fNameReqested = "";
        String lNameRequested = "";
        String SSNRequested = "";
        int requestedAccount;
        String acctTypeRequested = "";
        boolean acctCreated;


        if (totalAccountsReadIn < bank.getAccounts().length) {
            System.out.println("Enter new account number: ");
            requestedAccount = kybd.nextInt();

            //call findAcct to search if requestedAccount exists
            index = findAcct(bank, totalAccountsReadIn, requestedAccount);
            if (index == -1 && (requestedAccount <= 999999) && (requestedAccount >= 100000)) //Valid Account
            {
                acctCreated = true;
            } else if (requestedAccount > 999999 || requestedAccount < 100000) {
                outFile.println("Transaction Requested: New Account");
                outFile.println("Error: Account Number: " + requestedAccount + " is an Invalid Account Number");
                acctCreated = false;
            } else {
                outFile.println("Transaction Requested: New Account");
                outFile.println("Error: Account Number: " + requestedAccount + " Already Exists");
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

                for (int j = 0; j < totalAccountsReadIn; j++) {
                    //Checks if the SSN exists or not
                    if (bank.getAccounts()[j].getDepositor().getSSN().equals(SSNRequested)) {
                        outFile.println("Account Request: New Account");
                        outFile.println("Error: Social Security Number " + SSNRequested +
                                " already exists in another account");
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
                }
            }

            //Creates the new account
            if (acctCreated) {
                outFile.println();
                outFile.println("Transaction Requested: New Account");
                bank.setAccts(bank, totalAccountsReadIn, fNameReqested, lNameRequested,SSNRequested,requestedAccount,acctTypeRequested,0.0);
                //Displays new Account information
                outFile.println("Account Request: New Account Information");
                outFile.println("Owner: " + bank.getAccounts()[totalAccountsReadIn].getName().getfName() + " " +
                        bank.getAccounts()[totalAccountsReadIn].getName().getlName());
                outFile.println("Social Security Number: " +
                        bank.getAccounts()[totalAccountsReadIn].getDepositor().getSSN());
                outFile.println("Account Type: " + bank.getAccounts()[totalAccountsReadIn].getAcctType());
                outFile.println("Account " + requestedAccount + " Created");
                success = true;
                outFile.println();
                outFile.flush();
                return  success;
            }
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

    public boolean deleteAcct(Bank bank, int totalAccountsReadIn, PrintWriter outFile, Scanner kybd){
        int deleteAccount;
        int index;

        outFile.println();
        System.out.println("Enter the account number to delete:");
        deleteAccount = kybd.nextInt();

        index = bank.findAcct(bank, totalAccountsReadIn, deleteAccount);

        if (index != -1)    //Valid Account
        {
            if (bank.getAccounts()[index].getAcctBal() != 0) {
                outFile.println("Transaction Requested: Delete Account ");
                outFile.println("This account has a remaining balance, please empty the balance and retry");
                //return totalAccountsReadIn;
                return success = false;
            } else {
                while (index < totalAccountsReadIn - 1) {
                    bank.getAccounts()[index] = bank.getAccounts()[index+1];
                    index++;
                }
            }
            outFile.println("Transaction Requested: Delete Account ");
            outFile.println("Account Number: " + deleteAccount + " successfully deleted");
            //totalAccountsReadIn--;
            outFile.flush();                //flush the output buffer
            //return totalAccountsReadIn;
            return success =true;
        }

        //Invalid Account
        outFile.println("Error, invalid account number: " + deleteAccount);
        outFile.println();
        outFile.flush();               //flush the output buffer
        return success = false;
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
    public int findAcct(Bank bank, int totalAccountsReadIn, int requestedAccount) {
        for (int index = 0; index < totalAccountsReadIn; index++)
            if (bank.getAccounts()[index].getAcctNum() == requestedAccount)
                return index;
        return -1;
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
    public int findAcctSSN(Bank bank, int totalAccountsReadIn,
                           String SSNRequested){
        for (int index = 0; index < totalAccountsReadIn; index++)
            if (bank.getAccounts()[index].getDepositor().getSSN().equals(SSNRequested))
                return index;
        return -1;
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
     * Process:
     * adds a new account to the array of accounts
     */
    public void setAccts(Bank bank, int totalAccountsReadIn, String fName, String lName,
                         String SSN, int acctNum, String acctType, Double acctBal) {

        bank.accounts[totalAccountsReadIn] = new Account(fName, lName, SSN, acctNum, acctType,acctBal);
    }
}