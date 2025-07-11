# Cursor rules for Agile

## Motivation

Using modern IDEs with AI capabilities like **Cursor**, software engineers can use those features to implement software using prompts. However, in order to help the models, it is a good idea to provide the software requirements, technical decisions, and designs in context.

Using conversational and non-conversational approaches, you can decompose your problems into `EPICs`, `Features` & `User Stories` if you follow `Agile` principles. With that analysis, you can answer the questions about **What** you are going to build, **Why** it is necessary to build, and **Who** is going to use the software. Apart from those questions, you should describe the technical specifications about **How** you will implement the software, and you can describe those restrictions using *ADR* files as the format to describe the functional and non-functional requirements. You can also model the design using diagrams in `UML` & `C4` format.

Models using specifications, decision records, and diagrams in context will generate better software with less ambiguity and less waste.

## Goals

This repository provides a set of cursor rules to generate good software specifications based on Agile principles to be added to the Cursor context to generate better software.

The repository can be used by the following roles: `EA`, `PO`, `BA`, `SA`, `TL`, `SSE`, `SE`

---

[![](./docs/agile-hierarchy.png)](
https://learn.microsoft.com/en-us/azure/devops/boards/backlogs/define-features-epics?view=azure-devops&tabs=agile-process)

https://learn.microsoft.com/en-us/azure/devops/boards/backlogs/define-features-epics?view=azure-devops&tabs=agile-process

## Cursor Rules

Cursor rules designed to answer the questions about **WHAT** the development team is going to build, **WHY** it is necessary to build, and **WHO** is going to use it:

| Phase | Role | Cursor Rule | Description |
|-------|------|-------------|-------------|
| Software requirements gathering | Product Owner | [Create an Agile Epic](.cursor/rules/2001-agile-create-an-epic.mdc) | Define and structure high-level business objectives as epics |
| Software requirements gathering | Product Owner | [Create an Agile Feature](.cursor/rules/2002-agile-create-features-from-epics.mdc) | Break down epics into manageable features |
| Software requirements gathering | Product Owner / Business Analyst | [Create an Agile User Story](.cursor/rules/2003-agile-create-user-story.mdc) | Create detailed user stories from features with acceptance criteria based on Gherkin |

Cursor rules designed to answer the questions about **HOW** the team is going to build the requirements and **WHAT** restrictions the software will have:

| Phase | Role | Cursor Rule | Description |
|-------|------|-------------|-------------|
| Design | Solution Architect | [UML Sequence Diagram](.cursor/rules/2004-uml-sequence-diagram-about-solution.mdc) | Generate UML sequence diagrams to visualize system interactions |
| Design | Solution Architect | [C4 Model Diagrams](.cursor/rules/2005-c4-diagrams-about-solution.mdc) | Create C4 architecture diagrams for system visualization |
| Requirements | Solution Architect / Technical Lead | [ADR about Functional requirements for CLI Development](.cursor/rules/2006-adr-create-functional-requirements-for-cli-development.mdc) | Create architectural decision records about functional requirements for CLI applications |
| Requirements | Solution Architect / Technical Lead | [ADR about Functional requirements for REST API](.cursor/rules/2006-adr-create-functional-requirements-for-rest-api-development.mdc) | Create architectural decision records about functional requirements for REST API development |
| Requirements | Solution Architect / Technical Lead | [ADR about Acceptance Testing Strategy](.cursor/rules/2007-adr-create-acceptance-testing-strategy.mdc) | Define comprehensive acceptance testing strategies |
| Requirements | Architect / Technical Lead | [ADR about Non-Functional Requirements](.cursor/rules/2008-adr-create-non-functional-requirements-decisions.mdc) | Define non-functional requirements and constraints following *ISO-25010* |
| Requirements | General | [ADR creation using a Conversational approach](.cursor/rules/2300-adr-conversational-assistant.mdc) | Interactive assistant for creating architectural decision records |

Cursor rules designed to implement the requirements and refactor:

| Phase | Role | Cursor Rule | Description |
|-------|------|-------------|-------------|
| Implementation | Product Owner / Technical Lead | [Create Task List](.cursor/rules/2100-create-task-list.mdc) | Generate detailed task lists from agile artifacts |
| Implementation | Technical Lead / Senior Software engineer | [Implement Task List](.cursor/rules/2101-implement-task-list.mdc) | Manage and track implementation of generated tasks |
| Implementation | Product Owner / Technical Lead | [Definition of Done Creation](.cursor/rules/2102-agile-create-dor.mdc) | Create comprehensive Definition of Done documents with quality gates and completion criteria |
| Refactoring | Technical Lead / Senior Software engineer | [UML Class Diagram Generator](.cursor/rules/2200-uml-class-diagram.mdc) | Generate UML class diagrams for object-oriented design |

## Getting Started

If you are interested in getting the benefits from these cursor rules, you can manually download this repository and copy the './cursor' folder and paste it into your repository, or delegate this task to a specific command-line tool based on **Jbang**:

```bash
sdk install jbang
# Add cursor rules for Agile in ./cursor/rules
jbang --fresh setup@jabrena init --cursor https://github.com/jabrena/cursor-rules-agile
```

Once you have installed the cursor rules:

| Phase | Role | Cursor Rule | Description |
|-------|------|-------------|-------------|
| Getting Started | All | [Create Agile Development Checklist](.cursor/rules/2000-agile-checklist.mdc) | Cursor rule designed to help the user to use the whole set of cursor rules for agile in an easy way |

Type the following prompt in the cursor chat:

![](./docs/getting-started-prompt.png)

```bash
Create an agile development checklist using the cursor rule @2000-agile-checklist
```

## Examples 

Learn about how these rules helped to implement some [non-trivial examples](https://github.com/jabrena/cursor-rules-examples)

## How to contribute

If you have nice ideas, please [create an issue](https://github.com/jabrena/cursor-rules-agile/issues)

## References

- https://www.cursor.com/
- https://docs.cursor.com/context/rules
- https://docs.cursor.com/context/@-symbols/@-cursor-rules
- https://learn.microsoft.com/en-us/azure/devops/boards/backlogs/define-features-epics?view=azure-devops&tabs=agile-process
- https://gojko.net/books/specification-by-example/
- https://cucumber.io/docs/gherkin/reference
- https://plantuml.com/
- https://www.plantuml.com/plantuml/uml/
- https://c4model.com/abstractions
- https://www.iso25000.com/index.php/en/iso-25000-standards/iso-25010
- https://www.iso.org/standard/78176.html
- https://github.com/snarktank/ai-dev-tasks

## Cursor rules ecosystem

- https://github.com/jabrena/101-cursor
- https://github.com/jabrena/cursor-rules-methodology
- https://github.com/jabrena/cursor-rules-agile
- https://github.com/jabrena/cursor-rules-java
- https://github.com/jabrena/cursor-rules-spring-boot
- https://github.com/jabrena/cursor-rules-examples
- https://github.com/jabrena/cursor-rules-sandbox
- https://github.com/jabrena/plantuml-to-png-cli
- https://github.com/jabrena/setup-cli
- https://github.com/jabrena/jbang-catalog
