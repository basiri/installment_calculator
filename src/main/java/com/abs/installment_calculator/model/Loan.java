package com.abs.installment_calculator.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class Loan {
    @NotNull
    @Min(1000)
    @Max(1000000000)
    private BigDecimal loanAmount;
    @NotNull
    @Min(0)
    @Max(99)
    private double nominalRate;
    @NotNull
    @Min(3)
    @Max(144)
    private int duration;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date startDate;

    public Loan(BigDecimal loanAmount, double nominalRate, int duration, Date startDate) {
        this.loanAmount = loanAmount;
        this.nominalRate = nominalRate;
        this.duration = duration;
        this.startDate = startDate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public double getNominalRate() {
        return nominalRate;
    }

    public int getDuration() {
        return duration;
    }


    public Date getStartDate() {
        return startDate;
    }
}
