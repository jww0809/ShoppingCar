package jww.com.shoppingcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import adapter.ProductAdapter;
import bean.Product;
import thred.ProductHttpThread;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductHttpThread productHttpThread = new ProductHttpThread();
        productHttpThread.start();
        try {
            productHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Product> list = JSON.parseArray(productHttpThread.getResult(),Product.class);
        ProductAdapter productAdapter = new ProductAdapter(this,R.layout.product_layout,list);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(productAdapter);
    }
}
