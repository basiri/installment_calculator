package com.abs.installment_calculator.utility;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class LoanUtility {
    private static final int DAYS_IN_MONTH = 30;
    private static final int DAYS_IN_YEAR = 360;
    private static final int PRECISION = 2;

    public static BigDecimal getInterest(BigDecimal pv, double yearlyInterestRate) {
        return pv.multiply(BigDecimal.valueOf(yearlyInterestRate/100 * DAYS_IN_MONTH / DAYS_IN_YEAR))
                .setScale(PRECISION, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal getPayment(double yearlyInterestRate,int monthlyDuration, BigDecimal pv) {
        return pv.multiply(BigDecimal.valueOf(yearlyInterestRate/1200))
                .divide(BigDecimal.valueOf(1-Math.pow(1+yearlyInterestRate/1200,-1*monthlyDuration)),
                        MathContext.DECIMAL128)
                .setScale(PRECISION, RoundingMode.HALF_DOWN);
    }

}
