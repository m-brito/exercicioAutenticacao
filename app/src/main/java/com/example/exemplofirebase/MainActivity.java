package com.example.exemplofirebase;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {
    // Nathalia Calza
    // Ana Luísa Crecca
    EditText txtEmail;
    EditText txtSenha;
    private Button btnEntrar;
    private Button btnCadastrar;

    private  FirebaseAuth usuarios =  FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmail = findViewById( R.id.txtEmail );
        txtSenha = findViewById( R.id.txtSenha );
        btnEntrar = findViewById( R.id.btnLogin );
        btnCadastrar = findViewById( R.id.btnCriar);

        // Criando e associando o escutador de cliques do botão "cadastrar":
        btnCadastrar.setOnClickListener( new EscutadorBotaoNovo() );
        btnEntrar.setOnClickListener( new EscutadorBotaoEntrar() );

        if ( usuarios.getCurrentUser() != null ) {
            Toast.makeText(MainActivity.this, "Tem usuário logado no sistema: " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent( getApplicationContext(), ListaActivity.class);

            startActivity(i);
        }
    }

    private class EscutadorBotaoNovo implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Aviso de início de processo:
            Toast.makeText(MainActivity.this, "Tentando criar novo usuário...", Toast.LENGTH_SHORT).show();

            // Verifica se o usuário já está logado:
            if (usuarios.getCurrentUser() != null) {

                // Exibe mensagem de usuário já logado, em lblEstado:
                Toast.makeText(MainActivity.this, "Já tem um usuário logado: " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent( getApplicationContext(), ListaActivity.class);

                startActivity(i);
            } else {
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                txtEmail.setText("");
                txtSenha.setText("");
                // Tenta criar o usuário:
                usuarios.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // Testa se criou o usuário com sucesso:
                        if (task.isSuccessful()) {
                            // Criou e logou com sucesso.
                            // Exibe mensagem em lblEstado:
                            Toast.makeText(MainActivity.this, "Usuário criado e logado c/ sucesso: " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent( getApplicationContext(), ListaActivity.class);

                            startActivity(i);
                        } else {
                            // Não conseguiu criar o usuário.
                            // Exibe mensagem em lblEstado:
                            Toast.makeText(MainActivity.this, "Criação do usuário falhou!", Toast.LENGTH_SHORT).show();
                            // Exibe a mensagem de erro do Firebase num Toast:
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(MainActivity.this, "ERRO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private class EscutadorBotaoEntrar implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Aviso de início de processo:
            Toast.makeText(MainActivity.this, "Tentando logar usuário...", Toast.LENGTH_SHORT).show();

            // Verifica se o usuário já está logado:
            if (usuarios.getCurrentUser() != null) {

                // Exibe mensagem de usuário já logado, em lblEstado:
                Toast.makeText(MainActivity.this, "Já tem um usuário logado: " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent( getApplicationContext(), ListaActivity.class);

                startActivity(i);
            } else {
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                txtEmail.setText("");
                txtSenha.setText("");
                // Tenta criar o usuário:
                usuarios.signInWithEmailAndPassword(email, senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // Testa se criou o usuário com sucesso:
                        if (task.isSuccessful()) {
                            // Criou e logou com sucesso.
                            // Exibe mensagem em lblEstado:
                            Toast.makeText(MainActivity.this, "Usuário logado c/ sucesso: " + usuarios.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent( getApplicationContext(), ListaActivity.class);

                            startActivity(i);
                        } else {
                            // Não conseguiu criar o usuário.
                            // Exibe mensagem em lblEstado:
                            Toast.makeText(MainActivity.this, "Login do usuário falhou!", Toast.LENGTH_SHORT).show();
                            // Exibe a mensagem de erro do Firebase num Toast:
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(MainActivity.this, "ERRO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}