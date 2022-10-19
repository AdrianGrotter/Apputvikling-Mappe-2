package com.example.s354378_mappe2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    DBHandler dbHelper;
    SQLiteDatabase db;
    private final List<Contact> mContacts;

    public ContactsAdapter (List<Contact> contacts){
        mContacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        dbHelper = new DBHandler(context);
        db = dbHelper.getWritableDatabase();

        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {

        Contact contact = mContacts.get(position);

        TextView textView1 = holder.firstName;
        textView1.setText(contact.getFirst());
        TextView textView2 = holder.lastName;
        textView2.setText(contact.getLast());
        TextView textView3 = holder.phone;
        textView3.setText(contact.getPhone());
        Button button1 = holder.delete;
        button1.setTag(contact.get_ID());
        Button button2 = holder.edit;
        button2.setTag(contact.get_ID());


    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView firstName;
        public TextView lastName;
        public TextView phone;
        public Button delete;
        public Button edit;

        public ViewHolder(View itemView){
            super(itemView);

            firstName = (TextView) itemView.findViewById(R.id.firstName);
            lastName = (TextView) itemView.findViewById(R.id.lastName);
            phone = (TextView) itemView.findViewById(R.id.phone);
            delete = (Button) itemView.findViewById(R.id.delete);
            edit = (Button) itemView.findViewById(R.id.edit);
            delete.setOnClickListener(this);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Edit: " + edit.getTag());
                    Intent intent = new Intent(view.getContext(), EditContactActivity.class);
                    intent.putExtra("id", edit.getTag().toString());
                    view.getContext().startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View view) {
            System.out.println(delete.getTag());
            int pos = getAdapterPosition();
            dbHelper.deleteContact(db, delete.getTag().toString());
            mContacts.clear();
            mContacts.addAll(dbHelper.retrieveAllContacts(db));
            notifyItemRemoved(pos);
        }

    }
}
