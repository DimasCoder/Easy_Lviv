package dimasapp.dimas.com.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import dimasapp.dimas.com.ui.R;

public class HomeFragment extends Fragment {

    TextView tvLogin;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String user = getArguments().getString("user");

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppThemeFragment);

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View v = localInflater.inflate(R.layout.fragment_home, container, false);
       // tvLogin = (TextView)v.findViewById(R.id.tvLogin);
       // tvLogin.setText(user);

        return v;
    }



}
