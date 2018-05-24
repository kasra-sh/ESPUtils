# ESPUtils
[![](https://jitpack.io/v/kasra-sh/esputils.svg)](https://jitpack.io/#kasra-sh/esputils)
###
### A collection of components which make java easy (again ?).
### Includes
- ## Eson
#### Easy, Fast, Lightweight JSON library
 - ##### Combination of Gson and org.json features (EsonObject/EsonArray/ObjectMapping)
 - ##### Easy to use(no factories needed !) methods . Just annotate the fields (or not !).
 - ##### Easily map JSON to complex data classes containing other classes or even Lists of objects!
 - ##### Fluently build and transfer data from *EsonObject*/*EsonArray*/*POJO*s to *String*(serialize) and back(deserialize) !
- ## EString
#### A wrapper around `StringBuilder` with buffer-like abilities and more ...
- ##### Contains default StringBuilder functions plus : `insert()`, `seekTo()`, `seekBefore()`, `seekAfter()`, `skip()`, `rewind()`, `peekNext()` and more functions to come ... !
- #### EventMan : An easy way to pass objects between classes
 - ##### Can be used instead of *UGLY* callbacks or for passing objects in multithreaded applications !
 - ##### Define subscribers by ObjectType/Tag/Id, Tag and Id can be used even without passing an object for just delivering an event !
