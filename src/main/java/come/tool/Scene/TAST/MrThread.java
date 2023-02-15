package come.tool.Scene.TAST;

import come.tool.Scene.BTY.BTYScene;

public class MrThread implements Runnable{

    private MrScene btyScene;

    public MrThread(MrScene btyScene) {
        super();
        this.btyScene = btyScene;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (btyScene.isEnd()) {
            if (btyScene.time%180==0) {//刷怪
                btyScene.open();
            }
            try {
                btyScene.move();//移动
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            btyScene.time++;
        }
    }

}