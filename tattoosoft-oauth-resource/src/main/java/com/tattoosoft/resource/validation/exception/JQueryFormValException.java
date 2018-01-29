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
package com.tattoosoft.resource.validation.exception;

import com.tattoosoft.business.exception.AbstractContextualException;
import com.tattoosoft.business.exception.ContextualException;
import com.tattoosoft.resource.validation.model.FormValResponse;

/**
 * @author malara
 *
 */
@SuppressWarnings("serial")
public class JQueryFormValException extends AbstractContextualException implements ContextualException<FormValResponse> {

    public JQueryFormValException(FormValResponse response) {
	super(response);
    }

    public JQueryFormValException(FormValResponse response, Throwable cause) {
        super(response, cause);
    }

    public FormValResponse getContext() {
        return (FormValResponse)context;
    }
}
