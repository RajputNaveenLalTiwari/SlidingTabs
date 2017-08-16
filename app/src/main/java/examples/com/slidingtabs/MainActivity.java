package examples.com.slidingtabs;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private SlidingTabLayout tabsLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.appBarID);
        tabsLayout = (SlidingTabLayout) findViewById(R.id.tabsID);
        viewPager = (ViewPager) findViewById(R.id.viewPagerID);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        tabsLayout.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);

        tabsLayout.setDistributeEvenly(true);

       /* tabsLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position)
            {
                return getResources().getColor(R.color.colorAccent);
            }
        });*/

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            tabsLayout.setBackgroundColor(this.getColor(R.color.colorPrimary));
            tabsLayout.setSelectedIndicatorColors(this.getColor(R.color.colorPrimary));

            //tabsLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
            //tabsLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent);
        }
        else
        {
            tabsLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tabsLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        }

        tabsLayout.setViewPager(viewPager);


    }

    class MyViewPagerAdapter extends FragmentPagerAdapter
    {
        String[] tabsTitle;
        int[] icons = {R.drawable.ic_wifi_0,R.drawable.ic_wifi_1,R.drawable.ic_wifi_2};
        public MyViewPagerAdapter(FragmentManager fm)
        {
            super(fm);

            tabsTitle = getResources().getStringArray(R.array.tabsTitle);
        }

        @Override
        public Fragment getItem(int position)
        {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Drawable drawable = getResources().getDrawable(icons[position]);

            drawable.setBounds(0,0,36,36);

            ImageSpan imageSpan = new ImageSpan(drawable);

            SpannableString spannableString = new SpannableString(" ");

            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }
    }

    public static class MyFragment extends Fragment
    {
        TextView fragmentTextView;

        public static MyFragment getInstance(int position)
        {
            MyFragment myFragment = new MyFragment();

            Bundle bundle = new Bundle();

            bundle.putInt("Position",position);

            myFragment.setArguments(bundle);

            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View view = inflater.inflate(R.layout.fragment_one, container, false);

            fragmentTextView = (TextView) view.findViewById(R.id.fragmentTextViewID);

            Bundle bundle = getArguments();

            if( bundle != null )
            {
                fragmentTextView.setText("Page Selected "+bundle.getInt("Position"));
            }

            return view;
        }
    }
}
