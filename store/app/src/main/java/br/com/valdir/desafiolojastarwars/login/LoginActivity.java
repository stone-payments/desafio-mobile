package br.com.valdir.desafiolojastarwars.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.valdir.desafiolojastarwars.MainActivity;
import br.com.valdir.desafiolojastarwars.R;

public class LoginActivity extends AppCompatActivity {

    // Email, password edittext
    EditText txtUsername, txtPassword;

    // login button
    Button btnLogin;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManagement session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManagement(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();


                if(username.trim().length() > 0 && password.trim().length() > 0){
                    // Só para testes
                    // username = teste
                    // password = teste
                    if(username.equals("teste") && password.equals("teste")){

                        // Cria a sessão de login
                        session.createLoginSession(username, username + "@gmail.com");

                        // Staring MainActivity
                        finish();

//                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(i);
//                        finish();

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(LoginActivity.this, "Erro de Login ...", "Username/Password está errado", false);
                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Erro de Login ...", "Por favor, entre com o usuário e a senha", false);
                }

            }
        });
    }
}