package cmpe282.pubsub.example;

import java.util.logging.Logger;
import com.google.api.core.ApiFuture;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.ChannelProvider;
import com.google.api.gax.grpc.FixedChannelProvider;
import com.google.api.gax.grpc.GrpcTransportProvider;
import com.google.api.gax.rpc.AlreadyExistsException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PublisherLocalExample {

    private static final int MESSAGE_COUNT = 5;
    private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();
    private static Logger log = Logger.getLogger(PublisherLocalExample.class.getName());
    private static final String hostport = System.getenv("PUBSUB_EMULATOR_HOST");

    public static ApiFuture<String> publishMsg(Publisher publisher, String message) {
	ByteString data = ByteString.copyFromUtf8(message);
	PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
	return publisher.publish(pubsubMessage);
    }

    public static void main(String... args) {
	String topicId = args[0];

	ManagedChannel channel = ManagedChannelBuilder.forTarget(hostport).usePlaintext(true).build();
	ChannelProvider channelProvider = FixedChannelProvider.create(channel);
	CredentialsProvider credentialsProvider = new NoCredentialsProvider();

	TopicName topicName = TopicName.create(PROJECT_ID, topicId);
	try {
	    Publisher publisher = Publisher.defaultBuilder(topicName).setChannelProvider(channelProvider)
		    .setCredentialsProvider(credentialsProvider).build();

	    TopicAdminClient topicClient =
		      TopicAdminClient.create(
		          TopicAdminSettings.newBuilder()
		              .setTransportProvider(
		                  GrpcTransportProvider.newBuilder()
		                      .setChannelProvider(channelProvider)
		                      .build())
		              .setCredentialsProvider(credentialsProvider)
		              .build());
	    
	    try{
		topicClient.createTopic(topicName);
	    } catch (AlreadyExistsException e) {
		log.info("topic exists: " + topicName.toString());
	    }
	    
	    for (int i = 0; i < MESSAGE_COUNT; i++) {
		String message = "message - " + i;
		log.info(message);
		ApiFuture<String> msgId = publishMsg(publisher, message);
		log.info(msgId.get());
	    }

	    if (publisher != null)
		publisher.shutdown();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
