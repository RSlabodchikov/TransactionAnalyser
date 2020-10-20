package com.targsoft.ta;

import com.targsoft.ta.model.Report;
import com.targsoft.ta.model.Transaction;
import com.targsoft.ta.parser.CsvParser;
import com.targsoft.ta.service.AnalyserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionAnalyser {

  public static void main(String[] args) {
    List<Transaction> transactions = CsvParser.parseFile(args[0]);
    if (transactions == null || transactions.isEmpty()) {
      System.out.println("Transactions not loaded from file");
      return;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date dateFrom, dateTo;
    try {
      dateFrom = dateFormat.parse(args[2]);
      dateTo = dateFormat.parse(args[3]);
    } catch (ParseException e) {
      System.out.println("Incorrect date format provided");
      return;
    }

    Report report = AnalyserService.analyseTransactions(transactions, args[1], dateFrom, dateTo);
    System.out.println("Number of transaction: " + report.getTotalCount());
    System.out.println("Average transaction value: " + report.getAverageValue());

  }
}
