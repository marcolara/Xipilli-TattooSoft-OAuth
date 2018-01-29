/***
 **  @(#) ecomzero.com
 **
 **  (C) Copyright 2011 Ecomzero.com, All rights reserved.
 **
 **
 **  THIS COMPUTER SOFTWARE IS THE PROPERTY OF Ecomzero.
 **
 **  This program code and all derivatives thereof are the sole property of
 **  Ecomzero.com.  Recipient and/or user, by accepting this source
 **  code, agrees that neither this source code nor any part thereof
 **  shall be reproduced, copied, adapted, distributed, used, displayed
 **  or transferred to any party, or used or disclosed to others for
 **  development, consulting, or any other purpose except as specifically
 **   authorized in writing by Ecomzero.com.
 **
 **  @version ecomzero-web 1.0
 **  (C) Copyright 2011 Ecomzero.com, All rights reserved.
 **
 **/
package com.tattoosoft.resource.validation.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;

/**
 * JQuery form validation response model. The expected API for a single field in
 * the response is a list of the properties: validateId, validateError, status
 * (boolean). This class is converted to that by its wired-in custom message
 * converter.
 */
public class FormValResponse {
	/**
	 * @param forFailure
	 */
	public static <T> FormValResponse forFailure(Set<ConstraintViolation<T>> violations) {
		FormValResponse response = new FormValResponse();
		for (ConstraintViolation<T> violation : violations) {
			response.addFailure(PropertyValResponse.forFailure(violation.getPropertyPath().toString(), violation.getMessage()));
		}
		return response;
	}

	/**
	 * @param forSucess
	 */
	public static FormValResponse forSuccess(LinkedHashMap<String, String> request) {
		FormValResponse response = new FormValResponse();
		Iterator<String> it = request.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			response.addSuccess(PropertyValResponse.forSuccess((String) key));
		}
		return response;
	}

	@NotNull
	private List<PropertyValResponse>	valResponseList	= new ArrayList<PropertyValResponse>();

	/**
	 * @return the valResponseList
	 */
	public List<PropertyValResponse> getValResponseList() {
		return valResponseList;
	}

	/**
	 * @param valResponseList
	 *            the valResponseList to set
	 */
	public void setValResponseList(List<PropertyValResponse> valResponseList) {
		this.valResponseList = valResponseList;
	}

	/**
	 * @param forFailure
	 */
	public void addFailure(PropertyValResponse forFailure) {
		valResponseList.add(forFailure);
	}

	/**
	 * @param addSuccess
	 */
	public void addSuccess(PropertyValResponse forSucess) {
		valResponseList.add(forSucess);
	}

	/**
	 * Convert to flat array of values as expected by JQuery validation engine
	 * v2.1.
	 *
	 * @return List
	 */
	public Object[] toJsonConversionObject() {
		Object[] results = new Object[valResponseList.size()];
		int i = 0;
		for (PropertyValResponse response : valResponseList) {
			results[i] = new Object[]{response.getFieldId(), response.isValid(), response.getErrorMessage()};
			i++;
		}
		return results;
	}

}
