package cn.itedus.lottery.controller;


import cn.hutool.json.JSONUtil;
import cn.itedus.lottery.infrastructure.dao.IUserStrategyExportDao;
import cn.itedus.lottery.po.UserStrategyExport;
import cn.itedus.lottery.rpc.ILotteryActivityBooth;
import cn.itedus.lottery.rpc.req.DrawReq;
import cn.itedus.lottery.rpc.res.DrawRes;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private IUserStrategyExportDao userStrategyExportDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/hello")
    String hello() {
        log.error("212344");
        return "hello";
    }

    @RequestMapping("/send")
    public String sendMesg(@RequestParam("id") Integer id)
    {
        UserStrategyExport userStrategyExport1 = new UserStrategyExport();
        userStrategyExport1.setuId("xiaofuge");
        userStrategyExport1.setId((long) id);
        UserStrategyExport userStrategyExport = userStrategyExportDao.queryUserStrategyExportById(userStrategyExport1);
        rabbitTemplate.convertAndSend(
                "lottery-event-exchange",
                "lottery.succeed.delay",
                userStrategyExport
        );
        log.error(JSONUtil.toJsonStr(userStrategyExport));
        return "success";
    }

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @RequestMapping("/doDraw")
    public void test_doDraw() {
        DrawReq drawReq = new DrawReq();
        drawReq.setuId("xiaofuge");
        drawReq.setActivityId(100001L);
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        log.error("请求参数：{}", JSON.toJSONString(drawReq));
        log.error("测试结果：{}", JSON.toJSONString(drawRes));

    }



}
