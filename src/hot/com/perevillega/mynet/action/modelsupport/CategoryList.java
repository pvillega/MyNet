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

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Category;
import static com.perevillega.mynet.action.settings.Settings.*;

@Name("categoryList")
@Scope(ScopeType.CONVERSATION)
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
