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

/**
 * AMMUtils contains standalone methods related to app markets.
 * 
 * @author Tim Mackenzie - Simplify Now, LLC
 * @since Android API 3
 * @version 1.0.0
 */
public class AMMUtils {  
    /**
     * Verify if this device is a Kindle Fire, based on the Build.MODEL
     *  
     * @param context - the context to run the check within
     * @return true if this device is the Kindle Fire
     */
    public static boolean isDeviceKindleFire(final Context context) {
        boolean isKindle = false;

        // Build.MANUFACTURER should also equal "Amazon"
        final String model = Build.MODEL;
        
        if(AMMConstants.DEVICE_MODEL_KINDLE_FIRE.equals(model)) {
            isKindle = true;
        }

        return isKindle;
    }
}