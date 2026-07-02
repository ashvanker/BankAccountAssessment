package Interfaces;

import java.math.BigDecimal;

import Models.WithdrawalResponse;

public interface IWithdrawalService {
    WithdrawalResponse withdraw(Long accountId, BigDecimal amount);
}
