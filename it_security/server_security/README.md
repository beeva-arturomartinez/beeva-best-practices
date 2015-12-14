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
* For the security procedures in relation to cloud operations in AWS, search for the document named “”.
* For credential rotation in AWS, search for the document named “”.
* For securing your laptop during development and daily testing, search for the document named “”.
* For company wide security procedures and policies please check the ISO 27000 standard family: https://en.wikipedia.org/wiki/ISO/IEC_27000


## Credential management

### Credenciales 

**¿Qué es una credencial?**

Según [wikipedia](https://es.wikipedia.org/wiki/Credencial "wikipedia"), es lo siguiente:

“Una credencial es una orden o un documento que atestigua o autoriza la cualificación, competencia o autoridad otorgada a un individuo por un tercero con autoridad de iure o de facto. Su fin es que se dé posesión al empleado de su plaza (o al estudiante de sus cursos o de su grado), sin perjuicio de obtener luego el título correspondiente.”

En este caso, vamos a dedicar el siguiente texto a las buenas prácticas que debemos realizar sobre las passwords.

Para ello, hemos de saber, ¿cuánto tiempo se necesita para “reventar” una password?; el siguiente gráfico lo muestra claramente.

![alt text](/static/crack.png "passwords")


Con esta imagen, podemos hacer el siguiente resumen, la contraseña mínimo tiene que tener los siguientes requisitos:

- **Longitud mínima de 8 caracteres**
- **Mayúsculas**
- **Minúsculas**
- **Números**
- **Caracteres especiales (_-!@.....)**

Sólo con eso, se tardaría en conseguir nuestra password 2.10 centurias, en vez de 2.42 días si solo se hiciera con minúsculas.

Cuando tienes un servidor o un login, es importante controlar esto, para ellos usaremos pam.d y el fichero sshd_config.


### Configuración de passwords en servidores. 

PAM.D, son unos ficheros de configuración para autenticación de aplicaciones, por ejemplo RHEL lo tiene activo por defecto, pero en otros sistemas como Ubuntu, puede utilizarse.

Antiguamente, se configuraba en /etc/pam.conf y actualmente es en /etc/pam.d/

Ahora, hablaremos de un ejemplo de configuración en el pam.d que nos bloquea el usuario si falla la autenticación y el mínimo de configuración de contraseña permitido. Mas abajo, indicamos el significado de esta configuración, basado en colores.

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
    

Explicaremos las líneas, por colores.

Azul - Con estas líneas indicamos, que si el usuario intenta acceder 5 veces y le da acceso denegado, al sexto acceso bloqueará al usuario durante 30 minutos, esto nos ayuda a evitar ataques de fuerza bruta.

Rojo - Aquí forzaremos que el usuario ingrese passwords complicadas, mínimo de 8 caracteres, 1 mayúscula, 1 minúscula y 1 decimal. Además se recordarán las últimas 5 passwords cambiadas y se cifrarán en sha512. Por último, la línea “password requisite pam_passwdqc.so min=disabled,disabled,16,12,8” indica en base a los caracteres de la password, que requisitos tiene que cumplir; en nuestro caso, si la password es inferior a 12 caracteres, nos obligará a incluir un caracter especial.

Os dejamos mas información sobre esta configuración en los siguientes links.

[https://peterpap.net/index.php/Enforcing_Password_Complexity_on_CentOS/RedHat](https://peterpap.net/index.php/Enforcing_Password_Complexity_on_CentOS/RedHat)

[http://linux.die.net/man/8/pam_passwdqc](http://linux.die.net/man/8/pam_passwdqc)


## Public Key Infrastructure (PKI) solutions


## Network security


## Server bastioning

### Actualizaciones

Una pregunta que todo el mundo ha de hacerse, es la siguiente **¿Cuándo he de actualizar/parchear mis sistemas?**

Nuestra recomendación, es crear una política de actualizaciones, que cada X tiempo, se realicen sobre dichos sistemas. Una política puede ser, actualizar todos los sistemas cada 3 meses.

Esta política, siempre tiene que tener excepciones, es decir, si surge una vulnerabilidad grave como HeartBleed o Poodle, esa actualización debe ser inmediatamente; por lo que tenemos que incluir en nuestra política un control sobre la criticidad de las vulnerabilidades que aparecen.

Otro ejemplo podría ser la actualización del kernel, que trae consigo actualizaciones de seguridad; habría que revisar que criticidad tienen y decidir si se aplica inmediatamente, a corto plazo (crear una ventana solo para esa actualización) o con la siguiente actualización trimestral.

### Las actualizaciones de software. 

Normalmente, cuando usamos un software tipo tomcat, apache, etc.. su actualización  puede llevar a problemas en nuestros aplicativos o webs. Por lo que no recomiendo su inclusión en la política trimestral, pero si una política que pruebe nuevas versiones, vea si afecta al aplicativo o web y si todo continúa correctamente, instalar la actualización.

Respecto a esto, es muy importante que intentemos tener la misma versión del software en todos nuestros sistemas y entornos.

### Certificados SSL/TLS

Siempre es positivo utilizar certificados SSL para cualquier acceso web, pero no siempre es necesario, ya que hay que tener en cuenta los recursos que consume la máquina al cifrar/descifrar el tráfico.

Por lo que vamos a indicar, en qué casos hay que utilizar SSL/TLS:

- Para proteger cualquier tipo de transacción online, por ejemplo, pagar con tarjeta (en este caso, sería obligatorio).
- Proteger cualquier tipo de login o acceso a una plataforma, web, etc..
- Al mostrar cualquier dato personal de un usuario; correo teléfono, etc…
- Para la transmisión de ficheros.

Para estos casos, recomendamos el uso de SSL/TLS.

Especialmente, si tenemos accesos vía HTTPS, podemos utilizar herramientas para verificar y comprobar el estado y configuración de nuestro certificado.

En Beeva, nos gusta utilizar una servicio de Qualys, que comprueba varias cosas relacionadas con SSL/TLS; además, una vez finalizada la revisión, te da una puntuación, dónde F es lo mas bajo y A+ lo mas alto.

[https://www.ssllabs.com/ssltest/](https://www.ssllabs.com/ssltest/)

Lo mas positivo de esta revisión, es que si detecta un fallo o vulnerabilidad en tu configuración, te proporciona diferentes links para poder solucionarlo.

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



## Data encryption


## Security audits


## Incident response

Toda empresa ha de disponer de un protocolo de respuesta en base a un incidente de seguridad. En Beeva, este protocolo de actuación se entrega al empleado al entrar a la empresa y se vuelve a revisar anualmente.

Cualquier empleado de Beeva puede descargarlo de la intranet:

[Normativa de Seguridad de la Información](https://intranet.beeva.com/wp-content/uploads/2015/08/Normativa-de-seguridad-de-la-informaci%C3%B3n_5.0.pdf)

Si no se dispone de un procedimiento y quiere implementar uno, ha de empezar por conocer [RBAC](https://en.wikipedia.org/wiki/Role-based_access_control "RBAC").

RBAC, es una jerarquía de roles, pensados para indicar la responsabilidad de una persona/departamento/cargo al acceder a un sistema y cuál sería su actuación frente a problemas o incidentes. 
