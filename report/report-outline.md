# Streams Everywhere Report Outline

Status: archived planning document. The finalized submission is
`final/Streams_Everywhere_Final.pdf`.

## Working Title

Streams Everywhere: Comparing Local Pipelines and HTTP in Java

## Thesis Direction

Java uses streams in both local process communication and network-based HTTP
communication, but the behavior, control, observability, and failure handling
are different enough that developers need different mental models for each.

## Section Outline

### 1. Introduction

- State the project question
- Explain why stream-based communication matters in real systems
- Preview the comparison: local IPC versus HTTP

### 2. Background

- Explain Java streams at a high level
- Explain local process communication with `ProcessBuilder`
- Explain HTTP request/response flow with `HttpClient`

### 3. Local IPC In Java

- `inheritIO()` behavior
- Captured stdout and stderr
- Redirected pipes
- File redirection
- Exit codes and parent/child coordination
- Buffering and timing observations

### 4. HTTP Streams In Java

- Request and response lifecycle
- TLS handshake happens before the HTTP response is available
- Certificate-chain and trust-store validation can fail before any status code is returned
- Status codes
- Response headers
- Body bytes versus decoded text
- Character encoding and `Content-Type`
- Redirects and authentication-related responses

### 5. Comparison

- Where both models are similar
- Where both models differ
- Error signaling: stderr and exit codes versus status codes and headers
- HTTPS can also fail before HTTP-level signaling if TLS trust validation fails
- Observability and debugging tradeoffs
- Reliability and usability tradeoffs

### 6. Demo Findings

- IPC demo observations
- HTTP demo observations
- Java `HttpClient` may fail on some HTTPS targets with `SSLHandshakeException`
  before any HTTP response is available
- Certificate-path errors such as `PKIX path building failed` show a network trust
  dependency that does not exist in local IPC
- Evidence-file references

### 7. Conclusion

- Answer the project question directly
- Summarize the most important practical takeaways

## Source Checklist

- At least 8 academic or industry sources
- Mix Java documentation with reputable technical references
- Prefer sources that directly discuss `ProcessBuilder`, `HttpClient`, HTTP
  semantics, stream handling, buffering, and character encodings
- If discussing TLS failures, include one source on Java TLS or certificate-path
  validation

## Evidence Checklist

- Terminal output for `inheritIO()` and captured output
- Pipeline run output
- File redirection example
- HTTP response examples for different status codes
- One HTTPS handshake failure example showing `SSLHandshakeException` or
  `PKIX path building failed`
- Notes about headers such as `Content-Type`, `Location`, and
  `WWW-Authenticate`
