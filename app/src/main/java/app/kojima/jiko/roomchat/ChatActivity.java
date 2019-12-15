package app.kojima.jiko.roomchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    SharedPreferences prefs;
    final String ROOM_CHAT = "ROOM_CHAT";
    final String USER_NAME = "USER_NAME";
    FirebaseDatabase database;
    DatabaseReference ref;
    String userName;
    EditText postText;
    ListView chatListView;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        prefs = getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
        postText = (EditText)findViewById(R.id.postText);
        userName = prefs.getString(USER_NAME, "");

        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String roomId = intent.getStringExtra("ROOM_ID");
        ref = database.getReference("messages/" + roomId);

        chatListView = (ListView)findViewById(R.id.chatListView);
        adapter = new ChatAdapter(this);
        chatListView.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.addMessage(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void submit(View v) {
        String comment = postText.getEditableText().toString();
        if(!comment.isEmpty()) {
            Message message = new Message(userName, comment);
            ref.push().setValue(message);
            postText.setText("");
        }
    }
}
