package advs.prog_adr_stud;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

public class ger_hid extends AppCompatActivity {

    String appIp = "http://192.168.111.100:8090/";

    ToggleButton vaz;
    TextView vazao, teste;

    String recebe_vazao, rebebe_led_vazao;

    int min_recebe_led_vazao = 467;
    int max_recebe_led_vazao = min_recebe_led_vazao +1;

    int min_recebe_vazao = max_recebe_led_vazao +2;
    int max_recebe_vazao = min_recebe_vazao +7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ger_hid);

        vazao = (TextView)findViewById(R.id.textView7);

        teste = (TextView)findViewById(R.id.textView45);

        vaz = (ToggleButton)findViewById(R.id.toggleButton);

        vaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("bomba");
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

            rebebe_led_vazao = resultado.substring(min_recebe_led_vazao, max_recebe_led_vazao);
            recebe_vazao = resultado.substring(min_recebe_vazao, max_recebe_vazao);

            vazao.setText(recebe_vazao);

            /*if (rebebe_led_vazao == "1")
            {
                vaz.setChecked(true);
            }
            else
            {
                vaz.setChecked(false);
            }*/

            // Aqui vai a lógica de fazer o que quiser com a informação que veio do arduino
            // ela vem na variável "resultado"
        }
    }
}
