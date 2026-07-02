package Interfaces;

import java.math.BigDecimal;
import Models.Enums.WithdrawalResultEnum;

public interface IMessagingService {
    void publishWithdrawalEvent(BigDecimal amount, Long accountId, WithdrawalResultEnum status);
}
