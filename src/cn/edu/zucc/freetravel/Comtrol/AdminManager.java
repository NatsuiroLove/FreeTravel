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

        /*�û�������Ϊ��*/
        if(admin_name==null||admin_name.length()==0)throw new BaseException("�û�������Ϊ��");
        /*���벻��Ϊ��*/
        if(admin_pwd==null||admin_pwd.length()==0||admin_pwd2==null||admin_pwd2.length()==0)throw new BaseException("���벻��Ϊ��");
        /*����������Ҫ��ͬ*/
        if(!admin_pwd.equals(admin_pwd2))throw new BaseException("���������������Ҫ��ͬ");

        /* ���ӵ����ݿ� */
        Connection conn = null;
        try{
            conn=DBUtil.getConnection();

            /* ����û��˺��Ƿ��ظ� */
            String sql="select admin_id from tbl_administrator where admin_name = ?";//sql���
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,admin_name);
            ResultSet rs=pst.executeQuery();//��Ų�ѯ���
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("�û��Ѿ����ڣ�");
            }

            /* ������Ϊ�Ϸ����û� �������ݿ��� */
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
            /* �ر��������� */
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
        /*����û����������*/
        if(admin_name==null||admin_name.length()==0)throw new BaseException("�û�������Ϊ�գ�");
        if(admin_pwd==null||admin_pwd.length()==0)throw new BaseException("���벻��Ϊ��");

        /*Ϊ�������ݿ���׼��*/
        Connection conn= null;
        /*�������ݷ�װ����*/
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
                throw new BusinessException("�û������ڻ��������");
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
        String admin_name=admin.getAdmin_name();/*�洢�������ڵ�¼�û���id*/

        Connection conn=null;//���ݿ�����Ԥ��

        if(!newPwd.equals(newPwd2))throw new BaseException("�����������������뱣��һ�£�");
        if(newPwd.length()==0||newPwd2.length()==0)throw new BaseException("����������Ϊ�գ�");

        try{
            conn=DBUtil.getConnection();//�������ݿ�

            String sql="select admin_pwd from tbl_administrator where admin_name=?";//Ѱ�Ҷ�Ӧ�û������ݿ��д洢����Ϣ
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1,admin_name);
            java.sql.ResultSet rs=pst.executeQuery(); //�洢���

            /*�û������� ��ԭ���벻��*/
            if(rs.next()) {
                if(!oldPwd.equals(rs.getString(1)))throw new BusinessException("ԭ���벻����");
            }
//            else{
//                throw new BusinessException("�û���Ϣ�����ڣ�");
//            }
            rs.close();
            pst.close();

            /*�û�������������������ͬ��������ȷ �������ݿ���Ϣ*/
            sql = "update tbl_administrator set admin_pwd=? where admin_name=?";//���¸��û�����
            pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,admin_name);
            pst.execute();
            pst.close();
            /*��װ*/
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
