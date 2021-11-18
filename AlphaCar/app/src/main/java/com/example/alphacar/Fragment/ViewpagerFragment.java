package com.example.alphacar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.alphacar.Adapter.ViewpagerAdapter;
import com.example.alphacar.DTOS.StoreDTO;
import com.example.alphacar.MainActivity;
import com.example.alphacar.R;

import java.util.ArrayList;

public class ViewpagerFragment extends Fragment {
    MainActivity mActivity;
    ImageView pagerimage;
    TextView pagerstoreName, pagerintroduce;
    StoreDTO dto ;

    /* 뷰페이저 프래그먼트 뷰페이저 화면 만드는 장소 */
    
    public ViewpagerFragment( StoreDTO dto) {
        this.dto = dto ;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.viewpager_fragment,
                container,false);
        mActivity = (MainActivity) getActivity();
        
        /* 필요한 아이디 찾기 */
        pagerimage =rootView.findViewById(R.id.imagePath);
        pagerstoreName = rootView.findViewById(R.id.storeName);
        pagerintroduce = rootView.findViewById(R.id.introduce);

//        Glide.with(MainActivity.this)
//                .load(loginDTO.getImgpath())

//                .circleCrop()
//                .into(imageView);

        
        // 이미지 파일은 글리드 및 리소스로 변경 리스트에서 받아오기
        Glide.with(ViewpagerFragment.this)
                .load(dto.getImgpath())
                .into(pagerimage);
        pagerstoreName.setText(dto.getStore_name());
        pagerstoreName.setBackgroundColor(999999);
        pagerintroduce.setText(dto.getIntroduce());
        return rootView;
    }


}