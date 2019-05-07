public class Account {

    //Variable declarations
    private Depositor depositor;
    private Name name;
    private int acctNum;
    private String acctType;
    private Double acctBal;
    private int matDate;
    private int maturityDate;


    //Constructor
    public Account(String fName, String lName, String SSN, int acctNum, String acctType, Double acctBal, int matDate) {
        depositor = new Depositor(fName, lName, SSN);
        name = new Name(fName, lName);
        this.acctNum = acctNum;
        this.acctType = acctType;
        this.acctBal = acctBal;
        this.matDate = matDate;
    }

    //Getters and Setters

    public Depositor getDepositor() {
        return depositor;
    }

    public void setDepositor(Depositor depositor) {
        this.depositor = depositor;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }

    public String getAcctType() {
        return acctType.toUpperCase();
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public Double getAcctBal() {
        return acctBal;
    }

    public void setAcctBal(Double acctBal) {
        this.acctBal = acctBal;
    }

    public int getMatDate() {
        return matDate;
    }

    public void setMatDate(int matDate) {
        this.matDate = matDate;
    }
}


/*
  outFile.println("Transaction Requested: Withdrawal");
        outFile.println("Error: Account number: " + requestedAccount + " does not exist.");

        if (bank.getAccounts()[index].getAcctType().equals("CHECKING")) {
            if (index == 0) {  //invalid account
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Error: Account number: " + requestedAccount + " does not exist.");
            } else if (index == 1) { //valid account

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

                System.out.println("the date on the check  is: " + dateInfo.checkDate.getTime());

                if (dateInfo.checkDate.after(dateInfo.c1) && dateInfo.checkDate.before(dateInfo.calendar)) {
                    if (index == 1)     //valid account
                        System.out.print("Enter amount to withdrawal amount on the check: "); //prompts4amt2 withdraw
                    amountToWithdraw = kybd.nextDouble();               //reads in the amount to withdraw

                    if (amountToWithdraw <= 0.00) {     //invalid amount to withdraw
                        currentBal = bank.getAccounts()[index].getAcctBal();
                        outFile.println("Transaction Requested: Withdrawal");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.printf("Error: $%.2f is an invalid amount", amountToWithdraw);
                        outFile.println("A bouncing fee of $2.50 will be charged from your account.");
                        bank.getAccounts()[index].setAcctBal(currentBal - 2.50);
                        outFile.println();
                        clearCK = false;
                    } else if (amountToWithdraw > bank.getAccounts()[index].getAcctBal()) { //invalid amt to withdraw
                        currentBal = bank.getAccounts()[index].getAcctBal();
                        outFile.println("Transaction Requested: Withdrawal");
                        outFile.println("Account Number: " + requestedAccount);
                        outFile.printf("Error, Withdrawal Amount: $%.2f", amountToWithdraw);
                        outFile.print(" is higher than your balance of " + bank.getAccounts()[index].getAcctBal());
                        outFile.println("A bouncing fee of $2.50 will be charged from your account.");
                        bank.getAccounts()[index].setAcctBal(currentBal - 2.50);
                        outFile.println();
                        clearCK = false;
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
                        clearCK = true;
                    }
                }
            } else if (dateInfo.checkDate.before(dateInfo.c1)) {
                System.out.println("The Check is older than six months and can not be deposited.");
                outFile.println("The Check is older than six months and can not be deposited.");
            }
        } else {
            outFile.println("The entered account is not a Checking\'s Account");
        }
 */