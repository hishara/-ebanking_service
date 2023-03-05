package au.com.anz.wholesale.banking.backend.repository;

import au.com.anz.wholesale.banking.backend.model.Account;
import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
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
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepositoryUnderTest;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("validates if it returns the account by user_id")
    void findByUserUserId_whenPerformSearch_shouldReturnResults() {

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
        Object u = entityManager.persistAndGetId(user);
        entityManager.persistAndFlush(account);

        // when
        final Page<Account> accountsPagingList = accountRepositoryUnderTest.findByUserUserId((Long) u, PageRequest.of(0, 2));

        // then
        assertThat(accountsPagingList.getContent()).hasSize(1);
        assertEquals(accountsPagingList.getContent().get(0).getAccountName(), "TestAccount1");
    }

    @Test
    @DisplayName("validates if it returns the multiple accounts for the given user")
    void findByUserUserId_whenPerformSearchForUserWithTwoAccounts_shouldReturnTwoResults() {

        // given
        final User user = User.builder()
                .userName("TestUser1")
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .dob(LocalDate.of(1986, 03, 24))
                .dateCreated(LocalDateTime.now()).build();

        final List<Account> accounts = Arrays.asList(Account.builder()
                .accountName("TestAccount1")
                .accountType(AccountType.Current)
                .balanceDate(LocalDate.of(1990, 10, 03))
                .currency(CurrencyType.AUD)
                .openingAvailableBalance(1000.0)
                .dateCreated(LocalDateTime.now())
                .user(user)
                .build(),
                Account.builder()
                        .accountName("TestAccount2")
                        .accountType(AccountType.Savings)
                        .balanceDate(LocalDate.of(2004, 03, 24))
                        .currency(CurrencyType.AUD)
                        .openingAvailableBalance(2000.0)
                        .dateCreated(LocalDateTime.now())
                        .user(user)
                        .build()
        );
        Object u = entityManager.persistAndGetId(user);
        entityManager.persistAndFlush(accounts.get(0));
        entityManager.persistAndFlush(accounts.get(1));


        // when
        final Page<Account> accountsPagingList = accountRepositoryUnderTest.findByUserUserId((Long) u, PageRequest.of(0, 2));

        // then
        assertThat(accountsPagingList.getContent()).hasSize(2);
    }

    @Test
    @DisplayName("validates if if returns empty results set when a given user has no accounts associated to him")
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
        final Page<Account> accountsPagingList = accountRepositoryUnderTest.findByUserUserId(3000000L, PageRequest.of(0, 2));

        // then
        assertThat(accountsPagingList.getContent()).isEmpty();
    }
}