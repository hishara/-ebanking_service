package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.TransactionResponseDto;
import au.com.anz.wholesale.banking.backend.exception.ResourceNotFoundException;
import au.com.anz.wholesale.banking.backend.model.Transaction;
import au.com.anz.wholesale.banking.backend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service implementation for transaction service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * process enquiry method.
     * @param userId userId.
     * @param accountId accountId.
     * @param paging paging.
     * @return Page<TransactionResponseDto> response dto.
     */
    public Page<TransactionResponseDto> processEnquiry(final Long userId, final Long accountId, final Pageable paging) {

        final Page<Transaction> transactions = transactionRepository.findByAccountUserUserIdAndAccountAccountNumber(userId, accountId, paging);

        if (transactions == null || transactions.isEmpty()) {
            log.error("TH | Lookup | ERROR_2_001 | No transactions found for user: {} for the account: {}", userId, accountId);
            throw new ResourceNotFoundException("ERROR_2_001","TH | Lookup | ERROR_2_001 | No transactions found for user: " + userId + " for the account: " + accountId);
        }
        log.debug("TH | Lookup | Transactions retrieved for user: {} for the account: {}", userId, accountId);
        return mapToTransactionResponse(transactions);
    }

    /**
     * mapping between entity and dto.
     * @param transactionList transactionList.
     * @return Page<TransactionResponseDto> response dto.
     */
    private Page<TransactionResponseDto> mapToTransactionResponse(final Page<Transaction> transactionList) {
        final Page<TransactionResponseDto> transactionResponse = transactionList.map(transaction -> TransactionResponseDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .accountName(transaction.getAccount().getAccountName())
                .valueDate(transaction.getValueDate())
                .currency(transaction.getAccount().getCurrency())
                .debitAmount(transaction.getDebitAmount())
                .creditAmount(transaction.getCreditAmount())
                .transactionType(transaction.getTransactionType())
                .transactionNarrative(transaction.getTransactionNarrative())
                .build());
        return transactionResponse;
    }
}
