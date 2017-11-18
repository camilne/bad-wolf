#!/bin/sh

cd ../bad-wolf-launcher
rm -rf build
mkdir build
cd build

echo "Manifest-Version: 1.0" > MANIFEST.MF
echo "Main-Class: TitleScreen.TitleScreen" >> MANIFEST.MF

cd ../src
find ./ -name "*.java" > sources.txt
javac -d ../build @sources.txt
rm sources.txt
cd ../build
jar cvfm bad-wolf-launcher.jar MANIFEST.MF TitleScreen/*.class

rm -rf TitleScreen
rm MANIFEST.MF

cp ../src/TitleScreen/*.fxml .
cp -r ../resources/* .

cd ../../bad-wolf

/bin/bash gradlew desktop:dist

rm -rf build
mkdir build

cp -r ../bad-wolf-launcher/build/* build

cd build

echo "#!/bin/bash" > run.sh
echo "java -jar bad-wolf-launcher.jar" >> run.sh

cp ../desktop/build/libs/desktop-1.0.jar bad-wolf.jar
cp -r ../core/assets/* .
