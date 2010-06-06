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

package com.perevillega.mynet.action.validators;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.InvalidValue;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.perevillega.mynet.action.RegisterAction;
import com.perevillega.mynet.action.passwordsupport.PasswordBean;
import com.perevillega.mynet.model.User;

@Name("userValidator")
@AutoCreate
public class UserValidator {
	
	@In	private RegisterAction registerAction;
	
	private List<InvalidValue> invalidValues = new ArrayList<InvalidValue>();
	
	public boolean validate(User newuser, PasswordBean passwordBean) {
		
		if (!passwordBean.verify()) {
			addInvalidValue("confirm", PasswordBean.class,
				"Confirmation password does not match");
		}
		
		if (!registerAction.isUsernameAvailable(newuser.getUsername())) {
			addInvalidValue("username", User.class,
				"Username is already taken");
		}
		
		if (registerAction.isEmailRegistered(newuser.getEmail())) {
			addInvalidValue("emailAddress", User.class,
				"Email address is already registered");
		}
		
		return !hasInvalidValues();
	}
	
	public InvalidValue[] getInvalidValues() {
		return invalidValues.toArray(new InvalidValue[invalidValues.size()]);
	}
	
	public boolean hasInvalidValues() {
		return invalidValues.size() > 0;
	}
	
	public void reset() {
		invalidValues = new ArrayList<InvalidValue>();
	}
	
	protected void addInvalidValue(String property, Class beanClass, String message) {
		invalidValues.add(new InvalidValue(message, beanClass, property, null, null));
	}
	
}
