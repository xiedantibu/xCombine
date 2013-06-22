## xCombine 介绍：
* xCombine 是一个 android 的插件框架。
* xCombine 遵循简单易用，易扩展，约定大于配置的思想开发。


## 框架的约定：
* 插件分为 *容器(Container)* 和 *模块(Module)* 两类(继承自Plugin)。
* Module 的实现类必须命名为：packageName + ".CustomModule"。
* Container 随意命名，并在主界面初始化(参考tests/MainContainer)。
* Container 和 Module 必须使用相同的 android:sharedUserId。


## 使用方法：
* 插件之间互通可使用 *插件消息* 和 *MOP*(可以叫它面向方法编程)
* 使用 *插件消息* 时消息遵守接受者的实现约定，适用同步或非同步返回的场景。
* 使用 *MOP* 定义一个外部插件实现的方法时，方法名和参数类型与外部插件保持一致，返回值类有多个时变为List，适用同步返回场景。
* *MOP* 可用在 Module， Container 和 初始化Container的Activity 中(用法参考tests)。
* 上面的介绍在tests中有详细的示例，包含插件之间传递对象和View组件的例子。


## 注意：
* Module依赖xCombine, 但不要将xCombine编译进Module。
* 需要在AndroidManifest.xml为主界面Activity添加下面的intent-filter：

```xml
<intent-filter>
    <action android:name="android.intent.action.PACKAGE_ADDED"/>
    <action android:name="android.intent.action.PACKAGE_REMOVED"/>
    <data android:scheme="package"/>
</intent-filter>
```


## 关于：
Author： wyouflf
Email ： <wyouflf@gmail.com>
如有疑问或者建议可以发邮件给我，稍后我会参考大家的问题补充 *README* 和 修改代码。


----------------------------------------------------------------------------


## xCombine Introduction：
* xCombine is a plugin framework of android.
* xCombine comply with the principle: easy, extendable, convention over configuration.


## Convention of Framework：
* Plugins can be divided into *Container* & *Module* (extends Plugin)。
* The impl class of Module must be named：packageName + ".CustomModule"。
* The impl class of Container can be named casually，and init it in the main Activity.(reference: tests/MainContainer).
* Container & Module must have the same android:sharedUserId.


## Usage：
* Plugins can communicate with each other by *PluginMessage* & *MOP*(Method Oriented Programming).
* When using *PluginMessage*, you should comply with the convention of the receiver. It's suitable to sync & non-sync situation.
* When using *MOP* define an external method from other Plugin, method name and types of args keep same with the other Plugin. It's suitable to sync.
* *MOP* could be used by Module, Container and the main Activity(reference: tests).


## Attention：
* Module dependence on xCombine, but don't compile xCombine code in Module.
* need add the following intent-filter in AndroidManifest.xml

```xml
<intent-filter>
    <action android:name="android.intent.action.PACKAGE_ADDED"/>
    <action android:name="android.intent.action.PACKAGE_REMOVED"/>
    <data android:scheme="package"/>
</intent-filter>
```


## About：
Author： wyouflf
Email ： <wyouflf@gmail.com>