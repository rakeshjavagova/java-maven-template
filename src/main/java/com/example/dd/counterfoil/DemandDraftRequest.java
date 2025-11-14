package com.example.dd.counterfoil;

public class DemandDraftRequest {
    private String transactionReferenceNumber;
    private String bankJournalReferenceNumber;
    private String date;
    private String applicantName;
    private String ddInFavourOf;
    private String debitAccountNumber;
    private String drawnOn;
    private Double ddAmount;
    private Double ddCommission;
    private Double totalAmount;
    private String amountInWords;
    
    
	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}
	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}
	public String getBankJournalReferenceNumber() {
		return bankJournalReferenceNumber;
	}
	public void setBankJournalReferenceNumber(String bankJournalReferenceNumber) {
		this.bankJournalReferenceNumber = bankJournalReferenceNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getDdInFavourOf() {
		return ddInFavourOf;
	}
	public void setDdInFavourOf(String ddInFavourOf) {
		this.ddInFavourOf = ddInFavourOf;
	}
	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}
	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}
	public String getDrawnOn() {
		return drawnOn;
	}
	public void setDrawnOn(String drawnOn) {
		this.drawnOn = drawnOn;
	}
	public Double getDdAmount() {
		return ddAmount;
	}
	public void setDdAmount(Double ddAmount) {
		this.ddAmount = ddAmount;
	}
	public Double getDdCommission() {
		return ddCommission;
	}
	public void setDdCommission(Double ddCommission) {
		this.ddCommission = ddCommission;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAmountInWords() {
		return amountInWords;
	}
	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

    
}