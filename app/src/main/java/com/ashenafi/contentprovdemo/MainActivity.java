package com.ashenafi.contentprovdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id, name, email, phone;
    Button save, read, search, delete;
    dbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new dbHelper(this);

        id = findViewById(R.id.cid);
        name = findViewById(R.id.cname);
        email = findViewById(R.id.cemail);
        phone = findViewById(R.id.cphone);
        save = findViewById(R.id.btnSave);
        read = findViewById(R.id.btnRead);

        search = findViewById(R.id.btnsearch);
        delete = findViewById(R.id.btnDelete);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("")) {
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
                    String ci, cn, ce, cp;
                    try {
                        Cursor c = db.searchName(name.getText().toString());
                        if (c != null && c.getCount() > 0) {
                            while (c.moveToNext()) {
                                ci = c.getString(0);
                                cn = c.getString(1);
                                ce = c.getString(2);
                                cp = c.getString(3);
                                adapter.add("Customer Id: " + ci + "\n" +
                                        "Customer Name: " + cn + "\n" +
                                        "Customer EMail: " + ce + "\n" +
                                        "Customer Phone No: " + cp + "\n");
                            }
                        } else
                            adapter.add("No data");
                        c.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                    b.setTitle("SQLite Data");
                    b.setIcon(R.mipmap.ic_launcher);
                    b.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog ad = b.create();
                    ad.show();
                }

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!id.getText().toString().equals("")  && !name.getText().toString().equals("") &&
                        !email.getText().toString().equals("") &&
                        !phone.getText().toString().equals("")) {
                    try {
                        db.saveData(id.getText().toString(),name.getText().toString(),
                                email.getText().toString(),
                                phone.getText().toString());
                        id.getText().clear();
                        name.getText().clear();
                        email.getText().clear();
                        phone.getText().clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(),
                            "Please fill the empty fields.",
                            Toast.LENGTH_LONG).show();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String var = "-1";
                if(!id.getText().toString().equals("")){
                    var = id.getText().toString();
                }

                final ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1);
                String ci, cn, ce, cp;
                try {
                    Cursor c = db.readData(var);
                    if (c != null && c.getCount() > 0) {
                        while (c.moveToNext()) {
                            ci = c.getString(0);
                            cn = c.getString(1);
                            ce = c.getString(2);
                            cp = c.getString(3);
                            adapter.add("Customer Id: " + ci + "\n" +
                                    "Customer Name: " + cn + "\n" +
                                    "Customer EMail: " + ce + "\n" +
                                    "Customer Phone No: " + cp + "\n");
                        }
                    } else
                        adapter.add("No data");
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("SQLite Data");
                b.setIcon(R.mipmap.ic_launcher);
                b.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog ad = b.create();
                ad.show();
            }
        });
    }
}