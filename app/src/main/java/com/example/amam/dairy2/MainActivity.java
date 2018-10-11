package com.example.amam.dairy2;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;


    FirebaseRecyclerAdapter<Market, MarketViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference markets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        markets = database.getReference("markets");

        recyclerView = (RecyclerView)findViewById(R.id.marketrecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Market, MarketViewHolder>(
                Market.class,
                R.layout.card_markets,
                MarketViewHolder.class,
                markets
        ) {
            @Override
            protected void populateViewHolder(MarketViewHolder viewHolder, Market model, int position) {
                viewHolder.txtMarketId.setText("ID: "+adapter.getRef(position).getKey());
                viewHolder.txtMarketDate.setText(model.getDatePublish());
                viewHolder.txtFarmerName.setText("Farmer: "+model.getFarmerName());
                viewHolder.txtPhoneUpload.setText("Phone: "+model.getContactPerson());
                viewHolder.txtQuantity.setText("Quantity: "+model.getMilkQuantity()+" L");
                viewHolder.txtMarketPrice.setText("RM "+model.getPrice());
            }
        };

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        CustomDialog cdd=new CustomDialog(MainActivity.this, adapter.getRef(position).getKey().toString());
                        cdd.show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}
