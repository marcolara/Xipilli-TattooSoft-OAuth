/***
 **  @(#) ecomzero.com
 **
 **  Copyright (c) 2010 ecomzero, LLC.  All Rights Reserved.
 **
 **
 **  THIS COMPUTER SOFTWARE IS THE PROPERTY OF ecomzero, LLC.
 **
 **  Permission is granted to use this software as specified by the ecomzero
 **  COMMERCIAL LICENSE AGREEMENT.  You may use this software only for
 **  commercial purposes, as specified in the details of the license.
 **  ecomzero SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY THE LICENSEE
 **  AS A RESULT OF USING OR MODIFYING THIS SOFTWARE IN ANY WAY.
 **
 **  YOU MAY NOT DISTRIBUTE ANY SOURCE CODE OR OBJECT CODE FROM THE
 **  ecomzero.com TOOLKIT AT ANY TIME. VIOLATORS WILL BE PROSECUTED TO THE
 **  FULLEST EXTENT OF UNITED STATES LAW.
 **
 **  @version 1.0
 **  @author Copyright (c) 2010 ecomzero, LLC. All Rights Reserved.
 **
 **/
package com.tattoosoft.resource.validation.exception;

import com.tattoosoft.business.exception.AbstractContextualException;
import com.tattoosoft.business.exception.ContextualException;
import com.tattoosoft.resource.validation.model.PropertyValResponse;


/**
 *
 */
@SuppressWarnings("serial")
public class JQueryPropertyValException extends AbstractContextualException implements ContextualException<PropertyValResponse> {

    public JQueryPropertyValException(PropertyValResponse response) {
        super(response);
    }

    public JQueryPropertyValException(PropertyValResponse response, Throwable cause) {
        super(response, cause);
    }

    public PropertyValResponse getContext() {
        return (PropertyValResponse)context;
    }
}
