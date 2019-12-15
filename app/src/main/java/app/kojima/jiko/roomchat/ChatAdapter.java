package app.kojima.jiko.roomchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends BaseAdapter {
    List<Message> data;
    Context context;

    public ChatAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addMessage(Message message) {
        this.data.add(message);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = data.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.message_card, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.setMessage(message);
        return convertView;
    }

    class ViewHolder {
        TextView messageText;
        Message message;
        public ViewHolder(View view) {
            messageText = view.findViewById(R.id.messageText);
        }

        public void setMessage(Message message) {
            this.message = message;
            messageText.setText(message.getComment());
        }
    }
}
