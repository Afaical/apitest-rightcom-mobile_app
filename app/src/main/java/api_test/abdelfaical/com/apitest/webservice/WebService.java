package api_test.abdelfaical.com.apitest.webservice;

import java.util.List;
import api_test.abdelfaical.com.apitest.UserModel;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abdel-faical on 24/11/17.
 */

public interface WebService {

    String URL_DATA = "https://apitest-rightcom.herokuapp.com/";


    @GET("admin/makeConnexion")
    Call<QueryResponse> makeConnexion(
            @Query("adminMail") String adminMail,
            @Query("adminPassword") String adminPassword
    );

    @GET("user/getAllUser")
    Call<List<UserModel>> getAllUser();

    @DELETE("user/deleteUser")
    Call<QueryResponse> deleteUser(
            @Query("userId") int userId
    );
}
