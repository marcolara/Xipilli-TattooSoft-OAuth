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
package com.tattoosoft.resource.validation.controller;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.tattoosoft.business.exception.BindReflectionException;
import com.tattoosoft.business.exception.NonexistantResourceException;
import com.tattoosoft.business.util.ApplicationContextProvider;
import com.tattoosoft.resource.validation.exception.JQueryFormValException;
import com.tattoosoft.resource.validation.exception.JQueryPropertyValException;
import com.tattoosoft.resource.validation.model.FormValResponse;
import com.tattoosoft.resource.validation.model.PropertyValResponse;

/**
 * @author mlara
 *
 */
public class AbstractValController {
	public static final Log		logger			= LogFactory.getLog(AbstractValController.class);
	public static final String	LINKED_FORM_ID	= "fieldId";
	public static final String	LINKED_FORM_VAL	= "fieldValue";

	/****** Services & Support *******************************************************************/

	@Autowired
	private ValidatorFactory	validatorFactory;

	/****** Support Getters **********************************************************************/

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}

	public Validator getValidator() {
		return (SpringValidatorAdapter) getValidatorFactory();
	}

	/****** Binders ******************************************************************************/
	@InitBinder("form")
	public void formClassNameToFormObjectBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Object.class, new PropertyEditorSupport() {
			public void setAsText(String objectType) throws NonexistantResourceException {
				String formBeanName = new StringBuilder(objectType).toString();
				try {
					setValue(ApplicationContextProvider.getApplicationContext().getBean(formBeanName));
				} catch (BeansException e) {
					logger.warn(String.format("Unable to resolve form bean for %s.", formBeanName), e);
					throw new BindReflectionException(e);
				}
			}
		});
	}

	/****** Exception Handlers *******************************************************************/

//	@ExceptionHandler({BindReflectionException.class,/**/
//	})
//	@ResponseBody
//	public JQueryPropertyValResponse handleBindReflectionFailure(BindReflectionException exception) {
//		// propagate a 500 error
//		// TODO: improve handling of this.
//		throw exception;
//	}

	@ExceptionHandler({JQueryPropertyValException.class,})
	@ResponseBody
	public PropertyValResponse handleValidationFailure(JQueryPropertyValException valException) {
		return valException.getContext();
	}

	@ExceptionHandler({JQueryFormValException.class,})
	@ResponseBody
	public FormValResponse handleValidationFailure(JQueryFormValException valException) {
		return valException.getContext();
	}

	/****** Model Attributes *********************************************************************/

	/****** Helpers ******************************************************************************/

	protected final <T> void validateProperty(T form, String fieldId, String fieldValue) throws JQueryPropertyValException {
		// set the test value on the property in the runtime form object.
		_populateForm(form, fieldId, fieldValue);

		// run validation
		Set<ConstraintViolation<T>> violations = getValidator().validateProperty(form, fieldId);

		// manage validation errors
		if (!violations.isEmpty()) {
			logger.debug(String.format("JQuery property validation failure on %s.%s, errors: %s", form.getClass().getSimpleName(), fieldId, violations));
			String msg = null;
			for ( ConstraintViolation<T> v : violations){
				msg = v.getMessageTemplate();
			}
			// will bubble up to the above @ExceptionHandler
			throw new JQueryPropertyValException(PropertyValResponse.forFailure(fieldId, msg));
		}
	}

	protected final <T> void validateForm(LinkedHashMap<String, String> request, T form) throws JQueryPropertyValException {
		// populate the form for validation
		Iterator<String> it = request.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			_populateForm(form, key, request.get(key));
		}

		// run validation
		Set<ConstraintViolation<T>> violations = getValidator().validate(form, Default.class);

		// manage validation errors
		if (!violations.isEmpty()) {
			logger.debug(String.format("JQuery form validation failure on %s, errors: %s", form.getClass().getSimpleName(), violations));
			throw new JQueryFormValException(FormValResponse.forFailure(violations));
		}
	}

	private <T> void _populateForm(T form, String fieldId, String fieldValue) {
		// set the test value on the property in the runtime form object.
		try {
			int firstLen = fieldId.offsetByCodePoints(0, 1);
			String initialCapped = fieldId.substring(0, firstLen).toUpperCase().concat(fieldId.substring(firstLen));
			Method mth = form.getClass().getMethod("set" + initialCapped, String.class);
			mth.invoke(form, fieldValue);
		} catch (NoSuchMethodException e) {
			logger.warn(String.format("Unable to resolve [property=%s] on [class=%s].", fieldId, form.getClass().getSimpleName()), e);
			//throw new BindReflectionException(e);
		} catch (IllegalAccessException e) {
			logger.error(String.format("Unable to resolve [property=%s] on [class=%s].", fieldId, form.getClass().getSimpleName()), e);
			//throw new BindReflectionException(e);
		} catch (InvocationTargetException e) {
			logger.error(String.format("Unable to resolve [property=%s] on [class=%s].", fieldId, form.getClass().getSimpleName()), e);
			//throw new BindReflectionException(e);
		}
	}
}
