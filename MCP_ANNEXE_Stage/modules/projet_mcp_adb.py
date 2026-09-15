import os
import subprocess


def executer_commande_adb(commande_liste: list[str]) -> str:
    resultat=subprocess.run(commande_liste,capture_output=True,text = True )
    return resultat.stdout


def connexion_verification():
   
    resultat= subprocess.run(["adb", "devices"], capture_output= True,text= True) 
    lignes= resultat.stdout.splitlines()
    for ligne in lignes:
        if "\tdevice" in ligne:
            id_appareil= ligne.split("\t")[0]
            return f"Appareil connecté avec l'id {id_appareil}"
    return "Aucun appareil connecté vérifiez le cable"

def chemin_apk(nom_package: str) -> str:
    
    resultat= subprocess.run(["adb", "shell", "pm", "path", nom_package], capture_output=True, text=True)
    texte= resultat.stdout.strip()
    erreur= resultat.stderr.strip()
    if not texte: 
        return f"Auncun apk trouvé pour '{nom_package}'\n détail : {erreur}"
    return f"Chemins des fragments APK trouvés pour {nom_package} :\n{texte}"




def extraire_apk(chemin_apk: str, nom_fichier_local: str) -> str:
    chemin_apk = chemin_apk.replace("package:", "").strip()
    
    dossier_courant = os.path.dirname(os.path.abspath(__file__))
    chemin_absolu = os.path.join(dossier_courant, nom_fichier_local.strip())

    if os.path.exists(chemin_absolu):
        return (f"Le fichier a déjà été créé au chemin suivant {chemin_absolu}")

    resultat = subprocess.run(["adb", "pull", chemin_apk, chemin_absolu], capture_output=True, text=True)
    
    if not os.path.exists(chemin_absolu):
        return (f"Échec : Le fichier n'est pas présent.\n"
                f"Réponse de ADB : {resultat.stdout} {resultat.stderr}")
        
    return f"Succès : Fichier créé ici : {chemin_absolu}"