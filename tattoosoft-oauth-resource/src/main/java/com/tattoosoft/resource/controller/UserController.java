package com.tattoosoft.resource.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tattoosoft.business.form.ForgotPasswordForm;
import com.tattoosoft.business.web.rest.RestResponse;

@Controller
@RequestMapping(UserController.REQUEST_MAPPING)
public class UserController extends ExceptionControllerAdvice {
	public static final String REQUEST_MAPPING = "/user";
	
    @Autowired
    private TokenStore tokenStore;
    
    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.POST, value = "/password/reset")
    @ResponseBody
    public RestResponse<Object> postPasswordReset(@RequestBody @Valid ForgotPasswordForm form, BindingResult result) {
        return RestResponse.forSuccess();
    }
    
    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/extra")
    @ResponseBody
    public RestResponse<Object> getExtraInfo(OAuth2Authentication auth) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        return RestResponse.forSuccess(accessToken.getAdditionalInformation());
    }
}
