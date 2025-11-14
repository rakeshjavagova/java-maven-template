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
@RequestMapping("/sbi")
public class SBIPrintDownloadController {
	
	
	private static final Font boldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.GRAY);
	private static final Font normalFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY);
	private static final BaseColor headerBaseColor = new BaseColor(94, 46, 120);
	private static final Font amountColor = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, headerBaseColor);
	private static final Font headBoldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GRAY);
	private static final Font headNormalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GRAY);
	
	
	
	
	
	
	@GetMapping(value = "/generatePdf", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> geteejjjWorld() {

	    PDFCreationData request = new PDFCreationData();
	    request.setDebitStatus("failure");
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

	    

	    Document document = new Document();
	    try {
	        PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();
	        Image image = Image.getInstance(Objects.requireNonNull(SBIPrintDownloadController.class.getResource("/image/yono.png")));
	        image.scaleToFit(120, 30);
	        document.add(image);
	        Paragraph header = new Paragraph("Demand Draft Details", headBoldFont);
	        header.setAlignment(ALIGN_CENTER);
	        header.setSpacingAfter(10f);
	        document.add(header);

	        if (request.getDebitStatus().equalsIgnoreCase("Success")) {
	            Image successImage = Image.getInstance(Objects.requireNonNull(SBIPrintDownloadController.class.getResource("/image/success.png")));
	            successImage.scaleToFit(32, 32);
	            successImage.setAlignment(ALIGN_CENTER);
	            document.add(successImage);
	        } else {
	            Image pendingImage = Image.getInstance(Objects.requireNonNull(SBIPrintDownloadController.class.getResource("/image/error.png")));
	            pendingImage.scaleToFit(32, 32);
	            pendingImage.setAlignment(ALIGN_CENTER);
	            document.add(pendingImage);
	        }

	        Paragraph paragraph = new Paragraph();
	        addEmptyLine(paragraph, 1);

	        if (request.getDebitStatus().equalsIgnoreCase("Success")) {
	            Paragraph successPara = new Paragraph("Transaction has been successfully completed.", headBoldFont);
	            successPara.setAlignment(ALIGN_CENTER);
	            successPara.setSpacingAfter(20f);//
	            document.add(successPara);
	        } else {
	            Paragraph pendingPara = new Paragraph("Pending Authorization", headBoldFont);
	            pendingPara.setAlignment(ALIGN_CENTER);
	            pendingPara.setSpacingAfter(20f);
	            document.add(pendingPara);
	        }
	        
//	        paragraph.setAlignment(ALIGN_CENTER);
//	        addEmptyLine(paragraph, 1);
//	        paragraph.add(new Phrase("e-Pay Order Details", headBoldFont));
//	        addEmptyLine(paragraph, 2);
//	        addEmptyLine(paragraph, 3);
//	        document.add(paragraph);
	        
	       
	        Paragraph orderDetailsHeader = new Paragraph("e-Pay Order Details", headBoldFont);
	        orderDetailsHeader.setAlignment(ALIGN_CENTER);
	        orderDetailsHeader.setSpacingBefore(10f);
	        orderDetailsHeader.setSpacingAfter(40f);
	        document.add(orderDetailsHeader);
	        
	        
	        
	        

	        PdfPTable statusTable = new PdfPTable(1);
	        statusTable.setWidthPercentage(100);

	        PdfPTable rowOne = new PdfPTable(4);
	        rowOne.setWidthPercentage(100);
	        addCell(rowOne, "Date", Objects.equals(request.getDate(), "") ? "-" : request.getDate());
	        addCell(rowOne, "e-PayOrder Number", Objects.equals(request.geteChequeNo(), "") ? "-" : request.geteChequeNo());
	        addCell(rowOne, "Bank Journal Number", request.getJournalNo());
	        addCell(rowOne, "In favor of", Objects.equals(request.getInFavorOf(), "") ? "-" : request.getInFavorOf());
	        document.add(rowOne);

	        Paragraph paragraphTwo = new Paragraph();
	        addEmptyLine(paragraphTwo, 1);
	        document.add(paragraphTwo);

	        PdfPTable rowTwo = new PdfPTable(4);
	        rowTwo.setWidthPercentage(100);
	        addCell(rowTwo, "From A/c", Objects.equals(request.getDebitAccountNo(), "") ? "-" : request.getDebitAccountNo());
	        addCell(rowTwo, "Branch", Objects.equals(request.getDrawnBranchName(), "") ? "-" : request.getDrawnBranchName());
	        addCell(rowTwo, "DD Amount", Objects.equals(request.getDdAmount(), "") ? "-" : String.valueOf(Double.valueOf(request.getDdAmount())));
	        addCell(rowTwo, "Commission", String.valueOf(Double.valueOf(request.getDdCommission())));
	        document.add(rowTwo);

	        Paragraph paragraphThree = new Paragraph();
	        addEmptyLine(paragraphThree, 1);
	        document.add(paragraphThree);

	        PdfPTable rowThree = new PdfPTable(4);
	        rowThree.setWidthPercentage(100);
	        addCell(rowThree, "Total Amount", String.valueOf(Double.parseDouble(request.getDdCommission()) + Double.parseDouble(request.getDdAmount())));
	        addCell(rowThree, "Delivery Mode", Objects.equals(request.getDeliveryMode(), "") ? "-" : request.getDeliveryMode());
	        addCell(rowThree, "", Objects.equals("", "") ? "-" : "");
	        addCell(rowThree, "", Objects.equals("", "") ? "-" : "");
	        document.add(rowThree);

	        document.close();
	    } catch (Exception e) {
	        throw new CreatePDFException("Error occurred while creating pdf.", e);
	    }

	    return Optional.of(byteArrayOutputStream.toByteArray());
	}
	
	

	
//	private void addCell(PdfPTable table, String label, String value) {
//	    PdfPCell cell = new PdfPCell();
//	    //cell.setHorizontalAlignment(Element.ALIGN_LEFT); // aligns cell content to left

//	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	    cell.setBorder(Rectangle.NO_BORDER);
//	    cell.setPaddingTop(2f);
//	    cell.setPaddingBottom(2f);
//
//	    Paragraph p = new Paragraph();
//	    p.setAlignment(Element.ALIGN_LEFT); // aligns text to left inside paragraph
//	    p.setSpacingBefore(0f);
//	    p.setSpacingAfter(0f);
//	    p.setLeading(10f);
//
//	    p.add(new Phrase(label + "\n", boldFont));
//	    p.add(new Phrase(value, normalFont));
//
//	    cell.addElement(p);
//	    table.addCell(cell);
//	}



	
	private void addCell(PdfPTable table, String label, String value) {
	    PdfPCell cell = new PdfPCell();
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER); // centers text in cell horizontally
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);   // optional: centers vertically
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setPaddingTop(5f); // optional: better spacing
	    cell.setPaddingBottom(5f);

	    Paragraph p = new Paragraph();
	    
	    p.setAlignment(Element.ALIGN_CENTER); // centers text in paragraph
	    p.add(new Phrase(label + "\n", boldFont));
	    p.add(new Phrase(value, normalFont));
	    cell.addElement(p);

	    table.addCell(cell);
	}
	
	
	private void addEmptyLine(Paragraph preface, int number) {
	    for (int i = 0; i < number; i++) {
	        preface.add(new Paragraph(" "));
	    }
	}


	@GetMapping("/test")
	public String getData(){
		return "Heloow world";
	}


	//This is from Release

}
