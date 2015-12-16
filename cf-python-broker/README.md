## cf-standard-broker ##
Broker de service implementant les verbes obligatoires de l'api borker 2.8 de Cloud Foundry

http://docs.cloudfoundry.org/services/api.html#catalog-mgmt

Le module Flask est leverage activer le pattern MVC d'implementation des verbes decrits ci-dessus
L'application a ete testee sur CFv220+ avec le BP Python de la communaute

fguichard@bosh-cli:~$ cf create-service-broker python-broker toto titi http://monserveur:monport

cf enable-service-access python-broker
