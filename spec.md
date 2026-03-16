\# AI Doubt Hint Generator  
\*\*Developer Specification Document (spec.md)\*\*  
\---  
\# 1\. Product Overview  
\#\# 1.1 Product Name  
\*\*AI Doubt Hint Generator\*\*

\#\# 1.2 Product Description  
AI Doubt Hint Generator is an Android application that helps students solve \*\*Physics and Mathematics numerical problems\*\* by providing \*\*progressive hints instead of immediate full solutions\*\*.

The application encourages \*\*active learning and problem-solving\*\* by guiding students step-by-step toward the correct reasoning process.

Instead of displaying a complete solution immediately, the system:  
1\. Identifies the concept behind the question.  
2\. Provides progressive hints.  
3\. Encourages the student to think and attempt the solution.  
4\. Reveals the full explanation only after multiple hints or upon explicit request.

\---

\# 2\. Problem Statement

Students often encounter academic questions they cannot solve during practice sessions.

Typical solutions include:

\- Searching Google for full answers  
\- Using AI tools that provide immediate solutions  
\- Asking peers or teachers  
\- Using solution manuals

These approaches often lead to:

\- Skipping the reasoning process  
\- Weak conceptual understanding  
\- Poor retention  
\- Over-reliance on solutions

The application solves this by \*\*providing guided hints rather than direct answers\*\*, promoting deeper learning.

\---

\# 3\. Target Users

\#\# Primary Users  
Students preparing for:

\- Engineering entrance examinations  
\- Medical entrance examinations  
\- Board examinations

\#\# Subjects Supported  
Initial scope includes:

\- \*\*Physics numerical problems\*\*  
\- \*\*Mathematics numerical problems\*\*

\---

\# 4\. Core Product Principles

The system should:

\- Guide the student toward the answer  
\- Avoid revealing full solutions prematurely  
\- Encourage conceptual understanding  
\- Promote independent problem-solving

\---

\# 5\. Functional Requirements

\#\# 5.1 Question Input

Students must be able to submit a question using:

\- Manual typing  
\- Copy-paste from notes  
\- Copy from digital sources

\#\#\# Constraints  
\- \*\*Text input only\*\*  
\- Image input is not supported

\---

\#\# 5.2 AI Question Processing

Once a question is submitted:

The system must send the question to an \*\*external AI API\*\* that performs:

\- Concept identification  
\- Chapter identification  
\- Formula recognition  
\- Problem classification

\---

\# 6\. Hint Generation System

Each question supports \*\*a maximum of 6 hints\*\*.

Hints are revealed progressively.

\#\# Hint Levels

\#\#\# Hint 1 – Concept Identification  
The system identifies the relevant concept.

Example:  
"This problem involves the concept of rotational motion."

\---

\#\#\# Hint 2 – Student Reflection  
The system asks the student what they attempted.

Example:  
"What approach have you tried so far?"

\---

\#\#\# Hint 3 – Relevant Formulas  
The system provides key formulas required.

\---

\#\#\# Hint 4 – Approach Guidance  
The system suggests the method to begin solving.

\---

\#\#\# Hint 5 – Partial Solution Step  
A portion of the solving process is revealed.

\---

\#\#\# Hint 6 – Near Complete Reasoning  
The system provides almost complete reasoning without revealing the final answer.

\---

\# 7\. Solution Reveal

The final solution becomes available when:

\- The student explicitly requests it  
OR  
\- The student reaches the \*\*6th hint\*\*

\---

\#\# Final Solution Must Include

\- Step-by-step derivation  
\- Concept explanation  
\- Formula substitution  
\- Diagrams or visual explanation  
\- Common mistakes to avoid

\---

\# 8\. Similar Question Generation

After a question is completed, the system must generate \*\*similar practice questions\*\*.

\#\# Source

Questions are generated using the \*\*AI API\*\* based on the detected concept.

Example:

Original concept: Electrostatics  
Generated questions: 2–3 additional electrostatics problems.

\#\#\# Behavior  
Students must attempt these questions \*\*without hints initially\*\*.

\---

\# 9\. Non-Functional Requirements

\#\# Performance  
\- AI response time should ideally be \*\*\<5 seconds\*\*

\#\# Scalability  
Application is intended for \*\*local single-user use\*\*, not large-scale concurrent usage.

\#\# Security  
\- No sensitive data stored  
\- No authentication required

\#\# Availability  
Requires internet connection.

\---

\# 10\. System Architecture

\#\# Architecture Pattern  
\*\*MVI (Model–View–Intent)\*\*

\---

\#\# Architecture Layers

UI Layer (Jetpack Compose)  
↓  
Intent Layer  
↓  
ViewModel  
↓  
Repository  
↓  
API Service (Retrofit)  
↓  
External AI API

\---

