package frame.zzt.com.appframe.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by allen on 18/8/31.
 */
@Entity
public class Location {
    @Id
    private Long id;
    private String lat;
    private String lng;
    private String time;

    @Generated(hash = 531690890)
    public Location(Long id, String lat, String lng, String time) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    @Generated(hash = 375979639)
    public Location() {
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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
