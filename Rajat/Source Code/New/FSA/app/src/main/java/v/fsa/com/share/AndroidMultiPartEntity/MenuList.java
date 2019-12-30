package v.fsa.com.share.AndroidMultiPartEntity;

/**
 * Created by cursor on 4/30/2018.
 */

public class MenuList {


    String title;


    public String getTitle_hr() {
        return title_hr;
    }

    public void setTitle_hr(String title_hr) {
        this.title_hr = title_hr;
    }

    String title_hr;

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int thumbnail;

    public MenuList(String title, int thumbnail,String title_hr) {
        this.title = title;
        this.thumbnail = thumbnail;

        this.title_hr=title_hr;
    }


}
