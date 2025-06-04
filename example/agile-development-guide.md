# Agile Development Guide

Use the following step-by-step process to implement a complete agile development workflow using Cursor Rules.

## Process Overview

### Phase 1: Requirements Analysis & Agile Artifacts

- **1. [ ] Review requirements.**

  - **1.1 [ ] Create an `Epic` about the development**

**Note:** Attach the initial free format text/markdown document describing the problem to solve.

```bash
Create an agile epic based the initial documentation received and use the cursor rule @2001-agile-create-an-epic
```

  - **1.2 [ ] Create a `Feature` about the development**

**Note:** Attach the EPIC created previously

```bash
Create a feature based on the epic and use the cursor rule  @2002-agile-create-features-from-epics
```

**Note:** Review if the rule generates several features and maybe it is possible to merge into a single one. If you prefer to have only one feature, ask it.

  - **1.3 [ ] Create an `User story` and the `Acceptance criteria` in `Gherkin` format based on the requirements.**

**Note:** Attach the EPIC and the Feature created previously

```bash
Create a user story based on the feature and the acceptance criteria using the information provided and use the cursor rule @2003-agile-create-user-stories
```

### Phase 2: Technical Design & Architecture

  - **2.1 [ ] Create an `UML` Sequence diagram about the functional requirements**

**Note:** Attach the EPIC, Feature, User Story & Gherkin created previously

```bash
Create the UML sequence diagram based in plantuml format using the information provided with the cursor rule @2004-uml-sequence-diagram-from-agile-artifacts
```

**Note:** You can use the following tool to generate a png file from the diagram.

```bash
jbang puml-to-png@jabrena --watch example
```

**Note:** If the model generate a diagram which fails, you can visit the website: https://www.plantuml.com/plantuml/uml/ in order to test the code and see the error. With that information, you could provide the model to fix it.

**Note:** Sometimes, you need to ask for simplified version:

```bash
Can you create the diagram again with less detail
```

  - **2.2 [ ] Create the `C4 Model` diagrams based on the requirements**

**Note:** Attach the EPIC, Feature, User Story, Gherkin & UML Sequence diagram created previously

```bash
Create the C4 Model diagrams from the requirements in plantuml format using the information provided with the cursor rule @2005-c4-diagrams-about-solution
```

**Note:** Review the diagrams, sometimes it is necessary to simplify the models. Sometimes, the diagram some some incoherence or some criteria that you are not agree. Review the diagram and review the previous documents (Epic, Feature or User story) maybe exist the issue there.

### Phase 3: Architecture Decision Records (ADRs)

  - **3.1 [ ] Create an `ADR` about the functional requirements**

**Note:** Attach the EPIC, Feature, User Story, Gherkin, UML Sequence diagram & C4 Model diagrams created previously

**Terminal/CLI development:**

```bash
Create the ADR about functional requirements using the cursor rule @2006-adr-create-functional-requirements-for-cli-development
```

**REST API development:**

```bash
Create the ADR about the functional requirements using the information provided with the cursor rule @2006-adr-create-functional-requirements-for-rest-api-development
```

  - **3.2 [ ] Create an `ADR` about the acceptance testing Strategy**

**Note:** Attach User Story & Gherkin created previously

```bash
Create the ADR about the acceptance testing strategy using the information provided with the cursor rule @2007-adr-create-acceptance-testing-strategy
```

  - **3.3 [ ] Create an `ADR` about the non functional requirements**

**Note:** Attach the EPIC, Feature, User Story, Gherkin, UML Sequence diagram & C4 Model diagrams created previously

```bash
Create the ADR about the non functional requirements using the information provided with the cursor rule @2008-adr-create-non-functional-requirements-decisions
```

### Phase 4: Solution planning

  - **4.1 [ ] Create a tasks a list with a potential task list based on the Agile analysis & Technical design

```bash
create task list with @2100-create-task-list.md using documents @agile @design 
```

**Note:** Review the high level design if you are agree and later continue with the process for the sublist typing "Go"

### Phase 5: Solution Review & Design Validation

- **5. [ ] Review current solution state.**

 - **5.1 [ ] Create an UML class diagram**

**Note:** Once you have a solution stable, you could review some aspects about the Design, maybe you could see some way to improve:

```bash
Create the UML diagram based on @src/main/java using the cursor rule @2009-uml-class-diagram-mdc
```

---

## Available Cursor Rules Reference

| Rule ID | Purpose | When to Use |
|---------|---------|-------------|
| @2000-agile-checklist | Create a Checklist with all agile steps | Starting any agile development process |
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
| @2100-create-task-list | Generate detailed task lists from agile artifacts | After completing Phase 3 (ADRs) |
| @2200-uml-class-diagram-mdc | Create UML Class Diagram for Java Projects | For reviewing Java solution architecture |
| @2300-adr-conversational-assistant | ADR Conversational Assistant | For interactive ADR creation and refinement |

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