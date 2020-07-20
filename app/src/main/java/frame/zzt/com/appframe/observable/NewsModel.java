package frame.zzt.com.appframe.observable;

/**
 * Created by allen on 18/10/16.
 */

public class NewsModel {
    private String title ;
    private String content ;

    public NewsModel(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
