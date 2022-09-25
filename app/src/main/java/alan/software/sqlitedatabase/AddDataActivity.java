package alan.software.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;

import alan.software.sqlitedatabase.databinding.ActivityAddDataBinding;

public class AddDataActivity extends AppCompatActivity {

    SQLiteDatabase database;
    int dataId;

    private ActivityAddDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddDataBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        database=openOrCreateDatabase("Datas",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS datas (id INTEGER PRIMARY KEY, name VARCHAR, lesson VARCHAR,note INTEGER)");

        Intent intent=getIntent();
        String getInfo=intent.getStringExtra("info");

        if (getInfo.equals("new")){
            binding.updateButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.GONE);
        }
        else{
            binding.addButton.setVisibility(View.GONE);
            dataId=intent.getIntExtra("dataId",0);

            Cursor cursor=database.rawQuery("select * from datas where id=?",new String[]{String.valueOf(dataId)});
            int nameIndex=cursor.getColumnIndex("name");
            int lessonIndex=cursor.getColumnIndex("lesson");
            int noteIndex=cursor.getColumnIndex("note");

            while (cursor.moveToNext()){
                binding.nameEditText.setText(cursor.getString(nameIndex));
                binding.lessonEditText.setText(cursor.getString(lessonIndex));
                binding.noteEditText.setText(cursor.getString(noteIndex));
            }
            cursor.close();
        }
    }

    public void add(View view) {
        String name=binding.nameEditText.getText().toString();
        String lesson=binding.lessonEditText.getText().toString();
        int note=Integer.valueOf(binding.noteEditText.getText().toString());

        try{
            String sqlString="insert into datas (name,lesson,note) VALUES (?,?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,name);
            sqLiteStatement.bindString(2,lesson);
            sqLiteStatement.bindLong(3,note);
            sqLiteStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        intentMainActivity();
    }

    public void update(View view) {
        try {
            String sqlString="update datas set name=?,lesson=?,note=? where id=?";
            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,binding.nameEditText.getText().toString());
            sqLiteStatement.bindString(2,binding.lessonEditText.getText().toString());
            sqLiteStatement.bindString(3,binding.noteEditText.getText().toString());
            sqLiteStatement.bindLong(4,dataId);

            sqLiteStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        intentMainActivity();
    }

    public void delete(View view) {
        try {
            String sqlString="delete from datas where id=?";
            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
            sqLiteStatement.bindLong(1,dataId);
            sqLiteStatement.execute();


        }catch (Exception e){
            e.printStackTrace();
        }
        intentMainActivity();
    }
    private void intentMainActivity(){
        Intent intent=new Intent(AddDataActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}