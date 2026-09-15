from markdown_pdf import Section, MarkdownPdf
from datetime import datetime

def rapport_pdf(markdown_content: str, output_filename: str) -> str:
    try:
        # Sécurité : force l'extension .pdf si l'agent l'oublie
        if not output_filename.endswith('.pdf'):
            output_filename += '.pdf'

        pdf = MarkdownPdf(toc_level=2)
        
        # En-tête professionnel généré automatiquement
        header = f"# Rapport d'Audit de Sécurité Mobile\n"
        header += f"**Cible et Itération :** {output_filename.replace('.pdf', '')}\n"
        header += f"**Date d'exécution :** {datetime.now().strftime('%Y-%m-%d %H:%M')}\n"
        header += f"**Généré par :** Agent MCP (audit_android)\n"
        header += f"---\n\n"
        
        full_content = header + markdown_content
        
        pdf.add_section(Section(full_content))
        pdf.save(output_filename)
        
        return f"Succès : Le rapport PDF a été généré et sauvegardé sous le nom '{output_filename}' dans le répertoire courant."
    except Exception as e:
        return f"Échec de la génération du PDF : {str(e)}"