#importation de la bibliotheque scapy et des modules necessaires DNS IP et DNSQR pour le filtrage base64 pour le decodage
from scapy.all import rdpcap, DNS, IP, DNSQR
import base64

paquets= rdpcap("incident_2026-06-11.pcap")
#L'ip cible (celle qui envoie les requetes dns tunneling)
ip_cible= "192.168.10.66"
print(len(paquets), "paquets au total")
paquets[0].show()

#le dictionnaire morceaux permet de stocker dans l'ordre les morceaux des requetes dns 
morceaux = {}

for pkt in paquets:
    #On verifie si le paquet est un paquet dns avec une ip et si l'ip correspond à l'ip cible et si il sagit d'une question au serveur dns
    if pkt.haslayer(DNS) and pkt.haslayer(IP) and pkt[IP].src== ip_cible and pkt.haslayer(DNSQR):
        #Extraction de la question au dns(qname) et convervion en texte lisible byte string to string avec utf-8
        requete= pkt[DNSQR].qname.decode('utf-8')
        #On cherche le nom de domaine vers lequel l'attaquant exfiltre ses données si ce nom est dans la requete (DNSQR) 
        if "metrics-sync.cloudstat-analytics.io" in requete:
            #si oui on split la requete à chaque point et on garde la partie à l'indice 0 dans le numéro et le code 
            code_w_couche= requete.split(".")[0]
            #puisque le numéro est séparé du code avec un tiret et qu'on veut juste le code on decoupe code_w_couche en 2 partie[0] avec num et [1] avec code           
            if "-" in code_w_couche:
                code_clear= code_w_couche.split("-", 1)
                #conversion en entier du numéro 
                numero = int(code_clear[0])
                
                donnee_b64 = code_clear[1]
                # dict clé => valeur
                morceaux[numero] = donnee_b64

#chaine de caractere vide pour stocker le message entier 
code_complet = ""

#on boucle sur la clé numero du dict morceaux pur recupérer dans l'ordre les nombres (sorted) correspondant aux couches du message 
for i in sorted(morceaux.keys()):
    #on ajoute à chaque fois morceaux à code complet
    code_complet += morceaux[i]
# padding pour le module base64 qui attend une string dont la longueur est un mutilpe de 4
code_complet += "=" * (-len(code_complet) % 4)

# Encodage decodage avec url safe empêchant les caractères spéciaux (comme + ou / ) de casser la structure de l'url
resultat_final = base64.urlsafe_b64decode(code_complet)


print(resultat_final.decode('utf-8').strip("\n"))            
    
            


        

            
                





