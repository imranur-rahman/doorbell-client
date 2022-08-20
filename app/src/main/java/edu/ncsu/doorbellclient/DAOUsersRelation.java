package edu.ncsu.doorbellclient;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DAOUsersRelation {
    private DatabaseReference databaseReference;
    private static DAOUsersRelation daoUsersRelation = null;

    public static DAOUsersRelation getDAOUsersRelation() {
        if (daoUsersRelation == null) {
            daoUsersRelation = new DAOUsersRelation();
        }
        return daoUsersRelation;
    }
    private DAOUsersRelation() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://doorbell-interface-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = db.getReference(UsersRelation.class.getSimpleName());
    }

    public Task<Void> add(String src, String dest) {
        StringEdge stringEdge = new StringEdge(src, dest);
        Gson gson = new Gson();
        return databaseReference.push().setValue(gson.toJson(stringEdge));
    }

    public void loadRecyclerViewData(SecondFragment secondFragment) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StringEdge> edges = new ArrayList<>();
                Log.d("shimul " + this.getClass().toString(), String.valueOf(snapshot.getChildrenCount()));
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String str = dataSnapshot.getValue(String.class);
                    Log.d("shimul " + this.getClass().toString(), str);
                    edges.add(new Gson().fromJson(str, StringEdge.class));
                }
                UsersRelation usersRelation = new UsersRelation(edges);
                secondFragment.setUsersRelation(usersRelation);
                Log.d("shimul " + this.getClass().getSimpleName().toString(),
                        "graph size: " + String.valueOf(usersRelation.getGraphSize()));
                Log.d("shimul " + this.getClass().getSimpleName().toString(),
                        "graph size: " + String.valueOf(secondFragment.getUsersRelation().getGraphSize()));
                secondFragment.setAdapterUsersRelation(new AdapterUsersRelation(usersRelation,
                        secondFragment.getContext()));
                secondFragment.getAdapterUsersRelation().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
