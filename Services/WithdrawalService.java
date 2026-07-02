package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import Interfaces.IMessagingService;
import Interfaces.IWithdrawalService;
import java.math.BigDecimal;

import Models.WithdrawalEvent;
import Models.WithdrawalResponse;
import Models.Constants.WithdrawalConstants;
import Models.Enums.WithdrawalResultEnum;

@Service
public class WithdrawalService implements IWithdrawalService {

    private final JdbcTemplate jdbcTemplate;
    private final IMessagingService messagingService;

    public WithdrawalService(JdbcTemplate jdbcTemplate, IMessagingService messagingService) {
        this.jdbcTemplate = jdbcTemplate;
        this.messagingService = messagingService;
    }
    

    public WithdrawalResponse withdraw(Long accountId, BigDecimal amount) {
        
        // Check if account exists
        String checkAccountExistsSql = "SELECT COUNT(*) FROM accounts WHERE id = ?";
        Integer accountExists = jdbcTemplate.queryForObject(checkAccountExistsSql, Integer.class, accountId);

        if (accountExists == null || accountExists == 0) {

            return new WithdrawalResponse(WithdrawalResultEnum.ACCOUNT_NOT_FOUND, WithdrawalConstants.ACCOUNT_NOT_FOUND);
        }

        // Update balance only if sufficient funds available
        String withdrawSql = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?";

        int rowsAffected = jdbcTemplate.update(withdrawSql, amount, accountId, amount);

        if (rowsAffected == 0) {
            return new WithdrawalResponse(WithdrawalResultEnum.INSUFFICIENT_FUNDS, WithdrawalConstants.INSUFFICIENT_FUNDS);
        }

        // After a successful withdrawal, publish a withdrawal event to SNS
        messagingService.publishWithdrawalEvent(amount, accountId, WithdrawalResultEnum.SUCCESSFUL);

        return new WithdrawalResponse(WithdrawalResultEnum.SUCCESSFUL, WithdrawalConstants.WITHDRAWAL_SUCCESS);
    }
}