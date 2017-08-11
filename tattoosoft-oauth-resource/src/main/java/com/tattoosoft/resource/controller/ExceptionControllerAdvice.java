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
 **  @version ecomzero-rest 1.0
 **  (C) Copyright 2011 Ecomzero.com, All rights reserved.
 **
 **/
package com.tattoosoft.resource.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tattoosoft.business.web.rest.RestResponse;

/**
 * @author malara
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	private static final Log logger	= LogFactory.getLog(ExceptionControllerAdvice.class);

	/****** Services & Support *******************************************************************/

	/****** Support Getters **********************************************************************/

	/****** Binders ******************************************************************************/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestResponse<FieldError[]> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return RestResponse.forError(ex.getBindingResult());
	}
	
	@ExceptionHandler({RuntimeException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public RestResponse<FieldError[]> handleValidationFailure(RuntimeException ex) {
		return RestResponse.forRuntimeExceptionError(ex);
	}
}
