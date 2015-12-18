                                                                                                    
                                                                                                    
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

Ce processus a pour but de permettre de g�n�rer un �v�nement, actuellement via la g�n�ration d'un fichier, 
pour r�veiller notre syst�mre de monitoring.

## Installation

unzip du fichier pour avoir l'arborescence suvante:
		+---bin 
			+--- start.cmd: script de d�marrage du processus
		+---lib
			+--- javax.mail-1.5.4.jar: librairie n�cessaire pour l'utilisation du serveur de mail
			+--- MailNotifier.jar: librairie correspondant au processus 
		+---notice
			+--- *.notice : fichier g�n�r� lors del ar�ceptino d'une nouvelle notice

## Usage

Le processus actuel tourne en ""t�che de fond" au sens o� il interroge une bo�te mail avec une certaine 
fr�quence pour v�rifier:
	1/ est ce qu'il y a de nouveau mail non lu.
	2/ est ce que ces nouveau mails non lus correspondent � des crit�res de mails de notice
	3/ si ces mails matchent avec une r�gles de "notice" alors le processus g�n�re un fichier 
		<objet du mail>.notice avec comme contenu de fichier la date d'envoie, le from et le sujet.
	4/ passer le mail � lu

La "configuration" est actuellement pass�e en param�tre d'appel du processus, � savoir:
	MailListener [ServerHostName] [userId] [password] <refresh default10s>

La fr�quence de rafraichissement est par d�faut de 10 secondes, il faut ajouter ce param�tres optionnel 
pour modifier la valeur.

Dans le script de d�marrage "start.cmd" ces diff�rentes variables de configuration sont au d�but du fichier :
	set mailServer="zzzzzzzz"
	set userid="yyyyyy"
	set pwd="xxxxxxx"
	set refresh="10"

## TODO

 - Il faudrait externaliser la configuration
 - avoir des logs, ce qui n'est actuellement pas le cas, tout est "juste" dans la console.
 - ajouter la modification � la vol�e lors d'une r�ception URS des status EPP en base.
 - faire le script d�marrage aussi pour linux
 
## History

18/12/2015: premi�re version

