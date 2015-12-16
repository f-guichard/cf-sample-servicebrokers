package com.orange.fab.services_operations;

import java.util.List;
import java.util.Map;
import java.util.Set;

//@Old style...
public class Patching {
//Implémenté à partir de http://docs.cloudfoundry.org/services/api.html#catalog-mgmt
	
	private String patching;
	
	public Patching(Object object, Object object2, Map<Object, Object> map)
	{
		
		this.patching = "{"
				 + "description : \'you told me \\: "+String.valueOf(object)+" and "+String.valueOf(object2);
		
		String par = new String();
		Set<Object> keys = map.keySet();
		for (Object key : keys)
		{
			par += "param="+map.get(key).toString()+"&";
		}
		this.patching += " and "+par+"}";
	}
	
    public String getContent() {
        return patching;
    }

}
