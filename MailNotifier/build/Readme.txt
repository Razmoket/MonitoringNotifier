                                                                                                    
                                                                                                    
               AAA                  ffffffffffffffff                      iiii                      
              A:::A                f::::::::::::::::f                    i::::i                     
             A:::::A              f::::::::::::::::::f                    iiii                      
            A:::::::A             f::::::fffffff:::::f                                              
           A:::::::::A            f:::::f       ffffffnnnn  nnnnnnnn    iiiiiii     cccccccccccccccc
          A:::::A:::::A           f:::::f             n:::nn::::::::nn  i:::::i   cc:::::::::::::::c
         A:::::A A:::::A         f:::::::ffffff       n::::::::::::::nn  i::::i  c:::::::::::::::::c
        A:::::A   A:::::A        f::::::::::::f       nn:::::::::::::::n i::::i c:::::::cccccc:::::c
       A:::::A     A:::::A       f::::::::::::f         n:::::nnnn:::::n i::::i c::::::c     ccccccc
      A:::::AAAAAAAAA:::::A      f:::::::ffffff         n::::n    n::::n i::::i c:::::c             
     A:::::::::::::::::::::A      f:::::f               n::::n    n::::n i::::i c:::::c             
    A:::::AAAAAAAAAAAAA:::::A     f:::::f               n::::n    n::::n i::::i c::::::c     ccccccc
   A:::::A             A:::::A   f:::::::f              n::::n    n::::ni::::::ic:::::::cccccc:::::c
  A:::::A               A:::::A  f:::::::f              n::::n    n::::ni::::::i c:::::::::::::::::c
 A:::::A                 A:::::A f:::::::f              n::::n    n::::ni::::::i  cc:::::::::::::::c
AAAAAAA                   AAAAAAAfffffffff              nnnnnn    nnnnnniiiiiiii    cccccccccccccccc
                                                                                                    
					  __  __       _ _   _   _       _   _  __ _           
					 |  \/  |     (_) | | \ | |     | | (_)/ _(_)          
					 | \  / | __ _ _| | |  \| | ___ | |_ _| |_ _  ___ _ __ 
					 | |\/| |/ _` | | | | . ` |/ _ \| __| |  _| |/ _ \ '__|
					 | |  | | (_| | | | | |\  | (_) | |_| | | | |  __/ |   
					 |_|  |_|\__,_|_|_| |_| \_|\___/ \__|_|_| |_|\___|_|   
																		   
# Project Name

Ce processus a pour but de permettre de générer un événement, actuellement via la génération d'un fichier, 
pour réveiller notre systèmre de monitoring.

## Installation

unzip du fichier pour avoir l'arborescence suvante:
		+---bin 
			+--- start.cmd: script de démarrage du processus
		+---lib
			+--- javax.mail-1.5.4.jar: librairie nécessaire pour l'utilisation du serveur de mail
			+--- MailNotifier.jar: librairie correspondant au processus 
		+---notice
			+--- *.notice : fichier généré lors del aréceptino d'une nouvelle notice

## Usage

Le processus actuel tourne en ""tâche de fond" au sens où il interroge une boîte mail avec une certaine 
fréquence pour vérifier:
	1/ est ce qu'il y a de nouveau mail non lu.
	2/ est ce que ces nouveau mails non lus correspondent à des critères de mails de notice
	3/ si ces mails matchent avec une règles de "notice" alors le processus génère un fichier 
		<objet du mail>.notice avec comme contenu de fichier la date d'envoie, le from et le sujet.
	4/ passer le mail à lu

La "configuration" est actuellement passée en paramètre d'appel du processus, à savoir:
	MailListener [ServerHostName] [userId] [password] <refresh default10s>

La fréquence de rafraichissement est par défaut de 10 secondes, il faut ajouter ce paramètres optionnel 
pour modifier la valeur.

Dans le script de démarrage "start.cmd" ces différentes variables de configuration sont au début du fichier :
	set mailServer="zzzzzzzz"
	set userid="yyyyyy"
	set pwd="xxxxxxx"
	set refresh="10"

## TODO

 - Il faudrait externaliser la configuration
 - avoir des logs, ce qui n'est actuellement pas le cas, tout est "juste" dans la console.
 - ajouter la modification à la volée lors d'une réception URS des status EPP en base.
 - faire le script démarrage aussi pour linux
 
## History

18/12/2015: première version

