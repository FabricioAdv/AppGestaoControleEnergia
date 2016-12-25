#include <String.h>

#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };
byte ip[] = { 192, 168, 111, 100 };

EthernetServer server(8090);

int segund = 0;

//Lampadas dos ambientes
int led_fab = 3;   //Lampada da fabrica
int led_eng = 4;   //Lampada da engenharia
int led_cor = 22;  //Lampada do corredor
int led_fab_rl = 23;   //Relê da lampada da fabrica
int led_eng_rl = 24;   //Relê da lampada da engenharia

int sens_luz_eng = A2;
int led_eng_1 = 29;
int led_eng_2 = 30;
int led_eng_3 = 31;

int sens_luz_fab = A3;
int led_fab_1 = 37;
int led_fab_2 = 38;
int led_fab_3 = 39;

int sens_luz_ext = A4;
int led_ext_1 = 33;
int led_ext_2 = 34;
int led_ext_3 = 35;

//Hidráulicos
int bomba = 40;
int sens_vaz = 2;

//Energia
int volt = A0;
int amp = A1;

//Medições
int mAmps;
int mVolts;
int mLum_eng;
int mLum_fab;
int mLum_ext;

//Calculos
int Amps;
int Volts;

float Lum_eng;
float Lux_eng;
float Cand_eng;

float Lum_fab;
float Lux_fab;
float Cand_fab;

float Lum_ext;
float Lux_ext;
float Cand_ext;

int PWM;

String readString = String (40);

String statusLed;
String PWM_fab;
String PWM_eng;

String Calculo;
String info1;
String info2;
String info3;
String info4;
String info5;
String info6;
String info7;
String info8;
String info9;
String info10;
String info11;
String info12;
String info13;
String info14;
String info15;
String info16;
String info17;

float sensorValue_aux_1 = 0;
float sensorValue_1 = 0;
float currentValue_1 = 0;
float voltsporUnidade = 0.0048828125;

float sensorValue_aux_2 = 0;
float sensorValue_2 = 0;
float currentValue_2 = 0;

float vazao; //Variável para armazenar o valor em L/min
float media=0; //Variável para tirar a média a cada 1 minuto
int contaPulso; //Variável para a quantidade de pulsos
int i=0; //Variável para contagem

unsigned long time;
unsigned int res;

int seg = 0;
int minu = 0;

void incpulso ()
{ 
  contaPulso++; //Incrementa a variável de contagem dos pulsos
  
  
  vazao = contaPulso / 5.5; //Converte para L/min 
  
  media=media+vazao; //Soma a vazão para o calculo da media
  i++;
  
  Serial.print(vazao); //Imprime na serial o valor da vazão
  Serial.print(" L/min - "); //Imprime L/min
  Serial.print(i); //Imprime a contagem i (segundos)
  Serial.println(" medicao "); //Imprime s indicando que está em segun

  if( minu >= 1)
  {
    minu = 0;
    seg = 0;
    media = media/i; //Tira a media dividindo por 
    Serial.print("\n Media por minuto = "); //Imprime a frase Media por minuto =
    Serial.print(media); //Imprime o valor da media
    Serial.println(" L/min - "); //Imprime L/min
    media = 0; //Zera a variável media para uma nova contagem
    i=0; //Zera a variável i para uma nova contagem
    Serial.println("\n\nInicio\n\n"); //Imprime Inicio indicando que a contagem iniciou
  }
} 




void medicao_sens()
{
  mAmps = analogRead(amp);
  mVolts = analogRead(volt);
  
  mLum_eng = analogRead(sens_luz_eng);
  mLum_fab = analogRead(sens_luz_fab);
  mLum_ext = analogRead(sens_luz_ext);
}

