package com.perevillega.mynet.action.modelsupport;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import com.perevillega.mynet.model.Role;

@Name("roleHome")
public class RoleHome extends EntityHome<Role>
{
    @RequestParameter Long roleId;

    @Override
    public Object getId()
    {
        if (roleId == null)
        {
            return super.getId();
        }
        else
        {
            return roleId;
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
