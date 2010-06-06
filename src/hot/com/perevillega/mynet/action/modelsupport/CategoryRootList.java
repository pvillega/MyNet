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

import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Category;

@Name("categoryRootList")
public class CategoryRootList extends EnhancedSortEntityQuery<Category>
{
    public CategoryRootList()
    {
        setEjbql("select category from Category category where category.parent is null");
    }    
    
}
