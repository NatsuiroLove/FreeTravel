package cn.edu.zucc.freetravel.model;

/**
 * 景点数据封装 将显示在界面
 */
public class BeanAttractions {
    public static final String[] tableTitles = {"景点编号","所属区域编号","景点名称","景区所属地区名称","景区票价","景区介绍","景区详细地址","游览用时","推荐游览路线"};

    /*表项内容填充*/
    public String getItems(int col){
        if(col==0)return String.valueOf(getAttractions_id());
        else if(col==1)return String.valueOf(getArea_id());
        else if(col==2)return getAttractions_name();
        else if(col==3)return getAttractions_area_name();
        else if(col==4)return String.valueOf(getAttractions_cost());
        else if(col==5)return getAttractions_introduce();
        else if(col==6)return getAttractions_address();
        else if(col==7)return getTour_time();
        else if(col==8)return getTour_route();
        else return"";
    }

    /*数据封装*/
    private int attractions_id;
    private int area_id;
    private String attractions_name;
    private String attractions_area_name;
    private float attractions_cost;
    private String attractions_introduce;
    private String attractions_address;
    private String tour_time;
    private String tour_route;

    public void setAttractions_id(int attractions_id){
        this.attractions_id=attractions_id;
    }
    public int getAttractions_id(){
        return attractions_id;
    }

    public void setArea_id(int area_id){
        this.area_id=area_id;
    }
    public int getArea_id(){
        return area_id;
    }

    public void setAttractions_cost(float attractions_cost){
        this.attractions_cost=attractions_cost;
    }
    public float getAttractions_cost(){
        return attractions_cost;
    }

    public void setAttractions_name(String attractions_name){
        this.attractions_name=attractions_name;
    }
    public String getAttractions_name(){
        return attractions_name;
    }

    public void setAttractions_introduce(String attractions_introduce){
        this.attractions_introduce=attractions_introduce;
    }
    public String getAttractions_introduce(){
        return attractions_introduce;
    }

    public void setAttractions_address(String attractions_address){
        this.attractions_address=attractions_address;
    }
    public String getAttractions_address(){
        return attractions_address;
    }

    public void setTour_time(String tour_time){
        this.tour_time=tour_time;
    }
    public String getTour_time(){
        return tour_time;
    }

    public void setTour_route(String tour_route){
        this.tour_route=tour_route;
    }
    public String getTour_route(){
        return tour_route;
    }

    public void setAttractions_area_name(String attractions_area_name){
        this.attractions_area_name=attractions_area_name;
    }
    public String getAttractions_area_name(){
        return attractions_area_name;
    }

}
