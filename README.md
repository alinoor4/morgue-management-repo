# Morgue Management System

A Java-based Command Line Interface (CLI) application designed to simulate and manage the workflow of a morgue. The system handles the entire lifecycle of body processing—from initial requests and cold storage allocation to autopsy reporting and final body return. It features a robust Role-Based Access Control (RBAC) system, ensuring secure and distinct functionality for Administrators, Workers, and Requesters.

## Key Features:

### Role-Based Authentication:
- **Admin:** Complete system control, including user management (Create/Update/Delete accounts) and full access to morgue operations.
- **Worker:** Operational staff access to process body requests, perform autopsies, and return bodies to guardians.
- **Requester:** Restricted access to submit new body requests and track the status of submitted requests using a unique ID.

### Morgue Operations & Workflow:
- **Request Management:** Requesters can submit details of a deceased person (Guardian, ID, Name, Date/Cause of Death). These requests enter a pending state.
- **Admission Control:** Workers/Admins review pending requests. The system enforces a Cold Storage Limit (default: 5 bodies). If storage is full, new bodies cannot be accepted until existing ones are returned.
- **Autopsy Processing:** Accepted bodies undergo autopsy. Staff can update records with autopsy results, changing the status to "Completed".
- **Body Return:** Once processing is complete, bodies are marked as "Returned," clearing space in the cold storage.

### Data Persistence:
- Uses custom flat-file databases (Requests.txt and Login.txt) to store user credentials and body records.
- Data is serialized using a custom delimiter to ensure field separation and integrity.

## Technical Implementation
- **Language:** Java
- **Architecture:** Modular design separating UI (Menu), Logic (TheMorgue, BodyRequester), and Authentication (UserLogin).
- **Storage:** File I/O (Text-based persistence).
- **Testing:** JUnit 5.

## Workflow: 

1. **Submission**
   - **Actor:** Requester
   - **Action:** Submits deceased details (Guardian, ID, Name, Cause of Death).
   - **Status:** `false` (Pending)

2. **Review & Admission**
   - **Actor:** Worker / Admin
   - **Action:** Reviews request and checks Cold Storage capacity (Limit: 5).
   - **Outcome:** 
        - *Accept:* Status updates to `accepted`.
        - *Reject:* Status updates to `rejected`.

3. **Processing**
   - **Actor:** Worker / Admin
   - **Action:** Performs autopsy and records results.
   - **Status:** Updates to `completed`.

4. **Conclusion**
   - **Actor:** Worker / Admin
   - **Action:** Body is returned to the guardian; storage space is freed.
   - **Status:** Updates to `returned`.
