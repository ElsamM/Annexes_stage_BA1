import frida
import sys
import subprocess

sessions_frida_actives = {}

def gestion_de_message(message, donnees):
    if message['type'] == 'send':
        log = f"[FRIDA LOG] {message['payload']}"
        print(log, file=sys.stderr)
        with open("frida_logs.txt", "a", encoding="utf-8") as f:
            f.write(log + "\n")
    elif message['type'] == 'error':
        err = f"[FRIDA ERROR] {message['description']}"
        print(err, file=sys.stderr)
        with open("frida_logs.txt", "a", encoding="utf-8") as f:
            f.write(err + "\n")

def analyse_dynamique(nom_app: str, code_js: str) -> str:
    try:
        subprocess.run(["adb", "forward", "tcp:27042", "tcp:27042"], capture_output=True)
        appareil = frida.get_device_manager().add_remote_device("127.0.0.1:27042")
        
        try:
            appareil.kill(nom_app)
        except Exception:
            pass
            
        pid = appareil.spawn([nom_app])
        session = appareil.attach(pid)
        
        script = session.create_script(code_js)
        script.on("message", gestion_de_message)
        script.load()
        
        appareil.resume(pid)
        
        sessions_frida_actives[pid] = (session, script)
        return f"Succès : application spawnée et script injecté dans {nom_app} (PID {pid})."
        
    except Exception as e:
        return f"Erreur lors de l'injection Frida : {str(e)}"