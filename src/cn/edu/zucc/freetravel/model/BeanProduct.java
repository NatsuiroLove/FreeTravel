package cn.edu.zucc.freetravel.model;

/**
 * ��Ʒ���ݷ�װ ����ʾ�ڽ���
 */
public class BeanProduct {
    public static final String[] tableTitles={"��Ʒ���","�������","��·����","������Чʱ��","�ۿ�"};

    public String getItems(int col){
        if(col==0)return String.valueOf(getProduct_id());
        else if(col==1)return String.valueOf(getOrder_id());
        else if(col==2)return String.valueOf(getRoute_id());
        else if(col==3)return getStart_time();
        else if(col==4)return String.valueOf(getDiscount());
        return "";
    }

    /*���ݷ�װ*/
    private int product_id;
    private int order_id;
    private int route_id;
    private String start_time ;
    private float discount;

    public void setProduct_id(int product_id){
        this.product_id=product_id;
    }
    public int getProduct_id(){
        return product_id;
    }

    public void setOrder_id(int order_id){
        this.order_id=order_id;
    }
    public int getOrder_id(){
        return order_id;
    }

    public void setRoute_id(int route_id){
        this.route_id=route_id;
    }
    public int getRoute_id(){
        return route_id;
    }

    public void setStart_time(String start_time){
        this.start_time=start_time;
    }
    public String getStart_time(){
        return start_time;
    }

    public void setDiscount(float discount){
        this.discount=discount;
    }
    public float getDiscount(){
        return discount;
    }

}
