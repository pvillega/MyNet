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

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import com.perevillega.mynet.model.User;
import org.jboss.seam.ScopeType;

import com.perevillega.mynet.action.queries.EnhancedSortEntityQuery;
import static com.perevillega.mynet.action.settings.Settings.MAX_RESULTS;

@Name("userList")
@Scope(ScopeType.CONVERSATION)
public class UserList extends EnhancedSortEntityQuery<User>
{
	private static final String[] RESTRICTIONS = {		
		"lower(user.username) like concat('%',lower(#{userList.name}),'%')",
		"lower(user.email) like concat('%',lower(#{userList.email}),'%')",};

	private String name;
	private String email;
	
    public UserList()
    {
        setEjbql("select user from User user");
        setMaxResults(MAX_RESULTS);
        setOrder("user.username asc");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void cleanSearch() {
		this.name = "";
		this.email = "";
	}
}
