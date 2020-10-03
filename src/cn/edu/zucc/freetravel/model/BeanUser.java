package cn.edu.zucc.freetravel.model;

/**
 * 用户个人信息数据封装 将显示在界面
 */
public class BeanUser {
    public static final String[] tableTitles={"用户编号","用户名","用户实名","性别","密码","绑定手机号","绑定邮箱","居住城市","注册时间","是否vip","vip过期时间"};

    /*获取表格内表项内容*/
    public String getItems(int col){
        if(col==0)return String.valueOf(getUser_id());
        else if(col==1)return getUser_name();
        else if(col==2)return getUser_realname();
        else if(col==3)return getUser_sex()==1?"男":"女";
        else if(col==4)return getUser_pwd();
        else if(col==5)return getUser_phonenum();
        else if(col==6)return getUser_mail();
        else if(col==7)return getReg_time();
        else if(col==8)return getUser_livecity();
        else if(col==9)return String.valueOf(getIsvip());
        else if(col==10)return getVip_end_time();
        return "";//待补充
    }

    public static BeanUser currentLoginUser=null;

    /*数据封装*/
    private int user_id;
    private String user_name;
    private String user_realname;
    private int user_sex;
    private String user_pwd;
    private String user_phonenum;
    private String user_mail;
    private String user_livecity;
    private String reg_time;
    private boolean isvip;
    private String vip_end_time;

    public void setUser_id(int user_id){
        this.user_id=user_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_name(String user_name){
        this.user_name=user_name;
    }
    public String getUser_name(){
        return user_name;
    }

    public void setUser_realname(String user_realname){
        this.user_realname=user_realname;
    }
    public String getUser_realname(){
        return user_realname;
    }

    public void setUser_sex(int user_sex){
        this.user_sex=user_sex;
    }
    public int getUser_sex(){
        return user_sex;
    }

    public void setUser_pwd(String user_pwd){
        this.user_pwd=user_pwd;
    }
    public String getUser_pwd(){
        return user_pwd;
    }

    public void setUser_phonenum(String user_phonenum){
        this.user_phonenum=user_phonenum;
    }
    public String getUser_phonenum(){
        return user_phonenum;
    }

    public void setUser_mail(String user_mail){
        this.user_mail=user_mail;
    }
    public String getUser_mail(){
        return user_mail;
    }

    public void setUser_livecity(String user_livecity){
        this.user_livecity=user_livecity;
    }
    public String getUser_livecity(){
        return user_livecity;
    }

    public void setReg_time(String reg_time){
        this.reg_time=reg_time;
    }
    public String getReg_time(){
        return reg_time;
    }

    public void setVip_end_time(String vip_end_time){
        this.vip_end_time=vip_end_time;
    }
    public String getVip_end_time(){
        return vip_end_time;
    }

    public void setIsvip(boolean isvip){
        this.isvip= isvip;
    }
    public boolean getIsvip(){
        return isvip;
    }
}
