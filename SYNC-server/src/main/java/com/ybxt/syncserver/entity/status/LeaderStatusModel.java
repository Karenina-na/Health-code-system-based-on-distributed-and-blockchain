package com.ybxt.syncserver.entity.status;

import com.ybxt.syncserver.entity.ServerModel;
import lombok.Data;

@Data
public class LeaderStatusModel {
    private ServerModel leader;
    private Integer serverNum=0;
    private Integer ackCount=0;
}
