package edu.ncsu.doorbellclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterUsersRelation extends RecyclerView.Adapter<AdapterUsersRelation.ViewHolder> {
    private UsersRelation usersRelation;
    private Context context;

    // constructor class for our Adapter
    public AdapterUsersRelation(UsersRelation usersRelation, Context context) {
        this.usersRelation = usersRelation;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterUsersRelation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("shimul " + this.getClass().toString(), "constructor");
        // passing our layout file for displaying our users item
        return new AdapterUsersRelation.ViewHolder(LayoutInflater.from(context).inflate(R.layout.connecting_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsersRelation.ViewHolder holder, int position) {
        // setting data to our views in Recycler view items.
        String user = usersRelation.reverseUserMapping(position);
        holder.textView1.setText(user);
        holder.textView2.setText(usersRelation.getUserList(user).toString());
        Log.d("shimul " + this.getClass().toString(), "onBindViewHolder: position: " + String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        Log.d("shimul " + this.getClass().toString(), "itemCount: " + usersRelation.getGraphSize());
        return usersRelation.getGraphSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our
        // views of recycler items.
        private TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing the views of recycler views.
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
