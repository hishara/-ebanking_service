package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.TransactionResponseDto;
import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import au.com.anz.wholesale.banking.backend.enumerate.TransactionType;
import au.com.anz.wholesale.banking.backend.exception.ResourceNotFoundException;
import au.com.anz.wholesale.banking.backend.model.Account;
import au.com.anz.wholesale.banking.backend.model.Transaction;
import au.com.anz.wholesale.banking.backend.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionServiceUnderTest;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("validates if the transactions for an account are returned for a given user")
    void processEnquiry_whenUserIdIsPassed_ShouldReturnAccountList() {
        // given
        final PageRequest pagingRequest = PageRequest.of(0, 2);
        final Transaction transaction = Transaction.builder()
                .valueDate(LocalDate.of(2022, 12, 30))
                .debitAmount(250.0)
                .creditAmount(1300.0)
                .transactionType(TransactionType.Credit)
                .transactionNarrative("Note")
                .account(Account.builder()
                        .accountName("TestAccount1")
                        .accountNumber(10000001L)
                        .accountType(AccountType.Current)
                        .balanceDate(LocalDate.of(1990, 10, 03))
                        .currency(CurrencyType.AUD)
                        .openingAvailableBalance(1000.0)
                        .build())
                .build();
        final List<Transaction> transactionList = Arrays.asList(transaction);
        final Page<Transaction> acoountsPageingList = new PageImpl<>(transactionList, pagingRequest, transactionList.size());

        final TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .accountName(transaction.getAccount().getAccountName())
                .valueDate(transaction.getValueDate())
                .currency(transaction.getAccount().getCurrency())
                .debitAmount(transaction.getDebitAmount())
                .creditAmount(transaction.getCreditAmount())
                .transactionType(transaction.getTransactionType())
                .transactionNarrative(transaction.getTransactionNarrative())
                .build();
        final List<TransactionResponseDto> transactionResponseDtoList = Arrays.asList(transactionResponseDto);
        final Page<TransactionResponseDto> accountResponseDtoPageingList = new PageImpl<>(transactionResponseDtoList, pagingRequest, transactionResponseDtoList.size());


        // when
        when(transactionRepository.findByAccountUserUserIdAndAccountAccountNumber(30000001L, 1000001L, pagingRequest)).thenReturn(acoountsPageingList);

        // then
        assertThat(transactionServiceUnderTest.processEnquiry(30000001L, 1000001L, pagingRequest)).isEqualTo(accountResponseDtoPageingList);
    }

    @Test
    @DisplayName("validates if an exception is thrown when no accounts are found for a given user")
    public void processEnquiry_whenUserIdIsPassedForUserWithNoAccount_ShouldReturnDataNotFoundException() {
        // given
        final PageRequest pagingRequest = PageRequest.of(0, 2);
        final List<Transaction> transactionList = new ArrayList<Transaction>();;
        final Page<Transaction> acoountsPageingList = new PageImpl<>(transactionList, pagingRequest, transactionList.size());

        // when
        when(transactionRepository.findByAccountUserUserIdAndAccountAccountNumber(30000006L, 1000006L, pagingRequest)).thenReturn(acoountsPageingList);

        // then
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> transactionServiceUnderTest.processEnquiry(30000006L, 1000006L, pagingRequest));
        assertEquals("TH | Lookup | ERROR_2_001 | No transactions found for user: 30000006 for the account: 1000006", resourceNotFoundException.getErrorMessage());
    }
}