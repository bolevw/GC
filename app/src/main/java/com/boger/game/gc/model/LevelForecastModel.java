package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 2016/6/2.
 */
public class LevelForecastModel extends BaseModel {
    private String message;
    private String name;
    private String duanwei;
    private String rank;
    private String rank6;
    private Matchstr matchstr;
    private Matchstr1 matchstr1;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuanwei() {
        return duanwei;
    }

    public void setDuanwei(String duanwei) {
        this.duanwei = duanwei;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank6() {
        return rank6;
    }

    public void setRank6(String rank6) {
        this.rank6 = rank6;
    }

    public Matchstr getMatchstr() {
        return matchstr;
    }

    public void setMatchstr(Matchstr matchstr) {
        this.matchstr = matchstr;
    }

    public Matchstr1 getMatchstr1() {
        return matchstr1;
    }

    public void setMatchstr1(Matchstr1 matchstr1) {
        this.matchstr1 = matchstr1;
    }

    protected class Matchstr {
        @SerializedName("1")
        @Expose
        private String tag1;
        @SerializedName("2")
        @Expose
        private String tag2;
        @SerializedName("3")
        @Expose
        private String tag3;
        @SerializedName("4")
        @Expose
        private String tag4;
        @SerializedName("5")
        @Expose
        private String tag5;
        @SerializedName("6")
        @Expose
        private String tag6;
        @SerializedName("7")
        @Expose
        private String tag7;
        @SerializedName("8")
        @Expose
        private String tag8;
        @SerializedName("9")
        @Expose
        private String tag9;
        @SerializedName("10")
        @Expose
        private String tag10;

        public String getTag1() {
            return tag1;
        }

        public void setTag1(String tag1) {
            this.tag1 = tag1;
        }

        public String getTag2() {
            return tag2;
        }

        public void setTag2(String tag2) {
            this.tag2 = tag2;
        }

        public String getTag3() {
            return tag3;
        }

        public void setTag3(String tag3) {
            this.tag3 = tag3;
        }

        public String getTag4() {
            return tag4;
        }

        public void setTag4(String tag4) {
            this.tag4 = tag4;
        }

        public String getTag5() {
            return tag5;
        }

        public void setTag5(String tag5) {
            this.tag5 = tag5;
        }

        public String getTag6() {
            return tag6;
        }

        public void setTag6(String tag6) {
            this.tag6 = tag6;
        }

        public String getTag7() {
            return tag7;
        }

        public void setTag7(String tag7) {
            this.tag7 = tag7;
        }

        public String getTag8() {
            return tag8;
        }

        public void setTag8(String tag8) {
            this.tag8 = tag8;
        }

        public String getTag9() {
            return tag9;
        }

        public void setTag9(String tag9) {
            this.tag9 = tag9;
        }

        public String getTag10() {
            return tag10;
        }

        public void setTag10(String tag10) {
            this.tag10 = tag10;
        }
    }

    protected class Matchstr1 {
        @SerializedName("1")
        @Expose
        private String tag1;
        @SerializedName("2")
        @Expose
        private String tag2;
        @SerializedName("3")
        @Expose
        private String tag3;
        @SerializedName("4")
        @Expose
        private String tag4;
        @SerializedName("5")
        @Expose
        private String tag5;
        @SerializedName("6")
        @Expose
        private String tag6;
        @SerializedName("7")
        @Expose
        private String tag7;
        @SerializedName("8")
        @Expose
        private String tag8;
        @SerializedName("9")
        @Expose
        private String tag9;
        @SerializedName("10")
        @Expose
        private String tag10;

        public String getTag1() {
            return tag1;
        }

        public void setTag1(String tag1) {
            this.tag1 = tag1;
        }

        public String getTag2() {
            return tag2;
        }

        public void setTag2(String tag2) {
            this.tag2 = tag2;
        }

        public String getTag3() {
            return tag3;
        }

        public void setTag3(String tag3) {
            this.tag3 = tag3;
        }

        public String getTag4() {
            return tag4;
        }

        public void setTag4(String tag4) {
            this.tag4 = tag4;
        }

        public String getTag5() {
            return tag5;
        }

        public void setTag5(String tag5) {
            this.tag5 = tag5;
        }

        public String getTag6() {
            return tag6;
        }

        public void setTag6(String tag6) {
            this.tag6 = tag6;
        }

        public String getTag7() {
            return tag7;
        }

        public void setTag7(String tag7) {
            this.tag7 = tag7;
        }

        public String getTag8() {
            return tag8;
        }

        public void setTag8(String tag8) {
            this.tag8 = tag8;
        }

        public String getTag9() {
            return tag9;
        }

        public void setTag9(String tag9) {
            this.tag9 = tag9;
        }

        public String getTag10() {
            return tag10;
        }

        public void setTag10(String tag10) {
            this.tag10 = tag10;
        }
    }
}
