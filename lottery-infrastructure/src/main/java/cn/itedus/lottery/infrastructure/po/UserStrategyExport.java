package cn.itedus.lottery.infrastructure.po;

import lombok.Data;

import java.util.Date;

/**
 * @description: 用户策略计算结果表
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/30
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Data
public class UserStrategyExport {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;
    /** 订单ID */
    private Long orderId;
    /** 策略ID */
    private Long strategyId;
    /** 策略方式（1:单项概率、2:总体概率） */
    private Integer strategyMode;
    /** 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工） */
    private Integer grantType;
    /** 发奖时间 */
    private Date grantDate;
    /** 发奖状态 */
    private Integer grantState;
    /** 发奖ID */
    private String awardId;
    /** 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品） */
    private Integer awardType;
    /** 奖品名称 */
    private String awardName;
    /** 奖品内容「文字描述、Key、码」 */
    private String awardContent;
    /** 防重ID */
    private String uuid;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
    /** mq的状态 */
    private Integer mqState;
    /** 0:未领取、1:已领取、2:已过期（需要返还库存） */
    private Integer claimState;


    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }
}
