package cn.itedus.lottery.controller;


import cn.itedus.lottery.common.R;
import cn.itedus.lottery.rpc.ILotteryActivityBooth;
import cn.itedus.lottery.rpc.req.DrawReq;
import cn.itedus.lottery.rpc.res.DrawRes;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api")
public class LotteryController {

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;
    @GetMapping("/lottery/{userId}/{activityId}")
    public R<DrawRes> lottery(@PathVariable("userId") String userId,@PathVariable("activityId") String activityId) {
        DrawReq drawReq = new DrawReq();
        drawReq.setuId(userId);
        drawReq.setActivityId(Long.valueOf(activityId));
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        log.info("请求参数：{}", JSON.toJSONString(drawReq));
        log.info("测试结果：{}", JSON.toJSONString(drawRes));
        return R.successWithData(drawRes);
    }

}
