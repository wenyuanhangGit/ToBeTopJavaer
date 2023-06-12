package com.wen.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class WatcherDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString("172.16.255.43:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();

//        addListenerWithNode(curatorFramework);
        addListenerWithChild(curatorFramework);
        System.in.read();
    }

    //创建、修改、删除
    private static void addListenerWithNode(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework, "/watch", false);
        NodeCacheListener nodeCacheListener = () -> {
            System.out.println("receive Node Changed");
            System.out.println(nodeCache.getCurrentData().getPath() + "---" + new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    //实现服务注册中心的时候，可以针对服务动态感知
    private static void addListenerWithChild(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework, "/watch", true);
        PathChildrenCacheListener nodeCacheListener = (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType() + "--->" + new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }

}
