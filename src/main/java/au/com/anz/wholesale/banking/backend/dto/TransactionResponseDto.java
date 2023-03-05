package au.com.anz.wholesale.banking.backend.dto;

import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import au.com.anz.wholesale.banking.backend.enumerate.TransactionType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Transaction reponse DTO.
 */
@Data
@NoArgsConstructor
public class TransactionResponseDto extends BaseResponseDto {
    private LocalDate valueDate;
    private Double debitAmount;
    private Double creditAmount;
    private TransactionType transactionType;
    private String transactionNarrative;

    /**
     * All args constructor.
     * @param accountNumber accountNumber.
     * @param accountName accountName.
     * @param valueDate valueDate.
     * @param currency currency.
     * @param debitAmount debitAmiunt.
     * @param creditAmount creditAmount.
     * @param transactionType transactionType.
     * @param transactionNarrative transactionNarrative.
     */
    @Builder
    public TransactionResponseDto(Long accountNumber, String accountName, LocalDate valueDate, CurrencyType currency, Double debitAmount, Double creditAmount, TransactionType transactionType, String transactionNarrative) {
        super(accountNumber, accountName, currency);
        this.valueDate = valueDate;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.transactionType = transactionType;
        this.transactionNarrative = transactionNarrative;
    }
}
