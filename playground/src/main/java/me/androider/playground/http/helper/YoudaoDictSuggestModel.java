package me.androider.playground.http.helper;

import java.util.List;

/**
 * created by Androider on 2019/7/18 13:36
 */
public class YoudaoDictSuggestModel {

    /**
     * result : {"msg":"success","code":200}
     * data : {"entries":[{"explain":"n. 苹果; 家伙; n. (Apple)人名; (英)阿普尔","entry":"apple"},{"explain":"n. [园艺] 苹果（复数）; 井架小零件","entry":"apples"},{"explain":"n. 支程序，小应用程序; 小型程式; 程序类型","entry":"applet"},{"explain":"n. 阿普比（英国英格兰西北部小镇）","entry":"appleby"},{"explain":"n. 程序; 小程序; 小应用程序（applet的复数）","entry":"applets"},{"explain":"n. 苹果酱，苹果沙司; 胡说","entry":"applesauce"},{"explain":"n. 苹果核战记（动画电影名）; n. (Appleseed)人名; (英)阿普尔西德","entry":"appleseed"},{"explain":"[食品] 苹果馅饼; 苹果蛋糕","entry":"apple tart"},{"explain":"苹果派; 苹果馅饼","entry":"apple pie"},{"explain":"苹果树","entry":"apple tree"},{"explain":"苹果电脑; 苹果电脑公司","entry":"apple computer"},{"explain":"苹果汁","entry":"apple juice"},{"explain":"n. 苹果绿; 苹果绿的","entry":"apple green"},{"explain":"苹果花","entry":"apple blossom"},{"explain":"苹果酒，苹果汁; 苹果醋","entry":"apple cider"},{"explain":"苹果核","entry":"apple core"},{"explain":"苹果商店","entry":"apple store"},{"explain":"苹果醋","entry":"apple vinegar"},{"explain":"苹果皮","entry":"apple skin"},{"explain":"拍马屁的人","entry":"apple polisher"},{"explain":"拍马屁","entry":"apple polish"}],"query":"apple","language":"eng","type":"dict"}
     */

    private ResultBean result;
    private DataBean data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * msg : success
         * code : 200
         */

        private String msg;
        private int code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class DataBean {
        /**
         * entries : [{"explain":"n. 苹果; 家伙; n. (Apple)人名; (英)阿普尔","entry":"apple"},{"explain":"n. [园艺] 苹果（复数）; 井架小零件","entry":"apples"},{"explain":"n. 支程序，小应用程序; 小型程式; 程序类型","entry":"applet"},{"explain":"n. 阿普比（英国英格兰西北部小镇）","entry":"appleby"},{"explain":"n. 程序; 小程序; 小应用程序（applet的复数）","entry":"applets"},{"explain":"n. 苹果酱，苹果沙司; 胡说","entry":"applesauce"},{"explain":"n. 苹果核战记（动画电影名）; n. (Appleseed)人名; (英)阿普尔西德","entry":"appleseed"},{"explain":"[食品] 苹果馅饼; 苹果蛋糕","entry":"apple tart"},{"explain":"苹果派; 苹果馅饼","entry":"apple pie"},{"explain":"苹果树","entry":"apple tree"},{"explain":"苹果电脑; 苹果电脑公司","entry":"apple computer"},{"explain":"苹果汁","entry":"apple juice"},{"explain":"n. 苹果绿; 苹果绿的","entry":"apple green"},{"explain":"苹果花","entry":"apple blossom"},{"explain":"苹果酒，苹果汁; 苹果醋","entry":"apple cider"},{"explain":"苹果核","entry":"apple core"},{"explain":"苹果商店","entry":"apple store"},{"explain":"苹果醋","entry":"apple vinegar"},{"explain":"苹果皮","entry":"apple skin"},{"explain":"拍马屁的人","entry":"apple polisher"},{"explain":"拍马屁","entry":"apple polish"}]
         * query : apple
         * language : eng
         * type : dict
         */

        private String query;
        private String language;
        private String type;
        private List<EntriesBean> entries;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<EntriesBean> getEntries() {
            return entries;
        }

        public void setEntries(List<EntriesBean> entries) {
            this.entries = entries;
        }

        public static class EntriesBean {
            /**
             * explain : n. 苹果; 家伙; n. (Apple)人名; (英)阿普尔
             * entry : apple
             */

            private String explain;
            private String entry;

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public String getEntry() {
                return entry;
            }

            public void setEntry(String entry) {
                this.entry = entry;
            }
        }
    }
}
