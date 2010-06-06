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

import java.net.URISyntaxException;

import org.hibernate.mapping.Property;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Validator;



/**
 * Validator that verifies a URL is following the proper format using the URI class from the Java API
 * @author pvillega
 *
 */
public class URIValidator implements Validator<URI>,PropertyConstraint {

	@Override
	public void initialize(URI arg0) {
		//do nothing here
	}

	@Override
	public boolean isValid(Object uri) {
		if(uri == null) return false;
		if(!(uri instanceof String)) return false;
		try {
			new java.net.URL((String)uri);			
			return true;
		} catch (Exception e) {			
			return false;
		}		
	}

	@Override
	public void apply(Property arg0) {
		//do nothing here		
	}
}