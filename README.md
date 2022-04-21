# jpetstore-boot

docker network create jpetstore-network

docker run --name redis --network jpetstore-network -it -p 6379:6379 -d redis:6.0.5

docker run --name rabbitmq --network jpetstore-network -d -p 5672:5672 -p 8087:15672 -e RABBITMQ_DEFAULT_USER=jpetstore -e RABBITMQ_DEFAULT_PASS=qwer1234 rabbitmq:3.8.5-management

docker run --name jpetstoredb_msa --network jpetstore-network -d -p 13306:3306 jaypark00/jpetstoredb:msa_v1

docker run --name orderdb_msa -d --network jpetstore-network -p 23306:3306 jaypark00/orderdb:msa_v1

docker run --name productdb_msa --network jpetstore-network -d -p 33306:3306 jaypark00/productdb:msa_v1

docker run --name configserver --network jpetstore-network -e DEFAULT_BRANCH=gcp-firsttime -d -p 8888:8888 jaypark00/configserver:msa_v1

docker run --name eurekaserver --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e EUREKA_PORT=8761 -e OTHER_EUREKA_URI=eurekaserver2:8762 -d -p 8761:8761 jaypark00/eurekaserver:msa_v1

docker run --name eurekaserver2 --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e EUREKA_PORT=8762 -e OTHER_EUREKA_URI=eurekaserver:8761 -d -p 8762:8762 jaypark00/eurekaserver:msa_v1

docker run --name product --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e PRODUCT_PORT=17071 -e DATASOURCE_URL=jdbc:mariadb://productdb_msa:3306/productdb -e DB_USERNAME=product -e DB_PASSWORD=qwer1234 -e RABBITMQ_HOST=rabbitmq -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=jpetstore -e RABBITMQ_PASSWORD=qwer1234 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 17071:17071 jaypark00/product:msa_v1

docker run --name product2 --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e PRODUCT_PORT=17072 -e DATASOURCE_URL=jdbc:mariadb://productdb_msa:3306/productdb -e DB_USERNAME=product -e DB_PASSWORD=qwer1234 -e RABBITMQ_HOST=rabbitmq -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=jpetstore -e RABBITMQ_PASSWORD=qwer1234 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 17072:17072 jaypark00/product:msa_v1

docker run --name order --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e ORDER_PORT=16061 -e DATASOURCE_URL=jdbc:mariadb://orderdb_msa:3306/orderdb -e DB_USERNAME=order -e DB_PASSWORD=qwer1234 -e RABBITMQ_HOST=rabbitmq -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=jpetstore -e RABBITMQ_PASSWORD=qwer1234 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 16061:16061 jaypark00/order:msa_v1

docker run --name order2 --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e ORDER_PORT=16062 -e DATASOURCE_URL=jdbc:mariadb://orderdb_msa:3306/orderdb -e DB_USERNAME=order -e DB_PASSWORD=qwer1234 -e RABBITMQ_HOST=rabbitmq -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=jpetstore -e RABBITMQ_PASSWORD=qwer1234 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 16062:16062 jaypark00/order:msa_v1

docker run --name jpetstore --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e REDIS_HOST=redis -e REDIS_PORT=6379 -e JPETSTORE_PORT=15051 -e DATASOURCE_URL=jdbc:mariadb://jpetstoredb_msa:3306/jpetstoredb -e DB_USERNAME=jpetstore -e DB_PASSWORD=qwer1234 -e RABBITMQ_HOST=rabbitmq -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=jpetstore -e RABBITMQ_PASSWORD=qwer1234 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 15051:15051 jaypark00/jpetstore:msa_v1

docker run --name jpetstore2 --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e REDIS_HOST=redis -e REDIS_PORT=6379 -e JPETSTORE_PORT=15052 -e DATASOURCE_URL=jdbc:mariadb://jpetstoredb_msa:3306/jpetstoredb -e DB_USERNAME=jpetstore -e DB_PASSWORD=qwer1234 -e RABBITMQ_HOST=rabbitmq -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=jpetstore -e RABBITMQ_PASSWORD=qwer1234 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 15052:15052 jaypark00/jpetstore:msa_v1

docker run --name zuulserver --network jpetstore-network -e PROFILE=prod -e SPRING_CLOUD_CONFIG_URI=http://configserver:8888 -e ZUUL_PORT=8080 -e EUREKA_DEFAULTZONE=http://eurekaserver:8761/eureka/,http://eurekaserver2:8762/eureka/ -d -p 8080:8080 jaypark00/zuulserver:msa_v1
