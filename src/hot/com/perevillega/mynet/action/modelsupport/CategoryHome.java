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
    
}
