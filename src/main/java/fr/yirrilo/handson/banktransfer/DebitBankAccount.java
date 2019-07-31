package fr.yirrilo.handson.banktransfer;

public class DebitBankAccount extends BankAccount {

	private long authorizedLimit;

	public DebitBankAccount(long initialSold) {
		this(initialSold, 400);
	}

	public DebitBankAccount(long initialSold, long authorizedLimit)	{
		super(initialSold);
		this.authorizedLimit  = authorizedLimit;
	}

	public StateTransfer debit(long amountToBeDebited, BankAccount credit)
	{
		if (ExceedTheAllowedLimit(amountToBeDebited, this.authorizedLimit))
		{
			return StateTransfer.LIMIT_EXCEED;
		}

		if (IsOutOfProvision(this.sold, amountToBeDebited))
		{
			return StateTransfer.OUT_OF_PROVISION;
		}

		StateTransfer stateTransfer = credit.credit(amountToBeDebited);
		if (stateTransfer == StateTransfer.SUCCESS)
		{
			this.sold = this.sold-amountToBeDebited;
		}

		return stateTransfer;
	}

	public void DefineAuthorizedLimit(long newAuthorizedLimit) {
		this.authorizedLimit = newAuthorizedLimit;
	}

	private static boolean ExceedTheAllowedLimit(long amountToBeDebited, long authorizedLimit)
	{
		return amountToBeDebited > authorizedLimit;
	}

	private static boolean IsOutOfProvision(long sold, long amountToBeDebited)
	{
		return sold - amountToBeDebited < 0;
	}
}
