package com.abs.installment_calculator.service;

import com.abs.installment_calculator.model.Installment;
import com.abs.installment_calculator.model.Loan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.abs.installment_calculator.utility.LoanUtility.*;

@Service
public class InstallmentCalculatorServiceImpl implements InstallmentCalculatorService{


    /**
     * @param loan
     * @return List of related installments
     * call static methods of LoanUtility for loan_PMT(installment_payment) and calculating interest
     */
    @Override
    public List<Installment> getAllInstallment(Loan loan) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getStartDate());
        calendar.add(Calendar.MONTH,1);//going to next month

        List<Installment> installments = new ArrayList<>();

        //preparing for first installment
        BigDecimal initOutPrincipal = loan.getLoanAmount();
        BigDecimal payment= getPayment(loan.getNominalRate(),loan.getDuration(),initOutPrincipal);

        boolean lastPayment=false;
        //Calculation of last payment is different from other installment
        while (!lastPayment){
            BigDecimal interest = getInterest(initOutPrincipal,loan.getNominalRate());
            BigDecimal principal;
            BigDecimal remOutPrincipal;
            //indicates last installment
            if(initOutPrincipal.compareTo(payment)>0){
                principal = payment.subtract(interest);
                remOutPrincipal = initOutPrincipal.subtract(principal);
            }else {
                //calculating last installments
                principal = initOutPrincipal;
                payment =interest.add(principal);
                remOutPrincipal=BigDecimal.ZERO;
                //exiting the while_loop
                lastPayment=true;
            }
            //Adding to the return list
            installments.add(new Installment(payment,calendar.getTime(),initOutPrincipal,interest,principal,remOutPrincipal));

            //preparing for next installment
            initOutPrincipal= remOutPrincipal;
            calendar.add(Calendar.MONTH,1);
        }

        return installments;

    }

}
