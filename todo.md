# AI Execution Instructions

You are implementing the project defined in spec.md.

Rules:
1. Read spec.md before writing any code.
2. Complete ONLY the first unchecked task in this todo list.
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
\- \[ \] Install latest \*\*Android Studio\*\*  
\- \[ \] Install \*\*Android SDK (API 34+)\*\*  
\- \[ \] Verify \*\*Kotlin support\*\*  
\- \[ \] Configure emulator or physical device for testing

\#\# Project Initialization  
\- \[ \] Create new Android project  
\- \[ \] Select \*\*Empty Compose Activity\*\*  
\- \[ \] Set language to \*\*Kotlin\*\*  
\- \[ \] Set \*\*Minimum SDK (recommended: API 24+)\*\*  
\- \[ \] Enable \*\*Jetpack Compose\*\*

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

\- \[ \] Create UI package  
\- \[ \] Create Intent package  
\- \[ \] Create State package  
\- \[ \] Create ViewModel package  
\- \[ \] Create Repository package  
\- \[ \] Create Network package  
\- \[ \] Create Model package  
\- \[ \] Create Utils package

\---

\# 3\. Dependencies Configuration

Add dependencies to \*\*build.gradle\*\*

\#\#\# Core Dependencies  
\- \[ \] Kotlin Standard Library  
\- \[ \] Jetpack Compose UI  
\- \[ \] Compose Material 3  
\- \[ \] Compose Navigation

\#\#\# Architecture  
\- \[ \] Lifecycle ViewModel  
\- \[ \] Kotlin Coroutines  
\- \[ \] Flow support

\#\#\# Networking  
\- \[ \] Retrofit  
\- \[ \] OkHttp  
\- \[ \] Gson / Moshi converter

\#\#\# Testing  
\- \[ \] JUnit  
\- \[ \] Mockito  
\- \[ \] Espresso

\---

\# 4\. Network Layer Implementation

\#\# API Interface  
Create \*\*AI API Service Interface\*\*

Tasks:  
\- \[ \] Define \*\*generateHint endpoint\*\*  
\- \[ \] Define \*\*generateSolution endpoint\*\*  
\- \[ \] Define \*\*generateSimilarQuestions endpoint\*\*

\---

\#\# Retrofit Setup  
\- \[ \] Create Retrofit instance  
\- \[ \] Configure base URL  
\- \[ \] Add converter factory  
\- \[ \] Configure OkHttp client  
\- \[ \] Add logging interceptor

\---

\#\# Network Models  
Create request/response models.

\- \[ \] HintRequest  
\- \[ \] HintResponse  
\- \[ \] SolutionRequest  
\- \[ \] SolutionResponse  
\- \[ \] SimilarQuestionRequest  
\- \[ \] SimilarQuestionResponse

\---

\# 5\. Repository Layer

Responsibilities:

\- Handle API calls  
\- Manage responses  
\- Provide data to ViewModel

Tasks:

\- \[ \] Create \`HintRepository\`  
\- \[ \] Implement \`getHint()\`  
\- \[ \] Implement \`getSolution()\`  
\- \[ \] Implement \`getSimilarQuestions()\`  
\- \[ \] Handle API errors  
\- \[ \] Return clean domain models

\---

\# 6\. ViewModel Implementation

ViewModel responsibilities:

\- Process intents  
\- Call repository  
\- Update UI state

Tasks:

\- \[ \] Create \`HintViewModel\`  
\- \[ \] Define UI states  
\- \[ \] Handle question submission  
\- \[ \] Handle hint requests  
\- \[ \] Handle solution requests  
\- \[ \] Handle similar question requests  
\- \[ \] Manage hint counter (max \= 6\)

\---

\# 7\. Intent Layer

Define user actions.

Tasks:

Create \*\*Intent classes\*\*

\- \[ \] SubmitQuestionIntent  
\- \[ \] RequestHintIntent  
\- \[ \] RequestSolutionIntent  
\- \[ \] GenerateSimilarQuestionsIntent  
\- \[ \] RetryNetworkIntent

\---

\# 8\. State Management

Define UI states.

Create \`HintUIState\`

Possible states:

\- \[ \] Idle  
\- \[ \] Loading  
\- \[ \] HintReceived  
\- \[ \] SolutionReceived  
\- \[ \] SimilarQuestionsLoaded  
\- \[ \] ErrorState

\---

\# 9\. UI Development (Jetpack Compose)

\#\# Home Screen

Tasks:

\- \[ \] Create question input field  
\- \[ \] Add submit button  
\- \[ \] Validate empty input  
\- \[ \] Trigger question analysis

UI Elements:

\- TextField  
\- Button  
\- Loading indicator

\---

\#\# Hint Screen

Tasks:

\- \[ \] Display hint text  
\- \[ \] Show hint number (1–6)  
\- \[ \] Add \*\*Next Hint\*\* button  
\- \[ \] Add \*\*Show Solution\*\* button  
\- \[ \] Add \*\*Try Solving\*\* button

\---

\#\# Solution Screen

Tasks:

\- \[ \] Display step-by-step solution  
\- \[ \] Display concept explanation  
\- \[ \] Display formulas used  
\- \[ \] Display common mistakes

\---

\#\# Similar Questions Screen

Tasks:

\- \[ \] Display generated questions  
\- \[ \] Show question list  
\- \[ \] Allow copy or reuse

\---

\# 10\. Navigation

Implement navigation between screens.

Tasks:

\- \[ \] Add Navigation Compose  
\- \[ \] Create navigation routes  
\- \[ \] Navigate Home → Hint  
\- \[ \] Navigate Hint → Solution  
\- \[ \] Navigate Solution → Similar Questions

\---

\# 11\. Hint Progression Logic

Implement logic:

\- \[ \] Initialize hint counter \= 1  
\- \[ \] Allow max hints \= 6  
\- \[ \] Increment hint counter on request  
\- \[ \] Enable solution button after hint 6  
\- \[ \] Allow early solution request

\---

\# 12\. Input Validation

Ensure proper input.

Tasks:

\- \[ \] Detect empty input  
\- \[ \] Detect extremely short question  
\- \[ \] Show validation messages

Example messages:

\- "Please enter a question"  
\- "Question appears incomplete"

\---

\# 13\. Error Handling

\#\# Network Errors

Tasks:

\- \[ \] Handle timeout  
\- \[ \] Handle no internet  
\- \[ \] Handle API failure

UI Messages:

\- "Network connection lost"  
\- "Unable to reach AI service"

\---

\#\# API Response Errors

Tasks:

\- \[ \] Handle malformed responses  
\- \[ \] Handle empty responses  
\- \[ \] Show fallback error message

\---

\#\# Unsupported Question

Tasks:

\- \[ \] Detect non-math/non-physics question  
\- \[ \] Display warning message

\---

\# 14\. Loading States

Tasks:

\- \[ \] Show loading spinner during API calls  
\- \[ \] Disable buttons during loading  
\- \[ \] Show "Analyzing question..." message

\---

\# 15\. AI Content Filtering

Ensure AI only responds to:

\- Physics questions  
\- Mathematics questions

Tasks:

\- \[ \] Validate subject classification  
\- \[ \] Reject unrelated questions

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