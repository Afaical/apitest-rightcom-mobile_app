package api_test.abdelfaical.com.apitest.webservice;

import android.support.annotation.Nullable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abdel-faical on 24/11/17.
 */

public class WebServiceUtils {

    public static WebService getWebserviceManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebService.URL_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebService.class);
    }
}