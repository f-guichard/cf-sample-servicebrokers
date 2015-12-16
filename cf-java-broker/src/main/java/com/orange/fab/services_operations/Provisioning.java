package com.orange.fab.services_operations;

import java.util.List;
import java.util.Map;
import java.util.Set;

//@Old style...
public class Provisioning {
//Implémenté à partir de http://docs.cloudfoundry.org/services/api.html#catalog-mgmt
	
	private String provisioning;
	
	public Provisioning(Object object, Object object2, Map<Object, Object> map)
	{
		
		this.provisioning = "{"
				 + "dashboard_url : http://"+String.valueOf(object)+"."+String.valueOf(object2)+".com/?";
		
		String par = new String();
		Set<Object> keys = map.keySet();
		for (Object key : keys)
		{
			par += "param="+map.get(key).toString()+"&";
		}
		this.provisioning += par+"}";
	}
	
    public String getContent() {
        return provisioning;
    }

}
