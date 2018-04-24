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
    * 汽水替换问题

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

### 汽水替换问题
2块钱可以买一瓶汽水瓶,4个瓶盖可换取一个汽水,2个空瓶可换取一个汽水,输入一个金额可喝多少瓶汽水
代码包https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/BottleReplaceTest.java  
该代码里的逻辑可通过规则引擎实现，目前是两个规则相互独立，没有交叉部分（一个规则里只有一个属性判断），更高级的规则里属性重叠，比如：
```
1个空瓶2个瓶盖可换一瓶汽水
2个空瓶3个瓶盖可换两瓶汽水
...
```
按照这种逻辑，应该有个最优换汽水步骤，以达到最大可喝汽水数量   
代码：https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/BottleReplaceTest2.java


