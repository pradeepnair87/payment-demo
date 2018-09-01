package com.pradeep.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationResult {

    String reason;
    boolean isvalid;

}
