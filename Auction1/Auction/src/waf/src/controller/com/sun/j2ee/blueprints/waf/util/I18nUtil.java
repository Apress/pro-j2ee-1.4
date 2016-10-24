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
 $Id: I18nUtil.java,v 1.2 2002/11/19 20:37:42 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.util;

import java.util.Locale;
import java.util.Vector;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.BreakIterator;
import java.util.Locale;
import java.io.ByteArrayOutputStream;


import com.sun.j2ee.blueprints.util.tracer.Debug;

/**
 * This utility class for internationalization. This class provides a 
 * central location to do specialized formatting in both 
 * a default and a locale specfic manner.
 */
public final class I18nUtil extends Object {


    /**
     * Converts a String SJIS or JIS URL encoded hex encoding to a Unicode String
     *
    */
    public static String convertJISEncoding(String target) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (target == null) return null;
        String paramString = target.trim();

        for (int loop =0; loop < paramString.length(); loop++) {
            int i = (int)paramString.charAt(loop);
            bos.write(i);
        }
        String convertedString = null;
        try {
            convertedString =  new String(bos.toByteArray(), "JISAutoDetect");
        } catch (java.io.UnsupportedEncodingException uex) {}
        return convertedString;
    }
   
    
  public static String formatCurrency(double amount, int precision, String pattern, Locale locale){
  NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
  DecimalFormat df = (DecimalFormat)nf;
  df.setMinimumFractionDigits(precision);
  df.setMaximumFractionDigits(precision);
  df.setDecimalSeparatorAlwaysShown(true);
  df.applyPattern(pattern);
  return df.format(amount);
    }
    
    public static String formatNumber(double amount, int precision, String pattern, Locale locale){
  NumberFormat nf = NumberFormat.getNumberInstance(locale);
  DecimalFormat df = (DecimalFormat)nf;
  df.setMinimumFractionDigits(precision);
  df.setMaximumFractionDigits(precision);
  df.setDecimalSeparatorAlwaysShown(true);
  df.applyPattern(pattern);
  return df.format(amount);
    }

    public static String formatCurrency(double amount, int precision, Locale locale){
  NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        nf.setMinimumFractionDigits(precision);
  nf.setMaximumFractionDigits(precision);
  return nf.format(amount);
    }
    
    public static String formatNumber(double amount, int precision, Locale locale){
  NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMinimumFractionDigits(precision);
  nf.setMaximumFractionDigits(precision);
  return nf.format(amount);
    }

    public static Vector parseKeywords(String keywordString){
  if (keywordString != null){
      Vector keywords = new Vector();
      BreakIterator breakIt = BreakIterator.getWordInstance();
      int index=0;
      int previousIndex =0;
      breakIt.setText(keywordString);
      try{
    while(index < keywordString.length()){
        previousIndex = index;
        index = breakIt.next();
        String word = keywordString.substring(previousIndex, index);
        if (!word.trim().equals("")) keywords.addElement(word);
    }
    return keywords;
      } catch (Throwable e){
    Debug.print(e, "Error while parsing search string");
      }
  } 
  return null;
    }

    public static Vector parseKeywords(String keywordString, Locale locale){
  if (keywordString != null){
      Vector keywords = new Vector();
      BreakIterator breakIt = BreakIterator.getWordInstance(locale);
      int index=0;
      int previousIndex =0;
      breakIt.setText(keywordString);
      try{
    while(index < keywordString.length()){
        previousIndex = index;
        index = breakIt.next();
        String word = keywordString.substring(previousIndex, index);
        if (!word.trim().equals("")) keywords.addElement(word);
    }
    return keywords;
      } catch (Throwable e){
    Debug.print(e, "Error while parsing search string" );
      }

  }
  return null;
    }

    /**
     * Convert a string based locale into a Locale Object
     * <br>
     * <br>Strings are formatted:
     * <br>
     * <br>language_contry_variant
     *
     **/
    
    public static Locale getLocaleFromString(String localeString) {
        if (localeString == null) return null;
        if (localeString.toLowerCase().equals("default")) return Locale.getDefault();
        int languageIndex = localeString.indexOf('_');
        if (languageIndex  == -1) return null;
        int countryIndex = localeString.indexOf('_', languageIndex +1);
        String country = null;
        if (countryIndex  == -1) {
            if (localeString.length() > languageIndex) {
                country = localeString.substring(languageIndex +1, localeString.length());
            } else {
                return null;
            }
        }
        int variantIndex = -1;
        if (countryIndex != -1) countryIndex = localeString.indexOf('_', countryIndex +1);
        String language = localeString.substring(0, languageIndex);
        String variant = null;
        if (variantIndex  != -1) {

            variant = localeString.substring(variantIndex +1, localeString.length());
        }
        if (variant != null) {
            return new Locale(language, country, variant);
        } else {
            return new Locale(language, country);
        }
        
    }
}












