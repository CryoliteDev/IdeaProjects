import java.io.PrintWriter;

public class SavingsAccount extends BankAccount implements Interest {

    //Constructor
    public SavingsAccount(int acctNum, double acctBal){
        super(acctNum, acctBal);
    }

    //Copy Constructor
    public SavingsAccount(BankAccount account){
        super(account);
    }

    public int getAcctNum(){
        return acctNum;
    }

    public double getBalance(){
        return acctBal;
    }

    public void makeDeposit(double amountToDeposit, PrintWriter outFile) {
        try{
            if (amountToDeposit < 0) {
                throw new NegativeAmountEntered(amountToDeposit);
            } else {
                outFile.println("Transaction Requested: Deposit");
                outFile.printf("Old Balance:  $%7.2f", acctBal);
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                acctBal += amountToDeposit;
                outFile.println("New Balance: $" + acctBal);
            }
        } catch (NegativeAmountEntered e) {
            outFile.println(e.getMessage());
        }
    }

    public void makeWithdrawal(double amountToWithdrawal, PrintWriter outFile) {
        try {
            if (amountToWithdrawal < 0) {
                throw new NegativeAmountEntered(amountToWithdrawal);
            } else if (amountToWithdrawal > acctBal) {
                throw new InsufficientFunds(amountToWithdrawal);
            } else {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.printf("Old Balance:  $%7.2f", acctBal);
                outFile.println();
                outFile.println("Amount to Withdraw: $" + amountToWithdrawal);
                acctBal -= amountToWithdrawal;
                outFile.println("New Balance: $" + acctBal);
            }
        } catch (NegativeAmountEntered e) {
            outFile.println(e.getMessage());
        } catch (InsufficientFunds e) {
            outFile.println(e.getMessage());
        }
    }

    public void addInterest(double rate, PrintWriter outFile){
        outFile.printf("Old Balance:  $%7.2f", acctBal);
        outFile.println();
        outFile.println("Amount of Interest: $" + (acctBal * rate));
        acctBal += (acctBal * rate);
        outFile.println("New Balance: $" + acctBal);
    }
}
