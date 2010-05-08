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
		System.out.println("IN");
		if(uri == null) return false;
		System.out.println("not null");
		if(!(uri instanceof String)) return false;
		System.out.println("string");
		try {
			new java.net.URL((String)uri);
			System.out.println("uri");
			return true;
		} catch (Exception e) {
			System.out.println("error: "+ e);
			return false;
		}		
	}

	@Override
	public void apply(Property arg0) {
		//do nothing here		
	}
}