study
===========================
该项目用于积累学习中的问题，记录一些有效的代码，其中包括一些算法题。 

****
|Author|小曾|
|---|---
|E-mail|haozengfa@163.com

****
## 目录
* [算法](#算法)
    * 矩阵顺序输出
    * 喝汽水问题
    * 数组全排列

算法
------
### 矩阵顺序输出
输入一个矩阵，按照从外向里已顺时针依次打印出每个数字，例如:
```
    1   2   3   4
    5   6   7   8
    9   10  11  12
    13  14  15  16
``` 
则依次打印输出：1、2、3、4、8、12、16、15、14、13、9、5、6、7、11、10  
代码https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/ArrayRightReadTest.java  
该代码可实现顺时或逆时输出，获得读取步数，更高级功能可实现随机行走方向，类似贪吃蛇效果。

### 喝汽水问题
2块钱可以买一瓶汽水瓶,4个瓶盖可换取一个汽水,2个空瓶可换取一个汽水,输入一个金额可喝多少瓶汽水
代码包https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/bottle/BottleReplaceTest.java  
该代码里的逻辑可通过规则引擎实现，目前是两个规则相互独立，没有交叉部分（一个规则里只有一个属性判断），更高级的规则里属性重叠，比如：
```
1个空瓶2个瓶盖可换一瓶汽水
2个空瓶3个瓶盖可换两瓶汽水
...
```
按照这种逻辑，应该有个最优换汽水步骤，以达到最大可喝汽水数量   
代码：https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/bottle/MutilRuleBottleReplaceTest.java

### 数组全排列
全排列概念：从n个不同元素中任取m（m≤n）个元素，按照一定的顺序排列起来，叫做从n个不同元素中取出m个元素的一个排列。当m=n时所有的排列情况叫全排列。   
例如1,2,3三个元素的全排列为：123、132、213、231、312、321   
全排列算法有很多，网上都有介绍，这里说明下字典排序。   
对字典排序要了解怎样寻找最近的大于某数的换位数，主要参考https://mp.weixin.qq.com/s/q2KnZ0dovz0zVy6xNyJ6tA   
比如下面这样： 
```
输入12345，返回12354
输入12354，返回12435
输入12435，返回12453
```
为了和原数接近，我们需要尽量保持高位不变，低位在最小的范围内变换顺序。低位的范围取决于逆序区域   
![](https://github.com/zengfa1988/study/blob/master/ImageCache/DictArrange1.png)   
如果所示，12354的逆序区域是最后两位，仅看这两位已经是当前的最大组合。若想最接近原数，又比原数更大，必须从倒数第3位开始改变。   
12345的倒数第3位是3，我们需要从后面的逆序区域中寻找到刚刚大于3的数字，和3的位置进行互换：   
![](https://github.com/zengfa1988/study/blob/master/ImageCache/DictArrange2.png)   
互换后的临时结果是12453，倒数第3位已经确定，这时候最后两位仍然是逆序状态。我们需要把最后两位转变回顺序，以此保证在倒数第3位数值为4的情况下，后两位尽可能小：
![](https://github.com/zengfa1988/study/blob/master/ImageCache/DictArrange3.png)
这样一来，我们就得到了想要的结果12435。   
采用这种方式可以对数组进行全排列，给定一个数组，可以依次获得数组的下个换位数，从而得到全排列

当前数组|下个换位数|
---|---|
123|132|
132|213|
213|231|
231|312|
312|321|
321|   |

这是线状流程，只对有序数组有效，可以变形处理环状流程，最大数组时转到最小数   
图片   
代码https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/arrange/DictArrangeTest.java   




