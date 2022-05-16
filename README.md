# Camunda Resource Server and OAuth2

This demo project shows how a Camunda Workflow engine functions as the resource server and 
can be secured behind an OAuth identity provider. This particular example is intended to work with Keycloak 18.

See [here](https://github.com/jfspps/CamundaOAuthClientDemo) for an example client.

Instructions on how to set up the resource (Camunda) and authorisation (Keycloak, see the [docker-compose](/docker/docker-compose.yml) script) servers was based on [this](https://github.com/camunda-community-hub/camunda-bpm-identity-keycloak).

## Local Testing

The client is running on port 8080, the authorisation server on 8083 and resource server on 8081.

Try to log in to Camunda Web App at `localhost:8081` and log in using the email address (that's the `useEmailAsCamundaUserId` 
in [application.yml](/src/main/resources/application.yml)) of a user saved on Keycloak.
