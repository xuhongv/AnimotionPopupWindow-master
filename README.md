### Android自定义View之popupwindow进阶封装：高仿ios “item动画弹出”效果的popupwindow。
 
 对应CSDN博客传送门：http://blog.csdn.net/xh870189248/article/details/77258388
 
- 废话不说，先上效果图：


![这里写图片描述](http://img.blog.csdn.net/20170816161310779?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



### 一、前言。


- 看到这样的效果，实现的方式很多种，一开始我想到的是和我上个月手撸的“高仿咸鱼”app的底部弹出效果：[点击我传送过去](http://blog.csdn.net/xh870189248/article/details/75949283)。 但是问题还是挺多的，我总结如下：

 - 1、要先固定好每个控件的位置，然后获取每个控件在屏幕的位置，这样就有很多位置的“坐标数据”了，这样做起来麻烦。
 
 - 2、要计算每个要“平移动画”的控件的开始坐标和结束坐标，

 - 3、如果一个项目多种这样的弹窗，那岂不是要每个都要写一个布局？这能算是“popupwidonw进阶封装”吗？



### 二、何其重要的 LayoutAnimationController ：控制子控件的动画。




- 给大家简单介绍下 LayoutAnimationController。
  
  - LayoutAnimationController 用可以为一个Layout里面的控件或者是Viewgroup的控件设置动画效果。
  
  - 可以设置子控件弹出顺序主要有随机，从头到尾，反向3种) ，控件出现的间隔时间等。
  



>  既然上面提到 LayoutAnimationController可以为一个Layout里面的控件或者是Viewgroup的控件设置动画效果，那么我想到的思路是在 popuwindow上的view的控件做出这样的效果，所以，我想到的是**listview**，毕竟必须是Viewgroup或layout，如果是layout，在其里面动态的增加子控件，是不简单的，但是利用一些Viewgroup的控件，比如 listview、recyclerView...  等等，这里我就用listView啦，毕竟大家都很烂熟这个控件使用了。 下面是我的popupwindow加载的布局：




![这里写图片描述](http://img.blog.csdn.net/20170816164836873?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



其代码就是如下这么少：



```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

   <!--注意其的宽高设置哦-->
    <ListView
        android:id="@+id/lv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@+id/btCancle"
        android:layout_margin="5dp" />

    <Button
        android:id="@+id/btCancle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_margin="5dp"
        android:background="@drawable/buttonbg"
        android:gravity="center"
        android:text="取消"
        android:textColor="#797979"
        android:textStyle="bold" />

</RelativeLayout>
```


----------
预览就如下：


----------
![这里写图片描述](http://img.blog.csdn.net/20170816165154391?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


### 三、代码分析。


----------
 
 - 上面已经做好了listView的显示位置，现在要做好其“**儿子控件***”的出现方式了。

 - 先设置平移动画 ：
   

```
// 从自已3倍的位置下面移到自己原来的位置，设置平移时间为0.4秒
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF,3f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(400);
        animation.setStartOffset(150);
```
- 然后在LayoutAnimationController的构造方法设置，其动画和每个子view的相继出现的时间间隔，我反复测试最佳时间是0.12，（注意是**浮点类型**）。

        

```     
        //构造方法，参数：动画、每个子view的相继出现的时间间隔
        mLac = new LayoutAnimationController(animation, 0.12f);
        //设置加速插值器
        mLac.setInterpolator(new DecelerateInterpolator());
```

- 好了，核心的代码就这么多啦，其他的popupwindow就没啥啦，想看整个代码去博文下面链接观看啦。相信大家都是可以做到的。
 



### 三、开发遇到的小问题总结。


- 1、想让弹出来的listView看起来不像 “listView”，如果你不设置其背景颜色为白色，就会被蒙蔽一层“纱布”似的，但是你在其背景颜色为白色，就会整个listview都是白色，不好看。以下是我截图：

- 2、所以，我们在子 item设置背景就好啦，详情看sample啦。


   - 未背景颜色时候：
  
![设置之前](http://img.blog.csdn.net/20170816172133086?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


   - 已背景颜色时：
   

![这里写图片描述](http://img.blog.csdn.net/20170816172250321?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


