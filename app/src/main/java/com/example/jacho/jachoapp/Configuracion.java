package com.example.jacho.jachoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Configuracion extends AppCompatActivity {
    Button bt3;
    Button bt4;
    Button bt5;
    Spinner sp;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    EditText ed4;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        bt3 = (Button) findViewById(R.id.button3);
        bt4 = (Button) findViewById(R.id.button4);
        bt5 = (Button) findViewById(R.id.button5);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        sp = (Spinner) findViewById(R.id.spinner);
        arrayList = new ArrayList<String>();
        arrayList.add("idMateria");

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_layout, arrayList);
        adapter.setDropDownViewResource(R.layout.custom_layout);
        sp.setAdapter(adapter);
        new BuscarMaterias().execute();

        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new InsertarPersona().execute();
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new ModificarPersona().execute();
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new EliminarPersona().execute();
            }
        });



    }

    public class InsertarPersona extends AsyncTask<String, Void, ArrayList<String>> {
        String id;
        String nombre;
        String apellido;
        String telefono;
        String idMat;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = ed1.getText().toString();
            nombre = ed2.getText().toString();
            apellido = ed3.getText().toString();
            telefono = ed4.getText().toString();
            idMat =  sp.getSelectedItem().toString();

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            JSONObject jsonObject = new JSONObject();

            try {

                //Log.d("Producto doInBackgroud",producto.getNombreProducto());

                jsonObject.put("cedula", id);
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellido", apellido);
                jsonObject.put("telefono", telefono);
                jsonObject.put("idmateria", idMat);


                URL url=new URL ("http://puceing.edu.ec:13000/JachoL/api/Persona/");
                String message = jsonObject.toString();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();
                os.close();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(ArrayList<String> paradas) {

        }

    }
    public class ModificarPersona extends AsyncTask<String, Void, ArrayList<String>> {
        String id;
        String nombre;
        String apellido;
        String telefono;
        String idMat;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = ed1.getText().toString();
            nombre = ed2.getText().toString();
            apellido = ed3.getText().toString();
            telefono = ed4.getText().toString();
            idMat = sp.getSelectedItem().toString();

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            JSONObject jsonObject = new JSONObject();

            try {



                jsonObject.put("cedula", id);
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellido", apellido);
                jsonObject.put("telefono", telefono);
                jsonObject.put("idmateria", idMat);


                URL url=new URL ("http://puceing.edu.ec:13000/JachoL/api/Persona/"+id.toString());
                String message = jsonObject.toString();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("PUT");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();
                os.close();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }


        protected void onPostExecute(ArrayList<String> paradas) {

        }

    }

    public class EliminarPersona extends AsyncTask<String, Void, ArrayList<String>> {
        String id;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = ed1.getText().toString();


        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            JSONObject jsonObject = new JSONObject();

            try {


                URL url=new URL ("http://puceing.edu.ec:13000/JachoL/api/Persona/"+id);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
                httpCon.setRequestMethod("DELETE");
                httpCon.connect();
                httpCon.getInputStream();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(ArrayList<String> paradas) {

        }

    }
    public class BuscarMaterias extends AsyncTask<String, Void, ArrayList<String>> {

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

                URL url = new URL("http://puceing.edu.ec:13000/JachoL/api/Materia");

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
                    int id = jObject.getInt("idmateria");

                    paradas.add(id+"");
                    arrayList.add(id+"");
                    Log.i("El producto es ", id+"");
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
