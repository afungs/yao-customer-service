package xyz.anfun.customer_service.util;

public class StatusUtil {
    // 激活状态
    public final static int ACTIVE = 1;

    // 禁用
    public final static int DISABLE = -1;

    // 待激活
    public final static int TO_BE_ACTIVE = 2;

    // 待结算
    public final static int TO_BE_SETTLED = 3;

    // 待审核
    public final static int PENDING_REVIEW = 4;

    public static String codeToString(int statys) {
       switch (statys){
           case ACTIVE:
               return "active";
           case DISABLE:
               return "disable";
           case TO_BE_ACTIVE:
               return "toBeActive";
           case TO_BE_SETTLED:
               return "toBeSettled";
           case PENDING_REVIEW:
               return "pendingReview";
       }
       return null;
    }
}
