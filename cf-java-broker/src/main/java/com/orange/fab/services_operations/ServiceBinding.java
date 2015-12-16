package com.orange.fab.services_operations;

import java.util.List;
import java.util.Map;
import java.util.Set;

//For binding stuff
import java.util.UUID;
import java.util.Random;

//@Old style...
public class ServiceBinding {
//Implémenté à partir de http://docs.cloudfoundry.org/services/api.html#catalog-mgmt
	
	private String servicebinding;
	
	public ServiceBinding(Map<String, Object> parameters)
	{
		//generateur avec collision, take care ;)
		//Stream API java 8 again :-p
		String username = new Random().ints(65,122) //65 = A ... 122 = z
                .filter(i-> (i <90 || i>97)) //on vire les caracteres speciaux de notre stream (voir http://www.table-ascii.com/)
                .mapToObj(i -> (char) i)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

		String bouchon = new String();
		Set<String> keys = parameters.keySet();
		for (Object key : keys)
		{
			bouchon += "/"+parameters.get(key).toString();
		}
		
		String password	= UUID.randomUUID().toString().substring(0, 10); //Olé, dangerous !	
		String host = "UnHoteQuiContientLeBackend";
		String port = "9999";
		String uri = "driver://"+username+":"+password+"@"+host+":"+port+bouchon;
		
		this.servicebinding = "{"
				 + "credentials : {"
				 + "uri : "+uri+","
				 + "username : "+username+","
				 + "password : "+password+","
				 + "host : "+host+","
				 + "port : "+port+","
				 + "database : "+bouchon
				 + "}"
				 + "}";
				 
	}
	
    public String getContent() {
        return servicebinding;
    }

}
