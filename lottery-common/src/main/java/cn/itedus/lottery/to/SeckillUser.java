package cn.itedus.lottery.to;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillUser implements Serializable {

    private static final long serialVersionUID = 11112L;

    private String activityId;
    private String userId;

}
