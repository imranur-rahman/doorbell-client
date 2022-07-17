package edu.ncsu.doorbellclient;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class DAOUsersRelation {
    private DatabaseReference databaseReference;
    public DAOUsersRelation() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://doorbell-interface-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = db.getReference(UsersRelation.class.getSimpleName());
    }

    public Task<Void> add(String src, String dest) {
        StringEdge stringEdge = new StringEdge(src, dest);
        Gson gson = new Gson();
        return databaseReference.push().setValue(gson.toJson(stringEdge));
    }
}
