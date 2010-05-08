package com.perevillega.mynet.action.modelsupport;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.model.Category;
import com.perevillega.mynet.model.Tag;

@Name("categoryListTools")
public class CategoryListTools {
	
	@In
	private EntityManager entityManager;
	
	@In
	private CategoryList categoryList;
	
    
    public void remove(Category category) {  	
    	entityManager.merge(category);
    	
    	entityManager.remove(category);
    	entityManager.flush();
    	
    	categoryList.refresh();    	
    }
    
    public void removeSelected() {
    	List<Category> list = categoryList.getStaticResultList();
    	for(Category cat: list) {
    		if(cat.isSelected()){    			
    			entityManager.remove(cat);    			
    		}
    	}
    	entityManager.flush();    	
    	categoryList.refresh();    	    	
    }

}
