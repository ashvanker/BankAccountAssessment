package Models;
import java.math.BigDecimal;
import Models.Enums.WithdrawalResultEnum;

public class WithdrawalEvent {
    private BigDecimal amount;
    private Long accountId;
    private WithdrawalResultEnum status;

    public WithdrawalEvent(BigDecimal amount, Long accountId, WithdrawalResultEnum status) {
        this.amount = amount;
        this.accountId = accountId;
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public WithdrawalResultEnum getStatus() {
        return status;
    }

    // Convert to JSON String
    public String toJson() {
        return String.format("{\"amount\":\"%s\",\"accountId\":%d,\"status\":\"%s\"}", amount, accountId, status.toString());
    }
}