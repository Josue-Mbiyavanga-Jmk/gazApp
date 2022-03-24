package com.sax.inc.coetegaz.Adaptaters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.sax.inc.coetegaz.Dao.CommuneDao;
import com.sax.inc.coetegaz.Dao.ProvinceDao;
import com.sax.inc.coetegaz.Dao.QuartierDao;
import com.sax.inc.coetegaz.Dao.VilleDao;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.ECommune;
import com.sax.inc.coetegaz.Entites.EProvince;
import com.sax.inc.coetegaz.Entites.EQuartier;
import com.sax.inc.coetegaz.Entites.EVille;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.UtilsMaps;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RecylerAdapterAgenceSearch extends RecyclerView.Adapter<RecylerAdapterAgenceSearch.ViewHolder> {
    private List<EAgence> items;
    private AppCompatActivity activity;
    private Context cxt;
    LatLng myPosition;

    public interface ItemButtonListener {

        void onCallClickListener(int position);
        void onSharLocaliseClickListener(int position);
        void onSharLocaliseGPSClickListener(int position);
        void onItemClickListener(int position);
    }

    private  final ItemButtonListener callback_click;


    public RecylerAdapterAgenceSearch(AppCompatActivity appCompatActivity, List<EAgence> items, ItemButtonListener callback) {
        this.items = items;
        this.activity=appCompatActivity;
        callback_click=callback;
    }

    public RecylerAdapterAgenceSearch(AppCompatActivity appCompatActivity, List<EAgence> items, LatLng myposition, ItemButtonListener callback) {
        this.items = items;
        this.activity=appCompatActivity;
        callback_click=callback;
        this.myPosition=myposition;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public RecylerAdapterAgenceSearch.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agence_liste_search, parent, false);


        return new ViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EAgence item =items !=null ? items.get(position):null;


        if(item!=null)
        {
            String coordonnee = "";
            coordonnee = item.getGps();

            String[] tab=coordonnee.split("#");

            LatLng marker=new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]));

            holder.txt_titre.setText(item.getName());
            holder.txt_qt.setText(item.getQuantite_produit()+" bouteille(s) disponible(s)");
            float distance= UtilsMaps.getDiffDistance(this.myPosition,marker);
            holder.txt_cart_value_metrage.setText( UtilsMaps.getTime(distance,1));
            holder.txt_footer_value_metrage.setText( UtilsMaps.getTime(distance,2));
            holder.txt_moto_bicycle.setText(UtilsMaps.getTime(distance,3));
            holder.txt_distance.setText(UtilsMaps.getDistance(distance));
            holder.txt_ref_lieu.setText("Réf : "+item.getReflieu());
            holder.txt_phone.setText(item.getTelephone());


            EQuartier eQuartier= QuartierDao.get(item.getQuartierref());
            ECommune eCommune= CommuneDao.get(item.getCommuneref());
            EVille eVille= VilleDao.get(item.getVilleref());
            EProvince eProvince= ProvinceDao.get(item.getProvinceref());

            holder.txt_adresse.setText("N° " +item.getNumero()+" Avenue : "+
                    item.getAvenue()+"Q/ "+eQuartier.getName()+
                    " C/ "+eCommune.getName());

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
        public TextView txt_titre,txt_qt,txt_cart_value_metrage,
                txt_footer_value_metrage,txt_moto_bicycle
                ,txt_distance,txt_ref_lieu,txt_adresse,txt_phone;
        public ImageView BtCall,BtShareLocalisation,BtShareLocalisationGps;

        public ViewHolder(View v) {
            super(v);

            txt_titre=v.findViewById(R.id.text_titre);
             txt_qt=v.findViewById(R.id.txt_qt);
             txt_cart_value_metrage=v.findViewById(R.id.txt_cart_value_metrage);
             txt_footer_value_metrage=v.findViewById(R.id.txt_footer_value_metrage);
             txt_distance=v.findViewById(R.id.txt_distance);
            txt_ref_lieu=v.findViewById(R.id.txt_ref_lieu);
            txt_adresse=v.findViewById(R.id.txt_adresse);
            txt_phone=v.findViewById(R.id.txt_phone);
            BtCall=v.findViewById(R.id.BtCall);
             txt_moto_bicycle=v.findViewById(R.id.txt_moto_bicycle);
            BtShareLocalisation=v.findViewById(R.id.BtShareLocalisation);
            BtShareLocalisationGps=v.findViewById(R.id.BtShareLocalisationGps);

        }

        private WeakReference<ItemButtonListener> callbackWeakRef;

        public void updateWith(ItemButtonListener callback){

            this.BtCall.setOnClickListener(this);
            this.BtShareLocalisation.setOnClickListener(this);
            this.BtShareLocalisationGps.setOnClickListener(this);
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
                    case R.id.BtCall:
                        callback.onCallClickListener(getAdapterPosition());
                        break;
                    case R.id.BtShareLocalisation:
                        callback.onSharLocaliseClickListener(getAdapterPosition());
                        break;
                    case R.id.BtShareLocalisationGps:
                        callback.onSharLocaliseGPSClickListener(getAdapterPosition());
                        break;
                    default:
                        callback.onItemClickListener(getAdapterPosition());
                        break;
                }

            }
        }
    }

}
