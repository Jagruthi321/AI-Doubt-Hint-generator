# AI Execution Instructions

You are implementing the project defined in spec.md.

Rules:
1. Read spec.md before writing any code.
3. After completing the task:
    - implement the code
    - explain the changes
    - mark the task as completed.
4. Do NOT skip tasks.
5. Ensure code compiles.

\# AI Doubt Hint Generator — Development Checklist (todo.md)

This checklist provides a \*\*step-by-step implementation guide\*\* for building the Android application.  
Follow the sections sequentially to implement the project from setup to deployment.

\---

\# 1\. Project Setup

\#\# Environment Setup  
\- \[x\] Install latest \*\*Android Studio\*\*  
\- \[x\] Install \*\*Android SDK (API 34+)\*\*  
\- \[x\] Verify \*\*Kotlin support\*\*  
\- [x] Configure emulator or physical device for testing

\#\# Project Initialization  
\- [x] Create new Android project  
\- [x] Select **Empty Compose Activity**  
\- [x] Set language to **Kotlin**  
\- [x] Set **Minimum SDK (recommended: API 24+)**  
\- [x] Enable **Jetpack Compose**

\---

\# 2\. Project Architecture Setup

\#\# MVI Architecture Structure  
Create the following package structure:  
com.app.doubthint  
│  
├── ui  
│ ├── screens  
│ ├── components  
│ └── theme  
│  
├── intent  
│  
├── state  
│  
├── viewmodel  
│  
├── repository  
│  
├── network  
│  
├── model  
│  
└── utils

Checklist:

\- [x] Create UI package  
\- [x] Create Intent package  
\- [x] Create State package  
\- [x] Create ViewModel package  
\- [x] Create Repository package  
\- [x] Create Network package  
\- [x] Create Model package  
\- [x] Create Utils package

\---

\# 3\. Dependencies Configuration

Add dependencies to \*\*build.gradle\*\*

\#\#\# Core Dependencies  
\- [x] Kotlin Standard Library  
\- [x] Jetpack Compose UI  
\- [x] Compose Material 3  
\- [x] Compose Navigation

\#\#\# Architecture  
\- [x] Lifecycle ViewModel  
\- [x] Kotlin Coroutines  
\- [x] Flow support

\#\#\# Networking  
\- [x] Retrofit  
\- [x] OkHttp  
\- [x] Gson / Moshi converter

\#\#\# Testing  
\- [x] JUnit  
\- [x] Mockito  
\- [x] Espresso

\---

\# 4\. Network Layer Implementation

\#\# API Interface  
Create \*\*AI API Service Interface\*\*

Tasks:  
\- [x] Define \*\*generateHint endpoint\*\*  
\- [x] Define \*\*generateSolution endpoint\*\*  
\- [x] Define \*\*generateSimilarQuestions endpoint\*\*

\---

\#\# Retrofit Setup  
\- [x] Create Retrofit instance  
\- [x] Configure base URL  
\- [x] Add converter factory  
\- [x] Configure OkHttp client  
\- [x] Add logging interceptor

\---

\#\# Network Models  
Create request/response models.

\- [x] HintRequest  
\- [x] HintResponse  
\- [x] SolutionRequest  
\- [x] SolutionResponse  
\- [x] SimilarQuestionRequest  
\- [x] SimilarQuestionResponse

\---

\# 5\. Repository Layer

Responsibilities:

\- Handle API calls  
\- Manage responses  
\- Provide data to ViewModel

Tasks:

