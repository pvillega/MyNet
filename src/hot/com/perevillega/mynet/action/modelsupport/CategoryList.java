package com.perevillega.mynet.action.modelsupport;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Category;
import static com.perevillega.mynet.action.settings.Settings.*;

@Name("categoryList")
public class CategoryList extends EnhancedSortEntityQuery<Category>
{		
	private static final String[] RESTRICTIONS = {		
		"lower(category.name) like concat('%',lower(#{categoryList.name}),'%')",};
	
	private String name;
	
    public CategoryList()
    {
        setEjbql("select category from Category category");
        setMaxResults(MAX_RESULTS);        
        setOrder("category.name asc");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
    }
    
    public List<Category> parentList(Category c) {
    	List<Category> list = getResultList();
    	list.remove(c);
    	return list;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void cleanSearch() {
		this.name = "";
	}

}
