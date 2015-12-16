package com.orange.fab.catalog;

//@Old style...
public class Catalogue {
//Implémenté à partir de http://docs.cloudfoundry.org/services/api.html#catalog-mgmt
	
	private String catalogue;
	
	public Catalogue()
	{
		String plan = "plans: [{"
				+ "id: plan1-guid-here,"
				+ "name: small,"
				+ "description: A small shared database with 100mb storage quota and 10 connections"
		    	+ "},{"
		    	+ "id: plan2-guid-here,"
		    	+ "name: large,"
		    	+ "description: A large dedicated database with 10GB storage quota, 512MB of RAM, and 100 connections,"
		    	+ "free: false"
		    	+ "}]";
		
		String dashboard = "dashboard_client: {"
			    + "id: client-id-1,"
			    + "secret: secret-1,"
			    + "redirect_uri: https://dashboard.service.com"
			    + "}";
		
		String service = "id: service-guid-here,"
			    + "name: mysql,"
			    + "description: A MySQL-compatible relational database,"
			    + "bindable: true";
		
		this.catalogue = "{"
				  			+ "services: [{"
				  				+ service
				  				+","
				  				+ plan
				  				+ ","
				  				+ dashboard
				  			+ "}]"
				  		+ "}";	
	}
	
    public String getContent() {
        return catalogue;
    }

}
