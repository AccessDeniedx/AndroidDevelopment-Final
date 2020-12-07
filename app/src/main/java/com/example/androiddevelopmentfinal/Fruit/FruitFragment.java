package com.example.androiddevelopmentfinal.Fruit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddevelopment.R;

import java.util.ArrayList;
import java.util.List;

public class FruitFragment extends Fragment {
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fruit_fragment, container, false);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        /*//RecyclerView横向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //瀑布流
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);*/

        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


        /*// 获取Bundle,该对象是Activity创建Fragment时，传入的
        Bundle bundle = getArguments();
        if (bundle != null) {
            String textValue = bundle.getString("textValue");// 将文本框的值赋值为传入的textValue
            textView.setText(textValue);
        }*/

        return view;
    }

    private void initFruits(){
        for(int i = 0;i < 2 ; i++){
            Fruit apple = new Fruit("Apple",R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit("Banana",R.drawable.apple_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit("Orange",R.drawable.apple_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("Watermelon",R.drawable.apple_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("Pear",R.drawable.apple_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit("Grape",R.drawable.apple_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("pineapple",R.drawable.apple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry",R.drawable.apple_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("Cherry",R.drawable.apple_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit("Mango",R.drawable.apple_pic);
            fruitList.add(mango);
        }
    }

}
