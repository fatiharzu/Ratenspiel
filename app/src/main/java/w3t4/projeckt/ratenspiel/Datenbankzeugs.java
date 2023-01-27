package w3t4.projeckt.ratenspiel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class Datenbankzeugs extends SQLiteOpenHelper
{
    /**
     * Hier angelegte Fields
     */
    private Context context;

    private static Datenbankzeugs instance = null;

//  ================================================================================================
    //Constructor
    private Datenbankzeugs(Context context, String dbName)
    {
        super(context, dbName, null, 1);
        this.context = context;

    }
//  ------------------------------------------------------------------------------------------------

    public static void init(Context context, String dbName)
    {
        if (instance == null)
        {
            instance=new Datenbankzeugs(context,dbName);
        }
    }


//  ------------------------------------------------------------------------------------------------

    //Datenbankzeugs getInstance method
    public static Datenbankzeugs getInstance()
    {
        return instance;
    }

    /**
     * Die implementierten Codes in der db finden sich in (SQLquery) und (executed)
     * @param db The database.
     */
//  ------------------------------------------------------------------------------------------------
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE Laender ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Land VARCHAR(255));";
        String sql2 ="INSERT INTO Laender (Land) VALUES ('Myland');";
        db.execSQL(sql);

        db.execSQL(sql2);
    }
//  ------------------------------------------------------------------------------------------------

    /**
     * Die implementierten Codes in der db
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
//  ------------------------------------------------------------------------------------------------

    /**
     * ausfuehren DB
     * @param sql
     */
    public void ausfuehren(String sql)
    {
        // öffnet DB zum Schreiben
        SQLiteDatabase db = getWritableDatabase();
        // führt Anweisung aus
        db.execSQL(sql);
        // schließt Datenbankverbindung
        db.close();
    }
//  ------------------------------------------------------------------------------------------------

    /**
     * Liest Variablen aus der SQLite-Datenbank
     * @return zeilen
     */
    public ArrayList<ArrayList<Object>> auslesen()
    {
        ArrayList<ArrayList<Object>> zeilen = new ArrayList<>();
        ArrayList<Object> zeile;

        SQLiteDatabase db = getReadableDatabase();

        // Anfrage an die Datenbank
        Cursor resultSet = db.rawQuery("SELECT ID, Land FROM Laender ORDER BY RANDOM() LIMIT 200;"
                                        , null);
        /*
         "moveToFirst" bewegt den Zeiger zum ersten Element. Ausserdem liefert es "true",
         falls was im Cursor enthalten ist und "false", falls nichts in der
         Tabelle stand, was zum Select-Statement passt.
         */
        if (resultSet.moveToFirst())
        {
            int anzahlSpalten = resultSet.getColumnCount();
            String[] spaltennamen = resultSet.getColumnNames();

            zeile = new ArrayList<>();
            for (int i = 0; i < anzahlSpalten; i++)
                zeile.add(spaltennamen[i]);
//            zeilen.add(zeile);

            do
            {
                zeile = new ArrayList<>();

                for (int spalte = 0; spalte < anzahlSpalten; spalte++)
                    zeile.add(resultSet.getString(spalte));

                zeilen.add(zeile);
            } while (resultSet.moveToNext());
        }

        db.close();

        return zeilen;
    }
//    ==============================================================================================
}
