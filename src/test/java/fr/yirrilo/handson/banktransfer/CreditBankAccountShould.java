package fr.yirrilo.handson.banktransfer;
import static org.fest.assertions.Assertions.assertThat;

import fr.yirrilo.handson.banktransfer.CreditBankAccount;
import fr.yirrilo.handson.banktransfer.StateTransfer;

public class CreditBankAccountShould {

	public void AddAmountToSoldWhenCallCredit() {
		long expectedSold = 400;
		long amountToBeDebited = 100;
		CreditBankAccount creditBankAccount = new CreditBankAccount(300);

		creditBankAccount.credit(amountToBeDebited);

		assertThat(creditBankAccount.sold).isEqualTo(expectedSold).as("Sold MUST be correctly updated");
	}

	public void ReturnSuccessStateTransferWhenCallCredit() {
		long amountToBeDebited = 100;
		CreditBankAccount creditBankAccount = new CreditBankAccount(300);

		StateTransfer actualState = creditBankAccount.credit(amountToBeDebited);

		assertThat(actualState).isEqualTo(StateTransfer.SUCCESS).as("Credit MUST end with success");
	}
}
