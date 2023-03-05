./mvnw clean package
docker build --tag=ebanking_service:v1.0.0 .
docker run -p8080:8080 ebanking_service:v1.0.0