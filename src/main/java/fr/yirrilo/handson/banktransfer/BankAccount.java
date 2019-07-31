package fr.yirrilo.handson.banktransfer;

public class BankAccount {

    public long sold;

	public BankAccount(long sold) {
		super();
		this.sold = sold;
	}

	public long getSold() {
		return sold;
	}

	protected void setSold(long sold) {
		this.sold = sold;
	}        
	
	public StateTransfer credit(long amount)
    {
        this.sold += amount;
        return StateTransfer.SUCCESS;
    }
}
