package me.androider.playground.http.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Androider on 2019/7/18 16:24
 */
public class SyncOtherSouceWalkDataModel {

    private List<WalkDataModel> walk_data;

    public SyncOtherSouceWalkDataModel() {
        walk_data = new ArrayList<>();
    }

    public static class WalkDataModel {
        /**
         * deviceId : 17c4b12882fddc1e66521328b6801c957794c357c8cbc5013f46c5143150bbe4
         * walk_calories : 106741
         * walk_distance : 2599
         * walk_step : 3510
         * walk_date : 2019-07-12
         */

        private String deviceId;
        private String walk_calories;
        private String walk_distance;
        private String walk_step;
        private String walk_date;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getWalk_calories() {
            return walk_calories;
        }

        public void setWalk_calories(String walk_calories) {
            this.walk_calories = walk_calories;
        }

        public String getWalk_distance() {
            return walk_distance;
        }

        public void setWalk_distance(String walk_distance) {
            this.walk_distance = walk_distance;
        }

        public String getWalk_step() {
            return walk_step;
        }

        public void setWalk_step(String walk_step) {
            this.walk_step = walk_step;
        }

        public String getWalk_date() {
            return walk_date;
        }



        public void setWalk_date(String walk_date) {
            this.walk_date = walk_date;
        }

        public WalkDataModel addDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public WalkDataModel addWalk_calories(String walk_calories) {
            this.walk_calories = walk_calories;
            return this;
        }

        public WalkDataModel addWalk_distance(String walk_distance) {
            this.walk_distance = walk_distance;
            return this;
        }

        public WalkDataModel addWalk_step(String walk_step) {
            this.walk_step = walk_step;
            return this;
        }

        public WalkDataModel addWalk_date(String walk_date) {
            this.walk_date = walk_date;
            return this;
        }
    }

    public static class Builder{

        private SyncOtherSouceWalkDataModel syncOtherSouceWalkDataModel;

        public Builder() {
            syncOtherSouceWalkDataModel = new SyncOtherSouceWalkDataModel();
        }

        public Builder add(WalkDataModel model) {
            syncOtherSouceWalkDataModel.walk_data.add(model);
            return this;
        }

        public SyncOtherSouceWalkDataModel build() {
            return syncOtherSouceWalkDataModel;
        }
    }
}
