package com.example.yolaman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yolaman.data.DatabaseHandler;
import com.example.yolaman.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private Button saveButton;
    private EditText itemName;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;

    private FloatingActionButton fab;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        bypassActivity();

        List<Item> items = databaseHandler.getAllItems();
        for(Item item: items){
            Log.d("Main", "on create: " + item.getItemName() + " " + items.size());
        }

        fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });
    }

    private void bypassActivity() {
       if( databaseHandler.getItemsCount() > 0){
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
       }
    }

    private void createPopUpDialog() {
        Context context;
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        itemName = view.findViewById(R.id.itemName);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        itemColor = view.findViewById(R.id.itemColor);
        itemSize = view.findViewById(R.id.itemSize);
        saveButton = view.findViewById(R.id.saveButton);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!itemName.getText().toString().isEmpty()
                && !itemColor.getText().toString().isEmpty()
                && !itemQuantity.getText().toString().isEmpty()
                && !itemSize.getText().toString().isEmpty()){
                    saveItem(v);
                }


            }
        });
    }

    private void saveItem(View v) {
        //Todo: Save item in db
        //Todo: Move to next screen
        Item item = new Item();

        String newItem = itemName.getText().toString().trim();
        String newColor = itemColor.getText().toString().trim();
        int quantity = Integer.parseInt(itemQuantity.getText().toString().trim());
        int size = Integer.parseInt(itemSize.getText().toString().trim());

        item.setItemName(newItem);
        item.setItemColor(newColor);
        item.setItemQuantity(quantity);
        item.setItemSize(size);

        databaseHandler.addItem(item);

        Snackbar.make(v, "Item Saved", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);

    }
}