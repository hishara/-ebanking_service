package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.AccountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Account service interface.
 */
public interface AccountService {
    Page<AccountResponseDto> processEnquiry(final Long userId, final Pageable paging);
}
