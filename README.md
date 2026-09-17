# DarTrack

DarTrack est un prototype local de suivi de rénovation pour un propriétaire MRE.

## Phases réalisées

Le socle contient :

- un frontend Angular 19 standalone avec SCSS, routing et Angular Material ;
- un backend Spring Boot 3 avec Maven, Java 21, JPA, validation et Flyway ;
- PostgreSQL local via Docker Compose.
- modèle métier et migration Flyway pour projets, étapes, dépenses, documents et activités ;
- API REST des projets, étapes, dépenses, documents et historique sous `/api/v1` ;
- workflow d'étape `IN_PROGRESS`, `PENDING_APPROVAL`, `APPROVED` et `REJECTED` ;
- stockage local des documents JPG, PNG, WebP et PDF ;
- interface Angular responsive pour le tableau de bord, les étapes, dépenses, preuves et historique.

## Prérequis

- Node.js 22 et npm ;
- JDK 21 et Maven ;
- Docker avec Docker Compose.

## Démarrage local

Copier `.env.example` vers `.env`, puis démarrer PostgreSQL et pgAdmin :

```bash
docker compose up -d postgres pgadmin
```

pgAdmin est ensuite disponible sur `http://localhost:5051`. Pour enregistrer PostgreSQL dans pgAdmin, utiliser `postgres` comme nom d'hôte, `5432` comme port et les identifiants `POSTGRES_USER` / `POSTGRES_PASSWORD` du fichier `.env`.

Lancer le backend :

```bash
cd backend
mvn spring-boot:run
```

Lancer le frontend dans un autre terminal :

```bash
cd frontend
npm start
```

Le profil `dev` active les données de démonstration :

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Le backend attend PostgreSQL sur `localhost:5432` et stocke les fichiers dans `./storage` par défaut.

## Organisation du code

Le frontend est organisé par responsabilités :

```text
frontend/src/app/
├── core/          # services HTTP, interceptors et resolvers
├── shared/        # modèles et composants réutilisables
└── features/      # fonctionnalités et écrans métier
```

Le backend sépare également les responsabilités :

```text
backend/src/main/java/ma/dartrack/
├── controllers/
├── models/
├── repositories/
├── services/
├── services/impl/
├── common/
└── config/
```# darTrack
