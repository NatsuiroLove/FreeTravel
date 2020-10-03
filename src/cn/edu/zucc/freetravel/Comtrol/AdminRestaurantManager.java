package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanAttractions;
import cn.edu.zucc.freetravel.model.BeanRestaurant;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRestaurantManager {
    public BeanRestaurant addRestaurant(String restaurant_id_s,String restaurant_name, String restaurant_area_name,  String restaurant_introduce
            , String restaurant_address , String special_recommand, String average_cost,String restaurant_route,String discount)throws BaseException {

        Connection conn=null;
        BeanRestaurant restaurant=new BeanRestaurant();
        int area_id=0;
        int restaurant_id=Integer.valueOf(restaurant_id_s).intValue();
        float average_cost_f=  Float.parseFloat(average_cost);
        float discount_f=Float.parseFloat(discount);
        int restaurant_route_i= Integer.valueOf(restaurant_route).intValue();

         /*名不可为空*/
        if(restaurant_name==null||restaurant_name.length()==0)throw new BaseException("餐厅名称不可为空");
        if(restaurant_area_name==null||restaurant_area_name.length()==0)throw new BaseException("餐厅归属名称不可为空");
        if(average_cost==null||average_cost.length()==0)throw new BaseException("餐厅平均消费不可为空");
        if(restaurant_address==null||restaurant_address.length()==0)throw new BaseException("餐厅详细地址不可为空");


        try{
            conn= DBUtil.getConnection();
            String sql="";
            java.sql.PreparedStatement pst;

            sql="select route_id from tbl_route where route_id = '"+restaurant_route_i+"'";
            pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            if(!rs.next())throw new BusinessException("线路不存在");

            sql="select area_id from tbl_area where area_name = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,restaurant_area_name);
            rs=pst.executeQuery();

            if(rs.next()) area_id=rs.getInt(1);
            else throw new BusinessException("区域不存在！");
            pst.close();
            rs.close();

            sql="select restaurant_name from tbl_restaurant where restaurant_id= ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,restaurant_id);
            rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("餐厅已存在！");

            sql="select restaurant_id from tbl_restaurant where restaurant_name= ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,restaurant_name);
            rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("餐厅已存在！");

            sql="insert into tbl_restaurant(restaurant_id,area_id,restaurant_name,restaurant_area_name," +
                    "restaurant_introduce,restaurant_address,special_recommand,average_cost,restaurant_route) value(?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,restaurant_id);
            pst.setInt(2,area_id);
            pst.setString(3,restaurant_name);
            pst.setString(4,restaurant_area_name);
            pst.setString(5,restaurant_introduce);
            pst.setString(6,restaurant_address);
            pst.setString(7,special_recommand);
            pst.setFloat(8,average_cost_f);
            pst.setInt(9,restaurant_route_i);
            pst.execute();

            sql="insert into route_restaurant(route_id,restaurant_id,dicount) value(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,restaurant_route_i);
            pst.setInt(2,restaurant_id);
            pst.setFloat(3,discount_f);
            pst.execute();

            /*数据封装*/
            restaurant.setArea_id(area_id);
            restaurant.setRestaurant_name(restaurant_name);
            restaurant.setRestaurant_introduce(restaurant_introduce);
            restaurant.setRestaurant_address(restaurant_address);
            restaurant.setSpecial_introduce(special_recommand);
            restaurant.setAverage_cost(average_cost_f);
            restaurant.setRestaurant_route(restaurant_route_i);

            return restaurant;
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new DbException(ex);
        }finally {
            if(conn!=null){
                try{
                    conn.close();;
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public List<BeanRestaurant> loadAll() throws BaseException{
        List<BeanRestaurant> result=new ArrayList<BeanRestaurant>();
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_restaurant order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            while(rs.next()){
                BeanRestaurant p=new BeanRestaurant();
                p.setRestaurant_id(rs.getInt(1));
                p.setArea_id(rs.getInt(2));
                p.setRestaurant_name(rs.getString(3));
                p.setRestaurant_area_name(rs.getString(4));
                p.setRestaurant_introduce(rs.getString(5));
                p.setRestaurant_address(rs.getString(6));
                p.setSpecial_introduce(rs.getString(7));
                p.setAverage_cost(rs.getFloat(8));
                p.setRestaurant_route(rs.getInt(9));
                result.add(p);
            }

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
        return result;
    }

    public void deleteRestaurant(int restaurant_id) throws BaseException {

//        int restaurant_id= restaurant.getRestaurant_id();
        Connection conn = null;

        try{
            /*建立数据库链接*/
            conn = DBUtil.getConnection();

            String sql="delete from route_restaurant where restaurant_id = "+restaurant_id;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

            sql="delete from tbl_restaurant where restaurant_id = "+restaurant_id;
            pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

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

    /*查询景区功能*/
    public List<BeanRestaurant> searchRestaurant(String restaurant_name)throws BaseException{
        List<BeanRestaurant> result=new ArrayList<BeanRestaurant>();

        if(restaurant_name.length()==0||restaurant_name==null)return result;//为空返回空结果，防止首次进入报错

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_restaurant where restaurant_name like '%"+restaurant_name+"%' order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            if(rs.next()) {
                do {
                    BeanRestaurant p = new BeanRestaurant();
                    p.setRestaurant_id(rs.getInt(1));
                    p.setArea_id(rs.getInt(2));
                    p.setRestaurant_name(rs.getString(3));
                    p.setRestaurant_area_name(rs.getString(4));
                    p.setRestaurant_introduce(rs.getString(5));
                    p.setRestaurant_address(rs.getString(6));
                    p.setSpecial_introduce((rs.getString(7)));
                    p.setAverage_cost(rs.getFloat(8));
                    p.setRestaurant_route(rs.getInt(9));
                    result.add(p);
                } while (rs.next());
            }

            pst.close();
            rs.close();

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
        return result;
    }
}
