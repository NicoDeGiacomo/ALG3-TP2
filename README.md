# Table of Contents
* [Instructions](#instructions)
* [Basic project structure](#basic-project-structure)
    * [Test classes nomenclature](#test-classes-nomenclature)
* [Instructions](#instructions)
    * [Build/test instructions](#buildtest-instructions)
    * [Running instructions](#running-instructions)
* [Continuous integration](#ci)

# Enunciado:
[Enunciado](https://docs.google.com/document/d/1OnD7ZRBIIZvv1snlR64WYj33abb-G3OODbTMaystsU8)

# Informe:
[Informe](https://www.overleaf.com/1212469133rwtksrpprgrt)

# Instructions:
[Instructions here!](https://docs.google.com/document/d/1OnD7ZRBIIZvv1snlR64WYj33abb-G3OODbTMaystsU8/edit#)

# Basic project structure
    - src: Main code
    - test: TestClases
    
### Test classes nomenclature:
If you need to test a class named _Foo_, create a class named _FooTests_.
This class will run automatically in each build.

# Instructions
### Build/Test instructions:
Install:
```
$ ant
```
Test and build:
```
$ ant integracion-continua
```
### Running instructions:
!TODO

# Continuous integration
Using [Travis CI](https://travis-ci.com/NicoDeGiacomo/ALG3-TP2) for continuous integration.

[![Build Status](https://travis-ci.com/NicoDeGiacomo/ALG3-TP2.svg?token=RnGp7rRLSaxceY1rovxE&branch=master)](https://travis-ci.com/NicoDeGiacomo/ALG3-TP2)

[![codecov](https://codecov.io/gh/NicoDeGiacomo/ALG3-TP2/branch/master/graph/badge.svg?token=IqboQTdsN6)](https://codecov.io/gh/NicoDeGiacomo/ALG3-TP2)
