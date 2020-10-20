package com.targsoft.ta.model.headers;

import lombok.Getter;

@Getter
public enum TransactionHeaders {
  ID("ID"),
  DATE("Date"),
  AMOUNT("Amount"),
  MERCHANT("Merchant"),
  TYPE("Type"),
  RELATED_TRANSACTION("Related Transaction");

  private String header;

  TransactionHeaders(String header) {
    this.header = header;
  }
}
