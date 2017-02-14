package com.hm.greendao;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hm.bean.Note;
import com.hm.bean.NoteDao;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private DbService dbService;
    private ListView listView;
    private List<String> myData = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private  List<Note> note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.id_et);
        listView = (ListView) findViewById(R.id.id_lv);
        MyApplication.getDaoMaster(this);
        dbService =  DbService.getInstance(this);
        setData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note bean = note.get(position);
                dbService.deleteNote(bean);
                setData();
            }
        });

    }
    private  void getData(){
        note = dbService.loadAllNote();
        if (null == note || note.size()<0) return;
        myData.clear();
        for (Note bean : note){
            myData.add(bean.getComment());
        }
    }
    private void setData(){
        getData();
        if (myData.size() > 0){
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myData);
            listView.setAdapter(adapter);
        }
    }
    public void  addToDb(View view){
        String noteText = editText.getText().toString();
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        Note note = new Note(null, comment, noteText, new Date());
        dbService.saveNote(note);
        setData();
    }
}
