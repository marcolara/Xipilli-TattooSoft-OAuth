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
 * JQuery field validation request model.  The validation engine sends a GET request with the
 * properties: validateId, validateValue, validateError.  We model that here.
 */
public class PropertyValRequest {

    public String validateValue;

    @NotNull
    public String validateId;

    @NotNull
    public String validateError;

    public String getValidateValue() {
        return validateValue;
    }

    public void setValidateValue(String validateValue) {
        this.validateValue = validateValue;
    }

    public String getValidateId() {
        return validateId;
    }

    public void setValidateId(String validateId) {
        this.validateId = validateId;
    }

    public String getValidateError() {
        return validateError;
    }

    public void setValidateError(String validateError) {
        this.validateError = validateError;
    }
}
