package com.orange.fab;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.orange.fab.catalog.Catalogue;
import com.orange.fab.services_operations.LastOperation;
import com.orange.fab.services_operations.Patching;
import com.orange.fab.services_operations.Provisioning;
import com.orange.fab.services_operations.ServiceBinding;

//Checker http://docs.cloudfoundry.org/services/api.html#catalog-mgmt

@RestController
public class ResponseController {

	//Authentication
	//Basic Http Auth : Authorized Basic 45MGL=565mgkgo09
	
	
	//Catalog
	//GET /v2/catalog
	//curl -H "X-Broker-API-Version: 2.8" http://username:password@broker-url/v2/catalog
    @RequestMapping(method=RequestMethod.GET, value="/v2/catalog")
    public ResponseEntity<Catalogue> getCatalogue() {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	return new ResponseEntity<Catalogue>(new Catalogue(), httpHeaders, HttpStatus.ACCEPTED);
    }

    //Polling Last Operation (async only)
    //GET /v2/service_instances/:instance_id/last_operation
    //curl http://username:password@broker-url/v2/service_instances/:instance_id/last_operation
    @RequestMapping(method=RequestMethod.GET, value="/v2/service_instances/{instanceID}/last_operation")
    public ResponseEntity<LastOperation> putServiceInstance(@PathVariable("instanceID") final String instanceID) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	return new ResponseEntity<LastOperation>(new LastOperation(instanceID), httpHeaders, HttpStatus.OK);
    }
    
    //Provisioning
    //PUT /v2/service_instances/:instance_id
    /*curl http://username:password@broker-url/v2/service_instances/:instance_id -d '{
    "organization_guid": "org-guid-here",
    "plan_id":           "plan-guid-here",
    "service_id":        "service-guid-here",
    "space_guid":        "space-guid-here",
    "parameters":        {
      "parameter1": 1,
      "parameter2": "value"
    	}
  	}' -X PUT -H "X-Broker-API-Version: 2.8" -H "Content-Type: application/json"
  */  
    @RequestMapping(method=RequestMethod.PUT, value="/v2/service_instances/{instance_id}",
    		consumes = "application/json")
    public ResponseEntity<Provisioning> putServiceBinding(
    		@RequestHeader("X-Broker-API-Version") final String apiversion, 
    		@PathVariable("instance_id") final String instance_id, 
    		@RequestBody final Map<String, Object> parameters) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.set("X-Broker-API-Version-Accepted", "false");
    	if(apiversion.contains("2.8"))
    	{
    		httpHeaders.set("X-Broker-API-Version-Accepted", "true");
    	}
    	httpHeaders.set("URL_INSTANCE_ID", instance_id);
    	return new ResponseEntity<Provisioning>(new Provisioning(
    			parameters.get("plan_id"), parameters.get("service_id"), 
    			(Map<Object, Object>)parameters.get("parameters")),
    			httpHeaders, HttpStatus.CREATED);
    }
    
    //Updating un plan de service
    //PATCH /v2/service_instances/:instance_id
    /*
     * curl http://username:password@broker-url/v2/service_instances/:instance_id -d '{
  		"service_id": "service-guid-here",
  		"plan_id": "plan-guid-here",
  		"parameters": 
  		{
    		"parameter1": 1,
    		"parameter2": "value"
  		},
  		"previous_values":
  		{
    		"plan_id": "old-plan-guid-here",
    		"service_id": "service-guid-here",
    		"organization_id": "org-guid-here",
    		"space_id": "space-guid-here"
  		}
	}' -X PATCH -H "X-Broker-API-Version: 2.8" -H "Content-Type: application/json"
     */
    @RequestMapping(method=RequestMethod.PATCH, value="/v2/service_instances/{instance_id}",
    		consumes = "application/json")
    public ResponseEntity<Patching> updateServiceProvisioning(
    		@RequestHeader("X-Broker-API-Version") final String apiversion,
    		@PathVariable("instance_id") final String instance_id,
    		@RequestBody final Map<String, Object> parameters) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.set("X-Broker-API-Version-Accepted", "KO");
    	if(apiversion.contains("2.8"))
    	{
    		httpHeaders.set("X-Broker-API-Version-Accepted", "OK");
    	}
    	httpHeaders.set("URL_INSTANCE_ID", instance_id);
    	return new ResponseEntity<Patching>(new Patching(
    			parameters.get("plan_id"), parameters.get("service_id"),
    			(Map<Object, Object>)parameters.get("previous_values")), 
    			httpHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    //Binding
    //PUT /v2/service_instances/:instance_id/service_bindings/:binding_id
    /*
     * curl http://username:password@broker-url/v2/service_instances/:instance_id/service_bindings/:binding_id -d '{
  			"plan_id":      "plan-guid-here",
  			"service_id":   "service-guid-here",
  			"app_guid":     "app-guid-here",
  			"bind_resource":     {
    			"app_guid": "app-guid-here"
  			},
  			"parameters":        {
    			"parameter1-name-here": 1,
    			"parameter2-name-here": "parameter2-value-here"
  			}
		}' -X PUT
     */
    @RequestMapping(method=RequestMethod.PUT, value="/v2/service_instances/{instance_id}/service_bindings/{binding_id}",
    		consumes = "application/json")
    public ResponseEntity<ServiceBinding> createServiceBinding(@PathVariable("binding_id") final String binding_id,
    		@PathVariable("instance_id") final String instance_id,
    		@RequestBody final Map<String, Object> parameters) {
    	HttpHeaders httpHeaders = new HttpHeaders();
      	httpHeaders.set("URL_INSTANCE_ID", instance_id);
      	httpHeaders.set("CFY-CC-BINDING_ID", binding_id);
    	return new ResponseEntity<ServiceBinding>(new ServiceBinding(parameters), httpHeaders, HttpStatus.CREATED);
    }
    
    //Unbinding
    //DELETE /v2/service_instances/:instance_id/service_bindings/:binding_id
    /*
     * curl 'http://username:password@broker-url/v2/service_instances/:instance_id/\
             service_bindings/:binding_id?service_id=service-id-here&plan_id=plan-id-here'
             -X DELETE -H "X-Broker-API-Version: 2.8"
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/v2/service_instances/{instance_id}/service_bindings/{binding_id}")
    public ResponseEntity<String> deleteServiceBinding(
    		@RequestHeader("X-Broker-API-Version") final String apiversion,
    		@PathVariable("binding_id") final String binding_id, 
    		@PathVariable("instance_id") final String instance_id, 
    		@RequestParam("service_id") final String service_id, 
    		@RequestParam("plan_id") final String plan_id) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.set("X-Broker-API-Version-Accepted", "KO");
    	if(apiversion.contains("2.8"))
    	{
    		httpHeaders.set("X-Broker-API-Version-Accepted", "OK");
    	}
      	httpHeaders.set("CFY-INSTANCE_ID", instance_id);
      	httpHeaders.set("CFY-CC-BINDING_ID", binding_id);
      	httpHeaders.set("CFY-DELETE-SID", service_id);
      	httpHeaders.set("CFY-DELETE-PID", plan_id);
    	return new ResponseEntity<String>(new String("{}"), httpHeaders, HttpStatus.GONE);
    }
    
    //Deprovisioning
    //DELETE /v2/service_instances/:instance_id
    /*
     * curl 'http://username:password@broker-url/v2/service_instances/:instance_id?service_id=
    	service-id-here&plan_id=plan-id-here' -X DELETE -H "X-Broker-API-Version: 2.8"
     */
    
    @RequestMapping(method=RequestMethod.DELETE, value="/v2/service_instances/{instance_id}")
    public ResponseEntity<String> deleteServiceProvisioning(
    		@RequestHeader("X-Broker-API-Version") final String apiversion,
    		@PathVariable("instance_id") final String instance_id, 
    		@RequestParam("service_id") final String service_id, 
    		@RequestParam("plan_id") final String plan_id) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.set("X-Broker-API-Version-Accepted", "KO");
    	if(apiversion.contains("2.8"))
    	{
    		httpHeaders.set("X-Broker-API-Version-Accepted", "OK");
    	}
      	httpHeaders.set("CFY-INSTANCE_ID", instance_id);
      	httpHeaders.set("CFY-DELETE-SID", service_id);
      	httpHeaders.set("CFY-DELETE-PID", plan_id);
    	return new ResponseEntity<String>(new String("{}"), httpHeaders, HttpStatus.GONE);
    }
}