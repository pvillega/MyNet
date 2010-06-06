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

import static com.perevillega.mynet.action.settings.Settings.MAX_RESULTS;

import java.util.Arrays;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Tag;

@Name("tagList")
@Scope(ScopeType.CONVERSATION)
public class TagList extends EnhancedSortEntityQuery<Tag>
{
	
	private static final String[] RESTRICTIONS = {		
		"lower(tag.name) like concat('%',lower(#{tagList.name}),'%')",};

	private String name;

    public TagList()
    {
        setEjbql("select tag from Tag tag");
        setMaxResults(MAX_RESULTS);
        setOrder("tag.name asc");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
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
