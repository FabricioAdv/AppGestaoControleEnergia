package advs.prog_adr_stud;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Inicio extends AppCompatActivity {

    Timer timer;
    TimerTask timerTask;

    int estado_app = 0;

    final Handler handler = new Handler();

    private ViewFlipper viewFlipper;
    private float lastX;

    TextView ini, energ, lum, hid;

    int page = 0;


    String appIp = "http://192.168.111.100:8090/";


    TextView ener, cor, cons;

    String recebe_ener, recebe_cor, recebe_cons;

    int min_recebe_ener = 281;
    int max_recebe_ener = min_recebe_ener +3;

    int min_recebe_cor = max_recebe_ener +2;
    int max_recebe_cor = min_recebe_cor +7;

    int min_recebe_cons = max_recebe_cor +2;
    int max_recebe_cons = min_recebe_cons +7;


    Button bt_baixo_eng, bt_medio_eng, bt_alto_eng, bt_baixo_fab, bt_medio_fab, bt_alto_fab;
    ToggleButton est_eng, est_fab, est_cor;

    TextView lum_eng, lux_eng, cd_eng, lum_fab, lux_fab, cd_fab, lum_ext, lux_ext, cd_ext;

    String recebe_led_eng, recebe_led_fab, recebe_led_cor, recebe_lum_eng, recebe_lux_eng, recebe_cd_eng;
    String recebe_lum_fab, recebe_lux_fab, recebe_cd_fab,recebe_lum_ext, recebe_lux_ext, recebe_cd_ext;

    int min_recebe_led_eng = max_recebe_cons + 5;
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


    ToggleButton est_bomb;
    TextView vazao;

    String recebe_vazao, recebe_led_vazao;

    int min_recebe_led_vazao = max_recebe_lux_ext + 2;
    int max_recebe_led_vazao = min_recebe_led_vazao +1;

    int min_recebe_vazao = max_recebe_led_vazao +2;
    int max_recebe_vazao = min_recebe_vazao +7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);

        ini = (TextView)findViewById(R.id.textView8);
        energ = (TextView)findViewById(R.id.textView30);
        lum = (TextView)findViewById(R.id.textView35);
        hid = (TextView)findViewById(R.id.textView41);

        switch (page)
        {
            case 0:
                ini.setTextColor(Color.GREEN);
                energ.setTextColor(Color.BLACK);
                lum.setTextColor(Color.BLACK);
                hid.setTextColor(Color.BLACK);
                break;
            case 1:
                ini.setTextColor(Color.BLACK);
                energ.setTextColor(Color.GREEN);
                lum.setTextColor(Color.BLACK);
                hid.setTextColor(Color.BLACK);
                break;
            case 2:
                ini.setTextColor(Color.BLACK);
                energ.setTextColor(Color.BLACK);
                lum.setTextColor(Color.GREEN);
                hid.setTextColor(Color.BLACK);
                break;
            case 3:
                ini.setTextColor(Color.BLACK);
                energ.setTextColor(Color.BLACK);
                lum.setTextColor(Color.BLACK);
                hid.setTextColor(Color.GREEN);
                break;
        }


        ener = (TextView)findViewById(R.id.textView);
        cor = (TextView)findViewById(R.id.textView2);
        cons = (TextView)findViewById(R.id.textView3);


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
                solicita("led_cor");
            }
        });


        vazao = (TextView)findViewById(R.id.textView7);

        est_bomb = (ToggleButton)findViewById(R.id.est_bomb);

        est_bomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicita("bomba");
            }
        });
    }

    protected void onResume() {
        super.onResume();
        startTimer();
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 3000);
    }

    public void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        estado_app = 0;
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable(){
                    public void run() {
                        solicita(" ");
                    }
                });
            }
        };
    }

    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                if (lastX < currentX) {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);

                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);

                    viewFlipper.showNext();

                    page = page - 1;
                }

                if (lastX > currentX)
                {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);

                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                    viewFlipper.showPrevious();

                    page = page + 1;
                }
                break;
        }

        switch (page)
        {
            case 0:
                ini.setTextColor(Color.GREEN);
                energ.setTextColor(Color.BLACK);
                lum.setTextColor(Color.BLACK);
                hid.setTextColor(Color.BLACK);
                break;
            case 1:
                ini.setTextColor(Color.BLACK);
                energ.setTextColor(Color.GREEN);
                lum.setTextColor(Color.BLACK);
                hid.setTextColor(Color.BLACK);
                break;
            case 2:
                ini.setTextColor(Color.BLACK);
                energ.setTextColor(Color.BLACK);
                lum.setTextColor(Color.GREEN);
                hid.setTextColor(Color.BLACK);
                break;
            case 3:
                ini.setTextColor(Color.BLACK);
                energ.setTextColor(Color.BLACK);
                lum.setTextColor(Color.BLACK);
                hid.setTextColor(Color.GREEN);
                break;
        }
        return false;
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

            recebe_ener = resultado.substring(min_recebe_ener, max_recebe_ener);
            recebe_cor = resultado.substring(min_recebe_cor, max_recebe_cor);
            recebe_cons = resultado.substring(min_recebe_cons, max_recebe_cons);

            ener.setText(recebe_ener);
            cor.setText(recebe_cor);
            cons.setText(recebe_cons);


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

            lum_eng.setText(recebe_lum_eng);
            lux_eng.setText(recebe_lux_eng);
            cd_eng.setText(recebe_cd_eng);
            lum_fab.setText(recebe_lum_fab);
            lux_fab.setText(recebe_lux_fab);
            cd_fab.setText(recebe_cd_fab);
            lum_ext.setText(recebe_lum_ext);
            lux_ext.setText(recebe_lux_ext);
            cd_ext.setText(recebe_cd_ext);


            recebe_led_vazao = resultado.substring(min_recebe_led_vazao, max_recebe_led_vazao);
            recebe_vazao = resultado.substring(min_recebe_vazao, max_recebe_vazao);

            vazao.setText(recebe_vazao);

            if (estado_app == 0)
            {
                if (recebe_led_eng.equals("1"))
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
                }

                if (recebe_led_vazao.equals("1"))
                {
                    est_bomb.setChecked(true);
                }
                else
                {
                    est_bomb.setChecked(false);
                }

                estado_app = 1;
            }
        }
    }
}
