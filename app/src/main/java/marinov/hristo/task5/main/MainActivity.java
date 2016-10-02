package marinov.hristo.task5.main;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import marinov.hristo.task5.R;
import marinov.hristo.task5.database.DatabaseAccess;
import marinov.hristo.task5.utils.FoodAdapter;
import marinov.hristo.task5.utils.FoodObj;
import marinov.hristo.task5.utils.IRecycleViewSelectedItem;

public class MainActivity extends AppCompatActivity implements IRecycleViewSelectedItem {

    Button mBtnGenerate, mBtnLoad;
    TextView mDbCount, mRvCount;
    private ArrayList<FoodObj> foodList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mBtnGenerate = (Button) findViewById(R.id.generateBtn);
        mBtnLoad = (Button) findViewById(R.id.loadBtn);
        mDbCount = (TextView) findViewById(R.id.dbCountTxt);
        mRvCount = (TextView) findViewById(R.id.rvCountTxt);

        foodList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FoodAdapter(foodList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemSelected(int position) {

    }

    public void generateList(View v) {
        new generateList().execute();
    }

    public void loadDatabaseList(View v) {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        foodList.clear();
        foodList = databaseAccess.getFood();
        databaseAccess.close();

        mAdapter = new FoodAdapter(foodList, this);
        mRecyclerView.setAdapter(mAdapter);

        String count = String.format("%s %s", mAdapter.getItemCount(), "rows");
        mRvCount.setText(count);

    }

    private class generateList extends AsyncTask<Void, Void, Void> {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBtnLoad.setEnabled(false);
            databaseAccess.open();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DummyData dummyData = new DummyData();

            for (int i = 0; i < dummyData.productNames.size(); i++) {

                String name = dummyData.productNames.get(i);
                String price = dummyData.productPrice.get(i);
                int rating = dummyData.productRating.get(i);
                String imageName = dummyData.productImages.get(i);

                Bitmap bitmap = getBitmapFromAssets(imageName);
                byte[] photo = getBytes(bitmap);

                databaseAccess.addFood(new FoodObj(name, price, rating, photo));

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String count = String.format("%s %s", databaseAccess.getFood().size(), "rows");
            mDbCount.setText(count);
            databaseAccess.close();
            mBtnLoad.setEnabled(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            databaseAccess.close();
            mBtnLoad.setEnabled(true);
        }
    }

    /**
     * Get Bitmap From Assets Folder
     *
     * @param fileName name of the file
     * @return Bitmap
     */
    public Bitmap getBitmapFromAssets(String fileName) {
        Bitmap bitmap;
        try {
            AssetManager assetManager = this.getAssets();
            InputStream iStr = assetManager.open("images/" + fileName + ".jpeg");
            bitmap = BitmapFactory.decodeStream(iStr);
        } catch (IOException e) {
            bitmap = null;
        }

        return bitmap;
    }

    /**
     * Convert from bitmap to byte array
     *
     * @param bitmap Bitmap
     * @return byte[]
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null)
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        return stream.toByteArray();
    }
}
