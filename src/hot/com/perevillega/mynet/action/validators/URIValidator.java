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