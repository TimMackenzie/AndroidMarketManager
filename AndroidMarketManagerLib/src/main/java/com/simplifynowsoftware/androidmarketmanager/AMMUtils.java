/*
 * Copyright (C) 2012 Simplify Now, LLC
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

import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * AMMUtils contains standalone methods related to app markets.
 * 
 * @author Tim Mackenzie - Simplify Now, LLC
 * @since Android API 3
 * @version 1.0.2
 */
public class AMMUtils {  
    /**
     * Verify if this device is a Kindle Fire, based on the Build.MODEL
     *  
     * @param context - the context to run the check within
     * @return true if this device is the Kindle Fire
     * 
     * @deprecated don't use this for general detection of Amazon devices - this only detects the original Kindle Fire (Gen 1)
     */
    @Deprecated
    public static boolean isDeviceKindleFire(final Context context) {
        boolean isKindle = false;

        // Build.MANUFACTURER should also equal "Amazon"
        final String model = Build.MODEL;
        
        if(AMMConstants.DEVICE_MODEL_KINDLE_FIRE.equals(model)) {
            isKindle = true;
        }

        return isKindle;
    }
    

    /**
     * Verify if this device is any known flavor of Nook
     *  
     * @param context - the context to run the check within
     * @return true if this device is a known Nook device
     */
    public static boolean isDeviceNook(final Context context) {
        return ( isDeviceNookCT(context) || isDeviceNookHD(context) );
    }
    
    
    /**
     * Verify if this device is a Nook Color or Nook Tablet, based on the Build.MODEL
     *  Any other Nook or Non-Nook device will return false.
     *  
     * @param context - the context to run the check within
     * @return true if this device is a Nook Color or Nook Tablet
     */
    @Deprecated
    public static boolean isDeviceNookCT(final Context context) {
        boolean isCustom = false;

        final String model = Build.MODEL;
        
        if (    model.equals(AMMConstants.DEVICE_MODEL_NOOK_COLOR) ||       // BNRV200  = NOOK Color
                model.equals(AMMConstants.DEVICE_MODEL_NOOK_TABLET_16GB) || // BNTV250  = NOOK Tablet 16GB
                model.equals(AMMConstants.DEVICE_MODEL_NOOK_TABLET_8GB)) {  // BNTV250A = NOOK Tablet 8GB 
            if(AMMConstants.DEBUG_ENABLED) {
                    Log.e("isDeviceNookCT", "Device is Nook Color or Nook Tablet.  Model = " + model);
            }
            
            isCustom = true;
        } else {
            if(AMMConstants.DEBUG_ENABLED) {
                Log.e("isDeviceNookCT", "Unknown Nook Device.  Model = " + model);
            }
        }

        return isCustom;
    }
        
        
    /**
     * Verify if this device is a Nook HD or Nook HD+, based on the Build.MODEL
     *  Any other Nook or Non-Nook device will return false.
     *  
     * @param context - the context to run the check within
     * @return true if this device is a Nook HD or Nook HD+
     */
    public static boolean isDeviceNookHD(final Context context) {
        boolean isHD = false;

        final String model = Build.MODEL;
        
        if (    model.equals(AMMConstants.DEVICE_MODEL_NOOK_HD) ||      // BNTV400 = NOOK HD
                model.equals(AMMConstants.DEVICE_MODEL_NOOK_HDPLUS)) {  // BNTV600 = NOOK HD+
            if(AMMConstants.DEBUG_ENABLED) {
                Log.e("isDeviceNookHD", "Device is Nook HD or Nook HD+.  Model = " + model);
            }
            
            isHD = true;
        } else {
            if(AMMConstants.DEBUG_ENABLED) {
                Log.e("isDeviceNookHD", "Not a Nook HD Device.  Model = " + model);
            }
        }

        return isHD;
    }
}