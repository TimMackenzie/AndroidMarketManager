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
 *  SlideMe can accept market URLs, but if the developer/package ID differs from
 *   that on Google Play it won't be found.
 *  Nokia X also accepts Google Play market URLs, with the same caveat about
 *   having an identical package ID.  Web links differ, of course.
 *   http://developer.nokia.com/community/wiki/Deep_Linking_to_Store_on_Nokia_X
 *   
 * @author Tim Mackenzie - Simplify Now, LLC
 * @since Android API 3
 * @version 1.0.3
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
     * Note that not all devices have a "1st Gen"; the Gen matches devices released at the same time. 
     * 
     * See https://developer.amazon.com/sdk/fire/specifications.html for additional details.
     * 
     * Values for android.os.Build.MODEL
     * 
     * Kindle Fire 1st Gen              - "Kindle Fire"
     * Kindle Fire 2nd Gen              - "KFOT"
     * Kindle Fire HD 7" 2nd Gen        - "KFTT"
     * Kindle Fire HD 7" 3rd Gen        - "KFSOWI"
     * Kindle Fire HDX 7" Wi-Fi 3rd Gen - "KFTHWI"
     * Kindle Fire HDX 7" Wan 3rd Gen   - "KFTHWA"
     * Kindle Fire HD 8.9" Wi-Fi        - "KFJWI"
     * Kindle Fire HD 8.9" WAN          - "KFJWA"
     * Kindle Fire HDX 8.9" Wi-Fi       - "KFAPWI"
     * Kindle Fire HDX 8.9" WAN         - "KFAPWA"
     * 
     * Fire Phone                       - "SD4930UR"
     * 
     * Fire HD 6 (4th Gen)              - TBD
     * Fire HD 7 (4th Gen)              - TBD
     * Fire HD 8.9 (4th Gen)            - TBD

     * 
     */
    public static final String DEVICE_MANUFACTURER_AMAZON   = "Amazon";
    public static final String DEVICE_MODEL_KINDLE_FIRE     = "Kindle Fire"; // 1st Gen only
    
    /*
     * Determine which Nook device the app is running on
     * Based on BN recommendations:
     *  https://nookdeveloper.zendesk.com/entries/20766627-android-os-build-model-and-build-product-information 
     * There is a little dissent as to if it is better to use Build.MODEL or 
     *  Build.PRODUCT.  However, until the HD line has known PRODUCT IDs, MODEL
     *  must be used.
     * A few PRODUCT IDs:
     *  Nook Color:         NOOKcolor
     *  Nook Tablet:        NOOKTablet
     *  Nook HD:            TBD
     *  Nook HD+:           TBD
     *  Samsung Galaxy Tab: degaswifiopenbnn 
     */
    public static final String DEVICE_MODEL_NOOK_COLOR          = "BNRV200";
    public static final String DEVICE_MODEL_NOOK_TABLET_16GB    = "BNTV250";
    public static final String DEVICE_MODEL_NOOK_TABLET_8GB     = "BNTV250a";
    public static final String DEVICE_MODEL_NOOK_HD             = "BNTV400";
    public static final String DEVICE_MODEL_NOOK_HDPLUS         = "BNTV600";
    public static final String DEVICE_MODEL_NOOK_SAMSUNG_TAB_4  = "SM-T230NU";
    
    /*
     * Google Play URLs are constructed:
     *  <prefix><appID>
     *  <prefix><vendorID>
     *  
     * The App ID is your package name.
     * A more precise vendor name will help prevent false matches.  
     * In some cases it might be better to use the DEVID prefix rather than the search.
     *  
     * The vendor name string must use formatting characters, such as:
     *  Entire phrase must be quoted (%22)
     *  Spaces (%20)
     *  Commas (%2C)
     */
    public static final String MARKET_URL_SEARCH_PREFIX     = "market://search?q="; // append vendor name string, using %20 and other formatting chars as appropriate
    public static final String MARKET_URL_DEVID_PREFIX      = "market://developer?id=";
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
     *  See the official documentation:
     *      https://developer.amazon.com/sdk/in-app-purchasing/sample-code/deeplink.html
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
     * Link to all apps by developer
     *  samsungapps://SellerDetail/your.vendor.id
     * Web link
     *  http://www.samsungapps.com/topApps/topAppsDetail.as?productId=<product_ID>
     * Search is possible, but not directly supported.  This may or may not find all apps by a developer
     *  http://www.samsungapps.com/topApps/topAppsList.as?mkt_keyword=<Developer Name>

     * Note that the SellerDetail vendor ID appears on the "Profile" tab in the Seller site as "Seller DeepLink"
     */
    public static final String SAMSUNG_URL_PREFIX               = "samsungapps://ProductDetail/";
    public static final String SAMSUNG_URL_VENDOR_ALL_PREFIX    = "samsungapps://SellerDetail/";
    public static final String SAMSUNG_WEB_SEARCH_PREFIX        = "http://www.samsungapps.com/topApps/topAppsList.as?mkt_keyword=";
}