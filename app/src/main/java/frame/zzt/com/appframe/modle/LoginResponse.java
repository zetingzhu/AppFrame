package frame.zzt.com.appframe.modle;

/**
 * Created by allen on 17/11/17.
 * 登录
 */

public class LoginResponse extends BaseModel {


    /**
     * data : {"token":"sfdfsdf","user_id":"22","is_first_login":"1"}
     */

    public static LoginResponse response;

    public static synchronized LoginResponse getInstance() {
        return response != null ? response : (response = new LoginResponse());
    }


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : sfdfsdf
         * user_id : 22
         * is_first_login : 1
         */

        private String token;
        private String user_id;
        private String is_first_login;
        private String phone;
        private String udid;
        private int is_super_manager; // 是否是超级管理员 0 ＝ 不是  1 ＝ 是

        //  设备检测数据
        private String g1;
        private String s1;
        private String show_dianmen; // 1 =  可执行远程电门 蓝牙电门  0 ＝ 不可执行

        public String getShow_dianmen() {
            return show_dianmen;
        }

        public int getIs_super_manager() {
            return is_super_manager;
        }

        public void setIs_super_manager(int is_super_manager) {
            this.is_super_manager = is_super_manager;
        }

        public void setShow_dianmen(String show_dianmen) {
            this.show_dianmen = show_dianmen;
        }

        public String getG1() {
            return g1;
        }

        public void setG1(String g1) {
            this.g1 = g1;
        }

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getIs_first_login() {
            return is_first_login;
        }

        public void setIs_first_login(String is_first_login) {
            this.is_first_login = is_first_login;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "g1='" + g1 + '\'' +
                    ", token='" + token + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", is_first_login='" + is_first_login + '\'' +
                    ", phone='" + phone + '\'' +
                    ", udid='" + udid + '\'' +
                    ", is_super_manager=" + is_super_manager +
                    ", s1='" + s1 + '\'' +
                    ", show_dianmen='" + show_dianmen + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "data=" + data +
                "} " + super.toString();
    }
}
