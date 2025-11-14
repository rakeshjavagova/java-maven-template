package com.example.PrintDownload;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import static com.itextpdf.text.Element.ALIGN_CENTER;

@RestController
@RequestMapping("/test")
public class TestController {
	
	//private static final Font headBoldFont =new Font(Font.FontFamily.HELVETICA,12,Font.BOLD,BaseColor.GRAY);
	
	private static final Font boldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.GRAY);
	private static final Font normalFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY);
//	private static final BaseColor headerBaseColor = new BaseColor( 37f,.18f,  .47f);
	private static final BaseColor headerBaseColor = new BaseColor(94, 46, 120);
	private static final Font amountColor = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, headerBaseColor);
	private static final Font headBoldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GRAY);
	private static final Font headNormalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GRAY);
	
	
	
	@GetMapping
	public  String getWorld() {
		
//		PDFCreationData request=new PDFCreationData();
//		
//		request.setDebitStatus("Success");
//		request.setDate("2025-07-15");
//		request.seteChequeNo("ECHK123456");
//		request.setInFavorOf("John Doe");
//		request.setDrawnBranchName("Hyderabad Main Branch");
//		request.setDdAmount("5000.00");
//		request.setJournalNo("JRN987654");
//		request.setDebitAccountNo("123456789012");
//		request.setDdCommission("50.00");
//		request.setDeliveryMode("Courier");
		
		
		
		
		return "Hello Bharath,..";
	}
	
	
	
	
	@GetMapping(value = "/generate-dd-pdf", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> geteeWorld() {

	    PDFCreationData request = new PDFCreationData();
	    request.setDebitStatus("Success");
	    request.setDate("2025-07-15");
	    request.seteChequeNo("ECHK123456");
	    request.setInFavorOf("John Doe");
	    request.setDrawnBranchName("Hyderabad Main Branch");
	    request.setDdAmount("5000.00");
	    request.setJournalNo("JRN987654");
	    request.setDebitAccountNo("123456789012");
	    request.setDdCommission("50.00");
	    request.setDeliveryMode("Courier");

	    Optional<byte[]> pdfBytes = generateDDPdf(request);

	    if (pdfBytes.isPresent()) {
	        String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes.get());

	        Map<String, String> response = new HashMap<>();
	        response.put("pdfFile", base64Pdf);

	        return ResponseEntity.ok(response);
	    } else {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", "Failed to generate PDF");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	}
	
	
	
	
	
	public Optional<byte[]> generateDDPdf(PDFCreationData request) {
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    Document document = new Document(PageSize.A4, 50, 50, 60, 50);
	    try {
	        PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();

	        // Add logo
	        Image logo = Image.getInstance(Objects.requireNonNull(TestController.class.getResource("/image/yono.png")));
	        logo.scaleToFit(100, 30);
	        logo.setAlignment(Element.ALIGN_RIGHT);
	        document.add(logo);

	        addEmptyLine(document, 1);

	        // Add status image
	        Image statusImage;
	        if ("Success".equalsIgnoreCase(request.getDebitStatus())) {
	            statusImage = Image.getInstance(Objects.requireNonNull(TestController.class.getResource("/image/success.png")));
	        } else {
	            statusImage = Image.getInstance(Objects.requireNonNull(TestController.class.getResource("/image/error.png")));
	        }
	        statusImage.scaleToFit(40, 40);
	        statusImage.setAlignment(Element.ALIGN_CENTER);
	        document.add(statusImage);

	        addEmptyLine(document, 1);

	        // Add success text
	        Font successFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
	        Paragraph successPara = new Paragraph(
	            request.getDebitStatus().equalsIgnoreCase("Success")
	                ? "Transaction has been successfully completed!"
	                : "Pending Authorization!",
	            successFont
	        );
	        successPara.setAlignment(Element.ALIGN_CENTER);
	        document.add(successPara);

	        addEmptyLine(document, 2);

	        // Add "e-Pay Order Details" heading
	        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	        Paragraph sectionTitle = new Paragraph("e-Pay Order Details", sectionFont);
	        sectionTitle.setAlignment(Element.ALIGN_CENTER);
	        document.add(sectionTitle);

	        addEmptyLine(document, 2);

	        // Row 1
	        PdfPTable rowOne = new PdfPTable(4);
	        rowOne.setWidthPercentage(100);
	        rowOne.setWidths(new float[]{3, 4, 3, 4});
	        addCell(rowOne, "Date", request.getDate());
	        addCell(rowOne, "e-PayOrder Number", request.geteChequeNo());
	        addCell(rowOne, "Transaction Type", "Pay Supplier");
	        addCell(rowOne, "Supplier Name", "BPCL LPG");
	        document.add(rowOne);

	        addEmptyLine(document, 1);

	        // Row 2
	        PdfPTable rowTwo = new PdfPTable(4);
	        rowTwo.setWidthPercentage(100);
	        rowTwo.setWidths(new float[]{3, 4, 3, 4});
	        addCell(rowTwo, "From A/c", request.getDebitAccountNo());
	        addCell(rowTwo, "Amount", request.getDdAmount());
	        addCell(rowTwo, "Counterfoil description", "Payment towards invoice/bill");
	        addCell(rowTwo, "Remarks", "Test");
	        document.add(rowTwo);

	        document.close();
	    } catch (Exception e) {
	        throw new CreatePDFException("Error occurred while creating pdf.");
	    }

	    return Optional.of(byteArrayOutputStream.toByteArray());
	}
	
	
	
	


	
	private void addEmptyLine(Document document, int number) throws DocumentException {
	    for (int i = 0; i < number; i++) {
	        document.add(new Paragraph(" "));
	    }
	}
	
	
//	private void addCell(PdfPTable table, String key, String value) {
//	    PdfPCell cellKey = new PdfPCell(new Phrase(key));
//	    PdfPCell cellValue = new PdfPCell(new Phrase(value));
//
//	    cellKey.setPadding(5);
//	    cellValue.setPadding(5);
//
//	    // Optional styling (e.g., no borders, alignment)
//	    cellKey.setBorder(Rectangle.NO_BORDER);
//	    cellValue.setBorder(Rectangle.NO_BORDER);
//
//	    table.addCell(cellKey);
//	    table.addCell(cellValue);
//	}
	
	

	
//	private void addCell(PdfPTable table, String key, String value) {
//	    // Fonts
//	    Font labelFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
//	    Font valueFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
//
//	    // Create paragraph with label and value stacked
//	    Paragraph cellContent = new Paragraph();
//	    cellContent.add(new Phrase(key + "\n", labelFont));   // Label (top)
//	    cellContent.add(new Phrase(value, valueFont));         // Value (below)
//	    cellContent.setSpacingAfter(5f);
//
//	    PdfPCell cell = new PdfPCell(cellContent);
//	    cell.setPadding(10f);
//	    cell.setBorder(Rectangle.NO_BORDER);
//	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//	    table.addCell(cell);
//	}
	
	
	private void addCell(PdfPTable table, String key, String value) {
	    BaseColor labelColor = new BaseColor(100, 100, 100); // dark gray
	    BaseColor valueColor = BaseColor.BLACK;

	    Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, labelColor);
	    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, valueColor);

	    Paragraph cellContent = new Paragraph();
	    cellContent.setLeading(14f);
	    cellContent.add(new Phrase(key + "\n", labelFont));
	    cellContent.add(new Phrase(value, valueFont));

	    PdfPCell cell = new PdfPCell(cellContent);
	    cell.setPadding(10f);
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    cell.setVerticalAlignment(Element.ALIGN_TOP);
	    table.addCell(cell);
	}




}
