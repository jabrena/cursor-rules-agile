# Agile Development Guide

Use the following step-by-step process to implement a complete agile development workflow using Cursor Rules.

## Process Overview

### Phase 1: Requirements Analysis & Agile Artifacts

- **1. [ ] Review requirements.**

  - **1.1 [x] Create an `Epic` about the development**

**Note:** Attach the initial free format text/markdown document describing the problem to solve.

```bash
Create a feature based on the epic and use the cursor rule  @2002-agile-create-features-from-epics
```

  - **1.2 [x] Create a `Feature` about the development**

**Note:** Attach the EPIC created previously

```bash
Create a feature based on the epic and use @2002-agile-create-features-from-epics
```

**Note:** Review if the rule generates several features and maybe it is possible to merge into a single one. If you prefer to have only one feature, ask it.

  - **1.3 [x] Create an `User story` and the `Acceptance criteria` in `Gherkin` format based on the requirements.**

**Note:** Attach the EPIC and the Feature created previously

```bash
Create a user story based on the feature and the acceptance criteria using the information provided with the cursor rule @2003-agile-create-user-stories
```

### Phase 2: Technical Design & Architecture

  - **1.4 [x] Create an `UML` Sequence diagram about the functional requirements**

**Note:** Attach the EPIC, Feature, User Story & Gherkin created previously

```bash
Create the UML sequence diagram based in plantuml format using the information provided with the cursor rule @2004-uml-sequence-diagram-from-agile-artifacts
```

  - **1.5 [x] Create the `C4 Model` diagrams based on the requirements**

**Note:** Attach the EPIC, Feature, User Story, Gherkin & UML Sequence diagram created previously

```bash
Create the C4 Model diagrams from the requirements in plantuml format using the information provided with the cursor rule @2005-c4-diagrams-about-solution
```

**Note:** Review the diagrams, sometimes it is necessary to simplify the models.

### Phase 3: Architecture Decision Records (ADRs)

  - **1.6 [] Create an `ADR` about the functional requirements**

**Note:** Attach the EPIC, Feature, User Story, Gherkin, UML Sequence diagram & C4 Model diagrams created previously

**Terminal/CLI development:**

```bash
Create the ADR about functional requirements using the cursor rule @2006-adr-create-functional-requirements-for-cli-development
```

**REST API development:**

```bash
Create the ADR about the functional requirements using the information provided with the cursor rule @2006-adr-create-functional-requirements-for-rest-api-development
```

  - **1.7 [x] Create an `ADR` about the acceptance testing Strategy**

**Note:** Attach User Story & Gherkin created previously

```bash
Create the ADR about the acceptance testing strategy using the information provided with the cursor rule @2007-adr-create-acceptance-testing-strategy
```

  - **1.8 [ ] Create an `ADR` about the non functional requirements**

**Note:** Attach the EPIC, Feature, User Story, Gherkin, UML Sequence diagram & C4 Model diagrams created previously

```bash
Create the ADR about the non functional requirements using the information provided with the cursor rule @2008-adr-create-non-functional-requirements-decisions
```

### Phase 4: Solution Review & Design Validation

- **2. [ ] Review current solution state.**

 - **2.1 [ ] Create an UML class diagram**

**Note:** Once you have a solution stable, you could review some aspects about the Design, maybe you could see some way to improve:

```bash
Create the UML diagram based on @src/main/java using the cursor rule @2009-uml-class-diagram-mdc
```

---

## Available Cursor Rules Reference

| Rule ID | Purpose | When to Use |
|---------|---------|-------------|
| @2001-agile-create-an-epic | Create agile epics | Start of project with initial requirements |
| @2002-agile-create-features-from-epics | Create agile features from an epic | After epic is created and approved |
| @2003-agile-create-user-stories | Create Agile User stories with Gherkin | After features are defined |
| @2004-uml-sequence-diagram-from-agile-artifacts | Create UML Sequence Diagrams | After user stories are complete |
| @2005-c4-diagrams-about-solution | Create C4 Diagrams | For architectural overview |
| @2006-adr-create-functional-requirements-for-cli-development | Create ADR for CLI Development | For command-line applications |
| @2006-adr-create-functional-requirements-for-rest-api-development | Create ADR for REST API Implementation | For REST API development |
| @2007-adr-create-acceptance-testing-strategy | Create ADR for Acceptance Testing Strategy | After user stories with Gherkin |
| @2008-adr-create-non-functional-requirements-decisions | Create ADR for Non-Functional Requirements | After technical design phase |
| @2009-uml-class-diagram-mdc | Create UML Class Diagrams | For final solution review |

## Tips for Success

### Best Practices
- **Always attach the required documents** mentioned in each step's note section
- **Follow the sequence** - each step builds on the previous ones
- **Review and refine** - Don't hesitate to ask for simplifications or modifications
- **Keep artifacts updated** - Maintain consistency across all documents

### Common Pitfalls to Avoid
- Skipping the attachment of previous artifacts when required
- Not reviewing generated features for potential consolidation
- Creating overly complex C4 diagrams (simplify when needed)
- Forgetting to choose between CLI or REST API ADR templates 