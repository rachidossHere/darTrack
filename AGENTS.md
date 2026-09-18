**# DarTrack — Instructions obligatoires pour les agents**

**## 1. Autorité de ce document**

Ce fichier définit le périmètre fonctionnel et technique autorisé du projet DarTrack.

Toutes les instructions de ce fichier sont obligatoires pour chaque agent qui lit, crée, modifie, teste ou analyse le code du projet.

Règle principale : **\*\*ne jamais effectuer de travail hors du périmètre explicitement demandé par l'utilisateur.\*\***

Une fonctionnalité techniquement utile, moderne, recommandée ou facile à ajouter ne devient pas automatiquement autorisée.

En cas de doute :

1\. Ne pas modifier le code concerné.

2\. Expliquer brièvement le point ambigu.

3\. Demander l'accord explicite de l'utilisateur.

4\. Attendre sa réponse avant de poursuivre.

**## 2. Présentation du projet**

Nom provisoire : **\*\*DarTrack\*\***.

DarTrack est un MVP destiné aux Marocains résidant à l'étranger (MRE) qui souhaitent suivre à distance les travaux de rénovation d'un logement situé au Maroc.

Le produit doit apporter :

\- de la visibilité sur l'avancement des travaux ;

\- une traçabilité des étapes et des décisions ;

\- un suivi simple du budget et des dépenses ;

\- des preuves organisées sous forme de photos et de documents ;

\- la possibilité de valider ou de refuser une étape de travaux.

DarTrack n'est pas une application immobilière généraliste. Ce n'est ni un portail d'annonces, ni une agence immobilière, ni une plateforme de location, ni un logiciel de syndic.

**## 3. Périmètre actuel du MVP**

La première version est un **\*\*prototype local pour un seul utilisateur propriétaire\*\***.

L'unique rôle fonctionnel autorisé actuellement est :

