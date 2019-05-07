import java.io.*;
import java.util.Scanner;

public class BankAccounts {

    public static void main(String[] args) throws IOException {

        //MUHAMMAD-AZHAR CISC 3115-MW9
        //constant definitions
        final int MAX_NUM = 50;

        //variable declarations
        int[] acctNumArray = new int[MAX_NUM];			//array of account numbers
        double[] balanceArray = new double[MAX_NUM];	//array of balances
        int numAccts;									//number of accounts
        char choice;									//menu item selected
        boolean not_done = true;						//loop control flag

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
        numAccts = readAccts(acctNumArray,balanceArray,MAX_NUM);
        printAccts(acctNumArray,balanceArray,numAccts,outFile);

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
                    printAccts(acctNumArray,balanceArray,numAccts,outFile);
                    break;
                case 'b':
                case 'B':
                    balance(acctNumArray,balanceArray,numAccts,outFile,kybd);
                    break;
                case 'd':
                case 'D':
                    deposit(acctNumArray,balanceArray,numAccts,outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    withdrawal(acctNumArray,balanceArray,numAccts,outFile,kybd);
                    break;
                case 'n':
                case 'N':
                    numAccts = newAcct(acctNumArray,balanceArray,numAccts,outFile,kybd);
                    break;
                case 'x':
                case 'X':
                    numAccts = deleteAcct(acctNumArray,balanceArray,numAccts,outFile,kybd);
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
     *  acctNumArray - reference to array of account numbers
     *  balanceArray - reference to array of account balances
     *  maxAccts - maximum number of active accounts allowed
     * Process:
     *  Reads the initial database of accounts and balances
     * Output:
     *  Fills in the initial account and balance arrays and returns the number of active accounts
     */
    public static int readAccts(int[] acctNumArray, double[] balanceArray, int maxAccts) throws IOException {

        //open database input file and creates File object
        //File dbFile = new File("/Users/Xiao/IdeaProjects/BankAccount_1/input.txt");
        File dbFile = new File("input.txt");

        //create Scanner object
        Scanner sc = new Scanner(dbFile);

        int count = 0;                          //account number counter

        while (sc.hasNext() && count < maxAccts) {
            acctNumArray[count] = sc.nextInt();
            balanceArray[count] = sc.nextDouble();
            count++;
        }

        //close the input file
        sc.close();

        //return the account number count
        return count;
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
    public static void printAccts(int[] acctNumArray, double[] balanceArray, int numAccts, PrintWriter outFile) {

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

        //runs through the array to find the requested account number
        for (int index = 0; index < numAccts; index++)
            if (acctNumArray[index] == requestedAccount)
                return index;   //returns the index where the account is located
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
    public static void balance(int[] acctNumArray, double[] balanceArray, int numAccts, PrintWriter outFile,
                               Scanner kybd) {
        int requestedAccount;
        int index;

        System.out.println();
        System.out.print("Enter the account number: ");			//prompt for account number
        requestedAccount = kybd.nextInt();						//read-in the account number

        //calls findAcct() to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        if (index == -1) {    //invalid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Error: Account number " + requestedAccount + " does not exist");
        } else {    //valid account
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.printf("Current Balance: $%.2f", balanceArray[index]);
            outFile.println();
        }
        outFile.println();
        outFile.flush();        //flush the output buffer
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

        System.out.print("Enter the account number: ");			//prompt for account number
        requestedAccount = kybd.nextInt();						//read-in the account number

        //call findAcct to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

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
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                balanceArray[index] += amountToDeposit;         //make the deposit
                outFile.printf("New Balance: $%.2f", balanceArray[index]);
                outFile.println();
            }
        }
        outFile.println();
        outFile.flush();        //flush the output buffer
    }

    /* Function Withdrawal
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
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
    public static void withdrawal(int[] acctNumArray, double[] balanceArray, int numAccts,
                                  PrintWriter outFile, Scanner kybd) {
        int requestedAccount;
        int index;
        double amountToWithdraw;

        System.out.println("Enter the account number: ");       //prompts for account number
        requestedAccount = kybd.nextInt();                      //reads in the account number

        //call findAcct to search if requestedAccount exists.
        index = findAcct(acctNumArray, numAccts, requestedAccount);

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
            } else if (amountToWithdraw > balanceArray[index]) {    //invalid amount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                outFile.print(" is higher than your balance of " + balanceArray[index]);
                outFile.println();
            } else {    //valid amount to withdraw
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Old Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Amount to Withdraw: $" + amountToWithdraw);
                balanceArray[index] -= amountToWithdraw;        //makes the withdrawal
                outFile.printf("New Balance: $%.2f", balanceArray[index]);
                outFile.println();
            }
        }
        outFile.println();
        outFile.flush();        //flush the output buffer
    }

    /* Function new account:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
     *  outFile - reference to output file
     * Process:
     *  prompts for the requested account
     *  calls findAcct() to see if the account exists
     *  if the account exists, prompts for another account number
     *  if the account does not exist, it makes the account and returns the new account number
     * Output:
     *  for a valid new account, a success message is printed with the new account number.
     */
    public static int newAcct(int[] acctNumArray, double[] balanceArray, int numAccts,
                              PrintWriter outFile, Scanner kybd) {

        int requestedAccount;
        int index;

        System.out.println("Enter new account number: ");       //prompts for new account number
        requestedAccount = kybd.nextInt();                      //reads in account number

        //calls findAcct() to search if requestedAccounts exits
        index = findAcct(acctNumArray,numAccts,requestedAccount);

        if (index == -1 && (requestedAccount <= 999999) && (requestedAccount >= 100000)) {  //Valid Account
            acctNumArray[numAccts] = requestedAccount;          //adds requestedAccount to the acctNumArray
            balanceArray[numAccts] = 0;                         //sets the balance of new account to zero dollars.

            outFile.println("Transaction Requested: New Account ");
            outFile.println("Account Number: " + requestedAccount + " Successfully Created");
            numAccts++;

            outFile.println();
            return numAccts;        //returns the array w/ the new account added to the acctNumArray.
        }

        //Invalid Account
        outFile.println("Transaction Requested: New Account");
        outFile.println("Error, Account Number: " + requestedAccount + " is unavailable");
        outFile.println();

        outFile.flush();        //flush the output buffer
        return numAccts;        //returns the array w/out adding a new account.
    }

    /* Function delete account:
     * Input:
     *  acctNumArray - array of account numbers
     *  balanceArray - array of account balances
     *  numAccts - number of active accounts
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
    public static int deleteAcct(int[] acctNumArray, double[] balanceArray, int numAccts,
                                 PrintWriter outFile, Scanner kybd) {

        int deleteAccount;
        int index;

        System.out.println("Enter the account number to delete");       //prompts for the account number to delete
        deleteAccount = kybd.nextInt();                                 //reads in the account number.

        index = findAcct(acctNumArray, numAccts, deleteAccount);

        if (index != -1) {  //Valid account number
            if (balanceArray[index] != 0) {
                outFile.println("Transaction Requested: Delete Account");
                outFile.println("Account Number: " + deleteAccount);
                outFile.println("This account has a remaining balance of " + balanceArray[index] +
                        " please empty the balance and retry");
                outFile.println();
                return numAccts;        //returns the array w/out deleting an account.
            } else {
                while (index < numAccts) {
                    acctNumArray[index] = acctNumArray[index+1];
                    balanceArray[index] = balanceArray[index+1];
                    index++;
                }
            }
            outFile.println("Transaction Requested: Delete Account");
            outFile.println("Account Number: " + deleteAccount + " has been successfully deleted");
            numAccts--;
            outFile.flush();        //flush the output buffer
            outFile.println();
            return numAccts;        //returns the numAccts w/ the account deleted
        }

        //Invalid Account
        outFile.println("Error, invalid account number: " + deleteAccount);
        outFile.println();
        outFile.flush();        //flush the output buffer
        return numAccts;        //returns the numAccts w/out the account deleted.
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