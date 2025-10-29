# customersvc (fixed)

This project exposes REST, GraphQL and gRPC endpoints that return mock customer data.

**What I fixed in this package**
- Corrected `pom.xml` XML header so Maven can parse it.
- Fixed gRPC Java imports so they match the generated Java package `com.example.customersvc.grpc`.
- Updated the Java gRPC test client to import the correct generated classes.

## Build & run
1. Build (this generates Java classes from proto):
   ```bash
   mvn clean package
   ```
2. Run:
   ```bash
   mvn spring-boot:run
   ```
   or:
   ```bash
   java -jar target/customersvc-0.0.1-SNAPSHOT.jar
   ```

## Test
- REST: `curl http://localhost:8080/api/customers`
- GraphQL: POST to `/graphql`
- gRPC: `grpcurl -plaintext -d '{}' localhost:9090 customersvc.CustomerService/ListCustomers`

If you want, I can also produce a zip that includes a pre-built fat JAR (built in this environment) — I can attempt that now, or walk you through building it locally.


**Note:** Removed `net.devh` starter; the gRPC server now boots via `GrpcServerRunner` using Netty on port 9090.

## Notes on gRPC wiring
We removed the `net.devh` starter and run a plain gRPC server managed by Spring via `SmartLifecycle` (`GrpcServerRunner`). 
The server listens on `grpc.port` (default 9090) and registers `CustomerGrpcService`. 
Runtime deps are pinned: `io.grpc:grpc-protobuf`, `io.grpc:grpc-stub`, `io.grpc:grpc-netty-shaded` 1.75.0 and `protobuf-java` 3.25.0.
