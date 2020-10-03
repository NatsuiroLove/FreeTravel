package cn.edu.zucc.freetravel.Comtrol;

import cn.edu.zucc.freetravel.model.BeanAttractions;
import cn.edu.zucc.freetravel.model.BeanHotel;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminHotelManager {
    /*�����Ƶ�*/
    public BeanHotel addHotel(String hotel_id_s,String hotel_name, String hotel_area_name,  String hotel_introduce
            , String hotel_address , String hotel_level, String run_time,String hotel_route,String recommand_time)throws BaseException {

        Connection conn=null;
        BeanHotel hotel=new BeanHotel();

        int area_id=0;
        int hotel_id=Integer.valueOf(hotel_id_s).intValue();

        float level= Float.parseFloat(hotel_level);//�ȼ����㻯
        int hotel_route_i= Integer.valueOf(hotel_route).intValue();

        if(run_time.charAt(0)>'2'||run_time.charAt(3)>'5'||run_time.charAt(6)>'2'||run_time.charAt(9)>'5')throw new BusinessException("��Ӫʱ�䲻�淶");
        if(run_time.charAt(0)=='2'&&run_time.charAt(1)>'4'||run_time.charAt(6)=='2'&&run_time.charAt(7)>'4')throw new BusinessException("��Ӫʱ�䲻�淶");
        if(run_time.charAt(0)=='2'&&run_time.charAt(1)=='4'&&(run_time.charAt(3)!='0'||run_time.charAt(4)!='0'))throw new BusinessException("��Ӫʱ�䲻�淶");
        if(run_time.charAt(6)=='2'&&run_time.charAt(7)=='4'&&(run_time.charAt(9)!='0'||run_time.charAt(10)!='0'))throw new BusinessException("��Ӫʱ�䲻�淶");
        /*������Ϊ��*/
        if(hotel_name==null||hotel_name.length()==0)throw new BaseException("�Ƶ����Ʋ���Ϊ��");
        if(hotel_area_name==null||hotel_area_name.length()==0)throw new BaseException("�Ƶ�������Ʋ���Ϊ��");
        if(hotel_level==null||hotel_level.length()==0)throw new BaseException("�Ƶ���������Ϊ��");
        if(hotel_address==null||hotel_address.length()==0)throw new BaseException("�Ƶ���ϸ��ַ����Ϊ��");


        try{
            conn= DBUtil.getConnection();
            String sql="";
            java.sql.PreparedStatement pst;

            sql="select route_id from tbl_route where route_id = '"+hotel_route_i+"'";
            pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            if(!rs.next())throw new BusinessException("��·������");

            sql="select area_id from tbl_area where area_name = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,hotel_area_name);
            rs=pst.executeQuery();

            if(rs.next()) area_id=rs.getInt(1);
            else throw new BusinessException("���򲻴��ڣ�");
            pst.close();
            rs.close();

            sql="select hotel_name from tbl_hotel where hotel_id= ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,hotel_id);
            rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("�Ƶ��Ѵ��ڣ�");

            sql="select hotel_id from tbl_hotel where hotel_name= ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,hotel_name);
            rs=pst.executeQuery();

            if(rs.next())throw new BusinessException("�Ƶ��Ѵ��ڣ�");

            sql="insert into tbl_hotel(hotel_id,area_id,hotel_name,hotel_area_name," +
                    "hotel_introduce,hotel_adress,hotel_level,startrun_time,hotel_route) value(?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,hotel_id);
            pst.setInt(2,area_id);
            pst.setString(3,hotel_name);
            pst.setString(4,hotel_area_name);
            pst.setString(5,hotel_introduce);
            pst.setString(6,hotel_address);
            pst.setFloat(7,level);
            pst.setString(8,run_time);
            pst.setInt(9,hotel_route_i);
            pst.execute();

            sql="insert into route_hotel(route_id,hotel_id,recommand_time) value(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,hotel_route_i);
            pst.setInt(2,hotel_id);
            pst.setString(3,recommand_time);
            pst.execute();

            /*���ݷ�װ*/
            hotel.setArea_id(area_id);
            hotel.setHotel_name(hotel_name);
            hotel.setHotel_area_name(hotel_area_name);
            hotel.setHotel_introduce(hotel_introduce);
            hotel.setHotel_address(hotel_address);
            hotel.setLevel(level);
            hotel.setStartrun_time(run_time);
            hotel.setHotel_route(hotel_route_i);

            return hotel;
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

    public List<BeanHotel> loadAll() throws BaseException{
        List<BeanHotel> result=new ArrayList<BeanHotel>();
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_hotel order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*���������н����װ��*/
            while(rs.next()){
                BeanHotel p=new BeanHotel();
                p.setHotel_id(rs.getInt(1));
                p.setArea_id(rs.getInt(2));
                p.setHotel_name(rs.getString(3));
                p.setHotel_area_name(rs.getString(4));
                p.setHotel_introduce(rs.getString(5));
                p.setHotel_address(rs.getString(6));
                p.setLevel(rs.getFloat(7));
                p.setStartrun_time(rs.getString(8));
                p.setHotel_route(rs.getInt(9));
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

    public void deleteHotel(int hotel_id) throws BaseException {

        Connection conn = null;

        try{
            /*�������ݿ�����*/
            conn = DBUtil.getConnection();

            String sql="delete from route_hotel where hotel_id = "+hotel_id;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute(sql);
            pst.close();

            sql="delete from tbl_hotel where hotel_id = "+hotel_id;
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

    /*��ѯ��������*/
    public List<BeanHotel> searchHotel(String hotel_name)throws BaseException{
        List<BeanHotel> result=new ArrayList<BeanHotel>();

        if(hotel_name.length()==0||hotel_name==null)return result;//Ϊ�շ��ؿս������ֹ�״ν��뱨��

        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select * from tbl_hotel where hotel_name like '%"+hotel_name+"%' order by area_id";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            java.sql.ResultSet rs= pst.executeQuery();

            /*���������н����װ��*/
            if(rs.next()) {
                do {
                    BeanHotel p = new BeanHotel();
                    p.setHotel_id(rs.getInt(1));
                    p.setArea_id(rs.getInt(2));
                    p.setHotel_name(rs.getString(3));
                    p.setHotel_area_name(rs.getString(4));
                    p.setHotel_introduce(rs.getString(5));
                    p.setHotel_address(rs.getString(6));
                    p.setLevel((rs.getInt(7)));
                    p.setStartrun_time(rs.getString(8));
                    p.setHotel_route(rs.getInt(9));
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
