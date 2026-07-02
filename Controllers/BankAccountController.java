package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import Interfaces.IWithdrawalService;
import Models.WithdrawalResponse;
import Models.Constants.WithdrawalConstants;
import Services.WithdrawalService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import java.math.BigDecimal;

@RestController
@RequestMapping("/bank")
public class BankAccountController {

    private final IWithdrawalService withdrawalService;

    public BankAccountController(IWithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> Withdraw(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        
        WithdrawalResponse result = withdrawalService.withdraw(accountId, amount);
        String message = result.getMessage();

        switch (result.getResult()) {

            case SUCCESSFUL:
                return ResponseEntity.ok(message);

            case INSUFFICIENT_FUNDS:
                return ResponseEntity.badRequest().body(message);

            case FAILED:
                return ResponseEntity.internalServerError().body(message);

            case ACCOUNT_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

            default:
                return ResponseEntity.internalServerError().body(WithdrawalConstants.UNEXPECTED_RESULT);
        }
    }
}