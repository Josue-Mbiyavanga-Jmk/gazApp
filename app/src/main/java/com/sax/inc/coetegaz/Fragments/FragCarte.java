package com.sax.inc.coetegaz.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sax.inc.coetegaz.Dao.AgenceDao;
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
import com.sax.inc.coetegaz.Utils.UtilGPS;
import com.sax.inc.coetegaz.Utils.UtilsMaps;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FragCarte extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    View root;
    private GoogleMap mMap;
    List<EAgence> agenceList;
    public  static LatLng myPosition;
    KProgressHUD progress;
    int count =0;

    public FragCarte() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static FragCarte newInstance() {
        FragCarte fragment = new FragCarte();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.activity_maps, container, false);
        initWidget();
        return root;
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        agenceList =new ArrayList<>();
        agenceList= AgenceDao.getAllSearch();
    }

    void initWidget()
    {
        //
       // initialise widget sheet

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        if(agenceList.size()==0)
        {
            // Add a marker in, kinshasa, and move the camera.
            LatLng lg = new LatLng(-4.304367042553763, 15.31219482421875);
            mMap.addMarker(new MarkerOptions().position(lg).title("République démocratique du congo, Kinshasa ville"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lg));
        }
        else
        {


            String value="",lg="";
            for (EAgence  o: agenceList) {

                try {

                   // LatLng lg = new LatLng(Double.valueOf(o.getLatitude()),Double.valueOf(o.getLongitude()));
                    boolean b= UtilGPS.verifyLtLg(o.getGps());

                    if(b)
                    {
                        String[] tab=o.getGps().split("#");
                        int height = 110;
                        int width = 110;
                        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.marker);
                        Bitmap bm = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(bm, width, height, false);

                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]))).title(o.getName()+"\nAvenue "+o.getAvenue()+ " N° "+o.getNumero()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                    }


                }
                catch (Exception e)
                {

                }
            }


            String coordonnee = "";
            coordonnee = UtilGPS.getGpsData((AppCompatActivity) getActivity());

            String[] tab=coordonnee.split("#");

            myPosition=new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]));

            mMap.addMarker(new MarkerOptions().position(myPosition));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]))));
            mMap.setOnMarkerClickListener(this);

        }


    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        count =0;

        if(marker.getPosition().latitude==myPosition.latitude && marker.getPosition().longitude==myPosition.longitude)
        {
            return false ;
        }

       final EAgence eAgence=getAgence(marker.getPosition().latitude+
            "#"+marker.getPosition().longitude);

        View dialogView = getLayoutInflater().inflate(R.layout.view_detail_entite_map_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(dialogView);

        TextView  txt_titre=dialogView.findViewById(R.id.text_titre);
        TextView txt_qt=dialogView.findViewById(R.id.txt_qt);
        TextView txt_cart_value_metrage=dialogView.findViewById(R.id.txt_cart_value_metrage);
        TextView txt_footer_value_metrage=dialogView.findViewById(R.id.txt_footer_value_metrage);
        TextView txt_moto_bicycle=dialogView.findViewById(R.id.txt_moto_bicycle);
        TextView txt_distance=dialogView.findViewById(R.id.txt_distance);
        TextView txt_phone=dialogView.findViewById(R.id.txt_phone);
        TextView txt_adresse=dialogView.findViewById(R.id.txt_adresse);
        TextView txt_ref_lieu=dialogView.findViewById(R.id.txt_ref_lieu);
        ImageView BtCall=dialogView.findViewById(R.id.BtCall);
        ImageButton BtShareLocalisation=dialogView.findViewById(R.id.BtShareLocalisation);
        ImageButton BtShareLocalisationGps=dialogView.findViewById(R.id.BtShareLocalisationGps);


        txt_titre.setText(eAgence.getName());
        txt_qt.setText(eAgence.getQuantite_produit()+" bouteille(s) disponible(s)");
        float distance= UtilsMaps.getDiffDistance(myPosition,marker.getPosition());
        txt_cart_value_metrage.setText(UtilsMaps.getTime(distance,1));
        txt_footer_value_metrage.setText(UtilsMaps.getTime(distance,2));
        txt_moto_bicycle.setText(UtilsMaps.getTime(distance,3));
        txt_distance.setText(UtilsMaps.getDistance(distance));
        txt_ref_lieu.setText("Réf : "+eAgence.getReflieu());

        txt_phone.setText(eAgence.getTelephone());

        txt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+eAgence.getTelephone()));
                startActivity(intent);
            }
        });

        BtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+eAgence.getTelephone()));
                startActivity(intent);
            }
        });

        EQuartier eQuartier= QuartierDao.get(eAgence.getQuartierref());
        ECommune eCommune= CommuneDao.get(eAgence.getCommuneref());
        EVille eVille= VilleDao.get(eAgence.getVilleref());
        EProvince eProvince= ProvinceDao.get(eAgence.getProvinceref());

        txt_adresse.setText("N° " +eAgence.getNumero()+" Avenue : "+
                eAgence.getAvenue()+" Q/ "+eQuartier.getName()+
                " C/ "+eCommune.getName());

        BtShareLocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder uri=new StringBuilder();

                EQuartier eQuartier= QuartierDao.get(eAgence.getQuartierref());
                ECommune eCommune= CommuneDao.get(eAgence.getCommuneref());

                String adress="N° " +eAgence.getNumero()+" Avenue : "+
                        eAgence.getAvenue()+" Q/ "+eQuartier.getName()+
                        " C/ "+eCommune.getName();

                uri.append("Agence de "+ eAgence.getName());
                uri.append("\nTélephone : "+ eAgence.getTelephone());
                uri.append("\nRéférence : "+ eAgence.getReflieu());
                uri.append("\nAdresse : "+ adress+"\n\n");

                String coordonnee = "";
                coordonnee = eAgence.getGps();

                String[] tab=coordonnee.split("#");

                LatLng marker=new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]));


                Double latitude = marker.latitude;
                Double longitude = marker.longitude;

                String gps_coordonnees = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;

                uri.append(gps_coordonnees);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Selectionnez une application";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, uri.toString());
                startActivity(Intent.createChooser(sharingIntent, "Partage via"));
            }
        });

        BtShareLocalisationGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double latitude = marker.getPosition().latitude;
                Double longitude = marker.getPosition().longitude;

                String uri = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;
                //Url https://maps.googleapis.com/maps/api/staticmap?center=Australia&size=640x400&style=element:labels|visibility:off&style=element:geometry.stroke|visibility:off&style=feature:landscape|element:geometry|saturation:-100&style=feature:water|saturation:-100|invert_lightness:true&key=YOUR_API_KEY
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Selectionnez une application";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(sharingIntent, "Partage via"));
            }
        });


        dialog.show();

        return false;
    }

    private EAgence getAgence(String lglt)
    {
        for (EAgence o: agenceList) {
         if(o.getGps().equals(lglt))
         {
             return o;
         }
        }
        return null;
    }
}