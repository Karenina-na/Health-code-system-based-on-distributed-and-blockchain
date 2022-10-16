package com.ybxt.syncserver.entity.message;

import com.ybxt.syncserver.entity.ServerModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器模型列表
 *
 * @author 15399
 * @date 2022/10/16
 */
@Data
public class ServerModelList {
    private List<ServerModel> serverModelList=new ArrayList<>();
}
