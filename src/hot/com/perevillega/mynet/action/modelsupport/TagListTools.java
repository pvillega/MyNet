package com.perevillega.mynet.action.modelsupport;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

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
}
