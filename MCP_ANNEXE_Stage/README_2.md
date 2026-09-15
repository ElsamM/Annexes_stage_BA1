Agent MCP de Pentest Mobile (audit_android)

Documentation technique et opérationnelle
Autrice : Elsa-Marie Moh
Application de validation : OWASP AndroGoat


1. Présentation du projet
Outil d'audit de sécurité Android basé sur le Model Context Protocol (MCP). Ce projet permet à un agent LLM d'orchestrer différentes phases d'analyse d'une application Android (APK), notamment l'extraction, la décompilation, l'analyse statique, l'instrumentation dynamique et la génération de rapports.
Contrairement à un script purement déterministe, cette architecture modulaire permet au modèle de sélectionner et d'enchaîner les outils disponibles en fonction du contexte de l'audit et des vulnérabilités découvertes. L'objectif est d'automatiser les tâches techniques répétitives tout en laissant à l'analyste l'interprétation des résultats et les décisions stratégiques.
Périmètre d'utilisation : cet outil est destiné aux environnements de test, aux applications de démonstration (CTF et défis de sécurité) et aux applications ou infrastructures pour lesquelles l'auditeur dispose d'une autorisation explicite.


2. Architecture technique
Interface MCP : serveur FastMCP enregistré sous le nom audit_android.
Transport : stdio, pour la communication avec Claude Desktop.
Orchestration : Python 3.
Agent LLM : Claude via Claude Desktop.
Analyse statique : JADX pour la décompilation et l'inspection du code décompilé et des ressources Android.
Analyse dynamique : Frida pour l'instrumentation à l'exécution (runtime) et le hooking de méthodes.
Génération de livrables : module de génération automatisée de rapports de pentest au format PDF.
Plateforme cible : Android.
Application de validation : OWASP AndroGoat (owasp.sat.agoat), version 1.0.


3. Prérequis et dépendances système
Les outils suivants doivent être installés sur la machine hôte et accessibles depuis la variable d'environnement PATH.
Outils requis
ADB (Android Debug Bridge) : communication avec l'émulateur, gestion des paquets et exécution de commandes shell.
JADX : décompilation des APK afin de faciliter l'analyse du code.
Frida : instrumentation dynamique de l'application Android.
Environnement validé lors des tests
Émulateur : Android Virtual Device (AVD) avec accès root.
Architecture : x86.
Version Android : API 25.
Frida côté hôte : version 16.3.3.
Frida-Server côté Android : version 16.3.3, architecture x86.
Compatibilité : l'environnement de référence utilise Frida 16.3.3 côté hôte et côté Android. Il est recommandé de conserver des versions compatibles, idéalement identiques, entre le client et le serveur.


4. Procédure de déploiement

4.1. Configuration de l'environnement Python
Les commandes suivantes sont prévues pour Windows avec PowerShell.
Créer l'environnement virtuel :
python -m venv .venv
Activer l'environnement :
.\.venv\Scripts\Activate.ps1
Installer les dépendances Python :
python -m pip install -r requirements.txt

4.2. Déploiement et configuration du pont Frida
Pour utiliser Frida via une redirection TCP locale, suivre les étapes suivantes.
Étape 1 : Vérifier la connexion ADB
Démarrer l'émulateur et vérifier qu'il est détecté :
adb devices
Étape 2 : Activer l'accès root
adb root
Cette commande suppose que l'image de l'émulateur autorise l'accès root.
Étape 3 : Déployer Frida-Server
Copier le binaire correspondant à l'architecture de l'émulateur :
adb push tools/frida-server-16.3.3-android-x86 /data/local/tmp/frida-server
Étape 4 : Attribuer les permissions d'exécution
adb shell "chmod 755 /data/local/tmp/frida-server"
Étape 5 : Lancer Frida-Server
Pour limiter l'écoute au loopback de l'émulateur :
adb shell "/data/local/tmp/frida-server -l 127.0.0.1:27042 &"
Étape 6 : Configurer la redirection TCP
Créer un transfert du port local de l'hôte vers le port correspondant de l'émulateur :
adb forward tcp:27042 tcp:27042
Étape 7 : Vérifier la connectivité
frida-ps -H 127.0.0.1:27042
Si la connexion est établie, Frida doit pouvoir retourner la liste des processus accessibles sur l'appareil.

4.3. Configuration du client Claude Desktop
Modifier le fichier claude_desktop_config.json afin d'enregistrer le serveur MCP.
Adapter les chemins absolus à l'emplacement réel du projet :
{
  "mcpServers": {
    "audit_android": {
      "command": "C:\\Chemin\\Vers\\Projet_MCP\\.venv\\Scripts\\python.exe",
      "args": [
        "C:\\Chemin\\Vers\\Projet_MCP\\server.py"
      ]
    }
  }
}
Redémarrer Claude Desktop afin de charger la configuration et d'initialiser le serveur MCP.


5. Workflow d'audit (SOP)
Le cycle d'exécution nominal de l'agent comprend les étapes suivantes :
Vérification de la connectivité : contrôle de la connexion ADB et identification de l'appareil cible.
Extraction de l'APK : récupération de l'application depuis l'appareil si le fichier n'est pas déjà disponible localement.
Décompilation : analyse de l'APK avec JADX.
Analyse du manifeste : inspection de AndroidManifest.xml afin de cartographier la surface d'attaque, notamment les composants exportés et les permissions.
Analyse du code : recherche de vulnérabilités potentielles dans le code décompilé, notamment dans la logique métier et la gestion du stockage.
Instrumentation dynamique : préparation d'un script JavaScript Frida pour examiner le comportement de l'application à l'exécution.
Validation : vérification des résultats des tests et des éventuels contournements de mécanismes de sécurité.
Génération du rapport : production d'un rapport PDF présentant les résultats, les preuves statiques et dynamiques et les méthodes de reproduction.


