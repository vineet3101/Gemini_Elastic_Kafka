Spring Boot (Backend) + React (Frontend)

System Architecture (Concrete)
React Chat UI
     |
     | POST /api/chat
     v
Spring Boot MCP Gateway
     |
     | Intent + Context Resolution
     v
MCP Context Engine
     |
     | Adapter call
     v
Google Sheets MCP Adapter
     |
     v
Google Sheets API

Project Structure
mcp-backend/
 ├── src/main/java/com/example/mcp
 │    ├── McpApplication.java
 │    ├── controller/
 │    │     └── ChatController.java
 │    ├── engine/
 │    │     ├── IntentResolver.java
 │    │     ├── ContextEngine.java
 │    ├── adapter/
 │    │     ├── MCPContextAdapter.java
 │    │     └── GoogleSheetsAdapter.java
 │    ├── model/
 │    │     ├── ChatRequest.java
 │    │     ├── ChatResponse.java
 │    │     └── ContextData.java
 │    └── config/
 │          └── GoogleSheetsConfig.java
 └── pom.xml
 
 
React App Structure
mcp-frontend/
 ├── src/
 │    ├── App.js
 │    ├── Chat.js
 │    └── api.js
 
==================================================================================
REQUIRED GOOGLE CLOUD STEPS (Non-Optional)

Your app will still fail later unless you do these:

✅ Step 1: Create Service Account
Google Cloud Console
→ IAM & Admin
→ Service Accounts
→ Create Service Account

✅ Step 2: Enable Google Sheets API
APIs & Services → Enable APIs → Google Sheets API

✅ Step 3: Download JSON Key

This is your real google.json

Replace the sample content

✅ Step 4: Share the Sheet

Share your Google Sheet with:

mcp-sheets-reader@my-gcp-project-id.iam.gserviceaccount.com


Permission: Viewer

==================================================================================