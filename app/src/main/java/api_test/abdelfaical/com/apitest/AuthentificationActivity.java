package api_test.abdelfaical.com.apitest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import api_test.abdelfaical.com.apitest.webservice.QueryResponse;
import api_test.abdelfaical.com.apitest.webservice.WebService;
import api_test.abdelfaical.com.apitest.webservice.WebServiceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthentificationActivity extends AppCompatActivity {

    private EditText edtMail;
    private EditText edtPassword;
    private Button btnConnexion;
    private Typeface typeface;
    private TextInputLayout til1;
    private TextInputLayout til2;
    private ProgressDialog progressDialog;
    private RelativeLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        edtMail = (EditText)findViewById(R.id.edtMail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnConnexion = (Button)findViewById(R.id.btnConnexion);
        til1 = (TextInputLayout)findViewById(R.id.til1);
        til2 = (TextInputLayout)findViewById(R.id.til2);
        parent = (RelativeLayout)findViewById(R.id.parent);

        progressDialog = new ProgressDialog(AuthentificationActivity.this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Connexion en cours...");

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Regular.ttf");
        edtMail.setTypeface(typeface);
        edtPassword.setTypeface(typeface);
        btnConnexion.setTypeface(typeface);
        til1.setTypeface(typeface);
        til2.setTypeface(typeface);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtMail.getText().toString().length() == 0 && edtMail.getText().toString().equals("")){
                    edtMail.setError("Votre mail svp");
                }
                else if (edtPassword.getText().toString().length() == 0 && edtPassword.getText().toString().equals("")){
                    edtPassword.setError("Votre mot de passe svp");
                }
                else {
                    progressDialog.show();
                    makeConnexion(edtMail.getText().toString(), edtPassword.getText().toString());
                }
            }
        });
    }

    private void makeConnexion(String mail, String password){
        WebService webService = WebServiceUtils.getWebserviceManager();
        Call<QueryResponse> call = webService.makeConnexion(mail, password);
        call.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                if (response.body() != null  && response.body().isSuccess()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(AuthentificationActivity.this, DataListActivity.class);
                    startActivity(intent);
                    AuthentificationActivity.this.finish();
                } else {
                    progressDialog.dismiss();
                    Snackbar.make(parent, "Désolé vous n'êtes pas un administrateur", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parent, "Une erreur s'est produite. Verifier votre connexion et réessayer", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
