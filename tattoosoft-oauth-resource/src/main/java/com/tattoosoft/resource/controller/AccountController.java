/**
 * 
 */
package com.tattoosoft.resource.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tattoosoft.business.form.ForgotPasswordForm;
import com.tattoosoft.business.service.RegistrationService;
import com.tattoosoft.business.web.rest.RestResponse;

/**
 * @author marcolara
 *
 */
@RestController
@RequestMapping(AccountController.REQUEST_MAPPING)
public class AccountController {
	public static final String REQUEST_MAPPING = "/account";
	
	@Autowired
	RegistrationService registrationService;
	
	// Handeling the reset of password
    @RequestMapping(method = RequestMethod.PUT)
    public RestResponse<Object> resetPassword(@Valid @RequestBody ForgotPasswordForm form, BindingResult result) throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException {
    	if (result.hasErrors()){
			throw new MethodArgumentNotValidException(new MethodParameter(this.getClass().getDeclaredMethod("resetPassword", ForgotPasswordForm.class, BindingResult.class), 0), result);
    	}
    	registrationService.recoverPasswordByEmail(form.getEmailAddress());
        return RestResponse.forSuccess();
    }
}
