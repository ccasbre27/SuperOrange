package com.nansoft.superorange;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.nansoft.superorange.model.Category;

public class MainActivity extends AppCompatActivity {

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            mClient = new MobileServiceClient(
                    "URL_MOBILESERVICES",
                    "LLAVE",
                    this
            );

            LoadCategories();
        } catch (Exception exception) {

        }
    }

    private void LoadCategories()
    {
        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceTable<Category> tableCategory;

            @Override
            protected void onPreExecute() {

                tableCategory = mClient.getTable("category",Category.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {

                    final MobileServiceList<Category> result =
                            tableCategory.execute().get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String aux = "";

                            for(Category category: result)
                            {
                                aux += category.Name + "\n";
                            }

                            Toast.makeText(MainActivity.this, "registros: \n" + aux, Toast.LENGTH_SHORT).show();

                        }
                    });

                } catch (final Exception exception3) {

                }


                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute();
    }
}
