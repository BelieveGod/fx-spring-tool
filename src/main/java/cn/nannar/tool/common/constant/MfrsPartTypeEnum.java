package cn.nannar.tool.common.constant;

/**
 * 厂家的部位类型
 * @author LTJ
 * @date 2023/1/10
 */
public enum MfrsPartTypeEnum {
    WHEEL(1,"轮子"),
    AXLE(3,"车轴"),
    BOGIE(2,"转向架"),
    CARRIAGE(5,"车厢"),
    PTG(6,"受电弓"),
    MOTOR(8,"电机"),
    SLIDE(9,"滑块"),
    TRAIN(10,"整列车");

    private Integer customType;
    private String name;

    MfrsPartTypeEnum(Integer customType, String name) {
        this.customType = customType;
        this.name = name;
    }

    public Integer getCustomType() {
        return customType;
    }

    public String getName() {
        return name;
    }
}