\- [x] Create \`HintRepository\`  
\- [x] Implement \`getHint()\`  
\- [x] Implement \`getSolution()\`  
\- [x] Implement \`getSimilarQuestions()\`  
\- [x] Handle API errors  
\- [x] Return clean domain models

\---

\# 6\. ViewModel Implementation

ViewModel responsibilities:

\- Process intents  
\- Call repository  
\- Update UI state

Tasks:

\- [x] Create \`HintViewModel\`  
\- [x] Define UI states  
\- [x] Handle question submission  
\- [x] Handle hint requests  
\- [x] Handle solution requests  
\- [x] Handle similar question requests  
\- [x] Manage hint counter (max \= 6\)

\---

\# 7\. Intent Layer

Define user actions.

Tasks:

Create \*\*Intent classes\*\*

\- [x] SubmitQuestionIntent  
\- [x] RequestHintIntent  
\- [x] RequestSolutionIntent  
\- [x] GenerateSimilarQuestionsIntent  
\- [x] RetryNetworkIntent

\---

\# 8\. State Management

Define UI states.

Create \`HintUIState\`

Possible states:

\- [x] Idle  
\- [x] Loading  
\- [x] HintReceived  
\- [x] SolutionReceived  
\- [x] SimilarQuestionsLoaded  
\- [x] ErrorState

\---

\# 9\. UI Development (Jetpack Compose)

\#\# Home Screen

Tasks:

\- [x] Create question input field  
\- [x] Add submit button  
\- [x] Validate empty input  
\- [x] Trigger question analysis

UI Elements:

\- TextField  
\- Button  
\- Loading indicator

\---

\#\# Hint Screen

Tasks:

\- [x] Display hint text  
\- [x] Show hint number (1–6)  
\- [x] Add \*\*Next Hint\*\* button  
\- [x] Add \*\*Show Solution\*\* button  
\- [x] Add \*\*Try Solving\*\* button

\---

\#\# Solution Screen

Tasks:

\- [x] Display step-by-step solution  
\- [x] Display concept explanation  
\- [x] Display formulas used  
\- [x] Display common mistakes

\---

\#\# Similar Questions Screen

Tasks:

\- [x] Display generated questions  
\- [x] Show question list  
\- [x] Allow copy or reuse

\---

\# 10\. Navigation

Implement navigation between screens.

Tasks:

\- [x] Add Navigation Compose  
\- [x] Create navigation routes  
\- [x] Navigate Home → Hint  
\- [x] Navigate Hint → Solution  
\- [x] Navigate Solution → Similar Questions

\---

\# 11\. Hint Progression Logic

Implement logic:

\- [x] Initialize hint counter \= 1  
\- [x] Allow max hints \= 6  
\- [x] Increment hint counter on request  
\- [x] Enable solution button after hint 6  
\- [x] Allow early solution request

\---

\# 12\. Input Validation

Ensure proper input.

Tasks:

\- [x] Detect empty input  
\- [x] Detect extremely short question  
\- [x] Show validation messages

Example messages:

\- "Please enter a question"  
\- "Question appears incomplete"

\---

\# 13\. Error Handling

\#\# Network Errors

Tasks:

\- [x] Handle timeout  
\- [x] Handle no internet  
\- [x] Handle API failure

UI Messages:

\- "Network connection lost"  
\- "Unable to reach AI service"

\---

\#\# API Response Errors

Tasks:

\- [x] Handle malformed responses  
\- [x] Handle empty responses  
\- [x] Show fallback error message

\---

\#\# Unsupported Question

Tasks:

\- \[ \] Detect non-math/non-physics question  
\- [x] Display warning message

\---

\# 14\. Loading States

Tasks:

\- [x] Show loading spinner during API calls  
\- [x] Disable buttons during loading  
\- [x] Show "Analyzing question..." message

\---

\# 15\. AI Content Filtering

Ensure AI only responds to:

\- Physics questions  
\- Mathematics questions

Tasks:

\- [x] Validate subject classification  
\- [x] Reject unrelated questions

\---

\# 16\. Performance Optimization

Tasks:

\- \[ \] Ensure API calls are asynchronous  
\- \[ \] Use coroutines  
\- \[ \] Avoid UI thread blocking

\---

\# 17\. Testing

\#\# Unit Tests

Tasks:

\- \[ \] Test ViewModel logic  
\- \[ \] Test hint progression logic  
\- \[ \] Test repository responses  
\- \[ \] Test error handling

\---

\#\# UI Tests

Tasks:

\- \[ \] Test question input  
\- \[ \] Test hint navigation  
\- \[ \] Test solution reveal  
\- \[ \] Test similar question generation

\---

\#\# Integration Tests

Tasks:

\- \[ \] Test Retrofit API calls  
\- \[ \] Test API response parsing  
\- \[ \] Test error scenarios

\---

\# 18\. Edge Case Testing

Test scenarios:

\- \[ \] Empty question input  
\- \[ \] Extremely long question  
\- \[ \] Unsupported subject  
\- \[ \] Invalid AI response  
\- \[ \] Network disconnection

\---

\# 19\. UX Improvements

Tasks:

\- \[ \] Improve readability of hints  
\- \[ \] Ensure responsive layout  
\- \[ \] Add proper spacing and typography  
\- \[ \] Ensure accessibility

\---

\# 20\. Final QA

Before release verify:

\- \[ \] All screens function correctly  
\- \[ \] Navigation works  
\- \[ \] Hint logic works  
\- \[ \] Solution display works  
\- \[ \] Similar questions load correctly  
\- \[ \] Errors handled properly

\---

\# 21\. Build & Release

Tasks:

\- \[ \] Generate release build  
\- \[ \] Test APK on real device  
\- \[ \] Optimize build size  
\- \[ \] Prepare release notes

\---

\# 22\. Post Release (Optional)

Future improvements:

\- \[ \] Image-based question input  
\- \[ \] Question history  
\- \[ \] Weak concept tracking  
\- \[ \] Multi-language support  
\- \[ \] Offline hint model

\---

\# Completion Checklist

Before declaring MVP complete:

\- \[ \] Question input implemented  
\- \[ \] AI hint system working  
\- \[ \] 6-level hint flow implemented  
\- \[ \] Solution generation working  
\- \[ \] Similar question generation working  
\- \[ \] Error handling complete  
\- \[ \] UI tested  
\- \[ \] Application stable

\---

\*\*End of todo.md\*\*