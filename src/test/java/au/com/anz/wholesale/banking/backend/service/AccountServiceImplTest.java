package au.com.anz.wholesale.banking.backend.service;

import au.com.anz.wholesale.banking.backend.dto.AccountResponseDto;
import au.com.anz.wholesale.banking.backend.exception.ResourceNotFoundException;
import au.com.anz.wholesale.banking.backend.model.Account;
import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import au.com.anz.wholesale.banking.backend.repository.AccountRepository;
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
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceUnderTest;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("validates if the accounts are returned for a given user")
    public void processEnquiry_whenUserIdIsPassed_ShouldReturnAccountList() {

        // given
        final PageRequest pagingRequest = PageRequest.of(0, 2);
        final Account account = Account.builder()
                .accountName("TestAccount1")
                .accountNumber(10000001L)
                .accountType(AccountType.Current)
                .balanceDate(LocalDate.of(1990, 10, 03))
                .currency(CurrencyType.AUD)
                .openingAvailableBalance(1000.0)
                .build();
        final List<Account> accountList = Arrays.asList(account);
        final Page<Account> acoountsPageingList = new PageImpl<>(accountList, pagingRequest, accountList.size());

        final AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balanceDate(account.getBalanceDate())
                .currency(account.getCurrency())
                .openingAvailableBalance(account.getOpeningAvailableBalance())
                .build();
        final List<AccountResponseDto> accountResponseDtoList = Arrays.asList(accountResponseDto);
        final Page<AccountResponseDto> accountResponseDtoPageingList = new PageImpl<>(accountResponseDtoList, pagingRequest, accountResponseDtoList.size());


        // when
        when(accountRepository.findByUserUserId(30000001L, pagingRequest)).thenReturn(acoountsPageingList);

        // then
        assertThat(accountServiceUnderTest.processEnquiry(30000001L, pagingRequest)).isEqualTo(accountResponseDtoPageingList);
    }

    @Test
    @DisplayName("validates if an exception is thrown when no accounts are found for a given user")
    public void processEnquiry_whenUserIdIsPassedForUserWithNoAccount_ShouldReturnDataNotFoundException() {

        // given
        final PageRequest pagingRequest = PageRequest.of(0, 2);
        final List<Account> accountList = new ArrayList<Account>();
        final Page<Account> acoountsPageingList = new PageImpl<>(accountList, pagingRequest, accountList.size());

        // when
        when(accountRepository.findByUserUserId(30000006L, pagingRequest)).thenReturn(acoountsPageingList);

        // then
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> accountServiceUnderTest.processEnquiry(30000006L, pagingRequest));
        assertEquals("AH | Lookup | ERROR_1_001 | No accounts found for user: 30000006", resourceNotFoundException.getErrorMessage());
    }
}