void calc_sens()
{
  Lum_eng = (251.327412288*mLum_eng)/1023;
  Calculo = String(Lum_eng, DEC);
  info7 = Calculo.substring(0,7);
  info7.trim();
  
  Lux_eng = (10406.9321858*mLum_eng)/1023;
  Calculo = String(Lux_eng, DEC);
  info8 = Calculo.substring(0,7);
  info8.trim();
  
  Cand_eng = (20*mLum_eng)/1023;
  Calculo = String(Cand_eng, DEC);
  info9 = Calculo.substring(0,7);
  info9.trim();
  
  Lum_fab = (251.327412288*mLum_fab)/1023;
  Calculo = String(Lum_fab, DEC);
  info10 = Calculo.substring(0,7);
  info10.trim();
  
  Lux_fab = (10406.9321858*mLum_fab)/1023;
  Calculo = String(Lux_fab, DEC);
  info11 = Calculo.substring(0,7);
  info11.trim();
  
  Cand_fab = (20*mLum_fab)/1023;
  Calculo = String(Cand_fab, DEC);
  info12 = Calculo.substring(0,7);
  info12.trim();
  
  Lum_ext = (251.327412288*mLum_ext)/1023;
  Calculo = String(Lum_ext, DEC);
  info13 = Calculo.substring(0,7);
  info13.trim();
  
  Lux_ext = (10406.9321858*mLum_ext)/1023;
  Calculo = String(Lux_ext, DEC);
  info14 = Calculo.substring(0,7);
  info4.trim();
  
  Cand_ext = (20*mLum_ext)/1023;
  Calculo = String(Cand_ext, DEC);
  info15 = Calculo.substring(0,7);
  info15.trim();


  for(int i=1000; i>0; i--)
  {
    sensorValue_aux_1 = (analogRead(amp) -511);
    sensorValue_1 += pow(sensorValue_aux_1,2);
  }
 
  sensorValue_1 = (sqrt(sensorValue_1/ 500)) * voltsporUnidade;
  currentValue_1 = ((sensorValue_1/66)*1000)-0.0850; 

  Calculo = String(currentValue_1, DEC);
  info2 = Calculo.substring(0,7);
  info2.trim();
  
  sensorValue_1 = 0;
  

  for(int i=1000; i>0; i--)
  {
    sensorValue_aux_2 = (analogRead(volt));
    sensorValue_2 += pow(sensorValue_aux_2,2); 
  }

  currentValue_2 = (sensorValue_2/100000);

  if ((currentValue_2 > 7430) && (currentValue_2 < 8120))
  {
    Volts = 127;
  }
  else if ((currentValue_2 > 8120))
  {
    Volts = 220;
  }
  else
  {
    Volts = 0;
  }

  Calculo = String(Volts, DEC);
  info1 = Calculo;
  info1.trim();


  Calculo = String(Volts*currentValue_1, DEC);
  info3 = Calculo.substring(0,7);
  
  sensorValue_2 = 0;

  if (Cand_eng <= 5)
  {
    digitalWrite(led_eng_1, HIGH); //Acende o LED Verde.
    digitalWrite(led_eng_2, LOW); //Apaga o LED Amarelo.
    digitalWrite(led_eng_3, LOW); //Apaga o LED Vermelho.
  }
  else if(Cand_eng > 5 && Cand_eng <= 7)
  {
    digitalWrite(led_eng_1, HIGH); //Acende o LED Amarelo.
    digitalWrite(led_eng_2, HIGH); //Apaga o LED Verde.
    digitalWrite(led_eng_3, LOW); //Apaga o LED Vermelho.
  }
  else
  {
    digitalWrite(led_eng_1, HIGH); //Acende o LED Vermelho.
    digitalWrite(led_eng_2, HIGH); //Apaga o LED Verde.
    digitalWrite(led_eng_3, HIGH); //Apaga o LED Amarelo.
  }

  if (Cand_fab <= 5)
  {
    digitalWrite(led_fab_1, HIGH); //Acende o LED Verde.
    digitalWrite(led_fab_2, LOW); //Apaga o LED Amarelo.
    digitalWrite(led_fab_3, LOW); //Apaga o LED Vermelho.
  }
  else if(Cand_fab > 5 && Cand_fab <= 7)
  {
    digitalWrite(led_fab_1, HIGH); //Acende o LED Amarelo.
    digitalWrite(led_fab_2, HIGH); //Apaga o LED Verde.
    digitalWrite(led_fab_3, LOW); //Apaga o LED Vermelho.
  }
  else
  {
    digitalWrite(led_fab_1, HIGH); //Acende o LED Vermelho.
    digitalWrite(led_fab_2, HIGH); //Apaga o LED Verde.
    digitalWrite(led_fab_3, HIGH); //Apaga o LED Amarelo.
  }

  if (Cand_ext <= 5)
  {
    digitalWrite(led_ext_1, HIGH); //Acende o LED Verde.
    digitalWrite(led_ext_2, LOW); //Apaga o LED Amarelo.
    digitalWrite(led_ext_3, LOW); //Apaga o LED Vermelho.
  }
  else if(Cand_ext > 5 && Cand_ext <= 7)
  {
    digitalWrite(led_ext_1, HIGH); //Acende o LED Amarelo.
    digitalWrite(led_ext_2, HIGH); //Apaga o LED Verde.
    digitalWrite(led_ext_3, LOW); //Apaga o LED Vermelho.
  }
  else
  {
    digitalWrite(led_ext_1, HIGH); //Acende o LED Vermelho.
    digitalWrite(led_ext_2, HIGH); //Apaga o LED Verde.
    digitalWrite(led_ext_3, HIGH); //Apaga o LED Amarelo;
  }
}

