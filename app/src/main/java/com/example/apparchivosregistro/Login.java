package com.example.apparchivosregistro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    //Variables de inicialización
    ImageView img;
    EditText correo, contra;
    Button iniciar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Asignamos cada variable a su elemento correspondiente en la vista
        img = view.findViewById(R.id.img);
        correo = view.findViewById(R.id.correoTxt);
        contra = view.findViewById(R.id.passTxt);
        iniciar = view.findViewById(R.id.ingresarBtn);

        //Cuando la aplicación inicie, llamamos una función para colocar imagen en imageVien con url
        cargarImg("https://tiriquizrigo.files.wordpress.com/2011/02/logoumg.jpg?w=640", img);

        //Constantes para ingresar al sistema
        final String CORREO = "admin@gmail.com";
        final String PASS = "admin";

        //Al pulsar el boton para iniciar sesión
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Comprobamos que sean las corresctas
                if(correo.getText().toString().equals(CORREO) && contra.getText().toString().equals(PASS)){
                    //Si es así lanzamos la activity correspondiente a la pantalla principal
                    Intent i = new Intent(view.getContext(), Pacientes.class);
                    startActivity(i);
                }else{
                    //Del caso contrario mostramos un toast diciendo que el usuario no existe
                    Context context = view.getContext();
                    CharSequence text = "Usuario no valido  ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        return view;
    }

    //Método para cargar imagen en un imageView mediante una url
    public void cargarImg(String imageUrl, ImageView img){
        Picasso.get()
                .load(imageUrl)
                .into(img);
    }
}