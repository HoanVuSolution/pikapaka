package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hoanvusolution.pikapaka.R;
import item.item_chat;

/**
 * Created by MrThanhPhong on 3/17/2016.
 */
public class adpater_chat1  extends RecyclerView.Adapter<adpater_chat1.ViewHolder>{

    private List<item_chat> mMessages;


    public adpater_chat1(Context context, List<item_chat> messages) {
        mMessages = messages;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
//        switch (viewType) {
//            case item_chat.TYPE_MESSAGE:
//                layout = R.layout.listview_widget_chat;
//                break;
//            case item_chat.TYPE_LOG:
//                layout = R.layout.item_log;
//                break;
//            case item_chat.TYPE_ACTION:
//                layout = R.layout.item_action;
//                break;
//        }
        layout = R.layout.listview_widget_chat;
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        item_chat message = mMessages.get(position);
        viewHolder.setMessage(message.content);
        viewHolder.setUsername(message.firstName);
       // viewHolder.setMessage(item_chat.class.);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return mMessages.get(position).getType();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mMessageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsernameView = (TextView) itemView.findViewById(R.id.tv_name_sender);
            mMessageView = (TextView) itemView.findViewById(R.id.tv_msg);
        }

        public void setUsername(String username) {
            if (null == mUsernameView) return;
            mUsernameView.setText(username);


        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }


    }

}
