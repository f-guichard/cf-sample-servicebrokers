package com.orange.fab.services_operations;

//@Old style...
public class LastOperation {
//Implémenté à partir de http://docs.cloudfoundry.org/services/api.html#catalog-mgmt
	
	private String lastoperation;
	
	public LastOperation(String instance_id)
	{
		this.lastoperation = "{" 
				+ "state : succeded,"
			    + "description : "+instance_id+" fake service created."
				+ "}";
	}
	
    public String getContent() {
        return lastoperation;
    }

}
