package com.ybxt.syncserver.entity.block_chain;

import com.ybxt.syncserver.entity.block_chain.BlockModel;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 临时数据队列
 *
 * @author 15399
 * @date 2022/10/13
 */
@Data
public class TempBlockQueueModel {
    private Queue<BlockModel> tempBlockChain=new LinkedList<>();
    private  final Lock lock=new ReentrantLock();
    private String Hash;

    /**
     * 检查块
     *
     * @param blockModel 块模型
     * @return boolean
     */
    public boolean  checkBlock(BlockModel blockModel){
        lock.lock();
        boolean flag2=blockModel.checkBlock();
        boolean flag1 = Hash.equals(blockModel.getPreviousHash());
        lock.unlock();
        return flag1&&flag2;
    }

    /**
     * 值入队
     *
     * @param blockModel 块模型
     */
    public void add(BlockModel blockModel){
        lock.lock();
        tempBlockChain.add(blockModel);
        this.Hash=blockModel.getHash();
        lock.unlock();
    }

    /**
     * 值出队
     *
     * @return {@link BlockModel}
     */
    public BlockModel get(){
        lock.lock();
        BlockModel blockModel = tempBlockChain.poll();
        lock.unlock();
        return blockModel;
    }

    /**
     * 队列长度
     *
     * @return int
     */
    public int size(){
        lock.lock();
        int size = tempBlockChain.size();
        lock.unlock();
        return size;
    }
}
