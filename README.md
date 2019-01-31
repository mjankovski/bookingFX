# bookingFX
Instrukcja konfiguracji projektu BookingFX dla środowiska IntelliJ IDEA:
1) W folderze resources należy edytować pliki konfiguracyjne poczty e-mail "mail.properties" oraz bazy danych "dbconfig.properties"(Wprowadzić dane logowania).
2) Należy pobrać plugin do IntelliJ o nazwie "Project Lombok"
	->File->Settings->Plugins->Lombok Plugin
3) Należy włączyć "Annotation Processing"(dla poprawnego działania pluginu Lombok)
	->File->Settings->Build, Execution, Deployment->Compiler->zaznaczyć checkbox "Enable annotation processing"

============================================

Desktop application(using Spring and JavaFX) that is a booking system for a hotel. Creating accounts(integrated with database), user's authorization, creating reservations, generating factures to pdf, getting realtime euro exchange rate(by RestAPI), admin account(from which you can manage all users and reservations).//winter 2018
