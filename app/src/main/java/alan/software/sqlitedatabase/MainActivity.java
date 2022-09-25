package alan.software.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import alan.software.sqlitedatabase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    int id,note;
    String name,lesson;

    ArrayList<Model> arrayList;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        arrayList=new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new Adapter(arrayList);
        binding.recyclerView.setAdapter(adapter);

        getData();

    }

    private void getData() {

        try{
            SQLiteDatabase database=this.openOrCreateDatabase("Datas",MODE_PRIVATE,null);

            Cursor cursor=database.rawQuery("select * from datas",null);
            int idIndex=cursor.getColumnIndex("id");
            int nameIndex=cursor.getColumnIndex("name");
            int lessonIndex=cursor.getColumnIndex("lesson");
            int noteIndex=cursor.getColumnIndex("note");

            while (cursor.moveToNext()){
                id=cursor.getInt(idIndex);
                name=cursor.getString(nameIndex);
                lesson=cursor.getString(lessonIndex);
                note=cursor.getInt(noteIndex);

                Model model=new Model(id,name,lesson,note);
                arrayList.add(model);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addData(View view) {
        Intent intent=new Intent(MainActivity.this,AddDataActivity.class);
        intent.putExtra("info","new");
        startActivity(intent);
    }
}