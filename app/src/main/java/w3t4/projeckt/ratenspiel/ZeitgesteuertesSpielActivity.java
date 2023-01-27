package w3t4.projeckt.ratenspiel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class ZeitgesteuertesSpielActivity extends AppCompatActivity
{
    /**
     * Hier angelegte Fields
     */

    private TextView tv_landInfo,tv_land, tv_timer, tv_sum;
    private EditText editText;
    private Random rndLaender, rndBuschtabe;
    private int rndLaenderNumber, rndBuschtabeNum, beginBuschNum, timer = 180000;
    private String selectLaender, landStreichen, editTextCheck;
    private ArrayList<Character> laenderChar;
    private int maxPoint = 100, reduzierenPoint, sumPoint = 0, endeSumPoint = 0;

    private Button btn_buschtabenS, btn_vermutenS, btn_restart;
//    ==============================================================================================

    /**
     * Buchstaben bringen, um das Wort leichter zu finden Button
     * @param view
     */
    public void btnBuschtabenS(View view)
    {
        if (laenderChar.size() > 0)
        {
            randomBuschtabenS();
            sumPoint -= reduzierenPoint;
            Toast.makeText(getApplicationContext(), "verbleibende Punkte = " + sumPoint, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "keine Buchstaben mehr!!!",Toast.LENGTH_SHORT).show();
        }
    }
//  ------------------------------------------------------------------------------------------------

    /**
     * Methode zum Neustart des Spiels
     * @param view
     */
    public void btnRestart(View view)
    {
        Intent intent = new Intent(this, ZeitgesteuertesSpielActivity.class);
        finish();
        startActivity(intent);
    }
//  ------------------------------------------------------------------------------------------------
    /**
     * Die Methode, die den eingegebenen Wert prüft (Button)
     * @param view
     */
    public void btnVermutenS(View view)
    {
        editTextCheck = editText.getText().toString();

        if (!TextUtils.isEmpty(editTextCheck))
        {
            if (editTextCheck.equals(selectLaender))
            {
                {
                    endeSumPoint += sumPoint;
                    Toast.makeText(getApplicationContext(), "STIMMT", Toast.LENGTH_SHORT).show();
                    tv_sum.setText("Gesamt Pukte : " + endeSumPoint);
//                System.out.println("SUM POINT :" + endeSumPoint);
                    editText.setText("");
                    randomWertS();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    System.out.println("Gewonnen, richtige Vermutung");
                }
            }
            else{
  //              System.out.println("Verloren, falsch geraten");

                Toast.makeText(getApplicationContext(), "FALSH!!!",Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Der geschätzte Wert darf nicht leer sein!",Toast.LENGTH_SHORT).show();
        }

    }
//  ------------------------------------------------------------------------------------------------
    /**
     * Die Methode, die das Land zufällig bringt
     */
    private void randomWertS()
    {

        ArrayList<ArrayList<Object>> inhalt =  Datenbankzeugs.getInstance().auslesen();
        Log.d("@@@@@@@@",inhalt.toString());
        landStreichen = "";
        rndLaender= new Random();
        rndLaenderNumber = rndLaender.nextInt(inhalt.size());
        selectLaender = inhalt.get(rndLaenderNumber).get(1).toString();

        for (ArrayList zeile : inhalt)
        {
            for (Object zelle : zeile)
            {
                selectLaender = String.format(zelle.toString());
            }
            break;
        }

        tv_landInfo.setText("Land mit "+selectLaender.length()+ " Buchstaben");

        tipBuschtaben();


        System.out.println("Laender buschtaben nummer = " + selectLaender.length() + "Beginnen buschtaben nummer = " + beginBuschNum);

        for (int i = 0 ; i < selectLaender.length() ; i++)
        {
            if (i < selectLaender.length() - 1)
            {
                landStreichen += "_ ";
            }
            else
            {
                landStreichen += "_";
            }
        }
        System.out.println(landStreichen);
        tv_land.setText(landStreichen);

        laenderChar = new ArrayList<>();

        for (char c : selectLaender.toCharArray())
        {
            laenderChar.add(c);
        }

        for (int c = 0 ; c < beginBuschNum; c++)
            randomBuschtabenS();

        reduzierenPoint = maxPoint / laenderChar.size();
        //       System.out.println("Reduzierte point : "+reduzierenPoint);
        sumPoint = maxPoint;
    }
//    ----------------------------------------------------------------------------------------------
    /**
     * Methode, die zufällige Buchstaben in das Wort Land einfügt
     */
    private void randomBuschtabenS()
    {
        rndBuschtabeNum = rndBuschtabe.nextInt(laenderChar.size());
//          System.out.println(laenderChar.get(rndBuschtabeNum));
        String[] txtBuschtaben = tv_land.getText().toString().split(" ");
        char[] laenderBuschtabeCharArray = selectLaender.toCharArray();

        for (int i = 0; i < selectLaender.length(); i++)
        {
            if (txtBuschtaben[i].equals("_") && laenderBuschtabeCharArray[i] == laenderChar.get(rndBuschtabeNum))
            {
                txtBuschtaben[i] = String.valueOf(laenderChar.get(rndBuschtabeNum));

                landStreichen = "";

                for (int j = 0; j < selectLaender.length(); j++)
                {
                    if(j == i)
                    {
                        landStreichen += txtBuschtaben[j]+ " ";
                    }
                    else if (j < selectLaender.length() - 1)
                    {
                        landStreichen += txtBuschtaben[j] + " " ;
                    }
                    else
                    {
                        landStreichen += txtBuschtaben[j];
                    }
                }
                break;
            }
        }
        tv_land.setText(landStreichen);
        laenderChar.remove(rndBuschtabeNum);
    }
//    ----------------------------------------------------------------------------------------------

    /**
     * Gibt 3 Minuten. Am Ende der Zeit werden die gesammelten Punkte
     * (Toast) auf dem Bildschirm angezeigt und das Spiel ist beendet.
     */
    private void timerMethod()
    {
        new CountDownTimer(timer,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                tv_timer.setText((millisUntilFinished / 60000) + ":" + ((millisUntilFinished % 60000) / 1000));
            }

            @Override
            public void onFinish()
            {
                tv_timer.setText("0:00");
                editText.setEnabled(false);
                btn_buschtabenS.setEnabled(false);
                btn_vermutenS.setEnabled(false);
                btn_restart.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "GAME OVER\nTOTAL PUNKTE: " + endeSumPoint, Toast.LENGTH_LONG).show();
            }
        }.start();
    }
//  ------------------------------------------------------------------------------------------------
    /**
     * Die ID der Fields in der Aktivität
     */
    private void initComponents()
    {
        btn_buschtabenS = (Button) findViewById(R.id.btn_buschtabenS);
        btn_vermutenS = (Button) findViewById(R.id.btn_vermutenS);
        btn_restart = (Button) findViewById(R.id.btnRestart);

        tv_landInfo = (TextView) findViewById(R.id.tv_landInfoS);
        tv_land = (TextView) findViewById(R.id.tv_landS);
        tv_sum = (TextView) findViewById(R.id.tv_sumS);
        tv_timer =(TextView) findViewById(R.id.tv_timer);
        editText = (EditText) findViewById(R.id.editTextS);
    }
//    ----------------------------------------------------------------------------------------------
    /**
     * Die Methode, die je nach Wortlänge hilft
     */
    private void tipBuschtaben()
    {
        if(selectLaender.length() >= 5 && selectLaender.length() <= 7)
        {
            beginBuschNum = 1;
        }
        else if (selectLaender.length() >= 8 && selectLaender.length() < 10)
        {
            beginBuschNum = 2;
        }
        else if(selectLaender.length() >=10)
        {
            beginBuschNum = 3;
        }
        else
        {
            beginBuschNum = 0;
        }
    }

 //    ==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeitgesteuertes_spiel);

        initComponents();
        timerMethod();
        rndBuschtabe = new Random();
        randomWertS();
    }
//    ==============================================================================================
}