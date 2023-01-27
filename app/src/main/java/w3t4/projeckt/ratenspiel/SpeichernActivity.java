package w3t4.projeckt.ratenspiel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class SpeichernActivity extends AppCompatActivity
{
    /**
     * Hier angelegte Fields
     */
    private EditText edLand;
    private Button btnSpeichern, btnAnzeigen;
    private TextView tvAnzeige;

//==================================================================================================

    /**
     * View OnClickListener aktiviert die Anzeigen- und Speichern-Buttons
     */
    private class MyOCL implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if (v == btnAnzeigen)
                anzeigen();
            else if (v == btnSpeichern)
                speichern();
        }
    }
//--------------------------------------------------------------------------------------------------

    /**
     * SQLite speichert die Variablen in der db
     */
    private void speichern()
    {
        String editTextControl = edLand.getText().toString();

        if(!TextUtils.isEmpty(editTextControl))
        {
            String sql = String.format("INSERT INTO Laender (Land) VALUES ('%s')", editTextControl);
            Datenbankzeugs.getInstance().ausfuehren(sql);

            edLand.setText("");
            Toast.makeText(getApplicationContext(), "Schpeicherd",Toast.LENGTH_SHORT).show();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "nicht leer sein", Toast.LENGTH_SHORT).show();
        }



    }
//--------------------------------------------------------------------------------------------------

    /**
     * Zeigt Variablen in der SQLite-Datenbank an
     */
    private void anzeigen()
    {
        ArrayList<ArrayList<Object>> inhalt = null;

            inhalt = Datenbankzeugs.getInstance().auslesen();

        tvAnzeige.setText("");

        for (ArrayList zeile : inhalt)
        {
            for (Object zelle : zeile)

                tvAnzeige.append(String.format("%5s ", zelle.toString()));


            tvAnzeige.append("\n");
        }

    }
//--------------------------------------------------------------------------------------------------

    /**
     * Components im Inhalt gefunden werden
     */
    private void initComponents()
    {
        MyOCL ocl = new MyOCL();

        edLand = (EditText) findViewById(R.id.edLand);

        btnSpeichern = (Button) findViewById(R.id.btnSpeichern);
        btnSpeichern.setOnClickListener(ocl);
        btnAnzeigen = (Button) findViewById(R.id.btnAnzeigen);
        btnAnzeigen.setOnClickListener(ocl);

        tvAnzeige = (TextView) findViewById(R.id.tvAnzeige);
    }
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speichern);

        initComponents();
    }
//==================================================================================================
}