![Android Market Manager](https://github.com/TimMackenzie/AndroidMarketManager/raw/master/AndroidMarketManager.png)


Introduction
------------

The Android Market Manager library is a small library project for Android app developers to simplify interaction with various app markets.

Currently, it contains:

 * Methods to link to the appropriate app store for either a single app, or all of a developer's apps
 * Some library utilities for checking which device the app is running on


Also included is the test project, which is a handy way to check how linking to each market will work on a particular device.

Note that while documentation is currently light, the constants file contains a lot of description for how the linking occurs.  This may be useful for you even if you don't want to use the library itself.

For a description of available methods and what they are for, see 
http://www.projectjourneyman.com/linking-android-app-stores-android-market-manager

Beyond simply copying the code into your project there are two easy ways to bring it into Android Studio.

Including as a local library
------------

To reference it as a local module (but not a part of your project), add the following in your settings.gradle (substituting the location as appropriate)

    include ':AndroidMarketManagerLib'
    project(':AndroidMarketManagerLib').projectDir = new File(settingsDir, '../AndroidMarketManager/AndroidMarketManagerLib')

Next, add the following to the build.gradle for your module:

    dependencies {
        compile project(':AndroidMarketManagerLib')
    } 

Referencing directly from GitHub
------------
To reference this (or any) library or codebase on GitHub or a few other places (but not in a repo), try this.

First, your module's build.gradle must add a new repo for JitPack (the second line):

    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }

Next, add the dependency to the same build.gradle.  Note that the final string is the commit version; you can update this at JitPack.io by entering the GitHub project url and picking the latest commit.

    dependencies {    
        ...    
        // Use jitpack.io to capture https://github.com/TimMackenzie/AndroidMarketManager
        compile('com.github.TimMackenzie:AndroidMarketManager:bc272e410c') {
            exclude module: 'AndroidMarketManagerTest'
        }
    }

That's it!

