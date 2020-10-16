#!/bin/bash
# Download the new JogAmp libs
url_path=v2.4.0-rc-20200307 # Change this for a newer version when available (https://jogamp.org/deployment/)
mkdir newlib
cd newlib
jars=( 
	gluegen-rt.jar
	gluegen-rt-natives-android-aarch64.jar
	gluegen-rt-natives-android-armv6.jar
	gluegen-rt-natives-linux-amd64.jar
	gluegen-rt-natives-linux-armv6.jar
	gluegen-rt-natives-linux-armv6hf.jar
	gluegen-rt-natives-linux-i586.jar
	gluegen-rt-natives-macosx-universal.jar
	gluegen-rt-natives-windows-amd64.jar
	gluegen-rt-natives-windows-i586.jar
	jogl-all.jar
	jogl-all-natives-android-aarch64.jar
	jogl-all-natives-android-armv6.jar
	jogl-all-natives-linux-amd64.jar
	jogl-all-natives-linux-armv6hf.jar
	jogl-all-natives-linux-i586.jar
	jogl-all-natives-macosx-universal.jar
	jogl-all-natives-windows-amd64.jar
	jogl-all-natives-windows-i586.jar
)
for i in "${jars[@]}"; do wget https://jogamp.org/deployment/${url_path=}/jar/$i; done
# Overwrite the old libs
v=2.3.2
mv gluegen-rt.jar gluegen-rt-$v.jar
for jar in gluegen-rt-*; do mv $jar ${jar//t-n/t-$v-n}; done
mv jogl-all.jar jogl-all-$v.jar
for jar in jogl-all-*; do mv $jar ${jar//l-n/l-$v-n}; done
cp * ../target/lib
cd ..
rm -rf newlib
