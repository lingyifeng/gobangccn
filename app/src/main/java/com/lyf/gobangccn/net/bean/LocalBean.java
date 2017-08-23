package com.lyf.gobangccn.net.bean;

import java.util.List;

/**
 * Created by 01F on 2017/8/22.
 */

public class LocalBean {
    /**
     * info : {"type":46,"error":0,"time":0,"message":""}
     * detail : {"poi_count":2,"poilist":[{"addr":"甘肃省平凉市静宁县","addr_info":{"adcode":"620826","c":"平凉市","d":"静宁县","p":"甘肃省","short_addr":""},"base_map_info":{"base_map_flag":"1"},"catacode":"261400","catalog":"地名地址:行政地名","direction":"北","dist":"621.6","dtype":"PLACE","final_score":"37.4","name":"张家岔","pointx":"105.690208","pointy":"35.389881","tags":[],"uid":"15605799364291814309","weight":"500.0"},{"addr":"甘肃省平凉市静宁县","addr_info":{"adcode":"620826","c":"平凉市","d":"静宁县","p":"甘肃省","short_addr":""},"base_map_info":{"base_map_flag":"1"},"catacode":"261400","catalog":"地名地址:行政地名","direction":"西北","dist":"893.8","dtype":"PLACE","final_score":"31.9","name":"张岔村","pointx":"105.697128","pointy":"35.388119","tags":[],"uid":"2706743123437390142","weight":"500.0"}],"request_id":"285363519841370184","results":[{"adcode":"620826","c":"平凉市","c_cht":"平涼市","c_en":"Pingliang","city_code":"156620800","d":"静宁县","dtype":"AD","n":"中国","n_cht":"中國","n_en":"China","name":"中国,甘肃省,平凉市,静宁县","nation_code":"156","p":"甘肃省","p_cht":"甘肅省","p_en":"GANSU","pointx":"105.692413","pointy":"35.395168","stat":0},{"address_children_scene":"Village","address_name":"静宁县甘沟镇张家岔","address_scene":"BaseStrategy","dtype":"FORMAT_ADDRESS","rough_address_name":"静宁县甘沟镇张家岔"},{"desc_weight":"500.0","direction":"内","dist":"0.0","dtype":"TOWN","name":"甘沟镇","pointx":"105.692413","pointy":"35.395168","uid":"620826212"},{"direction":"北","dist":"621.6","dtype":"PLACE","name":"张家岔","pointx":"105.690208","pointy":"35.389881","uid":"15605799364291814309"}]}
     */

