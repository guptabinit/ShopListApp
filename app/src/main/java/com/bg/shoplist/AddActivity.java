package com.bg.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText itemName = findViewById(R.id.name);
        EditText itemPrice = findViewById(R.id.price);
        TextView addBtn = findViewById(R.id.txt_add_item);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = itemName.getText().toString();
                String price = itemPrice.getText().toString();
                long createdTime = System.currentTimeMillis();

                realm.beginTransaction();
                ItemDetails items = realm.createObject(ItemDetails.class);
                items.setItem_name(name);
                items.setPrice(price);
                items.setCreatedTime(createdTime);
                realm.commitTransaction();

                Toast.makeText(getApplicationContext(), "Item Saved", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}