package com.bg.shoplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<ItemDetails> itemList;

    public MyAdapter(Context context, RealmResults<ItemDetails> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    //Reformatting time as per our date and time format

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemDetails itemDetails = itemList.get(position);
        holder.nameOutput.setText(itemDetails.getItem_name());
        holder.priceOutput.setText(itemDetails.getPrice());

        String formattedTime = DateFormat.getDateTimeInstance().format(itemDetails.createdTime);
        holder.timeOutput.setText(formattedTime);

        //-------------------------Long-Press Delete----------------------------------------------

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("DELETE")) {
                            //Delete the list
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            itemDetails.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    //Fetching the data and providing it to the designated output places

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameOutput;
        TextView priceOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameOutput = itemView.findViewById(R.id.item_name);
            priceOutput = itemView.findViewById(R.id.item_price);
            timeOutput = itemView.findViewById(R.id.time);

        }
    }
}
