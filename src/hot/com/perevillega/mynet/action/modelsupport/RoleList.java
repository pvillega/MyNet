package com.perevillega.mynet.action.modelsupport;

import java.util.Arrays;

import org.jboss.seam.ScopeType;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import com.perevillega.mynet.model.Role;

import static com.perevillega.mynet.action.settings.Settings.MAX_RESULTS;

@Name("roleList")
@Scope(ScopeType.CONVERSATION)
public class RoleList extends EnhancedSortEntityQuery<Role>
{
	private static final String[] RESTRICTIONS = {		
		"lower(role.name) like concat('%',lower(#{roleList.name}),'%')",};

	private String name;
	
    public RoleList()
    {
        setEjbql("select role from Role role");
        setMaxResults(MAX_RESULTS);
        setOrder("role.name asc");
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
