package cn.edu.zucc.freetravel.model;

/**
 * 餐厅数据封装 将显示在界面上
 */
public class BeanRestaurant {
    public static final String[] tableTitles={"餐厅编号","所属区域编号","餐厅名称","餐厅介绍","餐厅地址","特色介绍","人均消费","推荐路线"};

    /*获取表格内表项内容*/
    public String getItems(int col){
        if(col==0)return String.valueOf(getRestaurant_id());
        else if(col==1)return String.valueOf(getArea_id());
        else if(col==2)return getRestaurant_name();
        else if(col==3)return getRestaurant_introduce();
        else if(col==4)return getRestaurant_address();
        else if(col==5)return getSpecial_introduce();
        else if(col==6)return String.valueOf(getAverage_cost());
        else if(col==7)return String.valueOf(getRestaurant_route());
        return "";//待补充
    }

    /*数据封装*/
    private int restaurant_id;
    private int area_id;
    private String restaurant_name;
    private String restaurant_area_name;
    private String restaurant_introduce;
    private String restaurant_address;
    private String special_introduce;
    private float average_cost;
    private int restaurant_route;

    public void setRestaurant_route(int restaurant_route){
        this.restaurant_route=restaurant_route;
    }
    public int getRestaurant_route(){
        return restaurant_route;
    }

    public void setRestaurant_id(int restaurant_id){
        this.restaurant_id=restaurant_id;
    }
    public int getRestaurant_id(){
        return restaurant_id;
    }

    public void setArea_id(int area_id){
        this.area_id=area_id;
    }
    public int getArea_id() {
        return area_id;
    }

    public void setRestaurant_name(String restaurant_name){
        this.restaurant_name=restaurant_name;
    }
    public String getRestaurant_name(){
        return restaurant_name;
    }

    public void setRestaurant_introduce(String restaurant_introduce){
        this.restaurant_introduce=restaurant_introduce;
    }
    public String getRestaurant_introduce(){
        return restaurant_introduce;
    }

    public void setRestaurant_address(String restaurant_address){
        this.restaurant_address=restaurant_address;
    }
    public String getRestaurant_address(){
        return restaurant_address;
    }

    public void setSpecial_introduce(String special_introduce){
        this.special_introduce=special_introduce;
    }
    public String getSpecial_introduce(){
        return special_introduce;
    }

    public void setAverage_cost(float average_cost){
        this.average_cost=average_cost;
    }
    public float getAverage_cost(){
        return average_cost;
    }

    public void setRestaurant_area_name(String restaurant_area_name){
        this.restaurant_area_name=restaurant_area_name;
    }
    public String getRestaurant_area_name(){
        return restaurant_area_name;
    }
}
