# Database Schema Design

## Greek Gods API Database Schema

### Table: greek_god

**Purpose:** Stores information about Greek gods for the API data retrieval system.

**Table Structure:**

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for each Greek god record |
| name | VARCHAR(100) | NOT NULL, UNIQUE | Name of the Greek god |

**SQL Definition:**
```sql
CREATE TABLE greek_god (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL UNIQUE
);
```

**Indexes:**
- Primary key index on `id` (automatic)
- Unique index on `name` (automatic from UNIQUE constraint)

**Design Decisions:**
- `id` uses `BIGINT` for scalability and auto-increment for simplicity
- `name` uses `VARCHAR(100)` to accommodate longest Greek god names
- `UNIQUE` constraint on `name` prevents duplicate god entries
- `NOT NULL` constraint ensures data integrity

**Expected Data Volume:** ~20 Greek god records initially

**Relationships:** 
- Currently standalone table
- Future: May relate to other mythology tables (Roman gods, attributes, etc.)

**Performance Considerations:**
- Small dataset size requires minimal optimization
- Unique index on `name` supports fast lookups by god name
- Primary key supports efficient joins if needed in future 