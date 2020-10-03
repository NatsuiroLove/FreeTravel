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
    /**新增地区
     * 区域名不可为空 不可重复
     * 所属区域可以为空 若所属区域为空则为一级区域
     * 若所属区域不为空 则为二级区域 所属区域值必须在数据库检索到相应的地区
     * @param area_name
     * @param belong_areaname
     * @return
     * @throws BaseException
     */
    public BeanArea addArea(String area_id,String area_name,String belong_areaname)throws BaseException{
        Connection conn=null;
        BeanArea area=new BeanArea();

        int area_id_i=Integer.valueOf(area_id).intValue();

        /*区域名不可为空*/
        if(area_name==null||area_name.length()==0)throw new BaseException("区域名称不可为空");
        if(area_id==null||area_id.length()==0)throw new BaseException("区域id不可为空");

        try{
            conn=DBUtil.getConnection();
            String sql="";
            java.sql.PreparedStatement pst;
            java.sql.ResultSet rs;

            /*检查区域名重复*/
            sql="select * from tbl_area where area_name = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,area_name);
            rs = pst.executeQuery();

            if(rs.next())throw new BusinessException("区域名不可重复存在！");
            pst.close();
            rs.close();

            sql="select * from tbl_area where area_id = ?";
            pst= conn.prepareStatement(sql);
            pst.setInt(1,area_id_i);
            rs = pst.executeQuery();

            if(rs.next())throw new BusinessException("区域编号不可重复存在！");
            pst.close();
            rs.close();

            /*如果为一级区域*/
            if(belong_areaname==null||belong_areaname.length()==0){
                sql="insert into tbl_area(area_id,area_name,area_belong_id,area_belong_name) values(?,?,0,?)";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,area_id_i);
                pst.setString(2,area_name);
                pst.setString(3," ");
                pst.execute();
                pst.close();
            }
            /*二级区域*/
            else {
                sql="select area_id from tbl_area where area_name = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1,belong_areaname);
                rs=pst.executeQuery();

                /*存在相应的一级区域*/
                if(rs.next()){
                    int belong_id=rs.getInt(1);
                    /*将新的区域插入area*/
                    sql="insert into tbl_area(area_id,area_name,area_belong_id,area_belong_name) values(?,?,?,?)";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,area_id_i);
                    pst.setString(2,area_name);
                    pst.setInt(3,belong_id);
                    pst.setString(4,belong_areaname);
                    pst.execute();

                    pst.close();
                    rs.close();

                    /*数据封装*/
                    area.setArea_name(area_name);
                    area.setArea_belong_id(belong_id);
                    area.setArea_belong_name(belong_areaname);
                }
                /*一级区域不存在*/
                else{
                    throw new BusinessException("所属于的区域不存在于数据库中，请重新检查");
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

            /*将所有行列结果封装中*/
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
            /*建立数据库链接*/
            conn = DBUtil.getConnection();
            /*禁止删除存在从属区域的区域*/
            String sql = "select count(*) from tbl_area where area_belong_id = "+area_id;

            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);

            if(rs.next()){
                if(rs.getInt(1)>0){
                    rs.close();
                    st.close();
                    throw new BusinessException("该区域下存在从属区域，不可删除！");
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

    /*查询区域功能*/
    public List<BeanArea> searchArea(String area_name)throws BaseException{
        List<BeanArea> result=new ArrayList<BeanArea>();

        if(area_name.length()==0||area_name==null)return result;

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_area where area_name like '%"+area_name+"%' order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*将所有行列结果封装中*/
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

            /*将所有行列结果封装中*/
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
