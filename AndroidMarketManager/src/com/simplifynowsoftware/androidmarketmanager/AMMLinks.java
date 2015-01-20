/*
 * Copyright (C) 2011-2012 Simplify Now, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.simplifynowsoftware.androidmarketmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * The AMMLinks class exposes static functionality to:
 *  Link to all apps by a developer
 *  Link to a specified app (does not need to be current app)
 *  
 * All linking is done to the specified app market.
 * Linking to a market not available from the current device will fail.
 * 
 * @author Tim Mackenzie - Simplify Now, LLC
 * @since Android API 3
 * @version 1.1.2
 */
public class AMMLinks {
    /**
     * marketShowAll - link to the developer's page showing all available apps.
     * <br>
     * If any required information is not present, defaults will be used if
     *  possible.
     * <br>
     * @param context              the context context to perform this operation within
     * @param marketSelector       numeric identifier for the app market to link to
     * @param googleDeveloperID    developer ID for Google Play
     * @param amazonPackageID      package ID for an app released on the Amazon Appstore by same developer
     * @param bbDeveloperID        developer ID for BlackBerry Appworld
     * @param developerName        name used to search for developer in app markets
     */
    public static void marketShowAll(   final Context context, 
                                        final int marketSelector, 
                                        final String googleDeveloperID, 
                                        final String amazonPackageID,
                                        final String bbDeveloperID,
                                        final String samsungDeveloperID,
                                        final String developerName) {               
        if(marketSelector == AMMConstants.MARKET_SELECTOR_NOOK) {
            /*
             * The Nook Store doesn't currently support linking to all apps
             * from a particular developer, only to individual apps.
             * 
             * A future release of this package is planned to allow a
             * custom callback in the case of Nook, for developers who have
             * written a custom view showing their apps.
             */
            showMarketMessage( context, developerName );
        } else {
            String marketUrl = "";
            
            /*
             * Generate the market URL for Google, Amazon, and BlackBerry.
             * See AMMConstants for details on URL construction for each market
             */
            if(marketSelector == AMMConstants.MARKET_SELECTOR_AMAZON) {
                if(AMMConstants.AMAZON_USE_HTTP) {
                    marketUrl =   AMMConstants.AMAZON_URL_PREFIX_COMMON;
                } else {
                    marketUrl =   AMMConstants.AMAZON_AMZ_PREFIX_COMMON;
                }
                
                if(null == amazonPackageID) {
                    /*
                     *  Fallback - find by searching from the current package name.
                     *  This will NOT find anything if the app has not been released,
                     *   even if the base package name is shared by your other apps.
                     */
                    marketUrl +=  AMMConstants.AMAZON_URL_TYPE_APP + context.getPackageName() 
                                + AMMConstants.AMAZON_URL_POSTFIX_SHOWALL;
                } else {
                    /*
                     *  Search for developer's apps
                     */
                    /* WARNING - some have reported that 'showall' with ASIN is more reliable.
                     *  That is not yet implemented here, but would be trivial to 
                     *  swap the URL as shown below, and passing the correct ASIN 
                     *  for the amazonPackageID value
                     */                   
//                    marketUrl +=  AMMConstants.AMAZON_URL_TYPE_ASIN + amazonPackageID /* pass ASIN to amazonPackageID */
//                            + AMMConstants.AMAZON_URL_POSTFIX_SHOWALL;
                    
                    marketUrl +=  AMMConstants.AMAZON_URL_TYPE_APP + amazonPackageID 
                            + AMMConstants.AMAZON_URL_POSTFIX_SHOWALL;
                }
            } else if(marketSelector == AMMConstants.MARKET_SELECTOR_GOOGLE){
                if(null == googleDeveloperID) {
                    /*
                     * Attempt to extract developer name from package name.  
                     * This might not work too well - it is better to set the
                     *  googleDeveloperID.
                     */
                    final String packageName = context.getPackageName();
                    final String developerPackage = packageName.substring(0, packageName.lastIndexOf('.'));
                    marketUrl =   AMMConstants.MARKET_URL_SEARCH_PREFIX 
                            + developerPackage;
                } else {
                    // Search by developer ID
                    marketUrl =   AMMConstants.MARKET_URL_DEVSEARCH_PREFIX 
                                + googleDeveloperID;
                }
            } else if(marketSelector == AMMConstants.MARKET_SELECTOR_BLACKBERRY){
                /*
                 * For BlackBerry Appworld, Google play URLs work, and so do 
                 *  the custom BlackBerry URLs.
                 * 
                 * History: for a while, the BlackBerry links all broke.  Then
                 *  the web one worked but the appworld:// urls didn't.
                 *  However, the web link opened a web browser instead of the
                 *  Appworld app, so apps couldn't be downloaded.
                 *  Now (January 2015), it appears that either the appworld:// 
                 *  URL or the HTTP:// URL can be used to the same effect.
                 *  Good thing too, since the Google Play URLs now find a lot
                 *  of garbage.
                 */ 
                marketUrl =   AMMConstants.BLACKBERRY_URL_VENDOR_ALL_PREFIX //BLACKBERRY_URL_VENDOR_ALL_WEB_PREFIX
                            + bbDeveloperID;
                /*
                 * As of 2014, using any of the Google Play searches brings
                 *  back a mountain of garbage.
                 */
//                marketUrl = AMMConstants.MARKET_URL_SEARCH_PREFIX 
//                        + developerName;
            } else if(marketSelector == AMMConstants.MARKET_SELECTOR_SAMSUNG){
                /*
                 * Previous Samsung URL to search on the web
                 */
//                marketUrl =   AMMConstants.SAMSUNG_WEB_SEARCH_PREFIX + "\""
//                            + developerName + "\"";
                
                marketUrl = AMMConstants.SAMSUNG_URL_VENDOR_ALL_PREFIX
                        + samsungDeveloperID;
            }
            
            if(marketUrl.equals("")) {
                /* 
                 * Failed to generate a URL for the selected market.  Tell user
                 *  how to find it themselves.
                 */
                showMarketMessage( context, developerName );
            } else {
                if(AMMConstants.DEBUG_ENABLED) {
                    Log.d("marketShowAll", "Launching URL: " + marketUrl);
                }
                
                final Uri uri = Uri.parse(marketUrl);
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                
                /* 
                 * No history.  If user leaves app and returns, don't remember
                 *  being in the market 
                 */
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    /* 
                     * Failed to load the URL for the selected market.  Tell 
                     *  user how to find it themselves.
                     */
                    showMarketMessage( context, developerName );
                    
                    if(AMMConstants.DEBUG_ENABLED) {
                        Log.e("marketShowAll", "Can't launch intent for URL: " + marketUrl, e);
                    }
                }
            }
        }
    }
    

    /**
     * ShowApp shows a single app in the appropriate market.
     * A few things this can be used for:<br>
     * -Show an upgrade (premium) app<br>
     * -Show the current app (e.g. to allow rating it)<br>
     * -Show a companion app that users should install<br>
     * <p>
     * Specify the app with appPackage, nookEAN, and bbID.
     * <p>
     * If necessary, use context.getPackageName() to obtain package name for
     *  current app.
     * <p>
     * Both nookEAN and bbID must come from the respective app stores, and are
     *  not known until the app is released to that store.  In the case of 
     *  BlackBerry, the bbVendorID can be provided to show all apps by 
     *  this developer.  If bbID is provided, bbVendorID is ignored.
     * <p>
     * Unknown values for the app package/ID can be left blank, but this will
     *  cause failure (the market message) if that market is selected.
     *  
     * @param context          The context context to perform this operation within
     * @param marketSelector   Numeric identifier for the app market to link to
     * @param appPackage       Full package name for Google and Amazon (e.g. com.x.x)
     * @param nookEAN          Numeric ID for app on Nook Store
     * @param bbID             Numeric ID for app on BlackBerry Appworld
     * @param bbVendorID       Numeric ID for vendor on BlackBerry Appworld
     * @param developerName    Name used to search for developer in app markets
     */
    public static void marketShowApp(   final Context context, 
                                        final int marketSelector, 
                                        final String appPackage, 
                                        final String nookEAN, 
                                        final String bbID,
                                        final String bbVendorID,
                                        final String developerName) {
        String marketUrl = "";
        
        /*
         * Generate the market URL for Google, Amazon, BlackBerry, and Nook.
         * See AMMConstants for details on URL construction for each market.
         * If any required values are null, the error message will be displayed
         */
        if(marketSelector == AMMConstants.MARKET_SELECTOR_AMAZON & null != appPackage) {
            if(AMMConstants.AMAZON_USE_HTTP) {
                marketUrl =   AMMConstants.AMAZON_URL_PREFIX_COMMON;
            } else {
                marketUrl =   AMMConstants.AMAZON_AMZ_PREFIX_COMMON;
            }
            
            marketUrl +=      AMMConstants.AMAZON_URL_TYPE_APP 
                            + appPackage;
        } else if(marketSelector == AMMConstants.MARKET_SELECTOR_GOOGLE & null != appPackage){
            marketUrl =   AMMConstants.MARKET_URL_APP_PREFIX 
                        + appPackage;
        } else if(marketSelector == AMMConstants.MARKET_SELECTOR_NOOK & null != nookEAN){
            // Just the EAN for Nook
            marketUrl = nookEAN;
        } else if(marketSelector == AMMConstants.MARKET_SELECTOR_BLACKBERRY){
            /*
             * Until BB linking works again, reverting to Google Play linking
             * Note that if your package name is different in BlackBerry, 
             *  you'll have to ensure the proper data is passed in for the
             *  packageName for that build.
             * January 2015 - it appears that the appworld:// URLs now work,
             *  and are needed for 'find all'.  They are not needed here for a
             *  single app though, so leaving as-is unless there's a problem.
             */
            if(null != appPackage){
                marketUrl =   AMMConstants.MARKET_URL_APP_PREFIX 
                        + appPackage;
            }
            // Original logic to restore later...
//            // Attempt to use bbID, then fall back to bbVendorID
//            if(null == bbID) {
//                marketUrl = AMMConstants.BLACKBERRY_URL_VENDOR_ALL_WEB_PREFIX
//                                    + bbVendorID;
//            } else {
//                marketUrl = AMMConstants.BLACKBERRY_URL_PREFIX + bbID;
//            }
        } else if(marketSelector == AMMConstants.MARKET_SELECTOR_SAMSUNG & null != appPackage){
            marketUrl =   AMMConstants.SAMSUNG_URL_PREFIX 
                        + appPackage;
        }
        
        if(marketUrl.equals("")) {
            /* 
             * Failed to generate a URL for the selected market.  Tell user
             *  how to find it themselves.
             */
            showMarketMessage( context, developerName );
        } else {
            Intent intent = null;
            
            // Special case for Nook market
            if(marketSelector == AMMConstants.MARKET_SELECTOR_NOOK) {
                intent = new Intent();
                intent.setAction(AMMConstants.NOOK_MARKET_INTENT); 
                intent.putExtra(AMMConstants.NOOK_MARKET_EXTRA_NAME, marketUrl);
            } else {
                final Uri uri = Uri.parse(marketUrl);
                intent = new Intent(Intent.ACTION_VIEW, uri);
            }

            /* 
             * No history.  If user leaves app and returns, don't remember
             *  being in the market 
             */
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                /* 
                 * Failed to load the URL for the selected market.  Tell 
                 *  user how to find it themselves.
                 */
                showMarketMessage( context, developerName );
                
                if(AMMConstants.DEBUG_ENABLED) {
                    Log.e("marketShowApp", "Can't launch intent for URL: " + marketUrl, e);
                }
            }
        }
    }
    
    /**
     * Generate error message for user, showing the developer's name to search for
     */
    public static String generateMarketMessage(final Context context, final String developerName) {
        return context.getString(R.string.market_not_found_message_prefix) + developerName + context.getString(R.string.market_not_found_message_postfix);
    }
    
    /**
     * showOkAlert shows a simple pop-up dialog with a single button to dismiss it.
     * <br>
     * If the user rotates the screen, this will be leaked unless the reference
     *  is managed.
     * @param           The context context to perform this operation within
     * @param message   The message to be displayed in the dialog box
     * @return          the Dialog object for tracking
     */
    protected static Dialog showMarketMessage(final Context context, final String developerName) {
        final String message = generateMarketMessage(context, developerName);
        
        final AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
        alt_bld.setCancelable(false);

        alt_bld.setMessage(message);

        alt_bld.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                // Action for 'OK' Button
                dialog.dismiss();
            }
        });

        final AlertDialog alert = alt_bld.create();
        alert.show();
        
        return alert;
    }
}