@echo off

cd ..\bad-wolf-launcher
RD /S /Q build
mkdir build
cd build

@echo Manifest-Version: 1.0 > MANIFEST.MF
@echo Main-Class: TitleScreen.TitleScreen >> MANIFEST.MF

cd ..\src
javac -d ..\build TitleScreen\*.java
cd ..\build
jar cvfm bad-wolf-launcher.jar MANIFEST.MF TitleScreen\*.class

RD /S /Q TitleScreen
DEL MANIFEST.MF

for /R "..\src\TitleScreen" %%f in (*.fxml) do copy "%%f" "."
xcopy /s "..\resources" "."

cd ..\..\bad-wolf

CALL ./gradlew.bat desktop:dist

RD /S /Q build
mkdir build

xcopy /s "..\bad-wolf-launcher\build" "build"

cd build

@echo @echo off > run.bat
@echo java -jar bad-wolf-launcher.jar >> run.bat

COPY "..\desktop\build\libs\desktop-1.0.jar" "bad-wolf.jar"
xcopy /s "..\core\assets" "."

