package app.kojima.jiko.roomchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    ListView roomListView;
    RoomAdapter adapter;
    List<String> roomKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        adapter = new RoomAdapter(this);
        roomKey = new ArrayList<>();
        roomListView = (ListView)findViewById(R.id.roomListView);
        roomListView.setAdapter(adapter);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("ROOM_ID", roomKey.get(position));
                startActivity(intent);
            }
        });

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("rooms");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                roomKey.add(dataSnapshot.getKey());
                adapter.addRoom(dataSnapshot.getValue(Room.class));
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

    public void addRoom(View v) {
        final EditText editText = new EditText(this);
        editText.setHint("部屋の名前");
        new AlertDialog.Builder(this)
                .setTitle("追加した部屋の名前")
                .setMessage("部屋を追加します")
                .setView(editText)
                .setPositiveButton("追加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String roomName = editText.getEditableText().toString();
                        if(!roomName.isEmpty()) {
                            Room room = new Room(roomName);
                            ref.push().setValue(room);
                        }
                    }
                }).show();
    }
}
