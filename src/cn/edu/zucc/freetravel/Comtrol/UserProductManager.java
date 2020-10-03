package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanArea;
import cn.edu.zucc.freetravel.model.BeanProduct;
import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProductManager {
    public BeanProduct addProduct(int route_id,String start_time ) throws BaseException {
        if(start_time.length()==0||start_time==null)throw new BaseException("起始时间不可为空");

        Connection conn=null;
        int user_id = BeanUser.currentLoginUser.getUser_id();
        int order_id=0;
        float order_cost=0;
        float real_pay=0;
        float discount_f= (float) 1;
        if(BeanUser.currentLoginUser.getIsvip()==true||BeanUser.currentLoginUser.getVip_end_time().length()!=0)discount_f=(float) 0.8;

        try{
            /*建立数据库链接*/
            conn = DBUtil.getConnection();
            /*寻找到该账号此次登录使用的订单*/
            String sql = "select max(order_id) from tbl_productorder ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())order_id = rs.getInt(1);


            /*新添加此订单下产品并返回此订单*/
            sql="insert into tbl_product(order_id,route_id,start_time,discount) value(?,?,?,?)";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            pst.setInt(2,route_id);
            pst.setString(3,start_time);
            pst.setFloat(4,discount_f);
            pst.execute();

            /*更新订单消费*/
            sql="select route_cost from tbl_route where route_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,route_id);
            rs = pst.executeQuery();
            if(rs.next())order_cost= rs.getFloat(1);

            sql="update tbl_productorder set real_pay = real_pay + ? where order_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setFloat(1,order_cost*discount_f);
            pst.setInt(2,order_id);
            pst.execute();

            sql = "select real_pay from tbl_productorder where order_id= ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            rs = pst.executeQuery();
            if(rs.next())real_pay = rs.getInt(1);

            BeanProductOrder.currentUserOrder.setReal_pay(real_pay);

            BeanProduct product= new BeanProduct();
            product.setOrder_id(order_id);
            product.setRoute_id(route_id);
            product.setStart_time(start_time);
            product.setDiscount(discount_f);

            return product;

        }catch (SQLException ex){
            ex.printStackTrace();
            throw new DbException(ex);
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public List<BeanProduct> searchProduct( )throws BaseException{
        List<BeanProduct> result=new ArrayList<BeanProduct>();

        int order_id=0;

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();

            /*寻找到该账号此次登录使用的订单*/
            String sql = "select max(order_id) from tbl_productorder ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())order_id = rs.getInt(1);

            sql="select * from tbl_product where order_id= ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            rs=pst.executeQuery();

            /*将所有行列结果封装中*/
            if(rs.next()) {
                do {
                    BeanProduct p = new BeanProduct();
                    p.setProduct_id(rs.getInt(1));
                    p.setOrder_id(rs.getInt(2));
                    p.setRoute_id(rs.getInt(3));
                    p.setStart_time(rs.getString(4));
                    p.setDiscount(rs.getFloat(5));
                    result.add(p);
                } while (rs.next());
            }

            pst.close();
            rs.close();

            return result;

        }catch(SQLException ex){
            throw new DbException(ex);
        }finally{
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
