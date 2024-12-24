package com.android.csebrur.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.user=user;
        holder.fullName.setText(user.Name);
        holder.address.setText("Address: "+user.Address);
        holder.blood.setText("Blood Group: "+user.BloodGroup);
        if(user.LastDonationDate.isEmpty()){
            holder.lastDate.setText("Last Donation Date: Not Yet Donated");
        }else
            holder.lastDate.setText("Last Donation Date: "+user.LastDonationDate);


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        User user;
        TextView fullName,address,blood,lastDate;
        MaterialButton forCall,forEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName= itemView.findViewById(R.id.lName);
            address= itemView.findViewById(R.id.lAddress);
            blood = itemView.findViewById(R.id.lBlood);
            lastDate = itemView.findViewById(R.id.lLastDate);
            forCall= itemView.findViewById(R.id.lCall);
            forEmail= itemView.findViewById(R.id.lEmail);

            forCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent dialer = new Intent(Intent.ACTION_DIAL);
                    dialer.setData(Uri.parse("tel:+88"+user.Phone));
                    context.startActivity(dialer);
                }

            });
            forEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                    sendIntent.setData(Uri.parse("mailto:"+user.Email));
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT,"BloodDonationApp: Need "+user.BloodGroup+" Blood Urgently");
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Hi "+user.Name+",\nI need "+user.BloodGroup+" Blood Urgently.\n" +
                            "If you are able to donate.Please reply this Email.\nThank You.\n");
                    if(sendIntent.resolveActivity(context.getPackageManager())!=null) {
                        context.startActivity(sendIntent);
                    }else
                        Toast.makeText(view.getContext(), "Email Client Not Found! ", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

}
