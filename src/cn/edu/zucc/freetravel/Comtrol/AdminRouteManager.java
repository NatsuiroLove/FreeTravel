package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanAttractions;
import cn.edu.zucc.freetravel.model.BeanRoute;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRouteManager {
    /*新增景区*/
    public BeanRoute addRoute(String route_name, String route_introduce, String route_cost
            , String destination, String dayspend ,String special_recommand,String traffic_tips)throws BaseException {

        Connection conn=null;
        BeanRoute route=new BeanRoute();
        int area_id=0;
        float route_cost_f=  Float.parseFloat(route_cost);
//        int product_id_i=Integer.valueOf(product_id).intValue();
        int dayspend_i=Integer.valueOf(dayspend).intValue();
//        int recommandplay_id_i=Integer.valueOf(recommandplay_id).intValue();

        /*名不可为空*/
        if(route_name==null||route_name.length()==0)throw new BaseException("线路名称不可为空");
        if(route_cost==null||route_cost.length()==0)throw new BaseException("线路票价不可为空");
        if(destination==null||destination.length()==0)throw new BaseException("线路目的地址不可为空");
//        if(product_id==null||product_id.length()==0)throw new BaseException("产品id不可为空");
        if(dayspend==null||dayspend.length()==0)throw new BaseException("线路天数不可为空");
//        if(recommandplay_id==null||recommandplay_id.length()==0)throw new BaseException("推荐id不可为空");

        try{
            conn= DBUtil.getConnection();
            String sql="";
            java.sql.PreparedStatement pst;

//            sql="select area_id from tbl_area where area_name = ?";
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,destination);
//            java.sql.ResultSet rs=pst.executeQuery();
//
//            if(rs.next()) area_id=rs.getInt(1);
//            else throw new BusinessException("目的地不存在！");
//            pst.close();
//            rs.close();

            sql="select route_id from tbl_route where route_name= ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,route_name);
            java.sql.ResultSet rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("线路已存在！");

            sql="insert into tbl_route(area_id,route_name,route_introduce," +
                    "route_cost,destination ,dayspend,special_recommand,traffic_tips) value(?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,area_id);
            pst.setString(2,route_name);
            pst.setString(3,route_introduce);
            pst.setFloat(4,route_cost_f);
            pst.setString(5,destination);
            pst.setInt(6,dayspend_i);
            pst.setString(7,special_recommand);
            pst.setString(8,traffic_tips);
//            pst.setInt(9,recommandplay_id_i);
            pst.execute();

            /*数据封装*/
//            route.setProduct_id(product_id_i);
            route.setArea_id(area_id);
            route.setRoute_name(route_name);
            route.setRoute_introduce(route_introduce);
            route.setDestination(destination);
            route.setDayspend(dayspend_i);
            route.setSpecial_recommand(special_recommand);
            route.setTraffic_tips(traffic_tips);
//            route.setRecommandplay_id(recommandplay_id_i);

            return route;
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

    public List<BeanRoute> loadAll() throws BaseException{
        List<BeanRoute> result=new ArrayList<BeanRoute>();
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_route order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            while(rs.next()){
                BeanRoute p=new BeanRoute();
                p.setRoute_id(rs.getInt(1));
                p.setArea_id(rs.getInt(2));
                p.setRoute_name(rs.getString(3));
                p.setRoute_introduce(rs.getString(4));
                p.setRoute_cost(rs.getFloat(5));
                p.setDestination(rs.getString(6));
                p.setDayspend(rs.getInt(7));
                p.setSpecial_recommand(rs.getString(8));
                p.setTraffic_tips(rs.getString(9));
//                p.setRecommandplay_id(rs.getInt(10));
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

    public void deleteRoute(int route_id) throws BaseException {

//        int route_id= route.getRoute_id();
        Connection conn = null;

        try{
            /*建立数据库链接*/
            conn = DBUtil.getConnection();

            String sql="delete from route_attractions where route_id = "+route_id;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

            sql="delete from route_hotel where route_id = "+route_id;
            pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

            sql="delete from route_restaurant where route_id = "+route_id;
            pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

            sql="delete from tbl_route where route_id = "+route_id;
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
    public List<BeanRoute> searchRoute(String route_name)throws BaseException{
        List<BeanRoute> result=new ArrayList<BeanRoute>();

        if(route_name.length()==0||route_name==null)return result;//为空返回空结果，防止首次进入报错

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_route where route_name like '%"+route_name+"%' order by route_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            if(rs.next()) {
                do {
                    BeanRoute p = new BeanRoute();
                    p.setRoute_id(rs.getInt(1));
                    p.setArea_id(rs.getInt(2));
                    p.setRoute_name(rs.getString(3));
                    p.setRoute_introduce(rs.getString(4));
                    p.setRoute_cost(rs.getFloat(5));
                    p.setDestination(rs.getString(6));
                    p.setDayspend((rs.getInt(7)));
                    p.setSpecial_recommand(rs.getString(8));
                    p.setTraffic_tips(rs.getString(9));
//                    p.setRecommandplay_id(rs.getInt(10));
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
