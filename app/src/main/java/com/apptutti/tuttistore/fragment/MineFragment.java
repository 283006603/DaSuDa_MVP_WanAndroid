package com.apptutti.tuttistore.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.base.BaseFragment;
import com.apptutti.tuttistore.mactivity.ImageMeiziActivity;
import com.apptutti.tuttistore.utils.BlurUtil;
import com.apptutti.tuttistore.widge.ItemView;
import com.apptutti.tuttistore.widge.ZoomScrollView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView backImgView;
    private ZoomScrollView scrollView;
    private View avatarLayout;
    private TextView meiziView;
    private ItemView favoriteItemView;
    private ItemView meiziItemView;
    private ItemView aboutItemView;

    public MineFragment() {
    }

    public static BaseFragment newInstance(/*String uid*/){
        MineFragment fragment = new MineFragment();
      /* Bundle args = new Bundle();
                args.putString("uid", uid);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }



    @Override
    protected void initView(View rootView) {
        backImgView = rootView.findViewById(R.id.iv_avatar_background);
        scrollView = rootView.findViewById(R.id.sv_scroll);
        //        avatarLayout = rootView.findViewById(R.id.rl_layout);
        favoriteItemView = rootView.findViewById(R.id.iv_mine_favorite);
        meiziItemView = rootView.findViewById(R.id.iv_mine_meizi);
        aboutItemView = rootView.findViewById(R.id.iv_mine_about);
    }

    @Override
    protected void initData() {
        scrollView.setZoomView(backImgView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        backImgView.setImageBitmap(BlurUtil.blur(mContext, bitmap, 18));


        favoriteItemView.setOnClickListener(this);
        meiziItemView.setOnClickListener(this);
        aboutItemView.setOnClickListener(this);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_mine_about) {
//            gotoAboutActivity();
        } else if (v.getId() == R.id.iv_mine_favorite) {
//            gotoFavoriteActivity();
        } else if (v.getId() == R.id.iv_mine_meizi) {
            gotoMeiziActivity();
        }
    }

    private void gotoMeiziActivity() {
        Intent intent=new Intent(getContext(), ImageMeiziActivity.class);
        getContext().startActivity(intent);
    }

    /*@UserLoginTrace(value = 0)
    private void gotoFavoriteActivity() {
        ARouter.getInstance()
                .build("/main/FavoriteActivity")
                .navigation();
    }*/

    /*private void gotoAboutActivity() {
        ARouter.getInstance()
                .build("/main/AboutActivity")
                .navigation();
    }*/
}

