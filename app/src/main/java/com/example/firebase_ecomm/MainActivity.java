package com.example.firebase_ecomm;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.firebase_ecomm.databinding.ActivityMainBinding;
import com.example.firebase_ecomm.ui.Addproduct.AddProductFragment;
import com.example.firebase_ecomm.ui.home.HomeFragment;
import com.example.firebase_ecomm.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home){
                    addfragment(new HomeFragment());
                    binding.drawerLayout.closeDrawers();
                }
                if(item.getItemId()==R.id.nav_addproduct){

                    addfragment(new AddProductFragment());
                    Log.d("TTT", "onNavigationItemSelected: ");
                   // Splash_Screen.editor.putString("from","add");
                    binding.drawerLayout.closeDrawers();
                }
                if(item.getItemId()==R.id.nav_slideshow){
                    addfragment(new SlideshowFragment());

                    binding.drawerLayout.closeDrawers();
                }
//                if(item.getItemId()==R.id.signout){
//                    editor.putInt("login",0);
//                    editor.commit();
//                    Intent intent = new Intent(Main_Page.this,Signup_Page.class);
//                    startActivity(intent);
//                }
                return true;
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_addproduct, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.framelayout);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
//        binding.appBarMain.menudot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(Main_Page.this,view);
//                popupMenu.getMenuInflater().inflate(R.menu.menudot,popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//
//                        if(menuItem.getItemId()==R.id.nav_setting)
//                        {
//
//                        }
//                        if(menuItem.getItemId()==R.id.nav_logout)
//                        {
//                            Splash_Screen.editor.putInt("login",0);
//                            Splash_Screen.editor.commit();
//                            Intent intent = new Intent(Main_Page.this, Login_Activity.class);
//                            startActivity(intent);
//                        }
//                        return true;
//                    }
//                });
//                popupMenu.show();
//            }
//        });
        return true;
    }
    private void addfragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.framelayout);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}