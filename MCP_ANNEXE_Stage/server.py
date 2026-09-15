from fastmcp import FastMCP
from modules import executer_commande_adb as action_commande 
from modules import connexion_verification as action_connexion
from modules import chemin_apk as action_chemin
from modules import extraire_apk as action_extraire
from modules import decompiler_apk as action_decompiler
from modules import lire_fichier as action_lire
from modules import analyse_dynamique as action_analyse_dynamique
from modules import rapport_pdf as action_rapport_pdf


mcp = FastMCP("server")

@mcp.tool()
def executer_commande_adb():
    """Exécute n'importe quelle commande sur le terminal local via une liste de mots"""
    return action_commande()


@mcp.tool()
def connexion_verification():
    """Vérifie si un appareil Android est correctement connecté via ADB"""
    
    return action_connexion()


@mcp.tool()

def chemin_apk(nom_package: str) -> str:
    """
        Trouve les chemins de stockage (base et fragments) d'une application installée.
        L'IA doit fournir le nom du package cible
        """
    
    return action_chemin(nom_package)

@mcp.tool()
def extraire_apk(chemin_apk : str,nom_fichier_local: str )-> str:
    """
        Extrait (télécharge) un fichier depuis le téléphone Android vers l'ordinateur local
        L'IA doit fournir le chemin complet du fichier sur le téléphone et le nom du fichier local 
        de destination sur le PC sous le format nom de l'application.apk (ex: instagram.apk) 
        """
       
    return action_extraire(chemin_apk, nom_fichier_local)

@mcp.tool()
def decompiler_apk(chemin_apk: str, dossier_sortie: str) -> str:
    """
        Décompile un fichier APK pour en extraire le code source java
        L'IA doit fournir le chemin du fichier APK à analyser et le dossier où sauvegarder le code
        """
    
    return action_decompiler(chemin_apk, dossier_sortie)

@mcp.tool()
def lire_fichier (chemin_fichier: str, debut: int=0) -> str:
    """
        Lit le contenu d'un fichier avec un système de pagination pour éviter les crashs.
        L'argument 'debut' permet de commencer la lecture à un caractère précis (offset).
        """

    return action_lire(chemin_fichier, debut)

@mcp.tool()
def analyse_dynamique(nom_app: str, code_js: str) -> str:
    """
    Injecte un script Frida dans une application Android.
    Le paramètre 'nom_app' est le nom du package cible (ex: 'owasp.sat.agoat').
    L'outil utilise la méthode spawn pour gérer automatiquement le lancement et l'interception, sans avoir besoin de PID.
    """
    return action_analyse_dynamique(nom_app, code_js)

@mcp.tool()
def rapport_pdf(markdown_content: str, output_filename: str) -> str:
    """
    Génère un rapport PDF professionnel à partir des résultats de l'audit.
    L'agent doit fournir le contenu structuré au format Markdown.
    
    CRITIQUE : L'agent DOIT générer dynamiquement la valeur de 'output_filename' 
    en utilisant le format strict suivant : [nom_app]_v[version]_[YYYY-MM-DD]_audit_[numero].pdf
    Exemple attendu : owasp.sat.agoat_v1.0_2026-09-18_audit_1.pdf
    """
    return action_rapport_pdf(markdown_content, output_filename)
    




















@mcp.resource("greeting://{name}")
def greeting(name: str) -> str:
    return f"Hello, {name}!"

if __name__ == "__main__":
    mcp.run()