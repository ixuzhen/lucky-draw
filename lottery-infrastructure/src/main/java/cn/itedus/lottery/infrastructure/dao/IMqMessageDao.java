package cn.itedus.lottery.infrastructure.dao;

import cn.itedus.lottery.po.MqMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMqMessageDao {


    void insert(MqMessage mqMessage);

    void updateState(MqMessage mqMessage);


}
