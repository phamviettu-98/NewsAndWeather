package viettu.pvt.newsandweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import viettu.pvt.newsandweather.FragmentAdapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    weatherFragment weatherFragment;
    NewsFragment newsFragment;
    MenuItem prevMenuItem;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;

                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    break;
            }
            return false;

        }
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPagerMain);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
      viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

          }

          @Override
          public void onPageSelected(int position) {
              if(prevMenuItem !=null){
                  prevMenuItem.setChecked(false);
              } else {
                  navView.getMenu().getItem(0).setChecked(false);

              }
              navView.getMenu().getItem(position).setChecked(true);
              prevMenuItem = navView.getMenu().getItem(position);


          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        newsFragment=new NewsFragment();
        weatherFragment=new weatherFragment();
        adapter.addFragment(newsFragment);
        adapter.addFragment(weatherFragment);
        viewPager.setAdapter(adapter);
    }
}