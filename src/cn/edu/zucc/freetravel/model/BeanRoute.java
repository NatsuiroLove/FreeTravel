package cn.edu.zucc.freetravel.model;

/**
 * 线路数据封装 将显示在界面
 */
public class BeanRoute {
    public static final String[] tableTitles={"线路编号","所属区域编号","线路名称","线路介绍","线路价格","目的地","线路天数","特色推荐","交通提示"};

    /*获取表格内表项内容*/
    public String getItems(int col){
        if(col==0)return String.valueOf(getRoute_id());
        else if(col==1)return String.valueOf(getArea_id());
        else if(col==2)return getRoute_name();
        else if(col==3)return getRoute_introduce();
        else if(col==4)return String.valueOf(getRoute_cost());
        else if(col==5)return getDestination();
        else if(col==6)return String.valueOf(getDayspend());
        else if(col==7)return getSpecial_recommand();
        else if(col==8)return getTraffic_tips();
//        else if(col==9)return String.valueOf(getRecommandplay_id());
        return "";//待补充
    }

    /*数据封装*/
    private int route_id;
    private int product_id;
    private int area_id;
    private String route_name;
    private String route_introduce;
    private float route_cost;
    private String destination;
    private int dayspend;
    private String special_recommand;
    private String traffic_tips;
    private int recommandplay_id;

    public void setRoute_id(int route_id){
        this.route_id=route_id;
    }
    public int getRoute_id(){
        return route_id;
    }

    public void setProduct_id(int product_id){
        this.product_id=product_id;
    }
    public int getProduct_id(){
        return product_id;
    }

    public void setArea_id(int area_id){
        this.area_id=area_id;
    }
    public int getArea_id(){
        return area_id;
    }

    public void setRoute_name(String route_name){
        this.route_name=route_name;
    }
    public String getRoute_name(){
        return route_name;
    }

    public void setRoute_introduce(String route_introduce){
        this.route_introduce=route_introduce;
    }
    public String getRoute_introduce(){
        return route_introduce;
    }

    public void setRoute_cost(float route_cost){
        this.route_cost=route_cost;
    }
    public float getRoute_cost(){
        return route_cost;
    }

    public void setDestination(String destination){
        this.destination=destination;
    }
    public String getDestination(){
        return destination;
    }

    public void setDayspend(int dayspend){
        this.dayspend=dayspend;
    }
    public int getDayspend(){
        return dayspend;
    }

    public void setSpecial_recommand(String special_recommand){
        this.special_recommand=special_recommand;
    }
    public String getSpecial_recommand(){
        return special_recommand;
    }

    public void setTraffic_tips(String traffic_tips){
        this.traffic_tips=traffic_tips;
    }
    public String getTraffic_tips(){
        return traffic_tips;
    }

    public void setRecommandplay_id(int recommandplay_id){
        this.recommandplay_id=recommandplay_id;
    }
    public int getRecommandplay_id(){
        return recommandplay_id;
    }

}
