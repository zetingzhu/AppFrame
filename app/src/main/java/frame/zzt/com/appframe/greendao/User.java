package frame.zzt.com.appframe.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by allen on 18/8/7.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id ;
    private String email ;
    private String password ;
    private String phone ;
    private String time ;
    @Generated(hash = 1882742242)
    public User(Long id, String email, String password, String phone, String time) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.time = time;
    }
    @Generated(hash = 586692638)
    public User() {
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }

}
