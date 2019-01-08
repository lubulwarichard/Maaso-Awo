package net.unique.awo.model.payment

data class RequestPayment (
    val amount: String, // Amount that will be debited from the payer account.
    val currency: String, // ISO4217 Currency
    val financialTransactionId: String,
    val externalId: String, //External id is used as a reference to the transaction. External id is used for reconciliation. The external id will be included in transaction history report. <br>External id is not required to be unique.
    val payer: Payer,
    val status: String,
    val reason: Reason,
    val payerMessage: String, // Message that will be written in the payer transaction history message field.
    val payeeNote: String // Message that will be written in the payee transaction history note field.
)