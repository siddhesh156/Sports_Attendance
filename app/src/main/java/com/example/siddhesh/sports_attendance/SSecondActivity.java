package com.example.siddhesh.sports_attendance;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SSecondActivity extends AppCompatActivity {
     ListView listView;
    private ArrayList<MyDataModel> list;
     ArrayList<String> list2;
     ArrayAdapter<String> list1;
    private MyArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssecond);

        //list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        list2 = new ArrayList<String>();
        /**
         * Binding that List to Adapter
         */
        //adapter = new MyArrayAdapter(this, list);
        list1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list2);



        /**
         * Getting List and Setting List Adapter
         */

        listView.setAdapter(list1);
        //listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position).getPhone(), Snackbar.LENGTH_LONG).show();
            }
        });*/

        /*if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
           // new Toast(getApplicationContext(),"internet connection is not available",Toast.LENGTH_SHORT).show();
        }*/


        new GetDataTask().execute();
    }


    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
/*
            x=list.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            dialog = new ProgressDialog(SSecondActivity.this);
            dialog.setTitle("Hey Wait Please..."+x);
            dialog.setMessage("I am getting your JSON");
            dialog.show();

            */
        }


        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();
            Log.d("jsobjectprint",jsonObject.toString());

            try {

                /**
                 * Check Whether Its NULL???
                 */
                //if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {


                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray("records");
                        Log.d("sfsdgsdg","hello world");

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                       // if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                MyDataModel model = new MyDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString("uid_names");




                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);



                                model.setUid_names(name);
                                String d = model.getUid_names();
                                Log.e("sdffef",d);


                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                //list.add(model);
                                list2.add(name);

                            }
                        }
                    //}
                //} else {

                //}
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "+++++++++" + je.getLocalizedMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //dialog.dismiss();

            list1.notifyDataSetChanged();

            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            /*if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
               // Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }*/
        }
    }
}