    private InfoBean info;
    private DetailBean detail;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public static class InfoBean {
        /**
         * type : 46
         * error : 0
         * time : 0
         * message :
         */

        private int type;
        private int error;
        private int time;
        private String message;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DetailBean {
        /**
         * poi_count : 2
         * poilist : [{"addr":"甘肃省平凉市静宁县","addr_info":{"adcode":"620826","c":"平凉市","d":"静宁县","p":"甘肃省","short_addr":""},"base_map_info":{"base_map_flag":"1"},"catacode":"261400","catalog":"地名地址:行政地名","direction":"北","dist":"621.6","dtype":"PLACE","final_score":"37.4","name":"张家岔","pointx":"105.690208","pointy":"35.389881","tags":[],"uid":"15605799364291814309","weight":"500.0"},{"addr":"甘肃省平凉市静宁县","addr_info":{"adcode":"620826","c":"平凉市","d":"静宁县","p":"甘肃省","short_addr":""},"base_map_info":{"base_map_flag":"1"},"catacode":"261400","catalog":"地名地址:行政地名","direction":"西北","dist":"893.8","dtype":"PLACE","final_score":"31.9","name":"张岔村","pointx":"105.697128","pointy":"35.388119","tags":[],"uid":"2706743123437390142","weight":"500.0"}]
         * request_id : 285363519841370184
         * results : [{"adcode":"620826","c":"平凉市","c_cht":"平涼市","c_en":"Pingliang","city_code":"156620800","d":"静宁县","dtype":"AD","n":"中国","n_cht":"中國","n_en":"China","name":"中国,甘肃省,平凉市,静宁县","nation_code":"156","p":"甘肃省","p_cht":"甘肅省","p_en":"GANSU","pointx":"105.692413","pointy":"35.395168","stat":0},{"address_children_scene":"Village","address_name":"静宁县甘沟镇张家岔","address_scene":"BaseStrategy","dtype":"FORMAT_ADDRESS","rough_address_name":"静宁县甘沟镇张家岔"},{"desc_weight":"500.0","direction":"内","dist":"0.0","dtype":"TOWN","name":"甘沟镇","pointx":"105.692413","pointy":"35.395168","uid":"620826212"},{"direction":"北","dist":"621.6","dtype":"PLACE","name":"张家岔","pointx":"105.690208","pointy":"35.389881","uid":"15605799364291814309"}]
         */

        private int poi_count;
        private String request_id;
        private List<PoilistBean> poilist;
        private List<ResultsBean> results;

        public int getPoi_count() {
            return poi_count;
        }

        public void setPoi_count(int poi_count) {
            this.poi_count = poi_count;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public List<PoilistBean> getPoilist() {
            return poilist;
        }

        public void setPoilist(List<PoilistBean> poilist) {
            this.poilist = poilist;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class PoilistBean {
            /**
             * addr : 甘肃省平凉市静宁县
             * addr_info : {"adcode":"620826","c":"平凉市","d":"静宁县","p":"甘肃省","short_addr":""}
             * base_map_info : {"base_map_flag":"1"}
             * catacode : 261400
             * catalog : 地名地址:行政地名
             * direction : 北
             * dist : 621.6
             * dtype : PLACE
             * final_score : 37.4
             * name : 张家岔
             * pointx : 105.690208
             * pointy : 35.389881
             * tags : []
             * uid : 15605799364291814309
             * weight : 500.0
             */

            private String addr;
            private AddrInfoBean addr_info;
            private BaseMapInfoBean base_map_info;
            private String catacode;
            private String catalog;
            private String direction;
            private String dist;
            private String dtype;
            private String final_score;
            private String name;
            private String pointx;
            private String pointy;
            private String uid;
            private String weight;
            private List<?> tags;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public AddrInfoBean getAddr_info() {
                return addr_info;
            }

            public void setAddr_info(AddrInfoBean addr_info) {
                this.addr_info = addr_info;
            }

            public BaseMapInfoBean getBase_map_info() {
                return base_map_info;
            }

            public void setBase_map_info(BaseMapInfoBean base_map_info) {
                this.base_map_info = base_map_info;
            }

            public String getCatacode() {
                return catacode;
            }

            public void setCatacode(String catacode) {
                this.catacode = catacode;
            }

            public String getCatalog() {
                return catalog;
            }

            public void setCatalog(String catalog) {
                this.catalog = catalog;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDist() {
                return dist;
            }

            public void setDist(String dist) {
                this.dist = dist;
            }

            public String getDtype() {
                return dtype;
            }

            public void setDtype(String dtype) {
                this.dtype = dtype;
            }

            public String getFinal_score() {
                return final_score;
            }

            public void setFinal_score(String final_score) {
                this.final_score = final_score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPointx() {
                return pointx;
            }

            public void setPointx(String pointx) {
                this.pointx = pointx;
            }

            public String getPointy() {
                return pointy;
            }

            public void setPointy(String pointy) {
                this.pointy = pointy;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }

            public static class AddrInfoBean {
                /**
                 * adcode : 620826
                 * c : 平凉市
                 * d : 静宁县
                 * p : 甘肃省
                 * short_addr :
                 */

                private String adcode;
                private String c;
                private String d;
                private String p;
                private String short_addr;

                public String getAdcode() {
                    return adcode;
                }

                public void setAdcode(String adcode) {
                    this.adcode = adcode;
                }

                public String getC() {
                    return c;
                }

                public void setC(String c) {
                    this.c = c;
                }

                public String getD() {
                    return d;
                }

                public void setD(String d) {
                    this.d = d;
                }

                public String getP() {
                    return p;
                }

                public void setP(String p) {
                    this.p = p;
                }

                public String getShort_addr() {
                    return short_addr;
                }

                public void setShort_addr(String short_addr) {
                    this.short_addr = short_addr;
                }
            }

            public static class BaseMapInfoBean {
                /**
                 * base_map_flag : 1
                 */

                private String base_map_flag;

                public String getBase_map_flag() {
                    return base_map_flag;
                }

                public void setBase_map_flag(String base_map_flag) {
                    this.base_map_flag = base_map_flag;
                }
            }
        }

        public static class ResultsBean {
            /**
             * adcode : 620826
             * c : 平凉市
             * c_cht : 平涼市
             * c_en : Pingliang
             * city_code : 156620800
             * d : 静宁县
             * dtype : AD
             * n : 中国
             * n_cht : 中國
             * n_en : China
             * name : 中国,甘肃省,平凉市,静宁县
             * nation_code : 156
             * p : 甘肃省
             * p_cht : 甘肅省
             * p_en : GANSU
             * pointx : 105.692413
             * pointy : 35.395168
             * stat : 0
             * address_children_scene : Village
             * address_name : 静宁县甘沟镇张家岔
             * address_scene : BaseStrategy
             * rough_address_name : 静宁县甘沟镇张家岔
             * desc_weight : 500.0
             * direction : 内
             * dist : 0.0
             * uid : 620826212
             */

            private String adcode;
            private String c;
            private String c_cht;
            private String c_en;
            private String city_code;
            private String d;
            private String dtype;
            private String n;
            private String n_cht;
            private String n_en;
            private String name;
            private String nation_code;
            private String p;
            private String p_cht;
            private String p_en;
            private String pointx;
            private String pointy;
            private int stat;
            private String address_children_scene;
            private String address_name;
            private String address_scene;
            private String rough_address_name;
            private String desc_weight;
            private String direction;
            private String dist;
            private String uid;

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getC() {
                return c;
            }

            public void setC(String c) {
                this.c = c;
            }

            public String getC_cht() {
                return c_cht;
            }

            public void setC_cht(String c_cht) {
                this.c_cht = c_cht;
            }

            public String getC_en() {
                return c_en;
            }

            public void setC_en(String c_en) {
                this.c_en = c_en;
            }

            public String getCity_code() {
                return city_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public String getD() {
                return d;
            }

            public void setD(String d) {
                this.d = d;
            }

            public String getDtype() {
                return dtype;
            }

            public void setDtype(String dtype) {
                this.dtype = dtype;
            }

            public String getN() {
                return n;
            }

            public void setN(String n) {
                this.n = n;
            }

            public String getN_cht() {
                return n_cht;
            }

            public void setN_cht(String n_cht) {
                this.n_cht = n_cht;
            }

            public String getN_en() {
                return n_en;
            }

            public void setN_en(String n_en) {
                this.n_en = n_en;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNation_code() {
                return nation_code;
            }

            public void setNation_code(String nation_code) {
                this.nation_code = nation_code;
            }

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }

            public String getP_cht() {
                return p_cht;
            }

            public void setP_cht(String p_cht) {
                this.p_cht = p_cht;
            }

            public String getP_en() {
                return p_en;
            }

            public void setP_en(String p_en) {
                this.p_en = p_en;
            }

            public String getPointx() {
                return pointx;
            }

            public void setPointx(String pointx) {
                this.pointx = pointx;
            }

            public String getPointy() {
                return pointy;
            }

            public void setPointy(String pointy) {
                this.pointy = pointy;
            }

            public int getStat() {
                return stat;
            }

            public void setStat(int stat) {
                this.stat = stat;
            }

            public String getAddress_children_scene() {
                return address_children_scene;
            }

            public void setAddress_children_scene(String address_children_scene) {
                this.address_children_scene = address_children_scene;
            }

            public String getAddress_name() {
                return address_name;
            }

            public void setAddress_name(String address_name) {
                this.address_name = address_name;
            }

            public String getAddress_scene() {
                return address_scene;
            }

            public void setAddress_scene(String address_scene) {
                this.address_scene = address_scene;
            }

            public String getRough_address_name() {
                return rough_address_name;
            }

            public void setRough_address_name(String rough_address_name) {
                this.rough_address_name = rough_address_name;
            }

            public String getDesc_weight() {
                return desc_weight;
            }

            public void setDesc_weight(String desc_weight) {
                this.desc_weight = desc_weight;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDist() {
                return dist;
            }

            public void setDist(String dist) {
                this.dist = dist;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }
}

