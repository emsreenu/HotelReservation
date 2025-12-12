Prerequisites

Ensure you have the following installed:

1.Java 17+ (or the version required by your project)
2.Maven 3.8+ or Gradle (depending on your project)
3.Git (optional)

An IDE like IntelliJ, Eclipse, or VS Code (optional)

Project Setup
1.Clone the Project(co mmand Prompt)

      git clone https://github.com/emsreenu/HotelReservation.git
      cd HotelReservation
      mvn clean install
      
2.Run the packaged JAR:
      mvn spring-boot:run
    Run the package JAR
    
      java -jar target/HotelReservation-0.0.1.war

To call the Endpoint:
curl -X POST http://localhost:8080/api/echo](http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{ "noOfCustomers":2,"roomType":"MEDIUM", "startDate":"2025-04-01", "endDate":"2025-04-03","paymentMode":"CASH","customerName":"Krishna","noOfCustomers":}'
