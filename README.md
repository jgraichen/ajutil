A Java Utility Libary
=====================

Collection of some java utilities I've used in some of my projects some time ago.

Released on request for [cmur2](https://github.com/cmur2).

Features
--------

* a powerful asset loading system (including caching and different loaders) *that relies on*
* a flexible resource locating system (supports classpath, absolute files, URLs, compressed archives) - because the *where* and *what* of an asset shouldn't depend on each other.  
* an easy to use translation/i18n system (allows multiple storage formats e.g. XML of translation resources) 
* a task scheduling system
* some system utilites (native loader, single App instance detection, CLI parser, system infos)
* a pure Java, improved 3D Perlin noise implementation

Dependencies
------------

* [Log4J](https://logging.apache.org/log4j/1.2/) 1.2.16
* [JDOM](http://www.jdom.org/) 1.1.2

(Note: if you are familiar with Apache Ivy and/or want automate the dependency download you can simply use `ant resolve`.)

License
-------

Licensed under Apache 2.0 License for non-commercial use.
See LICENSE for more information.
