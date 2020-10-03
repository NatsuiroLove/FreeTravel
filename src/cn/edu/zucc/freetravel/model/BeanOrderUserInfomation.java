package cn.edu.zucc.freetravel.model;

/**
 * 订单用户身份数据封装 将显示在界面
 */
public class BeanOrderUserInfomation {
    public static final String[] tableTitles={"订单身份信息编号,订单编号,身份号码"};

    public String getItem(int col){
        return "";
    }

    private int  information_order_id;
    private int order_id;
    private String user_identifynum;
    private String user_realname;

    public void setInformation_order_id(int information_order_id){
        this.information_order_id=information_order_id;
    }
    public int getInformation_order_id(){
        return information_order_id;
    }

    public void setOrder_id(int order_id){
        this.order_id=order_id;
    }
    public int getOrder_id(){
        return order_id;
    }

    public void setUser_identifynum(String identifynum){
        this.user_identifynum=user_identifynum;
    }
    public String getUser_identifynum(){
        return user_identifynum;
    }

    public void setUser_realname(String user_realname){
        this.user_realname=user_realname;
    }
    public String getUser_realname(){
        return user_realname;
    }
}
