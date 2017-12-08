# Sprint 5 - T15 - Wolf Pack
## Goal

## Reliable first release with clean code!

## Definition of Done

* Ready for demo / customer release.
* Sprint Review and Restrospectives completed.
* Product Increment release `v5.0` created on GitHub with appropriate version number and name, a description based on the template, and an executable JAR file for the demo.
* Version in pom.xml should be `<version>5.0.0</version>`.
* Unit tests for all new features and public methods at a minimum.
* Coverage at least 50% overall and for each class.
* Clean continuous integration build/test on master branch.

## Policies

* Tests and Javadoc are written before/with code.  
* All pull requests include tests for the added or modified code.
* Master is never broken.  If broken, it is fixed immediately.
* Always check for new changes in master to resolve merge conflicts locally before committing them.
* All changes are built and tested before they are committed.
* Continuous integration always builds and tests successfully.
* All commits with more than 1 line of change include a task/issue number.
* All Java dependencies in pom.xml.
* Code inspection for most scrum meeting


## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks |  28    | 40
Story Points |  52  | 70


Statistic | Start | End
--- | ---: | ---:
Overall Test Coverage | 81 | 84 
Smells | 35 | 17
Duplication | 55 | 16
Technical Debt Ratio | 19.7 | 4.3

## Plan

Epics planned for this release.

* #358 - Map with zoom and pan 
* #360 - Select a starting location
* #357 - Clean Code
* #356 - Test Coverage
* #378 - Code Cleanup - App.js 

## Daily Scrums

Date | Tasks done now | Tasks done next | Impediments | Coverage | Smells | Duplication | Technical Debt Ratio
:--- | :--- | :--- | :--- | ---: | ---: | ---: | ---:
11-15 | #362 |  | None | 81 | 35 | 55 | 19.7
 11-27|#365 #364 #369 #368 #395 #330 #388| #383 #362 | Other classes, refactoring, React | 83 | 28 | 38 | 14.3 |
 11-29 | #382 #383 #381 #361 #363 #366 #399 #367 | #371 | Other Classes, React | 84 | 16 | 16 | 4.4
 12-4 || #409 #371 #376 | Other Classes | 84 | 16 | 16 | 4.4
 12-6 | #371 #376 | #409 #415 #376 #418 | Other classes | 84 | 16 | 16 | 4.3
 12-8| #375 #376 #377 #409 #415 #417 #418 #423 #372 #413 #422 #358 #356 #357 #360 | | none| 84 | 17 | 16 | 4.3
## Review

#### Completed user stories (epics) in Sprint Backlog 
* #358: Creating the kml file was more complicated than expected
* #356: No major issues
* #372: Very successful
* #357: Slight impovement 81%-84%
* #360: Significantly improvied reacts implemtation 

#### Incomplete user stories / epics in Sprint Backlog 
* None
#### What went well
* Code clean up
* Code coverage

#### Problems encountered and resolutions
* React: hours of google searches
* KML file saving: Build file from scratch

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we will change this time |Actively find a better way to communicate - possibly Slack | Using code climate better - don't push unless it looks pretty good, Stop putting up pull requests with multiple issues | Switch to Slack for Communication and possibly meeting in CS120 Lab for work
What we did well | Working together in the 120 lab make debuging easier | Better cooperation working together in the lab | meeting in person worked best for us
What we need to work on | Communicating outside of meetings | Updating the zenboard | find a communication tool we actually use
What we will change next time | more team building excersises | more commits | find a beeter communication tool
