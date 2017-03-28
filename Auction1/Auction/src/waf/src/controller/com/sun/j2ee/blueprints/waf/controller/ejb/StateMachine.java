/* Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 
 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 
 - Redistribution in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in
   the documentation and/or other materials provided with the
   distribution.
 
 Neither the name of Sun Microsystems, Inc. or the names of
 contributors may be used to endorse or promote products derived
 from this software without specific prior written permission.
 
 This software is provided "AS IS," without a warranty of any
 kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 
 You acknowledge that Software is not designed, licensed or intended
 for use in the design, construction, operation or maintenance of
 any nuclear facility.
 $Id: StateMachine.java,v 1.2 2002/11/19 20:37:38 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.ejb;

import java.rmi.RemoteException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.SessionContext;

import java.util.Collection;
import java.util.HashMap;

// waf imports
import com.sun.j2ee.blueprints.waf.controller.ejb.action.EJBAction;
import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.sun.j2ee.blueprints.waf.event.EventException;
import com.sun.j2ee.blueprints.waf.event.EventResponse;
import com.sun.j2ee.blueprints.waf.event.Event;

/**
 * This class is a responsible for processing Events recieved from the 
 * client tier. Af part of the WAF framework the events are generated
 * by web actions.
 * 
 * The State Machine ties all EJB components together dynamically at
 * runtime thus providing support for reusable components.
 * 
 * This class should not be updated to handle various event types.
 * This class will use ActionHandlers to handle events that require
 * processing beyond the scope of this class.
 *
 * The mapping of the event names to handlers is mangaged by the JNDI
 * key contained in the Event:getEventName() which is looked up from
 * an environment entry located in the EJB Deployment descriptor of the
 * EJBClientController. A second option to event handling is to do so
 * in the XML file.
 * 
 * State may be stored in the attributeMap
 *
 * 
 */
public class StateMachine implements java.io.Serializable {
    
    private EJBControllerLocalEJB ccejb;
    private HashMap attributeMap;
    private HashMap actionMap;
    private SessionContext sc;

    public StateMachine(EJBControllerLocalEJB ccejb, SessionContext sc) {
        this.ccejb = ccejb;
        this.sc = sc;
        attributeMap = new HashMap();
        actionMap = new HashMap();
    }

    public EventResponse processEvent(Event ev) throws EventException {
        String actionName = ev.getEJBActionClassName();
        EventResponse response = null;
        if (actionName  != null) {
            EJBAction action = null;
            try {
                 if (actionMap.get(actionName) != null) {
                    action = (EJBAction)actionMap.get(actionName);
                 } else {
                     action = (EJBAction)Class.forName(actionName).newInstance();
                     actionMap.put(actionName, action);
             }
            } catch (Exception ex) {
                System.err.println("StateMachine: error loading " + actionName + " :" + ex);
            }
            if (action != null) {
                action.init(this);
                // do the magic
                action.doStart();
                response = action.perform(ev);
                action.doEnd();
            }
        }
        return response;
    }
    
    public void setAttribute(String key, Object value) {
        attributeMap.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributeMap.get(key);
    }

    public EJBControllerLocalEJB getEJBController() {
        return ccejb;
    }

    public SessionContext getSessionContext() {
        return sc;
    }

}

