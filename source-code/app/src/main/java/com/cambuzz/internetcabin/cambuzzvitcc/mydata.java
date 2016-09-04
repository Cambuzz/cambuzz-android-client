package com.cambuzz.internetcabin.cambuzzvitcc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import java.sql.SQLException;
public class mydata
{
    public static final String Key_ROWID="row_id";  //rowid
    public static final String Key_NAME="Teachers_name"; //teachers name
    public static final String Key_PERIODS="Teachers_periods";//periods
    public static final String Key_CABINS="Teachers_cabins";//periods
    private static final String DATABASE_NAME="Timetable";// database name
    private static final String DATABASE_TABLE="Teachings4";// database table
    private static final int DATABASE_VERSION=1;//version
    private Dbhelper ourhelper;
    private  final Context ourContext;
    private SQLiteDatabase ourDatabase;
    private static class Dbhelper extends SQLiteOpenHelper {

        //constructor
        public Dbhelper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        // gets called only once i.e when we are creating the database
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(
                    "CREATE TABLE "+DATABASE_TABLE+" ("+
                            Key_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            Key_NAME+" TEXT NOT NULL, "+
                            Key_PERIODS+" TEXT NOT NULL, "+
                            Key_CABINS+" TEXT NOT NULL);"
            );

        }

        // gets called everytime we want to access our database

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(
                    "DROP TABLE IF EXISTS "+DATABASE_TABLE );
            onCreate(db);
        }
    }
    public mydata(Context c)
    {
        ourContext=c;

    }
    public mydata open() throws SQLException
    {
        ourhelper=new Dbhelper(ourContext);
        ourDatabase=ourhelper.getReadableDatabase();
        return this;
    }
    public void close()
    {
        ourhelper.close();// closes sqliteopenhelper
    }
    public long createentry(String teachers, String periodss,String cabi)
    {
        ContentValues cv=new ContentValues();
        cv.put(Key_NAME,teachers);
        cv.put(Key_PERIODS,periodss);
        cv.put(Key_CABINS,cabi);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);

    }
    public String[] searching(String search) {

        String []columns=new String[]{Key_ROWID,Key_NAME,Key_PERIODS,Key_CABINS};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        int flag=0;
        String result1=null;
        String result2=null;
        int iRow=c.getColumnIndex(Key_ROWID);
        int iName=c.getColumnIndex(Key_NAME);
        int iPeriod=c.getColumnIndex(Key_PERIODS);
        int iCabins=c.getColumnIndex(Key_CABINS);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(iName).equals(search))
            {
                result1=c.getString(iPeriod);
                result2=c.getString(iCabins);
                break;
            }
        }
        return new String[]{result1,result2};
    }

}
