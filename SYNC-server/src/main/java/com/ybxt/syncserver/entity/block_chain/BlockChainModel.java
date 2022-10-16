package com.ybxt.syncserver.entity.block_chain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 块链模型
 *
 * @author 15399
 * @date 2022/10/13
 */
@Data
public class BlockChainModel {
    private  final Lock lock=new ReentrantLock();
    private List<BlockModel> blockChain=new LinkedList<>();

    /**
     * 创建块
     *
     * @param content 内容
     * @return {@link BlockModel}
     */
    public BlockModel createBlock(String content){
        BlockModel blockModel=new BlockModel();
        blockModel.setContent(content);
        if (blockChain.size()==0){
            blockModel.setId(0);
            blockModel.setPreviousHash("0");
        }else {
            blockModel.setId(blockChain.size());
            blockModel.setPreviousHash(blockChain.get(blockChain.size()-1).getHash());
        }
        blockModel.createHash();
        return blockModel;
    }

    /**
     * 添加块
     *
     * @param blockModel 块模型
     */
    public void addBlock(BlockModel blockModel){
        lock.lock();
        blockChain.add(blockModel);
        lock.unlock();
    }

    /**
     * 得到头区块并从链上删除
     *
     * @return {@link BlockModel}
     */
    public BlockModel getHeadBlock(){
        lock.lock();
        blockChain.remove(0);
        BlockModel blockModel = blockChain.get(0);
        lock.unlock();
        return blockModel;
    }

    /**
     * 得到链的长度
     *
     * @return 链的长度
     */
    public int getBlockChainSize(){
        lock.lock();
        int size = blockChain.size();
        lock.unlock();
        return size;
    }

    /**
     * 检查区块
     *
     * @param blockModel 块模型
     * @return boolean
     */
    public boolean  checkBlock(BlockModel blockModel){
        lock.lock();
        boolean flag2=blockModel.checkBlock();
        boolean flag1 = blockChain.get(blockChain.size() - 1).getHash().equals(blockModel.getPreviousHash());
        lock.unlock();
        return flag1&&flag2;
    }

    /**
     * 获取最后一个块的hash
     *
     * @return {@link String}
     */
    public String getHash(){
        lock.lock();
        String hash = blockChain.get(blockChain.size() - 1).getHash();
        lock.unlock();
        return hash;
    }
}
