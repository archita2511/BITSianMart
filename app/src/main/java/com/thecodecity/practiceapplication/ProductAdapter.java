package com.thecodecity.practiceapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.thecodecity.practiceapplication.Model.Products;

public class ProductAdapter extends FirebaseRecyclerAdapter<Products, ProductAdapter.productViewholder> {

    public ProductAdapter(
            @NonNull FirebaseRecyclerOptions<Products> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull productViewholder holder,
                     int position, @NonNull Products model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.name.setText(model.getPname());
        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.description.setText(model.getDescription());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.price.setText(model.getPrice());
       // Picasso.get().load(model.getImage()).into(holder.image);
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public productViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
        return new ProductAdapter.productViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class productViewholder extends RecyclerView.ViewHolder {
        TextView name, description, price;
    //  CircleImageView image;
        public productViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
           //  image =  itemView.findViewById(R.id.product_image);
        }
    }
}


