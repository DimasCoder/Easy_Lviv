package dimasapp.dimas.com.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import dimasapp.dimas.com.ui.R;
import dimasapp.dimas.com.ui.cardfragments.BeerFragment;
import dimasapp.dimas.com.ui.cardfragments.MonumentsFragment;

public class HomeFragment extends Fragment {

    TextView tvLogin;
    CardView cardMonuments;
    CardView cardMustSee;
    CardView cardFood;
    CardView cardBeer;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String user = getArguments().getString("user");

        /*
        cardMustSee = (CardView) getView().findViewById(R.id.cardMustSee);
        cardFood = (CardView) getView().findViewById(R.id.cardFood);*/





        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppThemeFragment);

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View v = localInflater.inflate(R.layout.fragment_home, container, false);
        v.getContext().getTheme().applyStyle(R.style.AppThemeFragment, true);
       // tvLogin = (TextView)v.findViewById(R.id.tvLogin);
       // tvLogin.setText(user);
        cardMonuments = (CardView) v.findViewById(R.id.cardMonuments);
        cardMonuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    loadFragment(new MonumentsFragment());
                }catch (Exception e){}
            }
        });
        cardBeer = (CardView) v.findViewById(R.id.cardBeer);
        cardBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    loadFragment(new BeerFragment());
                }catch (Exception e){}
            }
        });

        return v;
    }

    private void loadFragment(Fragment fragment){
        if(fragment != null){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }



}
