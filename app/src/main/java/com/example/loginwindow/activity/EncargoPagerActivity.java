package com.example.loginwindow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.loginwindow.R;
import com.example.loginwindow.ZoomOutPageTransformer;
import com.example.loginwindow.fragmentPager.EncargoPageFragment;
import com.example.loginwindow.model.Encargo;
import com.example.loginwindow.fragmentPager.ScreenSlidePageFragment;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EncargoPagerActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * Created OS list
     *
     */
    private List<Encargo> listaEncargo = new ArrayList<>();

    private FloatingActionButton fab;

    private Animation animation;

    ImageButton btVoltar,btFrente;

    private int position;


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(1000); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo

        this.criarEncargo();

        btFrente = (ImageButton)findViewById(R.id.btFrente);
        btVoltar = (ImageButton)findViewById(R.id.btTras);

        btFrente.setOnClickListener(this);
        btVoltar.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.position = (int) extras.get("position");
        }


        fab = findViewById(R.id.fabItem);
        fab.startAnimation(animation);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaInicial = new Intent(EncargoPagerActivity.this,MainActivityNova.class);
                startActivity(voltarTelaInicial);
            }
        });

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mPager.setCurrentItem(position);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            Intent irParaTelaEncargo = new Intent(EncargoPagerActivity.this,EncargoActivity.class);
            startActivity(irParaTelaEncargo);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btFrente:
                if(mPager.getCurrentItem() != listaEncargo.size()-1)
                mPager.setCurrentItem(mPager.getCurrentItem()+1);
                break;
            case R.id.btTras:
                if(mPager.getCurrentItem() != 0)
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                break;
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Create fragment object
            System.out.println("-------------------- POSITION -----------------");
            System.out.println(position);
            EncargoPageFragment fragment = new EncargoPageFragment(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return listaEncargo.size();
        }
    }

    public void criarEncargo(){

        Encargo os =  new Encargo("Encargo 1","ENCARGO 1 SENDO EXECUTADO", Calendar.getInstance().getTime());
        listaEncargo.add(os);

        Encargo os2 =  new Encargo("Encargo 2","ENCARGO 2 SENDO EXECUTADO",Calendar.getInstance().getTime());
        listaEncargo.add(os2);

        Encargo os3 =  new Encargo("Encargo 3","ENCARGO 3 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os3);

        Encargo os4 =  new Encargo("Encargo 4","ENCARGO 4 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os4);

        Encargo os5 =  new Encargo("Encargo 5","ENCARGO 5 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os5);

        Encargo os6 =  new Encargo("Encargo 6","ENCARGO 6 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os6);

        Encargo os7 =  new Encargo("Encargo 7","ENCARGO 7 SENDO EXECUTADO",Calendar.getInstance().getTime());
        listaEncargo.add(os7);

        Encargo os8 =  new Encargo("Encargo 8","ENCARGO 8 SENDO EXECUTADO",Calendar.getInstance().getTime());
        listaEncargo.add(os8);
    }



}

