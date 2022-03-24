package com.sax.inc.coetegaz.Adaptaters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sax.inc.coetegaz.Entites.EClient;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import customfonts.MyTextView_Roboto_Regular;

public class RecylerAdapterClient extends RecyclerView.Adapter<RecylerAdapterClient.ViewHolder> {
    private List<EClient> items;
    private AppCompatActivity activity;
    private Context cxt;

    public interface ItemButtonListener {

        void onUpdateClickListener(int position);
        void onItemClickListener(int position);
    }

    private  final ItemButtonListener callback_click;


    public RecylerAdapterClient(AppCompatActivity appCompatActivity, List<EClient> items, ItemButtonListener callback) {
        this.items = items;
        this.activity=appCompatActivity;
        callback_click=callback;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecylerAdapterClient.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_client, parent, false);


        return new ViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EClient item =items !=null ? items.get(position):null;


        if(item!=null)
        {

            holder.txt_designation.setText(item.getName());

            holder.txt_Id.setText(item.getTelephone());
            if(item.getStatus()==0){
                holder.txt_Id.setVisibility(View.GONE);
            }else{
                holder.txt_Id.setVisibility(View.VISIBLE);
                holder.txt_Id.setText(item.getToken());
            }

            String date= UtilTimeStampToDate.convert(item.getDatewrite());
            String [] tab=date.split(" ");
            holder.txt_date.setText(tab[0]);



        }
                holder.updateWith(this.callback_click);
                holder.updateItemClick(this.callback_click);
        }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(EClient item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(EClient item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void change(EClient item) {
        int position = items.indexOf(item);
        notifyItemChanged(position);
    }



    public void notify(List<EClient> list_items) {

        //items.clear();
//        items.addAll(list_items);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public MyTextView_Roboto_Regular txt_designation,txt_Id,txt_date,
                BtRecharge;

        public ViewHolder(View v) {
            super(v);

            txt_designation=v.findViewById(R.id.txt_designation);
            txt_Id=v.findViewById(R.id.txt_Id);
            txt_date=v.findViewById(R.id.txt_date);
            BtRecharge=v.findViewById(R.id.BtRecharge);

        }

        private WeakReference<ItemButtonListener> callbackWeakRef;

        public void updateWith(ItemButtonListener callback){

            this.BtRecharge.setOnClickListener(this);
            this.callbackWeakRef = new WeakReference<>(callback);
        }


        public void updateItemClick(ItemButtonListener callback){

            //3 - Implement Listener on ImageButton
            if(this.callbackWeakRef!=null)
            {
                itemView.setOnClickListener(this);
            }
            else
            {
                this.callbackWeakRef = new WeakReference<>(callback);
                itemView.setOnClickListener(this);
            }

        }


        @Override
        public void onClick(View v) {
            // 5 - When a click happens, we fire our listener.
            ItemButtonListener callback = callbackWeakRef.get();
            if (callback != null)
            {

                callback.onItemClickListener(getAdapterPosition());
              switch (v.getId())
                {
                    case R.id.BtRecharge:
                        callback.onUpdateClickListener(getAdapterPosition());
                        break;
                    default:
                        callback.onItemClickListener(getAdapterPosition());
                        break;
                }

            }
        }
    }

}
