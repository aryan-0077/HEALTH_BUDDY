package com.example.healthcare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.viewmodels.DocInfo;
import com.example.healthcare.R;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.nameholder> {

    ArrayList<DocInfo> list;

    public DoctorAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public DoctorAdapter.nameholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.doc_card , parent , false);

        return new nameholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.nameholder holder, int position) {

        DocInfo di = list.get(position);

        holder.name.setText(di.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent i = new Intent(c , chat.class);
                i.putExtra("provider name" , a);
                i.putExtra("provider number" , phonenumber.get(position));
                c.startActivity(i);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class nameholder extends RecyclerView.ViewHolder{

        TextView name;

        public nameholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv);
        }
    }

    public void addDoc(DocInfo di){
       list.add(di);
       notifyDataSetChanged();
    }
}
