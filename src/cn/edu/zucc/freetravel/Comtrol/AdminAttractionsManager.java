package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanArea;
import cn.edu.zucc.freetravel.model.BeanAttractions;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminAttractionsManager {
    /*新增景区*/
    public BeanAttractions addAttractions(String attractions_id_s ,String attractions_name, String attractions_area_name, String attractions_cost, String attractions_introduce
            , String attractions_address , String tour_time, String tour_route,String recommand_time)throws BaseException{

        Connection conn=null;
        BeanAttractions attractions=new BeanAttractions();
        int area_id=0;
        int attractions_id=Integer.valueOf(attractions_id_s).intValue();
        float attractions_cost_f=  Float.parseFloat(attractions_cost);
        int tour_route_i = Integer.valueOf(tour_route).intValue();
//        int recommand_time_i=Integer.valueOf(recommand_time).intValue();

        /*名不可为空*/
        if(attractions_name==null||attractions_name.length()==0)throw new BaseException("景区名称不可为空");
        if(attractions_area_name==null||attractions_area_name.length()==0)throw new BaseException("景区归属名称不可为空");
        if(attractions_cost==null||attractions_cost.length()==0)throw new BaseException("景区票价不可为空");
        if(attractions_address==null||attractions_address.length()==0)throw new BaseException("景区详细地址不可为空");


        try{
            conn= DBUtil.getConnection();
            String sql="";
            java.sql.PreparedStatement pst;

            sql="select route_id from tbl_route where route_id = '"+tour_route_i+"'";
            pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            if(!rs.next())throw new BusinessException("线路不存在");

            sql="select area_id from tbl_area where area_name = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,attractions_area_name);
            rs=pst.executeQuery();

            if(rs.next()) area_id=rs.getInt(1);
            else throw new BusinessException("区域不存在！");
            pst.close();
            rs.close();

            sql="select attractions_name from tbl_attractions where attractions_id= ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,attractions_id);
            rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("景区已存在！");

            sql="select attractions_id from tbl_attractions where attractions_name= ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,attractions_name);
            rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("景区已存在！");

            sql="insert into tbl_attractions(attractions_id,area_id,attractions_name,attractions_area_name,attractions_cost," +
                    "attractions_introduce,attractions_address,tour_time,tour_route) value(?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,attractions_id);
            pst.setInt(2,area_id);
            pst.setString(3,attractions_name);
            pst.setString(4,attractions_area_name);
            pst.setFloat(5,attractions_cost_f);
            pst.setString(6,attractions_introduce);
            pst.setString(7,attractions_address);
            pst.setString(8,tour_time);
            pst.setInt(9,tour_route_i);
            pst.execute();
            pst.close();

            sql="insert into route_attractions(route_id,attractions_id,recommand_time) value(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,tour_route_i);
            pst.setInt(2,attractions_id);
            pst.setString(3,recommand_time);
            pst.execute();

            /*数据封装*/
            attractions.setArea_id(area_id);
            attractions.setAttractions_name(attractions_name);
            attractions.setAttractions_cost(attractions_cost_f);
            attractions.setAttractions_introduce(attractions_introduce);
            attractions.setAttractions_address(attractions_address);
            attractions.setTour_time(tour_time);
            attractions.setTour_route(tour_route);

            return attractions;
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

    public List<BeanAttractions> loadAll() throws BaseException{
        List<BeanAttractions> result=new ArrayList<BeanAttractions>();
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_attractions order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            while(rs.next()){
                BeanAttractions p=new BeanAttractions();
                p.setAttractions_id(rs.getInt(1));
                p.setArea_id(rs.getInt(2));
                p.setAttractions_name(rs.getString(3));
                p.setAttractions_area_name(rs.getString(4));
                p.setAttractions_cost(rs.getFloat(5));
                p.setAttractions_introduce(rs.getString(6));
                p.setAttractions_address(rs.getString(7));
                p.setTour_time(rs.getString(8));
                p.setTour_route(rs.getString(9));
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

    public void deleteAttractions(int attractions_id) throws BaseException {

        Connection conn = null;

        try{
            /*建立数据库链接*/
            conn = DBUtil.getConnection();

            String sql="delete from route_attractions where attractions_id = "+attractions_id;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

            sql="delete from tbl_attractions where attractions_id = "+attractions_id;
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
    public List<BeanAttractions> searchAttractions(String attractions_name)throws BaseException{
        List<BeanAttractions> result=new ArrayList<BeanAttractions>();

        if(attractions_name.length()==0||attractions_name==null)return result;//为空返回空结果，防止首次进入报错

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_attractions where attractions_name like '%"+attractions_name+"%' order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            if(rs.next()) {
                do {
                    BeanAttractions p = new BeanAttractions();
                    p.setAttractions_id(rs.getInt(1));
                    p.setArea_id(rs.getInt(2));
                    p.setAttractions_name(rs.getString(3));
                    p.setAttractions_area_name(rs.getString(4));
                    p.setAttractions_cost(rs.getFloat(5));
                    p.setAttractions_introduce(rs.getString(6));
                    p.setAttractions_address(rs.getString(7));
                    p.setTour_time((rs.getString(8)));
                    p.setTour_route(rs.getString(9));
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
