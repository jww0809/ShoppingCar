package adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bean.Product;
import jww.com.shoppingcar.R;
import thred.ImageHttpThred;

public class ProductAdapter extends ArrayAdapter {

    private int resourceId;
    public ProductAdapter( Context context, int resource,List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Product product  = (Product)getItem(position);
       ProductLayout productLayout = new ProductLayout();
       View view;

       if(convertView==null){
           view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

           productLayout.titleView = view.findViewById(R.id.tv_title);
           productLayout.priceView = view.findViewById(R.id.tv_price);
           productLayout.btnView = view.findViewById(R.id.btn_addCart);
           productLayout.imageView = view.findViewById(R.id.img);
           //你因为写成product后面报转换类型错误
           view.setTag(productLayout);
       }else{
           //找缓存数据
           view = convertView;
           //获得一个对象，里面装满了各种属性
           productLayout = (ProductLayout) view.getTag();
       }
       productLayout.titleView.setText(product.getTitle());
       productLayout.priceView.setText(product.getPrice());
       ImageHttpThred imageHttpThred = new ImageHttpThred(product.getImage());
       imageHttpThred.start();
        try {
            imageHttpThred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productLayout.imageView.setImageBitmap(imageHttpThred.getResultBitmap());
        return  view;

    }

    //
    class ProductLayout{
        TextView titleView;
        TextView priceView;
        Button btnView;
        ImageView imageView;
    }
}
