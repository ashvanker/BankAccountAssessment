package Models;

import Models.Enums.WithdrawalResultEnum;

public class WithdrawalResponse {
    private WithdrawalResultEnum result;
    private String message;

    public WithdrawalResponse(WithdrawalResultEnum result, String message) {
        this.result = result;
        this.message = message;
    }

    public WithdrawalResultEnum getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
