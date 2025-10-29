package com.example.customersvc.grpc;

import com.example.customersvc.model.Customer;
import com.example.customersvc.service.CustomerService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import com.example.customersvc.grpc.CustomerServiceGrpc;
import com.example.customersvc.grpc.GetCustomerRequest;
import com.example.customersvc.grpc.GetCustomerResponse;
import com.example.customersvc.grpc.ListCustomersRequest;
import com.example.customersvc.grpc.ListCustomersResponse;

@Component
public class CustomerGrpcService extends CustomerServiceGrpc.CustomerServiceImplBase {

  private final CustomerService svc;

  public CustomerGrpcService(CustomerService svc) {
    this.svc = svc;
  }

  @Override
  public void getCustomer(GetCustomerRequest request, StreamObserver<GetCustomerResponse> responseObserver) {
    Customer c = svc.getById(request.getId());
    com.example.customersvc.grpc.Customer protoCustomer = toProto(c);
    GetCustomerResponse resp = GetCustomerResponse.newBuilder().setCustomer(protoCustomer).build();
    responseObserver.onNext(resp);
    responseObserver.onCompleted();
  }

  @Override
  public void listCustomers(ListCustomersRequest request, StreamObserver<ListCustomersResponse> responseObserver) {
    ListCustomersResponse.Builder b = ListCustomersResponse.newBuilder();
    svc.all().forEach(c -> b.addCustomers(toProto(c)));
    responseObserver.onNext(b.build());
    responseObserver.onCompleted();
  }

  private com.example.customersvc.grpc.Customer toProto(Customer c) {
    return com.example.customersvc.grpc.Customer.newBuilder()
        .setId(c.getId() == null ? "" : c.getId())
        .setFirstName(c.getFirstName() == null ? "" : c.getFirstName())
        .setLastName(c.getLastName() == null ? "" : c.getLastName())
        .setEmail(c.getEmail() == null ? "" : c.getEmail())
        .setPhone(c.getPhone() == null ? "" : c.getPhone())
        .build();
  }
}