\- \`OWNER\` : le propriétaire MRE qui suit son chantier.

Pour tester le concept, le propriétaire peut lui-même saisir les mises à jour, dépenses, photos et décisions. La gestion de comptes artisans, administrateurs ou autres collaborateurs n'est pas incluse dans cette version.

Le parcours essentiel à démontrer est :

1\. Le propriétaire crée un projet de rénovation.

2\. Il ajoute et organise les étapes des travaux.

3\. Il met à jour l'avancement d'une étape.

4\. Il ajoute une photo, un document ou une dépense comme preuve.

5\. Il place l'étape en attente de décision.

6\. Il valide ou refuse l'étape.

7\. Le budget, l'avancement et l'historique sont actualisés.

Tout développement doit servir directement ce parcours.

**## 4. Fonctionnalités autorisées**

**### 4.1 Compte propriétaire**

\- Un seul compte propriétaire local.

\- Connexion simple si l'authentification fait partie de la tâche demandée.

\- Consultation et modification des informations essentielles du profil.

\- Aucun système complet de gestion multi-utilisateur.

**### 4.2 Projet de rénovation**

Un projet peut contenir :

\- nom ;

\- description ;

\- type de bien ;

\- adresse ;

\- ville au Maroc ;

\- budget initial en MAD ;

\- date de début ;

\- date de fin estimée ;

\- statut ;

\- pourcentage d'avancement calculé.

Types de biens autorisés :

\- appartement ;

\- maison ;

\- villa ;

\- local commercial ;

\- autre.

Statuts autorisés pour un projet :

\- \`DRAFT\` ;

\- \`IN_PROGRESS\` ;

\- \`ON_HOLD\` ;

\- \`COMPLETED\` ;

\- \`CANCELLED\`.

**### 4.3 Étapes de travaux**

Une étape peut contenir :

\- titre ;

\- description ;

\- ordre d'affichage ;

\- dates prévues de début et de fin ;

\- budget prévu ;

\- dépense réelle calculée ;

\- pourcentage d'avancement ;

\- statut ;

\- commentaires ;

\- photos et documents associés.

Statuts autorisés pour une étape :

\- \`TODO\` ;

\- \`IN_PROGRESS\` ;

\- \`PENDING_APPROVAL\` ;

\- \`APPROVED\` ;

\- \`REJECTED\` ;

\- \`BLOCKED\`.

Règles métier :

\- une étape doit appartenir à un projet ;

\- une étape refusée doit comporter un commentaire expliquant le refus ;

\- une étape ne peut être validée que depuis l'état \`PENDING_APPROVAL\` ;

\- les changements importants doivent apparaître dans l'historique ;

\- le pourcentage d'avancement doit rester compris entre 0 et 100.

**### 4.4 Budget et dépenses**

Une dépense peut contenir :

\- libellé ;

\- montant positif en MAD ;

\- date ;

\- catégorie ;

\- étape associée ;

\- prestataire sous forme de texte libre ;

\- référence facultative ;

\- justificatif facultatif ;

\- statut du paiement.

Catégories autorisées :

\- matériaux ;

\- main-d'œuvre ;

\- transport ;

\- équipement ;

\- frais administratifs ;

\- autre.

Statuts autorisés :

\- \`PENDING\` ;

\- \`PAID\` ;

\- \`CANCELLED\`.

L'application suit seulement des paiements déclarés. Elle ne réalise aucune transaction financière.

**### 4.5 Photos et documents**

\- Ajout local d'images JPG, PNG ou WebP.

\- Ajout local de documents PDF.

\- Association des fichiers à un projet ou à une étape.

\- Contrôle du format et de la taille des fichiers.

\- Stockage local pour le prototype.

**### 4.6 Tableau de bord**

Le tableau de bord peut afficher :

\- le projet courant ;

\- son pourcentage d'avancement ;

\- le budget initial ;

\- les dépenses totales ;

\- le budget restant ;

\- les étapes par statut ;

\- les étapes en retard ;

\- les dernières activités.

**### 4.7 Historique**

Les événements suivants peuvent être enregistrés :

\- création ou modification d'un projet ;

\- création ou modification d'une étape ;

\- changement de statut ;

\- ajout ou modification d'une dépense ;

\- ajout d'un document ;

\- validation ou refus d'une étape.

**## 5. Fonctionnalités explicitement hors périmètre**

Ne pas concevoir, coder, configurer ou simuler les fonctionnalités suivantes sans demande explicite ultérieure de l'utilisateur :

\- déploiement ou hébergement ;

\- DigitalOcean, AWS, Azure, Docker Swarm ou Kubernetes ;

\- CI/CD et pipelines de production ;

\- architecture microservices ;

\- application mobile native ou Ionic ;

\- plateforme d'annonces immobilières ;

\- recherche, vente ou location de biens ;

\- gestion locative et quittances de loyer ;

\- gestion de syndic ou copropriété ;

\- location saisonnière, Airbnb ou Booking ;

\- paiement en ligne ;

\- Stripe, PayPal, CMI ou passerelle bancaire ;

\- abonnement SaaS et facturation client ;

\- intégration WhatsApp, SMS ou email ;

\- notifications push ;

\- intelligence artificielle ;

\- analyse automatique des photos ;

\- géolocalisation ou cartes ;

\- signature électronique ;

\- génération de contrats juridiques ;

\- marketplace d'artisans ;

\- notation ou vérification des prestataires ;

\- comptes \`CONTRACTOR\`, \`ADMIN\` ou gestion multi-utilisateur ;

\- invitations et gestion des permissions collaboratives ;

\- multilingue et traduction arabe ;

\- stockage cloud S3 ou DigitalOcean Spaces ;

\- fonctionnalités analytiques avancées ;

\- exports complexes ;

\- refonte globale non demandée ;

\- optimisation prématurée pour une forte charge.

Cette liste n'autorise pas implicitement tout ce qui n'y figure pas. Toute nouveauté doit appartenir au périmètre autorisé et à la demande courante.

**## 6. Stack technique autorisée**

**### Frontend**

\- Angular 21 ;

\- TypeScript en mode strict ;

\- composants, directives et pipes standalone uniquement ;

\- aucune création de `NgModule` sauf contrainte explicite d'une bibliothèque existante ;

\- Angular Material ;

\- SCSS ;

\- Reactive Forms ;

\- NgRx Store, Actions, Reducers, Selectors et Effects ;

\- NgRx ComponentStore pour l'état local autonome des composants conteneurs et composants métier stateful ;

\- Signals pour l'état strictement local et simple, sans dupliquer les données détenues par NgRx ;

\- RxJS avec gestion explicite du cycle de vie des souscriptions ;

\- stratégie de détection `OnPush` ;

\- interface responsive web.

**### Backend**

\- Java 21 ;

\- Spring Boot 3 ;

\- Spring Web ;

\- Spring Data JPA ;

\- Bean Validation ;

\- Spring Security uniquement si nécessaire pour la tâche courante ;

\- Flyway ;

\- Maven ;

\- PostgreSQL.

**### Architecture**

\- monorepository avec \`frontend/\` et \`backend/\` ;

\- backend monolithique modulaire ;

\- API REST sous \`/api/v1\` ;

\- DTO distincts des entités JPA ;

\- logique métier dans les services, pas dans les contrôleurs ;

\- migrations de base versionnées ;

\- aucune surarchitecture.

**### 6.1 Architecture frontend Angular**

Le frontend doit utiliser une architecture standalone et être organisé au minimum avec les répertoires suivants :

```text
frontend/src/app/
├── core/
│   ├── interceptors/
│   ├── guards/
│   ├── resolvers/
│   ├── services/
│   └── config/
├── shared/
│   ├── components/
│   ├── directives/
│   ├── pipes/
│   ├── models/
│   ├── services/
│   ├── utils/
│   └── validators/
├── features/
│   ├── dashboard/
│   ├── projects/
│   ├── work-steps/
│   ├── expenses/
│   ├── media/
│   ├── documents/
│   └── history/
├── store/
│   ├── actions/
│   ├── effects/
│   ├── reducers/
│   ├── selectors/
│   └── app.state.ts
├── app.component.ts
├── app.config.ts
└── app.routes.ts
```

Règles de dépendance :

\- `core/` contient uniquement les éléments singleton et transversaux chargés une seule fois : interceptors HTTP, guards, resolvers, configuration et services d'infrastructure ;

\- `shared/` contient uniquement les composants, modèles, pipes, directives, validateurs, utilitaires et services réellement réutilisables par plusieurs features ;

\- un service ou modèle propre à une feature doit rester dans cette feature et ne doit pas être déplacé prématurément dans `shared/` ;

\- `features/` contient les domaines fonctionnels et leurs pages, composants, routes, services d'accès aux données et stores locaux ;

\- les features ne doivent pas dépendre directement les unes des autres ; les contrats réellement partagés passent par `shared/` ou par le store global ;

\- les routes de features doivent être chargées paresseusement avec `loadComponent` ou `loadChildren` sur des fichiers de routes standalone ;

\- utiliser les APIs fonctionnelles Angular lorsque disponibles et pertinentes, notamment pour les interceptors, guards et resolvers ;

\- utiliser l'injection par `inject()` lorsque cela améliore la lisibilité et la testabilité ;

\- les pages orchestrent les cas d'usage et les composants de présentation reçoivent leurs données avec des inputs et exposent les interactions avec des outputs ;

\- éviter les souscriptions manuelles dans les composants ; privilégier `AsyncPipe`, les selectors et `takeUntilDestroyed()` lorsque la souscription est indispensable.

**### 6.2 Architecture NgRx**

DarTrack doit utiliser deux niveaux d'état clairement séparés.

**Store global de l'application :**

\- configuré dans `app.config.ts` avec `provideStore()` et `provideEffects()` ;

\- réservé aux données partagées entre plusieurs écrans ou nécessaires pendant toute la session ;

\- organisé avec des Actions, Reducers, Selectors et Effects explicitement séparés ;

\- expose un `AppState` typé ;

\- aucune donnée dérivée ne doit être stockée si elle peut être calculée par un selector ;

\- les appels HTTP et autres effets de bord doivent être réalisés dans les Effects ou dans des services appelés par les Effects, jamais dans les Reducers ;

\- les Reducers restent purs et les mises à jour d'état sont immuables ;

\- utiliser `createActionGroup`, `createFeature`, `createReducer`, `createSelector` et les APIs NgRx compatibles avec la version Angular retenue ;

\- les états de chargement, succès et erreur sont modélisés explicitement lorsque nécessaires.

**Stores de features et de composants :**

\- chaque feature lazy-loaded enregistre son état avec `provideState()` et ses effets avec `provideEffects()` lorsqu'elle possède un état partagé entre plusieurs pages ou composants de la feature ;

\- chaque composant conteneur ou composant métier possédant un état local non trivial doit disposer d'un `ComponentStore` fourni au niveau du composant afin de rester autonome et d'éviter les fuites d'état entre instances ;

\- un composant purement présentational, sans état métier propre, ne doit pas recevoir un store artificiel ;

\- le `ComponentStore` local expose des selectors ou view-models, des updaters et des effects typés ;

\- les templates consomment de préférence un view-model unique, par exemple `vm$`, afin de limiter la duplication des souscriptions ;

\- une même donnée ne doit pas être détenue simultanément par le store global et un store local ;

\- le store global reste la source de vérité pour les données partagées, tandis que le store local gère l'état d'interface ou le workflow propre à une instance de composant ;

\- les composants ne doivent jamais appeler directement le backend lorsqu'un flux NgRx est prévu ; ils déclenchent une action ou une méthode du store local ;

\- ne pas utiliser NgRx comme simple cache générique et ne pas ajouter un store à une fonctionnalité sans état.

**### 6.3 Architecture backend Spring Boot**

Le backend doit être un monolithe modulaire organisé par domaine fonctionnel, et non uniquement par couche technique globale.

Structure recommandée :

```text
backend/src/main/java/.../dartrack/
├── common/
│   ├── error/
│   ├── validation/
│   └── config/
├── project/
├── workstep/
├── expense/
├── media/
├── document/
└── history/
```

Chaque domaine peut contenir ses contrôleurs, DTO, mappers, services, entités et repositories.

Règles backend :

\- contrôleurs REST minces, sans logique métier ;

\- services responsables des cas d'usage et des transactions ;

\- repositories limités à l'accès aux données ;

\- DTO d'entrée et de sortie distincts des entités JPA ;

\- mapping explicite et testable entre DTO et entités ;

\- validation des entrées avec Bean Validation et validation métier dans les services ;

\- gestion centralisée des erreurs avec un format de réponse homogène ;

\- pagination pour les collections uniquement lorsque le besoin fonctionnel le justifie ;

\- transactions courtes et explicites ;

\- pas de logique métier dans les listeners JPA, les mappers ou les repositories ;

\- pas de dépendance circulaire entre domaines ;

\- Flyway est l'unique moyen autorisé pour faire évoluer le schéma de la base.

**## 7. Principes d'interface**

\- Interface moderne, sobre et rassurante.

\- Respecter le design DarTrack de référence sans remplacer sa direction graphique ni renommer ses rubriques sans demande explicite.

\- Conserver les rubriques principales suivantes : `Vue d'ensemble`, `Étapes du chantier`, `Photos & preuves`, `Budget` et `Documents`.

