package fr.yirrilo.handson.banktransfer;
import static org.fest.assertions.Assertions.assertThat;

import org.mockito.Mockito;

import fr.yirrilo.handson.banktransfer.BankAccount;
import fr.yirrilo.handson.banktransfer.DebitBankAccount;
import fr.yirrilo.handson.banktransfer.StateTransfer;

public class DebitBankAccountShould {

	public void ReturnCreditStateOfTransferWhenCallDebitAndDebitAccountHaveProvisionAndAmountNotExceededLimit() {
		long amountToBeDebited = 100;

		BankAccount creditMock = Mockito.mock(BankAccount.class);
		Mockito.when(creditMock.credit(amountToBeDebited)).thenReturn(StateTransfer.FAIL);

		DebitBankAccount debitBankAccount = new DebitBankAccount(300);

		StateTransfer actualState = debitBankAccount.debit(amountToBeDebited, creditMock);

		Mockito.verify(creditMock, Mockito.times(1)).credit(amountToBeDebited);
		assertThat(actualState).isEqualTo(StateTransfer.FAIL);
	}

	public void ReturnCreditStateOfTransferWhenCallDebitAndDebitAccountHaveProvisionAndAmountNotExceededLimitAndCreditIsSuccess() {
		long amountToBeDebited = 100;

		BankAccount creditMock = Mockito.mock(BankAccount.class);
		Mockito.when(creditMock.credit(amountToBeDebited)).thenReturn(StateTransfer.SUCCESS);

		DebitBankAccount debitBankAccount = new DebitBankAccount(300);

		StateTransfer actualState = debitBankAccount.debit(amountToBeDebited, creditMock);

		Mockito.verify(creditMock, Mockito.times(1)).credit(amountToBeDebited);
		assertThat(actualState).isEqualTo(StateTransfer.SUCCESS);
	}

	public void ReturnLimitTransferIsEqual400ByDefault() {
		long amountToBeDebited = 401;

		BankAccount creditMock = Mockito.mock(BankAccount.class);

		DebitBankAccount debitBankAccount = new DebitBankAccount(600);

		StateTransfer actualState = debitBankAccount.debit(amountToBeDebited, creditMock);

		assertThat(actualState).isEqualTo(StateTransfer.LIMIT_EXCEED);
		Mockito.verify(creditMock, Mockito.never()).credit(Mockito.anyLong());
	}

	public void ReturnOutOfProvisionWhenFutureSoldIsLessThanZero() {
		long amountToBeDebited = 301;

		BankAccount creditMock = Mockito.mock(BankAccount.class);

		DebitBankAccount debitBankAccount = new DebitBankAccount(300);

		StateTransfer actualState = debitBankAccount.debit(amountToBeDebited, creditMock);

		assertThat(actualState).isEqualTo(StateTransfer.OUT_OF_PROVISION);
		Mockito.verify(creditMock, Mockito.never()).credit(Mockito.anyLong());
	}

	public void SubtractSoldWhenCallDebitAndDebitAccountHaveProvisionAndAmountNotExceededLimitAndCreditIsSuccess() {
		long expectedSold = 200;
		long amountToBeDebited = 100;

		BankAccount creditMock = Mockito.mock(BankAccount.class);
		Mockito.when(creditMock.credit(amountToBeDebited)).thenReturn(StateTransfer.SUCCESS);

		DebitBankAccount debitBankAccount = new DebitBankAccount(300);

		debitBankAccount.debit(amountToBeDebited, creditMock);

		assertThat(debitBankAccount.sold).isEqualTo(expectedSold);
	}

	public void UnChangeSoldWhenCreditHasFailed() {
		long expectedSold = 300;
		long amountToBeDebited = 100;

		BankAccount creditMock = Mockito.mock(BankAccount.class);
		Mockito.when(creditMock.credit(amountToBeDebited)).thenReturn(StateTransfer.FAIL);

		DebitBankAccount debitBankAccount = new DebitBankAccount(300);

		debitBankAccount.debit(amountToBeDebited, creditMock);

		assertThat(debitBankAccount.sold).isEqualTo(expectedSold);
	}
}