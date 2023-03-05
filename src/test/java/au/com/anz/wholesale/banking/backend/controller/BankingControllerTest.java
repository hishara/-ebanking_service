package au.com.anz.wholesale.banking.backend.controller;

import au.com.anz.wholesale.banking.backend.dto.AccountResponseDto;
import au.com.anz.wholesale.banking.backend.dto.TransactionResponseDto;
import au.com.anz.wholesale.banking.backend.exception.ResourceNotFoundException;
import au.com.anz.wholesale.banking.backend.enumerate.AccountType;
import au.com.anz.wholesale.banking.backend.enumerate.CurrencyType;
import au.com.anz.wholesale.banking.backend.enumerate.TransactionType;
import au.com.anz.wholesale.banking.backend.service.BankingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankingController.class)
class BankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankingService bankingService;


    @Test
    @DisplayName("validates if accounts api returns HTTPS 200")
    void enquireAccountList_whenInvoked_shouldReturn200Response() throws Exception {

        // given
        final AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .accountName("enquireAccountList_whenInvoked_shouldReturn200Response")
                .accountNumber(10000001L)
                .accountType(AccountType.Current)
                .balanceDate(LocalDate.of(1990, 10, 03))
                .currency(CurrencyType.AUD)
                .openingAvailableBalance(1000.0)
                .build();
        final List<AccountResponseDto> accountResponseDtoList = Arrays.asList(accountResponseDto);
        final Page<AccountResponseDto> accountResponseDtoPageingList = new PageImpl<>(accountResponseDtoList, PageRequest.of(0, 2), accountResponseDtoList.size());
        when(bankingService.enquireAccountList(any(), any())).thenReturn(accountResponseDtoPageingList);

        // when
        final ResultActions result = this.mockMvc.perform(get("/account-management/api/10000001/accounts"))
                .andDo(print());

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("enquireAccountList_whenInvoked_shouldReturn200Response")));
    }

    @Test
    @DisplayName("validates if transactions api returns HTTPS 200")
    void enquireAccountTransaction_whenInvoked_shouldReturn200Response() throws Exception {

        // given
        final TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                .accountName("enquireAccountTransaction_whenInvoked_shouldReturn200Response")
                .accountNumber(10000001L)
                .valueDate(LocalDate.of(1990, 10, 03))
                .currency(CurrencyType.AUD)
                .debitAmount(120.00)
                .creditAmount(1240.00)
                .transactionType(TransactionType.Credit)
                .transactionNarrative("Note")
                .build();
        final List<TransactionResponseDto> transactionResponseDtoList = Arrays.asList(transactionResponseDto);
        final Page<TransactionResponseDto> accountResponseDtoPageingList = new PageImpl<>(transactionResponseDtoList, PageRequest.of(0, 2), transactionResponseDtoList.size());
        when(bankingService.enquireAccountTransaction(any(), any(), any())).thenReturn(accountResponseDtoPageingList);

        // when
        final ResultActions result = this.mockMvc.perform(get("/account-management/api/30000001/accounts/10000001/transactions"))
                .andDo(print());

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("enquireAccountTransaction_whenInvoked_shouldReturn200Response")));
    }

    @Test
    @DisplayName("validates if accounts api returns HTTPS 404 in the even of empty results")
    void enquireAccountList_whenInvoked_shouldReturn404Response() throws Exception {

        // given
        when(bankingService.enquireAccountList(any(), any())).thenThrow(ResourceNotFoundException.class);

        // when
        final ResultActions result = this.mockMvc.perform(get("/account-management/api/10000001/accounts"))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("validates if transactions api returns HTTPS 404 in the even of empty results")
    void enquireAccountTransaction_whenInvoked_shouldReturn404Response() throws Exception {

        // given
        when(bankingService.enquireAccountTransaction(any(), any(), any())).thenThrow(ResourceNotFoundException.class);

        // when
        final ResultActions result = this.mockMvc.perform(get("/account-management/api/30000001/accounts/10000001/transactions"))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }
}