\- Ne pas ajouter une rubrique principale, un menu ou une page hors périmètre.

\- Utiliser une navigation latérale sur ordinateur et une navigation adaptée aux petits écrans sur mobile.

\- Conserver la palette visuelle de référence : vert profond pour la navigation et les actions principales, doré pour les accents et la progression, fonds clairs et cartes blanches.

\- Conserver la présentation sous forme de cartes, les badges de statut, les indicateurs de progression, les tableaux lisibles et la timeline verticale.

\- Priorité à la lisibilité et à l'utilisation sur mobile.

\- L'interface doit être responsive sans défilement horizontal involontaire aux tailles mobile, tablette et desktop.

\- Utiliser des breakpoints cohérents et tester au minimum les largeurs représentatives de 360 px, 768 px et 1280 px.

\- Respecter l'accessibilité de base : HTML sémantique, navigation clavier, focus visible, labels de formulaires, textes alternatifs et contrastes suffisants.

\- Devise affichée : MAD.

\- Première version uniquement en français.

\- Badges explicites pour les statuts.

\- Barre de progression pour le projet et les étapes.

\- Timeline simple pour l'historique.

\- Formulaires avec validations et messages d'erreur compréhensibles.

\- États de chargement, états vides et confirmations pour les actions importantes.

Ne pas créer de pages ou de menus pour des fonctionnalités hors périmètre.

