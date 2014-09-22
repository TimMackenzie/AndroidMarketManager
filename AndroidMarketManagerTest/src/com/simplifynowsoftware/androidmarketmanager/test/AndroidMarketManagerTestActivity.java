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

package com.simplifynowsoftware.androidmarketmanager.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.simplifynowsoftware.androidmarketmanager.AMMLinks;
import com.simplifynowsoftware.androidmarketmanager.AMMUtils;

/**
 * AndroidMarketManagerTestActivity is the interface to test the 
 *  AndroidMarketManager library.
 *   
 * @author Tim Mackenzie - Simplify Now, LLC
 * @since Android API 3
 * @version 1.0.1
 */
public class AndroidMarketManagerTestActivity extends Activity {
    
    /* 
     * Test data for the app Evernote.  Replace all of these constants to test
     *  a different app/vendor.
     * Note the use of formatting characters.
     */
    public static final String DEVELOPER_NAME = "Evernote";
    public static final String GOOGLE_DEVELOPER_ID = "%22Evernote+Corp.%22";
    public static final String AMAZON_DEVELOPER_ID = "Evernote%20Corp.";
    public static final String BB_DEVELOPER_ID = "581";
    public static final String APP_PACKAGE = "com.evernote";
    public static final String NOOK_EAN = "2940043353757";
    public static final String BBID = "56171";
    public static final String SAMSUNG_VENDOR_ID = "adevqewb3c"; // Using ID from Simplify Now, LLC - don't know the one for Evernote
    
    
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
    }
    
    /*
     * Return the index of the selected radio button within the market selector
     *  radio group.
     * Note that this requires the radio buttons to match the order of the 
     *  market selectors defined in AMMConstants. No conversion is performed.
     */
    protected int getRadioSelection() {
        final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup_marketselector);
       
        return rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));
    }
    
    /*
     * Show the app in the selected market
     */
    public void onShowApp(final View v) {      
        AMMLinks.marketShowApp( this, 
                                getRadioSelection(), 
                                APP_PACKAGE, 
                                NOOK_EAN, 
                                BBID,
                                BB_DEVELOPER_ID,
                                DEVELOPER_NAME);
    }
    
    /*
     * Show all apps for this vendor in the selected market
     */
    public void onShowAll(final View v) {
        AMMLinks.marketShowAll( this, 
                                getRadioSelection(), 
                                GOOGLE_DEVELOPER_ID, 
                                AMAZON_DEVELOPER_ID, 
                                BB_DEVELOPER_ID, 
                                SAMSUNG_VENDOR_ID,
                                DEVELOPER_NAME);
    }
    
    /*
     * Run the device checks from AMMUtils and report the results
     */
    public void onTestDevice(final View v) {
        @SuppressWarnings("deprecation")
        final boolean isKindleFire = AMMUtils.isDeviceKindleFire(this);
        final boolean isNook = AMMUtils.isDeviceNook(this);
        final boolean isNookCT = AMMUtils.isDeviceNookCT(this);
        final boolean isNookHD = AMMUtils.isDeviceNookHD(this);
        
        StringBuilder builder = new StringBuilder();
        
        builder.append(getString(R.string.message_kindlefire) + "\n\t" + isKindleFire + "\n\n");
        builder.append(getString(R.string.message_nook) + "\n\t" + isNook + "\n\n");
        builder.append(getString(R.string.message_nookct) + "\n\t" + isNookCT + "\n\n");
        builder.append(getString(R.string.message_nookhd) + "\n\t" + isNookHD + "\n\n");
        
        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
    }
}