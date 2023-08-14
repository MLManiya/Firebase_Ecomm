package com.example.firebase_ecomm.ui.slideshow;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.firebase_ecomm.R;
import com.example.firebase_ecomm.ui.Addproduct.AddProductFragment;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.adapter> {

    SlideshowFragment slideshowFragment;
   // List<Productdatum> productdata;
   // TransferDataFragment transferDataFragment;


    @NonNull
    @Override
    public adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_slideshow_itemfile,parent,false);
        adapter holder = new adapter(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull adapter holder, int position) {

//        holder.item.setText(""+productdata.get(position).getProName());
//        holder.price.setText(""+productdata.get(position).getProPrice());
//        holder.category.setText(""+productdata.get(position).getProDes());
//        Glide.with(slideshowFragment).load("https://hardyecommerce.000webhostapp.com/Register_API/"+productdata.get(position).getProImage()).into(holder.imageView);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(slideshowFragment.getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menumore, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.nav_update)
                        {
                            addfragment(new AddProductFragment());
                            Toast.makeText(slideshowFragment.getContext(),"Update successfully",Toast.LENGTH_LONG);
                        }
                        if(menuItem.getItemId()==R.id.nav_delete)
                        {

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void addfragment(Fragment fragment) {
        FragmentManager fm = slideshowFragment.getParentFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class adapter extends ViewHolder {
        ImageView imageView,more;
        TextView item,price,category;

        public adapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rec_img);
            item = itemView.findViewById(R.id.rec_itemname);
            price = itemView.findViewById(R.id.rec_price);
            category = itemView.findViewById(R.id.rec_desc);
            more = itemView.findViewById(R.id.rec_more);
        }
    }
    }


