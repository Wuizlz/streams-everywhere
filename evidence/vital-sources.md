# Vital Sources For Current Implementation

Date collected: 2026-03-24
Last updated: 2026-05-05

This file collects source references for the APIs and protocol behavior used by
the current implementation.

Important: these are supporting sources, not execution evidence.

- Source evidence = documentation, standards, chapter material, research
- Runtime evidence = terminal output, generated files, and demo results from
  this project

## Current Code Covered

### IPC Demo

File: `ipc-demo/ProcessBuilderIpcDemo.java`

Used features:

- `ProcessBuilder.inheritIO()`
- `ProcessBuilder.startPipeline(...)`
- `redirectOutput(...)`
- `redirectError(...)`
- child-process exit codes and stream redirection

### HTTP Demo

File: `http-demo/FetchURLDemo.java`

Used features:

- `HttpClient.newBuilder()`
- `HttpClient.Builder.followRedirects(...)`
- `HttpClient.send(...)`
- `HttpRequest.newBuilder()`
- `HttpRequest.Builder.uri(...)`
- `HttpRequest.Builder.GET()`
- `HttpResponse.statusCode()`
- `HttpResponse.headers()`
- `HttpResponse.BodyHandlers.ofByteArray()`
- `Content-Type` header parsing
- `Location` and `WWW-Authenticate` header inspection
- `Charset.forName(...)`

## Primary Sources

### 1. ProcessBuilder API

Title: Oracle Java SE 25 ProcessBuilder API
URL: https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/ProcessBuilder.html

Why it matters:

- defines `inheritIO()`
- defines `redirectInput`, `redirectOutput`, and `redirectError`
- defines `startPipeline(...)`
- confirms `start()` creates operating system processes and may throw
  `IOException`

Use this source for:

- explaining inherited terminal I/O
- explaining redirected output and error streams
- explaining multi-process pipelines

### 2. HttpClient API

Title: Oracle Java SE 25 HttpClient API
URL: https://docs.oracle.com/en/java/javase/25/docs/api/java.net.http/java/net/http/HttpClient.html

Why it matters:

- documents the client builder
- documents redirect policy configuration
- documents synchronous `send(...)`

Use this source for:

- explaining how the HTTP client is configured
- explaining why `followRedirects(HttpClient.Redirect.NEVER)` leaves `3xx`
  responses for manual inspection

### 3. HttpClient.Redirect API

Title: Oracle Java SE 25 HttpClient.Redirect API
URL: https://docs.oracle.com/en/java/javase/25/docs/api/java.net.http/java/net/http/HttpClient.Redirect.html

Why it matters:

- states that the redirect policy is checked whenever a `3XX` response code is
  received
- confirms that if redirection does not happen automatically, the `3XX`
  response is returned for manual handling

Use this source for:

- justifying the current choice to inspect redirect responses directly

### 4. HttpRequest API

Title: Oracle Java SE 25 HttpRequest API
URL: https://docs.oracle.com/en/java/javase/25/docs/api/java.net.http/java/net/http/HttpRequest.html

Why it matters:

- documents request creation with a builder
- documents setting the request URI
- shows GET request construction

Use this source for:

- explaining how the HTTP request object is created

### 5. HttpResponse API

Title: Oracle Java SE 25 HttpResponse API
URL: https://docs.oracle.com/en/java/javase/25/docs/api/java.net.http/java/net/http/HttpResponse.html

Why it matters:

- documents `statusCode()`
- documents `headers()`
- documents access to the response body

Use this source for:

- explaining how the demo reads response metadata and body data

### 6. HttpResponse.BodyHandlers API

Title: Oracle Java SE 25 HttpResponse.BodyHandlers API
URL: https://docs.oracle.com/en/java/javase/25/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandlers.html

Why it matters:

- documents `ofByteArray()`
- explains that the body can be received as raw bytes
- explains the default `ofString()` charset behavior, which is useful when
  comparing the built-in behavior to the manual decoding approach in our code

Use this source for:

- explaining why the demo first captures raw bytes and then decodes them

### 7. Charset API

Title: Oracle Java SE 24 Charset API
URL: https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/nio/charset/Charset.html

Why it matters:

- documents `Charset.forName(...)`
- documents what happens when a charset name is unsupported
- lists standard charsets and notes UTF-8 support

Use this source for:

- explaining the manual charset resolution logic in the HTTP demo

## Protocol Source

### 8. RFC 9110: HTTP Semantics

Title: RFC 9110, HTTP Semantics
URL: https://www.rfc-editor.org/rfc/rfc9110

Relevant sections:

- Section 8.3: `Content-Type`
- Section 10.2.2: `Location`
- Section 11.6.1: `WWW-Authenticate`
- Section 15: Status codes overview

Why it matters:

- defines what `Content-Type` means
- defines `Location` in redirect-related responses
- defines `WWW-Authenticate` in authentication challenges
- defines status code classes such as `2xx`, `3xx`, and `4xx`

Use this source for:

- explaining why the HTTP demo prints `content-type`, `location`, and
  `www-authenticate`
