import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Bank {

    //HOLDS THE BANK ACCOUNT INFO.
    private ArrayList<Account> accounts;

    public  Bank() {
        accounts = new ArrayList<Account>();
    }
    private int maturityDate;


    public boolean success = false; //TO CHECK IF TO CREATE/DELETE ACCOUNT

    /* Method readAccts()
     * Input:
     *  Bank bank - Constructor which hold the accounts
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public void readAccts() throws IOException {

        System.out.println("test 1");
        //OPEN DATABASE INPUT FILE AND CREATE FILE OBJECT
        //File dbFile = new File("/Users/Xiao/IdeaProjects/BankAccount_4/input.txt");
        File dbFile = new File("input.txt");

        //create Scanner object
        Scanner sc = new Scanner(dbFile);
        while (sc.hasNext()) {
            String fName = sc.next();
            String lName = sc.next();
            String acctType = sc.next();
            String SSN = sc.next();
            int acctNum = Integer.parseInt(sc.next());
            Double acctBal = Double.parseDouble(sc.next());
            Boolean matDate = false;
            Account account = new Account(fName, lName, SSN, acctNum, acctType, acctBal, matDate);
            this.addAccount(account);
        }

        //CLOSE THE INPUT FILE
        sc.close();
    }

    /* Method printAccts:
     * Input:
     *  Bank bank - Constructor which hold the accounts
     *  outFile - reference to the output file
     * Process:
     *  Prints the database of accounts and balances
     * Output:
     *  Prints the database of accounts and balances
     */

    public static void printAccts(Bank bank, PrintWriter outFile) {

        outFile.println("\t\t\t\t\t\t*******************************");
        outFile.println("\t\t\t\t\t\t\tDatabase of Bank Accounts");
        outFile.println("\t\t\t\t\t\t*******************************");
        outFile.println();
        outFile.printf("%-14s   %-14s    %-18s  %-12s   %-6s    %-16s ",
                "First Name", "Last Name", "Account Type", "SSN", "Account", "Balance");
        outFile.println();
        outFile.print("------------------------------------------------------------------------------------------");
        for (int index = 0; index < bank.getAccounts().size(); index++) {
            Account account = bank.getAccounts().get(index);
            Depositor depositor = account.getDepositor();
            Name name = depositor.getName();
            if(name == null)
            {
                System.out.println("this is null");
            }
            outFile.println();
            outFile.printf("%-14s  |  %-14s  |  %-12s  |  %-10s  | %-8s  | $%7.2f ",
                    name.getfName(),
                    name.getlName(),
                    account.getAcctType(),
                    depositor.getSSN(),
                    account.getAcctNum(),
                    account.getAcctBal());
        }
        outFile.println();
        outFile.println();

        //FLUSH TO THE OUTPUT FILE
        outFile.flush();
    }

    /* Method findAcct:
     * Input:
     *  Bank bank - Constructor which holds the accounts
     *  requestedAccount - requested account to find
     * Process:
     *  Performs a linear search on the acctNunArray for the requested account
     * Output:
     *  If found, the index of the requested account is returned
     *  Otherwise, returns -1
     */
    public Account findAcct(int requestedAccount) {
        for (Account account : this.getAccounts()) {
            if (account.getAcctNum() == requestedAccount)
                return account;
        }
        return null;
    }

    public Account findSSN(String requestedSSN) {
        for (Account account : this.getAccounts()) {
            if (account.getDepositor().getSSN().equals(requestedSSN))
                return account;
        }
        return null;
    }

    /* Method balance:
     * Input:
     *  Bank bank - Constructor which hold the active accounts
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
    public void balance(PrintWriter outFile, Scanner kybd) {
        int requestedAccount = requestAccountNumber(kybd);

        //calls findAcct() to search if requestedAccount exists
        Account account = findAcct(requestedAccount);
        String requestType = "Balance Inquiry";
        if (account != null) {  //VALID ACCOUNT
            displayValidAccount(outFile,requestType,requestedAccount);
            outFile.println(String.format("Current Balance: $%.2f\n", account.getAcctBal()));
        } else {    //INVALID ACCOUNT
            displayInvalidAccount(outFile,requestType, requestedAccount);
        }
        outFile.flush();
    }

    /* Method accountInfo
     * Input:
     *  Bank bank - Constructor which hold active accounts
     *  outFile - Reference to the output file
     *  kypd - reference to the Scanner object
     * Process:
     *  Prompts  for the SSN
     *  Calls findAcct() to see if the SSN exists
     *  If the account exists, the account info is printed
     * Output:
     *  if the SSS exists, the account info is printed
     *  Otherwise, an error message is printed.
     */
    public void accountInfo(PrintWriter outFile, Scanner kybd) {

        System.out.print("Enter your SSN: ");
        String requestedSSN = kybd.next();

        Account account = this.findSSN(requestedSSN);
        if (account != null) {
            for (int index = 0; index < accounts.size(); index++) {
                if (account.getDepositor().getSSN().equals(requestedSSN)) {
                    outFile.println("Transaction Requested: Account Information");
                    outFile.println("Account Owner: " + account.getName().getfName() + " " +
                            account.getName().getlName());
                    outFile.println("Account Type: " + account.getAcctType());
                    outFile.println("Account Number: " + account.getAcctNum());
                    outFile.println("Social Security Number: " + account.getDepositor().getSSN());
                    outFile.println(String.format("Account Balance: $%.2f\n", account.getAcctBal()));

                    outFile.flush();    //FLUSH TO OUTPUT BUFFER
                    return;
                }
            }
        } else {
            outFile.println("Transaction Requested: Account Information");
            outFile.println("Error: Account with the SSN of: " + requestedSSN + " does not exist\n");
        }

        outFile.flush();    //FLUSH TO OUTPUT BUFFER
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
    public void deposit(DateInfo dateInfo, PrintWriter outFile, Scanner kybd) {

        int requestedAccount;

        requestedAccount = requestAccountNumber(kybd);
        Account account = findAcct(requestedAccount);
        String requestType = "Deposit";

        if (account != null) {
            if (account.getAcctType().equals("CD"))
                depositToCDAccount(dateInfo, requestedAccount, outFile, kybd);
            else
                makeDeposit(account,requestType, requestedAccount,outFile,kybd);

        } else {
            displayInvalidAccount(outFile, requestType, requestedAccount);
        }

        outFile.flush();
    }


    /*Method depCdAcct
     *Function
     * Input:
     *  Date date - reference to Calendar class.
     *  index - the active account
     *  requestedAccount - the active account
     *  outFile - reference to output file
     *  kybd - reference to the "test cases" input file
     * Process:
     *  prompts for the maturity date if it doesn't already have one
     *  makes deposit for valid AcctType and valid amount
     * Output:
     *  For a valid deposit, the deposit transaction is printed
     *  Otherwise, an error message is printed
     *  returns the matDate
     */
    public void depositToCDAccount(DateInfo dateInfo, int requestedAccount,
                                   PrintWriter outFile, Scanner kybd) {
        outFile.println();
        Account account = this.findAcct(requestedAccount);
        String requestType = "Deposit CD Account";

        //HAS NO MATURITY DATE
        if (!account.getMatDate()) {
            setMatDate(account, dateInfo,requestedAccount, requestType,outFile,kybd);
        }
        //HAS A MATURITY DATE
        if (account.getMatDate()) {
            if (dateInfo.maturityCal.getTime().before(dateInfo.calendar.getTime())) {
                makeDeposit(account,requestType,requestedAccount,outFile,kybd);
            } else if (dateInfo.maturityCal.getTime().after(dateInfo.calendar.getTime())) {
                displayValidAccount(outFile,requestType,requestedAccount);
                outFile.println("Error: Your Maturity Date is still in Affect");
            }
        }

        outFile.println();
        outFile.flush();    //FLUSH TO OUTPUT BUFFER
    }

    private void makeDeposit(Account account, String requestType, int requestedAccount, PrintWriter outFile, Scanner kybd) {
        double amountToDeposit = requestDepositAmount(kybd);

        if (amountToDeposit <= 0.00) {
            displayValidAccount(outFile,requestType,requestedAccount);
            outFile.println(String.format("Amount to Deposit $" + amountToDeposit));
            outFile.println(String.format("Error: $%.2f is an invalid amount\n", amountToDeposit));
        } else {
            double currentBal = account.getAcctBal();
            displayValidAccount(outFile,requestType, requestedAccount);
            outFile.println(String.format("Old Balance: $%.2f", account.getAcctBal()));
            outFile.println("Amount to Deposit: $" + amountToDeposit);
            account.setAcctBal(currentBal + amountToDeposit);
            outFile.println(String.format("New Balance: $%.2f\n", account.getAcctBal()));
        }
        outFile.flush();
    }


    public void withdrawal (DateInfo dateInfo, PrintWriter outFile, Scanner kybd) {
        int requestedAccount = requestAccountNumber(kybd);
        String requestType = "Withdrawal";
        Account account = this.findAcct(requestedAccount);

        if (account != null) {
            if (account.getAcctType().equals("CD"))
                withCdAcct(dateInfo, requestedAccount,outFile,kybd);
            else
                makeWithdrawal(requestedAccount,outFile, kybd);
        } else {
            displayInvalidAccount(outFile, requestType, requestedAccount);
        }

    }

    /*Method withCdAcct
     *Function
     * Input:
     *  Bank bank - Constructor which contain the accounts
     *  Date date - reference to Calendar class
     *  index - the active account
     *  requestedAccount - the active account
     *  outFile - reference to output file
     *  kybd - reference to the Scanner object
     * Process:
     *  prompts for the maturity date if it doesn't already have one
     *  makes withdrawal for valid AcctType and valid amount
     * Output:
     *  For a valid deposit, the deposit transaction is printed
     *  Otherwise, an error message is printed
     *  returns the matDate
     */
    public void withCdAcct(DateInfo dateInfo, int requestedAccount,
                           PrintWriter outFile, Scanner kybd) {

        double amountToWithdraw;
        double currentBal;

        Account account = this.findAcct(requestedAccount);
        String requestType = "Withdrawal CD Account";

        //NO MATURITY DATE
        if (!account.getMatDate()) {
            setMatDate(account,dateInfo,requestedAccount, requestType,outFile,kybd);
        }

        if (account.getMatDate()){

            if (dateInfo.maturityCal.getTime().after(dateInfo.calendar.getTime())) {    //MATURITY DATE IN AFFECT
                displayValidAccount(outFile,requestType,requestedAccount);
                outFile.println("Error: Your Maturity Date is still in Affect");
            } else if (dateInfo.maturityCal.getTime().before(dateInfo.calendar.getTime())) { //AFTER MATURITY DATE
                //PROMPTS FOR AND READS-IN AMOUNT TO WITHDRAW
                amountToWithdraw = requestWithdrawalAmount(kybd);

                if (amountToWithdraw <= 0.00) {     //INVALID AMOUNT TO WITHDRAW
                    displayValidAccount(outFile, requestType,requestedAccount);
                    outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                    outFile.println();
                } else if (amountToWithdraw > account.getAcctBal()) {//INVALID AMOUNT TO WITHDRAW
                    displayValidAccount(outFile, requestType, requestedAccount);
                    outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                    outFile.print(" is higher than your balance of " + account.getAcctBal());
                    outFile.println();
                } else {    //VALID AMOUNT TO WITHDRAW
                    currentBal = account.getAcctBal();
                    displayValidAccount(outFile,requestType,requestedAccount);
                    outFile.printf("Old Balance: $%.2f\n", account.getAcctBal());
                    outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                    account.setAcctBal(currentBal - amountToWithdraw);   //MAKES THE WITHDRAWAL
                    outFile.printf("New Balance: $%.2f\n", account.getAcctBal());
                }
            }
        }

        outFile.println();
        outFile.flush();    //FLUSH TO OUTPUT BUFFER
    }

    /* Function Withdrawal
     * Input:
     *  Bank bank - Constructor which holds the active accounts.
     *  outFile - reference to output file
     *  kybd - reference to the input Scanner
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
    public void makeWithdrawal(int requestedAccount, PrintWriter outFile, Scanner kybd) {
        double amountToWithdraw;
        double currentBal;

        //CALLS findAcct() TO SEARCH IF requestedAccount EXIST OR NOT.
        Account account = this.findAcct(requestedAccount);
        String requestType = "Withdrawal";

        if (account != null) {
            amountToWithdraw = requestWithdrawalAmount(kybd);

            if (amountToWithdraw <= 0.0) {  //INVALID AMOUNT TO WITHDRAW
                displayValidAccount(outFile,requestType,requestedAccount);
                outFile.println(String.format("Error: $%.2f is an invalid amount\n", amountToWithdraw));
            } else if (amountToWithdraw >account.getAcctBal()) { //INVALID AMOUNT TO WITHDRAW
                displayValidAccount(outFile,requestType,requestedAccount);
                outFile.print(String.format("Error, Withdrawal Amount: $%.2f", amountToWithdraw));
                outFile.print(" is higher than you balance of " + account.getAcctBal());
                outFile.println();
            } else {    //VALID AMOUNT TO WITHDRAW
                currentBal = account.getAcctBal();
                displayValidAccount(outFile,requestType,requestedAccount);
                outFile.println(String.format("Old Balance: $%.2f", account.getAcctBal()));
                outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                account.setAcctBal(currentBal - amountToWithdraw);
                outFile.println(String.format("New Balance: $%.2f\n", account.getAcctBal()));
            }
        }

        outFile.println();
        outFile.flush();    //FLUSH THE OUTPUT BUFFER
    }





    /* Method clearCheck
     * Input:
     *  Bank bank - Constructor which contains all account
     *  outFile - Reference to the output file
     *  kypd - reference to the Scanner object
     * Process:
     *  Prompts  for the SSN
     *  Calls findAcct() to see if the SSN exists
     *  If the account exists, the account info is printed
     * Output:
     *  if the SSS exists, the account info is printed
     *  Otherwise, an error message is printed.
     */
    public boolean clearCheck(DateInfo dateInfo, PrintWriter outFile, Scanner kybd) {

        //VARIABLE DECLARATIONS
        int yearCK, monthCK, dayOfMonthCK;
        int requestedAccount;
        double amountToWithdraw;
        double currentBal;
        boolean clearCK = false;
        String requestType = "Withdrawal from Check";

        requestedAccount = requestAccountNumber(kybd);

        Account account = this.findAcct(requestedAccount);

        if (account != null) {   //VALID ACCOUNT
            if (account.getAcctType().equals("CHECKING")) {
                //PROMPTS FOR THE DATE ON CHECK
                System.out.println("Enter the Date on the Check: \n In the Format of MM DD YYYY");
                monthCK = kybd.nextInt();
                dayOfMonthCK = kybd.nextInt();
                yearCK = kybd.nextInt();
                monthCK = monthCK - 1;      //SUBTRACTS 1 BECAUSE THE Calender.MONTH starts at 0.

                //THE ENTERED DATA IS SET OT THE CHECKdATE CALENDAR
                dateInfo.checkDate.set(yearCK, monthCK, dayOfMonthCK);
                dateInfo.checkDate.set(Calendar.MONTH, monthCK);
                dateInfo.checkDate.set(Calendar.DAY_OF_MONTH, dayOfMonthCK);
                dateInfo.checkDate.set(Calendar.YEAR, yearCK);

                //SETS THE c1 CALENDAR DATE 6 MONTH BEFORE TODAY'S DATE.
                dateInfo.c1.add(Calendar.MONTH, -6);

                System.out.println("The date on the check  is: " + dateInfo.checkDate.getTime());

                if (dateInfo.checkDate.after(dateInfo.c1) &&
                        dateInfo.checkDate.before(dateInfo.calendar)) {  //VALID ACCOUNT

                    //PROMPTS AND READS-IN AMOUNT TO WITHDRAW
                    System.out.print("Enter amount to withdrawal amount on the check: ");
                    amountToWithdraw = kybd.nextDouble();

                    if (amountToWithdraw <= 0.00) {     //INVALID AMOUNT TO WITHDRAW
                        currentBal = account.getAcctBal();
                        outFile.println("Transaction Requested: Clear Check");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.println(String.format("Error: $%.2f is an invalid amount", amountToWithdraw));
                        outFile.println("A bouncing fee of $2.50 will be charged from your account.");
                        account.setAcctBal(currentBal - 2.50);
                        outFile.println();
                        clearCK = false;
                    } else if (amountToWithdraw > account.getAcctBal()) { //INVALID AMOUNT
                        currentBal = account.getAcctBal();
                        outFile.println("Transaction Requested: Clear Check");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.print(String.format("Error, Withdrawal Amount: $%.2f", amountToWithdraw));
                        outFile.println(" is higher than your balance of " + account.getAcctBal());
                        outFile.println("A bouncing fee of $2.50 will be charged from your account.");
                        account.setAcctBal(currentBal - 2.50);
                        outFile.println();
                        clearCK = false;
                    } else {    //VALID AMOUNT TO WITHDRAW
                        currentBal = account.getAcctBal();
                        outFile.println("Transaction Requested: Clear Check");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.println(String.format("Old Balance: $%.2f", account.getAcctBal()));
                        outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                        account.setAcctBal(currentBal - amountToWithdraw); //MAKES THE WITHDRAWAL
                        outFile.println(String.format("New Balance: $%.2f", account.getAcctBal()));
                        outFile.println();
                        clearCK = true;
                    }
                } else if (dateInfo.checkDate.before(dateInfo.c1)) { //INVALID CHECK
                    outFile.println("The Check is older than six months and can not be deposited.");
                }
            } else { //INVALID ACCOUNT TYPE
                outFile.println("Transaction Requested: Clear Check");
                outFile.println("Error: Account Number: " + requestedAccount + " is not a Checking Account\n");
            }

        } else { //INVALID ACCOUNT NUMBER
            displayInvalidAccount(outFile, requestType,requestedAccount);
        }

        outFile.flush();        //FLUSH TO OUTPUT BUFFER
        return clearCK;
    }










    /* Function new account:
     * Input:
     *  Bank bank - reference to array of account numbers
     *  outFile - reference to output file
     *  kybd - reference to the Scanner object
     * Process:
     *  prompts for the requested account
     *  calls findAcct() to see if the account exists
     *  if the account exists, prompts for another account number
     *  if the account does not exist, it makes the account and returns the new account number
     * Output:
     *  for a valid new account, a success message is printed with the new account info.
     */
    public void newAcct(PrintWriter outFile, Scanner kybd) {

        String fNameRequested = "";
        String lNameRequested = "";
        String SSNRequested = "";
        int requestedAccount;
        String acctTypeRequested = "";
        boolean matDate = false;
        boolean acctCreated = false;
        String requestType = "New Account";

            requestedAccount = requestAccountNumber(kybd);

            Account account = this.findAcct(requestedAccount);

            if (account == null && (requestedAccount <= 999999) && (requestedAccount >= 100000)) {
                acctCreated = true;
            } else {    //INVALID ACCOUNT NUMBER
                outFile.println("Transaction Requested: " + requestType);
                outFile.println("Error: Account Number: " + requestedAccount + " already exists");
                acctCreated = false;
            }

            if (acctCreated) {
                //PROMPTS AND READS-IN THE FIRST NAME
                System.out.println("Enter First Name: ");
                fNameRequested = kybd.next();

                //PROMPTS AND READS-IN THE LAST NAME
                System.out.println("Enter Last Name: ");
                lNameRequested = kybd.next();

                //PROMPTS AND READS-IN acctType
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
                //SETS THE CD ACCOUNT TO 0 => NO MATURITY DATE
                if (acctTypeRequested.equals("cd") || acctTypeRequested.equals("CD"))
                    matDate = false;

                System.out.println("Enter Social Security Number: ");
                SSNRequested = kybd.next();
                Account account1 = findSSN(SSNRequested);
                for (int i = 0; i < accounts.size(); i++) {
                    if (account1 != null) {
                        outFile.println("Transaction Requested: New Account");
                        outFile.println("Error: Social Security Number " + SSNRequested + " already exists");
                        acctCreated = false;
                    }
                }
            }

            if (acctCreated) {
                outFile.println();
                outFile.println("Transaction requested: New Account");

                //SETS THE NEW ACCT TO THE NXT AVAILABLE SPOT W/ BAL OF $0
                accounts.add(new Account(fNameRequested, lNameRequested, SSNRequested,
                        requestedAccount, acctTypeRequested, 0.0, matDate));
                //DISPLAYS NEW ACCOUNT INFO
                outFile.println("Accounts Request: New Account Information");
                outFile.println("Owner " + accounts.get(accounts.size()-1).getDepositor().getName().getfName() + " " +
                        accounts.get(accounts.size()-1).getDepositor().getName().getlName());
                outFile.println("Social Security Number: " + accounts.get(accounts.size()-1).getDepositor().getSSN());
                outFile.println("Account Type: " + accounts.get(accounts.size()-1).getAcctType());
                outFile.println("Account Number: " + requestedAccount + " Successfully Created\n");

                outFile.flush();
            }

            if (!acctCreated) {
                outFile.println("Error: Account not created\n");
            }


        outFile.flush();

    }


    /* Function delete account:
     * Input:
     *  Bank  - Constructor which contains the accounts
     *  outFile - reference to the output file
     *  kybd - reference to the Scanner object
     * Process:
     *  prompts for the requested account
     *  calls findAcct() to see if the account exists
     *  if the account exists, checks if the account has balance
     *  if there is balance, it returns the same array without deletion
     *  if the account does not have a balance, it deletes the account and returns the edited acctNumArrayList
     * Output:
     *  for a valid deletion, success message is printed with deleted account number.
     */
    public void deleteAcct(PrintWriter outFile, Scanner kybd) {

        int deleteAccount;

        System.out.println("Enter the account number to delete: ");
        deleteAccount = kybd.nextInt();

        //CALLS findAcct() TO SEARCH IF requestedAccount EXISTS OR NOT
        Account account = this.findAcct(deleteAccount);

        if (account != null) {  //Valid account number
            if (account.getAcctBal() != 0) {
                outFile.println("Transaction Requested: Delete Account");
                outFile.println("Account Number: " + deleteAccount);
                outFile.println("This account has a remaining balance of " + account.getAcctBal() +
                        " please empty the balance and retry");
                outFile.println();
                //return success;        //returns the array w/out deleting an account.
            } else {
                removeAccount(account);
            }
            outFile.println("Transaction Requested: Delete Account");
            outFile.println("Account Number: " + deleteAccount + " has been successfully deleted");
            outFile.flush();        //flush the output buffer
            outFile.println();
            //return success = true;        //returns the numAccts w/ the account deleted
        }

        //Invalid Account
        outFile.println("Error, invalid account number: " + deleteAccount);
        outFile.println();
        outFile.flush();        //flush the output buffer
        //return success;        //returns the numAccts w/out the account deleted.
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
     * matDate
     * Process:
     * adds a new account to the array of accounts
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    private void setMatDate(Account account, DateInfo dateInfo,int accountNum, String requestType, PrintWriter outFile, Scanner kybd) {
        System.out.println("Choose a Maturity Date.\nIt can be either 6, 12, 18, or 24 months.");
        maturityDate = kybd.nextInt();
        if (maturityDate <= 24 && maturityDate %6 == 0) {
            dateInfo.maturityCal.add(Calendar.MONTH, maturityDate);
            outFile.println("Your maturity date has been set to: " + dateInfo.maturityCal.getTime());
            account.setMatDate(true);
        } else {
            displayValidAccount(outFile, requestType, accountNum);
            outFile.println("Error: Invalid Maturity Date");
            account.setMatDate(false);
        }
    }

    public void addNewAccount(Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }

    public double requestDepositAmount(Scanner kybd) {
        System.out.println("Enter amount to Deposit: ");
        return kybd.nextDouble();
    }
    public double requestWithdrawalAmount(Scanner kybd) {
        System.out.println("Enter amount to Withdraw: ");
        return kybd.nextDouble();
    }

    public int requestAccountNumber(Scanner kybd) {
        System.out.println("Enter the Account Number:");
        return kybd.nextInt();
    }

    public void displayValidAccount(PrintWriter outFile, String requestType, int accountNumber){
        outFile.println("Transaction Requested: " + requestType);
        outFile.println("Account Number: " + accountNumber);
    }

    public void displayInvalidAccount(PrintWriter outFile, String requestType, int accountNumber) {
        outFile.println("Transaction Requested: " + requestType);
        outFile.println("Error: Account Number: " + accountNumber + " does not exist\n");
    }
}