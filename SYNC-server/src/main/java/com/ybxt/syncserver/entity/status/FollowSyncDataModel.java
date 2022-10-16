package com.ybxt.syncserver.entity.status;

import com.ybxt.syncserver.entity.block_chain.BlockModel;
import com.ybxt.syncserver.entity.ServerModel;
import lombok.Data;

@Data
public class FollowSyncDataModel {
    private ServerModel leader;
    private BlockModel blockModel;
}
