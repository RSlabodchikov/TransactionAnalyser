package com.targsoft.ta.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Report {
  int totalCount = 0;
  double averageValue = 0.0d;
}
