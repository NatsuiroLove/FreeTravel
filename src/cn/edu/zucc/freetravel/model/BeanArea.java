package cn.edu.zucc.freetravel.model;

/**
 *�������ݷ�װ ����ʾ�ڽ���
 */
public class BeanArea {
    public static final String[] tableTitles={"������","��������","����������","������������"};

    /*��ȡ����ڱ�������*/
    public String getItems(int col){
        if(col==0)return String.valueOf(getArea_id());
        else if(col==1)return getArea_name();
        else if(col==2)return String.valueOf(getArea_belong_id());
        else if(col==3)return getArea_belong_name();
        else return "";
    }

    /*���ݷ�װ*/
    public static BeanArea currentArea=null;

    private int area_id;
    private String area_name ;
    private int area_belong_id;
    private String area_belong_name;

    public void setArea_id(int area_id){
        this.area_id=area_id;
    }
    public int getArea_id(){
        return area_id;
    }

    public void setArea_name(String area_name){
        this.area_name=area_name;
    }
    public String getArea_name(){
        return area_name;
    }

    public void setArea_belong_id(int area_belong_id){
        this.area_belong_id=area_belong_id;
    }
    public int getArea_belong_id(){
        return area_belong_id;
    }

    public void setArea_belong_name(String area_belong_name){
        this.area_belong_name=area_belong_name;
    }
    public String getArea_belong_name(){
        return area_belong_name;
    }

}
