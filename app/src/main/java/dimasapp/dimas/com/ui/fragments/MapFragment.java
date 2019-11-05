package dimasapp.dimas.com.ui.fragments;

import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dimasapp.dimas.com.ui.R;

import static android.content.ContentValues.TAG;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final LatLng OPERA = new LatLng(49.84371, 24.0264523);
    private static final LatLng HIGHCASTLE = new LatLng(49.8481849,24.0393758);
    private static final LatLng POTOCKIY = new LatLng(49.8379368,24.0267808);
    private static final LatLng YURAS = new LatLng(49.8386906,24.0130218);
    private static final LatLng RATUSHA = new LatLng(49.8417178, 24.0316819);

    private Marker ot;
    private Marker hc;
    private Marker pp;
    private Marker ys;
    private Marker rt;
    float curLat = (float) 49.8373601;
    float curLng = (float) 24.0335928;

    GoogleMap map;
    private EditText mSearchText;
    private ImageView btnSearch;
    ImageView dialogImage;
    TextView dialogTitle, dialogText;

    int otTitle = R.string.otTitle;
    int hcTitle = R.string.hcTitle;
    int ysTitle = R.string.ysTitle;
    int ppTitle = R.string.ppTitle;
    int rtTitle = R.string.rtTitle;


    int otText = R.string.otText;
    int hcText = R.string.hcText;
    int ysText = R.string.ysText;
    int ppText = R.string.ppText;
    int rtText = R.string.rtText;


    int otImage = R.drawable.ot_image;
    int hcImage = R.drawable.hc_image;
    int ysImage = R.drawable.ys_image;
    int ppImage = R.drawable.pp_image;
    int rtImage = R.drawable.rt_image;

    int otAddress = R.string.otAddress;
    int hcAddress = R.string.hcAddress;
    int ysAddress = R.string.ysAddress;
    int ppAddress = R.string.ppAddress;
    int rtAddress = R.string.rtAddress;

    int otAudio = R.raw.opera;
    int hcAudio = R.raw.castle;
    int ppAudio = R.raw.palace;

    MediaPlayer mediaPlayer;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mSearchText = (EditText) v.findViewById(R.id.input_search);
        btnSearch = (ImageView) v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    geoLocate();
                }catch (Exception e){}
            }
        });

        return v;
    }

    public void MyCustomDialog(int title, int text, int image, int address, final int audio){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(dialogView);

        Button btnDialogExit = (Button)dialogView.findViewById(R.id.btnDialogExit);
        Button btnPlay = (Button)dialogView.findViewById(R.id.btnPlay);


        TextView dialogTitle = (TextView)dialogView.findViewById(R.id.dialogTitle);
        TextView dialogText = (TextView)dialogView.findViewById(R.id.dialogText);
        ImageView dialogImage = (ImageView)dialogView.findViewById(R.id.dialogImage);
        TextView dialogAddress = (TextView)dialogView.findViewById(R.id.dialogAddress);

        dialogTitle.setText(title);
        dialogText.setText(text);
        dialogAddress.setText(address);
        dialogImage.setImageResource(image);
        final AlertDialog dialog = builder.create();
        btnDialogExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                    }
                    dialog.cancel();
                }catch (Exception e){}
            }
        });

        if(audio == 1){
            btnPlay.setVisibility(View.INVISIBLE);
        }
        else {
            mediaPlayer = MediaPlayer.create(getActivity(), audio);
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                    else if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            });
        }
        dialog.show();

    }

    private void init() {
        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate() {

        Log.d(TAG, "geoLocate: geolocating");
        String searchString = mSearchText.getText().toString();


        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 15, address.getLocality());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if(map != null){
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView tvLocality = v.findViewById(R.id.tv_locality);
                    TextView tvSnippet = v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvSnippet.setText(marker.getSnippet());
                    init();
                    return v;
                }
            });
        }


        final String opT = "asdЛьвівський національний академічний театр опери та балету імені Соломії Крушельницької — театр опери і балетуЛьвівський національний академічний театр опери та балету імені Соломії Крушельницької — театр опери і балету  у Львові, розташований в історично";



        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
               if(marker.equals(ot)){
                    MyCustomDialog(otTitle, otText, otImage, otAddress, otAudio);
                }
                if(marker.equals(hc)){
                   MyCustomDialog(hcTitle, hcText, hcImage, hcAddress, hcAudio);
                }
                if(marker.equals(ys)){
                    MyCustomDialog(ysTitle, ysText, ysImage, ysAddress, 1);
                }
                if(marker.equals(pp)){
                    MyCustomDialog(ppTitle, ppText, ppImage, ppAddress, ppAudio);
                }
                if(marker.equals(rt)){
                    MyCustomDialog(rtTitle, rtText, rtImage, rtAddress, 1);
                }
                return true;
            }
        });

        goToLocationZoom(curLat, curLng, 19);

        ot = map.addMarker(new MarkerOptions()
                .position(OPERA)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ellogo))
        );
        ot.setTag(0);

        hc = map.addMarker(new MarkerOptions()
                .position(HIGHCASTLE)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ellogo))
        );
        hc.setTag(0);

        ys = map.addMarker(new MarkerOptions()
                .position(YURAS)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ellogo))
        );
        ys.setTag(0);

        pp = map.addMarker(new MarkerOptions()
                .position(POTOCKIY)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ellogo))
        );
        pp.setTag(0);

        rt = map.addMarker(new MarkerOptions()
                .position(RATUSHA)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ellogo))
        );
        rt.setTag(0);
    }
    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        map.addMarker(options);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        map.moveCamera(update);
    }
}
