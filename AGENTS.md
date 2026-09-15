# DarTrack — Instructions obligatoires pour les agents

## 1. Autorité de ce document

Ce fichier définit le périmètre fonctionnel et technique autorisé du projet DarTrack.

Toutes les instructions de ce fichier sont obligatoires pour chaque agent qui lit, crée, modifie, teste ou analyse le code du projet.

Règle principale : **ne jamais effectuer de travail hors du périmètre explicitement demandé par l'utilisateur.**

Une fonctionnalité techniquement utile, moderne, recommandée ou facile à ajouter ne devient pas automatiquement autorisée.

En cas de doute :

1. Ne pas modifier le code concerné.
2. Expliquer brièvement le point ambigu.
3. Demander l'accord explicite de l'utilisateur.
4. Attendre sa réponse avant de poursuivre.

## 2. Présentation du projet

Nom provisoire : **DarTrack**.

DarTrack est un MVP destiné aux Marocains résidant à l'étranger (MRE) qui souhaitent suivre à distance les travaux de rénovation d'un logement situé au Maroc.

Le produit doit apporter :

- de la visibilité sur l'avancement des travaux ;
- une traçabilité des étapes et des décisions ;
- un suivi simple du budget et des dépenses ;
- des preuves organisées sous forme de photos et de documents ;
- la possibilité de valider ou de refuser une étape de travaux.

DarTrack n'est pas une application immobilière généraliste. Ce n'est ni un portail d'annonces, ni une agence immobilière, ni une plateforme de location, ni un logiciel de syndic.

## 3. Périmètre actuel du MVP

La première version est un **prototype local pour un seul utilisateur propriétaire**.

L'unique rôle fonctionnel autorisé actuellement est :

- `OWNER` : le propriétaire MRE qui suit son chantier.

Pour tester le concept, le propriétaire peut lui-même saisir les mises à jour, dépenses, photos et décisions. La gestion de comptes artisans, administrateurs ou autres collaborateurs n'est pas incluse dans cette version.

Le parcours essentiel à démontrer est :

1. Le propriétaire crée un projet de rénovation.
2. Il ajoute et organise les étapes des travaux.
3. Il met à jour l'avancement d'une étape.
4. Il ajoute une photo, un document ou une dépense comme preuve.
5. Il place l'étape en attente de décision.
6. Il valide ou refuse l'étape.
7. Le budget, l'avancement et l'historique sont actualisés.

Tout développement doit servir directement ce parcours.

## 4. Fonctionnalités autorisées

### 4.1 Compte propriétaire

- Un seul compte propriétaire local.
- Connexion simple si l'authentification fait partie de la tâche demandée.
- Consultation et modification des informations essentielles du profil.
- Aucun système complet de gestion multi-utilisateur.

### 4.2 Projet de rénovation

Un projet peut contenir :

- nom ;
- description ;
- type de bien ;
- adresse ;
- ville au Maroc ;
- budget initial en MAD ;
- date de début ;
- date de fin estimée ;
- statut ;
- pourcentage d'avancement calculé.

Types de biens autorisés :

- appartement ;
- maison ;
- villa ;
- local commercial ;
- autre.

Statuts autorisés pour un projet :

- `DRAFT` ;
- `IN_PROGRESS` ;
- `ON_HOLD` ;
- `COMPLETED` ;
- `CANCELLED`.

### 4.3 Étapes de travaux

Une étape peut contenir :

- titre ;
- description ;
- ordre d'affichage ;
- dates prévues de début et de fin ;
- budget prévu ;
- dépense réelle calculée ;
- pourcentage d'avancement ;
- statut ;
- commentaires ;
- photos et documents associés.

Statuts autorisés pour une étape :

- `TODO` ;
- `IN_PROGRESS` ;
- `PENDING_APPROVAL` ;
- `APPROVED` ;
- `REJECTED` ;
- `BLOCKED`.

Règles métier :

- une étape doit appartenir à un projet ;
- une étape refusée doit comporter un commentaire expliquant le refus ;
- une étape ne peut être validée que depuis l'état `PENDING_APPROVAL` ;
- les changements importants doivent apparaître dans l'historique ;
- le pourcentage d'avancement doit rester compris entre 0 et 100.

