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
