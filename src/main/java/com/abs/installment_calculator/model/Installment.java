package com.abs.installment_calculator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Installment {
    //TODO Double to BigDecimal is amount id too big
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal borrowerPaymentAmount;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal initialOutstandingPrincipal;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal interest;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal principal;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainingOutstandingPrincipal;

    public Installment(BigDecimal borrowerPaymentAmount, Date date, BigDecimal initialOutstandingPrincipal,
                       BigDecimal interest, BigDecimal principal, BigDecimal remainingOutstandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interest = interest;
        this.principal = principal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getBorrowerPaymentAmount() {
        return borrowerPaymentAmount.setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getPrincipal() {
        return principal.setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getInterest() {
        return interest.setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal.setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal.setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public String toString() {
        return "Installment{" +
                "borrowerPaymentAmount=" + borrowerPaymentAmount +
                ", date=" + date +
                ", initialOutstandingPrincipal=" + initialOutstandingPrincipal +
                ", interest=" + interest +
                ", principal=" + principal +
                ", remainingOutstandingPrincipal=" + remainingOutstandingPrincipal +
                '}';
    }
}
