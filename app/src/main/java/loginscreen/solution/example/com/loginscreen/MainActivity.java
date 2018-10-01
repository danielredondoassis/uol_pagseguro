package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import loginscreen.solution.example.com.loginscreen.model.UserModel;
import loginscreen.solution.example.com.loginscreen.utils.DialogManager;
import loginscreen.solution.example.com.loginscreen.utils.Validator;

import static loginscreen.solution.example.com.loginscreen.MainActivity.LoginTabs.SIGN_IN;
import static loginscreen.solution.example.com.loginscreen.MainActivity.LoginTabs.SIGN_UP;

public class MainActivity extends AppCompatActivity {

    public static final String USER_KEY = "USER_KEY" ;

    private Button btLogin;
    private Button btSignup;
    private EditText etName;
    private LinearLayout ltName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btSignIn;
    private EditText etPhone;
    private Button btCreate;
    private ViewFlipper viewFlipper;
    private LoginTabs mCurrentTab = SIGN_IN;

    public enum LoginTabs {
        SIGN_IN,
        SIGN_UP;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDialogManager = null;
    }

    private DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        btLogin = (Button) findViewById(R.id.bt_login);
        btSignup =  (Button) findViewById(R.id.bt_signup);
        etName =  (EditText) findViewById(R.id.et_name);
        ltName =  (LinearLayout) findViewById(R.id.lt_name);
        etEmail =  (EditText) findViewById(R.id.et_email);
        etPassword =  (EditText) findViewById(R.id.et_password);
        btSignIn =  (Button) findViewById(R.id.bt_sign_in);
        etPhone =  (EditText) findViewById(R.id.et_phone);
        btCreate =  (Button) findViewById(R.id.bt_create);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

        setupHelpers();
        setupClickListeners();
    }

    private void setupClickListeners() {

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserInfo(false);
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentTab != SIGN_UP) {
                    viewFlipper.showNext();
                    ltName.setVisibility(View.VISIBLE);
                    mCurrentTab = SIGN_UP;
                }
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentTab != SIGN_IN) {
                    viewFlipper.showPrevious();
                    ltName.setVisibility(View.INVISIBLE);
                    etName.setText("");
                    etPhone.setText("");
                    mCurrentTab = SIGN_IN;
                }
            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserInfo(true);
            }
        });
    }

    private void setupHelpers() {
        mDialogManager = new DialogManager(this);
    }

    private void validateUserInfo(boolean creating) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());

        Pattern specialCharPattern = Pattern.compile("[^A-Za-z0-9]");
        Pattern digitPattern = Pattern.compile(".*[^0-9].*");

        Matcher matcherDigit = digitPattern.matcher(password);
        Matcher matcher = specialCharPattern.matcher(password);

        if(creating && name.length() == 0){
            showErrorDialog(getString(R.string.invalid_name));
        } else if (creating && !Validator.validatePhone(phone)) {
            showErrorDialog(getString(R.string.invalid_phone));
        } else if(!Validator.validateEmail(email)){
            showErrorDialog(getString(R.string.invalid_email));
        } else if(password.length() < 6){
            showErrorDialog(getString(R.string.invalid_password));
        } else if(!matcher.find()){
            showErrorDialog(getString(R.string.invalid_password_special_characters));
        } else if(!hasUppercase){
            showErrorDialog(getString(R.string.invalid_password_upper_case));
        } else if(!hasLowercase) {
            showErrorDialog(getString(R.string.invalid_password_lower_case));
        } else if (!matcherDigit.find()){
            showErrorDialog(getString(R.string.invalid_password_digits));
        } else {
            UserModel model = new UserModel();
            model.setEmail(email);
            model.setName(name);
            model.setPhone(phone);

            checkForUser(model);
        }
    }

    private void checkForUser(UserModel model) {
        boolean userExists = true;
        if(userExists){
            navigateToWelcomeActivity(model);
        } else {
            showErrorDialog(getString(R.string.invalid_credentials));
        }
    }

    private void navigateToWelcomeActivity(UserModel model) {
        Intent newIntent = new Intent(MainActivity.this, LoginWelcomeActivity.class);
        newIntent.putExtra(USER_KEY,model);
        startActivity(newIntent);
    }

    private void showErrorDialog(String reason) {
        mDialogManager.showInvalidLoginDialog(reason);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }
}
