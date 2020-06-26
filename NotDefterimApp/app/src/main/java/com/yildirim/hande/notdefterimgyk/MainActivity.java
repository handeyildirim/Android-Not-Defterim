package com.yildirim.hande.notdefterimgyk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText etBaslik,etDetay;
    Button btnKaydet;
    ListView lvBaslikListe;

    String baslik,detay;
    ArrayList<String> baslikListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaslik = (EditText) findViewById(R.id.editTextBaslik);
        etDetay = (EditText) findViewById(R.id.editTextDetay);
        btnKaydet = (Button) findViewById(R.id.buttonKaydet);
        lvBaslikListe = (ListView) findViewById(R.id.listViewBaslikListe);

        final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,baslikListe);
        lvBaslikListe.setAdapter(adapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference notRef = database.getReference("notlar");

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference notReferans = notRef.push();

                baslik = etBaslik.getText().toString();
                detay = etDetay.getText().toString();

                notReferans.child("baslik").setValue(baslik);
                notReferans.child("detay").setValue(detay);
            }
        });

        notRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                baslikListe.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    baslikListe.add(ds.child("baslik").getValue().toString());

                }

                adapter.notifyDataSetChanged(); //liste güncellendi bu listeyi adaptöre al diyoruz

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
