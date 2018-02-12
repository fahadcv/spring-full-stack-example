package com.fhd.devopsbuddy.enums;

public enum PlansEnum {
    BASIC(1, "Basic"),
    PRO(2, "Pro", "plan_CJJPydPKJ9gtX7");


    private final int id;

    private final String planName;

    private final String stripePlanId;

    PlansEnum(int id, String planName) {
        this(id, planName, null);
    }

    PlansEnum(int id, String planName, String stripePlanId) {
        this.id = id;
        this.planName = planName;
        this.stripePlanId = stripePlanId;
    }

    public int getId() {
        return id;
    }

    public String getPlanName() {
        return planName;
    }

    public String getStripePlanId() {
        return stripePlanId;
    }

}
