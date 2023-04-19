package com.example.prjfarmfreshv1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;

import com.example.prjfarmfreshv1.models.User;
import com.example.prjfarmfreshv1.ui.home.Login;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prjfarmfreshv1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ImageView icon_account;
    Button btnShopHome;

    User user=null;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent.hasExtra("user")){
            user = (User)getIntent().getExtras().getSerializable("user");
        }
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SendUsMessageActivity.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about_us, R.id.nav_contact_us)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        icon_account = findViewById(R.id.icon_account);


        icon_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user==null){
                    Intent intent = new Intent(MainActivity.this , Login.class);
                    startActivity(intent);
                }else{
                    Intent i = new Intent(MainActivity.this, ClientActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }

            }
        });

        btnShopHome = findViewById(R.id.btnShopHome);

        btnShopHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ProductActivity.class);
                intent.putExtra("user", (java.io.Serializable) null);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }


}