package com.abs.installment_calculator.controller;

import com.abs.installment_calculator.model.Installment;
import com.abs.installment_calculator.model.Loan;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InstallmentCalculatorControllerTest {


    public static final int MIN_LOAN_AMOUNT = 1000;
    public static final int MAX_LOAN_AMOUNT = 1000000000;
    public static final int MIN_DURATION = 3;
    public static final int MAX_DURATION = 144;
    public static final double MIN_RATE = 0;
    public static final double MAX_RATE = 99;



    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;


    @Test
    public void isRunning() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+randomServerPort+"/test";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
        //Verify bad request and missing header
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("OK!!!"));

    }

    @Test
    public void installmentsCalculator() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+randomServerPort+"/installment-calculator";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        //Initializing a loan
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date=null;
        try {
            date = simpleDateFormat.parse("2018-01-01T00:00:01Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Loan loan = new Loan(BigDecimal.valueOf(5000),5,24,date);
        HttpEntity<Loan> request = new HttpEntity<>(loan,headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(200, result.getStatusCodeValue());

        Gson gson = new Gson();
        Installment[] installments =gson.fromJson(result.getBody(),Installment[].class);

        assertEquals(0,BigDecimal.valueOf(219.36).compareTo(installments[0].getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(5000).compareTo(installments[0].getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(20.83).compareTo(installments[0].getInterest()));
        assertEquals(0,BigDecimal.valueOf(198.53).compareTo(installments[0].getPrincipal()));
        assertEquals(0,BigDecimal.valueOf(4801.47).compareTo(installments[0].getRemainingOutstandingPrincipal()));
        assertEquals("2018-02-01T00:00:01Z", simpleDateFormat.format(installments[0].getDate()));


        assertEquals(0,BigDecimal.valueOf(219.36).compareTo(installments[1].getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(4801.47).compareTo(installments[1].getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(20.01).compareTo(installments[1].getInterest()));
        assertEquals(0,BigDecimal.valueOf(199.35).compareTo(installments[1].getPrincipal()));
        assertEquals(0,BigDecimal.valueOf(4602.12).compareTo(installments[1].getRemainingOutstandingPrincipal()));
        assertEquals("2018-03-01T00:00:01Z", simpleDateFormat.format(installments[1].getDate()));

        assertEquals(0,BigDecimal.valueOf(219.28).compareTo(installments[23].getBorrowerPaymentAmount()));
        assertEquals(0,BigDecimal.valueOf(218.37).compareTo(installments[23].getInitialOutstandingPrincipal()));
        assertEquals(0,BigDecimal.valueOf(0.91).compareTo(installments[23].getInterest()));
        assertEquals(0,BigDecimal.valueOf(218.37).compareTo(installments[23].getPrincipal()));
        assertEquals(0,BigDecimal.ZERO.compareTo(installments[23].getRemainingOutstandingPrincipal()));
        assertEquals("2020-01-01T00:00:01Z", simpleDateFormat.format(installments[23].getDate()));







    }

    @Test
    public void handleValidationException() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+randomServerPort+"/installment-calculator";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        //Initializing a loan
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date=null;
        try {
            date = simpleDateFormat.parse("2018-01-01T00:00:01Z");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // less than expected loan amount
        Loan loan = new Loan(BigDecimal.valueOf(MIN_LOAN_AMOUNT-1),5,24,date);
        HttpEntity<Loan> request = new HttpEntity<>(loan,headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(400, result.getStatusCodeValue());
        Gson gson = new Gson();
        ApiError apiError = gson.fromJson(result.getBody(),ApiError.class);
        Assert.assertEquals(101, apiError.getInternalErrorCode());

        // more than expected loan amount
        loan = new Loan(BigDecimal.valueOf(MAX_LOAN_AMOUNT+1),5,24,date);
        request = new HttpEntity<>(loan,headers);
        result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(400, result.getStatusCodeValue());
        apiError = gson.fromJson(result.getBody(),ApiError.class);
        Assert.assertEquals(101, apiError.getInternalErrorCode());

        // less than expected loan interest-rate
        loan = new Loan(BigDecimal.valueOf(5000),MIN_RATE-1,24,date);
        request = new HttpEntity<>(loan,headers);
        result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(400, result.getStatusCodeValue());
        apiError = gson.fromJson(result.getBody(),ApiError.class);
        Assert.assertEquals(101, apiError.getInternalErrorCode());

        // More than expected loan interest-rate
        loan = new Loan(BigDecimal.valueOf(5000),MAX_RATE +1,24,date);
        request = new HttpEntity<>(loan,headers);
        result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(400, result.getStatusCodeValue());
        apiError = gson.fromJson(result.getBody(),ApiError.class);
        Assert.assertEquals(101, apiError.getInternalErrorCode());

        // Less than expected loan duration
        loan = new Loan(BigDecimal.valueOf(5000),5, MIN_DURATION-1,date);
        request = new HttpEntity<>(loan,headers);
        result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(400, result.getStatusCodeValue());
        apiError = gson.fromJson(result.getBody(),ApiError.class);
        Assert.assertEquals(101, apiError.getInternalErrorCode());

        // More than expected loan duration
        loan = new Loan(BigDecimal.valueOf(5000),5, MAX_DURATION+1,date);
        request = new HttpEntity<>(loan,headers);
        result = this.restTemplate.postForEntity(uri,request,String.class);
        Assert.assertEquals(400, result.getStatusCodeValue());
        apiError = gson.fromJson(result.getBody(),ApiError.class);
        Assert.assertEquals(101, apiError.getInternalErrorCode());


    }
}