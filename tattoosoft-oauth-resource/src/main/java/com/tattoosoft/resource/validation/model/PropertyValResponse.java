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
 **  @version 1.0
 **  (C) Copyright 2011 Ecomzero.com, All rights reserved.
 **
 **/
package com.tattoosoft.resource.validation.model;

import javax.validation.constraints.NotNull;


/**
 * JQuery field validation response model. The expected API for a single field
 * in the response is a list of the properties: validateId, validateError,
 * status (boolean). This class is converted to that by its wired-in custom
 * message converter.
 */
public class PropertyValResponse {

	public static final PropertyValResponse forSuccess(String fieldId) {
		PropertyValResponse valResponse = new PropertyValResponse(fieldId);
		valResponse.setValid(Boolean.TRUE);
		return valResponse;
	}

	public static final PropertyValResponse forFailure(String fieldId, String errorMessage) {
		PropertyValResponse valResponse = new PropertyValResponse(fieldId);
		valResponse.setValid(Boolean.FALSE);
		valResponse.setErrorMessage(errorMessage);
		return valResponse;
	}

	@NotNull
	private String	fieldId;

	@NotNull
	private boolean	valid;

	@NotNull
	private String	errorMessage;

	private PropertyValResponse(String fieldId) {
		this.setFieldId(fieldId);
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Convert to flat array of values as expected by JQuery validation engine
	 * v2.1.
	 *
	 * @return List
	 */
	public Object[] toJsonConversionObject() {
		return new Object[]{getFieldId(), isValid(), getErrorMessage()};
	}
}
