package marinov.hristo.task5.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import marinov.hristo.task5.R;

/**
 * @author HristoMarinov (christo_marinov@abv.bg).
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private IRecycleViewSelectedItem mListener;
    private ArrayList<FoodObj> mAdapterData;

    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ImageView photo;
        TextView name, price;
        RatingBar rating;

        public void setItemPosition(int position) {
            this.position = position;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price_txt);
            rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
            photo = (ImageView) itemView.findViewById(R.id.photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(position);
                }
            });
        }
    }

    public FoodAdapter(ArrayList<FoodObj> data, IRecycleViewSelectedItem listener) {
        this.mAdapterData = data;
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mAdapterData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder != null) {
            FoodObj data = mAdapterData.get(position);

            holder.name.setText(data.get_name());
            holder.price.setText(String.format("%s %s", data.get_price(), "$"));
            holder.rating.setRating(data.get_rating());
            holder.photo.setImageBitmap(getImage(data.getImage()));

            holder.setItemPosition(position);
        }
    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
