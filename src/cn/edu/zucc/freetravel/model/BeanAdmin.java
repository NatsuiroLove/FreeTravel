package cn.edu.zucc.freetravel.model;

/**
 * ����Ա���ݷ�װ ����ʾ�ڽ���
 */
public class BeanAdmin {
    /*���ݷ�װ*/
    public static BeanAdmin currentLoginAdmin = null;

    private int admin_id;
    private String admin_name;
    private String admin_pwd;

    public void setAdmin_id(int admin_id){
        this.admin_id=admin_id;
    }
    public int getAdmin_id(){
        return admin_id;
    }

    public void setAdmin_name(String admin_name){
        this.admin_name=admin_name;
    }
    public String getAdmin_name(){
        return admin_name;
    }
    public void setAdmin_pwd(String admin_pwd){
        this.admin_pwd=admin_pwd;
    }
    public String getAdmin_pwd(){
        return admin_pwd;
    }
}
