package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.TransactionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Transaction interface.
 */
public interface TransactionService {
    Page<TransactionResponseDto> processEnquiry(final Long userId, final Long accountId, final Pageable paging);
}