**### 7.1 Référence visuelle obligatoire**

Le fichier `index(1).html` fourni avec le projet constitue la référence visuelle officielle de DarTrack.

Avant toute création ou modification importante du frontend, l'agent doit lire ce fichier et comparer son implémentation au rendu de référence.

La version Angular doit reproduire fidèlement le design, la hiérarchie visuelle, les proportions, les rubriques et les comportements responsive du fichier de référence. Elle ne doit toutefois pas copier son JavaScript impératif : le HTML doit être décomposé en composants Angular standalone, alimenté par des données typées et piloté par les stores NgRx prévus dans ce document.

**Tokens visuels obligatoires :**

```scss
--color-ink: #17312d;
--color-muted: #6f7f7c;
--color-forest: #153f38;
--color-mint: #d9eee7;
--color-sand: #f5f0e6;
--color-gold: #f4c76d;
--color-orange: #df7846;
--color-red: #b94f4f;
--color-line: #e2e9e6;
--color-surface: #ffffff;
--color-background: #f7f9f8;
--shadow-elevated: 0 14px 40px rgba(22, 63, 56, 0.10);
```

Ces tokens doivent être centralisés dans les styles globaux ou dans un fichier de thème. Ne pas disperser leurs valeurs en dur dans les composants.

**Structure visuelle à conserver :**

