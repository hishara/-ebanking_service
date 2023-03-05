package au.com.anz.wholesale.banking.backend.dto;

import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Account response DTO.
 */
@Data
@NoArgsConstructor
public class AccountResponseDto extends BaseResponseDto {
    private AccountType accountType;
    private LocalDate balanceDate;
    private Double openingAvailableBalance;

    /**
     * All args constructor.
     * @param accountNumber accountNumber.
     * @param accountName accountName.
     * @param currency currency.
     * @param accountType accountType.
     * @param balanceDate balanceDate.
     * @param openingAvailableBalance openingAvailableBalance.
     */
    @Builder
    public AccountResponseDto(Long accountNumber, String accountName, CurrencyType currency, AccountType accountType, LocalDate balanceDate, Double openingAvailableBalance) {
        super(accountNumber, accountName, currency);
        this.accountType = accountType;
        this.balanceDate = balanceDate;
        this.openingAvailableBalance = openingAvailableBalance;
    }
}
