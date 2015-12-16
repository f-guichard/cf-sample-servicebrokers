## cf-standard-broker ##
Broker de service implementant tous les verbes de l'api borker 2.8 de Cloud Foundry

http://docs.cloudfoundry.org/services/api.html#catalog-mgmt

Spring boot + Java 8 (Stream API) sont utilis√s pour impl√©mnenter le pattern MVC du broker
L'application a ete testee sur CFv220+ avec le BP Java de la communaute

Attention : si vous souhaitez/devez utiliser un manifest, je ne l'ai pas fourni avec l'application.

fguichard@bosh-cli:~$ cf create-service-broker java-broker toto titi http://monserveur:montport

cf enable-service-access java-broker
