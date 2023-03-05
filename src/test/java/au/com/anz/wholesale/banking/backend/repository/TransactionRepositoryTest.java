package au.com.anz.wholesale.banking.backend.repository;

import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import au.com.anz.wholesale.banking.backend.enumerate.TransactionType;
import au.com.anz.wholesale.banking.backend.model.Account;
import au.com.anz.wholesale.banking.backend.model.Transaction;
import au.com.anz.wholesale.banking.backend.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepositoryUnderTest;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("validates if it returns the transactions by user_id and account_id")
    void findByAccountUserUserIdAndAccountAccountNumber_whenPerformSearch_shouldReturnResults() {

        // given
        final User user = User.builder()
                .userName("TestUser1")
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dob(LocalDate.of(1986, 03, 24))
                .dateCreated(LocalDateTime.now()).build();

        final Account account = Account.builder()
                .accountName("TestAccount1")
                .accountType(AccountType.Current)
                .balanceDate(LocalDate.of(1990, 10, 03))
                .currency(CurrencyType.AUD)
                .openingAvailableBalance(1000.0)
                .dateCreated(LocalDateTime.now())
                .user(user)
                .build();

        final Transaction transaction = Transaction.builder()
                .valueDate(LocalDate.of(2022, 12, 30))
                .debitAmount(250.0)
                .creditAmount(1300.0)
                .transactionType(TransactionType.Credit)
                .transactionNarrative("Note")
                .account(account)
                .dateCreated(LocalDateTime.now())
                .build();

        Object u = entityManager.persistAndGetId(user);
        Object a = entityManager.persistAndGetId(account);
        entityManager.persistAndFlush(transaction);

        // when
        final Page<Transaction> transactionPagingList = transactionRepositoryUnderTest.findByAccountUserUserIdAndAccountAccountNumber((Long) u, (Long) a, PageRequest.of(0, 2));

        // then
        assertThat(transactionPagingList.getContent()).hasSize(1);
        assertEquals(transactionPagingList.getContent().get(0).getAccount().getAccountName(), "TestAccount1");
        }

    @Test
    @DisplayName("validates if it returns the multiple transactions by user_id and account_id")
    void findByAccountUserUserIdAndAccountAccountNumber_whenPerformSearchForUserWithTwoAccounts_shouldReturnTwoResults() {

        // given
        final User user = User.builder()
                .userName("TestUser1")
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dob(LocalDate.of(1986, 03, 24))
                .dateCreated(LocalDateTime.now()).build();

        final Account account = Account.builder()
                .accountName("TestAccount1")
                .accountType(AccountType.Current)
                .balanceDate(LocalDate.of(1990, 10, 03))
                .currency(CurrencyType.AUD)
                .openingAvailableBalance(1000.0)
                .dateCreated(LocalDateTime.now())
                .user(user)
                .build();

        final List<Transaction> transactions = Arrays.asList(Transaction.builder()
                .valueDate(LocalDate.of(2022, 12, 30))
                .debitAmount(250.0)
                .creditAmount(1300.0)
                .transactionType(TransactionType.Credit)
                .transactionNarrative("Note 1")
                .account(account)
                .dateCreated(LocalDateTime.now())
                .build(),
                Transaction.builder()
                        .valueDate(LocalDate.of(2023, 05, 15))
                        .debitAmount(450.0)
                        .creditAmount(300.0)
                        .transactionType(TransactionType.Debit)
                        .transactionNarrative("Note 2")
                        .account(account)
                        .dateCreated(LocalDateTime.now())
                        .build()
        );

        Object u = entityManager.persistAndGetId(user);
        Object a = entityManager.persistAndGetId(account);
        entityManager.persistAndFlush(transactions.get(0));
        entityManager.persistAndFlush(transactions.get(1));

        // when
        final Page<Transaction> transactionPagingList = transactionRepositoryUnderTest.findByAccountUserUserIdAndAccountAccountNumber((Long) u, (Long) a, PageRequest.of(0, 2));

        // then
        assertThat(transactionPagingList.getContent()).hasSize(2);
    }

    @Test
    @DisplayName("validates if if returns empty results set when a given user has no transactions associated to him")
    void findByUserUserId_whenPerformSearchForUserWithNoAccounts_shouldReturnEMptyResultsSet() {

        // given
        final User user = User.builder()
                .userName("TestUser1")
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dob(LocalDate.of(1986, 03, 24))
                .dateCreated(LocalDateTime.now()).build();
        entityManager.persistAndFlush(user);

        // when
        final Page<Transaction> transactionPagingList = transactionRepositoryUnderTest.findByAccountUserUserIdAndAccountAccountNumber(3000000L, 1000000L, PageRequest.of(0, 2));

        // then
        assertThat(transactionPagingList.getContent()).isEmpty();
    }
}
