import subprocess
import os
import traceback

def decompiler_apk(chemin_apk: str, dossier_sortie: str) -> str:
    try:
        # 1. Nettoyage des chemins (on retire les guillemets ou espaces en trop)
        chemin_apk = chemin_apk.strip(' "\'')
        dossier_sortie = dossier_sortie.strip(' "\'')

        chemin_absolu_apk = os.path.abspath(chemin_apk)
        chemin_absolu_sortie = os.path.abspath(dossier_sortie)
        
        # 2. Création proactive du dossier de sortie par Python (fini les erreurs de dossier inexistant)
        os.makedirs(chemin_absolu_sortie, exist_ok=True)

        if not os.path.exists(chemin_absolu_apk):
            return f"Erreur : Le fichier APK est introuvable à l'adresse {chemin_absolu_apk}"

        # 3. Augmentation de la RAM pour Jadx
        mon_environnement = os.environ.copy()
        mon_environnement["JAVA_OPTS"] = "-Xmx4g"
        
        # 4. Formatage de la commande sous forme de chaîne unique (plus stable sur Windows avec shell=True)
        commande = f'jadx -d "{chemin_absolu_sortie}" "{chemin_absolu_apk}"'
        
        resultat = subprocess.run(commande, capture_output=True, text=True, shell=True,
            env=mon_environnement
        )

        if resultat.returncode != 0:
            return f"Erreur Jadx (Code {resultat.returncode}): {resultat.stderr} | {resultat.stdout}"

        return f"Succès : L'APK a été décompilé dans le dossier : {chemin_absolu_sortie}"

    except Exception as e:
        # 5. Si Python plante, on attrape l'erreur et on l'envoie à Claude !
        erreur = traceback.format_exc()
        return f"Erreur du script Python : {e}\nTrace complète :\n{erreur}"



def lire_fichier(chemin_fichier: str, debut: int = 0) -> str:
    
    try:
        if not os.path.exists(chemin_fichier):
            return f"Erreur : Le fichier '{chemin_fichier}' n'existe pas."
            
        with open(chemin_fichier, 'r', encoding='utf-8', errors='replace') as fichier:
            # On place le curseur de lecture au bon endroit (offset)
            fichier.seek(debut)
            
            # On lit notre tranche de 15 000 caractères
            limite = 15000
            contenu = fichier.read(limite)
            
            # On vérifie secrètement s'il y a encore du texte après
            char_suivant = fichier.read(1)
            
        # Si on n'est pas à la fin du fichier, on indique à Claude comment lire la suite
        if char_suivant:
            prochain_debut = debut + limite
            message = contenu + f"\n\n... [FICHIER TRONQUÉ : Tu as lu les caractères de {debut} à {prochain_debut}. "
            message += f"Pour lire la suite, rappelle cet outil avec l'argument debut={prochain_debut}]"
            return message
            
        return contenu
        
    except Exception as e:
        return f"Erreur critique lors de la lecture : {e}"