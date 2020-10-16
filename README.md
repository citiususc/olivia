[![Build Status](https://travis-ci.org/travis-ci/travis-web.svg)](https://travis-ci.org/citiususc/olivia)

# OLIVIA

> A Developer-Friendly "Open Lidar Visualiser and Analyser" for Point Clouds with 3D Stereoscopic View

![This link](https://nextcloud.citius.usc.es/index.php/s/qzCazCaLeQJZbjd/preview)

## Table of Contents
- [Install](#install)
- [Usage](#usage)
- [Authorship](#authorship)

## Install
First install a Java JDK and Maven.
```bash
sudo apt install openjdk-14-jdk maven
```
Then download the source code:
```bash
git clone https://github.com/citiususc/olivia
```
Inside the root folder run:
```bash
mvn package
```
Finally, upgrade JogAmp to the lastest version:
```bash
chmod +x upgrade-jogamp.sh
./upgrade-jogamp.sh
```

The executable *Olivia.jar* will be generated inside the *target* folder.

## Usage
Open a terminal inside the JAR folder and run:  
`java -jar Olivia.jar`  
To launch with stereoscopic 3D enabled use:  
`java -jar Olivia.jar -stereo`  
To launch with detached render windows with an internal desktop use:  
`java -jar Olivia.jar -detachedD`  
To launch with detached render windows, one for each visualisation, use:  
`java -jar Olivia.jar -detachedI`  

## Authorship
Grupo de Arquitectura de Computadores (GAC)  
Centro Singular de Investifación en Tecnologías de la Información (CiTIUS)   
University of Santiago de Compostela (USC)   
Maintainers: [@OscGz](https://github.com/OscGz) [@deuxbot](https://github.com/deuxbot)

This software is presented in the following paper:

J. Martínez, O. G. Lorenzo, D. L. Vilariño, T. F. Pena, J. C. Cabaleiro and F. F. Rivera, "A Developer-Friendly “Open Lidar Visualiser and Analyser” for Point Clouds With 3D Stereoscopic View," in _IEEE Access_, vol. 6, pp. 63813-63822, 2018.  
doi: 10.1109/ACCESS.2018.2877319 [http://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=8502756&isnumber=8274985](http://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=8502756&isnumber=8274985)
