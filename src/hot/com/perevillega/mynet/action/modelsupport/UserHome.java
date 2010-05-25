package com.perevillega.mynet.action.modelsupport;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import com.perevillega.mynet.model.User;

@Name("userHome")
public class UserHome extends EntityHome<User>
{
    @RequestParameter Long userId;

    @Override
    public Object getId()
    {
        if (userId == null)
        {
            return super.getId();
        }
        else
        {
            return userId;
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
