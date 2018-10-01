package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import loginscreen.solution.example.com.loginscreen.model.UserModel;

public class LoginWelcomeActivity extends AppCompatActivity {

    TextView toolbarTitle;
    Toolbar toolbar;
    TextView tvName;
    TextView tvEmail;
    TextView tvPhone;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(LoginWelcomeActivity.this, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvPhone = (TextView) findViewById(R.id.tv_phone);


        if(getIntent() != null && getIntent().getSerializableExtra(MainActivity.USER_KEY) != null){

            UserModel userModel = (UserModel) getIntent().getSerializableExtra(MainActivity.USER_KEY);

            tvName.setText(userModel.getName());
            tvEmail.setText(userModel.getEmail());
            tvPhone.setText(userModel.getPhone());
        }
    }
}
