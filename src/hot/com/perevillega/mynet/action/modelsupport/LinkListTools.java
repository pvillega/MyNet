package com.perevillega.mynet.action.modelsupport;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.perevillega.mynet.model.Link;

@Name("linkListTools")
public class LinkListTools {
	
	@In
	private EntityManager entityManager;
	
	@In
	private LinkList linkList;
	
    
    public void remove(Link link) {  	
    	entityManager.merge(link);
    	
    	link.remove();
    	entityManager.remove(link);
    	entityManager.flush();
    	
    	linkList.refresh();    	
    }
    
    public void removeSelected() {
    	List<Link> list = linkList.getStaticResultList();
    	for(Link link: list) {
    		if(link.isSelected()){    
    			link.remove();
    			entityManager.remove(link);    			
    		}
    	}
    	entityManager.flush();    	
    	linkList.refresh();    	    	
    }

}
