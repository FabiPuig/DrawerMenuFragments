package com.example.fabian.drawermenufragments;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reemplazamos el ActionBar por la Toolbar
        toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );

        // Preparamos el drawer view
        navigationView = (NavigationView) findViewById( R.id.nvView );
        setupDrawerContent( navigationView );

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        // La acción home/up de la barra de acción debe abrir o cerrar el drawer
        switch ( item.getItemId() ) {
            case android.R.id.home:
                drawerLayout.openDrawer( GravityCompat.START );
                return true;
        }

        return super.onOptionsItemSelected( item );
    }

    private void setupDrawerContent( NavigationView navigationView ) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected( MenuItem menuItem ) {
                        selectDrawerItem( menuItem );
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Crea un nuevo fragment y especifica el fragment a mostrar basandose en el item
        // del nav seleccionado
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inserta el fragment reemplazando el existente
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Realza el item seleccionado en el NavigationView
        menuItem.setChecked(true);
        // Añadimos el titulo a la toolbar
        setTitle(menuItem.getTitle());
        // Cierra el navigation drawer
        drawerLayout.closeDrawers();
    }
}
