package com.pradeep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TransferRequest {

    private String fromAccount;
    private String toAccount;
    private Long amount;
	

}
