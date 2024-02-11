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
    public List<InvoiceResponseModel> getAllInvoices(@RequestParam(required = false) String invoiceid, @RequestParam(required = false) String userid, @RequestParam(required = false) String username,  @RequestParam(required = false) String status, @RequestParam(required = false) String paymenttype) {
        return invoiceService.getAllInvoices(invoiceid, userid, username, status, paymenttype);
    }


    @PostMapping()
    public InvoiceResponseModel addInvoice(@RequestBody InvoiceRequestModel invoiceRequestModel){
        return invoiceService.addInvoice(invoiceRequestModel);
    }

    @GetMapping("/users/{userId}")
    public List<InvoiceResponseModel> getAllInvoiceByUserId(@PathVariable String userId){
        return invoiceService.getAllInvoiceByUserId(userId);
    }

@GetMapping("/{invoiceId}")
public InvoiceResponseModel getInvoiceById(@PathVariable String invoiceId){
    return invoiceService.getInvoiceById(invoiceId);
}


    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> removeInvoice(@PathVariable String invoiceId){
        invoiceService.removeInvoice(invoiceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
