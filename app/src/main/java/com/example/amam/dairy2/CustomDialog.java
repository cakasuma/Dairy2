package com.example.amam.dairy2;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener{
    FirebaseDatabase database;
    DatabaseReference tables;

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private String pos;


    public CustomDialog(Activity a, String position) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.pos = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        database = FirebaseDatabase.getInstance();
        tables = database.getReference("markets");
        switch (v.getId()) {
            case R.id.btn_yes:
                tables.child(pos).removeValue();
                Toast.makeText(c, "Purchase Successful, the item will be given to you in the coming hours... ", Toast.LENGTH_SHORT).show();
                Log.d("here", pos);
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
