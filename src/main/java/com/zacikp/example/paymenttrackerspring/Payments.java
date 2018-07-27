/*
 * Copyright (c) 2018 Petr Žáčík. All rights reserved.
 */
package com.zacikp.example.paymenttrackerspring;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents Payment data service. Payment is stored as Map of Currencies and Values.
 * Currency name is normalized to its uppercase representation eg. "Eur"->"EUR". Value represents transaction amount and
 * it can be positive or negative value. This service also provides simple text output that can be called directly,
 * see {@link #print()}
 */
@Service
public class Payments {

    private ConcurrentHashMap<String, Double> usdRates = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> payments = new ConcurrentHashMap<>();

    public Payments() {
        usdRates.put("EUR", 1.171234);
    }

    Payments(ConcurrentHashMap<String, Double> usdRates, ConcurrentHashMap<String, Integer> payments) {
        this.usdRates = usdRates;
        this.payments = payments;
    }

    /**
     * Insert new Payment. Payments with same currencies are summarized and Payments with final value of zero will be removed.
     * Incorrect values will be ignored.
     *
     * @param key   Currency needs to be exactly three characters long.
     * @param value Value for Currency, can be positive or negative number.
     */
    public void add(String key, int value) {
        if (key == null || key.trim().length() != 3 || value == 0) {
            return; // No action
        }
        String interKey = key.trim().toUpperCase();
        payments.merge(interKey, value, (oldValue, newValue) -> {
            int i = oldValue + newValue;
            return i == 0 ? null : i;
        });
    }

    /**
     * Print basic output to stdout using {@link #getFormattedLine(String, Integer)} formatter.
     * Currencies with USD exchange rate will be displayed with amount converted by USD rate.
     * Example output:
     * <pre><code>
     * EUR 3 (USD 3.51)
     * USD 200
     * --- List END ---
     * </code></pre>
     */
    public void print() {
        payments.forEach(1, (key, value) -> {
            String response = getFormattedLine(key, value);
            System.out.println(response);
        });
        System.out.println("--- List END ---");
    }

    /**
     * Get all payments
     *
     * @return Unmodifiable map of payments
     */
    public Map<String, Integer> getPayments() {
        return Collections.unmodifiableMap(payments);
    }

    protected String getFormattedLine(String key, Integer value) {
        String response = String.format("%s %s", key, value);
        Double rate = usdRates.get(key);
        if (rate != null) {
            response += String.format(" (USD %.2f)", value * rate);
        }
        return response;
    }
}