- explaining the meaning of `200`, redirect status codes such as `301`, `401`,
  and `403`

## Existing Course Materials In This Repo

These are useful as background sources, but they do not prove that our
implementation worked.

### Local Kraft Course Notes

Files:

- `evidence/HTTP - CS_33600.pdf`
- `evidence/Network clients - CS_33600.pdf`
- `evidence/ProcessBuilder - CS_33600.pdf`
- `evidence/Streams - CS_33600.pdf`

Original URLs:

- http://math.pnw.edu/~rlkraft/cs33600/for-class/readmes/Readme_3_streams/
- http://math.pnw.edu/~rlkraft/cs33600/for-class/readmes/Readme_4_ProcessBuilder/
- http://math.pnw.edu/~rlkraft/cs33600/for-class/readmes/Readme_10_network_clients/
- http://math.pnw.edu/~rlkraft/cs33600/for-class/readmes/Readme_12_http/

Why they matter:

- `Streams - CS_33600.pdf` includes diagrams for standard streams,
  redirection, pipes, and filter pipelines.
  - useful pages: 2, 8-19
- `ProcessBuilder - CS_33600.pdf` includes diagrams for default child-process
  pipes, `inheritIO()`, redirection, and `startPipeline(...)`.
  - useful pages: 5-6, 16-17
- `Network clients - CS_33600.pdf` includes diagrams comparing same-host IPC
  pipes with network IPC through sockets.
  - useful pages: 3-5
- `HTTP - CS_33600.pdf` explains HTTP request/response structure, status
  codes, headers, body bytes, redirection, and persistent connections.
  - useful pages: 3-5, 8-9

Use these in the report for:

- visual support for the pipe and pipeline model
- visual support for the socket/client/server model
- explaining why HTTP adds message syntax on top of streamed bytes
- connecting the demos to course concepts

Note: the PDFs are the local copies for the four Kraft sources cited in the
final report. The previously downloaded HTML copies were removed because the
PDFs support stable page-number citations.

### 9. Chapter 13 Material

Files:

- `../Fun/Resources/Chapter13.pdf`
- `../Fun/Resources/Questions/c13.md`

Why it matters:

- covers TCP and UDP background
- explains sockets, byte streams, datagrams, and `URLConnection`
- includes URL encoding and `openConnection()` notes

Useful note references:

- `../Fun/Resources/Questions/c13.md` lines 10-64
- `../Fun/Resources/Questions/c13.md` lines 125-186
- `../Fun/Resources/Questions/c13.md` lines 194-253

### 10. Chapter 22 Material

Files:

- `../Fun/Resources/ch22.pdf`
- `../Fun/Resources/Questions/ch22.md`

Why it matters:

- covers HTTP, sockets, `URLConnection`, `URL`, and response handling
- supports report discussion of web communication concepts

Useful note references:

- `../Fun/Resources/Questions/ch22.md` lines 13-38
- `../Fun/Resources/Questions/ch22.md` lines 54-89
- `../Fun/Resources/Questions/ch22.md` lines 104-117

## Limits Of The PDF Sources

The local chapter PDFs are good for background and class concepts, especially:

- sockets
- TCP and UDP behavior
- URL and `URLConnection`
- HTTP basics

They are not enough by themselves for the exact implementation choices in this
project, especially:

- `ProcessBuilder`
- `inheritIO()`
- `startPipeline(...)`
- `HttpClient`
- `HttpResponse.BodyHandlers.ofByteArray()`

For those, the Oracle Java API docs above are the stronger sources.

## What Counts As Actual Evidence For The Report

Put these in `evidence/` as you run the demos:

- terminal output from compiling and running the IPC demo
- terminal output from running the HTTP demo
- the generated file `ipc-demo/producer-output.txt`
- short notes describing what changed between `inheritIO()`, captured streams,
  and file redirection
- short notes describing the meaning of the HTTP status code and headers shown
  in each run

## Runtime Evidence Collected

These files were generated from local demo runs and can be cited as direct
project evidence:

- `evidence/ipc-run-output.txt`
  - shows `inheritIO()`, captured stdout/stderr, pipeline output, file
    redirection, and exit codes
- `evidence/http-google-output.txt`
  - shows a successful `200` HTML response with `charset=ISO-8859-1`
- `evidence/http-github-output.txt`
  - shows a successful `200` JSON response with `charset=utf-8` and a
    `content-length`
- `evidence/http-redirect-output.txt`
  - shows a `301` redirect response and its `Location` header
- `evidence/http-401-output.txt`
  - shows a `401` authentication challenge and its `WWW-Authenticate` header
- `evidence/http-403-output.txt`
  - shows a `403` HTTP response that still provides status/header metadata
- `evidence/http-example-output.txt`
  - currently shows a successful `200` response from `https://example.com`
    with no declared charset, causing the demo to use its UTF-8 fallback
- `handshake.log`
  - earlier TLS debug evidence showing `SSLHandshakeException` and
    `PKIX path building failed`
