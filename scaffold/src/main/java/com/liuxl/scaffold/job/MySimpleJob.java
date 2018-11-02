package com.liuxl.scaffold.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * Created with IntelliJ IDEA.
 * Description:Elastic-Job
 *
 * @author liuxl
 * @date 2018/10/30
 */
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
                Thread.currentThread().getId(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem())+System.currentTimeMillis());
        /**
         * 实际开发中，有了任务总片数和当前分片项，就可以对任务进行分片执行了
         * 比如 SELECT * FROM user WHERE status = 0 AND MOD(id, shardingTotalCount) = shardingItem
         */
    }
}
