# Camunda Resource Server and OAuth2

This demo project shows how a Camunda Workflow engine functions as the resource server and 
can be secured behind an OAuth identity provider. This particular example is intended to work with Keycloak 18.

See [here](https://github.com/jfspps/CamundaOAuthClientDemo) for an example client.

Instructions on how to set up the resource (specifically, configuring Camunda's web app with Keycloak) and authorisation (Keycloak, see the [docker-compose](/docker/docker-compose.yml) script) 
servers was based on [this resource](https://github.com/camunda-community-hub/camunda-bpm-identity-keycloak).

## Starting the full solution

As a reminder (please confirm from the included [docker-compose](docker/docker-compose.yml) file):

+ The Keycloak authorisation server is using ports 8083 (HTTP) and 8084 (HTTPS, may not always be applied)
+ This resource server is using port 8081
+ The demo [client](https://github.com/jfspps/CamundaOAuthClientDemo) application is using port 8080

Pull the latest images with the accompanying Docker Compose script and then run with `docker-compose up`.

The KeyCloak credentials (including those managed on behalf of Camunda) are saved to MySQL, see 
the [docker-compose](docker/docker-compose.yml) script for more info.

Log in to KeyCloak and retrieve the secret for the client "camunda-identity-service" and update the corresponding field
in [application.yml](/src/main/resources/application.yml). Then start up the resource server.

The redirect URIs for "camunda-identity-service" are `http://127.0.0.1:8081/login` and `http://127.0.0.1:8081/camunda/*`, noting we refer to
the resource servers port 8081, not 8080. Also note that redirect URIs are treated as Strings (it seems) so `localhost` is NOT 
the same as `127.0.0.1`.

## The basic idea

[This article](https://docs.camunda.io/docs/components/best-practices/operations/securing-camunda-c7/) explains how Camunda is
secured through its own identity service or via a third-party identity service.

This project makes use of the [Camunda Identity Service plugin](https://github.com/camunda-community-hub/camunda-platform-7-keycloak).
This plugin allows Camunda's REST services (as part of the Camunda resource server) to act as a client, having its own OAuth client credentials 
on Keycloak's authorisation server. The name of the client in this case is "camunda-identity-service", under the realm "Spring_demo" (see these
[instructions](https://github.com/camunda-community-hub/camunda-platform-7-keycloak#prerequisites-in-your-keycloak-realm)).
This account enables Camunda to offload its user's authentication credentials 
to Keycloak while retaining control of the users and groups credentials that are listed in the Camunda web app Cockpit page. The Identity Service
Provider implemented through the plugin grants read-only access, so it is not possible to push changes to Camunda via the web app
or through POST/PUT/DELETE styled REST calls.

## Testing the secured Camunda web app

Try to log in to Camunda Web App at `127.0.0.1:8081/` (check [application.yml](/src/main/resources/application.yml)) and 
log in using 

+ the email address (if `useEmailAsCamundaUserId` is set to true in [application.yml](/src/main/resources/application.yml)) 
+ the username (if `useUsernameAsCamundaUserId` is set to true)
 
of a (Camunda) user saved on Keycloak. If the user is not part of the camunda-admin group (see `administratorGroupName` in
[application.yml](/src/main/resources/application.yml)) then they will get limited/no access to the cockpit, admin and task list
panels.

## POSTMAN testing

Please see [here](/Camunda%20Demo.postman_collection.json) for an example set of requests which follow the OAuth 
Authorisation Code Grant workflow.

When logging in to request an authorisation code, enter the username (not the email address). Currently, the BPMN process
cannot execute because we are missing a tenant id associated with the user. Two endpoints, one with and one without tenant id,
are provided in the POSTMAN export.

## Work in progress

Web App login confirmed; REST security is confirmed (HTTP 401 is returned without the JWT as part of the `Authorization` header)
however the process invocation is missing a tenant id. This is mentioned in the above 
[article](https://docs.camunda.io/docs/components/best-practices/operations/securing-camunda-c7/#securing-camundas-rest-api).
Multi-tenancy in Camunda is explained [here](https://docs.camunda.org/manual/latest/user-guide/process-engine/multi-tenancy/). 
Essentially, the idea is to isolate users to particular process instances who all share the same engine.

Also note that the logout does not appear to work. While the web app indicates that the logout was completed, the Keycloak 
session was still present.