# OLIVIA

> A Developer-Friendly "Open Lidar Visualizer and Analyser" for Point Clouds with 3D Stereoscopic View

## Table of Contents
- [Libraries](#libraries)
- [Install](#install)
- [Usage](#usage)
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
git clone https://github.com/citiususc/olivia
```
Then inside the root folder simply execute:
```bash
mvn package
```

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

## Authorship
Grupo de Arquitectura de Computadores (GAC)  
Centro Singular de Investifación en Tecnologías de la Información (CiTIUS)   
University of Santiago de Compostela (USC)   
Maintainers: @oscar.garcia @deuxbot

