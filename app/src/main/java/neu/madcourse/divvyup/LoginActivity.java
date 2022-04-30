package neu.madcourse.divvyup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import neu.madcourse.divvyup.groups.GroupListActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView userInput = findViewById(R.id.loginEditText);

        Button loginButton =findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GroupListActivity.class);
                intent.putExtra("userKey", userInput.getText().toString());
                startActivity(intent);
            }
        });
    }
}