package client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Steps:
 * <p>
 * 1. Create a Channel and client(Sync or Async)
 * 2. Create a ProtoBuff Greeting Message and build
 * 3. Create a Request with Message
 * 4. Create a Response and get the response from RPC call (endpoint)
 * 5. Print or process the response
 */
public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hello, gRPC Client");

        // in Server: Created a Server builder
        // in Client: ManagedChannel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // Not preferred in prod
                .build();

        System.out.println("Creating Sync Stub");
        // Client: Creating Sync Client using Blocking Stub
        // TestyServiceGrpc.TestyServiceBlockingStub syncClient = TestyServiceGrpc.newBlockingStub(channel);

        // Async Client:
        // TestyServiceGrpc.TestyServiceFutureStub asyncClient = TestyServiceGrpc.newFutureStub(channel);

        /**
         * Steps:
         *
         * 1. Create a Channel and client(Sync or Async)
         * 2. Create a ProtoBuff Greeting Message and build
         * 3. Create a Request with Message
         * 4. Create a Response and get the response from RPC call (endpoint)
         * 5. Print or process the response
         */

        // GreetService sync Client
        GreetServiceGrpc.GreetServiceBlockingStub syncGreetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Create ProtoBuff Greeting Message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("A")
                .setLastName("B")
                .build();

        // Create a Greet Request
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        // Call the RPC and get back a Response (protoBuffs)
        // gRPC: Make it easy just call func with Request
        GreetResponse response = syncGreetClient.greet(greetRequest);

        // Print Response
        System.out.println(response.getResult());

        // Shutdown the connection once Api stuff is done and clear the channel
        channel.shutdown();
        System.out.println("Shutdown the Channel");

    }
}
