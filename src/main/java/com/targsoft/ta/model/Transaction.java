package com.targsoft.ta.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Transaction {

  private String id;
  private Date date;
  private double amount;
  private String merchant;
  private Type type;
  private String relatedTransaction;

  public enum Type {
    PAYMENT,
    REVERSAL
  }
}
