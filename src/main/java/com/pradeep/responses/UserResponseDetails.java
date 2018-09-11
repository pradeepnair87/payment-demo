package com.pradeep.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class UserResponseDetails {
	String name;
	Long balance;
	long id;

	/*
	 * public UserResponseDetails(String name, Long balance, String message,Status
	 * status, int code){ super(message,status,code); this.name = name;
	 * this.balance=balance; }
	 */
}
