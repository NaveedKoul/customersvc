package com.example.customersvc.client;

import com.example.customersvc.grpc.Customer;
import com.example.customersvc.grpc.CustomerServiceGrpc;
import com.example.customersvc.grpc.GetCustomerRequest;
import com.example.customersvc.grpc.ListCustomersRequest;
import com.example.customersvc.grpc.ListCustomersResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClientMain {

    public static void main(String[] args) throws Exception {
        String target = "localhost:9090";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

        CustomerServiceGrpc.CustomerServiceBlockingStub stub = CustomerServiceGrpc.newBlockingStub(channel);

        System.out.println("Calling ListCustomers...");
        ListCustomersResponse listResp = stub.listCustomers(ListCustomersRequest.newBuilder().build());
        for (Customer c : listResp.getCustomersList()) {
            System.out.println("- " + c.getId() + ": " + c.getFirstName() + " " + c.getLastName() + " <" + c.getEmail() + "> " + c.getPhone());
        }

        System.out.println("Calling GetCustomer(id=1)...");
        GetCustomerRequest req = GetCustomerRequest.newBuilder().setId("1").build();
        com.example.customersvc.grpc.GetCustomerResponse res = stub.getCustomer(req);
        Customer c = res.getCustomer();
        System.out.println("Got: " + c.getId() + " -> " + c.getFirstName() + " " + c.getLastName());

        channel.shutdownNow();
    }
}
