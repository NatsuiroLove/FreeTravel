package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanArea;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class AdminAreaManager{
    /**��������
     * ����������Ϊ�� �����ظ�
     * �����������Ϊ�� ����������Ϊ����Ϊһ������
     * ����������Ϊ�� ��Ϊ�������� ��������ֵ���������ݿ��������Ӧ�ĵ���
     * @param area_name
     * @param belong_areaname
     * @return
     * @throws BaseException
     */
    public BeanArea addArea(String area_id,String area_name,String belong_areaname)throws BaseException{
        Connection conn=null;
        BeanArea area=new BeanArea();

        int area_id_i=Integer.valueOf(area_id).intValue();

        /*����������Ϊ��*/
        if(area_name==null||area_name.length()==0)throw new BaseException("�������Ʋ���Ϊ��");
        if(area_id==null||area_id.length()==0)throw new BaseException("����id����Ϊ��");

        try{
            conn=DBUtil.getConnection();
            String sql="";
            java.sql.PreparedStatement pst;
            java.sql.ResultSet rs;

            /*����������ظ�*/
            sql="select * from tbl_area where area_name = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,area_name);
            rs = pst.executeQuery();

            if(rs.next())throw new BusinessException("�����������ظ����ڣ�");
            pst.close();
            rs.close();

            sql="select * from tbl_area where area_id = ?";
            pst= conn.prepareStatement(sql);
            pst.setInt(1,area_id_i);
            rs = pst.executeQuery();

            if(rs.next())throw new BusinessException("�����Ų����ظ����ڣ�");
            pst.close();
            rs.close();

            /*���Ϊһ������*/
            if(belong_areaname==null||belong_areaname.length()==0){
                sql="insert into tbl_area(area_id,area_name,area_belong_id,area_belong_name) values(?,?,0,?)";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,area_id_i);
                pst.setString(2,area_name);
                pst.setString(3," ");
                pst.execute();
                pst.close();
            }
            /*��������*/
            else {
                sql="select area_id from tbl_area where area_name = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1,belong_areaname);
                rs=pst.executeQuery();

                /*������Ӧ��һ������*/
                if(rs.next()){
                    int belong_id=rs.getInt(1);
                    /*���µ��������area*/
                    sql="insert into tbl_area(area_id,area_name,area_belong_id,area_belong_name) values(?,?,?,?)";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,area_id_i);
                    pst.setString(2,area_name);
                    pst.setInt(3,belong_id);
                    pst.setString(4,belong_areaname);
                    pst.execute();

                    pst.close();
                    rs.close();

                    /*���ݷ�װ*/
                    area.setArea_name(area_name);
                    area.setArea_belong_id(belong_id);
                    area.setArea_belong_name(belong_areaname);
                }
                /*һ�����򲻴���*/
                else{
                    throw new BusinessException("�����ڵ����򲻴��������ݿ��У������¼��");
                }
            }
            return area;
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

    public List<BeanArea> loadAll() throws BaseException{
        List<BeanArea> result=new ArrayList<BeanArea>();
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_area order by area_belong_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*���������н����װ��*/
            while(rs.next()){
                BeanArea p=new BeanArea();
                p.setArea_id(rs.getInt(1));
                p.setArea_name(rs.getString(2));
                p.setArea_belong_id(rs.getInt(3));
                p.setArea_belong_name(rs.getString(4));
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

    public void deleteArea(int area_id) throws BaseException {

        Connection conn = null;

        try{
            /*�������ݿ�����*/
            conn = DBUtil.getConnection();
            /*��ֹɾ�����ڴ������������*/
            String sql = "select count(*) from tbl_area where area_belong_id = "+area_id;

            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);

            if(rs.next()){
                if(rs.getInt(1)>0){
                    rs.close();
                    st.close();
                    throw new BusinessException("�������´��ڴ������򣬲���ɾ����");
                }
            }
            rs.close();

            sql="delete from tbl_area where area_id = "+area_id;
            st.execute(sql);
            st.close();

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

    /*��ѯ������*/
    public List<BeanArea> searchArea(String area_name)throws BaseException{
        List<BeanArea> result=new ArrayList<BeanArea>();

        if(area_name.length()==0||area_name==null)return result;

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_area where area_name like '%"+area_name+"%' order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*���������н����װ��*/
            if(rs.next()) {
                do {
                    BeanArea p = new BeanArea();
                    p.setArea_id(rs.getInt(1));
                    p.setArea_name(rs.getString(2));
                    p.setArea_belong_id(rs.getInt(3));
                    p.setArea_belong_name(rs.getString(4));
                    result.add(p);
                } while (rs.next());
            }

            pst.close();
            rs.close();

            sql="select * from tbl_area where area_belong_name like '%"+area_name+"%' order by area_id";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();

            /*���������н����װ��*/
            while(rs.next()){
                BeanArea p=new BeanArea();
                p.setArea_id(rs.getInt(1));
                p.setArea_name(rs.getString(2));
                p.setArea_belong_id(rs.getInt(3));
                p.setArea_belong_name(rs.getString(4));
                result.add(p);
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
