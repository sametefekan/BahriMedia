package com.bahricorp.bahrimedia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter
{
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] imageSource;

    public ViewPagerAdapter(Context context, String[] dataArgs)
    {
        this.context = context;
        imageSource = dataArgs;
    }

    @Override
    public int getCount()
    {
        return imageSource.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_slider_layout_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        switch(position)
        {
            case 0:
                Picasso.get().load(imageSource[position]).into(imageView);
                break;
            case 1:
                Picasso.get().load(imageSource[position]).into(imageView);
                break;
            case 2:
                Picasso.get().load(imageSource[position]).into(imageView);
                break;
            default:
                Picasso.get().load(imageSource[position]).into(imageView);
                break;

        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
