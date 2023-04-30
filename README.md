# start postgres 
docker run --network host -e POSTGRES_PASSWORD=admin postgres

# start pact broker
docker run --network host -e PACT_BROKER_DATABASE_URL=postgres://postgres:admin@127.0.0.1:5432/pact_broker pactfoundation/pact-broker

# log into postgresql
docker exec -it container_id psql -U postgres -W

# create database
CREATE DATABASE pact_broker;
CREATE ROLE pact_broker WITH LOGIN PASSWORD 'CHANGE_ME';
GRANT ALL PRIVILEGES ON DATABASE pact_broker TO pact_broker;

# publish ui contract
docker run --network host -v $(pwd)/pacts:/pacts pactfoundation/pact-cli pact-broker publish /pacts/MyConsumer-MyProvider.json --consumer-app-version=1.0.0 --broker-base-url http://127.0.0.1:9292 

# verify ui contract on provider and publish results
mvn verify -Dpact.verifier.publishResults=true

# can i deploy
docker run --rm \
--network host \
-e PACT_BROKER_BASE_URL=http://127.0.0.1:9292 \
-e PACT_BROKER_USERNAME=pact_broker \
-e PACT_BROKER_PASSWORD=CHANGE_ME \
pactfoundation/pact-cli \
pact-broker can-i-deploy \
--pacticipant MyConsumer \
--latest