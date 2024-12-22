# Serveur HTTP Java

Ce projet est un serveur HTTP développé en Java, avec une interface graphique permettant de configurer les paramètres du serveur. Il supporte également l'exécution de scripts PHP, en fonction des configurations fournies.

---

## ⚙️ Fonctionnalités

- **Démarrage et arrêt du serveur HTTP** via une interface utilisateur.
- **Modification des paramètres de configuration** (port et dossier `htdocs`) directement dans l'application.
- **Support PHP** (si activé via la configuration).
- Journalisation des connexions dans une console intégrée.

---

## 📁 Structure des fichiers

- **`htdocs`** : Le dossier racine utilisé pour servir les fichiers. Assurez-vous que tous les fichiers nécessaires, y compris les icônes, sont placés ici.
  - 📂 `icons` : Contient les icônes nécessaires au bon fonctionnement. **Ce dossier doit être dans le répertoire pointé par `htdocs` pour que les icônes s'affichent correctement.**

---

## 🔧 Instructions

### 1️⃣ Configuration

1. **Modifier les paramètres via l'interface :**
   - **Port** : Numéro de port sur lequel le serveur doit écouter.
   - **Htdocs** : Chemin vers le dossier contenant les fichiers à servir.
2. **Support PHP** :
   - Activez ou désactivez cette option via la case à cocher dans l'interface.

### 2️⃣ Démarrage du serveur

- Cliquez sur le bouton **Démarrer** pour lancer le serveur.

---

## ⚠️ Remarques importantes

1. **Icônes** :  
   Les icônes ne fonctionneront pas si le dossier `icons` n'est pas dans le répertoire `htdocs` spécifié.

2. **Changement de port** :  
   Tout changement de port nécessite un redémarrage du serveur pour être pris en compte.

---
