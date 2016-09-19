package chitchat.com.chitchat;

        import android.content.DialogInterface;
        import android.media.MediaCodec;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final EditText email = (EditText) findViewById(R.id.email2);
        final EditText password = (EditText) findViewById(R.id.password2);
        final Button validate = (Button) findViewById(R.id.validate2);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail(email.getText(). toString())) {
                    email.setError("Invalid Email");
                    email.requestFocus();
                }else if (!validatePassword(password.getText(). toString())) {
                    password.setError("Invalid Password");
                    password.requestFocus();
                } else {
                    Toast.makeText(Main3Activity.this, "Input Validation Success", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //Return true if password is valid and false if invalid
    private boolean validatePassword(String password) {
        if (password!=null && password.length()>7) {
            return true;
        } else {
            return false;
        }
    }
    //Return true if email is valid and false if invalid
    private boolean validateEmail(String email) {
        String emailPattern = "\"[a-zA-Z0-9\\\\+\\\\.\\\\_\\\\%\\\\-\\\\+]{1,256}\" +\n" +
                "          \"\\\\@\" +\n" +
                "          \"[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,64}\" +\n" +
                "          \"(\" +\n" +
                "          \"\\\\.\" +\n" +
                "          \"[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,25}\" +\n" +
                "          \")+\"";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();


    }


}