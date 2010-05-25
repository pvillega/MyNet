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
