package dimasapp.dimas.com.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import dimasapp.dimas.com.ui.auth.LoginFragment;
import dimasapp.dimas.com.ui.auth.RegistrationFragment;
import dimasapp.dimas.com.ui.database.DataBaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvReg;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tvReg = (TextView)findViewById(R.id.tvReg);
        tvReg.setOnClickListener(this);


    }

   /* @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvReg:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fragmentContainer, new RegistrationFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btnLogin:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fragmentContainer, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
