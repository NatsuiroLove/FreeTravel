package cn.edu.zucc.freetravel.model;

import java.sql.Time;

/**
 * 酒店数据封装 将显示在界面
 */
public class BeanHotel {
    public static final String[] tableTitles={"酒店编号","所属地区编号","酒店名称","酒店介绍","酒店地址","酒店评级","开业时间","推荐线路"};

    public String getItems(int col){
        if(col==0)return String.valueOf(getHotel_id());
        else if(col==1)return getHotel_area_name();
        else if(col==2)return getHotel_name();
        else if(col==3)return getHotel_introduce();
        else if(col==4)return getHotel_address();
        else if(col==5)return String.valueOf(getLevel());
        else if(col==6)return getStartrun_time();
        else if(col==7)return String.valueOf(getHotel_route());
        return "";
    }

    /*数据封装*/
    private int hotel_id;
    private int area_id;
    private String hotel_name;
    private String hotel_area_name;
    private String hotel_introduce;
    private String hotel_address;
    private float level;
    private String startrun_time;
    private int hotel_route;

    public void setHotel_route(int hotel_route){
        this.hotel_route= hotel_route;
    }
    public int  getHotel_route(){
        return hotel_route;
    }

    public void setHotel_id(int hotel_id){
        this.hotel_id=hotel_id;
    }
    public int getHotel_id(){
        return hotel_id;
    }

    public void setArea_id(int area_id){
        this.area_id=area_id;
    }
    public int getArea_id(){
        return area_id;
    }

    public void setLevel(float level){
        this.level=level;
    }
    public float getLevel(){
        return level;
    }

    public void setHotel_introduce(String hotel_introduce){
        this.hotel_introduce=hotel_introduce;
    }
    public String getHotel_introduce(){
        return hotel_introduce;
    }

    public void setHotel_name(String hotel_name){
        this.hotel_name=hotel_name;
    }
    public String getHotel_name(){
        return hotel_name;
    }

    public void setHotel_address(String hotel_address){
        this.hotel_address=hotel_address;
    }
    public String getHotel_address(){
        return hotel_address;
    }

    public void setStartrun_time(String startrun_time){
        this.startrun_time=startrun_time;
    }
    public String getStartrun_time(){
        return startrun_time;
    }

    public void setHotel_area_name(String hotel_area_name){
        this.hotel_area_name=hotel_area_name;
    }
    public String getHotel_area_name(){
        return hotel_area_name;
    }
}
