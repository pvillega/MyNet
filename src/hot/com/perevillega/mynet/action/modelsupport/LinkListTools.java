package com.perevillega.mynet.action.modelsupport;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

import com.perevillega.mynet.model.Link;
import com.perevillega.mynet.model.User;
import com.perevillega.mynet.model.Vote;

@Name("linkListTools")
public class LinkListTools {
	
	@In
	private EntityManager entityManager;
	
	@In
	private LinkList linkList;
	
	@In(required=false)
	private User currentUser;
    
	@Transactional
    public void remove(Link link) {  	
    	entityManager.merge(link);
    	
    	link.remove();
    	entityManager.remove(link);
    	entityManager.flush();
    	
    	linkList.refresh();    	
    }
	
    @Transactional
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

    
    public void vote(Link link, int value) { 
    	//TODO: first remove existing vote?     	
    	//add new vote
    	
    	entityManager.merge(currentUser);
    	
    	Vote v = new Vote();
    	v.setValue(value);
    	v.setLink(link);
    	v.setUser(currentUser);
    	
    	entityManager.persist(v);    	
    	
    	if(link.valoration() < -5) {
    		link.setHidden(true);
    	}    	
    	//TODO: update valoration on web page when voting!!
    	entityManager.merge(link);
    	entityManager.flush();    
    	
    	linkList.refresh();
    }
    
    public void favorite(Link link) {
    	
    	link.addFavorite(currentUser);
    	
    	entityManager.merge(link);    	
    	entityManager.flush();    
    	
    	linkList.refresh();
    }
    
    public void removeFavorite(Link link) {
    	
    	link.removeFavorite(currentUser);
    	
    	entityManager.merge(link);    	
    	entityManager.flush();    
    	
    	linkList.refresh();
    }
}
