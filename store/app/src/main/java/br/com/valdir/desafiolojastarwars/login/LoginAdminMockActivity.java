package br.com.valdir.desafiolojastarwars.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.valdir.desafiolojastarwars.AdminActivity;
import br.com.valdir.desafiolojastarwars.R;

public class LoginAdminMockActivity extends AppCompatActivity {

    // Email, password edittext
    EditText txtUsernameAdmin, txtPasswordAdmin;

    // login button
    Button btnLoginAdmin;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManagement session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mock_login);

        // Session Manager
        session = new SessionManagement(getApplicationContext());

        // Email, Password input text
        txtUsernameAdmin = findViewById(R.id.txtUsernameAdmin);
        txtPasswordAdmin = findViewById(R.id.txtPasswordAdmin);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);


        // Login button click event
        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String usernameAdmin = txtUsernameAdmin.getText().toString();
                String passwordAdmin = txtPasswordAdmin.getText().toString();


                if(usernameAdmin.trim().length() > 0 && passwordAdmin.trim().length() > 0){
                    // Só para testes (mock)
                    // usernameAdmin = admin
                    // passwordAdmin = admin
                    if(usernameAdmin.equals("admin") && passwordAdmin.equals("admin")){

                        // Cria a sessão de login
                        session.createLoginSession(usernameAdmin, usernameAdmin + "@gmail.com");

                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(LoginAdminMockActivity.this, "Erro de Login ...", "Username/Password está errado", false);
                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginAdminMockActivity.this, "Erro de Login ...", "Por favor, entre com o usuário e a senha", false);
                }

            }
        });
    }
}