\- sidebar vert profond de 250 px sur desktop, fixe ou sticky sur toute la hauteur ;

\- logo DarTrack avec accent doré en haut de la sidebar ;

\- profil propriétaire en bas de la sidebar ;

\- topbar claire et sticky de 76 px sur desktop ;

\- contenu centré avec une largeur maximale de 1400 px ;

\- cartes blanches avec bordure légère, ombre discrète et rayon principal de 20 px ;

\- carte du projet principal avec dégradé vert, pourcentage doré et barre de progression ;

\- bloc `À valider` distinct avec actions de validation et consultation ;

\- quatre indicateurs principaux : budget consommé, étapes terminées, photos reçues et écart de planning ;

\- timeline verticale pour l'avancement et bloc séparé pour l'activité récente ;

\- page des étapes sous forme de timeline ;

\- page photos sous forme de grille de cartes ;

\- pages budget et documents sous forme de tableaux lisibles ;

\- badges visuels cohérents pour les états terminé, en cours, à venir, à valider, payé et refusé ;

\- boutons principaux en vert profond et accents de progression en doré.

**Navigation obligatoire :**

1\. `Vue d'ensemble` ;

2\. `Étapes du chantier` ;

3\. `Photos & preuves` ;

4\. `Budget` ;

5\. `Documents`.

L'ordre et les libellés de ces rubriques ne doivent pas changer sans demande explicite de l'utilisateur.

**Responsive obligatoire :**

\- au-dessus de 1050 px : disposition desktop complète avec sidebar et grilles en plusieurs colonnes ;

\- entre 761 px et 1050 px : les grilles principales passent progressivement sur une colonne et les statistiques sur deux colonnes ;

\- à 760 px et moins : la sidebar devient un drawer accessible avec un bouton menu et un overlay, le contenu utilise des marges réduites et les photos passent sur une colonne ;

\- à 430 px et moins : les statistiques passent sur une colonne et les éléments secondaires non indispensables peuvent être masqués conformément à la référence ;

\- aucune information métier essentielle ni action principale ne doit devenir inaccessible sur mobile ;

\- les tableaux doivent être consultables sur petit écran avec une stratégie responsive explicite et accessible.

**Contrôle de conformité du design :**

Avant de considérer une tâche frontend terminée, vérifier au minimum le rendu à 1280 px, 768 px et 360 px. Comparer la navigation, les espacements, les couleurs, la hiérarchie typographique, les cartes, les badges, les tableaux et les états interactifs avec `index(1).html`.

Toute amélioration visuelle qui modifie sensiblement cette référence est hors scope sans accord explicite de l'utilisateur.

**## 8. Règles de qualité**

