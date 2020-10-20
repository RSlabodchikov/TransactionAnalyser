package com.targsoft.ta.parser;

import com.targsoft.ta.model.Transaction;
import com.targsoft.ta.model.headers.TransactionHeaders;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  private static final String DELIMITER = ", ";
  private static final String COMMA = ",";
  private static final String CUSTOM_DELIMITER = "§";

  public static List<Transaction> parseFile(String path) {
    List<Transaction> transactions = new ArrayList<>();
    try {
      Stream<String> lines = Files.lines(Paths.get(path));

      List<String> records = lines.map(line ->
          line.replaceAll(Pattern.quote(DELIMITER), CUSTOM_DELIMITER)
              .replaceAll(Pattern.quote(COMMA), CUSTOM_DELIMITER))
          .collect(Collectors.toList());
      BufferedReader buffer =
          new BufferedReader(new StringReader(String.join("\n", records)));
      Iterable<CSVRecord> parser = new CSVParser(
          buffer, CSVFormat.EXCEL.withDelimiter('§').withFirstRecordAsHeader());
      parser.forEach(record -> {
        try {
          transactions.add(parseRecord(record));
        } catch (ParseException | IllegalArgumentException e) {
          System.out.println(String.format("Parsing error: %s", e.getMessage()));
        }
      });
    } catch (FileNotFoundException e) {
      System.out.println(String.format("Cannot found file with this name: %s", path));
      return Collections.emptyList();
    } catch (IOException e) {
      System.out.println(String.format("IOException occurred while parsing file: %s", e.getMessage()));
      return Collections.emptyList();
    }
    return transactions;
  }

  private static Transaction parseRecord(CSVRecord record) throws ParseException {
    Transaction transaction = Transaction.builder()
        .id(record.get(TransactionHeaders.ID.getHeader()))
        .date(dateFormat.parse(record.get(TransactionHeaders.DATE.getHeader())))
        .amount(Double.valueOf(record.get(TransactionHeaders.AMOUNT.getHeader())))
        .merchant(record.get(TransactionHeaders.MERCHANT.getHeader()))
        .type(Transaction.Type.valueOf(record.get(TransactionHeaders.TYPE.getHeader())))
        .build();
    if (Transaction.Type.REVERSAL.equals(transaction.getType())) {
      transaction.setRelatedTransaction(record.get(TransactionHeaders.RELATED_TRANSACTION.getHeader()));
    }
    return transaction;
  }
}
