package Services;
import org.springframework.stereotype.Service;
import Interfaces.IMessagingService;
import Models.WithdrawalEvent;
import Models.Constants.WithdrawalConstants;
import Models.Enums.WithdrawalResultEnum;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import java.math.BigDecimal;

@Service
public class MessagingService implements IMessagingService{

    private final SnsClient snsClient;

    public MessagingService() {
        this.snsClient = SnsClient.builder()
                        .region(Region.AF_SOUTH_1)
                        .build();
    }

    public void publishWithdrawalEvent(BigDecimal amount, Long accountId, WithdrawalResultEnum status) {

        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, status);

        String snsTopicArn = WithdrawalConstants.SNS_TOPIC_ARN;

        PublishRequest publishRequest =
                PublishRequest.builder()
                        .message(event.toJson())
                        .topicArn(snsTopicArn)
                        .build();

        snsClient.publish(publishRequest);
    }
}