\- Ne pas utiliser \`any\` sans justification documentée.

\- Activer et respecter les options strictes TypeScript et Angular Templates.

\- Utiliser `ChangeDetectionStrategy.OnPush` sur les composants.

\- Conserver les composants courts, spécialisés et centrés sur une responsabilité claire.

\- Ne pas placer de logique métier dans les templates, les composants de présentation, les reducers ou les effects.

\- Les selectors doivent être purs, composables et testables.

\- Les effects doivent traiter explicitement les succès et les erreurs pertinents.

\- Ne jamais muter directement l'état NgRx ni les objets reçus par input.

\- Ne pas dupliquer une source de vérité entre service, signal, store global et ComponentStore.

\- Ne pas créer de store local pour un composant purement visuel ou sans état propre.

\- Ajouter des tests unitaires ciblés pour les reducers, selectors, effects, ComponentStores et règles métier modifiés.

\- Ne pas exposer directement une entité JPA dans une API.

\- Ne pas ajouter de dépendance sans besoin direct.

\- Ne pas créer d'abstraction destinée uniquement à une hypothétique évolution.

\- Ne pas dupliquer la logique métier.

\- Valider les données côté frontend et backend.

\- Utiliser des codes HTTP cohérents et un format d'erreur homogène.

\- Ajouter des tests ciblés sur les règles métier modifiées.

\- Préserver les modifications existantes de l'utilisateur.

\- Ne jamais supprimer ou réécrire un fichier sans vérifier son contenu.

\- Ne jamais affirmer qu'une fonctionnalité fonctionne sans l'avoir vérifiée.

**## 9. Comportement obligatoire de l'agent**

Avant toute modification, l'agent doit :

1\. Lire entièrement ce fichier.

2\. Inspecter les fichiers directement concernés.

3\. Reformuler brièvement la demande courante.

4\. Identifier les fichiers qui devront probablement être modifiés.

5\. Vérifier que chaque changement prévu appartient au scope.

Pendant la modification, l'agent doit :

1\. Faire uniquement les changements nécessaires à la demande.

2\. Éviter toute refactorisation périphérique.

3\. Ne pas corriger spontanément des problèmes sans rapport.

4\. Ne pas ajouter de fonctionnalité « pour plus tard ».

5\. Conserver la compatibilité avec le code existant lorsque possible.

Après la modification, l'agent doit :

1\. Exécuter les tests ou compilations pertinents.

2\. Résumer exactement ce qui a été modifié.

3\. Signaler ce qui n'a pas pu être vérifié.

4\. Mentionner clairement toute proposition hors scope sans l'implémenter.

**## 10. Protocole anti-hors-scope**

Avant chaque action, appliquer cette question :

\> Cette action est-elle indispensable pour satisfaire la demande actuelle et appartient-elle au périmètre autorisé de DarTrack ?

Si la réponse n'est pas clairement « oui », ne pas effectuer l'action.

Si une demande nécessite une extension du périmètre, répondre avec :

\> Cette demande semble étendre le périmètre actuel du MVP. L'extension envisagée est : [description]. Elle aurait les impacts suivants : [impacts]. Souhaites-tu modifier le scope avant que je l'implémente ?

L'agent doit attendre une confirmation explicite.

**## 11. Gestion des demandes ambiguës**

\- Ne pas deviner une règle métier importante.

\- Ne pas choisir silencieusement entre plusieurs comportements possibles.

\- Poser une question courte lorsque le choix modifie les données, le workflow, la sécurité ou le périmètre.

\- Pour un détail réversible et sans impact métier, appliquer l'option la plus simple et indiquer l'hypothèse.

**## 12. Définition de terminé**

Une tâche est terminée uniquement si :

\- la demande exacte est implémentée ;

\- aucune fonctionnalité hors scope n'a été ajoutée ;

\- le code compile dans la mesure permise par l'environnement ;

\- les tests pertinents passent ou leurs blocages sont expliqués ;

\- les règles métier concernées sont respectées ;

\- les erreurs et cas vides essentiels sont traités ;

\- les fichiers modifiés sont clairement listés dans le compte rendu.

**## 13. Rappel final**

**\*\*Toujours rester dans le scope demandé.\*\***

Ne pas déployer. Ne pas ajouter d'intégration externe. Ne pas préparer une évolution non demandée. Ne pas transformer le prototype mono-utilisateur en plateforme complète.

Lorsque l'utilisateur souhaite faire évoluer DarTrack, mettre d'abord à jour ce fichier avec son accord, puis seulement implémenter le nouveau périmètre.
