package me.androider.playground.http.helper;

/**
 * created by Androider on 2019/7/18 13:30
 */
public class YesNoModel {

    /**
     * answer : no
     * forced : false
     * image : https://yesno.wtf/assets/no/10-d5ddf3f82134e781c1175614c0d2bab2.gif
     */

    private String answer;
    private boolean forced;
    private String image;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