\# 11\. Technology Stack

| Layer | Technology |  
|------|------------|  
Language | Kotlin  
UI | Jetpack Compose  
Architecture | MVI  
Networking | Retrofit  
HTTP Client | OkHttp  
AI | External AI API

\---

\# 12\. Application Modules

\#\# UI Module  
Responsible for:

\- Question input screen  
\- Hint display screen  
\- Solution screen  
\- Similar question screen

\---

\#\# ViewModel Module  
Handles:

\- User intents  
\- State management  
\- API requests

\---

\#\# Repository Module  
Handles:

\- API interaction  
\- Response parsing

\---

\#\# Network Module

Uses:

\- Retrofit  
\- OkHttp

Responsible for:

\- HTTP requests  
\- Error handling  
\- Response mapping

\---

\# 13\. Data Handling

\#\# Stateless Design

The application does \*\*not store user data\*\*.

No storage of:

\- User accounts  
\- Question history  
\- Performance tracking  
\- Local cache

Each request is handled independently.

User Question → API → Response → Display

\---

\# 14\. API Interaction

\#\# Request Structure

POST /generate-hint

\#\#\# Request Body

{  
 "question": "string",  
 "hint\_level": 1-6  
 }

\---

\#\# Response Structure

{  
 "concept": "string",  
 "hint": "string",  
 "formula": "string"  
 }

\---

\#\# Solution Request

POST /generate-solution

Response:

{  
 "step\_by\_step\_solution": "...",  
 "concept\_explanation": "...",  
 "common\_mistakes": "..."  
 }

\---

\#\# Similar Question Request

POST /generate-similar

Response:

{  
 "questions": \[  
 "question1",  
 "question2",  
 "question3"  
 \]  
 }

\---

\# 15\. Error Handling

\#\# Network Errors

Possible cases:

\- No internet connection  
\- Timeout  
\- API unavailable

\#\#\# Strategy

Display user message:

"Unable to connect. Please check your internet connection."

Retry option must be available.

\---

\#\# Invalid Question Input

Example:

\- Incomplete question  
\- Missing values

\#\#\# Strategy

AI should attempt to:

\- Interpret the question  
\- Ask for clarification

Example message:

"The question appears incomplete. Please provide additional details."

\---

\#\# Unsupported Question Type

If question is not Physics or Math:

Display message:

"This application currently supports Physics and Mathematics questions only."

\---

\# 16\. Content Safety

AI responses must be restricted to:

\- Physics  
\- Mathematics

The system must reject:

\- General chat  
\- Non-academic questions

\---

\# 17\. UX Flow

\#\# Step 1 – Home Screen

User enters question.

\[ Question Input Field \]

\[ Analyze Question \]

\---

\#\# Step 2 – Hint Screen

Displays first hint.

Options:

\[ Try Solving \]  
 \[ Next Hint \]  
 \[ Show Solution \]

\---

\#\# Step 3 – Additional Hints

Hints appear sequentially.

\---

\#\# Step 4 – Solution Screen

Displays full explanation.

\---

\#\# Step 5 – Practice Screen

Displays AI-generated similar questions.

\---

\# 18\. Error States UI

The UI must handle:

\- Loading state  
\- Network error state  
\- Empty input state

Example loading indicator:

"Analyzing question..."

\---

\# 19\. Testing Plan

\#\# Unit Testing

Test:

\- ViewModel logic  
\- Hint progression  
\- API response parsing

Tools:

\- JUnit  
\- Mockito

\---

\#\# UI Testing

Test:

\- Question input  
\- Hint navigation  
\- Solution reveal

Tools:

\- Espresso

\---

\#\# Integration Testing

Test:

\- Retrofit API calls  
\- Error responses  
\- Response handling

\---

\#\# Edge Case Testing

Test cases:

1\. Empty question input  
2\. Extremely long question  
3\. Unsupported subject  
4\. API timeout  
5\. Invalid API response

\---

\# 20\. Limitations

\- No offline support  
\- No question history  
\- No image input  
\- AI accuracy depends on API quality

\---

\# 21\. Future Enhancements

Potential improvements:

\- OCR for image questions  
\- Weak concept tracking  
\- Personalized learning analytics  
\- Multi-language support  
\- Offline AI models

\---

\# 22\. MVP Scope

Included in first version:

\- Question input  
\- AI concept detection  
\- Progressive hints (6 max)  
\- Final solution  
\- AI generated similar questions

\---

\# 23\. Summary

AI Doubt Hint Generator is designed to help students \*\*learn by thinking rather than copying answers\*\*.

Through progressive hints and guided reasoning, the application strengthens:

\- Conceptual understanding  
\- Analytical thinking  
\- Problem-solving ability

The system acts as a \*\*learning assistant\*\*, not a solution provider.

\---  
