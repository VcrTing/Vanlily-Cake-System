package com.van.lily.model.order.transfer.remind;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.van.lily.commons.config.VanLilyConfigure;
import com.van.lily.commons.utils.qiong.QDateUtil;
import com.van.lily.commons.utils.qiong.QValueUtil;
import com.van.lily.model.order.define.enums.EnumRemindOrderType;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.order.front.group.GroupOrderRemindForm;
import com.van.lily.model.order.front.view.remind.ViewRemindData;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public final class RemindMessageTransfer {
    /**
    * 消息頭
    */
    public static StringBuilder addCashierPrefix(StringBuilder src) {
        src.append("【店內提醒】");
        return src;
    }
    public static void addCashierSuffix(StringBuilder src) {
        src.append("，最後，請看到消息後儘快處理，以免影響到客人的體驗");
    }
    public static StringBuilder addCustomerPrefix(StringBuilder src) {
        src.append("【" + VanLilyConfigure.NAME_FULL + "】尊敬的客戶您好，");
        return src;
    }
    public static void addCustomerSuffix(StringBuilder src) {
        src.append("，最後，感謝您支持").append(VanLilyConfigure.NAME_FULL).append("，祝您生活愉快。");
    }

    /**
    * 生成短信數據
    */
    public static String orderRemindMessage(Order order, OrderRemind remind) {
        StringBuilder sb = new StringBuilder();
        EnumRemindOrderType workType = remind.getType_work();

        switch (workType) {
            case pay_not_yet_for_customer:
                addCustomerPrefix(sb).append("您於")
                        .append(QDateUtil.convertCN(order.getPublished(), true))
                        .append("在本店訂購的蛋糕訂單還未付款，若訂單持續未付款，訂單將會被取消，親於")
                        .append(QDateUtil.convertCN(QDateUtil.afterHour(2), true))
                        .append("前付款，已付款的訂單，將很快會被受理");
                addCustomerSuffix(sb);
                break;
            case pay_not_yet_for_cashier:
                addCashierPrefix(sb).append("有項訂單完善訂單數據後，還未有付款紀錄的數據，")
                        .append("請儘快查看該訂單付款狀態，並且儘快聯絡客人，了解客人的付款意願，以免耽誤訂單進度，")
                        .append("該訂單號為：").append(order.getUuid()).append("，訂單數據的完善時間為")
                        .append(QDateUtil.convertCN(order.getDate_filling(), true));
                addCashierSuffix(sb);
                break;
            case paid_for_customer:
                addCustomerPrefix(sb).append("訂單已在火速受理中，訂單流水號為：")
                        .append(order.getArticle_number())
                        .append("，訂單流水號是取貨時候會用到的重要憑證，即便您指定了配送方式，您也可以憑借訂單流水號在訂單打包之後，前來本店內自提訂單的蛋糕，")
                        .append("，此外，您還可憑借該流水號在").append(VanLilyConfigure.NAME_FULL).append("官方網站內查看訂單的受理進度")
                        .append("也可以與店內服務員取得聯繫，憑借訂單流水號詢問訂單的作業詳情");
                addCustomerSuffix(sb);
                break;
            case paid_for_cashier:
                addCashierPrefix(sb).append("該訂單30分鐘後，就到了客人預定的送貨時間，請檢查該訂單的作業進度")
                        .append("如若訂單還未完成，請儘快將訂單完成並且提交好訂單打包數據，謝謝配合，")
                        .append("該訂單流水號為：").append(order.getArticle_number())
                        .append("客人預定的配送時間為：").append(QDateUtil.convertCN(order.getDate_reserve_delivery(), true));
                addCashierSuffix(sb);
                break;
            case packaged_for_customer:
                addCustomerPrefix(sb).append("您於")
                        .append(QDateUtil.convertCN(order.getPublished(), true))
                        .append("在本店訂購的訂單蛋糕已經打包完成，我們將在預定的配送時間內將訂單蛋糕配送到您的手中")
                        .append("您預定的配送時間為").append(QDateUtil.convertCN(order.getDate_reserve_delivery(), true))
                        .append("還請您耐心等待。此外，如若您想提前取得訂單蛋糕，可憑借訂單流水號到店內進行自取，")
                        .append("您的訂單流水號為：").append(order.getArticle_number());
                addCustomerSuffix(sb);
                break;
            case packaged_for_cashier:
                addCustomerPrefix(sb).append("客人的訂單已到達預定的送貨時間，請在管理後台內的工作板查看該訂單的配送詳情")
                        .append("看完配送信息後，請儘快聯絡配送員進行訂單的配送，以免耽誤客人生日派對活動，感謝配合。")
                        .append("該訂單流水號為：").append(order.getArticle_number())
                        .append("預定的送達時間為：").append(QDateUtil.convertCN(order.getDate_reserve_delivery(), true));
                addCustomerSuffix(sb);
                break;
        }
        return sb.toString();
    }
}
