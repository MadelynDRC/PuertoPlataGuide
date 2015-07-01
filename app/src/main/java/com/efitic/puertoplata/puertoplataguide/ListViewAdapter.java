package com.efitic.puertoplata.puertoplataguide;

/**
 * Created by madelyn on 4/6/15.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView nombre;
        TextView descripcion;
        TextView coordenadas;
        ImageView foto;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_item_places, parent, false);
        // Get the position
        resultp = data.get(position);

        if (position % 2 == 0){
           itemView.setBackgroundResource(R.drawable.selector2);
        } else {
        //   itemView.setBackgroundResource(R.drawable.selector1);
        }

        ///itemView.getBackground(((toString()) android.R.color.black));
        // Locate the TextViews in xml
       nombre = (TextView) itemView.findViewById(R.id.nombre);
        descripcion = (TextView) itemView.findViewById(R.id.descripcion);

       // Locate the ImageView in xml
        foto = (ImageView) itemView.findViewById(R.id.foto);

        // Capture position and set results to the TextViews
        nombre.setText(resultp.get(PlacesFragment.NOMBRE));
        descripcion.setText(resultp.get(PlacesFragment.DESCRIPCION));

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(PlacesFragment.FOTO), foto);
        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            // Get the position
                resultp = data.get(position);

             Intent intent = new Intent(context, View_Places.class);
            //  Intent intent = new Intent(context, com.efitic.puertoplata.puertoplataguide.detailActivity.class);
                // Pass all data nombre
              intent.putExtra("nombre", resultp.get(PlacesFragment.NOMBRE));
                //Pass all data descripcion
                intent.putExtra("descripcion", resultp.get(PlacesFragment.DESCRIPCION));
                // Pass all data coordenadas
              intent.putExtra("coordenadas",resultp.get(PlacesFragment.COORDENADAS));
                // Pass all data foto
               intent.putExtra("foto", resultp.get(PlacesFragment.FOTO));
                intent.putExtra("web", resultp.get(PlacesFragment.WEB));
                intent.putExtra("phone", resultp.get(PlacesFragment.PHONE));
                intent.putExtra("contenido", resultp.get(PlacesFragment.CONTENIDO));
                intent.putExtra("categoria", resultp.get(PlacesFragment.CATEGORIA));
                // Start SingleItemView Class
             context.startActivity(intent);

            }
        });
        return itemView;
    }
}