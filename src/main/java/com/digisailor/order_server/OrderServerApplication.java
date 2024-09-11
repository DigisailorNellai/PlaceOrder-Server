package com.digisailor.order_server;

import com.digisailor.order_server.service.OrderServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class OrderServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServerApplication.class, args);
	}

}

@Configuration
class GrpcServerConfiguration {

	@Autowired
	private OrderServiceImpl orderService;

	@PostConstruct
	public void startGrpcServer() throws IOException {
		Server server = ServerBuilder.forPort(9090)
				.addService(orderService)
				.build();

		server.start();

		System.out.println("gRPC server started on port 9090");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Received shutdown request");
			server.shutdown();
			System.out.println("gRPC server stopped");
		}));
	}
}
