package com.sax.inc.coetegaz.Adaptaters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RecylerAdapterAgence extends RecyclerView.Adapter<RecylerAdapterAgence.ViewHolder> {
    private List<EAgence> items;
    private AppCompatActivity activity;
    private Context cxt;
    
    public interface ItemButtonListener {

        void onUpdateClickListener(int position);
        void onItemClickListener(int position);
    }

    private  final ItemButtonListener callback_click;


    public RecylerAdapterAgence(AppCompatActivity appCompatActivity, List<EAgence> items, ItemButtonListener callback) {
        this.items = items;
        this.activity=appCompatActivity;
        callback_click=callback;
    }

    
    // Create new views (invoked by the layout manager)
    @Override
    public RecylerAdapterAgence.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agence, parent, false);


        return new ViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EAgence item =items !=null ? items.get(position):null;


        if(item!=null)
        {

            holder.txt_titre.setText("Agence de "+item.getName());
            holder.txt_date_create.setText(item.getName());
            holder.txt_phone.setText(item.getTelephone());
            holder.txt_ref.setText("Réf : "+item.getReflieu());

            String date= UtilTimeStampToDate.convert(item.getDatewrite());
            String [] tab=date.split(" ");
            holder.txt_date_create.setText(tab[0]);

        }
                holder.updateWith(this.callback_click);
                holder.updateItemClick(this.callback_click);
        }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(EAgence item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(EAgence item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void change(EAgence item) {
        int position = items.indexOf(item);
        notifyItemChanged(position);
    }



    public void notify(List<EAgence> list_items) {

        //items.clear();
//        items.addAll(list_items);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView txt_titre,txt_add_agent,txt_date_create,
                txt_phone,txt_ref;

        public ViewHolder(View v) {
            super(v);

            txt_titre=v.findViewById(R.id.text_titre);
            txt_ref=v.findViewById(R.id.txt_ref);
            txt_phone=v.findViewById(R.id.txt_phone);
            txt_date_create=v.findViewById(R.id.txt_date_create);
            txt_add_agent=v.findViewById(R.id.txt_add_agent);

        }

        private WeakReference<ItemButtonListener> callbackWeakRef;

        public void updateWith(ItemButtonListener callback){

            this.txt_add_agent.setOnClickListener(this);
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
                    case R.id.txt_add_agent:
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