void info_app()
{  
  // Luminosidade
  if (digitalRead(led_eng_rl))
  {
    info4 = "1";
  }
  else
  {
    info4 = "0";
  }
  
  if (digitalRead(led_fab_rl))
  {
    info5 = "1";
  }
  else
  {
    info5 = "0";
  }
  
  if (digitalRead(led_cor))
  {
    info6 = "1";
  }
  else
  {
    info6 = "0";
  }
  
  // Hidráulica
  if (digitalRead(bomba))
  {
    info16 = "1";
  }
  else
  {
    info16 = "0";
  }

  Calculo = String(vazao, DEC);
  info17 = Calculo.substring(0,7);
  info17.trim();  
}

void setup () 
{
  Serial.begin(9600);
  
  Ethernet.begin (mac, ip);  
  
  pinMode (led_fab, OUTPUT);
  pinMode (led_eng, OUTPUT);
  pinMode (led_cor, OUTPUT);
  pinMode (led_fab_rl, OUTPUT);
  pinMode (led_eng_1, OUTPUT);
  pinMode (led_eng_2, OUTPUT);
  pinMode (led_eng_3, OUTPUT);
  pinMode (led_fab_1, OUTPUT);
  pinMode (led_fab_2, OUTPUT);
  pinMode (led_fab_3, OUTPUT);
  pinMode (led_ext_1, OUTPUT);
  pinMode (led_ext_2, OUTPUT);
  pinMode (led_ext_3, OUTPUT);
  pinMode (led_eng_rl, OUTPUT);
  pinMode (bomba, OUTPUT);

  pinMode(sens_vaz, INPUT);
  attachInterrupt(0, incpulso, RISING); //Configura o pino 2(Interrupção 0) para trabalhar como interrupção
  Serial.println("\n\nInicio\n\n"); //Imprime Inicio na serial

  if (Ethernet.begin (mac) == 0) 
  {
    //  Serial.println ("Erro na conecção");
    for (;;);
  }
  else 
  {
    //   Serial.println ("Conectado");
    //   Serial.print ("IP: ");
    for (byte thisByte = 0; thisByte < 4; thisByte++) 
    {
      //   Serial.print(Ethernet.localIP()[thisByte], DEC);
      //   Serial.print(".");
    }
  }
}

