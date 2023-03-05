package au.com.anz.wholesale.banking.backend.repository;

import au.com.anz.wholesale.banking.backend.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Transaction repository interface to work with Transaction entity.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByAccountUserUserIdAndAccountAccountNumber(final Long userId, final Long accountId, final Pageable paging);
}
