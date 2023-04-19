//package com.example.prjfarmfreshv1;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Menu;
//import android.widget.AdapterView;
//import android.widget.Toast;
//
//import com.google.android.material.snackbar.Snackbar;
//import com.google.android.material.navigation.NavigationView;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.prjfarmfreshv1.databinding.ActivityProfileBinding;
//
//public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//
//    private AppBarConfiguration mAppBarConfiguration;
//private ActivityProfileBinding binding;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//     binding = ActivityProfileBinding.inflate(getLayoutInflater());
//     setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.appBarProfile.toolbarProfiler);
//
//        binding.appBarProfile.fabProfiler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//
//        DrawerLayout drawer = binding.drawerLayout;
//
//        NavigationView navigationView = binding.navViewProfiler;
//        navigationView.setNavigationItemSelectedListener(this);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_profile, R.id.nav_order_history, R.id.nav_logout)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_profile);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.profile, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_profile);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        Fragment myFragemnt;
//        if (id == R.id.nav_logout) {
//            Toast.makeText(this, "test", Toast.LENGTH_LONG);
//        } else if (id == R.id.btnBackToHome){
//            if (id == R.id.btnBackToHome) {
//                Toast.makeText(this, "test", Toast.LENGTH_LONG);
//            }
//        }
//
//        return true;
//    }
//}