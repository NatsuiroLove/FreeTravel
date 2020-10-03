package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanAdmin;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminManager {
    public BeanAdmin reg(String admin_name,String admin_pwd,String admin_pwd2) throws BaseException{

        /*用户名不可为空*/
        if(admin_name==null||admin_name.length()==0)throw new BaseException("用户名不可为空");
        /*密码不可为空*/
        if(admin_pwd==null||admin_pwd.length()==0||admin_pwd2==null||admin_pwd2.length()==0)throw new BaseException("密码不可为空");
        /*两次密码需要相同*/
        if(!admin_pwd.equals(admin_pwd2))throw new BaseException("两次输入的密码需要相同");

        /* 链接到数据库 */
        Connection conn = null;
        try{
            conn=DBUtil.getConnection();

            /* 检查用户账号是否重复 */
            String sql="select admin_id from tbl_administrator where admin_name = ?";//sql语句
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,admin_name);
            ResultSet rs=pst.executeQuery();//存放查询结果
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("用户已经存在！");
            }

            /* 检测结束为合法新用户 插入数据库中 */
            rs.close();
            pst.close();
            sql = "insert into tbl_administrator(admin_name,admin_pwd) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,admin_name);
            pst.setString(2,admin_pwd);
            pst.execute();
            BeanAdmin admin = new BeanAdmin();
            admin.setAdmin_name(admin_name);
            admin.setAdmin_pwd(admin_pwd);
            return admin;

        }catch(SQLException ex){
            throw new DbException(ex);
        }finally {
            /* 关闭数据链接 */
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public BeanAdmin login(String admin_name,String admin_pwd) throws BaseException{
        /*检查用户名和密码空*/
        if(admin_name==null||admin_name.length()==0)throw new BaseException("用户名不可为空！");
        if(admin_pwd==null||admin_pwd.length()==0)throw new BaseException("密码不可为空");

        /*为连接数据库做准备*/
        Connection conn= null;
        /*建立数据封装对象*/
        BeanAdmin admin = new BeanAdmin();

        if(admin_pwd==null);
        try{
            conn= DBUtil.getConnection();
            String sql="select admin_id from tbl_administrator where admin_name = ? and admin_pwd = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,admin_name);
            pst.setString(2,admin_pwd);
            java.sql.ResultSet rs = pst.executeQuery();

            if(rs.next()){
                admin.setAdmin_id(rs.getInt(1));
                admin.setAdmin_name(admin_name);
                admin.setAdmin_pwd(admin_pwd);
                return admin;
            }
            else {
                throw new BusinessException("用户不存在或密码错误！");
            }

        }catch(SQLException ex){
            throw new DbException(ex);
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public BeanAdmin change_pwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException{
        String admin_name=admin.getAdmin_name();/*存储现在正在登录用户的id*/

        Connection conn=null;//数据库链接预备

        if(!newPwd.equals(newPwd2))throw new BaseException("两次输入的新密码必须保持一致！");
        if(newPwd.length()==0||newPwd2.length()==0)throw new BaseException("密码栏不可为空！");

        try{
            conn=DBUtil.getConnection();//链接数据库

            String sql="select admin_pwd from tbl_administrator where admin_name=?";//寻找对应用户在数据库中存储的信息
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1,admin_name);
            java.sql.ResultSet rs=pst.executeQuery(); //存储结果

            /*用户名存在 但原密码不符*/
            if(rs.next()) {
                if(!oldPwd.equals(rs.getString(1)))throw new BusinessException("原密码不符！");
            }
//            else{
//                throw new BusinessException("用户信息不存在！");
//            }
            rs.close();
            pst.close();

            /*用户名存在且两次密码相同且密码正确 更改数据库信息*/
            sql = "update tbl_administrator set admin_pwd=? where admin_name=?";//更新该用户密码
            pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,admin_name);
            pst.execute();
            pst.close();
            /*封装*/
            admin.setAdmin_pwd(newPwd);
            return admin;
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
