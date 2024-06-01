package online.shenjian.cloud.common.enums;

public class Constant {


    public static final String INIT_PASSWORD = "123456";

    public enum YesOrNo {

        NO("0", "否"),
        YES("1", "是");

        private String val;
        private String desc;

        YesOrNo(String val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public String val() {
            return val;
        }

        public String desc() {
            return desc;
        }
    }

    public enum NormalSave {

        NO(0, "否"),
        YES(1, "是");

        private Integer val;
        private String desc;

        NormalSave(Integer val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public Integer val() {
            return val;
        }

        public String desc() {
            return desc;
        }
    }

    public enum ProjectStatus {

        NOT_START(0, "未开始"),
        RUNNING(1, "进行中"),
        FINISHED(2, "已结束");

        private Integer val;
        private String desc;

        ProjectStatus(Integer val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public Integer val() {
            return val;
        }

        public String desc() {
            return desc;
        }

        public static String getDescByVal(Integer val) {
            for (ProjectStatus projectStatus : ProjectStatus.values()) {
                if (projectStatus.val.equals(val)) {
                    return projectStatus.desc();
                }
            }
            return "";
        }
    }

    public enum ProjectPeriod {

        ONE(1, "Ⅰ期"),
        TWO(2, "Ⅱ期"),
        THREE(3, "Ⅲ期"),
        FOUR(4, "Ⅳ期");

        private Integer val;
        private String desc;

        ProjectPeriod(Integer val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public Integer val() {
            return val;
        }

        public String desc() {
            return desc;
        }

        public static String getDescByVal(Integer val) {
            for (ProjectPeriod projectPeriod : ProjectPeriod.values()) {
                if (projectPeriod.val.equals(val)) {
                    return projectPeriod.desc();
                }
            }
            return "";
        }
    }
}