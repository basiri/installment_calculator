package com.abs.installment_calculator.service;

import com.abs.installment_calculator.model.Installment;
import com.abs.installment_calculator.model.Loan;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class InstallmentCalculatorServiceImplTest {
    //TODO Feasibility check
    private static final Logger logger = LoggerFactory.getLogger(InstallmentCalculatorServiceImplTest.class);

    private InstallmentCalculatorService insCalService;
    @Before
    public void setUp() throws Exception {
        insCalService= new InstallmentCalculatorServiceImpl();
    }

    @Test
    public void getAllInstallment() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date=null;
        try {
            date = simpleDateFormat.parse("2018-01-01T00:00:01Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Loan loan = new Loan(BigDecimal.valueOf(5000),5,24,date);
        List<Installment> installments = insCalService.getAllInstallment(loan);
        installments.forEach(i-> logger.info(i.toString()));

        assertEquals(loan.getDuration(),installments.size());

        Installment installment =installments.get(0);
        assertEquals(0,BigDecimal.valueOf(219.36).compareTo(installment.getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(5000).compareTo(installment.getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(20.83).compareTo(installment.getInterest()));
        assertEquals(0,BigDecimal.valueOf(198.53).compareTo(installment.getPrincipal()));
        assertEquals(0,BigDecimal.valueOf(4801.47).compareTo(installment.getRemainingOutstandingPrincipal()));
        assertEquals("2018-02-01T00:00:01Z", simpleDateFormat.format(installment.getDate()));

        installment =installments.get(1);
        assertEquals(0,BigDecimal.valueOf(219.36).compareTo(installment.getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(4801.47).compareTo(installment.getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(20.01).compareTo(installment.getInterest()));
        assertEquals(0,BigDecimal.valueOf(199.35).compareTo(installment.getPrincipal()));
        assertEquals(0,BigDecimal.valueOf(4602.12).compareTo(installment.getRemainingOutstandingPrincipal()));
        assertEquals("2018-03-01T00:00:01Z", simpleDateFormat.format(installment.getDate()));

        installment =installments.get(loan.getDuration()-1);
        assertEquals(0,BigDecimal.valueOf(219.28).compareTo(installment.getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(218.37).compareTo(installment.getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(0.91).compareTo(installment.getInterest()));
        assertEquals(0,BigDecimal.valueOf(218.37).compareTo(installment.getPrincipal()));
        assertEquals(0,BigDecimal.ZERO.compareTo(installment.getRemainingOutstandingPrincipal()));
        assertEquals("2020-01-01T00:00:01Z", simpleDateFormat.format(installment.getDate()));

        //different loan with interest rate with precision
        try {
            date = simpleDateFormat.parse("2018-09-28T00:00:01Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        loan = new Loan(BigDecimal.valueOf(2000),2.5,12,date);
        installments = insCalService.getAllInstallment(loan);
        installments.forEach(i-> logger.info(i.toString()));

        assertEquals(loan.getDuration(),installments.size());

        installment =installments.get(0);
        assertEquals(0,BigDecimal.valueOf(168.93).compareTo(installment.getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(2000).compareTo(installment.getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(4.17).compareTo(installment.getInterest()));
        assertEquals(0,BigDecimal.valueOf(164.76).compareTo(installment.getPrincipal()));
        assertEquals("2018-10-28T00:00:01Z", simpleDateFormat.format(installment.getDate()));

    }
}