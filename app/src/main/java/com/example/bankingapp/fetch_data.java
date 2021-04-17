package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class fetch_data extends AppCompatActivity {
    DatabaseHelper myDB;
    ArrayList<String> Name;
    ArrayList<String> Email;
    ArrayList<Integer> Balance;
    ArrayList<String> PhoneNumber;
    ArrayList<String> Account_Number;
    Adapter adapter;

    private Adapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        RecyclerView ViewList = (RecyclerView) findViewById(R.id.viewRecycler);

        myDB = new DatabaseHelper(fetch_data.this);
        //  id= new ArrayList<>();
        Name= new ArrayList<>();
        Email = new ArrayList<>();
        Balance = new ArrayList<>();
        PhoneNumber = new ArrayList<>();
        Account_Number = new ArrayList<>();

        storeDataInArrays();
        setOnClickListner();

        adapter = new Adapter(fetch_data.this,Name,Email,Balance, listener);
        ViewList.scheduleLayoutAnimation();
        ViewList.setAdapter(adapter);
        ViewList.setLayoutManager(new LinearLayoutManager(fetch_data.this));
    }

    void storeDataInArrays(){
        SQLiteDatabase database = myDB.getReadableDatabase();
        ContentValues values =new ContentValues();

        Cursor cursor = database.rawQuery("SELECT NAME, EMAIL, BALANCE,PHONENUMBER ,ACCOUNT  FROM USER_DETAILS", new String[]{});

        if(cursor != null){
            cursor.moveToFirst();
        }

        do{
            String name = cursor.getString(0);
            int balance = cursor.getInt(2);
            String email = cursor.getString(1);
            String phoneNumber = cursor.getString(3);
            String account = cursor.getString(4);
            Name.add(name);
            Email.add(email);
            Balance.add(balance);
            Account_Number.add(account);
            PhoneNumber.add(phoneNumber);
        }while(cursor.moveToNext());

    }
    private void setOnClickListner(){
        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Detail.class);
                intent.putExtra("name",Name.get(position));
                intent.putExtra("email",Email.get(position));
                intent.putExtra("balance",Balance. get(position));
                intent.putExtra("account",Account_Number. get(position));
                intent.putExtra("phone",PhoneNumber. get(position));
                startActivity(intent);
            }
        };
    }
}