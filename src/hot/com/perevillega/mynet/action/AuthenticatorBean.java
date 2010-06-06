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

package com.perevillega.mynet.action;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.perevillega.mynet.action.passwordsupport.PasswordManager;
import com.perevillega.mynet.model.Role;
import com.perevillega.mynet.model.User;

@Name("authenticator")
public class AuthenticatorBean implements Authenticator
{
    @Logger private Log log;

    @In Identity identity;
    @In Credentials credentials;
    @In private EntityManager entityManager;
    @In(create=true) private PasswordManager passwordManager;
    @Out(required = false) private User currentUser;

    @Transactional
    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername());
        
        try{ 
	        User user = (User) entityManager.createQuery(
	        "select u from User u where u.username = :username")
	        .setParameter("username", identity.getUsername() )
	        .getSingleResult();
	        
	        if (!validatePassword(identity.getPassword(), user)) {
				return false;
			}
	        
	        currentUser = user;		
	        
	        identity.addRole("user");
			if (user.getRoles() != null) {
				for (Role role : user.getRoles()) {					
					identity.addRole(role.getName());
				}
			}
			return true;

        }catch(NoResultException e) {
        	return false;
        }
    }
    
    public boolean validatePassword(String password, User u) {
		return passwordManager.hash(password).equals(u.getPasswordHash());
	}

}
