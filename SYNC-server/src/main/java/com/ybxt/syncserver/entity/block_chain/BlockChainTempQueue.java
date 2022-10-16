package com.ybxt.syncserver.entity.block_chain;

import lombok.Data;

import java.util.Queue;

/**
 * 区块链临时队列
 *
 * @author 15399
 * @date 2022/10/16
 */
@Data
public class BlockChainTempQueue {
    private Queue<BlockModel> blockChainTempQueue;
    private String Hash;

    /**
     * 添加块
     *
     * @param blockModel 块模型
     */
    public void AddBlock(BlockModel blockModel){
        synchronized (this){
            blockChainTempQueue.add(blockModel);
            Hash=blockModel.getHash();
        }
    }

    /**
     * 检查块哈希
     *
     * @param hash 哈希
     * @return boolean
     */
    public boolean CheckBlock(String hash){
        synchronized (this){
            if (blockChainTempQueue.size()>0){
                BlockModel blockModel=blockChainTempQueue.peek();
                if (blockModel.getHash().equals(hash)){
                    return true;
                }
            }
        }
        return false;
    }
}
