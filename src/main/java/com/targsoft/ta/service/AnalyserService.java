package com.targsoft.ta.service;

import com.targsoft.ta.model.Report;
import com.targsoft.ta.model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnalyserService {
  public static Report analyseTransactions(List<Transaction> transactions,
                                           String merchant, Date dateFrom, Date dateTo) {
    AtomicInteger totalCount = new AtomicInteger();

    Set<String> transactionsWithReversal = transactions.stream()
        .filter(transaction -> Transaction.Type.REVERSAL.equals(transaction.getType()))
        .map(Transaction::getRelatedTransaction)
        .collect(Collectors.toSet());

    double average = transactions.stream()
        .filter(transaction -> !Transaction.Type.REVERSAL.equals(transaction.getType()) &&
            !transactionsWithReversal.contains(transaction.getId()))
        .filter(transaction -> merchant.equals(transaction.getMerchant()) &&
            (transaction.getDate().after(dateFrom) && transaction.getDate().before(dateTo)))
        .mapToDouble(transaction -> {
          totalCount.getAndIncrement();
          return transaction.getAmount();
        })
        .average().orElse(0.0);
    return Report.builder()
        .totalCount(totalCount.get())
        .averageValue(average)
        .build();
  }
}
