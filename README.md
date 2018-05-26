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
	compile 'com.github.kasra-sh:esputils:0.9.9'
}
```
####
## 1. { Eson }
#### { Easy, Fast, Lightweight JSON library }
#### Features :
 - A combination of Gson and org.json features (EsonObject/EsonArray/ObjectMapping).
 - Easy to use methods Just annotate the fields (or not !).
 - Easily map JSON to complex data classes containing other classes or even Generic Lists of objects!
 - Fluently generate and transfer data from *EsonObject*/*EsonArray*/*POJO*s to *String*(serialize) and back(deserialize)!
#### Usage
```java
// Serialize raw
String json = new EsonObject().put("test_value", "string").toString();

// Serialize object
String json = Eson.generate(myObject);

// Serialize with indent (prettify)
String jsonPretty = Eson.generate(myObject, 3);

// Deserialize raw
// Object
EsonObject object = Eson.wrap(myObject);
long l = object.getLong("long_value");

// Array
EsonArray array = Eson.wrap(myListOrArrayOfObjects);
EsonElement e = array.get(i);
long l = array.get(i).getObject().getLong("long_value");
...

// Deserialize and map to object
MyClass myObject = Eson.load(json, MyClass.class);

// Deserialize and map to arraylist
ArrayList<MyClass> myObjectList = Eson.loadArray(json, MyClass.class);

```
#### TODO :
- Adding more serializers for more data types (Date, Set, ...)

## 2. { EString }
#### A wrapper around `StringBuilder` with buffer-like abilities and more ...
- ##### Contains default StringBuilder functions plus : `insert()`, `seekTo()`, `seekBefore()`, `seekAfter()`, `skip()`, `rewind()`, `peekNext()` and more functions to come ... !

## 3. { EventMan }
#### An easy way to pass objects/events between classes
 - ##### Can be used instead of *UGLY* callbacks or for passing objects in multithreaded applications!
 - ##### Define subscribers by ObjectType/Tag/Id, Tag and Id can be used even without passing an object for just posting an event!
