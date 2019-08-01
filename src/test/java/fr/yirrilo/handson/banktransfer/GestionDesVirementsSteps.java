package fr.yirrilo.handson.banktransfer;
import static org.fest.assertions.Assertions.assertThat;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import fr.yirrilo.handson.banktransfer.CreditBankAccount;
import fr.yirrilo.handson.banktransfer.DebitBankAccount;
import fr.yirrilo.handson.banktransfer.StateTransfer;

public class GestionDesVirementsSteps {

	private CreditBankAccount creditBankAccount;

	private DebitBankAccount debitBankAccount;

	private StateTransfer stateTransfer;

	@Given("j'ai un compte cheque avec un solde de {long}€")
	public void GivenJAiUnCompteChequeAvecUnSoldeDe(long soldInitial) {
		this.debitBankAccount = new DebitBankAccount(soldInitial);
	}

	@Given("j'ai un compte épargne avec un solde de {long}€​")
	public void GivenJAiUnCompteEpargneAvecUnSoldeDe(long soldInitial) {
		this.creditBankAccount = new CreditBankAccount(soldInitial);
	}

	@When("j'effectue un virement de {long}€ du compte cheque vers le compte épargne")
	public void WhenJEffectueUnVirementDeDuCompteChequeVersLeCompteEpargne(long amount) {
		this.stateTransfer = this.debitBankAccount.debit(amount, this.creditBankAccount);
	}

	@Then("le solde du compte cheque est {long}€​")
	public void ThenLeSoldeDuCompteChequeEst(long soldFinal) {
		assertThat(this.debitBankAccount.sold).describedAs("Solde du compte chèque").isEqualTo(soldFinal).describedAs("Valeur désirée.");
	}

	@Then("le solde du compte épargne est {long}€​")
	public void ThenLeSoldeDuCompteEpargneEst(long soldFinal) {
		assertThat(this.creditBankAccount.sold).isEqualTo(soldFinal).as("La validation du contenu du compte crédit doit être correcte.");
	}

	@Then("le virement est confirmé")
	public void ThenLeVirementEstConfirme() {
		assertThat(this.stateTransfer).isEqualTo(StateTransfer.SUCCESS).as("Le transfert doit être confirmé.");
	}

	@Then("le virement est refusé pour motif hors provision​")
	public void ThenLeVirementEstRefusePourMotifHorsProvision() {
		assertThat(this.stateTransfer).isEqualTo(StateTransfer.OUT_OF_PROVISION).as("Le solde doit être déclaré comme étant insuffisant pour le transfert.");
	}

	@Given("la limite de virement est {long}€​")
	public void GivenLaLimiteDeVirementEst(long limit) {
		this.debitBankAccount.DefineAuthorizedLimit(limit);
	}

	@Given("la limite de virement par défaut et de 400€​")
	public void GivenLaLimiteDeVirementParDefautEtDe400() {
	}

	@Then("le virement est refusé pour motif plafond dépassé")
	public void ThenLeVirementEstRefusePourMotifPlafondDepasse() {
		assertThat(this.stateTransfer).isEqualTo(StateTransfer.LIMIT_EXCEED).as("La limite doit être déclarée comme atteinte dans le cadre du transfert.");
	}
}