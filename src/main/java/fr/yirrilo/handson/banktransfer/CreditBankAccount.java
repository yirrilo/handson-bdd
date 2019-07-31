package fr.yirrilo.handson.banktransfer;

public class CreditBankAccount extends BankAccount {

	public CreditBankAccount(long sold) {
		super(sold);
	}
	
    public StateTransfer credit(long amount)
    {
        this.sold += amount;
        return StateTransfer.SUCCESS;
    }
}
