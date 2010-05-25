package com.perevillega.mynet.action.modelsupport;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.model.Role;
import com.perevillega.mynet.model.User;

@Name("userListTools")
public class UserListTools {

	@In
	private EntityManager entityManager;
	
	@In
	private UserList userList;
	
    
    public void remove(User user) {  	
    	entityManager.merge(user);
    	
    	user.remove();
    	entityManager.remove(user);
    	entityManager.flush();
    	
    	userList.refresh();    	
    }
    
    public void removeSelected() {
    	List<User> list = userList.getStaticResultList();
    	for(User user: list) {
    		if(user.isSelected()){
    			user.remove();
    			entityManager.remove(user);    			
    		}
    	}
    	entityManager.flush();    	
    	userList.refresh();    	    	
    }
}
