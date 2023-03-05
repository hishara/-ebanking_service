package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.AccountResponseDto;
import au.com.anz.wholesale.banking.backend.exception.ResourceNotFoundException;
import au.com.anz.wholesale.banking.backend.model.Account;
import au.com.anz.wholesale.banking.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Corresponding implementation for the account service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    /**
     * process method.
     * @param userId userId.
     * @param paging paging.
     * @return Page<AccountResponseDto> page response for dto.
     */
    public Page<AccountResponseDto> processEnquiry(final Long userId, final Pageable paging) {
        final Page<Account> accounts = accountRepository.findByUserUserId(userId, paging);

        if (accounts == null || accounts.isEmpty()) {
            log.error("AH | Lookup | ERROR_1_001 | No accounts found for user: {}", userId);
            throw new ResourceNotFoundException("ERROR_1_001", "AH | Lookup | ERROR_1_001 | No accounts found for user: " + userId);
        }

        log.debug("AH | Lookup | Accounts retrieved for user: {}");

        return mapToAccountResponse(accounts);
    }

    /**
     * mapping between entity and dto.
     * @param accountList accountList.
     * @return Page<AccountResponseDto> oage reponse for dto.
     */
    private Page<AccountResponseDto> mapToAccountResponse(final Page<Account> accountList) {
        final Page<AccountResponseDto> accountResponse = accountList.map(account -> AccountResponseDto.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balanceDate(account.getBalanceDate())
                .currency(account.getCurrency())
                .openingAvailableBalance(account.getOpeningAvailableBalance())
                .build());
        return accountResponse;
    }
}