### 4.4 Budget et dépenses

Une dépense peut contenir :

- libellé ;
- montant positif en MAD ;
- date ;
- catégorie ;
- étape associée ;
- prestataire sous forme de texte libre ;
- référence facultative ;
- justificatif facultatif ;
- statut du paiement.

Catégories autorisées :

- matériaux ;
- main-d'œuvre ;
- transport ;
- équipement ;
- frais administratifs ;
- autre.

Statuts autorisés :

- `PENDING` ;
- `PAID` ;
- `CANCELLED`.

L'application suit seulement des paiements déclarés. Elle ne réalise aucune transaction financière.

### 4.5 Photos et documents

- Ajout local d'images JPG, PNG ou WebP.
- Ajout local de documents PDF.
- Association des fichiers à un projet ou à une étape.
- Contrôle du format et de la taille des fichiers.
- Stockage local pour le prototype.

### 4.6 Tableau de bord

Le tableau de bord peut afficher :

- le projet courant ;
- son pourcentage d'avancement ;
- le budget initial ;
- les dépenses totales ;
- le budget restant ;
- les étapes par statut ;
- les étapes en retard ;
- les dernières activités.

### 4.7 Historique

Les événements suivants peuvent être enregistrés :

- création ou modification d'un projet ;
- création ou modification d'une étape ;
- changement de statut ;
- ajout ou modification d'une dépense ;
- ajout d'un document ;
- validation ou refus d'une étape.

## 5. Fonctionnalités explicitement hors périmètre

Ne pas concevoir, coder, configurer ou simuler les fonctionnalités suivantes sans demande explicite ultérieure de l'utilisateur :

- déploiement ou hébergement ;
- DigitalOcean, AWS, Azure, Docker Swarm ou Kubernetes ;
- CI/CD et pipelines de production ;
- architecture microservices ;
- application mobile native ou Ionic ;
- plateforme d'annonces immobilières ;
- recherche, vente ou location de biens ;
- gestion locative et quittances de loyer ;
- gestion de syndic ou copropriété ;
- location saisonnière, Airbnb ou Booking ;
- paiement en ligne ;
- Stripe, PayPal, CMI ou passerelle bancaire ;
- abonnement SaaS et facturation client ;
- intégration WhatsApp, SMS ou email ;
- notifications push ;
- intelligence artificielle ;
- analyse automatique des photos ;
- géolocalisation ou cartes ;
- signature électronique ;
- génération de contrats juridiques ;
- marketplace d'artisans ;
- notation ou vérification des prestataires ;
- comptes `CONTRACTOR`, `ADMIN` ou gestion multi-utilisateur ;
- invitations et gestion des permissions collaboratives ;
- multilingue et traduction arabe ;
- stockage cloud S3 ou DigitalOcean Spaces ;
- fonctionnalités analytiques avancées ;
- exports complexes ;
- refonte globale non demandée ;
- optimisation prématurée pour une forte charge.

Cette liste n'autorise pas implicitement tout ce qui n'y figure pas. Toute nouveauté doit appartenir au périmètre autorisé et à la demande courante.

## 6. Stack technique autorisée

### Frontend

- Angular 19 ;
- TypeScript en mode strict ;
- composants standalone ;
- Angular Material ;
- SCSS ;
- Reactive Forms ;
- Signals lorsque leur utilisation est simple et justifiée ;
- interface responsive web.

### Backend

- Java 21 ;
- Spring Boot 3 ;
- Spring Web ;
- Spring Data JPA ;
- Bean Validation ;
- Spring Security uniquement si nécessaire pour la tâche courante ;
- Flyway ;
- Maven ;
- PostgreSQL.

### Architecture

- monorepository avec `frontend/` et `backend/` ;
- backend monolithique modulaire ;
- API REST sous `/api/v1` ;
- DTO distincts des entités JPA ;
- logique métier dans les services, pas dans les contrôleurs ;
- migrations de base versionnées ;
- aucune surarchitecture.

## 7. Principes d'interface

