# point-cloud-viewer

> Visualisation tool for LiDAR point clouds

## Table of Contents
- [Libraries](#libraries)
- [Install](#install)
- [Usage](#usage)
- [Bugs](#bugs)
- [Authorship](#authorship)

## Libraries
The tool uses the following libraries:
- JOGL (Java Binding for the OpenGL API) for rendering.
- JavaCV for video recording. 
- Substance for Graphite look and feel

## Install

### From sources
Download the source code from this web site or from git (requieres a CiTIUS account):
```bash
git clone https://gitlab.citius.usc.es/lidar/point-cloud-viewer
```
Then inside the root folder simply execute:
```bash
mvn package
```
### From JAR
Download the precompiled JAR from the lastest [tag](https://gitlab.citius.usc.es/lidar/point-cloud-viewer/tags).

## Usage
Open a terminal inside the JAR folder and run:  
`java -jar point-cloud-viewer.jar`  
To launch with stereoscopic 3D enabled use:  
`java -jar point-cloud-viewer.jar -stereo`  
To launch a specified visualisation and file use:  
`java -jar point-cloud-viewer.jar -visutype path`    
Example:  
`java -jar point-cloud-viewer.jar -classifier data/classifier`   
Current supported `-visutypes` are: `-basic -neighbours -segmenter -classifier`  
This can be used as well with `-stereo` argument:  
`java -jar point-cloud-viewer.jar -stereo -classifier data/classifier`   

## Bugs
1. Point selection.  
When the points are not centered in (0,0,0) it does not work well, there is a problem with `gluUnProject()`, the ray in no longer well drawn. Maybe due to the points coordinates beign large or something; there was a bug on `gluUnProject()` back in 2012 but should now be fixed. For now just center the points.   
2. Depth Test does not work on Intel GPUs (nor does Point Smooth, transparency OK)

## Authorship
Grupo de Arquitectura de Computadores (GAC)  
Centro Singular de Investifación en Tecnologías de la Información (CiTIUS)   
University of Santiago de Compostela (USC)   
Maintainers: @oscar.garcia @jorge.martinez.sanchez