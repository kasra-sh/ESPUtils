# ESPUtils
[![](https://jitpack.io/v/kasra-sh/esputils.svg)](https://jitpack.io/#kasra-sh/esputils)
### A collection of components which make java easy (again ?).
### > How to import
Include these in your project's build.gradle file
```java
repositories {
	...
	maven { url 'https://jitpack.io' }
}
```
```java
dependencies {
	...
	compile 'com.github.kasra-sh:esputils:0.9.5'
}
```
####
## 1. { Eson }
#### { Easy, Fast, Lightweight JSON library }
#### Features :
 - ##### A combination of Gson and org.json features (EsonObject/EsonArray/ObjectMapping).
 - ##### Easy to use methods Just annotate the fields (or not !).
 - ##### Easily map JSON to complex data classes containing other classes or even Generic Lists of objects!
 - ##### Fluently generate and transfer data from *EsonObject*/*EsonArray*/*POJO*s to *String*(serialize) and back(deserialize)!
#### TODO :
- ##### Adding more serializers for more data types (Date, Set, ...)

## 2. { EString }
#### A wrapper around `StringBuilder` with buffer-like abilities and more ...
- ##### Contains default StringBuilder functions plus : `insert()`, `seekTo()`, `seekBefore()`, `seekAfter()`, `skip()`, `rewind()`, `peekNext()` and more functions to come ... !

## 3. { EventMan }
#### An easy way to pass objects/events between classes
 - ##### Can be used instead of *UGLY* callbacks or for passing objects in multithreaded applications!
 - ##### Define subscribers by ObjectType/Tag/Id, Tag and Id can be used even without passing an object for just posting an event!
