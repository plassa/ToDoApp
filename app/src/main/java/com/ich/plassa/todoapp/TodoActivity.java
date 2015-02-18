package com.ich.plassa.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.widget.AdapterView.OnItemLongClickListener;


public class TodoActivity extends ActionBarActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etNewItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        items = new ArrayList<>();
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
//        items.add("First Item");
//        items.add("Second Item");
//        items.add("Third Item");
        setupListViewListener();

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
//        android:text="@string/add_button_label"

//        todoItemsAdapter = new ArrayAdapter<string>(this, R.layout.,objects)


    public void onAddItem(View v){
//        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String ItemText = etNewItem.getText().toString();
        itemsAdapter.add(ItemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));

        }  catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            FileUtils.writeLines(todoFile, items);
        }  catch (IOException e) {
//            items = new ArrayList<String>();
                e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
