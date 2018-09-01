package com.pradeep.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Getter
@Builder
public class TransactionLog {
   Long txnId;
   String name;
   Long amount;
   String state;


   public TransactionLog(Long txnId, String name, Long amount, String state) {
      this.txnId = txnId;
      this.name = name;
      this.amount = amount;
      this.state = state;
   }
}