void loop () 
{
  contaPulso = 0;   //Zera a variável para contar os giros por segundos
  sei();
  
  medicao_sens();
  calc_sens();
  
  
  static unsigned long ult_tempo = 0;
  int tempo = millis();
  if (tempo - ult_tempo >= 1000)
  {
    ult_tempo = tempo;
    seg++;
  }

  if (seg >= 60)
  {
    seg = 0;
    minu++;
  }
  
  //info_app(); 

  EthernetClient client = server.available ();

  if (client)
  {
    while (client.connected ())
    {
      if (client.available ())
      {
        char c = client.read ();

        if (readString.length () < 40)
        {
          readString += (c);
        }

        if (c == '\n')
        {
          //  Serial.println("Iniciando HTML");
          //  Serial.println(readString);          

          //Acionamentos
          
          //Controle de luminosidade
          //Fabrica
          if (readString.indexOf ("led_fab_rl") >= 0)
          {
            digitalWrite (led_fab_rl, !digitalRead (led_fab_rl));
          }

          if (readString.indexOf ("led_fab_b") >= 0)
          {
            analogWrite (led_fab, 85);
          }

          if (readString.indexOf ("led_fab_m") >= 0)
          {
            analogWrite (led_fab, 170);
          }

          if (readString.indexOf ("led_fab_a") >= 0)
          {
            analogWrite (led_fab, 255);
          }

          //Engenharia
          if (readString.indexOf ("led_eng_rl") >= 0)
          {
            digitalWrite (led_eng_rl, !digitalRead (led_eng_rl));
          }

          if (readString.indexOf ("led_eng_b") >= 0)
          {
            analogWrite (led_eng, 85);
          }

          if (readString.indexOf ("led_eng_m") >= 0)
          {
            analogWrite (led_eng, 170);
          }

          if (readString.indexOf ("led_eng_a") >= 0)
          {
            analogWrite (led_eng, 255);
          }

          //Corredor
          if (readString.indexOf ("led_cor") >= 0)
          {
            digitalWrite (led_cor, !digitalRead (led_cor));
          }
          

          //Controle hidráulico
          //Válvula
          if (readString.indexOf ("bomba") >= 0)
          {
            digitalWrite (bomba, !digitalRead (bomba));
          }

          // Cabeçalho HTTP padrão
          client.println ("HTTP/1.1 200 OK");
          client.println ("Contet-Type: text/html");
          client.println ();

          //Pagina da web
          client.println ("<!doctype html>");
          client.println ("<html>");
          client.println ("<head>");
          client.println ("<title>PI Gestão e monitoramento de energia</title>");
          client.println ("<meta name=\"viewport\" content=\"width=320\">");
          client.println ("<meta name=\"viewport\" content=\"width=device-width\">");
          client.println ("<meta charset=\"utf-8\">");
          client.println ("<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">");
          client.println ("<meta http-equiv=\"refresh\" content=\"2;URL=http://192.168.111.100:8090\">"); 

          info_app();
          
          client.println ("<!");
          client.println (info1);
          client.println (info2);
          client.println (info3);
          client.println (info4);
          client.println (info5);
          client.println (info6);
          client.println (info7);
          client.println (info8);
          client.println (info9);
          client.println (info10);
          client.println (info11);
          client.println (info12);
          client.println (info13);
          client.println (info14);
          client.println (info15);
          client.println (info16);
          client.println (info17);
          client.println (">"); 
                   
          client.println ("</head>");
          
          client.println ("<body>");
          client.println ("<center>");
          client.println ("<font size=\"5\" face=\"arial\" color=\"black\">PI</font>");
          client.println ("<br/>");
          client.println ("<font size=\"5\" face=\"arial\" color=\"black\">GESTÃO E MONITORAMENTO</font>");
          client.println ("<br/>");
          client.println ("<font size=\"5\" face=\"arial\" color=\"black\">DE ENERGIA</font>");
          client.println ("<br/><br/>");

          
          client.println ("<div id=\"Controle de energia\">");
          client.println ("<font size=\"5\" face=\"arial\" color=\"black\">Controle de energia</font>");
          client.println ("<br/><br/>");
          
          statusLed = String (analogRead (volt), DEC);
          
          client.println ("<font size=\"3\" face=\"arial\" color=\"black\">Tensão: "+statusLed+"</font>");
          client.println ("<br/>");

          statusLed = String (analogRead (amp), DEC);
          
          client.println ("<font size=\"3\" face=\"arial\" color=\"black\">Corrente: "+statusLed+"</font>");
          client.println ("<br/><br/>");
          
          client.println ("</div>");

          client.println ("<div id=\"Controle de luminosidade\">");
          client.println ("<font size=\"5\" face=\"arial\" color=\"black\">Controle de luminosidade</font>");
          client.println ("<br/><br/>");
          
          if (digitalRead (led_fab_rl))
          {
            statusLed = "Ligado";
          }
          else
          {
            statusLed = "Desligado";
          }
          
          client.println ("<form action=\"led_fab_rl\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Led da fabrica - "+statusLed+"</button>");
          client.println ("</form><br/>");

          if ((analogRead (sens_luz_fab) > 0) && (analogRead (sens_luz_fab) <= 341))
          {
            statusLed = "Baixo";
          }
          else if ((analogRead (sens_luz_fab) > 341) && (analogRead (sens_luz_fab) <= 682))
          {
            statusLed = "Médio";
          }
          else if (analogRead (sens_luz_fab) > 682)
          {
            statusLed = "Alto";
          }

          client.println ("<font size=\"3\" face=\"arial\" color=\"black\">Intensidade - "+statusLed+"</font>");
          client.println ("<br/>");
          client.println ("<table width=\"414\" height=\"34\" border=\"0\">");
          client.println ("<tbody>");
          client.println ("<tr>");
          client.println ("<td>");
          client.println ("<form action=\"led_fab_b\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Baixo</button>");
          client.println ("</form>");
          client.println ("</td>");
          client.println ("<td>");
          client.println ("<form action=\"led_fab_m\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Médio</button>");
          client.println ("</form>");
          client.println ("</td>");
          client.println ("<td>");
          client.println ("<form action=\"led_fab_a\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Alto</button>");
          client.println ("</form>");
          client.println ("</td>");
          client.println ("</tr>");
          client.println ("</tbody>");
          client.println ("</table>");
          

          if (digitalRead(led_eng_rl))
          {
            statusLed = "Ligado";
          }
          else
          {
            statusLed = "Desligado";
          }

          client.println ("<form action=\"led_eng_rl\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Led da engenharia - "+statusLed+"</button>");
          client.println ("</form><br/>");

          if ((analogRead (sens_luz_eng) > 0) && (analogRead (sens_luz_eng) <= 341))
          {
            statusLed = "Baixo";
          }
          else if ((analogRead (sens_luz_eng) > 341) && (analogRead (sens_luz_eng) <= 682))
          {
            statusLed = "Médio";
          }
          else if (analogRead (sens_luz_eng) > 682)
          {
            statusLed = "Alto";
          }

          client.println ("<font size=\"3\" face=\"arial\" color=\"black\">Intensidade - "+statusLed+"</font>");
          client.println ("<br/>");
          client.println ("<table width=\"414\" height=\"34\" border=\"0\">");
          client.println ("<tbody>");
          client.println ("<tr>");
          client.println ("<td>");
          client.println ("<form action=\"led_eng_b\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Baixo</button>");
          client.println ("</form>");
          client.println ("</td>");
          client.println ("<td>");
          client.println ("<form action=\"led_eng_m\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Médio</button>");
          client.println ("</form>");
          client.println ("</td>");
          client.println ("<td>");
          client.println ("<form action=\"led_eng_a\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Alto</button>");
          client.println ("</form>");
          client.println ("</td>");
          client.println ("</tr>");
          client.println ("</tbody>");
          client.println ("</table>");
          

          if (digitalRead(led_cor))
          {
            statusLed = "Ligado";
          }
          else
          {
            statusLed = "Desligado";
          }

          client.println ("<form action=\"led_cor\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Led do corredor - "+statusLed+"</button>");
          client.println ("</form><br/>");
          client.println ("</div>");


          client.println ("<div id=\"Controle hidráulico\">");
          client.println ("<font size=\"5\" face=\"arial\" color=\"black\">Controle hidráulico</font>");
          client.println ("<br/><br/>");

          if (digitalRead(bomba))
          {
            statusLed = "Ligada";
          }
          else
          {
            statusLed = "Desligada";
          }

          client.println ("<form action=\"bomba\" method=\"get\">");
          client.println ("<button type=\"submit\" style=\"width:200px;\">Bomba - "+statusLed+"</button>");
          client.println ("</form><br/>");

          statusLed = String (analogRead (sens_vaz), DEC);

          client.println ("<font size=\"3\" face=\"arial\" color=\"black\">Vazão: "+statusLed+"</font>");
          client.println ("<br/><br/>");
          client.println ("</div>");
          
          client.println ("</center>");
          client.println ("</body>");
          
          client.println ("</html>");          

          readString = "";

          client.stop();
        }
      }     
    }
  }
  cli();
}


