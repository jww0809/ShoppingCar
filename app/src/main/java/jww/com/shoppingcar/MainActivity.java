package jww.com.shoppingcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import adapter.ProductAdapter;
import bean.Product;
import thred.ProductHttpThread;
import util.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button btnAddcar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //从本地数据库中取出数据
        //并在适配器上加载
        //展示出来

        ProductHttpThread productHttpThread = new ProductHttpThread();
        productHttpThread.start();
        try {
            productHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Product> list = JSON.parseArray(productHttpThread.getResult(),Product.class);
        ProductAdapter productAdapter = new ProductAdapter(this,R.layout.product_layout,list);
        ListView listView = findViewById(R.id.list_view1);
        listView.setAdapter(productAdapter);

        ImageButton imgBtnCar = findViewById(R.id.imgBtn_car);
        imgBtnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(MainActivity.this,MyCart.class);
                startActivity(intent);
           }
        });


    }
}
