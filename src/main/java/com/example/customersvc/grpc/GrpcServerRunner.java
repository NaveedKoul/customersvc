package com.example.customersvc.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import io.grpc.protobuf.services.ProtoReflectionService;

@Component
public class GrpcServerRunner implements SmartLifecycle {

  private final CustomerGrpcService customerService;
  private final int port;
  private Server server;
  private volatile boolean running = false;

  public GrpcServerRunner(CustomerGrpcService customerService, @Value("${grpc.port:9090}") int port) {
    this.customerService = customerService;
    this.port = port;
  }

  @Override
  public void start() {
    try {
      server = ServerBuilder.forPort(port)
          .addService(customerService)
          .addService(ProtoReflectionService.newInstance())   // <<-- add this line
          .build()
          .start();
      running = true;
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        if (server != null) server.shutdown();
      }));
      System.out.println("gRPC server started on port " + port);
    } catch (Exception e) {
      throw new RuntimeException("Failed to start gRPC server", e);
    }
  }

  @Override
  public void stop() {
    if (server != null) {
      server.shutdown();
      running = false;
    }
  }

  @Override
  public boolean isRunning() {
    return running;
  }
}
