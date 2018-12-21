package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import bean.Product;

public class DBUtils {

    DataBaseHelper dataBaseHelper;

    public DBUtils(Context context){
        dataBaseHelper = new DataBaseHelper(context,"Shopping.db",null,1);
    }

    //加入购物车
    public void addCart(Product product){
        Cursor cursor = dataBaseHelper.getWritableDatabase().query("shopping",null,"product_id=?"
                ,new String[]{product.getId() + ""}, null, null, null);
        ContentValues contentValues = new ContentValues();
        //查询得到的cursor是指向第一条记录之前的，调用就会指向第一条记录,所以用来判断查询结果是否为空
        if(cursor.moveToFirst()){
            //存在相同商品，就在原先基础上加1
            contentValues.put("num", cursor.getInt(5) + 1);
            dataBaseHelper.getWritableDatabase().update("shopping", contentValues, "product_id = ? "
                    ,new String[]{product.getId()+ ""});
        }else{
            //不存在就加入购物车
            contentValues.put("product_id", product.getId());
            contentValues.put("title", product.getTitle());
            contentValues.put("price", product.getPrice());
            contentValues.put("image", product.getImage());
            contentValues.put("num", 1);
            dataBaseHelper.getWritableDatabase().insert("shopping", null , contentValues);

        }
    }

    //从数据库中读取数据
    public List<Product> readProduct(){
        List<Product> list = new ArrayList<Product>();
        Cursor cursor = dataBaseHelper.getWritableDatabase().query("shopping",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            //一定要循环读
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex("product_id")));
                product.setImage(cursor.getString(cursor.getColumnIndex("image")));
                product.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                product.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                product.setNum(cursor.getInt(cursor.getColumnIndex("num")));
                list.add(product);
            } while (cursor.moveToNext());

        }
        return list;

    }

    //单机增加按钮
    public int addCount(Product product){


        Cursor cursor = dataBaseHelper.getWritableDatabase().query("shopping",null,"product_id=?",new String[]{product.getId()+ ""}, null, null, null);
        ContentValues contentValues = new ContentValues();
        int count=0;

        if(cursor.moveToFirst()){
            contentValues.put("num",cursor.getInt(5)+1);
            count = cursor.getInt(5)+1;
            dataBaseHelper.getWritableDatabase().update("shopping",contentValues,"product_id = ? ",new String[]{product.getId()+""});
        }
        return count;

    }

    //单机减少按钮
    public int redCount(Product product){


        Cursor cursor = dataBaseHelper.getWritableDatabase().query("shopping",null,"product_id=?",new String[]{product.getId()+ ""}, null, null, null);
        ContentValues contentValues = new ContentValues();
        int count=0;

        if(cursor.moveToFirst()){
            contentValues.put("num",cursor.getInt(5)-1);
            count = cursor.getInt(5)-1;
            dataBaseHelper.getWritableDatabase().update("shopping",contentValues,"product_id = ? ",new String[]{product.getId()+""});
        }
        return count;


    }

    public void delete(Product product){
        dataBaseHelper.getWritableDatabase().delete("shopping","product_id = ?",new String[]{product.getId()+""});
    }


}
