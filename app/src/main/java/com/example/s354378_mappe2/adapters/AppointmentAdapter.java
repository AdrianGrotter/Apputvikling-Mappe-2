package com.example.s354378_mappe2.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s354378_mappe2.DBHandler;
import com.example.s354378_mappe2.R;
import com.example.s354378_mappe2.Utilities;
import com.example.s354378_mappe2.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    DBHandler dbHelper;
    SQLiteDatabase db;
    private final List<Appointment> mAppointments;
    Context savedContext;

    public AppointmentAdapter (List<Appointment> appointments){
        mAppointments = appointments;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        savedContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(savedContext);

        dbHelper = new DBHandler(savedContext);
        db = dbHelper.getWritableDatabase();

        View AppointmentView = inflater.inflate(R.layout.item_appointment, parent, false);

        return new AppointmentAdapter.ViewHolder(AppointmentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Appointment appointment = mAppointments.get(position);

        TextView textView1 = holder.name;
        textView1.setText(appointment.getName());
        TextView textView2 = holder.date;
        textView2.setText(appointment.getDate());
        TextView textView3 = holder.time;
        textView3.setText(appointment.getTime());
        TextView textView4 = holder.location;
        textView4.setText(appointment.getLocation());
        TextView textView5 = holder.participant;
        textView5.setText(appointment.getParticipants());
        TextView textView6 = holder.message;
        textView6.setText(appointment.getMessage());

        Button button1 = holder.delete;
        button1.setTag(appointment.get_ID());


    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView date;
        public TextView time;
        public TextView location;
        public TextView participant;
        public TextView message;
        public Button delete;

        public ViewHolder(View itemView){
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            location = (TextView) itemView.findViewById(R.id.location);
            participant = (TextView) itemView.findViewById(R.id.participant);
            message = (TextView) itemView.findViewById(R.id.message);
            delete = (Button) itemView.findViewById(R.id.delete);

            delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            System.out.println(delete.getTag());
            int pos = getAdapterPosition();
            dbHelper.deleteAppointment(db, delete.getTag().toString());
            mAppointments.clear();
            mAppointments.addAll(Utilities.buildAppointmentList(savedContext));
            notifyItemRemoved(pos);
        }

    }
}