6. Exemple de directive initiale (prompt)
Le prompt suivant peut servir de point de départ pour lancer un audit dans un environnement autorisé.
Agis comme un auditeur en cybersécurité spécialisé dans l'analyse
d'applications Android.

Réalise un workflow d'audit de sécurité de l'application
owasp.sat.agoat dans un environnement de test autorisé.

1. Vérifie la connexion à l'appareil Android et récupère l'APK
   si celui-ci n'est pas déjà disponible localement.

2. Effectue une analyse statique du code décompilé afin d'identifier
   les vulnérabilités potentielles, notamment les mécanismes de
   détection de root, le stockage non sécurisé et les composants
   Android exportés.

3. Utilise Frida via la connexion TCP configurée pour examiner
   dynamiquement la logique de sécurité et vérifier les comportements
   identifiés pendant l'analyse statique.

4. Documente les résultats obtenus, les preuves techniques et les
   méthodes de reproduction des problèmes confirmés.

5. Génère le rapport d'audit PDF si le module de génération est
   disponible et opérationnel.
7. Arborescence du projet
Projet_MCP/
├── .venv/                              # Environnement virtuel Python
├── modules/                            # Modules logiques du serveur MCP
│   ├── __init__.py
│   ├── projet_mcp_adb.py               # Fonctions d'interaction ADB
│   ├── projet_mcp_frida.py             # Gestion de Frida et du transport TCP
│   ├── projet_mcp_jadx.py              # Logique de décompilation
│   └── projet_mcp_rapport.py           # Génération des rapports PDF
├── tools/                              # Binaires tiers
│   └── frida-server-16.3.3-android-x86 # Serveur Frida pour l'émulateur
├── server.py                           # Point d'entrée du serveur FastMCP
├── requirements.txt                    # Dépendances Python
└── README.md                           # Documentation du projet


8. Roadmap et améliorations futures
Plusieurs évolutions sont envisagées afin d'améliorer l'efficacité de l'agent, sa couverture d'analyse et la reproductibilité des audits.

8.1. Intégration d'outils d'analyse statique spécialisés
Constat : l'exploration manuelle des fichiers générés par JADX peut consommer un nombre important de tokens et augmenter le temps nécessaire à l'analyse.
Solution envisagée : développer un module MCP exposant des outils d'analyse statique automatisée, par exemple au moyen de wrappers autour de Semgrep ou de règles personnalisées de recherche.
L'agent pourrait lancer des recherches ciblées sur des motifs tels que AES, password ou addJavascriptInterface, puis récupérer directement les chemins des fichiers et les extraits pertinents.
Cette approche permettrait de réduire l'exploration manuelle, de mieux cibler le contexte transmis au modèle et de faciliter la vérification des résultats.

8.2. Extension de la couverture de l'analyse dynamique
Objectif : étendre les tests aux mécanismes de communication interprocessus Android (IPC).
Solution envisagée : ajouter des outils MCP permettant de construire et d'émettre des Intents Android à l'aide de commandes ADB, notamment adb shell am start et adb shell am broadcast.
Ces outils permettraient de tester, dans un environnement autorisé, les composants Activities, Services et BroadcastReceivers identifiés lors de l'analyse statique.

8.3. Automatisation du provisionnement de l'environnement
Objectif : réduire les étapes manuelles nécessaires à la préparation de l'environnement de test.
Solution envisagée : développer un script Python capable de :
Identifier l'architecture de l'appareil à l'aide de adb shell getprop ro.product.cpu.abi.
Sélectionner une version compatible de Frida-Server.
Automatiser le déploiement du binaire et la configuration de la redirection TCP.
Vérifier la connectivité avant de lancer l'audit.
Cette évolution permettrait de simplifier la préparation de l'environnement et d'améliorer la reproductibilité des tests.

8.4. Fiabilisation de la logique d'audit et gestion des faux positifs Objectif : Améliorer la rigueur des conclusions rendues par l'agent IA. Solution envisagée : Mettre en place un système de traçabilité des actions exécutées pour faciliter la vérification humaine. L'agent devra classer explicitement ses découvertes avec des niveaux de confiance (Vulnérabilité confirmée, Hypothèse, Faux positif potentiel) en croisant systématiquement l'analyse statique avec une preuve dynamique. 


 9. Troubleshooting et problèmes connus

**Processus Frida persistants (Zombies) bloquant les ressources réseau**
*   **Symptôme :** Échec de connexion lors d'une nouvelle exécution de l'audit ou impossibilité de lier la redirection TCP.
*   **Cause :** Lors des redémarrages successifs de l'environnement de test, des processus Frida peuvent persister en arrière-plan après l'arrêt du script Python, conservant ainsi des ressources réseau.
*   **Solution :** Mettre en œuvre un nettoyage systématique des processus et une remise en état de l'environnement entre deux exécutions. En cas d'arrêt brutal, redémarrer manuellement le processus `frida-server` sur l'émulateur via ADB.

**Erreurs de chemins de fichiers sous Windows (Génération du rapport)**
*   **Symptôme :** Échec de la génération du rapport PDF final ou fichiers APK/JADX introuvables par le système.
*   **Cause :** Difficultés liées à la gestion et à l'interprétation des chemins de fichiers relatifs par le LLM sous un environnement Windows.
*   **Solution :** Adapter les instructions de l'agent (le prompt système/SOP) pour privilégier systématiquement des chemins absolus explicites.
