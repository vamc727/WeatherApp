package com.vamsi.weatherapp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vamsi.weatherapp.api.NetworkService;
import com.vamsi.weatherapp.data.DataServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility class that provides provides instances for NetworkService class and DataServiceImpl class
 */
public class Injection {
    public static NetworkService provideNetworkService(@NonNull Context context) {
        checkNotNull(context);
        return NetworkService.getInstance(context);
    }

    public static DataServiceImpl provideDataService(@NonNull Context context) {
        checkNotNull(context);
        return DataServiceImpl.getInstance(context);
    }
}
