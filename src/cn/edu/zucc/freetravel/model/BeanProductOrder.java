package cn.edu.zucc.freetravel.model;

/**
 * 用户产品订单信息数据封装 将显示在界面
 */
public class BeanProductOrder {
    public static BeanProductOrder currentUserOrder = null;

    public static final String[] tableTitles={"订单编号","用户编号","组团人数","启程城市","实际价格","支付时间","订单状态","住宿评分","推荐玩法评分","附属产品评分","评价内容"};

    public String getItems(int col){
        if(col==0)return String.valueOf(getOrder_id());
        else if(col==1)return String.valueOf(getUser_id());
        else if(col==2)return String.valueOf(getOrder_group_num());
        else if(col==3)return getStart_city();
        else if(col==4)return String.valueOf(getReal_pay());
        else if(col==5)return getPay_time();
        else if(col==6)return String.valueOf(getOrder_state());
        else if(col==7)return String.valueOf(getAccommodation_grade());
        else if(col==8)return String.valueOf(getRecommandPlay_grade());
        else if(col==9)return String.valueOf(getBelongProduct_grade());
        else if(col==10)return getEvaluate();
        return "";
    }

    /*数据封装*/
    private int order_id;
    private int user_id;
    private int order_group_num;
    private String start_city;
    private float real_pay;
    private String pay_time;
    private int order_state;
    private int accommodation_grade;
    private int recommandPlay_grade;
    private int belongProduct_grade;
    private String evaluate;

    public void setOrder_id(int order_id){
        this.order_id=order_id;
    }
    public int getOrder_id(){
        return order_id;
    }

    public void setUser_id(int user_id){
        this.user_id=user_id;
    }
    public int getUser_id(){
        return user_id;
    }

    public void setOrder_group_num(int order_group_num){
        this.order_group_num=order_group_num;
    }
    public int getOrder_group_num(){
        return order_group_num;
    }

    public void setStart_city(String start_city){
        this.start_city=start_city;
    }
    public String getStart_city(){
        return start_city;
    }

    public void setReal_pay(float real_pay){
        this.real_pay=real_pay;
    }
    public float getReal_pay(){
        return real_pay;
    }

    public void setPay_time(String pay_time){
        this.pay_time=pay_time;
    }
    public String getPay_time(){
        return pay_time;
    }

    public void setOrder_state(int order_state){
        this.order_state=order_state;
    }
    public String getOrder_state(){
        switch(order_state){
            case 0:return "空订单";
            case 1:return "用户已预定";
            case 2:return "用户已付款";
            case 3:return "订单已完成";
            case 4:return "订单已取消";
        }
        return "";
    }

    public void setAccommodation_grade(int accommodation_grade){
        this.accommodation_grade=accommodation_grade;
    }
    public int getAccommodation_grade(){
        return accommodation_grade;
    }

    public void setRecommandPlay_grade(int recommandPlay_grade){
        this.recommandPlay_grade=recommandPlay_grade;
    }
    public int getRecommandPlay_grade(){
        return recommandPlay_grade;
    }

    public void setBelongProduct_grade(int belongProduct_grade){
        this.belongProduct_grade=belongProduct_grade;
    }
    public int getBelongProduct_grade(){
        return belongProduct_grade;
    }

    public void setEvaluate(String evaluate){
        this.evaluate=evaluate;
    }
    public String getEvaluate(){
        return evaluate;
    }

}
