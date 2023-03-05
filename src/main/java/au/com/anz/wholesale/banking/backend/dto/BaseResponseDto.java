package au.com.anz.wholesale.banking.backend.dto;

import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Base class to represent common attributes.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto {
    private Long accountNumber;
    private String accountName;
    private CurrencyType currency;
}
