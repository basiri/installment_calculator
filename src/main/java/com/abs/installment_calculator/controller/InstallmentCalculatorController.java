package com.abs.installment_calculator.controller;

import com.abs.installment_calculator.model.Installment;
import com.abs.installment_calculator.model.Loan;
import com.abs.installment_calculator.service.InstallmentCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Is the main and only controller accepting JSON
 * Has  path /test GET for validating running server
 * Has  path /installment-calculator POST with json request body
 *
 *
 */
@RestController
public class InstallmentCalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(InstallmentCalculatorController.class);


    private InstallmentCalculatorService instCalService;

    public InstallmentCalculatorController(InstallmentCalculatorService instCalService) {
        this.instCalService=instCalService;
    }

    @GetMapping("/test")
    public @ResponseBody  String isRunning(){
        return "OK!!!";
    }

    @PostMapping(path = "/installment-calculator", consumes = "application/json", produces = "application/json")
    public List<Installment> installmentsCalculator(@RequestBody @Valid Loan loan ){
        return instCalService.getAllInstallment(loan);
    }

//    ExceptionHandlers
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException exception){
        logger.error(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),101,"Invalid input data."));

    }

}
