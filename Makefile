run:
	mvn clean javafx:run

build:
	mvn clean package
	mvn clean compile assembly:single

package: build
	jpackage --name Sales-Trail --input target/ --main-jar sales-trail-1.0.0-jar-with-dependencies.jar --main-class com.example.app.App --module-path ./openjfx-23.0.1_linux-x64_bin-sdk/javafx-sdk-23.0.1/lib/ --add-modules javafx.controls,javafx.fxml

start: build
	java --module-path ./openjfx-23.0.1_linux-x64_bin-sdk/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml -jar ./target/sales-trail-1.0.0-jar-with-dependencies.jar
