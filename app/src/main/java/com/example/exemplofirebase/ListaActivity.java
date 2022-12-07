package com.example.exemplofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaActivity extends AppCompatActivity {

    private Button btnSair;
    private Button btnInserir;
    private AdapterAluno adapter;
    private ListView listaAlunos;
    private EditText txtNome;
    private EditText txtNota1;
    private EditText txtNota2;
    private TextView lblUsuario;

    private DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth usuarios =  FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        txtNome = findViewById( R.id.txtNome);
        txtNota1 = findViewById( R.id.txtNota1);
        txtNota2 = findViewById( R.id.txtNota2);
        lblUsuario = findViewById( R.id.lblUsuario );
        btnSair = findViewById( R.id.btnSair);
        btnInserir = findViewById( R.id.btnInserir);
        listaAlunos = findViewById( R.id.lista );

        lblUsuario.setText(usuarios.getCurrentUser().getEmail());

        btnSair.setOnClickListener( new ListaActivity.EscutadorBotaoSair() );
        btnInserir.setOnClickListener( new ListaActivity.EscutadorBotaoInserir() );

        // Referência para o nó principal deste exemplo:
        DatabaseReference cont27 = BD.child( "exercicioLogin" );

        // Criando objeto com parâmetros para o adapter:
        FirebaseListOptions<Aluno> opt = new FirebaseListOptions.Builder<Aluno>().setLayout(R.layout.item_lista).setQuery(cont27, Aluno.class).setLifecycleOwner(this).build();

        // Criando o objeto adapter (usando os parâmetros criados acima):
        adapter = new AdapterAluno(opt);

        // Colocando o adapter no ListView:
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        // Vazio pra desabilitar o back
    }

    private class EscutadorBotaoSair implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            Toast.makeText(ListaActivity.this, "Tentando deslogar no Firebase...", Toast.LENGTH_SHORT).show();
            // Verifica se existe um usuário logado:
            if ( usuarios.getCurrentUser() == null ) {
                // Exibe mensagem que não tem usuário logado, em lblEstado:
                Toast.makeText(ListaActivity.this, "Não tem um usuário logado!!", Toast.LENGTH_SHORT).show();
            }
            else {
                // Existe usuário logado.
                // Deslogando...
                usuarios.signOut();
                // Exibe mensagem de usuário deslogado:
                Toast.makeText(ListaActivity.this, "Usuário deslogado!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private class EscutadorBotaoInserir implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // Variáveis auxiliares:
            String nome, id;
            Double nota1, nota2;

            // Referência para o nó principal deste exemplo:
            DatabaseReference exeLogin = BD.child("exercicioLogin");

            // Pegando os dados digitados nas caixas.
            //nome
            nome = txtNome.getText().toString();

            //notas
            nota1 = Double.parseDouble(txtNota1.getText().toString());
            nota2 = Double.parseDouble(txtNota2.getText().toString());

            // Gerando um nó aleatório, que será utilizado como "chave" para
            // os dados deste usuário (como se fosse a "chave" do registro na tabela.
            // OBS:  - método push() :   gera o valor aleatório.
            //       - método getKey() : devolve o valor gerado, pra podermos usar.
            id = exeLogin.push().getKey();

            // Criando uma intancia de produto
            Aluno aln = new Aluno(id, nome, nota1, nota2);

            // Enfim, gravando os dados deste usuário "debaixo" deste nó gerado:
            exeLogin.child(id).setValue(aln);

            //limpando campos
            txtNome.setText("");
            txtNota1.setText("");
            txtNota2.setText("");

        }
    }

    private class AdapterAluno extends FirebaseListAdapter<Aluno> {
        public AdapterAluno(FirebaseListOptions options) {
            super(options);
        }

        // Método que coloca dados ("povoa") a View (o desenho) do item da lista.
        // Recebe o objeto com os dados (vindos do Firebase), e a View já inflada.
        // Basta acessarmos os dados (nome e email) e colocarmos nos objetos corretos
        // dentro da View.
        @Override
        protected void populateView(View v, Aluno a, int position) {
            TextView lblNome = v.findViewById(R.id.lblNome);
            TextView lblNota1 = v.findViewById(R.id.lblNota1);
            TextView lblNota2 = v.findViewById(R.id.lblNota2);
            TextView lblMedia = v.findViewById(R.id.lblMedia);

            // Coloca dados do objeto Contato (c) nesses objetos gráficos:
            lblNome.setText(a.getNome());
            lblNota1.setText(String.valueOf(a.getNota1()));
            lblNota2.setText(String.valueOf(a.getNota2()));
            lblMedia.setText(String.valueOf(a.calculaMedia()));
        }
    }

}