package frame.zzt.com.appframe.mvvmbind.adapter;


/**
 * 列表类型
 */
public class ItemData {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;

    private int type;
    private String title;
    private String desc;

    public ItemData(int type, String title, String desc) {
        this.type = type;
        this.title = title;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
