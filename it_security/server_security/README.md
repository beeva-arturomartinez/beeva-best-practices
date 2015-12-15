# IT Security

## Content index

* [Introduction](#introduction)
* [Credential management](#credential-management)
* [Public Key Infrastructure solutions](#public-key-infrastructure-(PKI)-solutions)
* [Network security](#network-security)
* [Server bastioning](#server-bastioning)
* [Back up and disaster recovery](#back-up-and-disaster-recovery)
* [Monitoring](#monitoring)
* [Vulnerabilities](#vulnerabilities)
* [Data encryption](#data-encryption)
* [Security audits](#security-audits)
* [Incident response](#incident-response)


## Introduction

Security is one of the pillars of the IT industry nowadays, and as such it needs to be introduced in many of the current company processes, developments and deployments.

This best practices guide provides an overview of the main topics regarding security practices for IT server environments. It will therefore provide guidance on the main topics that need to be taken into account when securing server solutions, by presenting the main tools that are recommended and by forwarding the reader to the appropriate standards or recommendations where relevant.

### NOTE:

If you are looking for one of the following topics, please follow the provided instructions in each case:

* For developing secure code check [this other](../../it_security/security_hardening/README.md) best practices guide form Beeva.
* For security best practices in AWS, please check: http://media.amazonwebservices.com/AWS_Security_Best_Practices.pdf
* For company wide security procedures and policies please check the ISO 27000 standard family: https://en.wikipedia.org/wiki/ISO/IEC_27000


## Credential management

### Credenciales 

**¿Qué es una credencial?**

Según [wikipedia](https://es.wikipedia.org/wiki/Credencial "wikipedia"), es lo siguiente:

“Una credencial es una orden o un documento que atestigua o autoriza la cualificación, competencia o autoridad otorgada a un individuo por un tercero con autoridad de iure o de facto. Su fin es que se dé posesión al empleado de su plaza (o al estudiante de sus cursos o de su grado), sin perjuicio de obtener luego el título correspondiente.”

En este caso, vamos a dedicar el siguiente texto a las buenas prácticas que debemos realizar sobre las passwords.

Para ello hemos de saber, ¿cuánto tiempo se necesita para “reventar” una password?; el siguiente gráfico lo muestra claramente.

![alt text](static/crack.png?raw=true "passwords")


Con esta imagen, podemos hacer el siguiente resumen, la contraseña mínimo tiene que tener los siguientes requisitos:

- **Longitud mínima de 8 caracteres**
- **Mayúsculas**
- **Minúsculas**
- **Números**
- **Caracteres especiales (_-!@.....)**

Sólo con eso, se tardaría en conseguir nuestra password 2.10 centurias, en vez de 2.42 días si solo se hiciera con minúsculas.

Cuando tienes un servidor o un login es importante controlar esto, para ellos usaremos pam.d y el fichero sshd_config.


### Configuración de passwords en servidores. 

PAM.D, son unos ficheros de configuración para autenticación de aplicaciones, por ejemplo RHEL lo tiene activo por defecto, pero en otros sistemas como Ubuntu, puede utilizarse.

Antiguamente, se configuraba en /etc/pam.conf y actualmente es en /etc/pam.d/

Ahora, hablaremos de un ejemplo de configuración en el pam.d que nos bloquea el usuario si falla la autenticación y el mínimo de configuración de contraseña permitido. Mas abajo, indicamos el significado de esta configuración, basado en colores (esta información es una imagen, debajo de la explicación encontrarñan el código que pueden utilizar en sus ficheros).

![alt text](static/pamd_color.png?raw=true "pamd")


Explicaremos las líneas, por colores.

**Azul** - Con estas líneas indicamos, que si el usuario intenta acceder 5 veces y le da acceso denegado, al sexto acceso bloqueará al usuario durante 30 minutos, esto nos ayuda a evitar ataques de fuerza bruta.

**Rojo** - Aquí forzaremos que el usuario ingrese passwords complicadas, mínimo de 8 caracteres, 1 mayúscula, 1 minúscula y 1 decimal. Además se recordarán las últimas 5 passwords cambiadas y se cifrarán en sha512. Por último, la línea “password requisite pam_passwdqc.so min=disabled,disabled,16,12,8” indica en base a los caracteres de la password, que requisitos tiene que cumplir; en nuestro caso, si la password es inferior a 12 caracteres, nos obligará a incluir un caracter especial.

***vim /etc/pam.d/system-auth***

    ##%PAM-1.0
    # This file is auto-generated.
    # User changes will be destroyed the next time authconfig is run.
    authrequired  pam_env.so
    authsufficientpam_unix.so nullok try_first_pass
    authrequisite pam_succeed_if.so uid >= 500 quiet
    auth   required pam_faillock.so preauth audit silent deny=5 unlock_time=1800
    auth   [success=1 default=bad] pam_unix.so
    auth   [default=die] pam_faillock.so authfail audit deny=5 unlock_time=1800
    auth   sufficient pam_faillock.so authsucc audit deny=5 unlock_time=1800
    authrequired  pam_deny.so

    account required  pam_unix.so
    account sufficientpam_localuser.so
    account sufficientpam_succeed_if.so uid < 500 quiet
    account required  pam_permit.so

    passwordrequisite pam_cracklib.so try_first_pass retry=3 type=
    password required pam_cracklib.so try_first_pass retry=3 minlen=8,dcredit=-1,ucredit=-1,lcredit=-1
    password requisite pam_passwdqc.so min=disabled,disabled,16,12,8
    passwordsufficientpam_unix.so sha512 shadow nullok try_first_pass use_authtok
    password sufficient pam_unix.so remember=5
    passwordrequired  pam_deny.so

    session optional  pam_keyinit.so revoke
    session required  pam_limits.so
    session [success=1 default=ignore] pam_succeed_if.so service in crond quiet use_uid
    session required  pam_unix.so




Os dejamos mas información sobre esta configuración en los siguientes links.

[https://peterpap.net/index.php/Enforcing_Password_Complexity_on_CentOS/RedHat](https://peterpap.net/index.php/Enforcing_Password_Complexity_on_CentOS/RedHat)

[http://linux.die.net/man/8/pam_passwdqc](http://linux.die.net/man/8/pam_passwdqc)


## Public Key Infrastructure (PKI) solutions

Public Key Infrastructure is the set of entities and elements that when applied on internet solutions allows customer to:

1. Trust the other end in relation to the identity that it advertises.
2. Secure the communication between both parties so that no one having intercepted the communication is capable of understanding or modifying the message.

In this regard, Transport Layer Security (TLS) is the industry standard for communication encryption procedures for web related services. TLS is the evolution of SSL (Secure Socket Layer), and they are usually referred as TLS / SSL or even SSL. Most commonly but not only, TLS provides encryption for the HTTP protocol. This is the one used under HTTPS connections.

In this section, recommended practices are presented for all the aspects that have to do with the PKI area.

### On which services to protect

Depending on the characteristics of the service it might or might not be required to be protected. In general, the following points can be used as guidance for deciding which areas or services to protect, where the use is recommended:

* For online transactions.
* For any function that is not public and is login protected.
* For any personal data that is displayed.
* For file transfers.
* When the nature of the site requires the client to be protected.

On the contrary, when general information is made generally available it is not required and it can save a server side computational resources.

### Protocol version

The following are the guidelines for selecting the protocol version for a solution:

* SSL in any of its versions is fully deprecated.
* The recommended protocol is __TLS 1.2__.
* Please __make sure__ that the solution does not allow clients to negotiate any SSL version. TLS implementations were backwards compatible and this support has been dropped due to security issues.

### Certificate hashing algorithm

There are different hashing algorithms available to generate certificates:

* Certificate of the SHA 1 family.
* Certificate of the SHA 2 family.

Information points:

* SHA 1 algorithms are expected to be removed along 2016.
* Web browsers are currently displaying warnings for some certificates under this conditions.

The __recommended approach__ is to use:

* A SHA2 family certificate with at least 256 bit length key for new deployments.
* Existing SHA1 certificates should be gradually replaced until Q1 2016, beginning with the most critical services.

### Certificate deployment options

Server certificates can be deployed in different locations:

* Server: Deploy the certificate in the web server.
* Load balancer: The certificate is deployed in the load balancer and the traffic sent to the instances behind is sent in clear from this point.

In order to select a location please take into account:

* Installing the certificate in the load balancer is made in a single point, whereas in servers it needs to be replicated, which might introduce errors.
* The computational load generated by the encryption algorithms is very relevant. Additionally, virtualisation solutions do not perform cryptographic primitives very well since they are quite CPU intensive. For high load applications, the load balancer approach is recommended.
* In the case of the load balancer, a final communication is left cryptographically unprotected, although it might be physically secured. Care must be taken so that such traffic cannot be reached from other areas of the network.

### Autosigned certificates

It is possible to generate autosigned certificates, that is, when the CA is managed by the same company as the server. These type of certificates are not recommended in any case for production or open trial use. They should only be used for private trials where that is the only way of enabling TLS and the other option is not use ciphering.

### Certificate verification

When exposing HTTPS services it is possible to use tools to verify the status and configuration of deployed certificates.

As an example, Qualys provides a service that allows testing several certificate related parameters:

https://www.ssllabs.com/ssltest/

Once the revision is complete a grade is assigned A+ to F. The recommended value to be obtained is at least an A. In case of vulnerability detection useful information is provided in order to fix it.

## Network security

One key aspect of server security is the access to specific ports of machines. In order to prevent unauthorised access firewalls need to be used.

The use of firewalls of any kind is __absolutely necessary__ for internet reachable instances. In general, all traffic,

If a hardware firewall is not available, a good first step can be to make use of software firewalls. In Linux's case, it is possible to use the Kernel's netfilter module in order to block traffic. This module allows introducing rules to the networking stack of the Kernel and allows to perform operations on packets as the arrive, leave or they are forwarded through. Both the rules and the and the solution are usually referred to as IPtables, and they are available since Kernel 2.4 series. However a successor has been already introduced [nftables](https://en.wikipedia.org/wiki/Nftables).

For bastioning a host (please see next section), the following operations are the ones to be performed:

* As a default, block all incoming traffic.
* Provide access to service ports only.
* Limit the rate of calling to certain hosts.
* Create blacklists (origins whose packets are dopped) to be dynamic.
* Protect against known attacks such as SYN flooding and SYN spoofing.


## Server bastioning

Bastioning is the procedure of protecting a server to convert it into a bastion. In general, the clean installation of an operating system does not provide the required security guarantees for production operation. Hence, a set of changes __needs to be made__ for in order to protect the host.

The following is an overview of the tasks that need to be performed in order to obtain a bastion host:

* __Update all services and apply security patches:__ This will try to remove already known and therefore open security holes.
* __Protect the networking of the server with a firewall:__ This will drop all packets to ports not offering service or from unauthorised origins. It might be recommended to use some throttling solution in some cases, which blacklists the requester for some time in order to preven brute force and Denial os Service (DoS) attacks.
* __Remove unnecessary users from the host:__ As an example, if there is a user for the printer and the server is not a printing server, that user needs to be removed. The less accounts available the better in order to avoid permission escalations.
* __Set aggressive perimission schemes for users and services:__ Users should only be capable of the minimum when connecting to the system. That way, intentional or unintentional damages, and permission escalations are limited.
* __Configure the services to be offered by the server, with the most strict secure options:__ For example, if SSH is one of the services to be offered, disable tcp forwarding. A user should not be able to reach ports that are protected by the firewall.


### Actualizaciones

Una pregunta que todo el mundo ha de hacerse, es la siguiente **¿Cuándo he de actualizar/parchear mis sistemas?**

Nuestra recomendación, es crear una política de actualizaciones que cada X tiempo, se realicen sobre dichos sistemas. Una política puede ser, actualizar todos los sistemas cada 3 meses.

Esta política, siempre tiene que tener excepciones, es decir, si surge una vulnerabilidad grave como HeartBleed o Poodle, esa actualización debe ser realizada  inmediatamente; por lo que tenemos que incluir en nuestra política un control sobre la criticidad de las vulnerabilidades que aparecen.

Otro ejemplo podría ser la actualización del kernel, que trae consigo actualizaciones de seguridad; habría que revisar que criticidad tienen y decidir si se aplica inmediatamente, a corto plazo (crear una ventana solo para esa actualización) o con la siguiente actualización trimestral.

### Las actualizaciones de software. 

Normalmente, cuando usamos un software tipo tomcat, apache, etc.. su actualización  puede llevar a problemas en nuestros aplicativos o webs. Por lo que no recomiendo su inclusión en la política trimestral, pero si una política que pruebe nuevas versiones, vea si afecta al aplicativo o web y si todo continúa correctamente, instalar la actualización.

Respecto a esto, es muy importante que intentemos tener la misma versión del software en todos nuestros sistemas y entornos.

### Configuración SSH

Junto con el PAM.D, tenemos que configurar nuestro fichero sshd_config (en RHEL) para securizar nuestro acceso a las máquinas.

Una configuración básica que recomendamos, sería la siguiente:

***vim /etc/ssh/sshd_config***

- ClientAliveInterval 900  -  Cierra la sesión del usuario después de 15 minutos de inactividad.

- Ciphers aes128-ctr,aes192-ctr,aes256-ctr - Cifrado permitido por ssh

- Banner /path/del/banner  - cuando alguien acceda a las máquinas, le aparecerá el banner, normalmente se pone información que indique la prohibición de acceso a las máquinas si no estás autorizado, algo del tipo:

*“ALERT! You are entering into a secured area! Your IP, Login Time, Username has been noted and has been sent to the server administrator!
This service is restricted to authorized users only. All activities on this system are logged.
Unauthorized access will be fully investigated and reported to the appropriate law enforcement agencies.”* 

Otra recomendación, es cambiar el puerto de acceso a ssh y así evitar el ataque directo al puerto 22.

Cambiaríamos

**port 22**

por

**port 8222**

Por último, hablaremos de la rotación de passwords, se recomienda un cambio de passwords cada 90 días, esta rotación, se realizará modificando el fichero ***/etc/login.defs*** y modificaremos la siguiente línea:

    PASS_MAX_DAYS   90

Si hemos creado usuarios anteriormente, ejecutaremos el siguiente comando:

    chage -d 0 usuario

Esto obligará al usuario a cambiar la password en el siguiente login y entrará en la rotación de passwords cada 90 días.

Os indicamos un link de otros usos que se le puede dar al comando **chage**.

[http://rm-rf.es/comando-chage-tiempo-de-vida-de-claves-y-usuarios-en-gnulinux/](http://rm-rf.es/comando-chage-tiempo-de-vida-de-claves-y-usuarios-en-gnulinux/)


## Back up and disaster recovery

Any solution can suffer failure any time and at any level. Those failures, can result in a potential data loss which might not be possible to recover. In order to prevent these kind of situations, different prevention schemes are used. Two are the main areas covered by the prevention plan or scheme:

* Data safety and back up.
* Disaster recovery.

The former covers how to replicate the relevant data that should not be lost in any case, and the latter focuses on both the process of planning against possible contingencies and restoring the solution once a contingency actually happens.

### Data safety and back up

Relevant data __needs to be backed up regularly__ in order to prevent data loss upon failure. Failures should be taken for granted in every situation and backups are the solution for avoiding many relevant issues.

#### Data safety

The first point to take into account is to ensure that data is correctly stored. Currently, the loss of business critical data can affect the performance of a company on the short and long terms, therefore the __main storage of data needs to be redundant__. That is, the same data needs to be replicated in several disks at the same time. This way, data can be preserved from some usual hardware failures. Being replicated, it is possible to restore the lost copy from the other ones.

#### Regular backups

Even with redundant storages, larger failures still happen, and therefore __regular backups are required for business critical data__. It is recommended that backups are kept in a separate location from the main one in order to avoid disasters to affect both sets (main and backup) at the same time.

In general, depending on the relevance of data, backups do not necessarily need to be enforced. For example, some kind of logs might not be critical and losing them might not affect business. Similarly, the periodicity of backups also depends of the data's relevance.

### Disaster recovery (DR)

As introduced earlier, disaster recovery covers two main areas:

* Contingency planning
* Solution recovery

This area not only focuses in data but has a much wider view and it usually includes machines, networking, outages, fire, natural disasters etc.

For DR within AWS there is the following whitepaper available: http://media.amazonwebservices.com/AWS_Disaster_Recovery.pdf

#### Contingency planning

There are a number of possible issues that can happen and for which the solution needs to plan against. The following are the main ones:

* Hardware failures.
* Software failures.
* Power outages.
* 3rd party supplier failure.
* Fire.
* Natural disasters.

For each relevant case, a plan needs to be made, so that the solution is resilient against it and it can be recovered in a fixed amount of time. Cost is another relevant factor in this case. 

Usually, the disaster recovery proposal of a solution tries to make a balance between:

* Point in time recovery: The amount of data that is lost or not available when the solution is recovered.
* Recovery time: The time it takes to bring the solution back to work.
* The percentage of downtime.
* Cost.
* Complexity.

#### Recovering

Both in the case of on-premise or in cloud solutions when a failure or a disaster (which could be a chain of failures) occurs, the recovery procedure needs to be followed. 

Once the incident is resolved, it is good practice to perform an analysis of:

* The facts that have lead to the situation.
* How the process has been conducted.
* If both the solution, or the procedures can be improved for the future.


## Monitoring

Siempre que se habla de la monitorización, pensamos en el estado de un servidor, CPU, disco, memoria, etc…

En la seguridad hay otros tipos de monitorización, en este caso nos vamos a centrar en la detección de intrusos y eventos de seguridad.

Para ello, existen los IDS (Sistema de detección de instrusos), que se divide en 2 categorías, HIDS y NIDS. En este documento nos vamos a centrar en el HIDS, pero os dejamos unos “tips” sobre los NIDS.

**NIDS** 

Es un IDS basado en red, normalmente son unos “sniffers” de tráfico que pueden vigilar tanto el tráfico entrante, saliente o el tráfico local.

Para ello, se puede instalar desde “appliances” de marcas comerciales, o software en uno de nuestros servidores.

También existe software Open Source como Snort ([https://www.snort.org](https://www.snort.org))  o Suricata ([http://suricata-ids.org/](http://suricata-ids.org/)) , perfectos para comenzar en el mundo de los IDS y tener un entorno seguro y controlado.

Como pareja a estos IDS, podemos añadir Snorby ([https://github.com/Snorby/snorby](https://github.com/Snorby/snorby)) , una aplicación web, hecha en ruby, que facilita y nos muestra gráficas del tráfico capturado por nuestros IDS.

**HIDS**

Los HIDS son IDS para Hosts, es decir, se instalan en nuestros servidores y en base a logs, eventos, modificaciones, etc… nos reportan si en nuestro sistema ha sucedido algo que atente contra la integridad de nuestro sistema, tanto a nivel de ficheros, accesos y red.

En este documento, vamos a hablar de OSSEC ([http://ossec.github.io/](http://ossec.github.io/)) , un HIDS muy potente sin costo con licencia GNU.

En Beeva, ya comentamos como hacer la instalación ([https://www.beeva.com/beeva-view/sistemas/instalando-ossec-como-detector-de-intrusos-en-el-host/](https://www.beeva.com/beeva-view/sistemas/instalando-ossec-como-detector-de-intrusos-en-el-host/)) , por lo que no vamos a centrarnos en esa información.

Para el buen funcionamiento de Ossec, lo ideal es instalar agentes en todas las máquinas y centralizarlo en un único servidor Ossec, que además puede llevar instalada una web, para visualizar de forma más cómoda la información.


Una vez instalado, Ossec lo primero que hace, es** verificar el checksum** de unas rutas ya definidas en su configuración; ¿esto qué significa? Que al realizar su escaneo o en tiempo real, si hay alguna modificación de esos ficheros, se nos enviará un correo indicando lo que ha pasado.

Si por ejemplo tenemos un servidor web, al que le hemos instalado un agente de Ossec, podemos iniciar su **“respuesta activa”** y esto hará que revisando los logs de **Mod_security y Apache/Nginx**, si detecta un ataque en base a sus reglas, bloqueará esa IP, denegando el acceso desde ella.

Esta respuesta activa, puede ser peligrosa si no está bien configurada y no nos aseguramos de que tenemos el **X-Forwarded-Fo**r activado, ya que podría bloquear una IP interna (p.e: un balanceador) en vez de la IP cliente y provocar un corte de servicio de cara a internet.

Ossec también revisa los eventos de seguridad como accesos SSH, sudo, cambio de permisos, etc…

Es decir, cada vez que una persona accede al servidor, se nos enviará un mail comunicando esta acción. Si esa persona realiza un sudo, pasará exactamente lo mismo y si además cambia los permisos de una ruta que tenemos vigilada por Ossec, este también nos informará.

Aparte de todo esto, en Ossec podemos crear nuestras propias reglas y podría avisarnos de información que ocurriera en nuestros logs que fuera de nuestro interés, por ejemplo, un “null pointer” en nuestra aplicación de Java.

Es por toda la información que Ossec maneja y puede entregarnos, aparte de su alta configuración, que pensamos que es una herramienta imprescindible para cualquier entorno.


## Vulnerabilities

**¿Cómo estar al día de las vulnerabilidades?**

Salen vulnerabilidades nuevas diariamente y es muy complicado estar completamente al día, es por ello que os vamos a hacer unas recomendaciones para que vuestros entornos estén seguros de la forma más sencilla posible.

Listas de correo:

Consideramos importantísimas 3 listas de correo, por ellas pasan prácticamente todas las vulnerabilidades de casi todo el software; estas son:


[https://public.govdelivery.com/accounts/USDHSUSCERT/subscriber/new](https://public.govdelivery.com/accounts/USDHSUSCERT/subscriber/new)

De los EEUU, especialmente está basado en vulnerabilidades de marcas como Microsoft, RHEL, y vulnerabilidades con criticidad alta, además, envían un resumen semanal.

[http://www.securityfocus.com/archive/1/description](http://www.securityfocus.com/archive/1/description)

Bugtraq, de las más importantes, sobre vulnerabilidades y 0-days.

[http://nmap.org/mailman/listinfo/fulldisclosure](http://nmap.org/mailman/listinfo/fulldisclosure)

Full Disclosure, la lista por excelencia, tuvieron un problemilla en 2014 y fue cerrada, pero Fyodor, creador de Nmap, la volvió a abrir.

Tanto Bugtraq como Full Disclosure, tienen bastante movimiento de correos, por lo que os recomendamos su filtrado y tenerlo todo centralizado.

Otra forma, es acudir a blogs especializados, por ejemplo:

[http://unaaldia.hispasec.com/](http://unaaldia.hispasec.com/)

En este blog siempre nos contarán una vulnerabilidad nueva.

[http://blog.segu-info.com.ar/](http://blog.segu-info.com.ar/)

Reúnen diariamente información de seguridad de diferentes webs tanto en castellano como en inglés. Normalmente las traducen al castellano.


## Data encryption at rest

In this section, a basic overview of the data encryption needs available is made.

Data stored in servers is as sensitive as protecting access to the root account of a server. In a similar way, data is protected when on transit and it should be protected when it is at rest or stored. In order to protect the data from from being accessed when a disk fails and it is removed from the server or if is stolen, data should be encrypted. This actually depends on the security measures in place in the datacenter. In cloud providers, data encryption at rest is already provided and it should be used when possible. 

Since the inclusion of data encryption schemes introduce another point of failure, disaster recovery policies need to consider it as well. It is possible for a key to be lost and then the data protected with that key to be unrecoverable.

Data encryption also applies to backups since it does not make sense to have data protected and to leave the backup of that very same data unprotected. Hence and as a general rule, the same level of encryption should be applied to data in production and to its active replicas and backups.


## Security audits

When a solution is to be deployed and the final configuration is already designed it is good practice to heavily attack a solution clone in order to identify potential vulnerabilities. Once in production, this task should be perfomed with caution so that the solution is not affected. This task is one of which is usually understimated and which might leave attackers an open door if room is left for a simple attack to be performed.

Beyond attacking solutions, it is also necessary to perform audits periodically in order to detect deficiencies. The outcome of audits might impact on deployed solutions or even or company processes.

In general, security is a periodic job more than a one time task. Therefore, it is necessary define periodic company processes to evaluate and improve it.


## Incident response

Toda empresa ha de disponer de un protocolo de respuesta en base a un incidente de seguridad. En Beeva, este protocolo de actuación se entrega al empleado al entrar a la empresa y se vuelve a revisar anualmente.

Cualquier empleado de Beeva puede descargarlo de la intranet:

[Normativa de Seguridad de la Información](https://intranet.beeva.com/wp-content/uploads/2015/08/Normativa-de-seguridad-de-la-informaci%C3%B3n_5.0.pdf)

Si no se dispone de un procedimiento y quiere implementar uno, ha de empezar por conocer [RBAC](https://en.wikipedia.org/wiki/Role-based_access_control "RBAC").

RBAC, es una jerarquía de roles, pensados para indicar la responsabilidad de una persona/departamento/cargo al acceder a un sistema y cuál sería su actuación frente a problemas o incidentes. 
