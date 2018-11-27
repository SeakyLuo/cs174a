package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jude.swipbackhelper.SwipeBackHelper;

public class SignupActivity extends AppCompatActivity {
    private EditText _nameText;
    private EditText _addressText;
    private EditText _pinText;
    private EditText _cpinText;
    private Button _signupButton;
    private TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.activity_signup);

        _nameText = findViewById(R.id.input_name);
        _addressText = findViewById(R.id.input_address);
        _pinText = findViewById(R.id.input_pin);
        _cpinText = findViewById(R.id.input_confirm_pin);
        _signupButton = findViewById(R.id.btn_signup);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    onSignupSuccess();
                }else{
                    onSignupFailed();
                }
            }
        });
    }

    public void onSignupSuccess() {
        UseridDialog fragment = new UseridDialog();
        fragment.setCaller(SignupActivity.this);
        fragment.showNow(getSupportFragmentManager(), "UserID");
    }

    public void onSignupFailed() {
        Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String pin = _pinText.getText().toString();
        String cpin = _cpinText.getText().toString();

        if (name.trim().length() < 2) {
            _nameText.setError("Your name should have at least 2 characters");
            valid = false;
        }
        else if (name.trim().length() > 40) {
            _nameText.setError("Your name cannot have more than 40 characters");
            valid = false;
        }
        if (address.trim().length() > 40) {
            _nameText.setError("Your address cannot have more than 40 characters");
            valid = false;
        }
        if (pin.length() != 4) {
            _pinText.setError("You PIN should have exactly 4 digits.");
            valid = false;
        }
        // TODO: change to if used
        if (pin.length() > 30) {
            _pinText.setError("This PIN has been used.");
            valid = false;
        }

        if (cpin.isEmpty()) {
            _cpinText.setError("Empty!");
            valid = false;
        }
        if (!cpin.equals(pin)) {
            _cpinText.setError("Passwords NOT match!");
            valid = false;
        }

        return valid;
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