package app.kojima.jiko.roomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    final String USER_NAME = "USER_NAME";
    EditText userEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
        userEditText = (EditText)findViewById(R.id.userEditText);
        userEditText.setText(prefs.getString(USER_NAME, ""));
    }

    public void goToRoom(View v) {
        String userName = userEditText.getEditableText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("userName", userName);
        editor.putString(USER_NAME, userName);
        editor.commit();
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
}
