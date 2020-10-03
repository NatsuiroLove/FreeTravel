package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.awt.desktop.SystemEventListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserOrderManager {
    public BeanProductOrder userOrderStart() throws BaseException {

        Connection conn= null;
        int user_id=BeanUser.currentLoginUser.getUser_id();

        try{
            /*建立数据库链接*/
            conn = DBUtil.getConnection();
            /*寻找并删除空订单*/
            String sql = "delete from tbl_productorder where order_state = 0";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();

            /*新建空订单并返回此订单*/
            sql="insert into tbl_productorder(user_id,order_state,real_pay) value(?,0,0)";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,user_id);
            pst.execute();

            BeanProductOrder order = new BeanProductOrder();
            order.setUser_id(user_id);

            BeanProductOrder.currentUserOrder=order;
            return order;

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
    public void userOrderPay() throws BaseException{
        Connection conn= null;
        int order_id=0;

        try{
            /*建立数据库链接*/
            conn = DBUtil.getConnection();
            /*寻找并删除空订单*/
            /*寻找到该账号此次登录使用的订单*/
            String sql = "select max(order_id) from tbl_productorder ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())order_id = rs.getInt(1);
            pst.close();
            rs.close();

            /*更改订单状态*/
            sql="update tbl_productorder set order_state=2 where order_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            pst.execute();

            sql="update tbl_productorder set pay_time = ? where order_id = ? ";
            pst=conn.prepareStatement(sql);
            pst.setString(1,String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
            pst.setInt(2,order_id);
            pst.execute();
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


}
