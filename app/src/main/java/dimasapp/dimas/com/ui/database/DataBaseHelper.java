package dimasapp.dimas.com.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.icu.text.MessagePattern.ArgType.SELECT;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "usersurname";
    public static final String COL_4 = "password";
    public static final String COL_5 = "email";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE users (ID  INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, usersurname TEXT, password TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            onCreate(sqLiteDatabase);
    }

    public void dropTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE users");
    }

    public long addUser(String username, String usersurname, String password, String email){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("usersurname", usersurname);
        contentValues.put("password", password);
        contentValues.put("email", email);
        long res = db.insert("users", null, contentValues);
        db.close();
        return res;
    }

   public boolean checkEmail(String email)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COL_5 + " FROM " + TABLE_NAME + " WHERE " + COL_5 + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.getCount() > 0)
        {
            return false;
        }
        else
            return true;
    }

    public boolean checkUser(String email, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_5 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0)
            return true;
        else
            return false;
    }

}
