from scapy.all import ARP, Ether, srp, conf,sniff
import csv
import argparse
from datetime import datetime

parser= argparse.ArgumentParser()
parser.add_argument("--scan", action= "store_true",help="construit la base de données de éférence")
parser.add_argument("--watch", action="store_true",help="surveille le reseau")
args= parser.parse_args()


if args.scan:
    interface_active=conf.route.route("192.168.56.102")[0]    
    cible= "192.168.56.0/24"
    requete= Ether (dst= "ff:ff:ff:ff:ff:ff") / ARP(pdst=cible)
    responses= srp(requete, timeout=2, verbose= False, iface= interface_active)[0]

    with open("base_donnéesARP1", "w" , newline="") as f:
        writer = csv.DictWriter(f, fieldnames= ["ip", "mac"])
        writer.writeheader()
   
        for envoye, recu in responses:
            print(recu.psrc, "->", recu.hwsrc)
            writer.writerow({"ip": recu.psrc, "mac": recu.hwsrc})


elif args.watch:
   
    base_de_reference={}

    with open("base_donnéesARP1", "r", newline="")as f:
         reader = csv.DictReader(f)
         for colonne in reader:
            base_de_reference[colonne["ip"]]= colonne["mac"]

    def ecrire_log(message):
        horodatage = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        with open("log_R.txt", "a", encoding="utf-8") as f:
            f.write(f"[{horodatage}] {message}\n")



    def analyser_paquet(pkt):
        print(pkt.summary())
        if pkt.haslayer(ARP):
            ip_paquet=pkt[ARP].psrc
            mac_paquet=pkt[ARP].hwsrc

            if ip_paquet in base_de_reference:
                mac_legit=base_de_reference[ip_paquet]


                if mac_paquet == mac_legit:
                    pass
                


                else:
                    alerte = f"Alerte Spoofing : L'ip {ip_paquet} utilise la fausse MAC {mac_paquet} (MAC attendue : {mac_legit})"
                    print(alerte,flush=True)
                    ecrire_log(alerte)
                

            else:
                alerte = f"Nouvel appareil : L'ip {ip_paquet} (MAC: {mac_paquet}) est inconnue."
                print(alerte,flush=True)
                ecrire_log(alerte)

    interface_active = conf.route.route("192.168.56.102")[0]
    print(f"Surveillance sur : {interface_active}")

    
    sniff(filter="arp", store=False, prn=analyser_paquet, iface= interface_active)

        
    

    
             


             
   
          
    
    






    



    

     