- Interface moderne, sobre et rassurante.
- Priorité à la lisibilité et à l'utilisation sur mobile.
- Devise affichée : MAD.
- Première version uniquement en français.
- Badges explicites pour les statuts.
- Barre de progression pour le projet et les étapes.
- Timeline simple pour l'historique.
- Formulaires avec validations et messages d'erreur compréhensibles.
- États de chargement, états vides et confirmations pour les actions importantes.

Ne pas créer de pages ou de menus pour des fonctionnalités hors périmètre.

## 8. Règles de qualité

- Ne pas utiliser `any` sans justification documentée.
- Ne pas exposer directement une entité JPA dans une API.
- Ne pas ajouter de dépendance sans besoin direct.
- Ne pas créer d'abstraction destinée uniquement à une hypothétique évolution.
- Ne pas dupliquer la logique métier.
- Valider les données côté frontend et backend.
- Utiliser des codes HTTP cohérents et un format d'erreur homogène.
- Ajouter des tests ciblés sur les règles métier modifiées.
- Préserver les modifications existantes de l'utilisateur.
- Ne jamais supprimer ou réécrire un fichier sans vérifier son contenu.
- Ne jamais affirmer qu'une fonctionnalité fonctionne sans l'avoir vérifiée.

## 9. Comportement obligatoire de l'agent

Avant toute modification, l'agent doit :

1. Lire entièrement ce fichier.
2. Inspecter les fichiers directement concernés.
3. Reformuler brièvement la demande courante.
4. Identifier les fichiers qui devront probablement être modifiés.
5. Vérifier que chaque changement prévu appartient au scope.

Pendant la modification, l'agent doit :

1. Faire uniquement les changements nécessaires à la demande.
2. Éviter toute refactorisation périphérique.
3. Ne pas corriger spontanément des problèmes sans rapport.
4. Ne pas ajouter de fonctionnalité « pour plus tard ».
5. Conserver la compatibilité avec le code existant lorsque possible.

Après la modification, l'agent doit :

1. Exécuter les tests ou compilations pertinents.
2. Résumer exactement ce qui a été modifié.
3. Signaler ce qui n'a pas pu être vérifié.
4. Mentionner clairement toute proposition hors scope sans l'implémenter.

## 10. Protocole anti-hors-scope

Avant chaque action, appliquer cette question :

> Cette action est-elle indispensable pour satisfaire la demande actuelle et appartient-elle au périmètre autorisé de DarTrack ?

Si la réponse n'est pas clairement « oui », ne pas effectuer l'action.

Si une demande nécessite une extension du périmètre, répondre avec :

> Cette demande semble étendre le périmètre actuel du MVP. L'extension envisagée est : [description]. Elle aurait les impacts suivants : [impacts]. Souhaites-tu modifier le scope avant que je l'implémente ?

L'agent doit attendre une confirmation explicite.

## 11. Gestion des demandes ambiguës

- Ne pas deviner une règle métier importante.
- Ne pas choisir silencieusement entre plusieurs comportements possibles.
- Poser une question courte lorsque le choix modifie les données, le workflow, la sécurité ou le périmètre.
- Pour un détail réversible et sans impact métier, appliquer l'option la plus simple et indiquer l'hypothèse.

## 12. Définition de terminé

Une tâche est terminée uniquement si :

- la demande exacte est implémentée ;
- aucune fonctionnalité hors scope n'a été ajoutée ;
- le code compile dans la mesure permise par l'environnement ;
- les tests pertinents passent ou leurs blocages sont expliqués ;
- les règles métier concernées sont respectées ;
- les erreurs et cas vides essentiels sont traités ;
- les fichiers modifiés sont clairement listés dans le compte rendu.

## 13. Rappel final

**Toujours rester dans le scope demandé.**

Ne pas déployer. Ne pas ajouter d'intégration externe. Ne pas préparer une évolution non demandée. Ne pas transformer le prototype mono-utilisateur en plateforme complète.

Lorsque l'utilisateur souhaite faire évoluer DarTrack, mettre d'abord à jour ce fichier avec son accord, puis seulement implémenter le nouveau périmètre.
