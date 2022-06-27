package com.example.camundaoauthresourceserverdemo.multitenancy;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.cfg.multitenancy.TenantIdProvider;
import org.camunda.bpm.engine.impl.cfg.multitenancy.TenantIdProviderCaseInstanceContext;
import org.camunda.bpm.engine.impl.cfg.multitenancy.TenantIdProviderHistoricDecisionInstanceContext;
import org.camunda.bpm.engine.impl.cfg.multitenancy.TenantIdProviderProcessInstanceContext;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.identity.Authentication;

import java.util.List;

/**
 * Provides the tenant-id based on the current authenticated tenant.
 */
public class CustomTenantIdProvider implements TenantIdProvider {

    @Override
    public String provideTenantIdForProcessInstance(TenantIdProviderProcessInstanceContext ctx) {
        return getTenantIdOfCurrentAuthentication();
    }

    @Override
    public String provideTenantIdForCaseInstance(TenantIdProviderCaseInstanceContext ctx) {
        return getTenantIdOfCurrentAuthentication();
    }

    @Override
    public String provideTenantIdForHistoricDecisionInstance(TenantIdProviderHistoricDecisionInstanceContext ctx) {
        return getTenantIdOfCurrentAuthentication();
    }

    protected String getTenantIdOfCurrentAuthentication() {

        IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
        Authentication currentAuthentication = identityService.getCurrentAuthentication();

        if (currentAuthentication != null) {

            List<String> tenantIds = currentAuthentication.getTenantIds();
            if (tenantIds != null) {
                if (tenantIds.size() == 1) {
                    return tenantIds.get(0);

                } else if (tenantIds.isEmpty()) {
                    throw new IllegalStateException("no authenticated tenant");
                } else {
                    throw new IllegalStateException("more than one authenticated tenant");
                }
            } else
                throw new IllegalStateException("Could not retrieve tenants IDs");

        } else {
            throw new IllegalStateException("no authentication");
        }
    }

}
