package com.abs.installment_calculator.utility;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class LoanUtilityTest {

    @Test
    public void getInterest() {
        assertTrue(BigDecimal.valueOf(416.67).equals(LoanUtility.getInterest(BigDecimal.valueOf(5000),100)));
    }

    @Test
    public void getPayment() {
        assertTrue(BigDecimal.valueOf(268.84).equals(LoanUtility.getPayment(99,12,BigDecimal.valueOf(2000))));
    }
}