# Prompts

Use the following Checklist to use this set of Cursor Rules about Agile processes

- 1. [x] Review requirements.

  - 1.1 [x] Create an `Epic` about the development

**Note:** Attach the initial free format text/markdown document describing the problem to solve.

```bash
Create an agile epic based the initial documentation received and use @2001-agile-create-an-epic
```

  - 1.2 [x] Create a `Feature` about the development

**Note:** Attach the EPIC created previously

```bash
Create a feature based on the epic and use @2002-agile-create-features-from-epics
```

**Note:** Review if the rule generates several features and maybe it is possible to merge into a single one. If you prefer to have only one feature, ask it.

  - 1.3 [x] Create an `User story` and the `Acceptance criteria` in `Gherkin` format based on the requirements.

**Note:** Attach the EPIC and the Feature created previously

```bash
Create a user story based on the feature and the acceptance criteria using the information provided with the cursor rule @2003-agile-create-user-stories
```

  - 1.4 [x] Create an `UML` Sequence diagrama about the functional requirements

**Note:** Attach the EPIC,Feature,User Story & Gherkin created previously

```bash
Create the UML sequence diagram based in plantuml format using the information provided with the cursor rule @2004-uml-sequence-diagram-from-agile-artifacts 
```

  - 1.5 [x] Create the `C4 Model` diagrams based on the requirements

**Note:** Attach the EPIC,Feature,User Story, Gherkin & UML Sequence diagram created previously

```bash
Create the C4 Model diagrams from the requirements in plantuml format using the information provided with the cursor rule @2005-c4-diagrams-about-solution
```

**Note:** Review the diagrams, sometimes it is necessary to simplify the models.

  - 1.6 [x] Create an `ADR` about the functional requirements

**Note:** Attach the EPIC,Feature,User Story, Gherkin, UML Sequence diagram & C4 Model diagrams created previously

**Terminal/Cli development:**

```bash
Create the ADR about functional requirements using the cursor rule @2006-adr-create-functional-requirements-for-cli-development
```

**REST API development:**

```bash
Create the ADR about the functional requirements using the information provided with the cursor rule @2006-adr-create-functional-requirements-for-rest-api-development
```

  - 1.7 [x] Create an `ADR` about the acceptance testing Strategy

**Note:** Attach User Story & Gherkin created previously

```bash
Create the ADR about the acceptance testing strategy using the information provided with the cursor rule @2007-adr-create-acceptance-testing-strategy
```

  - 1.8 [x] Create an `ADR` about the non functional requirements

**Note:** Attach the EPIC,Feature,User Story, Gherkin, UML Sequence diagram & C4 Model diagrams created previously

```bash
Create the ADR about the functional requirements using the information provided with the cursor rule @2008-adr-create-non-functional-requirements-decisions
```

---

Once you have a solution stable, you could review some aspects about the Design, maybe you could see some way to improve:

- 2. [x] Review current solution state.

 - 2.1 [x] Create an UML class diagram

```bash
Create the UML diagram based on @src/main/java using the cursor rule @2009-uml-class-diagram-mdc
``` 
