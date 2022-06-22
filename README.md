# Camunda Resource Server and OAuth2

This demo project shows how a Camunda Workflow engine functions as the resource server and 
can be secured behind an OAuth identity provider. This particular example is intended to work with Keycloak 18.

See [here](https://github.com/jfspps/CamundaOAuthClientDemo) for an example client.

Instructions on how to set up the resource (Camunda) and authorisation (Keycloak, see the [docker-compose](/docker/docker-compose.yml) script) servers was based on [this](https://github.com/camunda-community-hub/camunda-bpm-identity-keycloak).

## Reminder about how to initialise the solution

Pull the latest images with the accompanying Docker Compose script and then run with `docker-compose up`.

The [client]((https://github.com/jfspps/CamundaOAuthClientDemo)) is set to run on port 8080, the authorisation server on 8083 and this resource server on 8081. 
The KeyCloak credentials are saved to MySQL, see the [docker-compose](docker/docker-compose.yml) script for more info.

Log in to KeyCloak and retrieve the secret for the client "camunda-identity-service" and update the corresponding field
in [application.yml](/src/main/resources/application.yml).

## Local Testing: secured Camunda web app

Try to log in to Camunda Web App at `localhost:8081` and log in using the email address (that's the `useEmailAsCamundaUserId` 
in [application.yml](/src/main/resources/application.yml)) of a user saved on Keycloak.

## Todo: securing the Camunda API

Currently, the Camunda API is not secured, so the [demo Camunda Client](https://github.com/jfspps/CamundaOAuthClientDemo) can 
request resources from the resource server.