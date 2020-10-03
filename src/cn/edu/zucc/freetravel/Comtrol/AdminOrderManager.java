package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanHotel;
import cn.edu.zucc.freetravel.model.BeanProduct;
import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderManager {
    public List<BeanProductOrder> loadAll() throws BaseException {
        List<BeanProductOrder> result=new ArrayList<BeanProductOrder>();
        Connection conn=null;
        try{
            conn= DBUtil.getConnection();
            String sql="select * from tbl_productorder order by user_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            while(rs.next()){
                BeanProductOrder p=new BeanProductOrder();
                p.setOrder_id(rs.getInt(1));
                p.setUser_id(rs.getInt(2));
                p.setOrder_group_num(rs.getInt(3));
                p.setStart_city(rs.getString(4));
                p.setReal_pay(rs.getFloat(5));
                p.setPay_time(rs.getString(6));
                p.setOrder_state(rs.getInt(7));
                p.setAccommodation_grade(rs.getInt(8));
                p.setRecommandPlay_grade(rs.getInt(9));
                p.setBelongProduct_grade(rs.getInt(10));
                p.setEvaluate(rs.getString(11));
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


    public List<BeanProductOrder> searchOrder(String order_id_s)throws BaseException{
        List<BeanProductOrder> result=new ArrayList<BeanProductOrder>();

        if(order_id_s.length()==0||order_id_s==null)return result;//为空返回空结果，防止首次进入报错

        int order_id = Integer.valueOf(order_id_s).intValue();

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_productorder where order_id ="+order_id+" order by user_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
            if(rs.next()) {
                do {
                    BeanProductOrder p=new BeanProductOrder();
                    p.setOrder_id(rs.getInt(1));
                    p.setUser_id(rs.getInt(2));
                    p.setOrder_group_num(rs.getInt(3));
                    p.setStart_city(rs.getString(4));
                    p.setReal_pay(rs.getFloat(5));
                    p.setPay_time(rs.getString(6));
                    p.setOrder_state(rs.getInt(7));
                    p.setAccommodation_grade(rs.getInt(8));
                    p.setRecommandPlay_grade(rs.getInt(9));
                    p.setBelongProduct_grade(rs.getInt(10));
                    p.setEvaluate(rs.getString(11));
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

    public void orderInfoM(String order_group_num_s,String start_city,String accommodation_grade_s,String recommandPlay_grade_s,String belongProduct_grade_s,String evaluate)throws BaseException{
        Connection conn = null;

        int order_id=0;
        int order_group_num=Integer.valueOf(order_group_num_s).intValue();
        int accommodation_grade=Integer.valueOf(accommodation_grade_s).intValue();
        int recommandPlay_grade=Integer.valueOf(recommandPlay_grade_s).intValue();
        int belongProduct_grade=Integer.valueOf(belongProduct_grade_s).intValue();

        if(accommodation_grade<0||accommodation_grade>5||recommandPlay_grade<0||recommandPlay_grade>5||belongProduct_grade<0||belongProduct_grade>5)throw new BaseException("请输入分数在0-5分");

        try{
            conn=DBUtil.getConnection();//链接数据库

            /*寻找到该账号此次登录使用的订单*/
            String sql = "select max(order_id) from tbl_productorder ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())order_id = rs.getInt(1);
            pst.close();
            rs.close();

            sql="update tbl_productorder set order_group_num = ? where order_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,order_group_num);
            pst.setInt(2,order_id);
            pst.execute();

            sql="update tbl_productorder set start_city = ? where order_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString (1,start_city);
            pst.setInt(2,order_id);
            pst.execute();

            sql="update tbl_productorder set evaluate = ? where order_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString (1,evaluate);
            pst.setInt(2,order_id);
            pst.execute();

            sql="update tbl_productorder set accommodation_grade = ? where order_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,accommodation_grade);
            pst.setInt(2,order_id);
            pst.execute();

            sql="update tbl_productorder set recommandPlay_grade = ? where order_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,recommandPlay_grade);
            pst.setInt(2,order_id);
            pst.execute();

            sql="update tbl_productorder set belongProduct_grade = ? where order_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,belongProduct_grade);
            pst.setInt(2,order_id);
            pst.execute();

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

    public void identyInfoM(String name,String realname)throws BaseException{
        Connection conn = null;

        int order_id = 0;

        try{
            conn=DBUtil.getConnection();//链接数据库

            /*寻找到该账号此次登录使用的订单*/
            String sql = "select max(order_id) from tbl_productorder ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())order_id = rs.getInt(1);
            pst.close();
            rs.close();

            sql="insert into tbl_oderuserinformation(order_id,user_identifynum,user_realname) values(?,?,?)";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            pst.setString(2,name);
            pst.setString(3,realname);
            pst.execute();


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
