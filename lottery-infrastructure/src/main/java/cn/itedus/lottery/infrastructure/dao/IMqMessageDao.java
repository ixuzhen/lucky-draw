package cn.itedus.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.itedus.lottery.po.MqMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMqMessageDao {

    @DBRouter(key = "messageId")
    void insert(MqMessage mqMessage);

    @DBRouter(key = "messageId")
    void updateState(MqMessage mqMessage);


}
