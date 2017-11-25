package api_test.abdelfaical.com.apitest;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import api_test.abdelfaical.com.apitest.webservice.QueryResponse;
import api_test.abdelfaical.com.apitest.webservice.WebService;
import api_test.abdelfaical.com.apitest.webservice.WebServiceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataListAdapter dataListAdapter;
    private RelativeLayout parent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private List<UserModel> userModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        //We make initialisation
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        parent = (RelativeLayout) findViewById(R.id.parent);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        dataListAdapter = new DataListAdapter(DataListActivity.this, userModelList);

        progressDialog = new ProgressDialog(DataListActivity.this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Suppression en cours...");

        //We configure the recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataListAdapter);

        //We listen when user want to delete item
        dataListAdapter.setOnRequestToDeleteItem(new DataListAdapter.OnRequestToDeleteItem() {
            @Override
            public void onDeleteItem(int itemId) {
                progressDialog.show();
                deleteItem(itemId);
            }
        });

        //We call function to get all user from server
        swipeToRefreshData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeToRefreshData();
            }
        });
    }

    /**
     *This function get from server all user
     * @return
     */
    private void getUserFromServer(){
        WebService webService = WebServiceUtils.getWebserviceManager();
        Call<List<UserModel>> call = webService.getAllUser();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> userModels = response.body();
                if (userModels.size() > 0){
                    for (UserModel user: userModels){
                        userModelList.add(user);
                    }
                    dataListAdapter.notifyDataSetChanged();
                }
                else {
                    Snackbar.make(parent, "Le chargement a échoué. Merci de réessayer", Snackbar.LENGTH_LONG).show();
                }
                dismissSwipeToRefresh();
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                dismissSwipeToRefresh();
                Snackbar.make(parent, "Une erreur s'est produite. Verifier votre connexion et réessayer", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This function delete one item
     * @param itemId
     */
    private void deleteItem(final int itemId) {
        WebService webService = WebServiceUtils.getWebserviceManager();
        Call<QueryResponse> call = webService.deleteUser(itemId);
        call.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                if (response.body() != null && response.body().isSuccess()){
                    for (int i=0; i<userModelList.size(); i++){
                        UserModel userModel = userModelList.get(i);
                        if (userModel.getUserId() == itemId){
                            userModelList.remove(i);
                            return;
                        }
                        progressDialog.dismiss();
                        dataListAdapter.notifyDataSetChanged();
                    }
                } else {
                    progressDialog.dismiss();
                    Snackbar.make(parent, "La suppression a échoué. Merci de réessayer", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parent, "Une erreur s'est produite. Verifier votre connexion et réessayer", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This function refresh data when swipe action is done
     */
    public void swipeToRefreshData(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        getUserFromServer();
    }

    public void dismissSwipeToRefresh(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
