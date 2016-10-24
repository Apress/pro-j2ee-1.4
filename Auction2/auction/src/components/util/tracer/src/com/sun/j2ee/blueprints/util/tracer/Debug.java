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
 $Id: Debug.java,v 1.4 2002/11/21 21:41:56 inder Exp $ */

package com.sun.j2ee.blueprints.util.tracer;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class is just a helper class to make it handy
 * to print out debug statements
 */
public final class Debug {
    
    private static Logger logger = Logger.getLogger("com.sun.j2ee.blueprints");
    
    public static void print(String msg) {
        logger.log(Level.INFO, msg);
    }
    
    public static void print(Exception e, String msg) {
        print((Throwable)e, msg);
    }
    
    public static void print(Exception e) {
        print(e, null);
    }
    
    public static void print(Throwable t, String msg) {
        logger.log(Level.WARNING, "Received throwable with Message: "+t.getMessage());
        if (msg != null)
            logger.log(Level.WARNING, msg);
        t.printStackTrace();
    }
    
    public static void print(Throwable t) {
        print(t, null);
    }
}
