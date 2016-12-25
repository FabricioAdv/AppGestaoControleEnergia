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
import android.widget.ToggleButton;

import java.io.IOException;

public class ger_luz extends AppCompatActivity {

    String appIp = "http://192.168.111.100:8090/";

    Button bt_baixo_eng, bt_medio_eng, bt_alto_eng, bt_baixo_fab, bt_medio_fab, bt_alto_fab;
    ToggleButton est_eng, est_fab, est_cor;

    TextView lum_eng, lux_eng, cd_eng, lum_fab, lux_fab, cd_fab, lum_ext, lux_ext, cd_ext, teste;

    String recebe_led_eng, recebe_led_fab, recebe_led_cor, recebe_lum_eng, recebe_lux_eng, recebe_cd_eng;
    String recebe_lum_fab, recebe_lux_fab, recebe_cd_fab,recebe_lum_ext, recebe_lux_ext, recebe_cd_ext;

    int min_recebe_led_eng = 380;
    int max_recebe_led_eng = min_recebe_led_eng +1;

    int min_recebe_led_fab = max_recebe_led_eng +1;
    int max_recebe_led_fab = min_recebe_led_fab +1;

    int min_recebe_led_cor = max_recebe_led_fab +1;
    int max_recebe_led_cor = min_recebe_led_cor +1;

    int min_recebe_lum_eng = max_recebe_led_cor +1;
    int max_recebe_lum_eng = min_recebe_lum_eng +7;

    int min_recebe_cd_eng = max_recebe_lum_eng +2;
    int max_recebe_cd_eng = min_recebe_cd_eng +7;

    int min_recebe_lux_eng = max_recebe_cd_eng +2;
    int max_recebe_lux_eng = min_recebe_lux_eng +7;

    int min_recebe_lum_fab = max_recebe_lux_eng +2;
    int max_recebe_lum_fab = min_recebe_lum_fab +7;

    int min_recebe_cd_fab = max_recebe_lum_fab +2;
    int max_recebe_cd_fab = min_recebe_cd_fab +7;

    int min_recebe_lux_fab = max_recebe_cd_fab +2;
    int max_recebe_lux_fab = min_recebe_lux_fab +7;

    int min_recebe_lum_ext = max_recebe_lux_fab +2;
    int max_recebe_lum_ext = min_recebe_lum_ext +7;

    int min_recebe_cd_ext = max_recebe_lum_ext +2;
    int max_recebe_cd_ext = min_recebe_cd_ext +7;

    int min_recebe_lux_ext = max_recebe_cd_ext +2;
    int max_recebe_lux_ext = min_recebe_lux_ext +7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ger_luz);

        bt_baixo_eng = (Button)findViewById(R.id.bt_baixo_eng);
        bt_medio_eng = (Button)findViewById(R.id.bt_medio_eng);
        bt_alto_eng = (Button)findViewById(R.id.bt_alto_eng);
        bt_baixo_fab = (Button)findViewById(R.id.bt_baixo_fab);
        bt_medio_fab = (Button)findViewById(R.id.bt_medio_fab);
        bt_alto_fab = (Button)findViewById(R.id.bt_alto_fab);

        est_eng = (ToggleButton)findViewById(R.id.est_eng);
        est_fab = (ToggleButton)findViewById(R.id.est_fab);
        est_cor = (ToggleButton)findViewById(R.id.est_cor);

        lum_eng = (TextView)findViewById(R.id.textView17);
        cd_eng = (TextView)findViewById(R.id.textView18);
        lux_eng = (TextView)findViewById(R.id.textView19);
        lum_fab = (TextView)findViewById(R.id.textView20);
        cd_fab = (TextView)findViewById(R.id.textView21);
        lux_fab = (TextView)findViewById(R.id.textView22);
        lum_ext = (TextView)findViewById(R.id.textView23);
        cd_ext = (TextView)findViewById(R.id.textView24);
        lux_ext = (TextView)findViewById(R.id.textView25);

        teste = (TextView)findViewById(R.id.textView43);

        bt_baixo_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_eng_b");
            }
        });

        bt_medio_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_eng_m");
            }
        });

        bt_alto_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_eng_a");
            }
        });

        bt_baixo_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_fab_b");
            }
        });

        bt_medio_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_fab_m");
            }
        });

        bt_alto_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_fab_a");
            }
        });

        est_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_eng_rl");
            }
        });

        est_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_fab_rl");
            }
        });

        est_cor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("led_cor_rl");
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

            recebe_led_eng = resultado.substring(min_recebe_led_eng, max_recebe_led_eng);
            recebe_led_fab = resultado.substring(min_recebe_led_fab, max_recebe_led_fab);
            recebe_led_cor = resultado.substring(min_recebe_led_cor, max_recebe_led_cor);
            recebe_lum_eng = resultado.substring(min_recebe_lum_eng, max_recebe_lum_eng);
            recebe_lux_eng = resultado.substring(min_recebe_lux_eng, max_recebe_lux_eng);
            recebe_cd_eng = resultado.substring(min_recebe_cd_eng, max_recebe_cd_eng);
            recebe_lum_fab = resultado.substring(min_recebe_lum_fab, max_recebe_lum_fab);
            recebe_lux_fab = resultado.substring(min_recebe_lux_fab, max_recebe_lux_fab);
            recebe_cd_fab = resultado.substring(min_recebe_cd_fab, max_recebe_cd_fab);
            recebe_lum_ext = resultado.substring(min_recebe_lum_ext, max_recebe_lum_ext);
            recebe_lux_ext = resultado.substring(min_recebe_lux_ext, max_recebe_lux_ext);
            recebe_cd_ext = resultado.substring(min_recebe_cd_ext, max_recebe_cd_ext);

            /*if (recebe_led_eng.equals("1"))
            {
                est_eng.setChecked(true);
            }
            else
            {
                est_eng.setChecked(false);
            }

            if (recebe_led_fab.equals("1"))
            {
                est_fab.setChecked(true);
            }
            else
            {
                est_fab.setChecked(false);
            }

            if (recebe_led_cor.equals("1"))
            {
                est_cor.setChecked(true);
            }
            else
            {
                est_cor.setChecked(false);
            }*/

            lum_eng.setText(recebe_lum_eng);
            lux_eng.setText(recebe_lux_eng);
            cd_eng.setText(recebe_cd_eng);
            lum_fab.setText(recebe_lum_fab);
            lux_fab.setText(recebe_lux_fab);
            cd_fab.setText(recebe_cd_fab);
            lum_ext.setText(recebe_lum_ext);
            lux_ext.setText(recebe_lux_ext);
            cd_ext.setText(recebe_cd_ext);

            // Aqui vai a lógica de fazer o que quiser com a informação que veio do arduino
            // ela vem na variável "resultado"
        }
    }
}
