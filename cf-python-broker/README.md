## cf-standard-broker ##
Broker de service implementant les verbes obligatoires de l'api borker 2.8 de Cloud Foundry

http://docs.cloudfoundry.org/services/api.html#catalog-mgmt

Le module Flask est leverage activer le pattern MVC d'implementation des verbes decrits ci-dessus
L'application a ete testee sur CFv220+ et sur Diego avec le BP Python de la communaute

fguichard@bosh-cli:~$ cf create-service-broker python-broker toto titi http://monserveur:monport

```
$ cf service-access
$ cf enable-service-access <mon_service>
```

Le repertoire vendor contient le module flask et le dependance enfin de permettre
d'etre installe sur un runner/cell offline :

```
$ pip install --download vendor -r requirements.txt
```

Pour Python3 :

```
$ pip3 download --dest vendor -r requirements.txt
$ sudo pip3 install -r requirements.txt --no-index --find-links file:////`pwd`/vendor 
```

