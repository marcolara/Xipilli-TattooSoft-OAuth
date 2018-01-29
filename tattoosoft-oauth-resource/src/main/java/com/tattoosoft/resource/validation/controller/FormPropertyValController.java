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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tattoosoft.resource.validation.model.FormValResponse;
import com.tattoosoft.resource.validation.model.PropertyValResponse;

/**
 * Validation service controller.
 */
@Controller
@RequestMapping("/valid/{form}")
public class FormPropertyValController extends AbstractValController {
    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    @Resource(name = "tokenStore")
    TokenStore tokenStore;
    
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public FormValResponse postForm(@RequestBody LinkedHashMap<String, String> request, @Valid @PathVariable Object form, BindingResult result) {
		validateForm(request, form);
		return FormValResponse.forSuccess(request);
	}
    
	@RequestMapping(method = RequestMethod.POST, value = "/{propertyName}")
	@ResponseBody
	public PropertyValResponse postField(@RequestBody LinkedHashMap<String, String> request, @Valid @PathVariable Object form, @PathVariable String propertyName, BindingResult result) {
		validateProperty(form, propertyName, request.get("value"));
		return PropertyValResponse.forSuccess(propertyName);
	}
}
