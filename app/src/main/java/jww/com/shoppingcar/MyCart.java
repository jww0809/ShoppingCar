package jww.com.shoppingcar;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import adapter.CartAdapter;
import adapter.ProductAdapter;
import bean.Product;
import thred.ProductHttpThread;
import util.DBUtils;

public class MyCart extends AppCompatActivity {
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        Context context = MyCart.this;
        DBUtils dbUtils = new DBUtils(context);
        List<Product> list = dbUtils.readProduct();
        cartAdapter = new CartAdapter(context, R.layout.cart_layout, list);
        ListView listView = findViewById(R.id.list_view2);
        listView.setAdapter(cartAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                builder = new AlertDialog.Builder(MyCart.this);
                alertDialog = builder.setTitle("提示信息").setMessage("确定要删除吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /*list.remove(position);
                       listView.setAdapter(cartAdapter);*/
                        cartAdapter.deleteItem(position);
                    }
                }).setNegativeButton("还是留着吧", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                alertDialog.show();
            }
        });


        ImageButton imgBtnBack = findViewById(R.id.btn_back);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(MyCart.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
