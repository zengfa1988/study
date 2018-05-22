study
===========================
该项目用于积累学习中的问题，记录一些有效的代码，其中包括一些算法题、设计模式。 

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
* [设计模式](#设计模式)
    * 工厂模式
    * 抽象工厂模式
    * 单例模式
    * 建造者模式
    * 原型模式
    * 适配器模式

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
代码[矩阵顺时输出](https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/ArrayRightReadTest.java)  
该代码可实现顺时或逆时输出，获得读取步数，更高级功能可实现随机行走方向，类似贪吃蛇效果。

### 喝汽水问题
2块钱可以买一瓶汽水瓶,4个瓶盖可换取一个汽水,2个空瓶可换取一个汽水,输入一个金额可喝多少瓶汽水
代码包[喝汽水问题](https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/bottle/BottleReplaceTest.java)  
该代码里的逻辑可通过规则引擎实现，目前是两个规则相互独立，没有交叉部分（一个规则里只有一个属性判断），更高级的规则里属性重叠，比如：
```
1个空瓶2个瓶盖可换一瓶汽水
2个空瓶3个瓶盖可换两瓶汽水
...
```
按照这种逻辑，应该有个最优换汽水步骤，以达到最大可喝汽水数量   
代码：[优先级规则路径](https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/bottle/MutilRuleBottleReplaceTest.java)

### 数组全排列
全排列概念：从n个不同元素中任取m（m≤n）个元素，按照一定的顺序排列起来，叫做从n个不同元素中取出m个元素的一个排列。当m=n时所有的排列情况叫全排列。   
例如1,2,3三个元素的全排列为：123、132、213、231、312、321   
全排列算法有很多，网上都有介绍，这里说明下字典排序。   
对字典排序要了解怎样寻找最近的大于某数的换位数，主要参考[什么是字典序算法](https://mp.weixin.qq.com/s/q2KnZ0dovz0zVy6xNyJ6tA)   
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
![](https://github.com/zengfa1988/study/blob/master/ImageCache/DictArrange4.png)     
代码[全排列-字典排序](https://github.com/zengfa1988/study/blob/master/src/main/java/com/tsh/exam/arrange/DictArrangeTest.java)   

设计模式
------
### 工厂模式
参考[设计模式](https://www.tutorialspoint.com/design_pattern/factory_pattern.htm)   
代码目录：[包地址](https://github.com/zengfa1988/study/blob/master/src/main/java/com/study/patterns/factoryPattern)   
工厂模式是Java中应用最多的设计模式，属于创建模式的一种，这种模式提供了很好的方式获得对象，结构图如下：
![](https://github.com/zengfa1988/study/blob/master/resource/images/pattern/factory_pattern_uml_diagram.jpg)   
第一步，创建一个接口Shape.java   
```
public interface Shape {
   void draw();
}
```
第二步，创建三个类（Rectangle、Square、Circle）实现同一个接口   
```
public class Rectangle implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Rectangle::draw() method.");
   }
}
```

```
public class Square implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}
```

```
public class Circle implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}
```

第三步，创建工厂类   
```
public class ShapeFactory {
	
   //use getShape method to get object of type shape 
   public Shape getShape(String shapeType){
      if(shapeType == null){
         return null;
      }		
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
         
      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
         
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      
      return null;
   }
}
```

第四步，测试   
```
public class FactoryPatternDemo {

   public static void main(String[] args) {
      ShapeFactory shapeFactory = new ShapeFactory();

      //get an object of Circle and call its draw method.
      Shape shape1 = shapeFactory.getShape("CIRCLE");

      //call draw method of Circle
      shape1.draw();

      //get an object of Rectangle and call its draw method.
      Shape shape2 = shapeFactory.getShape("RECTANGLE");

      //call draw method of Rectangle
      shape2.draw();

      //get an object of Square and call its draw method.
      Shape shape3 = shapeFactory.getShape("SQUARE");

      //call draw method of circle
      shape3.draw();
   }
}
```

运行结果：   
```
Inside Circle::draw() method.
Inside Rectangle::draw() method.
Inside Square::draw() method.
```

### 抽象工厂模式
参考[设计模式](https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm)   
代码目录：[包地址](https://github.com/zengfa1988/study/blob/master/src/main/java/com/study/patterns/abstractFactoryPattern)   
抽象工厂模式是通过超级工厂获得工厂，这个超级工厂是工厂集合，属于创建模式的一种，这种模式提供了很好的方式获得对象，结构图如下：   
![](https://github.com/zengfa1988/study/blob/master/resource/images/pattern/abstractfactory_pattern_uml_diagram.jpg)   
第一步，创建接口Shape
```
public interface Shape {
   void draw();
}
```
第二步，创建类Rectangle、Square、Circle实现Shape接口
```
public class Rectangle implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Rectangle::draw() method.");
   }
}
```

```
public class Square implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}
```

```
public class Circle implements Shape {

   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}
```

第三步， 创建接口Colors
```
public interface Color {
   void fill();
}
```
第四步，创建类Red、Green、Blue实现Colors接口
```
public class Red implements Color {
   @Override
   public void fill() {
      System.out.println("Inside Red::fill() method.");
   }
}
```

```
public class Green implements Color {
   @Override
   public void fill() {
      System.out.println("Inside Green::fill() method.");
   }
}
```

```
public class Blue implements Color {
   @Override
   public void fill() {
      System.out.println("Inside Blue::fill() method.");
   }
}
```

第五步，创建抽象工厂类AbstractFactory
```
public abstract class AbstractFactory {
   abstract Color getColor(String color);
   abstract Shape getShape(String shape) ;
}
```

第六步，创建工厂类ShapeFactory、ColorFactory继承抽象工厂类
```
public class ShapeFactory extends AbstractFactory {
	
   @Override
   public Shape getShape(String shapeType){
   
      if(shapeType == null){
         return null;
      }		
      
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
         
      }else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
         
      }else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      
      return null;
   }
   
   @Override
   Color getColor(String color) {
      return null;
   }
}
```

```
public class ColorFactory extends AbstractFactory {
	
   @Override
   public Shape getShape(String shapeType){
      return null;
   }
   
   @Override
   Color getColor(String color) {
   
      if(color == null){
         return null;
      }		
      
      if(color.equalsIgnoreCase("RED")){
         return new Red();
         
      }else if(color.equalsIgnoreCase("GREEN")){
         return new Green();
         
      }else if(color.equalsIgnoreCase("BLUE")){
         return new Blue();
      }
      
      return null;
   }
}
```

第七步，创建工厂生产类FactoryProducer
```
public class FactoryProducer {
   public static AbstractFactory getFactory(String choice){
   
      if(choice.equalsIgnoreCase("SHAPE")){
         return new ShapeFactory();
         
      }else if(choice.equalsIgnoreCase("COLOR")){
         return new ColorFactory();
      }
      
      return null;
   }
}
```

第八步，测试AbstractFactoryPatternDemo
```
public class AbstractFactoryPatternDemo {
   public static void main(String[] args) {

      //get shape factory
      AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");

      //get an object of Shape Circle
      Shape shape1 = shapeFactory.getShape("CIRCLE");

      //call draw method of Shape Circle
      shape1.draw();

      //get an object of Shape Rectangle
      Shape shape2 = shapeFactory.getShape("RECTANGLE");

      //call draw method of Shape Rectangle
      shape2.draw();
      
      //get an object of Shape Square 
      Shape shape3 = shapeFactory.getShape("SQUARE");

      //call draw method of Shape Square
      shape3.draw();

      //get color factory
      AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");

      //get an object of Color Red
      Color color1 = colorFactory.getColor("RED");

      //call fill method of Red
      color1.fill();

      //get an object of Color Green
      Color color2 = colorFactory.getColor("Green");

      //call fill method of Green
      color2.fill();

      //get an object of Color Blue
      Color color3 = colorFactory.getColor("BLUE");

      //call fill method of Color Blue
      color3.fill();
   }
}
```

执行结果：   
```
Inside Circle::draw() method.
Inside Rectangle::draw() method.
Inside Square::draw() method.
Inside Red::fill() method.
Inside Green::fill() method.
Inside Blue::fill() method.
```

### 单例模式
参考[设计模式](https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm)，
   [单例模式的多种实现](https://www.cnblogs.com/Kevin-mao/p/5969227.html)   
代码目录：[包地址](https://github.com/zengfa1988/study/blob/master/src/main/java/com/study/patterns/singletonPattern)   
单例模式确保某个类只有一个实例，而且自行实例化并向整个系统提供这个实例，属于创建模式的一种，这种模式提供了很好的方式获得对象，结构图如下：   
![](https://github.com/zengfa1988/study/blob/master/resource/images/pattern/singleton_pattern_uml_diagram.jpg)   
单例模式分五种形式：   
第一种形式:懒汉式，线程不安全。
```
public class Singleton {  
    //私有化属性
    private static Singleton instance;  
    //私有化构造器
    private Singleton (){}  
    //提供获取单例的方法 
    public static Singleton getInstance() {  
         if (instance == null) {  
             instance = new Singleton();  
         }  
         return instance;  
    }  
} 
```

第二种形式:懒汉式，线程安全。
```
public class Singleton {  
    //私有化属性
    private static Singleton instance;  
    //私有化构造器
    private Singleton (){}  
    //提供了一个供外部访问类的对象的静态方法，可以直接访问
    public static synchronized Singleton getInstance() {  
        if (instance == null) {  
            instance = new Singleton();  
        }  
        return instance;  
    }  
}
```

第三种形式:饿汉式。
```
public class Singleton{
    //在类自己内部定义自己的一个实例，只供内部调用
    private static final Singleton instance = new Singleton();
    //私有化构造器
    private Singleton(){}
    //这里提供了一个供外部访问本类实例的静态方法，可以直接访问
    public static Singleton getInstance(){
        return instance;
    }
}
```

第四种形式:静态内部类。
```
public class Singleton {  
    //静态内部类
    private static class SingletonHolder {  
        private static final Singleton INSTANCE = new Singleton();  
    }  
    //私有化构造器
    private Singleton (){}
    //提供一个供外部访问本类实例的静态方法
    public static final Singleton getInstance() {  
        return SingletonHolder.INSTANCE;  
    }  
} 
```

第五种形式:双重校验锁的形式。
```
public class Singleton {  
    //
    private volatile static Singleton singleton;  
    //私有化构造器
    private Singleton (){}
    //提供一个供外部访问本类实例的静态方法  
    public static Singleton getSingleton() {  
        if (singleton == null) {  
            synchronized (Singleton.class) {  
                 if (singleton == null) {  
                    singleton = new Singleton();  
                }  
            }  
        }  
        return singleton;  
    }  
}
```

### 建造者模式
参考[设计模式](https://www.tutorialspoint.com/design_pattern/builder_pattern.htm)   
代码目录：[包地址](https://github.com/zengfa1988/study/blob/master/src/main/java/com/study/patterns/builderPattern)   
建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式，结构图如下：  
![](https://github.com/zengfa1988/study/blob/master/resource/images/pattern/builder_pattern_uml_diagram.jpg)   


### 原型模式
参考[设计模式](https://www.tutorialspoint.com/design_pattern/prototype_pattern.htm)，[设计模式解密（18）- 原型模式](https://www.cnblogs.com/JsonShare/p/7300124.html)   
代码目录：[包地址](https://github.com/zengfa1988/study/blob/master/src/main/java/com/study/patterns/prototypePattern)   
原型模式（Prototype Pattern）这种模式是实现了一个原型接口，该接口用于创建当前对象的克隆。当直接创建对象的代价比较大时，则采用这种模式。例如，一个对象需要在一个高代价的数据库操作之后被创建。我们可以缓存该对象，在下一个请求时返回它的克隆，在需要的时候更新数据库，以此来减少数据库调用。结构图如下：  
![](https://github.com/zengfa1988/study/blob/master/resource/images/pattern/prototype_pattern_uml_diagram.jpg)   

### 适配器模式
参考[设计模式](https://www.tutorialspoint.com/design_pattern/adapter_pattern.htm)   
代码目录：[包地址](https://github.com/zengfa1988/study/blob/master/src/main/java/com/study/patterns/adapterPattern)   
适配器模式（Adapter Pattern）是作为两个不兼容的接口之间的桥梁。这种类型的设计模式属于结构型模式，它结合了两个独立接口的功能。结构图如下：  
![](https://github.com/zengfa1988/study/blob/master/resource/images/pattern/adapter_pattern_uml_diagram.jpg)   
