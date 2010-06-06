/**
* My Net is free software, you can redistribute it and/or modify
* it under the terms of GNU Affero General Public License
* as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
*
* You should have received a copy of the the GNU Affero
* General Public License, along with My Net. If not, see
*
* http://www.gnu.org/licenses/agpl.txt
*/

package com.perevillega.mynet.action.modelsupport;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.model.Role;

@Name("roleListTools")
public class RoleListTools {
	
	@In
	private EntityManager entityManager;
	
	@In
	private RoleList roleList;
	
    
    public void remove(Role role) {  	
    	entityManager.merge(role);
    	
    	role.remove();
    	entityManager.remove(role);
    	entityManager.flush();
    	
    	roleList.refresh();    	
    }
    
    public void removeSelected() {
    	List<Role> list = roleList.getStaticResultList();
    	for(Role role: list) {
    		if(role.isSelected()){
    			role.remove();
    			entityManager.remove(role);    			
    		}
    	}
    	entityManager.flush();    	
    	roleList.refresh();    	    	
    }
}
