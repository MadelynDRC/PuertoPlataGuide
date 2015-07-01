package com.efitic.puertoplata.puertoplataguide;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.text.format.Time;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.List;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import java.util.ArrayList;
        import java.util.HashMap;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.ListView;

        //import it.sephiroth.android.library.picasso.Picasso;

        import android.content.SharedPreferences;

   import android.preference.PreferenceManager;



/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class PlacesFragment extends Fragment {

  //  private ArrayAdapter<String> PlacesAdapter;

    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;

    ListView lugares;
    ListView categorias;
    ListViewAdapter adapter;
    ListViewAdapter adapterc;
    ProgressDialog MiDialogo;
    ArrayList<HashMap<String, String>> arraylist;
    static String NOMBRE = "nombre";
    static String DESCRIPCION = "descripcion";
    static String COORDENADAS = "coordenadas";
    static String FOTO = "foto";
    static String WEB = "web";
    static String PHONE = "phone";
    static String CONTENIDO = "contenido";


    static String CATEGORIA = "categoria";
    static String ICON = "icon";
    private ArrayAdapter<String> PlacesAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);


        setContentView(R.layout.list_item_places);
        // Execute DownloadJSON AsyncTask
        new FetchPlacesTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.placesfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

      /*  if (id == R.id.action_refresh) {
            // call AsynTask to perform network operation on separate thread
      //   new FetchPlacesTask().execute("http://www.puertoplata.com.do/api/lugares/es");
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ImageView iconView;
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.


    PlacesAdapter  =
                new ArrayAdapter<String>(
                      getActivity(), // The current context (this activity)
                       R.layout.list_item_places, // The name of the layout ID.
                       R.id.descripcion, // The ID of the textview to populate.
                        new ArrayList<String>());




        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        iconView = (ImageView) rootView.findViewById(R.id.foto);


        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.lugares);


        listView.setAdapter(PlacesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         /*   @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String places = PlacesAdapter.getItem(position);
                Toast.makeText(getActivity(), places, Toast.LENGTH_SHORT).show();
            }
        });*/

            // ImageView iconView = (ImageView) findViewById(R.id.list_item_icon);


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String places = PlacesAdapter.getItem(position);


                Intent intent = new Intent(getActivity(), View_Places.class)
                        .putExtra(Intent.EXTRA_TEXT, places);


                startActivity(intent);


            }

        });


        return rootView;
}


    private void updatePlaces() {
        FetchPlacesTask placesTask = new FetchPlacesTask();
       // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //tring location = prefs.getString(getString(R.string.pref_location_key),
          //      getString(R.string.pref_location_default));


        new FetchPlacesTask().execute();

    }

    @Override
    public void onStart() {
        super.onStart();
        updatePlaces();
    }


    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public void setContentView(int contentView) {
       // this.contentView = contentView;
    }

    //  public class FetchPlacesTask extends AsyncTask <String, Void, Void> {
 // private class FetchPlacesTask extends AsyncTask<String, Void, String> {
      // DownloadJSON AsyncTask
      private class FetchPlacesTask extends AsyncTask<Void, Void, Void> {

          @Override
          protected void onPreExecute() {
              super.onPreExecute();



                  // Create a progressdialog
               //  MiDialogo = new ProgressDialog(getActivity());
                  // Set progressdialog title
               //  MiDialogo.setTitle("");
                  // Set progressdialog message
               //  MiDialogo.setMessage("Waiting ...");
              //   MiDialogo.setIndeterminate(false);
                  // Show progressdialog
               //   MiDialogo.show();


          //    ProgressDialog MiDialogo;
            //  MiDialogo = ProgressDialog.show(getActivity(), "",
            //   "Waiting ...", true);

       // MiDialogo.show();

          }


      @Override
      protected Void doInBackground(Void... params) {
          // Create an array
          arraylist = new ArrayList<HashMap<String, String>>();



          SharedPreferences pref =
                  PreferenceManager.getDefaultSharedPreferences(
                          getActivity());


          String language = pref.getString("pref_languages_key", "");

          if (language.equals("es") ) {
              jsonobject = JSONfunctions
                      .getJSONfromURL("http://puertoplata.com.do/api/lugares/es");

          }  else {

              jsonobject = JSONfunctions
                      .getJSONfromURL("http://puertoplata.com.do/api/lugares/en");

          }

          try {

              jsonarray = jsonobject.getJSONArray("lugares");


              for (int i = 0; i < jsonarray.length(); i++) {
                  HashMap<String, String> map = new HashMap<String, String>();
                  jsonobject = jsonarray.getJSONObject(i);
                  // Retrive JSON Objects'
                  map.put("nombre", jsonobject.getString("nombre"));
                  map.put("descripcion", jsonobject.getString("descripcion"));
                  map.put("coordenadas", jsonobject.getString("coordenadas"));
                  map.put("foto", jsonobject.getString("foto"));
                  map.put("web", jsonobject.getString("web"));
                  map.put("phone", jsonobject.getString("telefono"));
                  map.put("contenido", jsonobject.getString("contenido"));
                  map.put("categoria", jsonobject.getString("categoria"));
                  // Set the JSON Objects into the array
                  arraylist.add(map);
              }

          } catch (JSONException e) {
              Log.e("Error", e.getMessage());
              e.printStackTrace();
          }
          return null;
      }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void args)  {


            // Locate the listview in listview_main.xml
            lugares = (ListView) getActivity().findViewById(R.id.lugares);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(getActivity(), arraylist);
            // Set the adapter to the ListView
            lugares.setAdapter(adapter);


     //  MiDialogo.dismiss();



        }



  }






}


