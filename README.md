CMaker
======

## **CMake plugin for IntelliJ Idea CE**

This is a CMake support plugin for IntelliJ Idea IDE. It brings syntax highlight support for CMake build and run system.
It is my play project to study intellij language support.


## **License**

Plugin is open-source software and is licenced under GPL v3 licence.

## **Versions**

**v.0.0.1**
* Supports basic syntax highlight, folding, gutter icons and psi structure.

## **Build Instruction**

* To get started you need the IntelliJ Plugin development SDK, *Grammar-Kit* plugin (to build parser and lexer from bnf grammar)

* Download the code, set Plugin SDK and Java SDK

* In project properties set the `plugin.xml` location
* Hover the `grammar/cmake.bnf` and generate parser code, jflex lexer.
* In project properties mark gen as source folder
* Generate lexer class from `*.flex` file
* Build project and you are set to go
