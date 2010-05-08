package com.perevillega.mynet.action.modelsupport;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Expressions.ValueExpression;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import com.perevillega.mynet.model.Tag;
import static com.perevillega.mynet.action.settings.Settings.*;

@Name("tagList")
public class TagList extends EnhancedSortEntityQuery<Tag>
{
	
	private static final String[] RESTRICTIONS = {		
		"lower(tag.name) like concat(lower('%',#{tagList.name}),'%')",};

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
