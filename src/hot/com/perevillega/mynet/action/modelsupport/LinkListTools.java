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

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

import com.perevillega.mynet.model.Link;
import com.perevillega.mynet.model.User;

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
    	
    	if(value > 0){
    		link.addLike(currentUser);
    		link.removeDislike(currentUser);
    	} else {
    		link.removeLike(currentUser);
    		link.addDislike(currentUser);    		
    	}
    	
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
