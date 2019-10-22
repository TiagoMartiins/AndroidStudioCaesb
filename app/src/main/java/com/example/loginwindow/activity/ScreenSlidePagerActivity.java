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
import com.example.loginwindow.model.OrdemServico;
import com.example.loginwindow.fragmentPager.ScreenSlidePageFragment;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScreenSlidePagerActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;


    /**
     * Created OS list
     *
     */
    private List<OrdemServico> listaOs = new ArrayList<>();

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

        this.criarOs();

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
                Intent voltarTelaInicial = new Intent(ScreenSlidePagerActivity.this,MainActivityNova.class);
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
            Intent irParaTelaOs = new Intent(ScreenSlidePagerActivity.this,OsActivity.class);
            startActivity(irParaTelaOs);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btFrente:
                if(mPager.getCurrentItem() != listaOs.size()-1)
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
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return listaOs.size();
        }
    }

    public void criarOs() {

        OrdemServico os = new OrdemServico("Os 01930", "Os sendo executada", Calendar.getInstance().getTime());
        listaOs.add(os);

        OrdemServico os2 = new OrdemServico("Os 029183", "Os nova atualizada", Calendar.getInstance().getTime());
        listaOs.add(os2);

        OrdemServico os3 = new OrdemServico("Os 1239821", "teste 2", Calendar.getInstance().getTime());
        listaOs.add(os3);

        OrdemServico os4 = new OrdemServico("Os 19373", "teste 3", Calendar.getInstance().getTime());
        listaOs.add(os4);

        OrdemServico os5 = new OrdemServico("Os 192", "Rodando Os..", Calendar.getInstance().getTime());
        listaOs.add(os5);

        OrdemServico os6 = new OrdemServico("Os 12", "Atualizando Os 12", Calendar.getInstance().getTime());
        listaOs.add(os6);

        OrdemServico os7 = new OrdemServico("Os 13", "Aasodkaodasmda", Calendar.getInstance().getTime());
        listaOs.add(os7);

        OrdemServico os8 = new OrdemServico("Os 14", "asdkladasdasdamçdas", Calendar.getInstance().getTime());
        listaOs.add(os8);
    }



}

