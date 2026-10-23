# Repair Calculator

Android project for organizing renovation projects, rooms, materials, notes, prices and work stages. Includes local storage and chart-based statistics.

## What is in the source

- Room entities and DAOs for projects, rooms, materials, notes, prices, stages and users.
- Kotlin coroutines and Flow for asynchronous data access.
- ViewModels, LiveData, Android Navigation and Hilt for application structure.
- Statistics screens using MPAndroidChart, including work-stage summaries.
- A drawing screen referencing a separate drawing-library module.

## Technology

Kotlin, Java, Android Views, Room/SQLite, Coroutines, Flow, Hilt, MVVM and MPAndroidChart. The checked-in configuration targets SDK 33 with minimum SDK 24.

## Explore

| Path | Contents |
|---|---|
| `app/src/main/java/com/example/repaircalculator/data/` | Relational entities and DAO queries |
| `app/src/main/java/com/example/repaircalculator/ui/screen/` | Project, room, material, note and statistics screens |
| `app/src/main/java/com/example/repaircalculator/ui/screen/statistics/` | Chart construction and statistics data access |
| `app/src/main/java/com/example/repaircalculator/di/` | Database dependency configuration |

## Current build limitation

`settings.gradle` and `app/build.gradle` reference `:cidrawinglib`, but that module is not present in this public repository. A fresh checkout needs the original compatible module or a deliberate replacement before a complete build can be expected. This documentation update does not claim a successful build or runtime validation.

## Portfolio context

This earlier Android project provides examples of relational modeling, SQL-backed persistence and visual reporting within an application. Current security labs are linked from the [profile](https://github.com/paulhorda).
