package com.gupaoedu.pattern.template.course;

public abstract class NetworkCourse {

    protected final void createCourse() {
        this.postPreResource();
        this.createPPT();
        this.liveVideo();
        this.postNote();
        this.postSource();
        if (needHomework()) {
            checkHomework();
        }
    }

     abstract void checkHomework();

    protected boolean needHomework() {
        return false;
    }

    final void postSource() {
        System.out.println("上传源代码！");
    }


    final void postNote() {
        System.out.println("上传笔记");
    }
    
    final void liveVideo() {
        System.out.println("在线直播");
    }

    final void createPPT() {
        System.out.println("创建ppt！");
    }

    final void postPreResource() {
        System.out.println("上传预习资料！");
    }

}
