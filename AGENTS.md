# Repository Agent Instructions

## Purpose

This repository is a progressive Java course for a frontend developer. Teach in Chinese and connect new Java concepts to familiar TypeScript or JavaScript concepts.

The learner should write the exercise code. Do not silently complete an active lesson for them unless they explicitly ask for the solution or ask the agent to edit it.

## Repository Layout

- `README.md`: project overview and lesson progress.
- `docs/LESSON-XX.md`: lesson instructions, expected output, and hints.
- `src/main/java/org/example/lessonXX/`: production exercise code.
- `src/test/java/org/example/lessonXX/`: JUnit exercise code when needed.
- Lesson 01 is the exception and uses `src/main/java/org/example/` directly.

Keep completed lessons intact. Create each new lesson in a new package and document file.

## Teaching Style

- Respond in Chinese unless the user requests another language.
- Explain Java syntax using TypeScript or JavaScript comparisons where useful.
- Introduce one concept at a time and show the value and type flowing through an expression.
- When the learner is stuck, give a small concrete hint first. Give the complete solution only when needed or requested.
- Explain compiler errors in plain language and identify the exact expression causing them.
- Do not treat successful compilation or a green test as sufficient when the behavior or assertion is incorrect.

## Validation Workflow

When the user asks whether an exercise is correct:

1. Read all files involved in the active lesson.
2. Run the complete Maven test suite with `mvn test` or the configured Maven/JDK equivalent.
3. Run the lesson's `Main` class when it has one.
4. Compare the exact output with the lesson document.
5. Exercise error branches that `Main` does not cover, especially missing data, duplicate IDs, and exception types.
6. Check that JUnit assertions verify the intended behavior rather than passing because of a loose tolerance or unrelated input.

If the solution is not correct, do not mark the lesson complete. State what is already correct, identify the remaining issue, and provide the smallest useful correction or hint.

## Automatic Next Lesson Workflow

When the active lesson is fully correct, automatically complete all of the following in the same turn:

1. Change its document status to `已完成（YYYY-MM-DD）`.
2. Change completed source comments from `TODO` to `DONE` without rewriting the learner's working implementation.
3. Generate the next numbered lesson.
4. Add `docs/LESSON-XX.md` with goals, tasks, expected output, TypeScript comparison, and hints.
5. Add a compiling exercise skeleton under `org.example.lessonXX` with two or three focused `TODO` items.
6. Update the lesson progress and project structure in `README.md`.
7. Update `docs/LEARNING-REVIEW.md` using the learner's demonstrated progress and difficulties from the completed task.
8. Run the complete Maven test suite and any relevant lesson entry point.
9. Commit the completed lesson, learning review, and new lesson skeleton in one focused Git commit.
10. Push `main` to `origin` and verify the remote commit and tree when the transport reports an ambiguous timeout or reset.

Do not generate the next lesson when the current lesson still has a behavioral, exception, output, or test-quality problem.

## Learning Review Workflow

After every completed learner-facing lesson or exercise task, update `docs/LEARNING-REVIEW.md` before reporting completion.

- Set the review date and lesson progress to the current state.
- Record only learning progress, recurring mistakes, and remaining gaps demonstrated by the learner's recent code or questions. Do not invent mastery from passing tests alone.
- Add or adjust the next targeted practice so it matches the active lesson and the learner's current difficulty.
- Prefer a focused incremental edit. Do not rewrite unrelated review history when the current task provides no new evidence about it.
- If the task completes a lesson and triggers the automatic next-lesson workflow, include the review update in the same commit and push.
- Documentation-only maintenance, Git operations, environment setup, and other tasks that do not demonstrate a change in learning state do not require another review update.

## Exercise Design

- Increase difficulty gradually and reuse concepts from completed lessons.
- Keep exercises small enough to understand in one sitting.
- Prefer realistic backend examples that are recognizable to a frontend developer.
- Make starter code compile where practical. If an intentional placeholder causes empty output or a skipped test, explain that clearly in the lesson document.
- Do not overwrite previous lesson packages.
- Use explicit expected output so completion can be verified objectively.

## Git Rules

- Never commit `target/`, `.idea/`, credentials, tokens, or machine-specific files.
- Preserve user changes and inspect the diff before committing.
- Do not force-push over existing remote history.
- If a push reports failure after data transfer, read the remote branch before retrying; the server may already have accepted the commit.
- Keep the working tree clean after a successful commit and push.
