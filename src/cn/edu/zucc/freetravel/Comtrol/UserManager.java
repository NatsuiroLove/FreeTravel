package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import javax.print.DocFlavor;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.security.cert.CertPath;
import java.util.List;


public class UserManager {
    public BeanUser reg(String user_name,String user_realname,String user_sex, String phonenum,String email,String livecity,String user_pwd, String user_pwd2) throws BaseException {

        /*�û�������Ϊ��*/
        if(user_name==null||user_name.length()==0)throw new BaseException("�û�������Ϊ��");
        /*���벻��Ϊ��*/
        if(user_pwd==null||user_pwd.length()==0||user_pwd2==null||user_pwd2.length()==0)throw new BaseException("���벻��Ϊ��");
        /*����������Ҫ��ͬ*/
        if(!user_pwd.equals(user_pwd2))throw new BaseException("���������������Ҫ��ͬ");
        /*�Ա�Ϸ�*/
        if(!(user_sex.equals("��")||user_sex.equals("Ů")))throw new BaseException("�Ա������롰�С���Ů��");
        int sex = user_sex.equals("��")?1:0;
        /* ���ӵ����ݿ� */
        Connection conn = null;
        try{
            conn= DBUtil.getConnection();

            /* ����û��˺��Ƿ��ظ� */
            String sql="select user_id from tbl_user where user_name = ?";//sql���
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,user_name);
            ResultSet rs=pst.executeQuery();//��Ų�ѯ���
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("�û��Ѿ����ڣ�");
            }

            sql="select user_id from tbl_user where user_phonenum= ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,phonenum);
            rs=pst.executeQuery();
            if(rs.next())throw new BusinessException("�ֻ����ѱ������û��󶨣�");


            /* ������Ϊ�Ϸ����û� �������ݿ��� */
            rs.close();
            pst.close();

