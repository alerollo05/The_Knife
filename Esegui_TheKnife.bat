@echo off
echo =====================================
echo Compilazione del progetto Maven...
echo =====================================

call .\mvnw.cmd clean package

IF ERRORLEVEL 1 (
    echo Errore durante la compilazione. Uscita...
    pause
    exit /b 1
)

echo =====================================
echo Esecuzione del programma...
echo =====================================

REM Percorso del JAR (modifica se il nome è diverso)
set JAR_PATH=target\the_knife-fat.jar

REM JavaFX module path (modifica se usi SDK FX esterni)
set JAVAFX_LIB_PATH="D:\Java\javafx-sdk-24.0.1\lib"

java --module-path %JAVAFX_LIB_PATH% --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -jar %JAR_PATH%

pause
