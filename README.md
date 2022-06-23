# Camunda Resource Server and OAuth2

This demo project shows how a Camunda Workflow engine functions as the resource server and 
can be secured behind an OAuth identity provider. This particular example is intended to work with Keycloak 18.

See [here](https://github.com/jfspps/CamundaOAuthClientDemo) for an example client.

Instructions on how to set up the resource (Camunda) and authorisation (Keycloak, see the [docker-compose](/docker/docker-compose.yml) script) 
servers was based on [this vital resource](https://github.com/camunda-community-hub/camunda-bpm-identity-keycloak).

## Reminder about how to initialise the solution

Pull the latest images with the accompanying Docker Compose script and then run with `docker-compose up`.

The [client]((https://github.com/jfspps/CamundaOAuthClientDemo)) is set to run on port 8080, the authorisation server on 8083 and this resource server on 8081. 
The KeyCloak credentials are saved to MySQL, see the [docker-compose](docker/docker-compose.yml) script for more info.

Log in to KeyCloak and retrieve the secret for the client "camunda-identity-service" and update the corresponding field
in [application.yml](/src/main/resources/application.yml).

As a reminder (please confirm from the included [docker-compose](docker/docker-compose.yml) file):

+ The Keycloak authorisation server is using ports 8083 (HTTP) and 8084 (HTTPS, may not always be applied)
+ This resource server is using port 8081
+ The demo client application is using port 8080

The redirect URI for "camunda-identity-service" is 

## The basic idea

This project makes use of the [Camunda Identity Service plugin](https://github.com/camunda-community-hub/camunda-platform-7-keycloak).
This plugin allows Camunda (as part of the Camunda resource server) to act as a client, having its own OAuth client credentials 
on Keycloak's authorisation server. The name of the client in this case is "camunda-identity-service", under the realm "Spring_demo". (These
[instructions](https://github.com/camunda-community-hub/camunda-platform-7-keycloak#prerequisites-in-your-keycloak-realm) were followed up to 
and including the section on "Login Caching".) This account enables Camunda to offload its user's authentication credentials 
to Keycloak while retaining control of the users and groups credentials that are listed in the Camunda web app Cockpit page.

The work needed to secure the web app and secure the REST API are different. Version 2.6.7 of this resource server is configured to secure
the web app, while releases post-version 2.7.0 provide examples of a secured REST API (this also includes SSO).

## Testing the secured Camunda web app

Try to log in to Camunda Web App at `localhost:8081` and log in using the email address (that's the `useEmailAsCamundaUserId` 
in [application.yml](/src/main/resources/application.yml)) of a user saved on Keycloak. 
These [instructions](https://github.com/camunda-community-hub/camunda-platform-7-keycloak/tree/master/examples/sso-kubernetes#optional-security-for-the-camunda-rest-api)
were followed, along with careful inspection of the example code from [here](https://github.com/camunda-community-hub/camunda-platform-7-keycloak/tree/master/examples/sso-kubernetes).
This includes custom SSO login and logout features. The [static resources](src/main/resources/META-INF/resources) are recognised by Spring [by default](https://www.baeldung.com/spring-mvc-static-resources)
and are JS scripts that look for specific elements and then replace them if found.

## Testing the secured Camunda REST API

For resource server version 2.6.7 and before, the [demo Camunda Client](https://github.com/jfspps/CamundaOAuthClientDemo) can 
request resources from the resource server without needing to authenticate. This changes for resource server version 2.7.0 onwards; the 
resource server returns an HTTP 401 Unauthorised response (authentication not completed) whenever the RESTful methods are invoked.
