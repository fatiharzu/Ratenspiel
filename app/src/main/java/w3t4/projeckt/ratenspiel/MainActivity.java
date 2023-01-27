package w3t4.projeckt.ratenspiel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    /**
     * Hier angelegte Fields
     */

    private Intent intent;
    //----------------------------------------------------------------------------------------------
    /**
     * Gehen Sie mit Menü-Item Aktivity speichern
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.add_menu_add_land)
        {
            intent = new Intent(this, SpeichernActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //----------------------------------------------------------------------------------------------

    /**
     * Leitet die Buttons auf der Startseite um
     * @param view
     */
    public void btnMainPage(View view)
    {
        switch (view.getId())
        {
            case R.id.spielButton:
                selectActivity("Spiel");

                break;
            case R.id.zeitSpielButton:
                selectActivity("ZeitSpiel");

                break;
            case R.id.beendenButton:
                beenden();
                break;
        }
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Wechselt zwischen Aktivities-Classes
     * @param activityName
     */
    private void selectActivity(String activityName)
    {
        if (activityName.equals("Spiel"))
            intent = new Intent(this, SpielActivity.class);


        if (activityName.equals("ZeitSpiel"))
            intent = new Intent(this, ZeitgesteuertesSpielActivity.class);


        startActivity(intent);
    }
//  ------------------------------------------------------------------------------------------------

    /**
     * Methode der Beenden Buttons
     */
    private void beenden()
    {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Methode der Zurück Buttons
     */
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        beenden();
    }
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Datenbankzeugs.init(this, "Laender");

    }
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_land, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //----------------------------------------------------------------------------------------------
}