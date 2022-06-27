# Camunda Resource Server and OAuth2

This demo project shows how a Camunda Workflow engine functions as the resource server and 
can be secured behind an OAuth identity provider. This particular example is intended to work with Keycloak 18.

See [here](https://github.com/jfspps/CamundaOAuthClientDemo) for an example client.

Much of the underlying connection between Camunda and OAuth was taken from [this repo](https://github.com/camunda-consulting/camunda-7-code-examples/tree/master/snippets/springboot-security-sso).

## Todo

At the moment, the Keycloak Docker configuration is not in use since we are connecting to the GitHub identity service provider.
