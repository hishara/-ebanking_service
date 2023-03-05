package au.com.anz.wholesale.banking.backend.repository;

import au.com.anz.wholesale.banking.backend.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Account repository interface to work with Account entity.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByUserUserId(final Long userId, final Pageable paging);
}
