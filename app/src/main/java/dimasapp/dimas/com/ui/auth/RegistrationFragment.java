package dimasapp.dimas.com.ui.auth;


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
import dimasapp.dimas.com.ui.R;
import dimasapp.dimas.com.ui.database.DataBaseHelper;
import dimasapp.dimas.com.ui.fragments.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {

    EditText etUserName;
    EditText etUserSurname;
    EditText etEmail;
    EditText etPassword;
    EditText cnfPassword;
    ImageView arrowBackReg;
    TextView tvReg;
    Button btnLogin;
    Button btnReg;
    HomeFragment acFragment;

    DataBaseHelper db;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registration, container, false);

        db = new DataBaseHelper(getActivity());

        etUserName = (EditText)v.findViewById(R.id.etUserName);
        etUserSurname = (EditText)v.findViewById(R.id.etUserSurname);
        etEmail = (EditText)v.findViewById(R.id.etEmail);
        etPassword = (EditText)v.findViewById(R.id.etPassword);
        cnfPassword = (EditText)v.findViewById(R.id.cnfPassword);
        arrowBackReg = (ImageView)v.findViewById(R.id.arrowBackReg);
        arrowBackReg.setOnClickListener(this);
        btnReg  = (Button)v.findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReg:
                String name = etUserName.getText().toString().trim();
                String surName = etUserSurname.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String password2 = cnfPassword.getText().toString().trim();

                if (password.equals(password2) && db.checkEmail(email)) {
                    long val = db.addUser(name, surName, password, email);
                    if (val > 0) {
                        Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                        R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                .replace(R.id.fragmentContainer, new LoginFragment())
                                .commit();
                    }

                } else if(!password.equals(password2)){
                    Toast.makeText(getActivity(), "Passwods don't match!", Toast.LENGTH_SHORT).show();
                }
                else if(!db.checkEmail(email)){
                    Toast.makeText(getActivity(), "Email is exists!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.arrowBackReg:
                getActivity().onBackPressed();
                break;
        }
    }
}
