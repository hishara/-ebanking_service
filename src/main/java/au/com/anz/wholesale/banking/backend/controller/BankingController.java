package au.com.anz.wholesale.banking.backend.controller;

import au.com.anz.wholesale.banking.backend.dto.AccountResponseDto;
import au.com.anz.wholesale.banking.backend.dto.TransactionResponseDto;
import au.com.anz.wholesale.banking.backend.exception.ErrorResponse;
import au.com.anz.wholesale.banking.backend.service.BankingService;
import au.com.anz.wholesale.banking.backend.validation.CustomValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Main controller which represents Account and Transaction APIs.
 */
@RestController
@RequiredArgsConstructor
@Validated
@Api(value = "Account Management", protocols = "http")
@RequestMapping("/account-management/api")
public class BankingController {

    private final BankingService bankingService;

    /**
     * Account details controller.
     * @param userId userId.
     * @param pageNo pageNo.
     * @param pageSize pageSize.
     * @return AccountResponseDto api respomse.
     */
    @ApiOperation(value = "Access list accounts for a given user", response = AccountResponseDto.class, code = 200)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Records not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)})
    @GetMapping("/{userId}/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseDto> enquireAccountList(@PathVariable("userId") @CustomValidator final Long userId,
                                                       @RequestParam(name = "pageNo", defaultValue = "0") final Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") final Integer pageSize) {
        final Page<AccountResponseDto> accountResponse = bankingService.enquireAccountList(userId, PageRequest.of(pageNo, pageSize));
        return accountResponse.getContent();
    }

    /**
     * Transactions controller.
     * @param userId userId.
     * @param accountId accountId.
     * @param pageNo pageNo.
     * @param pageSize pageSize.
     * @return TransactionResponseDto transaction reposne.
     */
    @ApiOperation(value = "Access transactions for a given account of user", response = TransactionResponseDto.class, code = 200)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Records not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class)})
    @GetMapping("/{userId}/accounts/{acccountId}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDto> enquireAccountTransaction(@PathVariable("userId") final Long userId,
                                                                  @PathVariable("acccountId") @CustomValidator final Long accountId,
                                                                  @RequestParam(name = "pageNo", defaultValue = "0") final Integer pageNo,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") final Integer pageSize) {
        final Page<TransactionResponseDto> transactionResponse = bankingService.enquireAccountTransaction(userId, accountId, PageRequest.of(pageNo, pageSize));
        return transactionResponse.getContent();
    }

}
