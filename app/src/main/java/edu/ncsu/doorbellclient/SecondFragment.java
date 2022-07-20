package edu.ncsu.doorbellclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.bandb.graphview.AbstractGraphAdapter;
import dev.bandb.graphview.decoration.edge.ArrowEdgeDecoration;
import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.layouts.layered.SugiyamaConfiguration;
import dev.bandb.graphview.layouts.layered.SugiyamaLayoutManager;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager;
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration;
import edu.ncsu.doorbellclient.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private RecyclerView mRelationRecycler;
    //private RelationListAdapter mRelationAdapter;
    private AbstractGraphAdapter graphAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRelationRecycler = binding.recycler;
        mRelationRecycler.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        mRelationRecycler.setNestedScrollingEnabled(false);
        
        initGraph();
        
        return root;
    }

    private void initGraph() {
        SugiyamaConfiguration configuration = new SugiyamaConfiguration.Builder()
                .setNodeSeparation(100)
                .setLevelSeparation(100)
                .build();
        binding.recycler.setLayoutManager(new SugiyamaLayoutManager(binding.getRoot().getContext(), configuration));
        Paint edgeStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedValue typedValue = new TypedValue();
        binding.getRoot().getContext().getTheme().resolveAttribute(R.attr.colorSecondary, typedValue, true);
        int color = ContextCompat.getColor(binding.getRoot().getContext(), typedValue.resourceId);
        edgeStyle.setColor(color);
        edgeStyle.setStrokeWidth(5f);
        edgeStyle.setStyle(Paint.Style.STROKE);
        edgeStyle.setStrokeJoin(Paint.Join.ROUND);
        edgeStyle.setPathEffect(new CornerPathEffect(10f));
        binding.addEdge.addItemDecoration(new ArrowEdgeDecoration(edgeStyle));

        graphAdapter = new AbstractGraphAdapter<NodeHolder>() {
            @Override
            public NodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                NodeBinding binding = NodeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new NodeHolder(binding);
            }

            @Override
            public void onBindViewHolder(NodeHolder holder, int position) {
                holder.set((EntityInfo) getNodeData(position));
            }
        };
        binding.graphView.setAdapter(graphAdapter);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        // https://developer.android.com/topic/libraries/view-binding
        // https://mkyong.com/android/android-prompt-user-input-dialog-example/
        binding.addEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput1 = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput1);
                final EditText userInput2 = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput2);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        // result.setText(userInput.getText());
                                        Toast.makeText(context,
                                                userInput1.getText().toString() + userInput2.getText().toString(),
                                                Toast.LENGTH_LONG); // not working donno why
                                        Log.d(this.getClass().getSimpleName(),
                                                userInput1.getText().toString() + userInput2.getText().toString()); // working

                                        // Need to add logic here to update the firebase database
                                        DAOUsersRelation daoUsersRelation = DAOUsersRelation.getDAOUsersRelation();
                                        daoUsersRelation.add(userInput1.getText().toString(), userInput2.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });



        // setup the graphview


        // 1. Set a layout manager of the ones described above that the RecyclerView will use.
//        BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
//                                .setSiblingSeparation(100)
//                                .setLevelSeparation(100)
//                                .setSubtreeSeparation(100)
//                                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
//                                .build();
        SugiyamaConfiguration configuration = new SugiyamaConfiguration.Builder()
//                                .setSiblingSeparation(100)
                                .setLevelSeparation(100)
//                                .setSubtreeSeparation(100)
//                                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                                .build();

        BuchheimWalkerLayoutManager buchheimWalkerLayoutManager = new BuchheimWalkerLayoutManager(context, configuration);
        binding.recycler.setLayoutManager(new SugiyamaLayoutManager(context, configuration));
        //binding.recycler.setLayoutManager(new Layoutmanager);

        // 2. Attach item decorations to draw edges
        binding.recycler.addItemDecoration(new TreeEdgeDecoration());

        new Graph();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}