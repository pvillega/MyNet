package com.perevillega.mynet.action.modelsupport;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessage.Severity;

import com.perevillega.mynet.model.Tag;

@Name("tagHome")
public class TagHome extends EntityHome<Tag>
{
    @RequestParameter Long tagId;

    @Override
    public Object getId()
    {
        if (tagId == null)
        {
            return super.getId();
        }
        else
        {
            return tagId;
        }
    }
    

    @Override
    public void create() {
        super.create();
    }
            
	@Override
	public String remove() {
		getInstance().remove();
		return super.remove();
	}
    
}
