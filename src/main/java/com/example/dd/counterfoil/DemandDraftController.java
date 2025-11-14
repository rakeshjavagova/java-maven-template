package com.example.dd.counterfoil;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dd")
public class DemandDraftController {

    private final PdfGeneratorService pdfGeneratorService;

    public DemandDraftController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    //ResponseEntity<byte[]>
    //ResponseEntity<Map<String, String>>
    
    
    @PostMapping(value = "/counterfoil", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateCounterfoil(@RequestBody DemandDraftRequest request)
            throws DocumentException, JsonProcessingException {

        byte[] pdfBytes = pdfGeneratorService.createCounterfoilPdf(request);
        
        
     // Convert to Base64 string
        String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
        
        Map<String,String> str=new HashMap<>();
        str.put("File", base64Pdf);
        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
       // headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=counterfoil.pdf");
       // headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=counterfoil.pdf"); // attachment for download

        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                //.headers(headers)
                //.body(pdfBytes);
        		.body(str);
    }
}
