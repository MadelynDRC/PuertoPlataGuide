package com.efitic.puertoplata.puertoplataguide;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.MotionEvent;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.app.Activity;

import org.w3c.dom.Text;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class view_PlacesFragment extends Fragment {

    // ImageLoader imageLoader;
    // Declare Variables

    String nombre;
    String descripcion;
    String coordenadas;
    String foto;
    String web;
    String phone;
    String categoria;
    String contenido;
    String position;
    ImageView fotourl;
    ImageView fotodirection;
    ImageView thephone;
    ImageView theweb;

    ImageLoader myimageLoader = new ImageLoader(getActivity());



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_places, container, false);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view

        getActivity().setContentView(R.layout.fragment_view_places);

        final Intent i = getActivity().getIntent();
        // Get the result of nombre
        nombre = i.getStringExtra("nombre");
        // Get the result of descripcion
        descripcion = i.getStringExtra("descripcion");
        // Get the result of coordenadas
        coordenadas = i.getStringExtra("coordenadas");
        // Get the result of foto
        foto = i.getStringExtra("foto");
        web = i.getStringExtra("web");
        phone = i.getStringExtra("phone");

        categoria = i.getStringExtra("categoria");
        contenido = i.getStringExtra("contenido");

        // Locate the TextViews in xml
        TextView txtnombre = (TextView) getActivity().findViewById(R.id.nombre);
        TextView txtdescripcion = (TextView) getActivity().findViewById(R.id.descripcion);
        TextView txtcoordenadas = (TextView) getActivity().findViewById(R.id.coordenadas);


        txtdescripcion.setMovementMethod(new ScrollingMovementMethod());


        // Locate the ImageView in xml

        // ImageView imgfoto = (ImageView) getActivity().findViewById(R.id.foto);
        fotourl = (ImageView) getActivity().findViewById(R.id.foto);
        fotodirection = (ImageView) getActivity().findViewById(R.id.direction);
        theweb= (ImageView) getActivity().findViewById(R.id.web);
        thephone = (ImageView) getActivity().findViewById(R.id.call);

        if(web.length() == 0 )
        {
            theweb.setVisibility(View.INVISIBLE);

       }

        if(phone.length() == 0  )
        {
            thephone.setVisibility(View.INVISIBLE);

        }

        if(coordenadas.length() == 0 )
        {
            fotodirection.setVisibility(View.INVISIBLE);


        }

        // Set results to the TextViews
        txtnombre.setText(nombre);
        txtdescripcion.setText(descripcion + '\n' + contenido);
        txtcoordenadas.setText(coordenadas);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

        // Picasso.with(getActivity()).load(foto).into(fotourl);

        myimageLoader.DisplayImage(foto, fotourl);

        fotodirection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position

                // Pass all data flag
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + coordenadas));
                startActivity(intent);
            }

        });

         String url = web;

        theweb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // WebSite


                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(web));
                    startActivity(i);

            }

        });



        thephone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Call
                String tel = phone;

                    Uri call = Uri.parse("tel:" + tel);
                    Intent i = new Intent(Intent.ACTION_DIAL, call);
                    startActivity(i);

            }

        });

    }
}