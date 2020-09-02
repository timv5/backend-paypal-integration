# backend-paypal-integration
This is a backend java (SpringBoot) application which integrates with Paypal (https://www.paypal.com/).
It communicates with paypal api and frontend application from where it gets an information about a desired product and a buyer.


## Usage
All configurations are set in application.yml file in a root directory.
These variables need to be configured: clientId, clientSecret, mode. All can be found on: https://developer.paypal.com/developer/accounts/
The application is running on localhost:8081, also the docker-compose file runs an instance of postgreSQL database, to which every transaction will be saved
and by default data.sql is executed (dummy data). Also you have to specify your sandbox/live account in data.sql (email, name, lastname), or create a new one on execution step.

## Prequences
- git clone project (backend-paypal-integration): https://github.com/timv5/backend-paypal-integration.git
- git clone project (frontend-paypal-integration): https://github.com/timv5/frontend-paypal-integration.git
- Installed Docker (docker-compose)
- Java, Maven (not for only running the application with docker)

To use this application you also need to get "frontend-paypal-integration" project that is available under my repos.
Both, frontend and backend application need to be in the same directory in order for docker-compose.yml file to work.

## Run the application
In a root directory execute:
    -  docker-compose -f docker-compose up -d
