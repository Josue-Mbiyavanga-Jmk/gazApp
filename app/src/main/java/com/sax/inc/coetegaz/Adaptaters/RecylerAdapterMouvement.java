package com.sax.inc.coetegaz.Adaptaters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import customfonts.MyTextView_Roboto_Regular;

public class RecylerAdapterMouvement extends RecyclerView.Adapter<RecylerAdapterMouvement.ViewHolder> {
    private List<EMouvementStock> items;
    private AppCompatActivity activity;
    private Context cxt;

    public interface ItemButtonListener {

        void onUpdateClickListener(int position);
        void onItemClickListener(int position);
    }

    private  final ItemButtonListener callback_click;


    public RecylerAdapterMouvement(AppCompatActivity appCompatActivity, List<EMouvementStock> items, ItemButtonListener callback) {
        this.items = items;
        this.activity=appCompatActivity;
        callback_click=callback;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public RecylerAdapterMouvement.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_mouvement, parent, false);


        return new ViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EMouvementStock item =items !=null ? items.get(position):null;


        if(item!=null)
        {

            if(item.getType().equals(Constant.INPUT))
            {
                holder.txt_designe.setText("Mouvement "+"d'entrée avec une quantité de "+item.getQuantite()+"");

            }


            String date= UtilTimeStampToDate.convert(item.getDatewrite());
            String [] tab=date.split(" ");
            holder.txt_date.setText(tab[0]);

           /* if(item.getType().equals(Constant.INPUT)){
                holder.badge_groupe.setBackgroundColor(activity.getResources().getColor(R.color.snackBarColorNotificationSuccess));
            }*/




        }
                holder.updateWith(this.callback_click);
                holder.updateItemClick(this.callback_click);
        }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(EMouvementStock item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(EMouvementStock item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void change(EMouvementStock item) {
        int position = items.indexOf(item);
        notifyItemChanged(position);
    }



    public void notify(List<EMouvementStock> list_items) {

        //items.clear();
//        items.addAll(list_items);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public MyTextView_Roboto_Regular txt_date,txt_designe;



        public ViewHolder(View v) {
            super(v);

            txt_designe = v.findViewById(R.id.txt_designe);
            txt_date = v.findViewById(R.id.txt_date);

        }

        private WeakReference<ItemButtonListener> callbackWeakRef;

        public void updateWith(ItemButtonListener callback){

            //this.BtUpdate.setOnClickListener(this);
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
             /*  switch (v.getId())
                {
                    case R.id.BtUpdate:
                        callback.onUpdateClickListener(getAdapterPosition());
                        break;
                    default:
                        callback.onItemClickListener(getAdapterPosition());
                        break;
                }*/

            }
        }
    }

}
