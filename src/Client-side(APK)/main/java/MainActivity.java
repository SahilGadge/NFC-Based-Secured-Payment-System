package com.the43appmart.aniruddha.nfcpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences savedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "NFC Pay-Home" );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        View v = navigationView.getHeaderView( 0 );
        TextView name = (TextView) v.findViewById( R.id.txtnavName );
        TextView email = (TextView) v.findViewById( R.id.txtnavEmail );
        TextView wholesalerid = (TextView) v.findViewById( R.id.txtUserId );

        savedUser = PreferenceManager.getDefaultSharedPreferences( MainActivity.this );
        String Name = savedUser.getString( "strUserName", "" );
        String Id = savedUser.getString( "strId", "" );
        String E_mail = savedUser.getString( "strEmail", "" );
        email.setText( E_mail );
        name.setText( Name );
        wholesalerid.setText( Id );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f = null;
        if (id == R.id.nav_add_card) {
            f = new AddCard();
        } else if (id == R.id.nav_logout) {
            LogOut();
        } else if (id == R.id.nav_saved_cards) {
            f = new MyCards();
        } else if (id == R.id.nav_add_money) {
            f = new AddMoney();
//            Intent intent =new Intent( this,AddMoney1.class );
//            startActivity( intent );
        }
// else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        if (f != null) {
            FragmentManager FM = getSupportFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out )
                    .replace( R.id.layout_MainActivity, f )
                    .addToBackStack( String.valueOf( FM ) )
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void LogOut() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                MainActivity.this );
        alertDialog.setTitle( "Logout application?" );
        // Setting Dialog Message
        alertDialog.setMessage( "Are you sure you want to Logout the application?" );
        // Setting Positive "Yes" Button
        final android.app.AlertDialog.Builder builder = alertDialog.setPositiveButton( "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent( MainActivity.this, Splash.class );
                        startActivity( i );
                        finish();

                        SharedPreferences savedUser = PreferenceManager
                                .getDefaultSharedPreferences( getApplicationContext() );
                        SharedPreferences.Editor editer = savedUser.edit();
                        editer.remove( "strUserName" );
                        editer.commit();
                    }
                } );
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton( "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                } );
        // Showing Alert Message
        alertDialog.show();
    }
}
