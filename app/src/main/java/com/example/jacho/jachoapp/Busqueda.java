package com.example.jacho.jachoapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Busqueda extends AppCompatActivity {
    Button bt6;
    Button bt7;
    EditText ed5;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        bt6 = (Button) findViewById(R.id.button6);
        bt7 = (Button) findViewById(R.id.button7);

        ed5 = (EditText) findViewById(R.id.editText5);
        lista = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_layout, arrayList);
        adapter.setDropDownViewResource(R.layout.custom_layout);
        lista.setAdapter(adapter);

        bt6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                new BuscarPersonaId().execute();
            }
        });
        bt7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                new BuscarPersonas().execute();
            }
        });
    }

    public class BuscarPersonaId extends AsyncTask<String, Void, ArrayList<String>> {
        String id;
        String nombre;
        String apellido;
        String telefono;
        String idMat;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = ed5.getText().toString();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            String[] resultado;
            StringBuilder content = new StringBuilder();

            // many of these calls can throw exceptions, so i've just
            // wrapped them all in one try/catch statement.
            try {

                URL url = new URL("http://puceing.edu.ec:13000/JachoL/api/Persona/" + id);

                // create a urlconnection object
                URLConnection urlConnection = url.openConnection();

                // wrap the urlconnection in a bufferedreader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    content.append(line + "\n");
                    Log.d("resultado:", line);
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Resultado:", content.toString());


            ArrayList<String> paradas = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject(content.toString());
                //JSONArray jArray = new JSONArray(content.toString());
                //for (int i = 0; i < jArray.length(); i++) {

                //  JSONObject jObject = jArray.getJSONObject(i);
                nombre = jsonObject.getString("nombre");
                apellido = jsonObject.getString("apellido");
                telefono = jsonObject.getString("telefono");
                idMat = jsonObject.getString("idmateria");

                paradas.add(nombre);
                arrayList.add("Nombre: "+nombre);
                arrayList.add("Apellido: "+apellido);
                arrayList.add("telefono: "+telefono);
                arrayList.add("idMateria: "+idMat);
                Log.i("El producto es ", nombre);
                //}

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return paradas;

        }
        protected void onPostExecute(ArrayList<String> paradas) {

            adapter.notifyDataSetChanged();


        }
    }

    public class BuscarPersonas extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            String[] resultado;
            StringBuilder content = new StringBuilder();

            // many of these calls can throw exceptions, so i've just
            // wrapped them all in one try/catch statement.
            try {

                URL url = new URL("http://puceing.edu.ec:13000/JachoL/api/Persona");

                // create a urlconnection object
                URLConnection urlConnection = url.openConnection();

                // wrap the urlconnection in a bufferedreader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    content.append(line + "\n");
                    Log.d("resultado:", line);
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Resultado:", content.toString());

            JSONObject jsonObject;
            ArrayList<String> paradas = new ArrayList<String>();
            try {
                JSONArray jArray = new JSONArray(content.toString());
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);
                    String id = jObject.getString("cedula");
                    String name = jObject.getString("nombre");
                    String telefono = jObject.getString("telefono");
                    String texto = id+", "+name+",tlf: "+telefono;
                    paradas.add(name);
                    arrayList.add(texto);
                    Log.i("El producto es ", name);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return paradas;

        }


        protected void onPostExecute(ArrayList<String> paradas) {

            adapter.notifyDataSetChanged();
        }

    }
}
