package app.kojima.jiko.roomchat;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends BaseAdapter {
    private List<Room> rooms;
    private Context context;
    public RoomAdapter(Context context) {
        this.context = context;
        rooms = new ArrayList();
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Room room = rooms.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.room_card, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.setRoom(room);
        return convertView;
    }

    public class ViewHolder {
        TextView roomNameTextView;
        Room room;
        public ViewHolder(View view) {
            roomNameTextView = view.findViewById(R.id.roomNameTextView);
        }
        public void setRoom(Room room) {
            this.room = room;
            roomNameTextView.setText(room.getName());
        }
    }
}
