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

/**
 * The AMMConstants class stores market-related constants for this package.
 * 
 * Additional notes:
 *  SlideMe can accept market URLs, but if the developer ID differs from that
 *   on Google Play it won't be found.
 *   
 * @author Tim Mackenzie - Simplify Now, LLC
 * @since Android API 3
 * @version 1.0.2
 */
public class AMMConstants {
    /*
     * If experiencing trouble with this package, enable this debug flag to 
     *  turn on additional logging.
     */
    public static final boolean DEBUG_ENABLED = false;
    
    /*
     * These constants trigger the behavior for the specified Android app market
     * Suggested use is to define a constant in the app using this package,
     *  and then using that constant everywhere that market-specific behavior
     *  is desired.
     *  
     * Not using an enum, which allows defining additional market constants in
     *  derivative projects.  Unknown markets will behave the same as 
     *  MARKET_SELECTOR_NONE in the methods in AMMLinks.
     *  
     * public static final int MARKET_SELECTOR = AMMConstants.MARKET_SELECTOR_GOOGLE;
     */
    public static final int MARKET_SELECTOR_NONE        = 0;
    public static final int MARKET_SELECTOR_GOOGLE      = 1;
    public static final int MARKET_SELECTOR_AMAZON      = 2;
    public static final int MARKET_SELECTOR_NOOK        = 3;
    public static final int MARKET_SELECTOR_BLACKBERRY  = 4;
    public static final int MARKET_SELECTOR_SAMSUNG     = 5;
    
    /*
     * The Kindle Fire identifies itself as such.
     * The Amazon manufacturer ID may be needed for future Amazon devices.
     */
    public static final String DEVICE_MANUFACTURER_AMAZON   = "Amazon";
    public static final String DEVICE_MODEL_KINDLE_FIRE     = "Kindle Fire";
    
    /*
     * Google Play URLs are constructed:
     *  <prefix><appID>
     *  <prefix><vendorID>
     *  
     * The App ID is your package name.
     *  
     * The vendor name string must use formatting characters, such as:
     *  Entire phrase must be quoted (%22)
     *  Spaces (%20)
     *  Commas (%2C)
     */
    public static final String MARKET_URL_SEARCH_PREFIX     = "market://search?q="; // append vendor name string, using %20 and other formatting chars as appropriate
    public static final String MARKET_URL_APP_PREFIX        = "market://details?id=";
    public static final String MARKET_URL_APP_PREFIX_WEB    = "http://play.google.com/store/apps/details?id=";
    
    /*
     * Amazon Appstore URLs constructed as such:
     *  <prefix><type><identifier>[<postfix>]
     * 
     * Note that there there is a direct URL for each app as well, which uses 
     *  the Amazon ASIN:
     *  http://www.amazon.com/gp/product/<ASIN>
     * 
     * Prefix options:
     *  It appears that the http and amzn prefix strings operate with the
     *      same parameters.  
     *  The HTTP version gives users a choice to view in the browser or in the
     *      Amazon Appstore app.  The direct method (amzn://) goes directly to
     *      the Amazon Appstore app if it is installed, and is now the default
     *      behavior.  If having trouble with the direct method, set 
     *      AMAZON_USE_HTTP to true to use the HTTP prefix. 
     * 
     * Type options:
     *  s       - search by term
     *  p       - package name (explicit)
     *  asin    - ASIN for a specific app (as seen on the Amazon product page)
     * 
     * The identifier can be either the app package name or the vendor ID.
     * The vendor ID should be formatted (spaces are OK, but use formatting 
     *  characters for quotes or commas).  There are some reports that you
     *  should use formatting characters for spaces as well.
     *  
     * Postfix options:
     *  "&showAll=1" - show multiple results (for use with 'p' type)
     *      All apps from vendor will be shown, with the selected app at the top
     *  "&t=myassociatetag" - add an associate tag to the link 
     *      Replace 'myassociatetag' with your own
     *      Note that app sales are currently excluded from Associates program
     *  "&ref=myreftag" - add a referral tag
     *      Not sure what this is for, but it was listed by an Amazon employee
     *      
     *  Some of the preceding is from Amazon employee comments in this thread:
     *      https://forums.developer.amazon.com/forums/thread.jspa?threadID=91&tstart=0
     *      
     *  Note that AMMLinks currently doesn't use the asin, associate, or 
     *      referral options.  It may in the future.
     */
    public static final boolean AMAZON_USE_HTTP              = false;
    public static final String  AMAZON_URL_PREFIX_COMMON     = "http://www.amazon.com/gp/mas/dl/android?";
    public static final String  AMAZON_AMZ_PREFIX_COMMON     = "amzn://apps/android?";
    public static final String  AMAZON_URL_TYPE_APP          = "p=";
    public static final String  AMAZON_URL_TYPE_SEARCH       = "s=";
    public static final String  AMAZON_URL_TYPE_ASIN         = "asin=";
    public static final String  AMAZON_URL_POSTFIX_SHOWALL   = "&showAll=1";
    public static final String  AMAZON_URL_POSTFIX_ASSOCIATE = "&t=";
    public static final String  AMAZON_URL_POSTFIX_REFERRAL  = "&ref=";
 
    /*
     * Nook URLs
     * EAN is numeric
     *  <prefix><EAN>
     */
    public static final String NOOK_MARKET_INTENT       = "com.bn.sdk.shop.details";
    public static final String NOOK_MARKET_EXTRA_NAME   = "product_details_ean";
    public static final String NOOK_URL_PREFIX_WEB      = "http://nookdeveloper.barnesandnoble.com/tools/dev/linkManager/";
    
    /*
     * BlackBerry URLs
     * appID and vendorID are numeric
     *  <prefix><appID>
     *  <prefix><vendorID>
     *  
     * NOTE as of July 2012, the appworld:\\ links don't appear to work at all.  
     *  The web links will work in an email (which launches the BB browser 
     *  properly) but will not work when launched from an Android app.
     * Reverting to Google Play linking for individual apps and for search by dev.
     */
    public static final String BLACKBERRY_URL_VENDOR_ALL_PREFIX     = "appworld://vendor/";
    public static final String BLACKBERRY_URL_VENDOR_ALL_WEB_PREFIX = "https://appworld.blackberry.com/webstore/vendor/";
    public static final String BLACKBERRY_URL_PREFIX                = "appworld://content/";
    public static final String BLACKBERRY_URL_PREFIX_WEB            = "https://appworld.blackberry.com/webstore/content/";
    
    /*
     * Samsung Apps
     * Link to individual app:
     *  samsungapps://ProductDetail/your.package.name
     * Web link
     *  http://www.samsungapps.com/topApps/topAppsDetail.as?productId=<product_ID>
     * Search is possible, but not directly supported.  This may or may not find all apps by a developer
     *  http://www.samsungapps.com/topApps/topAppsList.as?mkt_keyword=<Developer Name>
     */
    public static final String SAMSUNG_URL_PREFIX           = "samsungapps://ProductDetail/";
    public static final String SAMSUNG_WEB_SEARCH_PREFIX    = "http://www.samsungapps.com/topApps/topAppsList.as?mkt_keyword=";
}