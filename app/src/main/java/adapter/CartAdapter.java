package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bean.Product;
import jww.com.shoppingcar.R;
import thred.ImageHttpThred;
import util.DBUtils;

public class CartAdapter extends ArrayAdapter {
    private int resourceId;
    private List<Product> list;

    public CartAdapter(Context context, int resource, List<Product> list) {
        super(context, resource, list);
        resourceId = resource;
        this.list = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //获取对应位置的数据
        final Product product  = (Product)getItem(position);
        CartAdapter.CartLayout cartLayout = new CartAdapter.CartLayout();
        View view;

        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            cartLayout.titleView = view.findViewById(R.id.cart_tv_title);
            cartLayout.priceView = view.findViewById(R.id.cart_tv_price);
            cartLayout.btnAddView = view.findViewById(R.id.cart_btn_addnum);
            cartLayout.btnRedView = view.findViewById(R.id.cart_btn_reducenum);
            cartLayout.imageView = view.findViewById(R.id.cart_img);
            cartLayout.numView = view.findViewById(R.id.cart_tv_num);
            //你因为写成product后面报转换类型错误
            view.setTag(cartLayout);
        }else{
            view = convertView;
            cartLayout = (CartAdapter.CartLayout) view.getTag();
        }
        cartLayout.titleView.setText(product.getTitle());
        cartLayout.priceView.setText(product.getPrice());
        cartLayout.numView.setText(product.getNum()+"");
        //加
        cartLayout.btnAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils dbUtils = new DBUtils(getContext());
                int count = dbUtils.addCount(product);
                product.setNum(count);
                //刷新内容
                notifyDataSetChanged();

            }
        });

        //减
        cartLayout.btnRedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils dbUtils = new DBUtils(getContext());
                int count = dbUtils.redCount(product);
                if(count>0){
                    product.setNum(count);
                }else
                    Toast.makeText(getContext(),"你可以单机该商品将其删除",Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();
            }
        });


        ImageHttpThred imageHttpThred = new ImageHttpThred(product.getImage());
        imageHttpThred.start();
        try {
            imageHttpThred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cartLayout.imageView.setImageBitmap(imageHttpThred.getResultBitmap());
        return  view;

    }

    //
    class CartLayout{
        TextView titleView;
        TextView priceView;
        TextView numView;
        Button btnAddView;
        Button btnRedView;
        ImageView imageView;
    }
    public void deleteItem(int position){
        DBUtils dbUtils = new DBUtils(getContext());
        Product product =list.get(position);
        dbUtils.delete(product);
        list.remove(position);
        notifyDataSetChanged();

    }
}
