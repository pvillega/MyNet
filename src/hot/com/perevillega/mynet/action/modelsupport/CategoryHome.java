package com.perevillega.mynet.action.modelsupport;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessage.Severity;

import com.perevillega.mynet.model.Category;

@Name("categoryHome")
public class CategoryHome extends EntityHome<Category>
{
    @RequestParameter Long categoryId;

    @Override
    public Object getId()
    {
        if (categoryId == null)
        {
            return super.getId();
        }
        else
        {
            return categoryId;
        }
    }

    @Override
    public void create() {
        super.create();
    }
    
    @Override
    public String persist() {
    	if(!exists()) {
    		return super.persist();
    	} else {    		
			getStatusMessages().add(Severity.ERROR, "This Category name already exists");
			return "";
		}    	
    }
    
    private boolean exists(){
    	EntityManager em = getEntityManager();
		Query queryCategoryByName = em.createNamedQuery("findCategoryByName");
		queryCategoryByName.setParameter("name", getInstance().getName());
		Collection categories = queryCategoryByName.getResultList();
		
		return categories.size() > 0;
    }    

}
