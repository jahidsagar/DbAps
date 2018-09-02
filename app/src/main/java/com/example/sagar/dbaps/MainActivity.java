package com.example.sagar.dbaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText id , name , phone;
    Button btnadd , btnshow , btnupdate , btnremove , btnremoveall , btnsubmit;
    LinearLayout linearLayout;
    boolean addbool,idbool , editbool = false;
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.edit1);
        name = findViewById(R.id.edit2);
        phone = findViewById(R.id.edit3);
        btnadd = findViewById(R.id.addbtn);
        btnshow = findViewById(R.id.show);
        btnupdate = findViewById(R.id.update);
        btnremove = findViewById(R.id.remove);
        btnremoveall = findViewById(R.id.removeall);
        btnsubmit = findViewById(R.id.submit);
        linearLayout = findViewById(R.id.contacts);



        Log.e("dbw", "inserting...");
        final Dbhandler dbhandler = new Dbhandler(this);

//        dbhandler.addContact(new Contact("tmi", "56416516"));
//        dbhandler.addContact(new Contact("se", "53753753"));
//        dbhandler.addContact(new Contact("tara", "1254848"));
//        dbhandler.addContact(new Contact("amra", "058491961"));
//        dbhandler.addContact(new Contact("ami", "01258693"));

        showall();


//        int k = dbhandler.removeall();
//        Log.e("st", "value we get "+k);
//        int j = dbhandler.removeall();
//        Log.e("nd", "value we get "+j);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                btnsubmit.setVisibility(View.VISIBLE);
                n = 1;

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (n){
                    case 1://add the value
                        String n = String.valueOf(name.getText());
                        String p = phone.getText().toString();
                        if ( n.isEmpty() || p.isEmpty() && addbool == true){
                            Toast.makeText(MainActivity.this , "value is empty",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dbhandler.addContact(new Contact(n, p));
                        name.setText("");
                        name.setVisibility(View.INVISIBLE);
                        phone.setText("");
                        phone.setVisibility(View.INVISIBLE);
                        btnsubmit.setVisibility(View.INVISIBLE);
                        showall();
                        break;
                    case 2://remove the value with id
                        int i = Integer.parseInt(id.getText().toString());
                        id.setText("");
                        id.setVisibility(View.INVISIBLE);
                        btnsubmit.setVisibility(View.INVISIBLE);
                        int k = dbhandler.deleteContact(i);
                        Toast.makeText(MainActivity.this , "affected : "+k,Toast.LENGTH_SHORT).show();
                        showall();
                        break;
                    case 3:
                        i = Integer.parseInt(id.getText().toString());
                        n = String.valueOf(name.getText());
                        p = phone.getText().toString();

                        id.setText("");
                        id.setVisibility(View.INVISIBLE);
                        name.setText("");
                        name.setVisibility(View.INVISIBLE);
                        phone.setText("");
                        phone.setVisibility(View.INVISIBLE);
                        btnsubmit.setVisibility(View.INVISIBLE);

                        k = dbhandler.updateContact(new Contact(i , n , p));
                        Toast.makeText(MainActivity.this , "affected : "+k,Toast.LENGTH_SHORT).show();
                        showall();
                        break;
                }

            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                btnsubmit.setVisibility(View.VISIBLE);
                n = 3;


            }
        });
        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setVisibility(View.VISIBLE);
                btnsubmit.setVisibility(View.VISIBLE);
                n = 2;

            }
        });
        btnremoveall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k = dbhandler.removeall();
                Toast.makeText(MainActivity.this , "total affected :"+k,Toast.LENGTH_SHORT).show();
                showall();
            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getVisibility() ==View.VISIBLE ){
                    linearLayout.setVisibility(View.INVISIBLE);
                    btnshow.setText("show");
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                    btnshow.setText("hide");
                }
            }
        });
    }

    public void showall(){
        linearLayout.removeAllViews();
        List<Contact> list = new ArrayList<Contact>();
        Dbhandler dbhandler = new Dbhandler(this);

        list = dbhandler.getAll();

        for (Contact contact : list) {
            String s = "id : "+contact.getId()+"; name : "+contact.getName()+"; phone "+contact.getPhone();

            TextView dynamicText = new TextView(this);
            dynamicText.setText(s);
            dynamicText.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(dynamicText);

        }
    }
}
