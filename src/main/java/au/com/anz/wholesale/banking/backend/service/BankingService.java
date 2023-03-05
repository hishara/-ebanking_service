package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.AccountResponseDto;
import au.com.anz.wholesale.banking.backend.dto.TransactionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Banking service class.
 */
@Service
@RequiredArgsConstructor
public class BankingService {

    private final AccountService accountService;
    private final TransactionService transactionService;

    /**
     * enquire accounts list.
     * @param userId userId.
     * @param paging paging.
     * @return Page<AccountResponseDto> response dto.
     */
    public Page<AccountResponseDto>  enquireAccountList(final Long userId, final Pageable paging) {
        return accountService.processEnquiry(userId, paging);
    }

    /**
     * enquire transactions method.
     * @param userId userId.
     * @param accountId accountId.
     * @param paging paging.
     * @return Page<TransactionResponseDto> response dto.
     */
    public Page<TransactionResponseDto>  enquireAccountTransaction(final Long userId, final Long accountId, final Pageable paging) {
        return transactionService.processEnquiry(userId, accountId, paging);
    }

}
