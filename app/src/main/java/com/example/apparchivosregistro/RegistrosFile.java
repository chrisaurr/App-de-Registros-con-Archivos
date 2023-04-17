package com.example.apparchivosregistro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrosFile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrosFile extends Fragment {
    //Variables de inicialización
    EditText dpi, nombre, direccion, telefono, temperatura;
    Button guardar, salir, consultar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrosFile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrosFile.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrosFile newInstance(String param1, String param2) {
        RegistrosFile fragment = new RegistrosFile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registros_file, container, false);

        //Asociamos cada variable con su elemento correspondiente en la vista
        dpi = view.findViewById(R.id.dpiTxt);
        nombre = view.findViewById(R.id.nombreTxt);
        direccion = view.findViewById(R.id.direccionTxt);
        telefono = view.findViewById(R.id.telefonoTxt);
        temperatura = view.findViewById(R.id.temperaturaTxt);
        guardar = view.findViewById(R.id.guardarBtn);
        salir = view.findViewById(R.id.salirBtn);
        consultar = view.findViewById(R.id.consultarBtn);

        //Al presionar el botón guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extraemos todos los datos obtenidos en la vista separados por espacios
                String data = dpi.getText().toString() + " " + nombre.getText().toString() + " " +
                        direccion.getText().toString() + " " + telefono.getText().toString() + " " +
                        temperatura.getText().toString();

                //Método para guardar cierta información en una archivo determinado
                saveLocal(data, "registro.txt");

            }
        });


        //Al presionar salir
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Regresamos a la vista del login
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
                //leerArchivo("4567", "registro.txt");
            }
        });

        //Al presionar consultar
        consultar.setOnClickListener(new View.OnClickListener() {
            //Llamamos método para consultar información
            @Override
            public void onClick(View v) {
                leerArchivo(dpi.getText().toString(), "registro.txt");
            }
        });
        return view;
    }


    public void guardar(View v) {
        String dpiText = dpi.getText().toString();
        String nombreText = nombre.getText().toString();

        try {
            OutputStreamWriter archivo = new OutputStreamWriter(v.getContext().openFileOutput("prueba.txt", Context.MODE_PRIVATE));
            archivo.write(dpiText);
            archivo.write(nombreText);
            archivo.flush();
            archivo.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Método para guardar informacion
    public void saveLocal(String fileString, String Adress) {

        //Obtenemos ruta del archivo y variables para su lectura
        File directory = getContext().getFilesDir(); //or getExternalFilesDir(null); for external storage
        String file1 = String.valueOf(new File(directory, Adress));
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            //Cada vez que se introduzca un dato nuevo se añade un salto de linea para separarlos
            String data = fileString + "\n";

            //Creamos archivo
            File file = new File(file1);
            // Si el archivo no existe, se crea!
           if (!file.exists()) {//Si no existe creamos uno nuevo
                file.createNewFile();
           }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            //Obtenemos cada uno de los elementos separados por espacios
            String[] lista1 = data.split(" ");
            String busqueda = lista1[0].toString();

            if (isExist(busqueda, "registro.txt")) { //Si el dpi no existe
                bw.write(data); //Añadimos la nueva información al archivo
            } else { //Caso contrario
                //Avisamos que ya existe el numero de dpi
                System.out.println("Numero de Dpi y existe");
            }

            //System.out.println("información agregada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void leerArchivo(String buscar, String nomArchivo) {
        String param = dpi.getText().toString();

        try {

            //Variables para leer información y separar palabras segun los espacios
            InputStreamReader archivo = new InputStreamReader(getActivity().openFileInput(nomArchivo));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String valor1 = linea;
            String[] lista1 = valor1.split(" ");
            String busqueda = lista1[0].toString();
            String contenido = "";
            //v = true;

           /* System.out.println(busqueda);
            System.out.println(buscar);
            System.out.println(busqueda == buscar);*/
            while (linea != null) { //Hasta que no hayan mas lineas por recorrer
                System.out.println(busqueda+"="+buscar);
                if (busqueda.equals(buscar)) { //Cuando alguno de los dpi guardados coincida con el que buscaremos
                    contenido = contenido + linea + "\n"; //Añadirlo a ua variable contenido
                    //return false;
                   // v = false;
                    break; //Salir del bucle
                }
                // linea2 = br.readLine();

                // if(linea2.length() >= 0){

                linea = br.readLine(); //Avanza una nueva linea por cada vuelta
                valor1 = linea;

                //Seguimos separando valores de la nueva linea
                lista1 = valor1.split(" ");
                busqueda = lista1[0].toString();
                System.out.println(busqueda);

                //  }


            }

            br.close();
            archivo.close();

            //Separamos el valor que estabamos buscando
            String valor = contenido;
            String[] lista = valor.split(" ");

            //Lo seteamos en cada uno de los elementos en la vista
            dpi.setText(lista[0]);
            nombre.setText(lista[1]);
            direccion.setText(lista[2]);
            telefono.setText(lista[3]);
            temperatura.setText(lista[4]);
            Log.v("prueba", contenido);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("a");
        }catch(NullPointerException ex){
            System.out.println("null");
          /*  linea = br.readLine();
            valor1 = linea;

            lista1 = valor1.split(" ");
            busqueda = lista1[0].toString();*/


        }

    }

    //Método para saber si un dpi ya existe dentro del archivo
    public boolean isExist(String buscar, String nomArchivo) {
        String param = dpi.getText().toString();
        boolean v = true;
        try {
            //Variables para la lectura del archivo, recorrerlo e identificar un dato especifico por cada persona
            InputStreamReader archivo = new InputStreamReader(getActivity().openFileInput(nomArchivo));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String valor1 = linea;
            String[] lista1 = valor1.split(" ");
            String busqueda = lista1[0].toString();
            String contenido = "";

            //Variable de retorno inicializada en verdadero
            v = true;

           /* System.out.println(busqueda);
            System.out.println(buscar);
            System.out.println(busqueda == buscar);*/
            while (linea != null) { //Hasta que no hayan mas lineas
                System.out.println(busqueda+"="+buscar);

                if (busqueda.equals(buscar)) { //Si el valor de dpi ingresado ya se encuentra en el archivo
                    contenido = contenido + linea + "\n";
                    //return false;
                    v = false; //La variable de retorno ahora será falsa
                    break; //Salimos del bucle
                }

                //Seguimos avanzando a una nueva linea y obtenemos solo el valor correspondiente al dpi
                linea = br.readLine();
                valor1 = linea;
                lista1 = valor1.split(" ");
                busqueda = lista1[0].toString();
                System.out.println(linea);
            }

            br.close();
            archivo.close();

           // return v;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("a");
        } catch(NullPointerException ex){
            System.out.println("a");
        }
        return v; //Retornamos un booleano
    }

}