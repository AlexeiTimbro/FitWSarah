package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @GetMapping()
    public List<InvoiceResponseModel> getAllInvoices(@RequestParam(required = false) String invoiceId, @RequestParam(required = false) String userId, @RequestParam(required = false) String username,  @RequestParam(required = false) String status, @RequestParam(required = false) String paymentType) {
        return invoiceService.getAllInvoices(invoiceId, userId, username, status, paymentType);
    }


    @PostMapping()
    public InvoiceResponseModel addInvoice(@RequestBody InvoiceRequestModel invoiceRequestModel){
        return invoiceService.addInvoice(invoiceRequestModel);
    }

    @GetMapping("/users/{userId}")
    public List<InvoiceResponseModel> getAllInvoiceByUserId(@PathVariable String userId){
        return invoiceService.getAllInvoiceByUserId(userId);
    }

}
