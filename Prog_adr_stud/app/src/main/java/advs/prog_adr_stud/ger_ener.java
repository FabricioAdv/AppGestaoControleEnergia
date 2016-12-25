package advs.prog_adr_stud;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ger_ener extends AppCompatActivity {

    String appIp = "http://192.168.111.100:8090/";

    TextView ener, cor, cons, teste;

    String recebe_ener, recebe_cor, recebe_cons;

    Button atualizar;

    int min_recebe_ener = 354;
    int max_recebe_ener = min_recebe_ener +3;

    int min_recebe_cor = max_recebe_ener +2;
    int max_recebe_cor = min_recebe_cor +7;

    int min_recebe_cons = max_recebe_cor +2;
    int max_recebe_cons = min_recebe_cons +7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ger_ener);

        ener = (TextView)findViewById(R.id.textView);
        cor = (TextView)findViewById(R.id.textView2);
        cons = (TextView)findViewById(R.id.textView3);

        teste = (TextView)findViewById(R.id.textView44);

        atualizar = (Button)findViewById(R.id.button);

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita(" ");
            }
        });
    }

    public void solicita (String comando){

        String url = appIp + comando;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(url);
        } else {
            Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                Conexao conexao = new Conexao();

                return conexao.downloadDados(urls[0]);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro, a URL pode ser inválida", Toast.LENGTH_LONG).show();
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            teste.setText(resultado);

            recebe_ener = resultado.substring(min_recebe_ener, max_recebe_ener);
            recebe_cor = resultado.substring(min_recebe_cor, max_recebe_cor);
            recebe_cons = resultado.substring(min_recebe_cons, max_recebe_cons);

            ener.setText(recebe_ener);
            cor.setText(recebe_cor);
            cons.setText(recebe_cons);

            // Aqui vai a lógica de fazer o que quiser com a informação que veio do arduino
            // ela vem na variável "resultado"
        }
    }
}
