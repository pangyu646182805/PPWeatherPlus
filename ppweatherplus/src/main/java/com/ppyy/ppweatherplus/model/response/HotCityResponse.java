package com.ppyy.ppweatherplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class HotCityResponse {
    /**
     * data : {"hotInternational":[{"name":"首尔","cityId":"RKSS_04"},{"name":"新加坡","cityId":"WSAP_03"},{"name":"曼谷","cityId":"VT14_03"},{"name":"纽约","cityId":"NYC_01"},{"name":"迪拜","cityId":"OMDB_01"},{"name":"大阪府","cityId":"JP07_01"},{"name":"悉尼","cityId":"ASSY_01"},{"name":"墨尔本","cityId":"AMML_01"},{"name":"洛杉矶","cityId":"59L_01"},{"name":"吉隆坡","cityId":"WMKK_01"},{"name":"巴黎","cityId":"LFR8_01"},{"name":"伦敦","cityId":"EGLL_01"}],"hotNational":[{"cityLevelName":"北京市","country":"中国","upper":"","name":"北京市","provEn":"beijing","cityId":"101010100","cityLevelId":"101010100","type":0,"prov":"北京"},{"cityLevelName":"上海市","country":"中国","upper":"","name":"上海市","provEn":"shanghai","cityId":"101020100","cityLevelId":"101020100","type":0,"prov":"上海"},{"cityLevelName":"广州市","country":"中国","upper":"广东","name":"广州市","provEn":"guangdong","cityId":"101280101","cityLevelId":"101280101","type":0,"prov":"广东"},{"cityLevelName":"深圳市","country":"中国","upper":"广东","name":"深圳市","provEn":"guangdong","cityId":"101280601","cityLevelId":"101280601","type":0,"prov":"广东"},{"cityLevelName":"重庆市","country":"中国","upper":"","name":"重庆市","provEn":"zhongqing","cityId":"101040100","cityLevelId":"101040100","type":0,"prov":"重庆"},{"cityLevelName":"成都市","country":"中国","upper":"四川","name":"成都市","provEn":"sichuan","cityId":"101270101","cityLevelId":"101270101","type":0,"prov":"四川"},{"cityLevelName":"武汉市","country":"中国","upper":"湖北","name":"武汉市","provEn":"hubei","cityId":"101200101","cityLevelId":"101200101","type":0,"prov":"湖北"},{"cityLevelName":"南京市","country":"中国","upper":"江苏","name":"南京市","provEn":"jiangsu","cityId":"101190101","cityLevelId":"101190101","type":0,"prov":"江苏"},{"cityLevelName":"苏州市","country":"中国","upper":"江苏","name":"苏州市","provEn":"jiangsu","cityId":"101190401","cityLevelId":"101190401","type":0,"prov":"江苏"},{"cityLevelName":"杭州市","country":"中国","upper":"浙江","name":"杭州市","provEn":"zhejiang","cityId":"101210101","cityLevelId":"101210101","type":0,"prov":"浙江"},{"cityLevelName":"三亚市","country":"中国","upper":"海南","name":"三亚市","provEn":"hainan","cityId":"101310201","cityLevelId":"101310201","type":0,"prov":"海南"},{"cityLevelName":"厦门市","country":"中国","upper":"福建","name":"厦门市","provEn":"fujian","cityId":"101230201","cityLevelId":"101230201","type":0,"prov":"福建"},{"cityLevelName":"天津市","country":"中国","upper":"","name":"天津市","provEn":"tianjin","cityId":"101030100","cityLevelId":"101030100","type":0,"prov":"天津"},{"cityLevelName":"沈阳市","country":"中国","upper":"辽宁","name":"沈阳市","provEn":"liaoning","cityId":"101070101","cityLevelId":"101070101","type":0,"prov":"辽宁"},{"cityLevelName":"郑州市","country":"中国","upper":"河南","name":"郑州市","provEn":"henan","cityId":"101180101","cityLevelId":"101180101","type":0,"prov":"河南"},{"cityLevelName":"济南市","country":"中国","upper":"山东","name":"济南市","provEn":"shandong","cityId":"101120101","cityLevelId":"101120101","type":0,"prov":"山东"},{"cityLevelName":"兰州市","country":"中国","upper":"甘肃","name":"兰州市","provEn":"gansu","cityId":"101160101","cityLevelId":"101160101","type":0,"prov":"甘肃"},{"cityLevelName":"西安市","country":"中国","upper":"陕西","name":"西安市","provEn":"shanxi","cityId":"101110101","cityLevelId":"101110101","type":0,"prov":"陕西"},{"cityLevelName":"贵阳市","country":"中国","upper":"贵州","name":"贵阳市","provEn":"guizhou","cityId":"101260101","cityLevelId":"101260101","type":0,"prov":"贵州"},{"cityLevelName":"南宁市","country":"中国","upper":"广西","name":"南宁市","provEn":"guangxi","cityId":"101300101","cityLevelId":"101300101","type":0,"prov":"广西"},{"cityLevelName":"福州市","country":"中国","upper":"福建","name":"福州市","provEn":"fujian","cityId":"101230101","cityLevelId":"101230101","type":0,"prov":"福建"},{"cityLevelName":"南昌市","country":"中国","upper":"江西","name":"南昌市","provEn":"jiangxi","cityId":"101240101","cityLevelId":"101240101","type":0,"prov":"江西"},{"cityLevelName":"合肥市","country":"中国","upper":"安徽","name":"合肥市","provEn":"anhui","cityId":"101220101","cityLevelId":"101220101","type":0,"prov":"安徽"},{"cityLevelName":"长沙市","country":"中国","upper":"湖南","name":"长沙市","provEn":"hunan","cityId":"101250101","cityLevelId":"101250101","type":0,"prov":"湖南"},{"cityLevelName":"香港","country":"中国","upper":"","name":"香港","provEn":"xianggang","cityId":"101320101","cityLevelId":"101320101","type":0,"prov":"香港"},{"cityLevelName":"澳门","country":"中国","upper":"","name":"澳门","provEn":"aomen","cityId":"101330101","cityLevelId":"101330101","type":0,"prov":"澳门"},{"cityLevelName":"台北","country":"中国","upper":"台湾","name":"台北","provEn":"taiwan","cityId":"101340101","cityLevelId":"101340101","type":0,"prov":"台湾"},{"cityLevelName":"高雄","country":"中国","upper":"台湾","name":"高雄","provEn":"taiwan","cityId":"101340201","cityLevelId":"101340201","type":0,"prov":"台湾"}]}
     * status : 1000
     * desc : OK
     */
    private DataBean data;
    private int status;
    private String desc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class DataBean {
        @SerializedName("hot_international")
        private List<HotInternationalBean> hotInternational;
        @SerializedName("hot_national")
        private List<HotNationalBean> hotNational;

        public List<HotInternationalBean> getHotInternational() {
            return hotInternational;
        }

        public void setHotInternational(List<HotInternationalBean> hotInternational) {
            this.hotInternational = hotInternational;
        }

        public List<HotNationalBean> getHotNational() {
            return hotNational;
        }

        public void setHotNational(List<HotNationalBean> hotNational) {
            this.hotNational = hotNational;
        }

        public static class HotInternationalBean {
            /**
             * name : 首尔
             * cityId : RKSS_04
             */
            private String name;
            @SerializedName("cityid")
            private String cityId;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }
        }

        public static class HotNationalBean {
            /**
             * cityLevelName : 北京市
             * country : 中国
             * upper :
             * name : 北京市
             * provEn : beijing
             * cityId : 101010100
             * cityLevelId : 101010100
             * type : 0
             * prov : 北京
             */
            @SerializedName("city_level_name")
            private String cityLevelName;
            private String country;
            private String upper;
            private String name;
            @SerializedName("prov_en")
            private String provEn;
            @SerializedName("cityid")
            private String cityId;
            @SerializedName("city_level_id")
            private String cityLevelId;
            private int type;
            private String prov;

            public String getCityLevelName() {
                return cityLevelName;
            }

            public void setCityLevelName(String cityLevelName) {
                this.cityLevelName = cityLevelName;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getUpper() {
                return upper;
            }

            public void setUpper(String upper) {
                this.upper = upper;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProvEn() {
                return provEn;
            }

            public void setProvEn(String provEn) {
                this.provEn = provEn;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getCityLevelId() {
                return cityLevelId;
            }

            public void setCityLevelId(String cityLevelId) {
                this.cityLevelId = cityLevelId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getProv() {
                return prov;
            }

            public void setProv(String prov) {
                this.prov = prov;
            }
        }
    }
}
