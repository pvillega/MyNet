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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.model.Link;
import com.perevillega.mynet.model.Tag;

@Name("tagListTools")
public class TagListTools {

	@In
	private EntityManager entityManager;
	
	@In
	private TagList tagList;
	
    
    public void remove(Tag tag) {  	
    	entityManager.merge(tag);
    	
    	tag.remove();
    	entityManager.remove(tag);
    	entityManager.flush();
    	
    	tagList.refresh();    	
    }
    
    public void removeSelected() {
    	List<Tag> list = tagList.getStaticResultList();
    	for(Tag tag: list) {
    		if(tag.isSelected()){
    			tag.remove();
    			entityManager.remove(tag);    			
    		}
    	}
    	entityManager.flush();    	
    	tagList.refresh();    	    	
    }
    
    public void merge(Tag tag){
    	Query queryTagByName = entityManager.createNamedQuery("findTagByName");
		queryTagByName.setParameter("name", tag.getMergeTarget());
		Collection tags = queryTagByName.getResultList();

		if (!tags.isEmpty()) {
			// tag exists, we merge them			
			Iterator it = tags.iterator();
			Tag destinationTag = (Tag) it.next();
			for(Link link: tag.getLinks()) {				
				link.addTag(destinationTag);
				destinationTag.addLink(link);				
				entityManager.merge(link);
			}
			entityManager.merge(destinationTag);
			tag.remove();
			entityManager.remove(tag);
		} else {
			// tag doesn't exist, we just rename it			
			tag.setName(tag.getMergeTarget());			
			entityManager.merge(tag);
		}
		
    	entityManager.flush();    	
    	tagList.refresh();    	
    }
}