            sql = "insert into tbl_user(user_name,user_realname,user_sex,user_pwd,user_phonenum," +
                    "user_mail,user_livecity,reg_time,isvip,vip_end_time) values(?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user_name);
            pst.setString(2,user_realname);
            pst.setInt(3,sex);
            pst.setString(4,user_pwd);
            pst.setString(5,phonenum);
            pst.setString(6,email);
            pst.setString(7,livecity);
            pst.setString(8,String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
            pst.setBoolean(9,false);
            pst.setString(10,"");
            pst.execute();
            BeanUser user = new BeanUser();
            user.setUser_name(user_name);
            user.setUser_realname(user_realname);
            user.setUser_sex(sex);
            user.setUser_phonenum(phonenum);
            user.setUser_mail(email);
            user.setUser_livecity(livecity);
            user.setReg_time(String.valueOf(System.currentTimeMillis()));
            user.setIsvip(false);
            user.setVip_end_time("");
            return user;

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

    public BeanUser login(String user_name,String user_pwd) throws BaseException{
        /*����û����������*/
        if(user_name==null||user_name.length()==0)throw new BaseException("�û�������Ϊ�գ�");
        if(user_pwd==null||user_pwd.length()==0)throw new BaseException("���벻��Ϊ��");

        /*Ϊ�������ݿ���׼��*/
        Connection conn= null;
        /*�������ݷ�װ����*/
        BeanUser user = new BeanUser();

        if(user_pwd==null);
        try{
            conn= DBUtil.getConnection();
            String sql="select user_id from tbl_user where user_name = ? and user_pwd = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user_name);
            pst.setString(2,user_pwd);
            java.sql.ResultSet rs = pst.executeQuery();

            if(rs.next());
            else {
                throw new BusinessException("�û������ڻ��������");
            }

            sql="select * from tbl_user where user_name= ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,user_name);
            rs=pst.executeQuery();

            if(rs.next()){
                user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                user.setUser_realname(rs.getString(3));
                user.setUser_sex(rs.getInt(4));
                user.setUser_pwd(rs.getString(5));
                user.setUser_phonenum(rs.getString(6));
                user.setUser_mail(rs.getString(7));
                user.setUser_livecity(rs.getString(8));
                user.setReg_time(rs.getString(9));
                user.setIsvip(rs.getBoolean(10));
                user.setVip_end_time(rs.getString(11));

            }
            return user;
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

    public BeanUser change_pwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException{
        String user_name=user.getUser_name();/*�洢�������ڵ�¼�û���id*/

        Connection conn=null;//���ݿ�����Ԥ��

        if(!newPwd.equals(newPwd2))throw new BaseException("�����������������뱣��һ�£�");
        if(newPwd.length()==0||newPwd2.length()==0)throw new BaseException("����������Ϊ�գ�");

        try{
            conn=DBUtil.getConnection();//�������ݿ�

            String sql="select user_pwd from tbl_user where user_name=?";//Ѱ�Ҷ�Ӧ�û������ݿ��д洢����Ϣ
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1,user_name);
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
            sql = "update tbl_user set user_pwd=? where user_name=?";//���¸��û�����
            pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,user_name);
            pst.execute();
            pst.close();
            /*��װ*/
            user.setUser_pwd(newPwd);
            return user;
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

    public void beVip()throws BaseException{
        int user_id= BeanUser.currentLoginUser.getUser_id();


        Connection conn = null;
        try{
            conn=DBUtil.getConnection();//�������ݿ�

            String sql="select isvip from tbl_user where user_id = ? ";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            pst.setInt(1,user_id);
            java.sql.ResultSet rs  =pst.executeQuery();

            if(rs.next()){
                if(rs.getBoolean(1)==true)throw new BusinessException("�Ѿ��ǻ�Ա��");
            }


            sql="update tbl_user set isvip = true where user_id = ? ";
            pst= conn.prepareStatement(sql);
            pst.setInt(1,user_id);
            pst.execute();

            sql = "update tbl_user set vip_end_time = ? where user_id=?";
            pst = conn.prepareStatement(sql);

            String newtime;
            String time = String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            char month_10= time.charAt(3);
            char month_01= time.charAt(4);
            if(month_10=='1'){
                newtime=time.substring(0,4)+(char)(month_01+1)+time.substring(5);
            }
            else if(month_01=='9'){
                newtime=time.substring(0,3)+"10"+time.substring(5);
            }else {
                newtime=time.substring(0,4)+(char)(month_01+1)+time.substring(5);
            }

            pst.setString(1,newtime);
            pst.setInt(2,user_id);
            pst.execute();
            pst.close();

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
    public void userModifyInfo(String name , String realname,String sex ,String phone,String mail,String livecity,String registerTime,String isvip ,String vipendtime) throws BaseException{
        int user_id= BeanUser.currentLoginUser.getUser_id();

        int sex_i= sex=="��"?1:0;
        Boolean isvip_i = Boolean.parseBoolean(isvip);

        Connection conn = null;
        try{
            conn=DBUtil.getConnection();//�������ݿ�

            String sql="update tbl_user set user_name = ? where user_id = ?";
            java.sql.PreparedStatement pst =conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set user_realname = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString(1,realname);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set user_sex = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,sex_i);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set user_phonenum = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString(1,phone);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set user_mail = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString(1,mail);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set user_livecity = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString(1,livecity);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set reg_time = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString(1,registerTime);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set isvip = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setBoolean(1,isvip_i);
            pst.setInt(2,user_id);
            pst.execute();

            sql="update tbl_user set vip_end_time = ? where user_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setString(1,vipendtime);
            pst.setInt(2,user_id);
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

    public List<BeanUser> loadAll() throws BaseException {
        List<BeanUser> result=new ArrayList<BeanUser>();
        Connection conn=null;
        try{
            conn= DBUtil.getConnection();
            String sql="select * from tbl_user order by user_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*���������н����װ��*/
            while(rs.next()){
                BeanUser p=new BeanUser();
                p.setUser_id(rs.getInt(1));
                p.setUser_name(rs.getString(2));
                p.setUser_realname(rs.getString(3));
                p.setUser_sex(rs.getInt(4));
                p.setUser_pwd(rs.getString(5));
                p.setUser_phonenum(rs.getString(6));
                p.setUser_mail(rs.getString(7));
                p.setUser_livecity(rs.getString(8));
                p.setReg_time(rs.getString(9));
                p.setIsvip(rs.getBoolean(10));
                p.setVip_end_time(rs.getString(11));
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


    public List<BeanUser> searchUser(String user_id_s)throws BaseException{
        List<BeanUser> result=new ArrayList<BeanUser>();

        if(user_id_s.length()==0||user_id_s==null)return result;//Ϊ�շ��ؿս������ֹ�״ν��뱨��

        int user_id = Integer.valueOf(user_id_s).intValue();

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_user where user_id ="+user_id+" order by user_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*���������н����װ��*/
            if(rs.next()) {
                do {
                    BeanUser p=new BeanUser();
                    p.setUser_id(rs.getInt(1));
                    p.setUser_name(rs.getString(2));
                    p.setUser_realname(rs.getString(3));
                    p.setUser_sex(rs.getInt(4));
                    p.setUser_pwd(rs.getString(5));
                    p.setUser_phonenum(rs.getString(6));
                    p.setUser_mail(rs.getString(7));
                    p.setUser_livecity(rs.getString(8));
                    p.setReg_time(rs.getString(9));
                    p.setIsvip(rs.getBoolean(10));
                    p.setVip_end_time(rs.getString(11));
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
