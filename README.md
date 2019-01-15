# bookingFX
Instrukcja konfiguracji projektu BookingFX dla środowiska IntelliJ IDEA:
1) W folderze resources należy edytować pliki konfiguracyjne poczty e-mail "mail.properties" oraz bazy danych "dbconfig.properties"(Wprowadzić dane logowania).
2) Należy pobrać plugin do IntelliJ o nazwie "Project Lombok"
	->File->Settings->Plugins->Lombok Plugin
3) Należy włączyć "Annotation Processing"(dla poprawnego działania pluginu Lombok)
	->File->Settings->Build, Execution, Deployment->Compiler->zaznaczyć checkbox "Enable annotation processing"
