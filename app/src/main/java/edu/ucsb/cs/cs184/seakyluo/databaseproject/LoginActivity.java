package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jude.swipbackhelper.SwipeBackHelper;

/**
 * A Login screen that offers Login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private EditText _idText, _pinText;
    private Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SwipeBackHelper.onCreate(this);
        _idText = findViewById(R.id.input_id);
        _pinText = findViewById(R.id.input_pin);
        _loginButton = findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _loginButton.setClickable(false);
                if (Customer.VerifyPin(Integer.parseInt(_idText.getText().toString()), _pinText.getText().toString())) {
                    onLoginSuccess();
                } else{
                    onLoginFailed();
                }
                _loginButton.setClickable(true);
            }
        });
    }

    public void onLoginSuccess() {
        DbHelper.user = Customer.findCustomer(Integer.parseInt(_idText.getText().toString()));
        setResult(RESULT_OK);
        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}

