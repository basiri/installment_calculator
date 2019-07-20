package com.abs.installment_calculator.service;

import com.abs.installment_calculator.model.Installment;
import com.abs.installment_calculator.model.Loan;

import java.util.List;


public interface InstallmentCalculatorService {
    List<Installment> getAllInstallment(Loan loan);
}
