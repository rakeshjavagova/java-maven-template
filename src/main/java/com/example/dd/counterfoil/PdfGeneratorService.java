package com.example.dd.counterfoil;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

	
	
	public byte[] createCounterfoilPdf(DemandDraftRequest req) throws DocumentException {
	    Document document = new Document(PageSize.A4, 36, 36, 36, 36);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter.getInstance(document, baos);
	    document.open();

	    // Fonts
	    Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	    Font keyFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	    Font valueFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	    Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

	    // Title
	    Paragraph title = new Paragraph("STATE BANK OF INDIA", titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	    document.add(title);

	    Paragraph subtitle = new Paragraph(
	        "COUNTERFOIL FOR ONLINE APPLICATION FOR DEMAND DRAFT", 
	        titleFont
	    );
	    subtitle.setAlignment(Element.ALIGN_CENTER);
	    subtitle.setSpacingAfter(10f);
	    document.add(subtitle);

	    // Table
	    PdfPTable table = new PdfPTable(4);
	    table.setWidthPercentage(100);
	    table.setWidths(new float[]{2f, 2f, 2f, 2f});
	    table.setSplitLate(false); // avoid double lines

	    float borderThickness = 0.5f;

	    // Rows
	    addKeyValue(table, "Transaction Reference Number", req.getTransactionReferenceNumber(), keyFont, valueFont, borderThickness);
	    addKeyValue(table, "Date", req.getDate(), keyFont, valueFont, borderThickness);

	    addKeyValue(table, "Bank Journal Reference Number", req.getBankJournalReferenceNumber(), keyFont, valueFont, borderThickness);
	    addKeyValue(table, "Name of the Applicant", req.getApplicantName(), keyFont, valueFont, borderThickness);

	    
	    
	    
	    PdfPCell wordsLabel = new PdfPCell(new Phrase("DD in favor of", keyFont));
	    wordsLabel.setPadding(5);
	    wordsLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.addCell(wordsLabel);

	    PdfPCell wordsValue = new PdfPCell(new Phrase(
	            req.getAmountInWords() != null ? req.getDdInFavourOf() : "",
	            cellFont));
	    wordsValue.setPadding(5);
	    wordsValue.setColspan(3);
	    wordsValue.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.addCell(wordsValue);
	    
	    
	    
	    
	    //addKeyValue(table, "DD in favor of", req.getDdInFavourOf(), keyFont, valueFont, borderThickness);
	    addKeyValue(table, "Debit Account Number", req.getDebitAccountNumber(), keyFont, valueFont, borderThickness);

	    addKeyValue(table, "Drawn on", req.getDrawnOn(), keyFont, valueFont, borderThickness);
	    addKeyValue(table, "DD Amount", formatAmount(req.getDdAmount()), keyFont, valueFont, borderThickness);

	    addKeyValue(table, "DD Commission", formatAmount(req.getDdCommission()), keyFont, valueFont, borderThickness);
	    addKeyValue(table, "Total Amount", formatAmount(req.getTotalAmount()), keyFont, valueFont, borderThickness);

	    addKeyValue(table, "Amount in words", "Twenty One Rupees Only", keyFont, valueFont, borderThickness);

	    document.add(table);
	    document.close();

	    return baos.toByteArray();
	}

	private void addKeyValue(PdfPTable table, String key, String value, Font keyFont, Font valueFont, float borderThickness) {
	    PdfPCell keyCell = new PdfPCell(new Phrase(key != null ? key : "", keyFont));
	    keyCell.setPadding(5);
	    keyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    keyCell.setBorderWidth(borderThickness);
	    table.addCell(keyCell);

	    PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", valueFont));
	    valueCell.setPadding(5);
	    valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    valueCell.setBorderWidth(borderThickness);
	    table.addCell(valueCell);
	}

	private String formatAmount(Double amount) {
	    return amount != null ? String.format("%.2f", amount) : "0.00";
	}



	
	
	
	
	///////OLd
	
	
//	public byte[] createCounterfoilPdf(DemandDraftRequest req) throws DocumentException {
//	    Document document = new Document(PageSize.A4, 36, 36, 36, 36); // portrait like the screenshot
//	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	    PdfWriter.getInstance(document, baos);
//	    document.open();
//
//	    // Fonts
//	    Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//	    Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
//	    Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
//
//	    // Title
//	    Paragraph title = new Paragraph("STATE BANK OF INDIA", titleFont);
//	    title.setAlignment(Element.ALIGN_CENTER);
//	    document.add(title);
//
//	    Paragraph subtitle = new Paragraph("COUNTERFOIL FOR ONLINE APPLICATION FOR DEMAND DRAFT", subTitleFont);
//	    subtitle.setAlignment(Element.ALIGN_CENTER);
//	    subtitle.setSpacingAfter(10f);
//	    document.add(subtitle);
//
//	    // Table with equal column widths
//	    PdfPTable table = new PdfPTable(new float[]{2f, 2f, 2f, 2f});
//	    table.setWidthPercentage(100);
//	    table.getDefaultCell().setBorder(Rectangle.BOX);
//
//	    // Rows
//	    addCell(table, "Transaction Reference Number", cellFont);
//	    addCell(table, req.getTransactionReferenceNumber(), cellFont);
//	    addCell(table, "Date", cellFont);
//	    addCell(table, req.getDate(), cellFont);
//
//	    addCell(table, "Bank Journal Reference Number", cellFont);
//	    addCell(table, req.getBankJournalReferenceNumber(), cellFont);
//	    addCell(table, "Name of the Applicant", cellFont);
//	    addCell(table, req.getApplicantName(), cellFont);
//
//	    addCell(table, "DD in favor of", cellFont);
//	    addCell(table, req.getDdInFavourOf(), cellFont);
//	    addCell(table, "Drawn on", cellFont);
//	    addCell(table, req.getDrawnOn(), cellFont);
//
//	    addCell(table, "Debit Account Number", cellFont);
//	    addCell(table, req.getDebitAccountNumber(), cellFont);
//	    addCell(table, "DD Commission", cellFont);
//	    addCell(table, formatAmount(req.getDdCommission()), cellFont);
//
//	    addCell(table, "DD Amount", cellFont);
//	    addCell(table, formatAmount(req.getDdAmount()), cellFont);
//	    addCell(table, "Total Amount", cellFont);
//	    addCell(table, formatAmount(req.getTotalAmount()), cellFont);
//
//	    // Last row: Amount in words
//	    PdfPCell wordsLabel = new PdfPCell(new Phrase("Amount in words", cellFont));
//	    wordsLabel.setPadding(5);
//	    wordsLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
//	    table.addCell(wordsLabel);
//
//	    PdfPCell wordsValue = new PdfPCell(new Phrase(
//	            req.getAmountInWords() != null ? req.getAmountInWords() : "",
//	            cellFont));
//	    wordsValue.setPadding(5);
//	    wordsValue.setColspan(3);
//	    wordsValue.setHorizontalAlignment(Element.ALIGN_LEFT);
//	    table.addCell(wordsValue);
//
//	    document.add(table);
//
//	    document.close();
//	    return baos.toByteArray();
//	}
//
//	private void addCell(PdfPTable table, String text, Font font) {
//	    PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "", font));
//	    cell.setPadding(5);
//	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	    table.addCell(cell);
//	}
//
//	private String formatAmount(Double amount) {
//	    return amount != null ? String.format("%.2f", amount) : "0.00";
//	}
	
	
	
	

}