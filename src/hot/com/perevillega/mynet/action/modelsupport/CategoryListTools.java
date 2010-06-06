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
