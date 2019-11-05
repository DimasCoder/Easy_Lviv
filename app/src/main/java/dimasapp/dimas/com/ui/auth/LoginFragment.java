package dimasapp.dimas.com.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import dimasapp.dimas.com.ui.BottomActivity;
import dimasapp.dimas.com.ui.R;
import dimasapp.dimas.com.ui.database.DataBaseHelper;
import dimasapp.dimas.com.ui.fragments.HomeFragment;


public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText etLogin1;
    EditText etPassword1;
    Button btnLogin1;
    TextView tvReg1;
    TextView tvHelp;
    ImageView arrowBack;
    Button btnLogin;
    DataBaseHelper db;
    HomeFragment accFrag = new HomeFragment();


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        db = new DataBaseHelper(getActivity());

        etLogin1 = (EditText) v.findViewById(R.id.etLogin1);
        etPassword1 = (EditText) v.findViewById(R.id.etPassword1);

        btnLogin1 = (Button) v.findViewById(R.id.btnLogin1);
        btnLogin1.setOnClickListener(this);
        tvReg1 = (TextView) v.findViewById(R.id.tvReg1);
        tvReg1.setOnClickListener(this);
        tvHelp = (TextView)v.findViewById(R.id.tvHelp);
        tvHelp.setOnClickListener(this);
        arrowBack = (ImageView)v.findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(this);


        // Inflate the layout for this fragment

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin1:
                String email = etLogin1.getText().toString().trim();
                String password = etPassword1.getText().toString().trim();
                Boolean res = db.checkUser(email, password);

                Bundle bundle = new Bundle();
                bundle.putString("user", "admin");
               // accFrag.setArguments(bundle);
                if (res == true) {
                    Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), BottomActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                } else {
                    Toast.makeText(getActivity(), "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvReg1:
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fragmentContainer, new RegistrationFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.arrowBack:
                getActivity().onBackPressed();
                break;
        }
    }

  /*  @Override
    public boolean onBackPressed() {
                return false;
    }*/
}

